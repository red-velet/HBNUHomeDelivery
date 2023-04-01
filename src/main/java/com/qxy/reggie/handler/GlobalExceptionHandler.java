package com.qxy.reggie.handler;

import com.qxy.reggie.common.R;
import com.qxy.reggie.constants.Constant;
import com.qxy.reggie.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Author: SayHello
 * @Date: 2023/3/25 20:25
 * @Introduction: 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error("全局异常处理器捕获到异常: {}", ex.getMessage());
        ex.printStackTrace();
        StringBuilder builder = new StringBuilder();
        if (ex.getMessage().contains(Constant.DUPLICATE_ENTRY)) {
            String[] split = ex.getMessage().split(" ");
            String[] split1 = split[2].split("'");
            builder.append("新增失败")
                    .append(":")
                    .append(split1[1])
                    .append("已存在");
            return R.error(builder.toString());
        }
        builder.append("亲! 网络有问题,请您等一会...");
        return R.error(builder.toString());
    }

    @ExceptionHandler(CustomException.class)
    public R<String> customExceptionHandler(CustomException ex) {
        log.error("全局异常处理器捕获到异常: {}", ex.getMessage());
        ex.printStackTrace();
        return R.error(ex.getMessage());
    }
}
