package com.yyl.config;

import com.yyl.bean.MyUser;
import com.yyl.bean.SysUser;
import com.yyl.dao.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/15 10:04
 * @Version: 1.0
 */
@Component
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserMapper2 userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.findUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authority = new ArrayList<>();
        MyUser myUser = new MyUser(user.getUsername(),user.getPassword(),authority);
        myUser.setId(user.getId());
        myUser.setPhone(user.getPhone());
        return myUser;
    }
}
