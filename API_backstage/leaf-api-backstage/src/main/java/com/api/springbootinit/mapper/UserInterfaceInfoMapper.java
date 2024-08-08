package com.api.springbootinit.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.LeafApiCommon.model.UserInterfaceInfo;

import java.util.List;


public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




