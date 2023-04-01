package com.qxy.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 22:49
 * @Introduction: 静态资源配置类-配置resources目录下的静态资源文件
 */
@Slf4j
@Configuration
public class MyWebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 该类添加静态资源映射,因为springboot默认查找的resources/static下的资源,非该目录的资源访问不到,所有添加映射
     *
     * @param registry registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射....");
        registry.addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        log.info("配置进行静态资源映射完成....");
        super.addResourceHandlers(registry);
    }
}
