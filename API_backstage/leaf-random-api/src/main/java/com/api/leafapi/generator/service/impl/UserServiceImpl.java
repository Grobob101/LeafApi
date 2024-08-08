package com.api.leafapi.generator.service.impl;

import com.api.leafapi.common.ErrorCode;
import com.api.leafapi.excption.BusinessException;
import com.api.leafapi.generator.mapper.UserMapper;
import com.api.leafapi.generator.model.User;
import com.api.leafapi.generator.model.UserVO;
import com.api.leafapi.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import static com.api.leafapi.constant.UserConstant.USER_LOGIN_STATE;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public User getUser(HttpServletRequest request){
        UserVO userVO =(UserVO) request.getSession().getAttribute(USER_LOGIN_STATE);
        if(userVO == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        long id = userVO.getId();
        User user =getById(id);
        if(user == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return user;
    }

}




