package com.leaf.LeafApiCommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.LeafApiCommon.model.InterfaceInfo;
import com.leaf.LeafApiCommon.model.User;
import com.leaf.LeafApiCommon.model.UserInterfaceInfo;
import com.leaf.LeafApiCommon.model.UserVO;



/**
* @author ZhuanZ（无密码）
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-07-18 08:52:30
*/


public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    boolean invokeCount(long interfaceInfoId, long userId);
    InterfaceInfo getInterfaceInfoByUrl(String url);
    UserVO getUserVOByAccessKey(String accessKey);
    User getUserByAccessKey(String accessKey);
    void test();
}
