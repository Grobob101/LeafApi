package com.api.springbootinit.service.impl;

import com.api.springbootinit.common.ErrorCode;
import com.api.springbootinit.exception.BusinessException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.leaf.LeafApiCommon.model.InterfaceInfo;
import com.leaf.LeafApiCommon.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import com.api.springbootinit.mapper.InterfaceInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }


}




