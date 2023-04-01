package com.qxy.reggie.dto;

import com.qxy.reggie.entity.SetMeal;
import com.qxy.reggie.entity.SetMealDish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/26 21:23
 * @Introduction:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("接收前端提交参数-套餐参数")
public class SetMealDTO extends SetMeal {
    private static final long serialVersionUID = -7508046499786664074L;

    @ApiModelProperty("菜品")
    private List<SetMealDish> setmealDishes = new ArrayList<>();

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("复制")
    private Integer copies;
}
