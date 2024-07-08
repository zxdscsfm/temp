package emall.api.utils;

import emall.api.entity.User;

public class UserHolder {
    public static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    public static User getUser(){
        return userThreadLocal.get();
    }
    public static void setUser(User user){
        userThreadLocal.set(user);
    }
}
