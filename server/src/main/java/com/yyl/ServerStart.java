package com.yyl;

import com.yyl.annotation.MyBatisRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/13 21:09
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan(value="com.yyl.dao",annotationClass = MyBatisRepository.class)
public class ServerStart {
    public static void main(String[] args) {
        SpringApplication.run(ServerStart.class,args);
    }
}
