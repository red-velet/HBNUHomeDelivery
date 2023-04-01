package com.qxy.reggie.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户信息(User)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("实体类-用户实体")
public class User implements Serializable {
    private static final long serialVersionUID = -61787111574874623L;

    @ApiModelProperty("主键id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("身份证号")
    private String idNumber;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("状态 0:禁用，1:正常")
    private Integer status;
}

