package com.qxy.reggie.vo;

import com.qxy.reggie.entity.Dish;
import com.qxy.reggie.entity.DishFlavor;
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
 * @Date: 2023/3/26 22:12
 * @Introduction:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("返回前端-返回前端菜品参数")
public class DishVO extends Dish {
    private static final long serialVersionUID = 4229150512535772004L;
    @ApiModelProperty("口味")
    private List<DishFlavor> flavors = new ArrayList<>();

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("复制")
    private Integer copies;
}
