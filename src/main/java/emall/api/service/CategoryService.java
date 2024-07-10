package emall.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import emall.api.entity.Category;
import emall.api.entity.IconCategory;
import emall.api.mapper.CategoryMapper;
import emall.api.mapper.IconCategoryMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private IconCategoryMapper iconCategoryMapper;

    public Long add(Category category) {
        save(category);
        IconCategory iconCategory = new IconCategory();
        iconCategory.setCategoryId(category.getId());
        iconCategory.setIconId(category.getIconId());
        iconCategoryMapper.insert(iconCategory);
        //get the id
        Long id = categoryMapper.selectOne(new QueryWrapper<Category>().eq("name", category.getName())).getId();
        return id;
    }


    public boolean delete(Long id) {
        //delete the record in reference table
        iconCategoryMapper.delete(
                new QueryWrapper<IconCategory>().eq("category_id", id)
        );
        return removeById(id);
    }
}
