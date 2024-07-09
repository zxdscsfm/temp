package emall.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("good_standard")
public class Standard {
    private Integer goodId;
    private String value;
    private BigDecimal price;
    private Integer store;
}
