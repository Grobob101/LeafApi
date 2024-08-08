package com.leaf.LeafApiCommon.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @author 手拿键盘一路火花
 */



@TableName(value ="user")
@Data

public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    public Long id;

    /**
     * 用户昵称
     */
    @TableField(value = "userName")
    public String userName;

    /**
     * 账号
     */
    @TableField(value = "userAccount")
    public String userAccount;





    /**
     * 用户角色：user / admin
     */
    @TableField("userRole")
    public String userRole;

    /**
     * 密码
     */
    @TableField("userPassword")
    public String userPassword;

    /**
     * accessKey
     */
    @TableField("accessKey")
    public String accessKey;

    /**
     * secretKey
     */
    @TableField("secretKey")
    public String secretKey;

    /**
     * 创建时间
     */
    @TableField("createTime")
    public Date createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    public Date updateTime;

    /**
     * 是否删除
     */

    @TableLogic

    public Integer isDelete;

    @TableField(exist = false)
    public static final long serialVersionUID = 1L;


}