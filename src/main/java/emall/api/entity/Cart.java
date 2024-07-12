package emall.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cart")
public class Cart {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer count;
    private String createTime;
    private Long goodId;
    private String standard;
    private Long userId;

}
