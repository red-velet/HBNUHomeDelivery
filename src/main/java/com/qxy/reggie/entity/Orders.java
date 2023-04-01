package com.qxy.reggie.entity;

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
 * 订单表(Orders)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("实体类-订单实体")
public class Orders implements Serializable {
    private static final long serialVersionUID = -9171612670253029081L;

    @ApiModelProperty("主键id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("订单号")
    private String number;

    @ApiModelProperty("订单状态 1待付款，2待派送，3已派送，4已完成，5已取消")
    private Integer status;

    @ApiModelProperty("下单用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty("地址id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long addressBookId;

    @ApiModelProperty("下单时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime orderTime;

    @ApiModelProperty("结账时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime checkoutTime;

    @ApiModelProperty("支付方式 1微信,2支付宝")
    private Integer payMethod;

    @ApiModelProperty("实收金额")
    private Double amount;

    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("电话号码")
    private String phone;
    @ApiModelProperty("收货地址")
    private String address;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("收货人姓名")
    private String consignee;


}

