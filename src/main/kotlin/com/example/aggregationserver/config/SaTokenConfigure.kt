package com.example.aggregationserver.config

import cn.dev33.satoken.interceptor.SaInterceptor
import cn.dev33.satoken.stp.StpUtil
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class SaTokenConfigure : WebMvcConfigurer {
    // 注册 Sa-Token 拦截器
    override fun addInterceptors(registry: InterceptorRegistry) {
        // 注册 Sa-Token 的路由拦截器
        registry.addInterceptor(SaInterceptor { StpUtil.checkLogin() })
            // 拦截全部路由
            .addPathPatterns("/**")
            // 排除登录接口
            .excludePathPatterns("/user/register")
            .excludePathPatterns("/user/login")
            .excludePathPatterns("/user/isLogin")
            .excludePathPatterns("/user/logout")
    }
}


