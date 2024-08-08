package com.api.springbootinit;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;



/**
 * 主类（项目启动入口）
 *

 * @author 手拿键盘一路火花
*/

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@MapperScan("com.api.springbootinit.mapper")
@EnableDubbo
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
