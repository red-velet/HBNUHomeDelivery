package com.qxy.reggie.vo;

import com.qxy.reggie.entity.OrderDetail;
import com.qxy.reggie.entity.Orders;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/31 8:36
 * @Introduction:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("返回前端-返回前端订单参数")
public class OrdersVO extends Orders {
    private static final long serialVersionUID = -1568201033382824272L;
    @ApiModelProperty("订单明细列表")
    List<OrderDetail> orderDetails;
}
