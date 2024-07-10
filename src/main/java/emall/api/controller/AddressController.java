package emall.api.controller;

import emall.api.common.Result;
import emall.api.entity.Address;
import emall.api.service.AddressService;
import emall.api.utils.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Resource
    private AddressService addressService;

    @GetMapping("/{userid}")
    public Result findAllByID(@PathVariable Long userid){
        return new Result(Constants.SUCCESS, addressService.findAllById(userid), "All address of the UserID:"+userid);
    }

    @PostMapping
    public Result saveOrUpdate(@RequestBody Address address) {
        boolean flag = addressService.saveOrUpdate(address);
        if(flag){
            return new Result(Constants.SUCCESS, null, "Save or Update successfully");
        }else{
            return new Result(Constants.SYSTEM_ERROR, null, "Failed to Save or Update");
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        addressService.removeById(id);
        return new Result(Constants.SUCCESS, null, "Delete successfully");
    }
}

