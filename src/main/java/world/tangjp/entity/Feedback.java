package world.tangjp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 反馈实体
 *
 * @author Tangjp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("feedback")
@ApiModel(description = "反馈信息")
public class Feedback {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "反馈ID", example = "1")
    private Integer id;

    /**
     * 反馈用户
     */
    @ApiModelProperty(value = "反馈用户", required = true, example = "张三")
    private String name;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱地址", required = true, example = "zhangsan@example.com")
    private String email;

    /**
     * 反馈标题
     */
    @ApiModelProperty(value = "反馈标题", required = true, example = "系统使用问题")
    private String title;

    /**
     * 反馈内容
     */
    @ApiModelProperty(value = "反馈内容", required = true, example = "在使用系统时，发现保存历史记录功能无法正常工作，请尽快修复。")
    private String content;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

}
