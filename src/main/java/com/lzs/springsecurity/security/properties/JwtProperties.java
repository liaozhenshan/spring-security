package com.lzs.springsecurity.security.properties;

import lombok.Data;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/27 11:43
 */
@Data
public class JwtProperties {

    private String header;
    private String prefix;
    private long expiration;
    private long renewal;
    private String secret;

}
