package emall.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value="icon")
public class Icon {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String value;

    @TableField(exist = false)
    private List<Category> categories;
}
