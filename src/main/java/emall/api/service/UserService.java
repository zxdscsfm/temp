package emall.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import emall.api.dto.UserDTO;
import emall.api.entity.LoginForm;
import emall.api.entity.User;
import emall.api.mapper.UserMapper;
import emall.api.utils.TokenUtil;
import emall.api.utils.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    public UserDTO login(LoginForm form){
        //query user from database
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", form.getUsername());
        wrapper.eq("password", form.getPassword());
        User user = userMapper.selectOne(wrapper);
        if(user == null){
            throw new RuntimeException("Incorrect username or password");
        }
        //token
        String token = TokenUtil.generateToken(String.valueOf(user.getId()), user.getUsername());
        //set redis cache
        redisTemplate.opsForValue().set("user:token:" + token, user, 9, TimeUnit.HOURS);
        //return a DTO
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        dto.setToken(token);
        return dto;
    }

    public UserDTO register(LoginForm form){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", form.getUsername());
        User user = userMapper.selectOne(wrapper);
        if(user != null){
            throw new RuntimeException("This username had been used");
        }
        //Database has no same username
        user = setNewUser(form.getUsername(), form.getPassword());
        userMapper.insert(user);
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public User getUserInfo(int id){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        User user = userMapper.selectOne(wrapper);
        if(user == null){
            throw new RuntimeException("There is no such useId in Database");
        }
        return user;
    }

    public User getCurrentUser(){
        return UserHolder.getUser();
    }
    private User setNewUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname("New User");
        user.setRole("user");
        return user;
    }

}
