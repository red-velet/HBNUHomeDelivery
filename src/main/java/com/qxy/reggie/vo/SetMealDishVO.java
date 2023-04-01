package com.qxy.reggie.vo;

import com.qxy.reggie.entity.SetMealDish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: SayHello
 * @Date: 2023/3/29 20:22
 * @Introduction:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("返回前端-返回前端套餐包含的菜品参数")
public class SetMealDishVO extends SetMealDish {
    private static final long serialVersionUID = -507269631842423037L;
    @ApiModelProperty("菜品图片")
    private String image;
}
