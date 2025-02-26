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
    @Override
    public RespResult save(@RequestBody User user) {
        return super.save(user);
    }

    @ApiOperation("删除用户")
    @Override
    public RespResult delete(Integer id) {
        return super.delete(id);
    }
}
