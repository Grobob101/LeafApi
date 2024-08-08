package com.leaf.LeafApiCommon.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户调用接口关系
 * @author 手拿键盘一路火花
 */
@TableName(value ="user_interface_info")
@Data
public class UserInterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    public Long id;

    /**
     * 调用用户 id
     */
    public Long userId;

    /**
     * 接口 id
     */
    public Long interfaceInfoId;

    /**
     * 总调用次数
     */
    public Integer totalNum;

    /**
     * 剩余调用次数
     */
    public Integer leftNum;

    /**
     * 0-正常，1-禁用
     */
    public Integer status;

    /**
     * 创建时间
     */
    public Date createTime;

    /**
     * 更新时间
     */
    public Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    public Integer isDelete;

    @TableField(exist = false)
    public static final long serialVersionUID = 1L;
}