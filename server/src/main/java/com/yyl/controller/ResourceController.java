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

    @RequestMapping("/authmenu/getUsers")
    public Object getUser(){
        return userMapper.findAllUser();
    }

    @RequestMapping("/authmenu/getDataSource")
    public Object getDataSource(){
        return dataSource;
    }

    @RequestMapping("/noauthmenu/getUsers")
    public Object getUserForNoAuth(){
        return userMapper.findAllUser();
    }
}
