package emall.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import emall.api.annotation.Authority;
import emall.api.common.AuthorityLevel;
import emall.api.common.Result;
import emall.api.entity.Order;
import emall.api.service.OrderService;
import emall.api.utils.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Authority(AuthorityLevel.LOGIN)
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private OrderService orderService;


    /*
    查询
    */
    @GetMapping("/userid/{userid}")
    public Result selectByUserId(@PathVariable int userid) {
        return new Result(Constants.SUCCESS, orderService.selectByUserId(userid), "Order of UserID:" + userid);
    }

    @GetMapping("/orderNo/{orderNo}")
    public Result selectByOrderNo(@PathVariable String orderNo) {
        return new Result(Constants.SUCCESS, orderService.selectByOrderNo(orderNo), "OrderID:" + orderNo);
    }

    @GetMapping
    public Result findAll() {
        List<Order> list = orderService.list();
        return new Result(Constants.SUCCESS, list, "All order");
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam int pageNum,
                           @RequestParam int pageSize,
                           String orderNo,String state){
        IPage<Order> orderPage = new Page<>(pageNum,pageSize);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.ne("state","待付款");
        if(!Util.isEmptyString(state)){
            orderQueryWrapper.eq("state",state);
        }
        if(!Util.isEmptyString(orderNo)){
            orderQueryWrapper.like("order_no",orderNo);
        }

        orderQueryWrapper.orderByDesc("create_time");
        return new Result(Constants.SUCCESS, orderService.page(orderPage,orderQueryWrapper), "Order by page");
    }

    @PostMapping
    public Result save(@RequestBody Order order) {
        String orderNo = orderService.saveOrder(order);
        return new Result(Constants.SUCCESS, orderNo, "Save successfully");

    }

    @GetMapping("/paid/{orderNo}")
    public Result payOrder(@PathVariable String orderNo){
        orderService.payOrder(orderNo);
        return new Result(Constants.SUCCESS, null, "Pay successfully");
    }

    //发货
    @Authority(AuthorityLevel.ADMIN)
    @GetMapping("/delivery/{orderNo}")
    public Result delivery(@PathVariable String orderNo){
        orderService.delivery(orderNo);
        return new Result(Constants.SUCCESS, null, "Start delivery OrderID:" + orderNo);
    }

    //确认收货
    @GetMapping("/received/{orderNo}")
    public Result receiveOrder(@PathVariable String orderNo){
        if(orderService.receiveOrder(orderNo)){
            return new Result(Constants.SUCCESS, null, "Get order successfully");
        }
        else {
            return new Result(Constants.SYSTEM_ERROR, null ,"Failed to get order");
        }
    }

    @PutMapping
    public Result update(@RequestBody Order order) {
        orderService.updateById(order);
        return new Result(Constants.SUCCESS, null, "Update successfully");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        orderService.removeById(id);
        return new Result(Constants.SUCCESS, null, "Delete successfully");
    }
}
