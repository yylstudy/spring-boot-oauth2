package com.yyl.dao;


import com.yyl.annotation.MyBatisRepository;
import com.yyl.bean.SysUser;

import java.util.List;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/15 10:15
 * @Version: 1.0
 */
@MyBatisRepository
public interface UserMapper2 {
    SysUser findUserByUsername(String username);
    List<SysUser> findAllUser();
    int saveOrUpdate(SysUser sysUser);
}
