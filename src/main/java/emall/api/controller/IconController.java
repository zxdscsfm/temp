package emall.api.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import emall.api.annotation.Authority;
import emall.api.common.AuthorityLevel;
import emall.api.common.Result;
import emall.api.entity.Icon;
import emall.api.entity.User;
import emall.api.service.IconService;
import emall.api.service.UserService;
import emall.api.utils.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/icon")
public class IconController {
    @Resource
    private IconService iconService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserService userService;



    /*
    查询
    */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return new Result(Constants.SUCCESS, iconService.getById(id), "IconID:" + id);
    }

    @GetMapping
    public Result findAll() {
        List<Icon> list = iconService.getIconCategoryMapList();
        return new Result(Constants.SUCCESS, list, "All Icons as List");
    }


    /*
    保存
    */
    @Authority(AuthorityLevel.ADMIN)
    @PostMapping
    public Result save(@RequestBody Icon icon) {
        iconService.saveOrUpdate(icon);
        return new Result(Constants.SUCCESS, null, "Save or update successfully");
    }

    @Authority(AuthorityLevel.ADMIN)
    @PutMapping
    public Result update(@RequestBody Icon icon) {
        iconService.updateById(icon);
        return new Result(Constants.SUCCESS, null, "Save or update successfully");
    }

    /*
     *删除
     */
    @Authority(AuthorityLevel.ADMIN)
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Long id) {
        if(iconService.deleteById(id) > 0){
            return new Result(Constants.SUCCESS, null, "Delete successfully");
        }
        return new Result(Constants.SYSTEM_ERROR, null, "Failed to delete");
    }
}
