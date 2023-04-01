package com.qxy.reggie.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 20:23
 * @Introduction: 短信发送工具类
 */
@Component
@Slf4j
public class SMSUtil {
    public static String ASSESS_KEY;
    public static String SECRET;

    /**
     * 发送短信
     *
     * @param signName     签名
     * @param templateCode 模板
     * @param phoneNumbers 手机号
     * @param param        参数
     */
    public static void sendMessage(String signName, String templateCode, String phoneNumbers, String param) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ASSESS_KEY, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setPhoneNumbers(phoneNumbers);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + param + "\"}");
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            String message = response.getMessage();
            String code = response.getCode();
            String bizId = response.getBizId();
            String requestId = response.getRequestId();
            log.info("请求id: {}", requestId);
            log.info("状态码: {}", code);
            log.info("状态码描述: {}", message);
            log.info("发送回执ID: {}", bizId);
            System.out.println("短信发送成功");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Value("${SMSCloud.AssessKey}")
    public void setAssessKey(String assessKey) {
        ASSESS_KEY = assessKey;
    }

    @Value("${SMSCloud.Secret}")
    public void setSecret(String secret) {
        SMSUtil.SECRET = secret;
    }
}
