package com.lzs.springsecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzs.springsecurity.domain.User;
import com.lzs.springsecurity.domain.SysUserDetails;
import com.lzs.springsecurity.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/19 13:39
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询数据库用户
        User user = Optional.ofNullable(userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, username)
        )).orElseThrow(() -> {
            throw new UsernameNotFoundException("用户不存在");
        });

        //TODO 查询角色权限
        Set<GrantedAuthority> authorities = new HashSet<>(); //可以将角色和按钮权限都集成
        authorities.add(new SimpleGrantedAuthority("admin"));
        authorities.add(new SimpleGrantedAuthority("user:add"));
        authorities.add(new SimpleGrantedAuthority("user:list"));
        SysUserDetails details = new SysUserDetails();
        //扩展字段
        Map<String, Object> attributes = new HashMap<>();
        details.setRoles(Set.of("admin"));
        details.setUser(user);
        details.setAuthorities(authorities);
        details.setAttributes(attributes);
        return details;

        //这里只能加角色，或者权限，如果加了权限，基于角色的权限并不会加载
        /*return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("ADMIN")
                .authorities(authorities)
                .build();*/
    }
}
