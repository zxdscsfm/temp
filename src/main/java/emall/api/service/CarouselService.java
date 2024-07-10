package emall.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import emall.api.common.Result;
import emall.api.entity.Carousel;
import emall.api.mapper.CarouselMapper;
import emall.api.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarouselService extends ServiceImpl<CarouselMapper, Carousel> {
    @Resource
    private CarouselMapper carouselMapper;

    public Carousel findById(int id) {
        QueryWrapper<Carousel> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return  carouselMapper.selectOne(wrapper);
    }

    public List<Carousel> getAllCarousels(){
        return carouselMapper.getAllCarousels();
    }
}
