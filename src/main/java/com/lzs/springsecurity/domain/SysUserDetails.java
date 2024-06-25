package com.lzs.springsecurity.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/28 13:49
 */
@Data
public class SysUserDetails implements UserDetails {

    private User user;

    /**
     * 扩展
     */
    private Map<String,Object> attributes;

    /**
     * 角色集合
     */
    private Set<String> roles;

    /**
     * 权限
     */
    private Collection<GrantedAuthority> authorities;


    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getEnabled();
    }
}
