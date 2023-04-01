package com.qxy.homedelivery.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxy.homedelivery.entity.Dish;
import com.qxy.homedelivery.vo.DishVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: SayHello
 * @Date: 2023/3/26 11:46
 * @Introduction:
 */
public interface DishMapper extends BaseMapper<Dish> {
    DishVO getDishWithFlavorsById(@Param("id") Long dishId);
}
