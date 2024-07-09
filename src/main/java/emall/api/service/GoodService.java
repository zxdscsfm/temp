package emall.api.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import emall.api.dto.GoodDTO;
import emall.api.entity.Good;
import emall.api.entity.Standard;
import emall.api.mapper.GoodMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GoodService extends ServiceImpl<GoodMapper, Good> {
    @Resource
    private GoodMapper goodMapper;

    @Resource
    private RedisTemplate<String, Good> redisTemplate;

    public Good findById(Long id){
        String redisKey = "good:id:" + id;
        Good redisGood = redisTemplate.opsForValue().get(redisKey);
        if(redisGood != null){
            redisTemplate.expire(redisKey, 180, TimeUnit.MINUTES);
            return redisGood;
        }
        QueryWrapper<Good> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("is_delete", false);
        Good dbGood = goodMapper.selectOne(wrapper);
        if(dbGood != null){
            redisTemplate.opsForValue().set(redisKey, dbGood);
            redisTemplate.expire(redisKey, 180, TimeUnit.MINUTES);
            return dbGood;
        }else{
            throw new RuntimeException("Request GoodID:" + id + " not found in Database");
        }
    }

    public Long saveOrUpdateGood(Good good) {
        System.out.println(good);
        if (good.getId() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            good.setCreateTime(formatter.format(LocalDateTime.now()));
            goodMapper.insert(good);
        } else {
            UpdateWrapper<Good> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", good.getId());
            goodMapper.update(good, updateWrapper);
            redisTemplate.delete("good:id:" + good.getId());
        }
        return good.getId();
    }

    public void deleteGood(Long id) {
        redisTemplate.delete("good:id:" + id);
        UpdateWrapper<Good> wrapper = new UpdateWrapper<>();
        wrapper.set("is_delete", 1)
                .eq("id", id);

        goodMapper.update(null, wrapper);
    }

    public boolean setRecommend(Long id, Boolean isRecommend) {
        UpdateWrapper<Good> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("recommend", isRecommend);
        return goodMapper.update(null, updateWrapper) > 0 ? true : false;
    }

    public List<Good> getSaleRank(int num) {
        return goodMapper.getSaleRank(num);
    }

    public IPage<GoodDTO> findPage(Integer pageNum, Integer pageSize, String searchText, Integer categoryId) {
        //using LambdaQueryWrapper to do a more complex SQL
        LambdaQueryWrapper<Good> query = Wrappers.<Good>lambdaQuery().orderByDesc(Good::getId);

        //make a SQL
        if (StrUtil.isNotBlank(searchText)) {
            query.and(q -> q.like(Good::getName, searchText)
                    .or()
                    .like(Good::getDescription, searchText)
                    .or()
                    .eq(Good::getId, searchText));
        }
        if (categoryId != null) {
            query.eq(Good::getCategoryId, categoryId);
        }
        query.eq(Good::getIsDelete, false);

        //find a page in database
        IPage<Good> page = this.page(new Page<>(pageNum, pageSize), query);

        //turn to DTO
        IPage<GoodDTO> goodDTOPage = page.convert(good -> {
            GoodDTO goodDTO = new GoodDTO();
            BeanUtil.copyProperties(good, goodDTO);
            return goodDTO;
        });

        //show the lowest price of the good
        for (GoodDTO good : goodDTOPage.getRecords()) {
            good.setPrice(goodMapper.getMinPrice(good.getId()));
        }
        return goodDTOPage;
    }

    public String getStandard(int id) {
        List<Standard> standards = goodMapper.getStandardById(id);
        if (standards.size() == 0) {
            throw new RuntimeException("Not found Standard of GoodID: " + id);
        }
        return JSON.toJSONString(standards);
    }
}
