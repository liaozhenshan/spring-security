package com.lzs.springsecurity.security.properties;

import lombok.Data;

import java.util.List;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/27 17:25
 */
@Data
public class SecurityProperties {

    private List<String> excludes;
}
