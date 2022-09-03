package com.village.villageupload.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.village.villageupload.common.UserUtil;
import com.village.villageupload.exception.LoginException;
import com.village.villageupload.exception.enums.LoginEnum;
import com.village.villageupload.login.entity.UserEntity;
import org.springframework.context.annotation.Configuration;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfig implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Sa-Token的路由拦截器
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler)->{
            // 登录认证 -- 拦截所有路由 可通过自定义方法进行鉴权
//            SaRouter.match("/**", r -> StpUtil.checkLogin());
            SaRouter.match("/**", r -> checkUserInfo());
            System.out.println(UserUtil.getUserInfo());
        }))
                .addPathPatterns("/**")
                .excludePathPatterns("/captcha/get")
                .excludePathPatterns("/captcha/check")
                .excludePathPatterns("/login")
                .excludePathPatterns("/pdf");
    }
    public void checkUserInfo(){
        UserEntity userInfo = UserUtil.getUserInfo();
        if(!ObjectUtil.isNotEmpty(userInfo)){
            throw new LoginException(LoginEnum.NO_LOGIN_ERROR);
        }
    }
}
