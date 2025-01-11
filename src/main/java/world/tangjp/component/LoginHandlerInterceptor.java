package world.tangjp.component;

import org.springframework.web.servlet.HandlerInterceptor;
import world.tangjp.entity.User;
import world.tangjp.utils.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author Tangjp
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    /**
     * 在目标方式执行之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("loginUser");
        // 如果用户未登录，重定向到首页
        if (Assert.isEmpty(user)) {
            response.sendRedirect("/");
            return false;
        }

        // 用户已登录，允许继续访问
        return true;
    }
}
