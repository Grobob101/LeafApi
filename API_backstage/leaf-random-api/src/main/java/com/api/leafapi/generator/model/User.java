package com.api.leafapi.generator.model;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @TableName user
 */



@TableName(value ="user")
@Data

public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 用户昵称
     */
    @TableField(value = "userName")
    private String userName;

    /**
     * 账号
     */
    @TableField(value = "userAccount")
    private String userAccount;





    /**
     * 用户角色：user / admin
     */
    @TableField("userRole")
    private String userRole;

    /**
     * 密码
     */
    @TableField("userPassword")
    private String userPassword;

    /**
     * accessKey
     */
    @TableField("accessKey")
    private String accessKey;

    /**
     * secretKey
     */
    @TableField("secretKey")
    private String secretKey;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 是否删除
     */

    @TableLogic

    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}