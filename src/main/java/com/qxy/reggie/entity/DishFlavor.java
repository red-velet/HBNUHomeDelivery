package com.qxy.reggie.entity;

import com.qxy.reggie.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 菜品口味关系表(DishFlavor)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@ApiModel("实体类-菜品口味实体")
public class DishFlavor extends BaseEntity {
    private static final long serialVersionUID = -3626488089219234167L;

    @ApiModelProperty("菜品ID")
    private Long dishId;

    @ApiModelProperty("口味名称:辣度")
    private String name;

    @ApiModelProperty("口味数据列表:不辣、中辣、重辣")
    private String value;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;
}

