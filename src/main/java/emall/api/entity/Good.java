package emall.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("good")
public class Good {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double discount;
    private Integer sales;
    private BigDecimal saleMoney;
    private Long categoryId;
    private String imgs;
    private String createTime;
    private Boolean recommend;
    private Boolean isDelete;
}
