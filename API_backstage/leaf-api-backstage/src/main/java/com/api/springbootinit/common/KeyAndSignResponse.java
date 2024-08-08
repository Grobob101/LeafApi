package com.api.springbootinit.common;

import lombok.Data;

/**
 * @Author: yewei
 * @CreateTime: 2024-07-18
 * @Description:
 * @Version: 1.0
 */

@Data
public class KeyAndSignResponse {
    private String accessKey;
    private String sign;
}
