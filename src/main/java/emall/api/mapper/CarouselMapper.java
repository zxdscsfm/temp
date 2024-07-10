package emall.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import emall.api.entity.Carousel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CarouselMapper extends BaseMapper<Carousel> {
    @Select("select carousel.*,good.name as good_name,good.imgs as img from carousel,good where good.id = carousel.good_id order by show_order asc")
    List<Carousel> getAllCarousels();
}
