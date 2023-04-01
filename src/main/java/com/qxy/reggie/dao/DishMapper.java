package com.qxy.reggie.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxy.reggie.entity.Dish;
import com.qxy.reggie.vo.DishVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: SayHello
 * @Date: 2023/3/26 11:46
 * @Introduction:
 */
public interface DishMapper extends BaseMapper<Dish> {
    DishVO getDishWithFlavorsById(@Param("id") Long dishId);
}
