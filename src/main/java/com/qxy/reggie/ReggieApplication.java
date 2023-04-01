package com.qxy.reggie;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 22:43
 * @Introduction: 项目启动类
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.qxy.reggie.dao")
@EnableTransactionManagement
public class ReggieApplication {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ReggieApplication.class, args);
        Environment env = applicationContext.getEnvironment();
        try {
            log.info("\n❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️\n\t" +
                            "🥤应用 【{}】 启动成功! 访问连接:\n\t" +
                            "➡️项目后台: http://{}:{}/backend/page/login/login.html\n\t" +
                            "➡️项目移动端: http://{}:{}/front/page/login.html\n\t" +
                            "➡️项目API文档: http://{}:{}/doc.html\n\t" +
                            "🥤后台默认登录: 【用户名:{}】| 【密码:{}】\n\t" +
                            "🥤项目启动总耗时: {}秒\n\t" +
                            "🥤备注: 移动端需要浏览器按F12,进行移动端切换 \n" +
                            "❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️❤️",
                    env.getProperty("spring.application.name"),
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port"),
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port"),
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port"),
                    env.getProperty("constant.username"),
                    env.getProperty("constant.password"),
                    (System.currentTimeMillis() - start) / 1000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
