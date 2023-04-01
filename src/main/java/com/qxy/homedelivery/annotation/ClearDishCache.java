package com.qxy.homedelivery.annotation;

import java.lang.annotation.*;

/**
 * @Author: SayHello
 * @Date: 2023/4/1 21:02
 * @Introduction: AOP统一清除缓存
 */
@Documented //javadoc
@Retention(RetentionPolicy.RUNTIME) //哪里起作用
@Target(ElementType.METHOD) //放在哪些地方
public @interface ClearDishCache {
}
