package emall.api.controller;

import emall.api.annotation.Authority;
import emall.api.common.AuthorityLevel;
import emall.api.common.Result;
import emall.api.entity.Carousel;
import emall.api.entity.Good;
import emall.api.service.CarouselService;
import emall.api.service.GoodService;
import emall.api.utils.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/carousel")
public class CarouselController {
    @Resource
    private CarouselService carouselService;

    @Resource
    private GoodService goodService;

    @GetMapping("/{id}")
    public Result findById(@PathVariable int id){
        return new Result(Constants.SUCCESS, carouselService.findById(id), "Carousel ID:"+id);
    }

    @GetMapping
    public Result getAllCarousels(){
        return new Result(Constants.SUCCESS, carouselService.getAllCarousels(), "All Carousel");
    }

    @Authority(AuthorityLevel.ADMIN)
    @PostMapping
    public Result saveOrUpdate(@RequestBody Carousel carousel) {
        Good good = goodService.getById(carousel.getGoodId());
        if(good == null) {
            return new Result(Constants.NO_RESULT, null, "Not found the good which is linked to the Carousel in Database");
        }
        carouselService.saveOrUpdate(carousel);
        return new Result(Constants.SUCCESS, null, "Save successfully");
    }


    @Authority(AuthorityLevel.ADMIN)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        carouselService.removeById(id);
        return new Result(Constants.SUCCESS, null, "Delete successfully");
    }
}
