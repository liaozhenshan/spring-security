package com.lzs.springsecurity.checker;

import cn.hutool.core.util.ObjectUtil;
import com.lzs.springsecurity.constant.SystemConstants;
import com.lzs.springsecurity.domain.SysUserDetails;
import com.lzs.springsecurity.util.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/28 14:48
 */
@Component("el")
public class CustomPermissionChecker {

    public boolean check(String permission) {
        SysUserDetails userDetails = SecurityUtils.getCurrentUser();
        if (ObjectUtil.isNull(userDetails)){
            return false;
        }
        //如果是管理员放行
        if (userDetails.getRoles()!=null
                &&userDetails.getRoles().contains(SystemConstants.ADMIN_ROLE_CODE)){
            return true;
        }
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(permission));
    }
}
