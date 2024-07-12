package emall.api.entity;

import lombok.Data;

/**
 * get the information when user create a new order from Cart
 */
@Data
public class OrderItem {
    private long id;
    private String standard;
    private int num;
}
