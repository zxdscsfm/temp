package emall.api.common;

import emall.api.dto.UserDTO;
import lombok.Data;

@Data
public class Result {
    public int code;
    public Object data;
    public String message;

    public Result(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
