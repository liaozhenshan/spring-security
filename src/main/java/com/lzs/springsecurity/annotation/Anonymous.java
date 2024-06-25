package com.lzs.springsecurity.annotation;

import java.lang.annotation.*;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/28 15:32
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
