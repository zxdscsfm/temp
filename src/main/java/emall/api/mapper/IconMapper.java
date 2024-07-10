package emall.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import emall.api.entity.Icon;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IconMapper extends BaseMapper<Icon> {
    List<Icon> getIconCategoryMapList();
}
