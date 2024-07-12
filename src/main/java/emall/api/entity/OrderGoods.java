package emall.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order_goods")
public class OrderGoods {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;
    private Long goodId;
    private Integer count;
    private String standard;
}
