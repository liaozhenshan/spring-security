package com.lzs.springsecurity.security;

import com.lzs.springsecurity.constant.HttpStatus;
import com.lzs.springsecurity.domain.R;
import com.lzs.springsecurity.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/26 11:09
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException){
        //当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
        ResponseUtils.renderJson(response, R.fail(HttpStatus.FORBIDDEN,"鉴权失败，无法访问系统资源"));
    }
}
