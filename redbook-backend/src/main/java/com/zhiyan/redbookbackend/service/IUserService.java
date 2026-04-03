package com.zhiyan.redbookbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhiyan.redbookbackend.dto.LoginFormDTO;
import com.zhiyan.redbookbackend.dto.Result;
import com.zhiyan.redbookbackend.entity.User;
import jakarta.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface IUserService extends IService<User> {

    Result sendCode(String phone, HttpSession session);

    Result login(LoginFormDTO loginForm, HttpSession session);

    Result sign();

    Result signCount();
}
