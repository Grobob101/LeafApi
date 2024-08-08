package com.api.springbootinit.service.impl;
import com.api.springbootinit.common.ErrorCode;
import com.api.springbootinit.exception.BusinessException;
import com.api.springbootinit.mapper.InterfaceInfoMapper;
import com.api.springbootinit.mapper.UserInterfaceInfoMapper;
import com.api.springbootinit.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.LeafApiCommon.model.InterfaceInfo;
import com.leaf.LeafApiCommon.model.User;
import com.leaf.LeafApiCommon.model.UserInterfaceInfo;
import com.leaf.LeafApiCommon.model.UserVO;
import com.leaf.LeafApiCommon.service.UserInterfaceInfoService;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;



@DubboService
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {
     @Resource
     InterfaceInfoMapper interfaceInfoMapper;
     @Resource
     UserMapper userMapper;
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId){
        // 判断用户id和接口id是否合法
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.gt("leftNum", 0);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }

    @Override
    public InterfaceInfo getInterfaceInfoByUrl(String url){
        if(url == null || url.length() == 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        InterfaceInfo interfaceInfo=interfaceInfoMapper.selectOne(queryWrapper);

        if(interfaceInfo==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return interfaceInfo;
    }
    @Override
    public UserVO getUserVOByAccessKey(String accessKey){
        if(accessKey == null || accessKey.length() == 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey",accessKey);
        User user=userMapper.selectOne(queryWrapper);

        if(user==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public User getUserByAccessKey(String accessKey) {
        if(accessKey == null || accessKey.length() == 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey",accessKey);
        User user=userMapper.selectOne(queryWrapper);

        if(user==null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return user;
    }

    @Override
    public void test() {
        System.out.println("hellow,world!!!");
    }

}




