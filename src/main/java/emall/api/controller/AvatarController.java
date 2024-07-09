package emall.api.controller;

import emall.api.common.Result;
import emall.api.service.AvatarService;
import emall.api.utils.Constants;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    @Resource
    private AvatarService avatarService;

    @PostMapping("")
    public Result upload(@RequestBody MultipartFile file){
        String url = avatarService.upload(file);
        return new Result(Constants.SUCCESS, url, "Upload success");
    }

    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response){
        avatarService.download(fileName, response);
    }
}
