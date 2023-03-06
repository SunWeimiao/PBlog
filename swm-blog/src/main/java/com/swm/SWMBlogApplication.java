package com.swm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.swm.mapper")
@EnableScheduling
public class SWMBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SWMBlogApplication.class,args);
    }
}
