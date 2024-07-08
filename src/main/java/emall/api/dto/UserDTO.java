package emall.api.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String username;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
}
