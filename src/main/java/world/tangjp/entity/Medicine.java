package world.tangjp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 药品实体
 *
 * @author Tangjp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("medicine")
@ApiModel(value = "药品实体", description = "药品信息实体类")
public class Medicine {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "药品ID", example = "1")
    private Integer id;

    /**
     * 药物名字
     */
    @ApiModelProperty(value = "药品名称", example = "阿莫西林")
    private String medicineName;

    /**
     * 关键字搜索
     */
    @ApiModelProperty(value = "搜索关键词", example = "消炎药,感冒药")
    private String keyword;

    /**
     * 药物的功效
     */
    @ApiModelProperty(value = "药品功效")
    private String medicineEffect;

    /**
     * 药物的品牌
     */
    @ApiModelProperty(value = "药品品牌", example = "仁和")
    private String medicineBrand;

    /**
     * 药物的相互作用
     */
    @ApiModelProperty(value = "药品相互作用")
    private String interaction;

    /**
     * 禁忌
     */
    @ApiModelProperty(value = "禁忌事项")
    private String taboo;

    /**
     * 用法用量
     */
    @ApiModelProperty(value = "用法用量")
    private String usAge;

    /**
     * 药物的类型，0代表西药，1中药，2中成药
     */
    @ApiModelProperty(value = "药品类型(0:西药,1:中药,2:中成药)", example = "0")
    private Integer medicineType;

    /**
     * 药物的图片地址
     */
    @ApiModelProperty(value = "药品图片地址")
    private String imgPath;

    /**
     * 药物的价格
     */
    @ApiModelProperty(value = "药品价格", example = "19.9")
    private BigDecimal medicinePrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
