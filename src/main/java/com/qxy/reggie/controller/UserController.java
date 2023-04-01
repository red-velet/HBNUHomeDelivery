package com.qxy.reggie.controller;

import com.qxy.reggie.common.R;
import com.qxy.reggie.dto.UserDTO;
import com.qxy.reggie.entity.User;
import com.qxy.reggie.handler.BaseContextHandler;
import com.qxy.reggie.service.CommonService;
import com.qxy.reggie.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 20:30
 * @Introduction:
 */
@RestController
@RequestMapping("user")
@Slf4j
@Api(tags = "用户接口")
public class UserController {
    @Autowired
    CommonService commonService;

    @Autowired
    UserService userService;


    @PostMapping("sendMsg")
    @ApiOperation("发送验证码")
    @ApiImplicitParam(name = "user", value = "前端发送json格式手机号,后端使用对象接收手机号码")
    public R<String> sendMsg(@RequestBody User user) {
        log.info("接收到参数: {}", user);
        try {
            commonService.sendMsg(user);
            return R.success("短信验证码发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("短信验证码发送失败");
        }
    }

    @PostMapping("login")
    @ApiOperation("用户登录")
    @ApiImplicitParam(name = "userDTO", value = "登录信息")
    public R<User> login(@RequestBody UserDTO userDTO) {
        log.info("接收到参数: {}", userDTO);
        User user = userService.login(userDTO);
        return R.success(user);
    }

    @PostMapping("loginout")
    @ApiOperation("用户登出")
    public R<String> loginout() {
        Long userId = BaseContextHandler.getUserId();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.getSession().removeAttribute("user");
        return R.success("退出登录成功");
    }
}
