package com.qxy.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.reggie.dao.EmployeeMapper;
import com.qxy.reggie.entity.Employee;
import com.qxy.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:19
 * @Introduction:
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public Employee login(String password, String username) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee employeeDetail = employeeMapper.selectOne(queryWrapper);
        if (!(employeeDetail != null && password.equals(employeeDetail.getPassword()))) {
            throw new RuntimeException("用户不存在/密码错误");
        }
        if (employeeDetail.getStatus() == 0) {
            throw new RuntimeException("该用户已被禁用");
        }
        return employeeDetail;
    }
}
