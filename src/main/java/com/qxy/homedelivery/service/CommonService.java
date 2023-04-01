package com.qxy.homedelivery.service;

import com.qxy.homedelivery.entity.User;

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
