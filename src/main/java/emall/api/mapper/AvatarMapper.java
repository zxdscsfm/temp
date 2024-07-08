package emall.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import emall.api.entity.Avatar;
import org.apache.ibatis.annotations.Select;

public interface AvatarMapper extends BaseMapper<Avatar> {
    @Select("SELECT * FROM avatar WHERE md5 = #{md5}")
    Avatar selectByMd5(String md5);
}
