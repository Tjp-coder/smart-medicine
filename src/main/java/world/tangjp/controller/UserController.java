package world.tangjp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.result.RespResult;
import world.tangjp.entity.User;
import world.tangjp.utils.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;

/**
 * 用户控制器
 *
 * @author Tangjp
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping(value = "user")
public class UserController extends BaseController<User> {

    /**
     * 修改资料
     */
    @ApiOperation("修改用户资料")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userName", value = "用户姓名"),
        @ApiImplicitParam(name = "userAge", value = "用户年龄"),
        @ApiImplicitParam(name = "userSex", value = "用户性别(0:男,1:女)"),
        @ApiImplicitParam(name = "userEmail", value = "用户邮箱"),
        @ApiImplicitParam(name = "userTel", value = "用户电话")
    })
    @PostMapping("/saveProfile")
    public RespResult saveProfile(@RequestBody User user) {
        if (Assert.isEmpty(user)) {
            return RespResult.fail("保存对象不能为空");
        }
        user = userService.save(user);
        session.setAttribute("loginUser", user);
        return RespResult.success("保存成功");
    }

    /**
     * 修改密码
     */
    @ApiOperation("修改用户密码")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "oldPass", value = "旧密码", required = true),
        @ApiImplicitParam(name = "newPass", value = "新密码", required = true)
    })
    @PostMapping("/savePassword")
    public RespResult savePassword(String oldPass, String newPass) {
        if (!loginUser.getUserPwd().equals(oldPass)) {
            return RespResult.fail("旧密码错误");
        }
        loginUser.setUserPwd(newPass);
        loginUser = userService.save(loginUser);
        session.setAttribute("loginUser", loginUser);
        return RespResult.success("保存成功");
    }

    @ApiOperation(value = "保存用户", notes = "新增或更新用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户ID(更新时传入)"),
        @ApiImplicitParam(name = "userAccount", value = "用户账号", required = true),
        @ApiImplicitParam(name = "userPwd", value = "用户密码", required = true),
        @ApiImplicitParam(name = "roleStatus", value = "用户角色(0:普通用户,1:管理员)", defaultValue = "0")
    })
    @Override
    public RespResult save(User user) {
        return super.save(user);
    }

    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, example = "1")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
