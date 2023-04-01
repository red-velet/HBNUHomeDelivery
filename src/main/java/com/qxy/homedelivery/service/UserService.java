package com.qxy.homedelivery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxy.homedelivery.dto.UserDTO;
import com.qxy.homedelivery.entity.User;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 21:51
 * @Introduction:
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param userDTO
     */
    User login(UserDTO userDTO);

}
