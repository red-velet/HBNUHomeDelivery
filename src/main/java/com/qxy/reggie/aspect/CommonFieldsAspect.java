package com.qxy.reggie.aspect;

import com.qxy.reggie.handler.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @Author: SayHello
 * @Date: 2023/3/25 19:24
 * @Introduction: 通过切面方式，自定义注解，实现实体基础数据的注入（创建者、创建时间、修改者、修改时间）
 */
@Slf4j
@Aspect
@Component
public class CommonFieldsAspect {
    @Before("@annotation(com.qxy.reggie.annotation.CommonFields)")
    public void beforeEvent(JoinPoint point) {
        try {
            //TODO 自动注入基础属性（创建者、创建时间、修改者、修改时间）
            //获取添加了注解的方法的参数
            Object[] args = point.getArgs();
            //获取当前登录用户id
            Long employeeId = BaseContextHandler.getUserId();
            for (Object arg : args) {
                //获取参数类型
                Class<?> clazz = arg.getClass();
                //判断当前添加注解的方法的参数内有没有getId方法
                //从而判断需要添加创建人、创建时间、修改人、修改时间
                Object id = null;
                Method method = getMethod(clazz, "getId");
                if (method != null) {
                    //有getId方法
                    //有就调用方法获取id
                    id = method.invoke(arg);
                }

                //todo 通过区分用户传入的Employee参数内是否有id，区分是修改操作还是添加操作
                if (id == null) {
                    //添加操作
                    method = getMethod(clazz, "setCreateUser", Long.class);
                    if (method != null) {
                        method.invoke(arg, employeeId);
                    }
                    method = getMethod(clazz, "setCreateTime", LocalDateTime.class);
                    if (method != null) {
                        //method.invoke(arg, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        method.invoke(arg, LocalDateTime.now());
                    }
                }
                method = getMethod(clazz, "setUpdateUser", Long.class);
                if (method != null) {
                    //修改操作
                    method.invoke(arg, employeeId);
                }
                method = getMethod(clazz, "setUpdateTime", LocalDateTime.class);
                if (method != null) {
                    method.invoke(arg, LocalDateTime.now());

                    //method.invoke(arg, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得方法对象
     *
     * @param classes
     * @param name    方法名
     * @param types   参数类型
     * @return
     */
    private Method getMethod(Class classes, String name, Class... types) {
        try {
            return classes.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}

