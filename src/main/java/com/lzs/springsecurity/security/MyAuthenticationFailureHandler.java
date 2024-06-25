package com.lzs.springsecurity.security;

import com.alibaba.fastjson2.JSON;
import com.lzs.springsecurity.constant.HttpStatus;
import com.lzs.springsecurity.domain.R;
import com.lzs.springsecurity.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/19 15:40
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){
        //获取错误信息
        String localizedMessage = exception.getLocalizedMessage();
        //当前用户登录失败
        ResponseUtils.renderJson(response, R.fail(HttpStatus.UNAUTHORIZED, localizedMessage));
    }
}
