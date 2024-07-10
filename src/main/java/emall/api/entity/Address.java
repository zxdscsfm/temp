package emall.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("address")
public class Address {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String linkUser;
    private String linkAddress;
    private String linkPhone;
    private Long userId;
}
