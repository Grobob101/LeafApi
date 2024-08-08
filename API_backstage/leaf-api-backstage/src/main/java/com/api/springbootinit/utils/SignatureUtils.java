package com.api.springbootinit.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;




public class SignatureUtils {
    public static String getSign(String body,String secretKey){
        Digester digester = new Digester(DigestAlgorithm.MD5);
        String content=body+"."+secretKey;
        return digester.digestHex(content);
    }
}
