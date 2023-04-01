package com.qxy.reggie.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:23
 * @Introduction: 封装-统一结果返回类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressWarnings("all")
public class R<T> implements Serializable {
    /**
     * 响应编码: 1成功，0和其它数字为失败
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 动态数据
     */
    private Map map = new HashMap();


    public R(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    /**
     * 成功的结果
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> R<T> success(T object) {
        R<T> result = new R<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    /**
     * 失败的结果
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> R<T> error(String msg) {
        R result = new R();
        result.msg = msg;
        result.code = 0;
        return result;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
