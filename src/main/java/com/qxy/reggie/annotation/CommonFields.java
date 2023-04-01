package com.qxy.reggie.annotation;

import java.lang.annotation.*;

/**
 * @Author: SayHello
 * @Date: 2023/3/25 19:24
 * @Introduction: 自定义主键-AOP统一添加添加数据、修改时间....
 */
@Documented //javadoc
@Retention(RetentionPolicy.RUNTIME) //哪里起作用
@Target(ElementType.METHOD) //放在哪些地方
public @interface CommonFields {

}
