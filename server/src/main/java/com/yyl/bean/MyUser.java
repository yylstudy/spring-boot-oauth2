package com.yyl.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/22 16:53
 * @Version: 1.0
 */
@Getter
@Setter
public class MyUser extends User {
    private String id;
    private String phone;
    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
