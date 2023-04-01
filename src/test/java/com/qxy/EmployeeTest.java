package com.qxy;

import com.qxy.reggie.ReggieApplication;
import com.qxy.reggie.dao.EmployeeMapper;
import com.qxy.reggie.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:11
 * @Introduction:
 */
@SpringBootTest(classes = ReggieApplication.class)
@RunWith(SpringRunner.class)
public class EmployeeTest {
    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void testEmployeeMapper() {
        Employee employee = employeeMapper.selectById(1);
        System.out.println("employee = " + employee);
    }
}
