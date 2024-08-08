package com.leaf.LeafApiCommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.LeafApiCommon.model.InterfaceInfo;


/**
* @author ZhuanZ（无密码）
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-07-09 13:41:54
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
