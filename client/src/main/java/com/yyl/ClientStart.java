package com.yyl;

import com.yyl.annotation.MyBatisRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/13 21:08
 * @Version: 1.0
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan(value="com.yyl.dao",annotationClass = MyBatisRepository.class)
public class ClientStart {
    public static void main(String[] args) {
        SpringApplication.run(ClientStart.class,args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
