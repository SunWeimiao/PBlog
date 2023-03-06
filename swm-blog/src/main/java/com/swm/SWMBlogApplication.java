package com.swm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.swm.mapper")
@EnableScheduling
@EnableSwagger2
public class SWMBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SWMBlogApplication.class,args);
    }
}
