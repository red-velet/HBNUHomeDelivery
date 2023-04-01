package com.qxy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxy.reggie.dto.SetMealDTO;
import com.qxy.reggie.entity.SetMeal;
import com.qxy.reggie.vo.SetMealVO;

import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:10
 * @Introduction:
 */
public interface SetMealService extends IService<SetMeal> {


    /**
     * 同时保存套餐信息和菜品
     *
     * @param setMealDTO
     */
    void saveWithDishes(SetMealDTO setMealDTO);

    /**
     * 提供套餐id查询套餐信息和关联的菜品信息
     *
     * @param setMealId
     * @return
     */
    SetMealVO getSetMealWithDishesById(Long setMealId);

    /**
     * 修改套餐信息
     *
     * @param setMealDTO
     */
    void updateWithDishes(SetMealDTO setMealDTO);

    /**
     * 批量停售套餐
     *
     * @param status
     * @param ids
     */
    void disableBatchByIds(Integer status, List<Long> ids);

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    void deleteBatchByIds(List<Long> ids);
}
