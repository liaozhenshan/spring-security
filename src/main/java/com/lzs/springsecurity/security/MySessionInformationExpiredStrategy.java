package com.lzs.springsecurity.security;

import com.alibaba.fastjson2.JSON;
import com.lzs.springsecurity.constant.HttpStatus;
import com.lzs.springsecurity.domain.R;
import com.lzs.springsecurity.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/19 16:18
 */
@Component
public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event){
        HttpServletResponse response = event.getResponse();
        //并发会话处理
        ResponseUtils.renderJson(response, R.fail(HttpStatus.UNAUTHORIZED,"该用户已从另一个设备登录，请重新登录"));
    }
}
