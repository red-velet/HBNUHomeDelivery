package com.qxy.reggie.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.qxy.reggie.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 套餐菜品关系(SetmealDish)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("实体类-套餐菜品关联表-套餐包含的菜品实体")
@TableName("setmeal_dish")
public class SetMealDish extends BaseEntity {
    private static final long serialVersionUID = 189652031234322708L;
    @ApiModelProperty("套餐id")
    @JsonSerialize(using = StringSerializer.class)
    private String setmealId;

    @ApiModelProperty("菜品id")
    private String dishId;

    @ApiModelProperty("菜品名称 （冗余字段）")
    private String name;

    @ApiModelProperty("菜品原价（冗余字段）")
    private Double price;

    @ApiModelProperty("份数")
    private Integer copies;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;
}

