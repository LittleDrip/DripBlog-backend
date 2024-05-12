package com.drip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//扫描mapper
@MapperScan("com.drip.mapper")
@SpringBootApplication
public class DripBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(DripBlogApplication.class,args);
    }
}