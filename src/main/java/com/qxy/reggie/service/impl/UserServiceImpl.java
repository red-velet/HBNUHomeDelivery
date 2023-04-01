package com.qxy.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.reggie.dao.UserMapper;
import com.qxy.reggie.dto.UserDTO;
import com.qxy.reggie.entity.User;
import com.qxy.reggie.exception.CustomException;
import com.qxy.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User login(UserDTO userDTO) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //TODO 用户登录
        //1.比对用户输入验证码和系统发送验证码
        String code = userDTO.getCode();
        String phone = userDTO.getPhone();
        String validateCode = request.getSession().getAttribute(phone).toString();
        if (!(validateCode != null && validateCode.equals(code))) {
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
        //3.登录成功,保存登录状态
        request.getSession().setAttribute("user", user.getId());
        return user;
    }
}
