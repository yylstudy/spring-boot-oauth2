package com.yyl.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/15 10:07
 * @Version: 1.0
 */
@Setter
@Getter
public class SysUser  implements Serializable {
    private String id;
    private String username;
    private String password;
    private List<SysRole> roles;
    private String phone;

}
