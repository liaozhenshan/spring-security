package com.lzs.springsecurity.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lzs.springsecurity.annotation.Anonymous;
import com.lzs.springsecurity.domain.User;
import com.lzs.springsecurity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liaozhenshan
 * @version 1.0
 * @date 2024/5/19 12:03
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
//@Anonymous
@Tag(name = "用户管理", description = "用户管理相关接口")
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    @Operation(summary = "用户列表", description = "用户列表")
//    @PreAuthorize("hasAuthority('user:list')")
//    @PreAuthorize("hasAnyRole('admin') and authentication.name == 'admim' ")
    public List<User> list() {
        return userService.list();
    }

    @Anonymous
    @GetMapping("/test")
//    @PreAuthorize("@el.check('test')")
    public List<User> test() {
        return userService.list();
    }

    @Anonymous
    @PostMapping("/save")
//    @PreAuthorize("@el.check('test')")
    public String save(@RequestBody User user) {
        userService.save(user);
        return "ok";
    }

}
