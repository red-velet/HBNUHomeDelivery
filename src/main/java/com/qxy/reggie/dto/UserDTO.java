package com.qxy.reggie.dto;

import com.qxy.reggie.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 21:47
 * @Introduction:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("接收前端提交参数-登录参数")
public class UserDTO extends User {
    private static final long serialVersionUID = 6769176778053002787L;
    @ApiModelProperty("验证码")
    private String code;
}
