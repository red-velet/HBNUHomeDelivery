package com.qxy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxy.reggie.annotation.CommonFields;
import com.qxy.reggie.common.R;
import com.qxy.reggie.constants.OperateConstant;
import com.qxy.reggie.entity.Category;
import com.qxy.reggie.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:21
 * @Introduction:
 */
@Slf4j
@RestController
@RequestMapping("category")
@Api(tags = "分类管理")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    /**
     * 新增分类
     *
     * @return
     */
    @PostMapping
    @CommonFields
    @ApiOperation("新增分类接口")
    @ApiImplicitParam(name = "category", value = "分类信息", required = true)
    public R<String> save(@RequestBody Category category) {
        //TODO 新增分类
        ////1、检查该分类名称是否已存在
        //categoryService.checkIsExist(category);
        ////2、不存在就添加
        categoryService.save(category);
        log.info(OperateConstant.ADD_CATEGORY_SUCCESS);
        return R.success(OperateConstant.ADD_EMPLOYEE_SUCCESS);
    }

    /**
     * 分页显示
     *
     * @return
     */
    @GetMapping("page")
    @ApiOperation("分页查询分类信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码,从1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", required = true),
    })
    public R<Page> page(@RequestParam("page") Integer curr, @RequestParam("pageSize") Integer pageSize) {
        //TODO 分页查询分页信息
        try {
            Page<Category> page = new Page<>(curr, pageSize);
            LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
            categoryService.page(page, wrapper);
            return R.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("error");
        }
    }

    @PutMapping
    @CommonFields
    @ApiOperation("更新分类接口")
    @ApiImplicitParam(name = "category", value = "分类信息", required = true)
    public R<String> update(@RequestBody Category category) {
        //TODO 修改分类信息
        try {
            categoryService.updateById(category);
            log.info(OperateConstant.UPDATE_CATEGORY_SUCCESS);
            return R.success(OperateConstant.UPDATE_CATEGORY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(OperateConstant.UPDATE_CATEGORY_FAIL);
        }
    }


    @DeleteMapping()
    @ApiOperation("根据分类id删除分类信息接口")
    @ApiImplicitParam(name = "ids", value = "分类id", required = true)
    public R<String> deleteById(@RequestParam("ids") Long ids) {
        //TODO 删除分类信息
        categoryService.removeCategoryById(ids);
        log.info(OperateConstant.DELETE_CATEGORY_SUCCESS);
        return R.success(OperateConstant.DELETE_CATEGORY_SUCCESS);
    }

    @GetMapping("list")
    @ApiOperation("根据分类类型查询分类信息集合")
    @ApiImplicitParam(name = "type", value = "分类类型", required = false)
    public R<List<Category>> listById(@RequestParam(value = "type", required = false) Integer type) {
        //TODO 根据类型查询分类信息集合
        if (Objects.nonNull(type)) {
            LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Category::getType, type).orderByAsc(Category::getUpdateTime);
            List<Category> list = categoryService.list(wrapper);
            return R.success(list);
        }
        List<Category> list = categoryService.list();
        return R.success(list);
    }

}
