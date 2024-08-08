package com.yewei.apiclient.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;


import java.util.HashMap;
import java.util.Map;

import static com.yewei.apiclient.utlis.SignatureUtils.getSign;

/**
 * @Author: yewei
 * @CreateTime: 2024-07-12
 * @Description:
 * @Version: 1.0
 */


public class ApiClient {

    private String accessKey;
    private String secretKey;

    //部署时换成网关地址
    private static final String GATEWAY_HOST ="{网关真实地址}";

    public ApiClient(String accessKey, String secretKey) {
    }

    public String getRandomName(int number){
        String body = generateBody(number);
        String result = null;
        try {
            result = HttpRequest.get(GATEWAY_HOST+"api/player/niko")
                    .headerMap(getHeaderMap(body), true)
                    .timeout(20000)
                    .execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;

    }
    private String generateBody(int number) {

        return "{\"number\":" + number + "}";
    }


    public Map<String,String> getHeaderMap(String body){
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        headerMap.put("tag", RandomUtil.randomNumbers(10));//随机标签，防止重放
        headerMap.put("body",body);
        headerMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));//时间戳，防止重放
        headerMap.put("sign", getSign(accessKey,secretKey));//签名
        return headerMap;
    }
}
