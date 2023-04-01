package com.qxy.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.reggie.constants.OperateConstant;
import com.qxy.reggie.dao.DishMapper;
import com.qxy.reggie.dto.DishDTO;
import com.qxy.reggie.entity.Category;
import com.qxy.reggie.entity.Dish;
import com.qxy.reggie.entity.DishFlavor;
import com.qxy.reggie.exception.CustomException;
import com.qxy.reggie.service.CategoryService;
import com.qxy.reggie.service.DishFlavorService;
import com.qxy.reggie.service.DishService;
import com.qxy.reggie.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:19
 * @Introduction:
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishService dishService;

    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    DishMapper dishMapper;

    @Override
    @Transactional
    @SuppressWarnings("all")
    public void saveWithFlavors(DishDTO dishDTO) {
        //检查该菜品是否已存在
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getName, dishDTO.getName()).select(Dish::getName);
        Dish oldDish = dishService.getOne(wrapper);
        if (Objects.nonNull(oldDish)) {
            throw new CustomException(OperateConstant.ADD_DISH_FAIL + ":菜品名已被使用");
        }
        //todo 保存菜品信息
        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setCategoryId(dishDTO.getCategoryId());
        dish.setPrice(dishDTO.getPrice());
        dish.setCode("");
        dish.setImage(dishDTO.getImage());
        dish.setDescription(dishDTO.getDescription());
        dish.setStatus(dishDTO.getStatus());
        dish.setCreateTime(dishDTO.getCreateTime());
        dish.setUpdateTime(dishDTO.getUpdateTime());
        dish.setUpdateUser(dishDTO.getUpdateUser());
        dish.setCreateUser(dishDTO.getCreateUser());
        dishService.save(dish);

        //todo 保存菜品对应的口味信息
        List<DishFlavor> dishFlavors = saveFlavors(dishDTO.getFlavors(), dish);
        dishFlavorService.saveBatch(dishFlavors);
    }

    @Override
    public DishVO getDishWithFlavorsById(Long dishId) {
        DishVO dishWithFlavorsById = dishMapper.getDishWithFlavorsById(dishId);
        Category category = categoryService.getById(dishWithFlavorsById.getCategoryId());
        dishWithFlavorsById.setCategoryName(category.getName());
        return dishWithFlavorsById;
    }

    @Override
    public void updateWithFlavors(DishDTO dishDTO) {
        //todo 1.修改菜品表
        Dish dish = new Dish();
        dish.setId(dishDTO.getId());
        dish.setName(dishDTO.getName());
        dish.setCategoryId(dishDTO.getCategoryId());
        dish.setPrice(dishDTO.getPrice());
        dish.setCode("");
        dish.setImage(dishDTO.getImage());
        dish.setDescription(dishDTO.getDescription());
        dish.setStatus(dishDTO.getStatus());
        dish.setCreateTime(dishDTO.getCreateTime());
        dish.setUpdateTime(dishDTO.getUpdateTime());
        dish.setUpdateUser(dishDTO.getUpdateUser());
        dish.setCreateUser(dishDTO.getCreateUser());
        dishMapper.updateById(dish);

        //todo 2.修改口味表
        //先删除原有的
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dish.getId());
        dishFlavorService.remove(wrapper);

        //再添加
        List<DishFlavor> dishFlavors = saveFlavors(dishDTO.getFlavors(), dish);
        dishFlavorService.saveBatch(dishFlavors);

    }

    @Override
    public void disableBatchByIds(Integer status, List<Long> ids) {
        for (Long id : ids) {
            LambdaUpdateWrapper<Dish> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Dish::getId, id).set(Dish::getStatus, status);
            dishMapper.update(null, wrapper);
        }
    }

    @Override
    public void deleteBatchByIds(List<Long> ids) {
        //todo 删除菜品:在售不能删除,停售才能删除
        //检查菜品是否已经停售
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Dish::getId, ids).eq(Dish::getStatus, 1);
        Integer count = dishMapper.selectCount(wrapper);
        if (count > 0) {
            throw new CustomException(OperateConstant.DELETE_DISH_FAIL + ":菜品正在售出");
        }
        //删除菜品表数据
        dishMapper.deleteBatchIds(ids);
        //删除口味关联表数据
        LambdaQueryWrapper<DishFlavor> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(wrapper1);

    }

    private List<DishFlavor> saveFlavors(List<DishFlavor> flavors, Dish dish) {
        Long dishId = dish.getId();
        for (int i = 0; i < flavors.size(); i++) {
            flavors.get(i).setDishId(dishId);
            flavors.get(i).setCreateTime(dish.getCreateTime());
            flavors.get(i).setUpdateTime(dish.getUpdateTime());
            flavors.get(i).setCreateUser(dish.getCreateUser());
            flavors.get(i).setUpdateUser(dish.getUpdateUser());
        }
        return flavors;
    }
}
