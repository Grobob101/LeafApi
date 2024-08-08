package com.leaf.LeafApiCommon.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户视图（脱敏）
 *

 * @author 手拿键盘一路火花
*/
@Data
public class UserVO implements Serializable {

    private Long id;
    private String userName;
    private String userAccount;
    private String userRole;
    private Date createTime;

    private static final long serialVersionUID = 1L;
}