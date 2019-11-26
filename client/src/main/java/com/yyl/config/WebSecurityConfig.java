package com.yyl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/18 10:37
 * @Version: 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailService userDetailsService;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 都可以访问
                .antMatchers("/static/**","/css/**", "/js/**", "/fonts/**", "/img/**",
                        "/oauth/check_token","/redirect","/getProtectedResoureFromOauth2Server",
                        "/redirectAndLogin","/redirectAndLoginForGithub").permitAll()
                // 需要相应的角色才能访问
                //.antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                //基于 Form 表单登录验证
                .formLogin().loginProcessingUrl("/login")
//                .defaultSuccessUrl("/main")//登陆成功之后跳转的页面
                // 自定义登录界面
                .loginPage("/login").failureUrl("/login?error").permitAll()
                // 注销请求可直接访问
                .and().logout().logoutSuccessUrl("/login").permitAll()
                // 处理异常，拒绝访问就重定向到 403 页面
                .and().exceptionHandling().accessDeniedPage("/403");
        // 允许来自同一来源的H2 控制台的请求
        http.headers().frameOptions().sameOrigin();
        http.csrf().disable().authorizeRequests().anyRequest().authenticated();// 所有请求必须登陆后访问
    }
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/static/**","/css/**"
//                //使用oauth2做sso认证的话，并且令牌不是使用jwt，那么需要跳过这个url
//                ,"/oauth/check_token","/redirect","/getProtectedResoureFromOauth2Server"
//                ,"/redirectAndLogin","/redirectAndLoginForGithub");
//    }
}
