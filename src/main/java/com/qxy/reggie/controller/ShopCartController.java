package com.qxy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qxy.reggie.annotation.CommonFields;
import com.qxy.reggie.common.R;
import com.qxy.reggie.entity.ShoppingCart;
import com.qxy.reggie.handler.BaseContextHandler;
import com.qxy.reggie.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/29 20:09
 * @Introduction:
 */
@RestController
@Api(tags = "购物车管理")
@RequestMapping("shoppingCart")
@Slf4j
public class ShopCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("add")
    @CommonFields
    @ApiOperation("新增菜品/套餐到购物车")
    @ApiImplicitParam(name = "shoppingCart", value = "新增菜品/套餐到购物车信息")
    public R<ShoppingCart> save(@RequestBody ShoppingCart shoppingCart) {
        //TODO 添加数据到购物车
        try {
            shoppingCart.setUserId(BaseContextHandler.getUserId());
            ShoppingCart shoppingCartEntity = shoppingCartService.addShoppingCart(shoppingCart);
            return R.success(shoppingCartEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @GetMapping("list")
    @CommonFields
    @ApiOperation("查看购物车")
    public R<List<ShoppingCart>> list() {
        //TODO 查看购物车
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContextHandler.getUserId()).orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(wrapper);
        return R.success(list);
    }

    @PostMapping("sub")
    @CommonFields
    @ApiOperation("减少菜品/套餐到购物车")
    @ApiImplicitParam(name = "shoppingCart", value = "减少菜品/套餐到购物车信息")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart) {
        //TODO 减少数据到购物车
        try {
            shoppingCart.setUserId(BaseContextHandler.getUserId());
            shoppingCartService.subShoppingCart(shoppingCart);
            return R.success("减少商品成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @DeleteMapping("clean")
    @CommonFields
    @ApiOperation("清空购物车")
    public R<String> clean() {
        //TODO 清空购物车
        try {
            LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingCart::getUserId, BaseContextHandler.getUserId());
            shoppingCartService.remove(wrapper);
            return R.success("清空购物车成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
