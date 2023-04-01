package com.qxy.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.reggie.constants.OperateConstant;
import com.qxy.reggie.dao.CategoryMapper;
import com.qxy.reggie.entity.Category;
import com.qxy.reggie.entity.Dish;
import com.qxy.reggie.entity.SetMeal;
import com.qxy.reggie.exception.CustomException;
import com.qxy.reggie.service.CategoryService;
import com.qxy.reggie.service.DishService;
import com.qxy.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:19
 * @Introduction:
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    DishService dishService;

    @Autowired
    SetMealService setMealService;

    @Override
    public void checkIsExist(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Category::getName, category.getName())
                .eq(Category::getType, category.getType());
        Integer selectOne = categoryMapper.selectCount(wrapper);
        if (selectOne > 0) {
            throw new RuntimeException(OperateConstant.ADD_CATEGORY_FAIL + ":" + "该分类已存在");
        }
    }

    @Override
    public void removeCategoryById(Long id) {
        //TODO 检查该分类是否已经关联菜品/套餐
        //1、检查菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        Integer count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException(OperateConstant.DELETE_CATEGORY_FAIL + "该套餐已关联菜品");
        }
        //2、检查套餐
        LambdaQueryWrapper<SetMeal> setMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setMealLambdaQueryWrapper.eq(SetMeal::getCategoryId, id);
        count = setMealService.count(setMealLambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException(OperateConstant.DELETE_CATEGORY_FAIL + "该套餐已关联套餐");
        }

        //删除
        categoryMapper.deleteById(id);
        //super.removeById(id);
    }
}
