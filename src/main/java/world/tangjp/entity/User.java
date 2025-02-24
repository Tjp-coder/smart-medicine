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

import java.util.Date;

/**
 * 用户实体
 *
 * @author Tangjp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
@ApiModel(value = "用户实体", description = "用户信息实体类")
public class User {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID", example = "1")
    private Integer id;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", example = "zhangsan")
    private String userAccount;

    /**
     * 用户真实名字
     */
    @ApiModelProperty(value = "用户真实姓名", example = "张三")
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", example = "123456")
    private String userPwd;

    /**
     * 用户年龄
     */
    @ApiModelProperty(value = "用户年龄", example = "25")
    private Integer userAge;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "用户性别", example = "男")
    private String userSex;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱", example = "zhangsan@example.com")
    private String userEmail;

    /**
     * 用户电话
     */
    @ApiModelProperty(value = "用户电话", example = "13800138000")
    private String userTel;

    /**
     * 角色状态，1代表管理员，0普通用户
     */
    @ApiModelProperty(value = "角色状态(1:管理员,0:普通用户)", example = "0")
    private Integer roleStatus;

    /**
     * 图片的地址
     */
    @ApiModelProperty(value = "用户头像地址")
    private String imgPath;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
