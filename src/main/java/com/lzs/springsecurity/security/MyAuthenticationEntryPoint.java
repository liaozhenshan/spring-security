package com.lzs.springsecurity.security;

import com.lzs.springsecurity.constant.HttpStatus;
import com.lzs.springsecurity.domain.R;
import com.lzs.springsecurity.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/19 15:53
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException){
        //当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401响应
        ResponseUtils.renderJson(response, R.fail(HttpStatus.UNAUTHORIZED, "您长时间无操作，身份认证已过期，请重新登录"));
    }
}
