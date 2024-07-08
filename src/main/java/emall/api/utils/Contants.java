package emall.api.utils;

public class Contants {
    public static final int SUCCESS = 200;
    public static final int SYSTEM_ERROR = 500;
    public static final int NO_RESULT = 510;
    public static final int NO_AUTHORITY = 401;
    public static final int TOKEN_ERROR = 401;
    public static final int REFUSE = 403;
    //文件存储位置
    public static final String fileFolderPath = System.getProperty("user.dir") + "/file/";
    public static final String avatarFolderPath =  System.getProperty("user.dir") + "/avatar/";
}
