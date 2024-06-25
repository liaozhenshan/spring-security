package com.lzs.springsecurity.util;

import cn.hutool.core.util.IdUtil;
import com.lzs.springsecurity.security.properties.JwtProperties;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/27 14:42
 */
@Component
@RequiredArgsConstructor
public class JwtUtils implements InitializingBean {

    private final JwtProperties jwtProperties;
    public static final String AUTHORITIES_KEY = "user";
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    /**
     * 生成永久token，过期时间交给redis管理
     * @param username
     * @return
     */
    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400L * 1000);
        return jwtBuilder
                //加入ID确保生成的Token都不一致
                .setId(IdUtil.simpleUUID())
                .claim(AUTHORITIES_KEY, username)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)  // 设置一天过期时间 并不影响系统token有效期，因为交由redis管理
                .compact();
    }

    public String getUsername(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, key);
    }
}
