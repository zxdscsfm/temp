package emall.api.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import emall.api.entity.User;
import emall.api.service.UserService;
import emall.api.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Resource
    RedisTemplate<String, User> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");

        //let other method go
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        //if no token
        if(!StringUtils.hasLength(token)){
            throw  new RuntimeException("Token not found");
        }
        //get user object from redis cache
        User user = redisTemplate.opsForValue().get("user:token:" + token);

        if(user == null){
            throw  new RuntimeException("User in redis is not accessible");
        }

        //set the user object to ThreadLocal of current thread
        UserHolder.setUser(user);

        //reset the expiry time of token in redis cache
        redisTemplate.expire("user:token:" + token, 9, TimeUnit.HOURS);
        //check the token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUsername())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new RuntimeException("Token not accepted,please LOGIN AGAIN");
        }
        return true;
    }
}
