package com.leaf.LeafApiCommon;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data

public class LeafApiCommonConfig {

    public static void main(String[] args) {
        SpringApplication.run(LeafApiCommonConfig.class, args);
    }


}
