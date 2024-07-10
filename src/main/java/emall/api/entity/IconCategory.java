package emall.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("icon_category")
public class IconCategory {
    private Long iconId;

    private Long categoryId;
}
