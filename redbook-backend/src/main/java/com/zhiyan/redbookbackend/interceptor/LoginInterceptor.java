package com.zhiyan.redbookbackend.interceptor;

import com.zhiyan.redbookbackend.util.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否需要拦截(ThreadLocal中是否有用户)
        if (UserHolder.getUser() == null) {
            // 不存在，拦截，返回401状态码
            response.setStatus(401);
            return false;
        }
        // 存在，放行
        return true;
    }
}
