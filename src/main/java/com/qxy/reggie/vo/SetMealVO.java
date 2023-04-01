package com.qxy.reggie.vo;

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
 * @Date: 2023/3/28 15:37
 * @Introduction:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("返回前端-返回前端套餐参数")
public class SetMealVO extends SetMeal {
    private static final long serialVersionUID = 3475229547787315037L;
    @ApiModelProperty("菜品")
    private List<SetMealDish> setmealDishes = new ArrayList<>();

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("复制")
    private Integer copies;
}
