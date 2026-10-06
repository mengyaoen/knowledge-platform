package com.mye.knowledgeplatform.config;

import com.mye.knowledgeplatform.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类：用于注册拦截器
 */
@Configuration // 告诉 SpringBoot 这是一个配置类
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // 拦截所有请求（/** 代表拦截全部路径）
                .addPathPatterns("/**")
                // 放行登录接口、用户注册接口，否则用户没法登录进来
                .excludePathPatterns(
                        "/api/user/login",
                        "/api/user/register" // 预留注册接口
                );
    }
}