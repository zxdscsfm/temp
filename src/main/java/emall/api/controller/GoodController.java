package emall.api.controller;

import emall.api.annotation.Authority;
import emall.api.common.AuthorityLevel;
import emall.api.common.Result;
import emall.api.entity.Good;
import emall.api.service.GoodService;
import emall.api.utils.Contants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/good")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @GetMapping("/{id}")
    public Result getGoodById(@PathVariable Long id){
        Good good = goodService.findById(id);
        return new Result(Contants.SUCCESS, good, "Get GoodID:" + id);
    }

    @Authority(AuthorityLevel.ADMIN)
    @PostMapping
    public Result saveOrUpdate(@RequestBody Good good) {
        Long id = goodService.saveOrUpdateGood(good);
        return new Result(Contants.SUCCESS, id, "Save or Update to GoodID" + id);
    }

    @Authority(AuthorityLevel.ADMIN)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        goodService.deleteGood(id);
        return new Result(Contants.SUCCESS, id, "GoodID" + id + " was Deleted");
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return new Result(Contants.SUCCESS, goodService.findById(id), "Find Good by GoodID:" + id);
    }



}
