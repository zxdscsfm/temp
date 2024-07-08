package emall.api.annotation;

import emall.api.common.AuthorityLevel;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Authority {
    AuthorityLevel value() default AuthorityLevel.LOGIN;
}
