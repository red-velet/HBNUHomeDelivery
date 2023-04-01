package com.qxy.homedelivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
<<<<<<< HEAD
=======
import com.qxy.homedelivery.constants.RedisConstant;
>>>>>>> v1.0
import com.qxy.homedelivery.dao.UserMapper;
import com.qxy.homedelivery.dto.UserDTO;
import com.qxy.homedelivery.entity.User;
import com.qxy.homedelivery.exception.CustomException;
import com.qxy.homedelivery.service.UserService;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
>>>>>>> v1.0
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 21:52
 * @Introduction:
 */
@Service
<<<<<<< HEAD
=======
@Slf4j
>>>>>>> v1.0
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

<<<<<<< HEAD
    @Override
    public User login(UserDTO userDTO) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //TODO 用户登录
        //1.比对用户输入验证码和系统发送验证码
        String code = userDTO.getCode();
        String phone = userDTO.getPhone();
        String validateCode = request.getSession().getAttribute(phone).toString();
        if (!(validateCode != null && validateCode.equals(code))) {
=======
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public User login(UserDTO userDTO) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ////TODO 用户登录
        ////1.比对用户输入验证码和系统发送验证码
        //String code = userDTO.getCode();
        //String phone = userDTO.getPhone();
        //String validateCode = request.getSession().getAttribute(phone).toString();
        //if (!(validateCode != null && validateCode.equals(code))) {
        //    throw new CustomException("用户登录失败:验证码错误");
        //}
        ////2.判断手机号是否未新用户,不是则注册
        //LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        //wrapper.eq(User::getPhone, phone);
        //User user = userMapper.selectOne(wrapper);
        //if (Objects.isNull(user)) {
        //    user = new User();
        //    user.setPhone(phone);
        //    user.setStatus(1);
        //    userMapper.insert(user);
        //}
        ////3.登录成功,保存登录状态
        //request.getSession().setAttribute("user", user.getId());
        //return user;

        //TODO 用户登录v1.0
        //1.比对用户输入验证码和redis缓存中发送验证码
        String code = userDTO.getCode();
        String phone = userDTO.getPhone();
        if (Objects.isNull(phone)) {
            throw new CustomException("用户登录失败:手机号为空");
        }
        String cacheValidateCode = (String) redisTemplate.opsForValue().get(RedisConstant.PREFIX_VALIDATE_CODE + phone);
        if (Objects.isNull(cacheValidateCode)) {
            throw new CustomException("用户登录失败:验证码已过期");
        }
        if (!cacheValidateCode.equals(code)) {
>>>>>>> v1.0
            throw new CustomException("用户登录失败:验证码错误");
        }
        //2.判断手机号是否未新用户,不是则注册
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        User user = userMapper.selectOne(wrapper);
        if (Objects.isNull(user)) {
            user = new User();
            user.setPhone(phone);
            user.setStatus(1);
            userMapper.insert(user);
        }
<<<<<<< HEAD
        //3.登录成功,保存登录状态
=======
        //3.登录成功,清除redis的验证码,保存登录状态
        redisTemplate.delete(RedisConstant.PREFIX_VALIDATE_CODE + phone);
        log.info("登录成功,redis缓存中的验证码已被清除");
        //redisTemplate.opsForValue().set(RedisConstant.PREFIX_USER + phone, user.getId());
        //HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //Cookie cookie = new Cookie("token", user.getId().toString());
        //resp.addCookie(cookie);
>>>>>>> v1.0
        request.getSession().setAttribute("user", user.getId());
        return user;
    }
}
