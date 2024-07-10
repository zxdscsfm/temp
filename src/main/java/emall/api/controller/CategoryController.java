package emall.api.controller;

import emall.api.annotation.Authority;
import emall.api.common.AuthorityLevel;
import emall.api.common.Result;
import emall.api.entity.Category;
import emall.api.service.CategoryService;
import emall.api.utils.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;


    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return new Result(Constants.SUCCESS, categoryService.getById(id), "Category ID:" + id);
    }

    @GetMapping
    public Result findAll() {
        List<Category> list = categoryService.list();
        return new Result(Constants.SUCCESS, list, "All Category");
    }


    @PostMapping
    public Result save(@RequestBody Category category) {
        if(categoryService.saveOrUpdate(category)){
            return new Result(Constants.SUCCESS, null, "Save Or Update successfully");
        }
        return new Result(Constants.SYSTEM_ERROR, null, "Failed to Save or Update ");
    }

    /**
     * add category with the icon
     * @param category
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        Long id = categoryService.add(category);
        return new Result(Constants.SUCCESS, id, "Add category with CategoryID:" + id);
    }

    @Authority(AuthorityLevel.ADMIN)
    @PutMapping
    public Result update(@RequestBody Category category) {
        categoryService.updateById(category);
        return new Result(Constants.SUCCESS, null, "Update categoryID:" + category.getId() + " successfully");
    }


    @Authority(AuthorityLevel.ADMIN)
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Long id) {
        if(categoryService.delete(id)){
            return new Result(Constants.SUCCESS, null, "Delete Category successfully");
        }
        return new Result(Constants.SYSTEM_ERROR, null, "Failed to delete Category");
    }
}
