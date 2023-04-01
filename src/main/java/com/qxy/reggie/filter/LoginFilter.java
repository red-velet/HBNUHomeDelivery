package com.qxy.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.qxy.reggie.common.R;
import com.qxy.reggie.constants.Constant;
import com.qxy.reggie.handler.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/25 18:44
 * @Introduction: 检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "checkLogin", urlPatterns = "/*")
public class LoginFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //TODO 过滤器,所有未登录用户不能访问其它页面
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        log.info("拦截到请求,url = {}", httpServletRequest.getRequestURL().toString());
        //1、创建白名单
        List<String> whiteNames = new ArrayList<>(Arrays.asList(
                "/employee/login",
                "/employee/logout",
                "/favicon.ico",
                "/backend/**",
                "/front/**",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs",
                "/user/login",
                "/user/sendMsg"
        ));
        //2、检查此次请求是否存在白名单
        String requestURI = httpServletRequest.getRequestURI();
        int i = 0;
        for (i = 0; i < whiteNames.size(); i++) {
            if (PATH_MATCHER.match(whiteNames.get(i), requestURI)) {
                log.info("该请求【{}】在白名单,放行...", requestURI);
                //在白名单,放行
                chain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }
        if (i == whiteNames.size()) {
            //3、不存在白名单,检查是否登录
            HttpSession session = httpServletRequest.getSession();
            //判断网页端
            if (httpServletRequest.getSession().getAttribute("employee") != null) {
                log.info("该请求已认证登录...");
                Long employee = (Long) httpServletRequest.getSession().getAttribute("employee");
                //添加用户信息到threadlocal
                BaseContextHandler.setUserId(employee);
                //放行
                chain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            if (httpServletRequest.getSession().getAttribute("user") != null) {
                log.info("该请求已认证登录...");
                //添加用户信息到threadlocal
                Long user = (Long) httpServletRequest.getSession().getAttribute("user");
                BaseContextHandler.setUserId(user);
                //放行
                chain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            log.info("用户未登录...");
            httpServletResponse.getWriter().write(JSON.toJSONString(R.error(Constant.NOT_LOGIN)));
        }
    }
}
