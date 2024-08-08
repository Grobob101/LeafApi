package com.api.springbootinit.common;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 手拿键盘一路火花
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}