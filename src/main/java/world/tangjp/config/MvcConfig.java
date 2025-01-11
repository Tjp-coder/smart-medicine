package world.tangjp.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import world.tangjp.component.LoginHandlerInterceptor;

/**
 * MVC 配置
 *
 * @author Tangjp
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer, ErrorPageRegistrar {

    /**
     * 注册视图控制器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 注册错误页面
        registry.addViewController("/400").setViewName("error/400");
        registry.addViewController("/401").setViewName("error/401");
        registry.addViewController("/404").setViewName("error/404");
        registry.addViewController("/500").setViewName("error/500");

        registry.addViewController("/feedback.html").setViewName("feedback");
        registry.addViewController("/empty.html").setViewName("empty");
    }

    /**
     * 配置错误页面
     */
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400");
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
        registry.addErrorPages(error400Page, error401Page, error404Page, error500Page);
    }

    /**
     * 注册登录拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器并指定需要拦截的路径
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/doctor", "/all-feedback", "/profile", "/add-illness",
                        "/add-medical", "/all-illness", "/all-medical")
                .excludePathPatterns(
                        "/",
                        "/index.html",
                        "/logout",
                        "/findIllness",
                        "/findIllnessOne",
                        "/findMedicineOne",
                        "/findMedicines",
                        "/globalSelect"); // 排除无需验证的路径
    }

}
