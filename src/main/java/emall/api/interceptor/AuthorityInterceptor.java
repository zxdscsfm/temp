package emall.api.interceptor;

import emall.api.annotation.Authority;
import emall.api.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)) return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //get the method and class that will be used
        Method method = handlerMethod.getMethod();
        Class<?> clazz = handlerMethod.getBeanType();
        //get the annotation from method or class
        Authority annotation = null;
        if(method != null && clazz != null){
            boolean annotatedMethod = method.isAnnotationPresent(Authority.class);
            boolean annotatedClass = clazz.isAnnotationPresent(Authority.class);
            if(annotatedMethod){
                annotation = method.getAnnotation(Authority.class);
            }else if(annotatedClass){
                annotation = clazz.getAnnotation(Authority.class);
            }
        }
        if(annotation != null){
            switch (annotation.value()) {
                case ALL:
                    return true;
                case LOGIN:
                    return TokenUtil.validateLogin();
                case ADMIN:
                    return TokenUtil.validateAdmin();
            }
        }
        return true;
    }
}
