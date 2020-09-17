package com.yyl.controller;

import com.yyl.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/20 17:17
 * @Version: 1.0
 */
@RestController
public class ResourceController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DataSource dataSource;
    @PostConstruct
    public void test1(){
        System.out.println(userMapper.findAllUser());;
    }

    /**
     * 受oauth2保护的资源，如果不带正确的token，会提示
     * <oauth>
     * <error_description>Full authentication is required to access this resource</error_description>
     * <error>unauthorized</error>
     * </oauth>
     * @return
     */
    @RequestMapping("/authmenu/getUsers")
    public Object getUser(){
        return userMapper.findAllUser();
    }

    /**
     * 受oauth2保护的资源，如果不带正确的token，会提示
     * <oauth>
     * <error_description>Full authentication is required to access this resource</error_description>
     * <error>unauthorized</error>
     * </oauth>
     * @return
     */
    @RequestMapping("/authmenu/getDataSource")
    public Object getDataSource(){
        return dataSource;
    }

    /**
     * 不是oauth2保护的资源，即使带上正确的token，也无法访问，因为在ResourceServerConfiguration中
     * 只配置了authmenu的请求是受保护的资源，又因为server本身使用了spring security，所以访问该请求
     * 会跳转到登录界面
     * @return
     */
    @RequestMapping("/noauthmenu/getUsers")
    public Object getUserForNoAuth(){
        return userMapper.findAllUser();
    }
}
