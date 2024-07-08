package emall.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import emall.api.entity.User;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class TokenUtil {
    public static String generateToken(String userId, String username){
        String token = JWT.create()
                .withAudience(userId)
                .sign(Algorithm.HMAC256(username));
        return token;
    }

    public static boolean validateLogin() throws Exception {
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StringUtils.hasLength(token)){
                JWT.decode(token).getAudience();
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            throw new Exception("CODE_401 Login token error");
        }
    }

    public static boolean validateAdmin(){
        try{
            User user = UserHolder.getUser();
            if(user.getRole().equals("admin")){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        throw new RuntimeException("CODE_401 Admin token error");
    }
}
