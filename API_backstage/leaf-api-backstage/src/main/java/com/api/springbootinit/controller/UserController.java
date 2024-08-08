package com.api.springbootinit.controller;

import com.api.springbootinit.common.*;
import com.api.springbootinit.mapper.UserInterfaceInfoMapper;
import com.api.springbootinit.mapper.UserMapper;
import com.api.springbootinit.model.dto.user.UserRegisterRequest;
import com.api.springbootinit.exception.BusinessException;
import com.api.springbootinit.model.dto.user.UserLoginRequest;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leaf.LeafApiCommon.model.User;
import com.leaf.LeafApiCommon.model.UserInterfaceInfo;
import com.leaf.LeafApiCommon.model.UserVO;
import com.leaf.LeafApiCommon.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.api.springbootinit.utils.SignatureUtils.getSign;

/**
 * 用户接口
 *

 * @author 手拿键盘一路火花
*/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;



    @Resource
    private UserMapper userMapper;

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    // region 登录相关

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return //
     */
    @PostMapping("/register")
    public BaseResponse<UserVO> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
       UserVO userVO = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(userVO);
    }


    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 用户登录（微信开放平台）
     */
//    @GetMapping("/login/wx_open")
//    public BaseResponse<LoginUserVO> userLoginByWxOpen(HttpServletRequest request, HttpServletResponse response,
//            @RequestParam("code") String code) {
//        WxOAuth2AccessToken accessToken;
//        try {
//            WxMpService wxService = wxOpenConfig.getWxMpService();
//            accessToken = wxService.getOAuth2Service().getAccessToken(code);
//            WxOAuth2UserInfo userInfo = wxService.getOAuth2Service().getUserInfo(accessToken, code);
//            String unionId = userInfo.getUnionId();
//            String mpOpenId = userInfo.getOpenid();
//            if (StringUtils.isAnyBlank(unionId, mpOpenId)) {
//                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
//            }
//            return ResultUtils.success(userService.userLoginByMpOpen(userInfo, request));
//        } catch (Exception e) {
//            log.error("userLoginByWxOpen error", e);
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，系统错误");
//        }
//    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        UserVO user = userService.getLoginUser(request);
        return ResultUtils.success(user);
    }

    @GetMapping("/get/verify")
    public BaseResponse<KeyAndSignResponse> getVerify(HttpServletRequest request){
        UserVO user =userService.getLoginUser(request);
        if(user==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        long id = user.getId();
        User client=userMapper.selectById(id);
        if(client==null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        KeyAndSignResponse verify=new KeyAndSignResponse();
        verify.setAccessKey(client.getAccessKey());
        verify.setSign(getSign(client.getAccessKey(),client.getSecretKey()));
        return ResultUtils.success(verify);
    }
    @GetMapping("/get/count")
    public BaseResponse<TotalAndLeftRequest> getCount(HttpServletRequest request){
        UserVO user =userService.getLoginUser(request);
        if(user==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        long id = user.getId();
        QueryWrapper<UserInterfaceInfo>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userid",id);
        UserInterfaceInfo userInterfaceInfo=userInterfaceInfoMapper.selectOne(queryWrapper);
        if(userInterfaceInfo==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        TotalAndLeftRequest result=new TotalAndLeftRequest();
        BeanUtils.copyProperties(userInterfaceInfo,result);
        return ResultUtils.success(result);

    }

    // endregion

    // region 增删改查

    /**
     * 创建用户
     *
     * @param userAddRequest
     * @param request
     * @return
     */
//    @PostMapping("/add")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
//        if (userAddRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User user = new User();
//        BeanUtils.copyProperties(userAddRequest, user);
//        boolean result = userService.save(user);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(user.getId());
//    }

    /**
     * 删除用户
     *
     * @param deleteRequest
     * @param request
     * @return
     */
//    @PostMapping("/delete")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
//        if (deleteRequest == null || deleteRequest.getId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        boolean b = userService.removeById(deleteRequest.getId());
//        return ResultUtils.success(b);
//    }

    /**
     * 更新用户
     *
     * @param userUpdateRequest
     * @param request
     * @return
     */
//    @PostMapping("/update")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest,
//            HttpServletRequest request) {
//        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User user = new User();
//        BeanUtils.copyProperties(userUpdateRequest, user);
//        boolean result = userService.updateById(user);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(true);
//    }

    /**
     * 根据 id 获取用户（仅管理员）
     *
     * @param id
     * @param request
     * @return
//     */
//    @GetMapping("/get")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<User> getUserById(long id, HttpServletRequest request) {
//        if (id <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User user = userService.getById(id);
//        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
//        return ResultUtils.success(user);
//    }

    /**
     * 根据 id 获取包装类
     *
     * @param id
     * @param request
     * @return
     */
//    @GetMapping("/get/vo")
//    public BaseResponse<UserVO> getUserVOById(long id, HttpServletRequest request) {
//        BaseResponse<User> response = getUserById(id, request);
//        User user = response.getData();
//        return ResultUtils.success(userService.getUserVO(user));
//    }

    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest
     * @param request
     * @return
     */
//    @PostMapping("/list/page")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
//    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest,
//            HttpServletRequest request) {
//        long current = userQueryRequest.getCurrent();
//        long size = userQueryRequest.getPageSize();
//        Page<User> userPage = userService.page(new Page<>(current, size),
//                userService.getQueryWrapper(userQueryRequest));
//        return ResultUtils.success(userPage);
//    }

    /**
     * 分页获取用户封装列表
     *
     * @param userQueryRequest
     * @param request
     * @return
     */
//    @PostMapping("/list/page/vo")
//    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest,
//            HttpServletRequest request) {
//        if (userQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        long current = userQueryRequest.getCurrent();
//        long size = userQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<User> userPage = userService.page(new Page<>(current, size),
//                userService.getQueryWrapper(userQueryRequest));
//        Page<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
//        List<UserVO> userVO = userService.getUserVO(userPage.getRecords());
//        userVOPage.setRecords(userVO);
//        return ResultUtils.success(userVOPage);
//    }

    // endregion

    /**
     * 更新个人信息
     *
     * @param userUpdateMyRequest
     * @param request
     * @return
//     */
//    @PostMapping("/update/my")
//    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest,
//            HttpServletRequest request) {
//        if (userUpdateMyRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User loginUser = userService.getLoginUser(request);
//        User user = new User();
//        BeanUtils.copyProperties(userUpdateMyRequest, user);
//        user.setId(loginUser.getId());
//        boolean result = userService.updateById(user);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(true);
//    }
}
