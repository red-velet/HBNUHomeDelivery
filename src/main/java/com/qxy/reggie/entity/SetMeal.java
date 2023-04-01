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
 * 套餐(Setmeal)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("实体类-套餐实体")
@TableName("setmeal")
public class SetMeal extends BaseEntity {
    private static final long serialVersionUID = 3067059841572251671L;
    @ApiModelProperty("菜品分类id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("套餐价格")
    private Double price;

    @ApiModelProperty("销售状态: 0-停售 1-起售")
    private Integer status;

    @ApiModelProperty("商品码")
    private String code;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("套餐图片")
    private String image;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;
}

