package com.qxy.reggie.service;

import com.qxy.reggie.entity.User;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 21:37
 * @Introduction:
 */
public interface CommonService {
    /**
     * 发送短信
     *
     * @param user
     */
    void sendMsg(User user);
}
