package com.lzs.springsecurity.config;

import cn.hutool.core.convert.Convert;
import com.lzs.springsecurity.handler.AllUrlHandler;
import com.lzs.springsecurity.security.*;
import com.lzs.springsecurity.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/19 1:59
 */
@Configuration
@EnableMethodSecurity//开启方法权限保护
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    private final MyLogoutSuccessHandler myLogoutSuccessHandler;
    private final MyAccessDeniedHandler myAccessDeniedHandler;
    private final MySessionInformationExpiredStrategy mySessionInformationExpiredStrategy;
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    private final AllUrlHandler allUrlHandler;
    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //开启授权保护
                .authorizeHttpRequests(
                        authorize -> {
                            if (!allUrlHandler.getUrls().isEmpty()) {
                                //放行注解允许匿名访问的接口
                                authorize.requestMatchers(Convert.toStrArray(allUrlHandler.getUrls())).permitAll();
                            }
                            if (!securityProperties.getExcludes().isEmpty()) {
                                //将securityProperties的excludes配置添加到白名单中
                                authorize.requestMatchers(Convert.toStrArray(securityProperties.getExcludes())).permitAll();
                            }
                            //所有请求开启授权保护
                            authorize .anyRequest().authenticated();
                        }
                );
        http.formLogin(form ->
                form
                        .loginPage("/login").permitAll() //登录页面无需授权即可访问
                        .usernameParameter("username") //自定义表单用户名参数，默认是username
                        .passwordParameter("password") //自定义表单密码参数，默认是password
                        .failureUrl("/login?error") //登录失败的返回地址
                        .successHandler(myAuthenticationSuccessHandler) //认证成功时的处理
                        .failureHandler(myAuthenticationFailureHandler) //认证失败时的处理
        ); //使用表单授权方式

        //http.httpBasic(withDefaults());//使用基本授权方式  浏览器会自带登录框

        http.logout(logout -> {
            logout.logoutSuccessHandler(myLogoutSuccessHandler); //注销成功时的处理
        });

        //错误处理
        http.exceptionHandling(exception -> {
            exception
                    .authenticationEntryPoint(myAuthenticationEntryPoint)//请求未认证的接口
                    .accessDeniedHandler(myAccessDeniedHandler);//请求未授权认证的接口

        });

        /*http.sessionManagement(session ->
                session
                        .maximumSessions(1) //最大会话数
                        .expiredSessionStrategy(mySessionInformationExpiredStrategy)
        );*/
        //不创建会话
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //跨域
        http.cors(withDefaults());

        //关闭csrf攻击防御，提交post请求的时候要求带csrf
        http.csrf(csrf -> csrf.disable());

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 不走过滤器链的放行配置
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                AntPathRequestMatcher.antMatcher("/webjars/**"),
                AntPathRequestMatcher.antMatcher("/doc.html"),
                AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
                AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
                AntPathRequestMatcher.antMatcher("/swagger-ui/**")
        );
    }

}
