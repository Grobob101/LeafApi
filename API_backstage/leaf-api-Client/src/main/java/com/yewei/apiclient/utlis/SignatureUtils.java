package com.yewei.apiclient.utlis;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @Author: yewei
 * @CreateTime: 2024-07-12
 * @Description:
 * @Version: 1.0
 */


public class SignatureUtils {
    public static String getSign(String accessKey,String secretKey){
        Digester digester = new Digester(DigestAlgorithm.MD5);
        String content=accessKey+"."+secretKey;
        return digester.digestHex(content);
    }
}
