package com.qxy.homedelivery.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxy.homedelivery.annotation.CommonFields;
import com.qxy.homedelivery.common.R;
import com.qxy.homedelivery.constants.OperateConstant;
import com.qxy.homedelivery.constants.RedisConstant;
import com.qxy.homedelivery.dto.SetMealDTO;
import com.qxy.homedelivery.entity.Category;
import com.qxy.homedelivery.entity.Dish;
import com.qxy.homedelivery.entity.SetMeal;
import com.qxy.homedelivery.entity.SetMealDish;
import com.qxy.homedelivery.service.CategoryService;
import com.qxy.homedelivery.service.DishService;
import com.qxy.homedelivery.service.SetMealDishService;
import com.qxy.homedelivery.service.SetMealService;
import com.qxy.homedelivery.vo.SetMealDishVO;
import com.qxy.homedelivery.vo.SetMealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: SayHello
 * @Date: 2023/3/26 17:19
 * @Introduction:
 */
@RestController
@Api(tags = "套餐管理")
@RequestMapping("setmeal")
@Slf4j
public class SetMealController {
    @Autowired
    SetMealService setMealService;

    @Autowired
    DishService dishService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    SetMealDishService setMealDishService;

    @Autowired
    RedisTemplate redisTemplate;


    @PostMapping()
    @CommonFields
    @ApiOperation("新增套餐")
    @ApiImplicitParam(name = "setMealDTO", value = "套餐信息")
    public R<String> save(@RequestBody SetMealDTO setMealDTO) {
        log.info("新增套餐-参数:{}", setMealDTO);
        setMealService.saveWithDishes(setMealDTO);
        return R.success(OperateConstant.ADD_SETMEAL_SUCCESS);
    }

