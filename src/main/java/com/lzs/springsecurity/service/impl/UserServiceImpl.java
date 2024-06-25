package com.lzs.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzs.springsecurity.domain.User;
import com.lzs.springsecurity.service.UserService;
import com.lzs.springsecurity.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author lzs
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-05-19 12:02:27
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




