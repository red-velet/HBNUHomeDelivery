package com.qxy.reggie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: SayHello
 * @Date: 2023/3/31 9:29
 * @Introduction:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("接收前端提交参数-分页条件查询订单参数")
public class OrderPageDTO implements Serializable {
    private static final long serialVersionUID = -447645250711432034L;
    @ApiModelProperty("起始页:当前页码,从1开始")
    private Integer page;

    @ApiModelProperty("每页显示记录数")
    private Integer pageSize;

    @ApiModelProperty("订单号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long number;
    @ApiModelProperty("起始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beginTime;
    @ApiModelProperty("截止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
}
