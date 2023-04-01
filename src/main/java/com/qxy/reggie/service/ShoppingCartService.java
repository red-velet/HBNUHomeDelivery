package com.qxy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxy.reggie.entity.ShoppingCart;

/**
 * @Author: SayHello
 * @Date: 2023/3/29 20:39
 * @Introduction:
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    ShoppingCart addShoppingCart(ShoppingCart shoppingCart);

    /**
     * 减少用户下单到购物车数据
     *
     * @param shoppingCart
     */
    void subShoppingCart(ShoppingCart shoppingCart);
}
