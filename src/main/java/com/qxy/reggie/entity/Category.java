package com.qxy.reggie.entity;

import com.qxy.reggie.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 菜品及套餐分类(Category)实体类
 *
 * @author makejava
 * @since 2023-03-18 23:08:44
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@ApiModel("实体类-分类信息实体")
public class Category extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1989848274681768519L;

    @ApiModelProperty("类型: 1-菜品分类 2-套餐分类")
    private Integer type;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("顺序")
    private Integer sort;
}

