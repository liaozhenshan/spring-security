package com.lzs.springsecurity.security;

import com.lzs.springsecurity.security.properties.JwtProperties;
import com.lzs.springsecurity.security.properties.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/27 15:32
 */
@Configuration
public class PropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "security")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }
}
