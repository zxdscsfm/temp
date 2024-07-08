package emall.api.controller;

import emall.api.common.Result;
import emall.api.dto.UserDTO;
import emall.api.entity.LoginForm;
import emall.api.entity.User;
import emall.api.service.UserService;
import emall.api.utils.Contants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm form){
        UserDTO dto = userService.login(form);
        return new Result(Contants.SUCCESS, dto, "Login success");
    }

    @PostMapping("/register")
    public Result register(@RequestBody LoginForm form){
        UserDTO dto = userService.register(form);
        return new Result(Contants.SUCCESS, dto, "Register success");
    }

    @GetMapping("/userinfo/{id}")
    public Result getUserInfo(@PathVariable int id){
        User user = userService.getUserInfo(id);
        return new Result(Contants.SUCCESS, user, "UserInfo by id");
    }

    @GetMapping("/user")
    public Result getCurrentUser(){
        User currentUser = userService.getCurrentUser();
        return new Result(Contants.SUCCESS, currentUser, "Login User currently");
    }

}
