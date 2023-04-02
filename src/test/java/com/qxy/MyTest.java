package com.qxy;

import com.qxy.homedelivery.HomeDeliveryApplication;
import com.qxy.homedelivery.constants.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @Author: SayHello
 * @Date: 2023/4/1 23:15
 * @Introduction:
 */
@Slf4j
@SpringBootTest(classes = HomeDeliveryApplication.class)
@RunWith(value = SpringRunner.class)
public class MyTest {
    @Autowired
    RedisTemplate redisTemplate;


    @Test
    public void testJob() {
        Set<String> difference = redisTemplate.opsForSet().difference(RedisConstant.PREFIX_IMG_TMP, RedisConstant.PREFIX_IMG_DB);
        log.info("test垃圾图片集合:> {}", difference);
    }
}
