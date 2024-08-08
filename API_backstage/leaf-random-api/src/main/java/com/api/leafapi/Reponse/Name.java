package com.api.leafapi.Reponse;

import lombok.Data;

/**
 * @Author: yewei
 * @CreateTime: 2024-07-13
 * @Description:
 * @Version: 1.0
 */

@Data
public class Name {

    public  String name;

    public Name(String name){
        this.name = name;

    }
}
