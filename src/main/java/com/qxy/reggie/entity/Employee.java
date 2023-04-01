package com.qxy.reggie.entity;

import com.qxy.reggie.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 员工信息(Employee)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@ApiModel("实体类-员工信息实体")
public class Employee extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8389159943177495191L;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("身份证号")
    private String idNumber;

    @ApiModelProperty("状态 0:禁用，1:正常")
    private Integer status;
}

