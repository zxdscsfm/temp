package emall.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_order")
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String orderNo;
    private BigDecimal totalPrice;
    private int userId;
    private String linkUser;
    private String linkPhone;
    private String linkAddress;
    private String state;
    private String createTime;

    //该订单包含的商品信息
    @TableField(exist = false)
    private String goods;

    // 对应购物车id
    @TableField(exist = false)
    private Long cartId;
}
