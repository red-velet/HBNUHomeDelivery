package com.qxy.reggie.entity;

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
 * 菜品管理(Dish)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("实体类-菜品实体")
public class Dish extends BaseEntity {
    private static final long serialVersionUID = -2871336990225432143L;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品分类id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    @ApiModelProperty("菜品价格")
    private Double price;

    @ApiModelProperty("商品码")
    private String code;

    @ApiModelProperty("菜品图片")
    private String image;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("销售状态: 0-停售 1-起售")
    private Integer status;

    @ApiModelProperty("顺序")
    private Integer sort;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;
}

