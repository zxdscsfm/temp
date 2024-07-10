package emall.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import emall.api.entity.Icon;
import emall.api.entity.IconCategory;
import emall.api.mapper.IconCategoryMapper;
import emall.api.mapper.IconMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class IconService extends ServiceImpl<IconMapper, Icon> {
    @Resource
    private IconMapper iconMapper;

    @Resource
    private IconCategoryMapper iconCategoryMapper;

    public List<Icon> getIconCategoryMapList() {
        return iconMapper.getIconCategoryMapList();
    }

    /**
     * delete the Icon record
     * @param id
     * @return the record count that this function deleted
     */
    public Long deleteById(Long id) {
        // delete icon except some icons below it
        Long count = iconCategoryMapper.selectCount(
                new QueryWrapper<IconCategory>().eq("icon_id", id)
        );
        if (count > 0) {
            throw new RuntimeException("Try to delete IconID" + id + " failed, maybe the icon have its own icons below");
        }
        removeById(id);
        return count;
    }
}
