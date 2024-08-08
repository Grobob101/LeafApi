package com.api.leafapi.generator.service;

import com.api.leafapi.generator.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;


public interface UserService extends IService<User> {
     User getUser(HttpServletRequest request);
}
