package com.leaf.LeafApiGateway;


import com.leaf.LeafApiCommon.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@EnableDubbo
@Service
@SpringBootTest
class LeafApiGetwayApplicationTests {
    @DubboReference
    UserInterfaceInfoService userInterfaceInfo;
    @Test
    void contextLoads() {
        userInterfaceInfo.test();

    }

}
