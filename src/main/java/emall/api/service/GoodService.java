package emall.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import emall.api.entity.Good;
import emall.api.mapper.GoodMapper;
import emall.api.utils.Contants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}
