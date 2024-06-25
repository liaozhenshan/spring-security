package com.lzs.springsecurity.security;

import cn.hutool.core.util.ObjectUtil;
import com.lzs.springsecurity.constant.CacheConstants;
import com.lzs.springsecurity.domain.SysUserDetails;
import com.lzs.springsecurity.security.properties.JwtProperties;
import com.lzs.springsecurity.util.JwtUtils;
import com.lzs.springsecurity.util.RedisUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/27 14:57
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(jwtProperties.getHeader());
        String token = null;
        String username = null;

        if (header != null && header.startsWith(jwtProperties.getPrefix())) {
            token = header.substring(7);
            username = jwtUtils.getUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SysUserDetails userDto = RedisUtils.getCacheObject(CacheConstants.ONLINE_TOKEN_KEY + token);
            if (ObjectUtil.isNotNull(userDto)){
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDto, token, userDto.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 获取当前剩余的过期时间
                Long expireTime = RedisUtils.getTimeToLive(CacheConstants.ONLINE_TOKEN_KEY + token);
                if (expireTime != null && expireTime < jwtProperties.getRenewal()) {
                    // 仅当剩余时间小于续期时间时才续期
                    RedisUtils.expire(CacheConstants.ONLINE_TOKEN_KEY + token, Duration.ofSeconds(jwtProperties.getExpiration()));
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
