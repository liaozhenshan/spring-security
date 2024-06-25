package com.lzs.springsecurity.security;

import com.lzs.springsecurity.constant.CacheConstants;
import com.lzs.springsecurity.domain.R;
import com.lzs.springsecurity.domain.SysUserDetails;
import com.lzs.springsecurity.security.properties.JwtProperties;
import com.lzs.springsecurity.util.JwtUtils;
import com.lzs.springsecurity.util.RedisUtils;
import com.lzs.springsecurity.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/19 15:34
 */
@RequiredArgsConstructor
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProperties jwtProperties;
    private final JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        //获取用户身份信息
        SysUserDetails userDto = (SysUserDetails) authentication.getPrincipal();
        //生成token
        String token = jwtUtils.createToken(userDto.getUsername());

        //将数据保存至redis
        RedisUtils.setCacheObject(CacheConstants.ONLINE_TOKEN_KEY+token,userDto, Duration.ofSeconds(jwtProperties.getExpiration()));
        //封装响应数据
        Map<String, Object> result = new HashMap<>();
        result.put("user", userDto);
        result.put("token", token);
        //当前用户登录失败
        ResponseUtils.renderJson(response, R.ok("登录成功", result));
    }
}
