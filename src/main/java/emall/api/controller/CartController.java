package emall.api.controller;

import cn.hutool.core.date.DateUtil;
import emall.api.annotation.Authority;
import emall.api.common.AuthorityLevel;
import emall.api.common.Result;
import emall.api.entity.Cart;
import emall.api.service.CartService;
import emall.api.utils.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Authority(AuthorityLevel.LOGIN)
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Resource
    private CartService cartService;

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Long id) {
        return new Result(Constants.SUCCESS, cartService.getById(id), "Cart of CartID:" + id);
    }

    @Authority(AuthorityLevel.ADMIN)
    @GetMapping
    public Result findAll() {
        List<Cart> list = cartService.list();
        return new Result(Constants.SUCCESS, list, "All Carts");
    }

    @GetMapping("/userid/{userId}")
    public Result selectByUserId(@PathVariable Long userId) {
        return new Result(Constants.SUCCESS, cartService.selectByUserId(userId), "Cart of UserID:" + userId) ;
    }

    @PostMapping
    public Result save(@RequestBody Cart cart) {
        cart.setCreateTime(DateUtil.now());
        cartService.saveOrUpdate(cart);
        return new Result(Constants.SUCCESS, null, " Save successfully");
    }

    @PutMapping
    public Result update(@RequestBody Cart cart) {
        cartService.updateById(cart);
        return new Result(Constants.SUCCESS, null, " Update successfully");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        cartService.removeById(id);
        return new Result(Constants.SUCCESS, null, " Delete successfully");
    }

}
