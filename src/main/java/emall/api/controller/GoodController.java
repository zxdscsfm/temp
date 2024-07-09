package emall.api.controller;

import emall.api.annotation.Authority;
import emall.api.common.AuthorityLevel;
import emall.api.common.Result;
import emall.api.entity.Good;
import emall.api.entity.Standard;
import emall.api.service.GoodService;
import emall.api.service.StandardService;
import emall.api.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/good")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @Resource
    private StandardService standardService;

    @GetMapping("/{id}")
    public Result getGoodById(@PathVariable Long id){
        Good good = goodService.findById(id);
        return new Result(Constants.SUCCESS, good, "Get GoodID:" + id);
    }

    @Authority(AuthorityLevel.ADMIN)
    @PostMapping
    public Result saveOrUpdate(@RequestBody Good good) {
        Long id = goodService.saveOrUpdateGood(good);
        return new Result(Constants.SUCCESS, id, "Save or Update to GoodID" + id);
    }

    @Authority(AuthorityLevel.ADMIN)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        goodService.deleteGood(id);
        return new Result(Constants.SUCCESS, id, "GoodID" + id + " was Deleted");
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return new Result(Constants.SUCCESS, goodService.findById(id), "Find Good by GoodID:" + id);
    }

    @GetMapping("/standard/{id}")
    public Result getStandard(@PathVariable int id) {
        return new Result(Constants.SUCCESS, goodService.getStandard(id), "Good Standard of GoodID:" + id);
    }

    @GetMapping("/rank")
    public Result getSaleRank(@RequestParam int num){
        return new Result(Constants.SUCCESS, goodService.getSaleRank(num), "Top "+num+" sale rank");
    }

    @PostMapping("/standard")
    public Result saveStandard(@RequestBody List<Standard> standards, @RequestParam int goodId) {
        //delete old standard
        standardService.deleteAll(goodId);
        //insert new standard
        for (Standard standard : standards) {
            standard.setGoodId(goodId);
            if(!standardService.save(standard)){
                return new Result(Constants.SYSTEM_ERROR, null, "Save Failed");
            }
        }
        return new Result(Constants.SUCCESS, null, "Save Successfully");
    }

    @Authority(AuthorityLevel.ADMIN)
    @DeleteMapping("/standard")
    public Result deleteStandard(@RequestBody Standard standard) {
        boolean delete = standardService.delete(standard);
        if(delete) {
            return new Result(Constants.SUCCESS, null, "Delete Successfully");
        }else {
            return new Result(Constants.SYSTEM_ERROR, null, "Delete Failed");
        }
    }

    @Authority(AuthorityLevel.ADMIN)
    @GetMapping("/recommend")
    public Result setRecommend(@RequestParam Long id,@RequestParam Boolean isRecommend){
        return new Result(Constants.SUCCESS, goodService.setRecommend(id,isRecommend), "Set RecommendColumn Successfully");
    }

    @GetMapping("/page")
    public Result findPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String searchText,
            @RequestParam(required = false) Integer categoryId) {

        return new Result(Constants.SUCCESS, goodService.findPage(pageNum,pageSize,searchText,categoryId), "Page of the results for SearchText = " + searchText);
    }

}
