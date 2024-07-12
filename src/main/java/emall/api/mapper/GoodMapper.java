package emall.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import emall.api.entity.Good;
import emall.api.entity.Standard;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

public interface GoodMapper extends BaseMapper<Good> {

    @Select("SELECT * FROM `good` WHERE is_delete = 0 ORDER BY sale_money DESC LIMIT 0,#{num}")
    List<Good> getSaleRank(int num);

    @Select("SELECT discount * MIN(price) FROM good_standard gs, good WHERE good.id = gs.good_id AND good.id = #{id} ")
    BigDecimal getMinPrice(Long id);

    @Select("select * from good_standard where good_id = #{id}")
    List<Standard> getStandardById(int id);

    boolean saleGood(@Param("id")Long goodId, @Param("count") int count, @Param("money") BigDecimal totalPrice);
}
