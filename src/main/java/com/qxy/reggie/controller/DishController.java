package com.qxy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxy.reggie.annotation.CommonFields;
import com.qxy.reggie.common.R;
import com.qxy.reggie.constants.OperateConstant;
import com.qxy.reggie.dto.DishDTO;
import com.qxy.reggie.entity.Category;
import com.qxy.reggie.entity.Dish;
import com.qxy.reggie.service.CategoryService;
import com.qxy.reggie.service.DishService;
import com.qxy.reggie.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/26 17:19
 * @Introduction:
 */
@RestController
@Api(tags = "菜品管理")
@RequestMapping("dish")
@Slf4j
public class DishController {
    @Autowired
    DishService dishService;

    @Autowired
    CategoryService categoryService;

    @PostMapping()
    @CommonFields
    @ApiOperation("新增菜品")
    @ApiImplicitParam(name = "dishDTO", value = "菜品信息")
    public R<String> save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品-参数:{}", dishDTO);
        dishService.saveWithFlavors(dishDTO);
        return R.success(OperateConstant.ADD_DISH_SUCCESS);
    }

    @GetMapping("page")
    @ApiOperation("分页显示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码,从1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", required = true),
            @ApiImplicitParam(name = "name", value = "菜品名称", required = false),
    })
    public R<Page<DishVO>> page(@RequestParam("page") Integer curr, @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "name", required = false) String dishName) {
        //TODO 分页查询菜品信息
        Page<Dish> page = new Page<>(curr, pageSize);
        Page<DishVO> dishVOPage = new Page<>();
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        if (dishName != null) {
            wrapper.likeRight(Dish::getName, dishName);
        }
        wrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(page, wrapper);
        //todo 赋值操作
        BeanUtils.copyProperties(page, dishVOPage, "records");
        List<Dish> dishList = page.getRecords();
        List<DishVO> dishVOList = new ArrayList<>(dishList.size());
        for (int i = 0; i < dishList.size(); i++) {
            Long categoryId = dishList.get(i).getCategoryId();
            Category category = categoryService.getById(categoryId);
            DishVO dishVO = new DishVO();
            dishVO.setCategoryName(category.getName());
            BeanUtils.copyProperties(dishList.get(i), dishVO);
            dishVOList.add(dishVO);
        }
        dishVOPage.setRecords(dishVOList);
        return R.success(dishVOPage);
    }

    @GetMapping("{id}")
    @ApiOperation("根据id查询菜品信息")
    @ApiImplicitParam(name = "id", value = "菜品id", required = true)
    public R<DishVO> page(@PathVariable("id") Long dishId) {
        log.info("根据id查询菜品信息-参数:{}", dishId);
        //TODO 根据id查询菜品信息
        try {
            DishVO dishVO = dishService.getDishWithFlavorsById(dishId);
            return R.success(dishVO);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @PutMapping()
    @CommonFields
    @ApiOperation("修改菜品")
    @ApiImplicitParam(name = "dishDTO", value = "菜品信息")
    public R<String> update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品-参数:{}", dishDTO);
        try {
            //dishDTO.setCategoryId(Long.parseLong(dishDTO.getCategoryName()));
            dishService.updateWithFlavors(dishDTO);
            return R.success(OperateConstant.UPDATE_DISH_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(OperateConstant.UPDATE_DISH_FAIL);
        }
    }

    @PostMapping("status/{status}")
    @ApiOperation("修改菜品售卖状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "菜品售卖状态: 0-停售 1-在售"),
            @ApiImplicitParam(name = "ids", value = "菜品id集合"),
    })
    public R<String> disable(@PathVariable("status") Integer status, @RequestParam("ids") List<Long> ids) {
        log.info("新增菜品-参数:{},{}", status, ids);
        try {
            dishService.disableBatchByIds(status, ids);
            return R.success(OperateConstant.DISABLE_DISH_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(OperateConstant.DISABLE_DISH_FAIL);
        }
    }

    @DeleteMapping
    @ApiOperation("删除菜品")
    @ApiImplicitParam(name = "ids", value = "菜品id集合")
    public R<String> delete(@RequestParam("ids") List<Long> ids) {
        log.info("删除菜品-参数:{}", ids);
        dishService.deleteBatchByIds(ids);
        return R.success(OperateConstant.DELETE_DISH_SUCCESS);
    }

    //@GetMapping("list")
    //@ApiOperation("根据分类id查询菜品信息集合")
    //public R<List<Dish>> listDishById(Dish dish) {
    //    log.info("dish:{}", dish);
    //    //TODO 根据分类id查询菜品信息集合
    //    LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
    //    wrapper
    //            .eq(Dish::getCategoryId, dish.getCategoryId())
    //            .eq(Dish::getStatus, 1)
    //            .orderByAsc(Dish::getSort)
    //            .orderByDesc(Dish::getUpdateTime);
    //    List<Dish> dishes = dishService.list(wrapper);
    //    return R.success(dishes);
    //}
    @GetMapping("list")
    @ApiOperation("根据分类id查询菜品信息集合")
    public R<List<DishVO>> listDishById(Dish dish) {
        log.info("dish:{}", dish);
        //TODO 根据分类id查询菜品信息集合
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Dish::getCategoryId, dish.getCategoryId())
                .eq(Dish::getStatus, 1)
                .orderByAsc(Dish::getSort)
                .orderByDesc(Dish::getUpdateTime);
        List<Dish> dishes = dishService.list(wrapper);
        List<DishVO> list = new ArrayList<>();
        for (Dish dish1 : dishes) {
            DishVO dishVO = dishService.getDishWithFlavorsById(dish1.getId());
            list.add(dishVO);
        }
        return R.success(list);
    }
}
