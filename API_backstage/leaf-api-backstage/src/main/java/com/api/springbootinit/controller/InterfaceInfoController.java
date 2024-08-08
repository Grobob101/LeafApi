package com.api.springbootinit.controller;
import com.api.springbootinit.annotation.AuthCheck;
import com.api.springbootinit.common.*;
import com.api.springbootinit.constant.CommonConstant;
import com.api.springbootinit.constant.UserConstant;
import com.api.springbootinit.exception.BusinessException;
import com.api.springbootinit.exception.ThrowUtils;
import com.api.springbootinit.model.dto.interfaceinfo.*;
import com.api.springbootinit.model.enums.InterfaceInfoStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leaf.LeafApiCommon.model.InterfaceInfo;
import com.leaf.LeafApiCommon.model.UserVO;
import com.leaf.LeafApiCommon.service.InterfaceInfoService;
import com.leaf.LeafApiCommon.service.UserService;
import com.yewei.apiclient.client.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import java.net.URISyntaxException;
import java.util.List;
/**
 * 接口管理
 *

 * @author 手拿键盘一路火花
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    String TestBaseUrl="http://localhost:8080/";

    @Resource
    private InterfaceInfoService InterfaceInfoService;

    @Resource
    private UserService userService;

    private ApiClient apiClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param InterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest InterfaceInfoAddRequest, HttpServletRequest request) {
        //请求参数为空
        if (InterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoAddRequest, InterfaceInfo);
        System.out.println(InterfaceInfo.toString());

        // 校验参数是否合法
        InterfaceInfoService.validInterfaceInfo(InterfaceInfo,true);

        UserVO loginUser = userService.getLoginUser(request);

        //设置发布接口用户id
        InterfaceInfo.setUserid(loginUser.getId());

        //存入数据库
        boolean result = InterfaceInfoService.save(InterfaceInfo);

        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        //获取新增接口id
        long newInterfaceInfoId = InterfaceInfo.getId();

        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVO user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        //如果不存在就抛出异常，然后交给全局异常处理器处理
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserid().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //判断是否删除成功
        boolean b = InterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param InterfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest InterfaceInfoUpdateRequest) {
        if (InterfaceInfoUpdateRequest == null || InterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoUpdateRequest, InterfaceInfo);

        // 参数校验
        InterfaceInfoService.validInterfaceInfo(InterfaceInfo, false);
        long id = InterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        //如果不存在就抛出异常
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        //如果更新成功就返回true
        boolean result = InterfaceInfoService.updateById(InterfaceInfo);
        return ResultUtils.success(result);
    }

    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        log.info("success");
        //根据id获取接口
        InterfaceInfo interfaceInfo = InterfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();

        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        //建立查询条件
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);

        //返回查询列表
        List<InterfaceInfo> interfaceInfoList = InterfaceInfoService.list(queryWrapper);

        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //新建一个接口类并注入
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);

        //获取当前页号
        long current = interfaceInfoQueryRequest.getCurrent();
        //获取页的大小
        long size = interfaceInfoQueryRequest.getPageSize();

        //获取排序字段
        String sortField = interfaceInfoQueryRequest.getSortField();

        //获取排序规则
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();

        //获取描述
        String description = interfaceInfoQuery.getDescription();

        // description 需支持模糊搜索，为了提升性能将其置为null，获取部分数据后再进行模糊查询
        interfaceInfoQuery.setDescription(null);

        // 防止爬虫大量爬取
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //建立查询条件
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);

        //模糊查询
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);

        //排序
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);

        //传入查询结果并构造page
        Page<InterfaceInfo> interfaceInfoPage = InterfaceInfoService.page(new Page<>(current, size), queryWrapper);

        return ResultUtils.success(interfaceInfoPage);
    }

    // endregion

    /**
     * 发布
     *
     * @param idRequest
     * @param request
     * @return //
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<InterfaceInfo> onlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                           HttpServletRequest request) throws URISyntaxException {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //创建httpclient

        // 仅本人或管理员可修改
        oldInterfaceInfo.setId(id);
        oldInterfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = InterfaceInfoService.updateById(oldInterfaceInfo);
        if(!result){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"更新失败");
        }
        return ResultUtils.success(oldInterfaceInfo);
    }

    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest,
                                                     HttpServletRequest request) throws URISyntaxException {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = idRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 仅本人或管理员可修改
        oldInterfaceInfo.setId(id);
        oldInterfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = InterfaceInfoService.updateById(oldInterfaceInfo);
        return ResultUtils.success(result);
    }



    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,
                                                    HttpServletRequest request) {
        if (interfaceInfoInvokeRequest == null || interfaceInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = interfaceInfoInvokeRequest.getId();

        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = InterfaceInfoService.getById(id);

        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (oldInterfaceInfo.getStatus() == InterfaceInfoStatusEnum.OFFLINE.getValue()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已关闭");
        }

        return ResultUtils.success(null);

    }



}
