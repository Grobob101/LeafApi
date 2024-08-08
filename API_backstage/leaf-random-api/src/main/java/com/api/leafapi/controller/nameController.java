package com.api.leafapi.controller;

import com.api.leafapi.Reponse.Name;
import com.api.leafapi.common.ErrorCode;
import com.api.leafapi.excption.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @Author: yewei
 * @CreateTime: 2024-07-13
 * @Description:
 * @Version: 1.0
 */

@RestController
@RequestMapping("/player")
@Slf4j
public class nameController {

    @GetMapping("/niko")
    public Name getRandomName(HttpServletRequest request) {
        //如果请求参数为空就抛出参数异常
        Random r = new Random();
        int i = r.nextInt(10000);
        String name = "小黑子"+String.valueOf(i);
        Name niko=new Name(name);
        return niko;
    }







}
