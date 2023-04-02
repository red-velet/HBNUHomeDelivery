package com.qxy.homedelivery.aspect;

import com.qxy.homedelivery.constants.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
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
public class ClearSetMealCacheAspect {
    @Autowired
    RedisTemplate redisTemplate;


    @AfterReturning(
            "execution(* com.qxy.homedelivery.service.impl.SetMealServiceImpl.save*(..)) || " +
                    "execution(* com.qxy.homedelivery.service.impl.SetMealServiceImpl.update*(..)) ||" +
                    "execution(* com.qxy.homedelivery.service.impl.SetMealServiceImpl.delete*(..)) ||" +
                    "execution(* com.qxy.homedelivery.service.impl.SetMealServiceImpl.disable*(..))")
    public void afterEvent() {
        //TODO 方式2 全部清除套餐缓存
        Set keys = redisTemplate.keys(RedisConstant.PREFIX_SETMEAL + "*");
        redisTemplate.delete(keys);
    }
}
