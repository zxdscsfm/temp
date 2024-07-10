package emall.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import emall.api.entity.Address;
import emall.api.mapper.AddressMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressService extends ServiceImpl<AddressMapper, Address> {
    @Resource
    private AddressMapper addressMapper;

    public List<Address> findAllById(Long userid) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);
        return addressMapper.selectList(wrapper);
    }


}
