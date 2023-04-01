package com.qxy.reggie.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qxy.reggie.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 购物车(ShoppingCart)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("实体类-购物车实体")
@TableName("shopping_cart")
public class ShoppingCart extends BaseEntity {
    private static final long serialVersionUID = 2486990586665286369L;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("用户id-外键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty("菜品id-外键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dishId;

    @ApiModelProperty("套餐id-外键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long setmealId;

    @ApiModelProperty("口味")
    private String dishFlavor;

    @ApiModelProperty("数量")
    private Integer number;

    @ApiModelProperty("金额")
    private Double amount;
}

