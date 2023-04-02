package com.qxy.homedelivery.aspect;

import com.qxy.homedelivery.constants.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author: SayHello
 * @Date: 2023/4/1 21:03
 * @Introduction: 声明切面全部清除redis菜品缓存
 */
@Slf4j
@Aspect
@Component
public class ClearDishCacheAspect {
    @Autowired
    RedisTemplate redisTemplate;

    //@AfterReturning(pointcut = "@annotation(com.qxy.homedelivery.annotation.ClearDishCache)")
    //public void afterEvent(JoinPoint joinPoint) {
    //    //TODO 方式1 全部清除菜品缓存
    //    Set keys = redisTemplate.keys(RedisConstant.PREFIX_DISH + "*");
    //    redisTemplate.delete(keys);
    //    log.info("joinPoint: {}", joinPoint);
    //}

    @AfterReturning(value = "savePointCut() || updatePointCut() || deletePointCut() || disablePointCut()")
    public void afterEvent() {
        //TODO 方式2 全部清除菜品缓存
        Set keys = redisTemplate.keys(RedisConstant.PREFIX_DISH + "*");
        redisTemplate.delete(keys);
    }

    @Pointcut("execution(* com.qxy.homedelivery.service.impl.DishServiceImpl.save*(..))")
    public void savePointCut() {

    }

    @Pointcut("execution(* com.qxy.homedelivery.service.impl.DishServiceImpl.update*(..))")
    public void updatePointCut() {

    }

    @Pointcut("execution(* com.qxy.homedelivery.service.impl.DishServiceImpl.disable*(..))")
    public void disablePointCut() {

    }

    @Pointcut("execution(* com.qxy.homedelivery.service.impl.DishServiceImpl.delete*(..))")
    public void deletePointCut() {

    }

    //@AfterReturning("" +
    //        "execution(* com.qxy.homedelivery.service.impl.DishServiceImpl.save*(..)) || " +
    //        "execution(* com.qxy.homedelivery.service.impl.DishServiceImpl.update*(..)) ||" +
    //        "execution(* com.qxy.homedelivery.service.impl.DishServiceImpl.delete*(..)) ||" +
    //        "execution(* com.qxy.homedelivery.service.impl.DishServiceImpl.disable*(..))")
    //public void afterEvent2(JoinPoint joinPoint) {
    //    //TODO 方式2 全部清除菜品缓存
    //    Set keys = redisTemplate.keys(RedisConstant.PREFIX_DISH + "*");
    //    redisTemplate.delete(keys);
    //    log.info("joinPoint: {}", joinPoint);
    //}
}
