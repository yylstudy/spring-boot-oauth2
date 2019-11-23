package com.yyl.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/18 10:42
 * @Version: 1.0
 */
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/my_approval_page").setViewName("oauth_approval");
    }
}
