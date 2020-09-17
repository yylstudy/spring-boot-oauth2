package com.yyl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @Description: oauth2资源服务器设置
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/21 22:02
 * @Version: 1.0
 */
@EnableResourceServer
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "authmenu";
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //关闭stateless 则accessToken使用时会被记录，后续请求不带accessToken也可以正常被响应
        resources.resourceId(RESOURCE_ID).stateless(true);
    }

    /**
     * 配置需要token验证的url
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/authmenu/**")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().authorizeRequests().antMatchers("/authmenu/**")
                //设置 authmenu的请求必须带有oauth2的scope信息
                .access("#oauth2.hasScope('read,write')");


//        http
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/logout").permitAll()
//                .antMatchers("/authmenu/scope/user/**").access("#oauth2.hasScope('user')")
//                .antMatchers("/authmenu/scope/config/**").access("#oauth2.hasScope('config')");
    }
}
