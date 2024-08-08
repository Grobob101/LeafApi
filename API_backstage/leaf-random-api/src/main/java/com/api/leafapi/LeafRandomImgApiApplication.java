package com.api.leafapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.api.leafapi.generator.mapper")
public class LeafRandomImgApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeafRandomImgApiApplication.class, args);
    }

}
