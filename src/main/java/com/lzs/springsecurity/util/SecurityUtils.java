package com.lzs.springsecurity.util;

import com.lzs.springsecurity.constant.SystemConstants;
import com.lzs.springsecurity.domain.SysUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/28 14:48
 */
public class SecurityUtils {

    public static Integer getUserId() {
        SysUserDetails userDetails = getCurrentUser();
        return userDetails.getUser().getId();
    }

    public static String getCurrentUsername() {
        SysUserDetails userDetails = getCurrentUser();
        return userDetails.getUsername();
    }

    public static boolean isAdmin() {
        SysUserDetails userDetails = getCurrentUser();
        Set<String> roles = userDetails.getRoles();
        return roles != null && roles.contains(SystemConstants.ADMIN_ROLE_CODE);
    }

    public static SysUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        SysUserDetails userDto = (SysUserDetails) authentication.getPrincipal();
        return userDto;
    }
}
