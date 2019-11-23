package com.yyl.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/22 11:04
 * @Version: 1.0
 */
public class PasswordUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123"));
    }
}