    @GetMapping("page")
    @ApiOperation("分页显示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码,从1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", required = true),
            @ApiImplicitParam(name = "name", value = "查询条件使用setMealDTO接收", required = false)
    })
    public R<Page<SetMealVO>> page(@RequestParam("page") Integer curr, @RequestParam("pageSize") Integer pageSize, SetMealDTO setMealDTO) {
        //TODO 分页查询套餐信息
        Page<SetMeal> page = new Page<>(curr, pageSize);
        Page<SetMealVO> setMealVOPage = new Page<>();
        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(setMealDTO.getName())) {
            wrapper.likeRight(SetMeal::getName, setMealDTO.getName());
        }
        wrapper.orderByDesc(SetMeal::getUpdateTime);
        setMealService.page(page, wrapper);

        //todo 赋值操作
        BeanUtils.copyProperties(page, setMealVOPage, "records");
        List<SetMeal> setMealList = page.getRecords();
        List<SetMealVO> setMealVOList = new ArrayList<>(setMealList.size());
        for (int i = 0; i < setMealList.size(); i++) {
            Long categoryId = setMealList.get(i).getCategoryId();
            Category category = categoryService.getById(categoryId);
            SetMealVO setMealVO = new SetMealVO();
            setMealVO.setCategoryName(category.getName());
            BeanUtils.copyProperties(setMealList.get(i), setMealVO);
            setMealVOList.add(setMealVO);
        }
        setMealVOPage.setRecords(setMealVOList);
        return R.success(setMealVOPage);
    }

    @GetMapping("{id}")
    @ApiOperation("根据id查询套餐信息")
    @ApiImplicitParam(name = "id", value = "套餐id", required = true)
    public R<SetMealVO> page(@PathVariable("id") Long setMealId) {
        log.info("根据id查询套餐信息-参数:{}", setMealId);
        //TODO 根据id查询套餐信息
        try {
            SetMealVO setMealVO = setMealService.getSetMealWithDishesById(setMealId);
            return R.success(setMealVO);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    @PutMapping()
    @CommonFields
    @ApiOperation("修改套餐")
    @ApiImplicitParam(name = "setMealDTO", value = "套餐信息")
    public R<String> update(@RequestBody SetMealDTO setMealDTO) {
        log.info("修改菜品-参数:{}", setMealDTO);
        setMealService.updateWithDishes(setMealDTO);
        return R.success(OperateConstant.UPDATE_SETMEAL_SUCCESS);
    }

    @PostMapping("status/{status}")
    @ApiOperation("修改套餐售卖状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "套餐售卖状态: 0-停售 1-在售"),
            @ApiImplicitParam(name = "ids", value = "套餐id集合"),
    })
    public R<String> disable(@PathVariable("status") Integer status, @RequestParam("ids") List<Long> ids) {
        log.info("禁用套餐-参数:{},{}", status, ids);
        try {
            setMealService.disableBatchByIds(status, ids);
            return R.success(OperateConstant.DISABLE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(OperateConstant.DISABLE_SETMEAL_FAIL);
        }
    }

    @DeleteMapping
    @ApiOperation("删除套餐")
    @ApiImplicitParam(name = "ids", value = "套餐id集合")
    public R<String> delete(@RequestParam("ids") List<Long> ids) {
        log.info("删除套餐-参数:{}", ids);
        setMealService.deleteBatchByIds(ids);
        return R.success(OperateConstant.DELETE_SETMEAL_SUCCESS);
    }

    //@GetMapping("list")
    //@ApiOperation("根据分类id查询套餐信息集合")
    //public R<List<SetMealDish>> listById(@RequestParam("categoryId") Long id, @RequestParam("status") Integer status) {
    //    //TODO 根据类型查询分类信息集合
    //    LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
    //    wrapper.eq(SetMeal::getCategoryId, id).eq(SetMeal::getStatus, status);
    //    SetMeal setMeal = setMealService.getOne(wrapper);
    //    SetMealVO setMealVO = setMealService.getSetMealWithDishesById(setMeal.getId());
    //    return R.success(setMealVO.getSetmealDishes());
    //}
    
    @GetMapping("list")
    @ApiOperation("根据分类id查询套餐信息集合")
    public R<List<SetMeal>> listById(@RequestParam("categoryId") Long id, @RequestParam("status") Integer status) {
        //TODO 根据类型查询分类信息集合
        //todo 新增redis缓存
        List<SetMeal> list = null;
        String key = RedisConstant.PREFIX_SETMEAL + id + ":" + status;
        list = (List<SetMeal>) redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(list)) {
            log.info("移动端查询分类套餐走的缓存");
            //查看到再给套餐缓存续命
            redisTemplate.expire(key, RedisConstant.SETMEAL_TIME, TimeUnit.MINUTES);
            return R.success(list);
        }

        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMeal::getCategoryId, id).eq(SetMeal::getStatus, status);
        list = setMealService.list(wrapper);
        //将查询到的结果添加到缓存
        redisTemplate.opsForValue().set(key, list, RedisConstant.SETMEAL_TIME, TimeUnit.MINUTES);
        log.info("移动端查询分类套餐走的数据库");
        return R.success(list);
    }

    @GetMapping("dish/{id}")
    @ApiOperation("根据套餐id查询包含菜品集合")
    public R<List<SetMealDishVO>> listById(@PathVariable("id") Long setmealId) {
        //TODO 根据类型查询分类信息集合
        //todo 新增redis缓存
        //走缓存
        List<SetMealDishVO> setMealVOList = null;
        String key = RedisConstant.PREFIX_SETMEAL + setmealId;
        setMealVOList = (List<SetMealDishVO>) redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(setMealVOList)) {
            log.info("移动端查询分类套餐走的缓存");
            //查看到再给套餐缓存续命
            redisTemplate.expire(key, RedisConstant.SETMEAL_TIME, TimeUnit.MINUTES);
            return R.success(setMealVOList);
        }

        //查数据库
        LambdaQueryWrapper<SetMealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMealDish::getSetmealId, setmealId);
        List<SetMealDish> list = setMealDishService.list(wrapper);
        setMealVOList = new ArrayList<>();
        for (SetMealDish setMealDish : list) {
            SetMealDishVO setMealDishVO = new SetMealDishVO();
            BeanUtils.copyProperties(setMealDish, setMealDishVO);
            setMealVOList.add(setMealDishVO);
        }
        for (int i = 0; i < list.size(); i++) {
            String dishId = list.get(i).getDishId();
            Dish dish = dishService.getById(dishId);
            setMealVOList.get(i).setImage(dish.getImage());
        }
        //将查询到的结果添加到缓存
        redisTemplate.opsForValue().set(key, setMealVOList, RedisConstant.SETMEAL_TIME, TimeUnit.MINUTES);
        log.info("移动端查询分类套餐走的数据库");
        return R.success(setMealVOList);
    }
}
