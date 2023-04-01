package com.qxy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxy.reggie.entity.Employee;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:10
 * @Introduction:
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * 员工登录
     *
     * @param password
     * @param username
     * @return
     */
    Employee login(String password, String username);
}
