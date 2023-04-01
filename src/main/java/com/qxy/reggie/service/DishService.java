package com.qxy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxy.reggie.dto.DishDTO;
import com.qxy.reggie.entity.Dish;
import com.qxy.reggie.vo.DishVO;

import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:10
 * @Introduction:
 */
public interface DishService extends IService<Dish> {

    /**
     * 同时保存dish和dishflavor
     *
     * @param dishDTO
     */
    void saveWithFlavors(DishDTO dishDTO);

    /**
     * 通过id查询菜品信息
     *
     * @param dishId
     * @return
     */
    DishVO getDishWithFlavorsById(Long dishId);

    /**
     * 同时修改dish和dishflavor
     *
     * @param dishDTO
     */
    void updateWithFlavors(DishDTO dishDTO);

    /**
     * 批量停售菜品
     *
     * @param status
     * @param ids
     */
    void disableBatchByIds(Integer status, List<Long> ids);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void deleteBatchByIds(List<Long> ids);
}
