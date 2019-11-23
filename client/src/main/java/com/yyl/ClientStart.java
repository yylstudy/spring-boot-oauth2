package com.yyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/13 21:08
 * @Version: 1.0
 */
@SpringBootApplication
public class ClientStart {
    public static void main(String[] args) {
        SpringApplication.run(ClientStart.class,args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
