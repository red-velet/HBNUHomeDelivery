package com.qxy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxy.reggie.annotation.CommonFields;
import com.qxy.reggie.common.R;
import com.qxy.reggie.constants.Constant;
import com.qxy.reggie.constants.OperateConstant;
import com.qxy.reggie.entity.Employee;
import com.qxy.reggie.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:21
 * @Introduction:
 */
@Slf4j
@RestController
@RequestMapping("employee")
@Api(tags = "员工管理")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 该方法用于员工登录
     *
     * @param request  HttpRequest请求
     * @param employee 用户输入的用户名和密码
     * @return Result
     */
    @PostMapping("login")
    @ApiOperation("员工登录接口")
    @ApiImplicitParam(name = "employee", value = "员工登录填写的信息(用户名、密码)", required = true)
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //TODO 用户登录逻辑
        try {
            String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
            Employee employeeDetail = employeeService.login(password, employee.getUsername());
            log.info("登陆成功");
            //登录成功,将员工id存入session并返回登录结果
            request.getSession().setAttribute("employee", employeeDetail.getId());
            return R.success(employeeDetail);
        } catch (Exception e) {
            //error打印日志
            log.error("登录失败: {}", e.getMessage());
            e.printStackTrace();
            return R.error("登陆失败" + e.getMessage());
        }
    }

    /**
     * 该方法用于员工下线
     *
     * @param request HttpRequest请求
     * @return Result
     */
    @ApiOperation("员工登出接口")
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request) {
        //TODO 用户退出逻辑
        try {
            //清除存入session中的员工id
            request.getSession().removeAttribute("employee");
            return R.success("退出登录成功");
        } catch (Exception e) {
            //error打印日志
            log.error("退出登录失败: {}", e.getMessage());
            e.printStackTrace();
            return R.error("退出登录失败" + e.getMessage());
        }
    }

    /**
     * 添加用户
     *
     * @return
     */
    @PostMapping
    @CommonFields
    @ApiOperation("新增员工接口")
    @ApiImplicitParam(name = "employee", value = "员工信息", required = true)
    public R<String> save(@RequestBody Employee employee) {
        //TODO 新增员工
        employee.setPassword(DigestUtils.md5DigestAsHex(Constant.DEFAULT_PASSWORD.getBytes()));
        employeeService.save(employee);
        log.info(OperateConstant.ADD_EMPLOYEE_SUCCESS);
        return R.success(OperateConstant.ADD_EMPLOYEE_SUCCESS);
    }

    /**
     * 分页显示
     *
     * @return
     */
    @GetMapping("page")
    @ApiOperation("分页条件查询员工接口")
    @ApiImplicitParam(name = "map", value = "分页信息和条件查询信息", required = false)
    public R<Page> page(HttpServletRequest request, @RequestParam Map<String, String> map) {
        //TODO 分页查询员工信息
        try {
            int currPage = map.get("page") == null ? 1 : Integer.parseInt(map.get("page"));
            int pageSize = map.get("page") == null ? 16 : Integer.parseInt(map.get("pageSize"));
            Page<Employee> page = new Page<>(currPage, pageSize);
            LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
            Long employeeId = (Long) request.getSession(false).getAttribute("employee");
            if (employeeId != 1) {
                wrapper.ne(Employee::getId, 1);
            }
            if (StringUtils.isNotEmpty(map.get("name"))) {
                wrapper
                        .likeRight(Employee::getName, map.get("name"))
                        .orderByAsc(Employee::getUpdateTime);
                employeeService.page(page, wrapper);
            } else {
                employeeService.page(page, wrapper);
            }
            return R.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("error");
        }
    }

    @PutMapping
    @CommonFields
    @ApiOperation("更新员工接口")
    @ApiImplicitParam(name = "employee", value = "员工信息", required = true)
    public R<String> update(@RequestBody Employee employee) {
        //TODO 修改用户基本信息、状态
        try {
            employeeService.updateById(employee);
            log.info(OperateConstant.UPDATE_EMPLOYEE_SUCCESS);
            return R.success(OperateConstant.UPDATE_EMPLOYEE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(OperateConstant.UPDATE_EMPLOYEE_FAIL);
        }
    }

    @GetMapping("{id}")
    @CommonFields
    @ApiOperation("根据员工id获取员工信息接口")
    @ApiImplicitParam(name = "id", value = "员工id", required = true)
    public R<Employee> getById(@PathVariable Long id) {
        //TODO 修改用户基本信息、状态
        try {
            Employee employee = employeeService.getById(id);
            if (employee != null) {
                log.info("数据回显成功...");
                return R.success(employee);
            } else {
                return R.error(OperateConstant.GET_EMPLOYEE_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(OperateConstant.GET_EMPLOYEE_FAIL);
        }
    }
}
