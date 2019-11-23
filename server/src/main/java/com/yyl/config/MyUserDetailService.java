package com.yyl.config;

import com.yyl.bean.MyUser;
import com.yyl.bean.SysResource;
import com.yyl.bean.SysUser;
import com.yyl.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/15 10:04
 * @Version: 1.0
 */
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.findUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authority = user.getRoles().stream().flatMap(role->role.getResources().stream()
                .map(SysResource::getCode)).distinct()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        MyUser myUser = new MyUser(user.getUsername(),user.getPassword(),authority);
        myUser.setId(user.getId());
        myUser.setPhone(user.getPhone());
        return myUser;
    }
}
