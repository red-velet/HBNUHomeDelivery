package com.qxy.reggie.handler;

/**
 * @Author: SayHello
 * @Date: 2023/3/25 23:30
 * @Introduction: ThreadLocal保存用户id
 */
public class BaseContextHandler {
    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserId(Long id) {
        THREAD_LOCAL.set(id);
    }

    public static Long getUserId() {
        return THREAD_LOCAL.get();
    }
}
