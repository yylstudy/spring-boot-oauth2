package com.yyl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/18 10:42
 * @Version: 1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/github_success").setViewName("github_success");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/oauth2_server_success").setViewName("oauth2_server_success");
        registry.addViewController("/oauth/my_approval_page").setViewName("oauth_approval");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        super.addResourceHandlers(registry);
    }
}
