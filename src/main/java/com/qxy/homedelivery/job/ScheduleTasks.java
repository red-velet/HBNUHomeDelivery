package com.qxy.homedelivery.job;

import com.qxy.homedelivery.constants.RedisConstant;
import com.qxy.homedelivery.utils.QiNiuCloudUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;
import java.util.Set;

/**
 * @Author: SayHello
 * @Date: 2023/4/2 9:52
 * @Introduction: 开启定时任务清空七牛云内无效图片
 */
@Slf4j
@Configuration//1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling// 2.开启定时任务
public class ScheduleTasks {
    @Autowired
    RedisTemplate redisTemplate;

    @Scheduled(cron = "0/50 * * * * ?")
    public void clearTmpImage() {
        Set<String> difference = redisTemplate.opsForSet().difference(RedisConstant.PREFIX_IMG_TMP, RedisConstant.PREFIX_IMG_DB);
        if (Objects.nonNull(difference)) {
            log.info("检查到垃圾图片，垃圾图片集合:> {}", difference);
            for (String fileName : difference) {
                //七牛云删除垃圾图片
                QiNiuCloudUtil.deleteFileFrom2QiNiu(fileName);
                log.info("删除七牛云图片.....");
                redisTemplate.delete(RedisConstant.PREFIX_IMG_DB);
                redisTemplate.delete(RedisConstant.PREFIX_IMG_TMP);
                log.info("删除redis图片名.....");
            }
        }
        //redisTemplate.delete(RedisConstant.PREFIX_IMG_DB);
        //redisTemplate.delete(RedisConstant.PREFIX_IMG_TMP);
        //log.info("删除redis图片名.....");
    }
}

