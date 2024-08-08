package com.api.springbootinit.common;

// https://space.bilibili.com/12890453/

import lombok.Data;

import java.io.Serializable;

/**
 * @author 手拿键盘一路火花
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}