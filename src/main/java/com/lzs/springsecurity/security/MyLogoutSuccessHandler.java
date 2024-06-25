package com.lzs.springsecurity.security;

import com.alibaba.fastjson2.JSON;
import com.lzs.springsecurity.domain.R;
import com.lzs.springsecurity.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/19 15:47
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        //当前用户注销
        ResponseUtils.renderJson(response, R.ok("注销成功"));
    }
}
