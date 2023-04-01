package com.qxy.reggie.service.impl;

import com.qxy.reggie.entity.User;
import com.qxy.reggie.service.CommonService;
import com.qxy.reggie.utils.ValidateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 21:37
 * @Introduction:
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public void sendMsg(User user) {
        //TODO 发送验证码
        //1.检验手机号是否为空
        String phone = user.getPhone();
        if (StringUtils.isEmpty(phone)) {
            throw new RuntimeException("手机号为空");
        }
        //2.生成验证码
        String validateCode = ValidateCodeUtil.generateValidateCode(4).toString();

        //3.调用短信通道商接口发送短信
        //SMSUtil.sendMessage(SMSConstant.SIGN_NAME, SMSConstant.TEMPLATE_CODE, user.getPhone(), validateCode);
        log.info("validateCode:{}", validateCode);

        //4.存储短信到session中
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.getSession().setAttribute(phone, validateCode);
    }
}
