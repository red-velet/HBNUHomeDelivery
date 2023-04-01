package com.qxy.homedelivery.service.impl;


import com.qxy.homedelivery.constants.RedisConstant;
import com.qxy.homedelivery.entity.User;
import com.qxy.homedelivery.service.CommonService;
import com.qxy.homedelivery.utils.ValidateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 21:37
 * @Introduction:
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void sendMsg(User user) {
        //TODO 发送验证码
        //1.检验手机号是否为空
        String phone = user.getPhone();
        if (StringUtils.isEmpty(phone)) {
            throw new RuntimeException("手机号为空");
        }

        //2.判断该手机号是否已经发送过验证码,5分钟内不重复发送
        Object cacheValidateCode = redisTemplate.opsForValue().get(RedisConstant.PREFIX_VALIDATE_CODE + phone);
        if (Objects.nonNull(cacheValidateCode)) {
            log.info("使用redis缓存中的验证码validateCode:{}", cacheValidateCode);
            return;
        }
        //2.生成验证码
        String validateCode = ValidateCodeUtil.generateValidateCode(4).toString();


        //3.调用短信通道商接口发送短信
        //SMSUtil.sendMessage(SMSConstant.SIGN_NAME, SMSConstant.TEMPLATE_CODE, user.getPhone(), validateCode);
        log.info("发送了验证码validateCode:{}", validateCode);

        ////4.存储短信到session中
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //request.getSession().setAttribute(phone, validateCode);
        //4.存储短信到redis中,有效期5分钟
        redisTemplate.opsForValue().set(RedisConstant.PREFIX_VALIDATE_CODE + phone, validateCode, RedisConstant.VALIDATE_CODE_TIME, TimeUnit.MINUTES);
    }

    @Override
    public void clearDishRedisCache(String key) {
        //TODO 清空redis菜品缓存
        redisTemplate.delete(key);
        log.info("菜品数据发送变化:清除缓存 - {}", key);
    }
}
