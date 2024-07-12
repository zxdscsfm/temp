package emall.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import emall.api.entity.Cart;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface CartMapper extends BaseMapper<Cart> {
    @MapKey("id")
    List<Map<String, Object>> selectByUserId(Long userId);
}
