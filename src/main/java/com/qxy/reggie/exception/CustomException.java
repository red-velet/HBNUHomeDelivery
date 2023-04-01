package com.qxy.reggie.exception;

import java.io.Serializable;

/**
 * @Author: SayHello
 * @Date: 2023/3/26 13:07
 * @Introduction: 自定义业务异常
 */
public class CustomException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 6746866836997357394L;

    public CustomException(String message) {
        super(message);
    }
}
