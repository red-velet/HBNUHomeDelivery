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
 * 订单明细表(OrderDetail)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("实体类-订单明细实体")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = -361403289260622204L;

    @ApiModelProperty("主键id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("订单id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    @ApiModelProperty("菜品id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dishId;

    @ApiModelProperty("套餐id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long setmealId;

    @ApiModelProperty("口味")
    private String dishFlavor;

    @ApiModelProperty("数量")
    private Integer number;

    @ApiModelProperty("金额")
    private Double amount;
}

