package emall.api.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import emall.api.entity.OrderGoods;
import emall.api.mapper.OrderGoodsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderGoodsService extends ServiceImpl<OrderGoodsMapper, OrderGoods> {
    @Resource
    private OrderGoodsMapper orderGoodsMapper;
}
