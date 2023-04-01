package com.qxy.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.reggie.dao.ShoppingCartMapper;
import com.qxy.reggie.entity.ShoppingCart;
import com.qxy.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: SayHello
 * @Date: 2023/3/29 20:40
 * @Introduction:
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCart addShoppingCart(ShoppingCart shoppingCart) {
        //todo 判断是添加菜品还是添加套餐
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        if (Objects.nonNull(dishId)) {
            wrapper.eq(ShoppingCart::getDishId, dishId);
        }
        if (Objects.nonNull(setmealId)) {
            wrapper.eq(ShoppingCart::getSetmealId, setmealId);
        }
        //todo 判断该数据是否已存在
        ShoppingCart shoppingCartEntity = shoppingCartMapper.selectOne(wrapper);
        if (Objects.nonNull(shoppingCartEntity)) {
            shoppingCartEntity.setNumber(shoppingCartEntity.getNumber() + 1);
            shoppingCartMapper.updateById(shoppingCartEntity);
        } else {
            shoppingCart.setNumber(1);
            shoppingCartMapper.insert(shoppingCart);
            shoppingCartEntity = shoppingCart;
        }
        return shoppingCartEntity;
    }

    @Override
    public void subShoppingCart(ShoppingCart shoppingCart) {
        //TODO 减少用户下单到购物车的数据
        //todo 检查数量,为0就删除,不为0就减1
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
        if (Objects.nonNull(dishId)) {
            wrapper.eq(ShoppingCart::getDishId, dishId);
        }
        if (Objects.nonNull(setmealId)) {
            wrapper.eq(ShoppingCart::getSetmealId, setmealId);
        }
        ShoppingCart shoppingCartEntity = shoppingCartMapper.selectOne(wrapper);
        Integer number = shoppingCartEntity.getNumber();
        if (number <= 1) {
            //删除操作
            shoppingCartMapper.deleteById(shoppingCartEntity.getId());
        }
        //修改操作
        shoppingCartEntity.setNumber(shoppingCartEntity.getNumber() - 1);
        shoppingCartMapper.updateById(shoppingCartEntity);
    }
}
