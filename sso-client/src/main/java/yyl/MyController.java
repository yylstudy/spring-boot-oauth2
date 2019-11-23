package yyl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/19 20:30
 * @Version: 1.0
 */
@RestController
@Slf4j
public class MyController {

    @RequestMapping("/getPrincipal")
    public Authentication getPrincipal(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    @PreAuthorize("hasAuthority('user:test')")
    @RequestMapping("/test1")
    public String test1(){
        return "test1";
    }

    @PreAuthorize("hasAuthority('user:add')")
    @RequestMapping("/addUser")
    public String addUser(){
        return "add user success";
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
