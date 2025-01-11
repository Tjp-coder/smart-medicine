package world.tangjp.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.tangjp.result.RespResult;
import world.tangjp.entity.User;
import world.tangjp.utils.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登录控制器

 * @author Tangjp
 */
@RestController
@RequestMapping(value = "login")
public class LoginController extends BaseController<User> {

    /**
     * 注册
     */
    @PostMapping("/register")
    public RespResult register(User user, String code) {
        // 获取用户输入的邮箱地址
        String email = user.getUserEmail();
        // 检查邮箱是否为空，如果为空则返回失败响应
        if (Assert.isEmpty(email)) {
            return RespResult.fail("邮箱不能为空");
        }
        // 从session中获取发送到该邮箱的验证码信息
        Map<String, Object> codeData = (Map<String, Object>) session.getAttribute("EMAIL_CODE" + email);
        // 如果没有找到验证码信息，则返回失败响应
        if (codeData == null) {
            return RespResult.fail("尚未发送验证码");
        }
        // 获取验证码信息中的验证码和发送时间
        String sentCode = (String) codeData.get("code");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) codeData.get("time"));
        calendar.add(Calendar.MINUTE, 5); // 验证码有效期 5 分钟

        // 检查验证码是否已过期，如果过期则删除验证码信息并返回失败响应
        if (System.currentTimeMillis() > calendar.getTime().getTime()) {
            session.removeAttribute("EMAIL_CODE" + email); // 超时删除
            return RespResult.fail("验证码已经超时");
        }
        // 比较用户输入的验证码和发送的验证码是否一致，如果不一致则返回失败响应
        if (!sentCode.equals(code)) {
            return RespResult.fail("验证码错误");
        }
        // 查询数据库中是否已存在该用户账户，如果存在则返回失败响应
        List<User> query = userService.query(User.builder().userAccount(user.getUserAccount()).build());
        if (Assert.notEmpty(query)) {
            return RespResult.fail("账户已被注册");
        }
        // 设置用户角色状态和默认头像路径
        user.setRoleStatus(0);
        user.setImgPath("https://moti-cloud-v2.oss-cn-beijing.aliyuncs.com/Snipaste_2022-05-01_15-37-01.png");
        user = userService.save(user);
        // 将登录用户信息保存到session
        session.setAttribute("loginUser", user);
        // 返回注册成功响应
        return RespResult.success("注册成功", user);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public RespResult login(User user) {
        List<User> users = userService.query(user);
        if (Assert.notEmpty(users)) {
            session.setAttribute("loginUser", users.get(0));
            return RespResult.success("登录成功");
        }
        if (Assert.isEmpty(userService.query(User.builder().userAccount(user.getUserAccount()).build()))) {
            return RespResult.fail("账户尚未注册");
        }
        return RespResult.fail("密码错误");
    }

    /**
     * 发送邮箱验证码
     */
    @PostMapping("/sendEmailCode")
    public RespResult sendEmailCode(String email, Map<String, Object> map) {
        if (StrUtil.isEmpty(email)) {
            return RespResult.fail("邮箱不可为空");
        }
        // 发送验证码
        String verifyCode = emailClient.sendEmailCode(email);
        map.put("email", email);
        map.put("code", verifyCode);
        map.put("time", new Date());
        session.setAttribute("EMAIL_CODE" + email, map);
        return RespResult.success("发送成功");
    }
}
