package com.qxy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qxy.reggie.entity.Category;

/**
 * @Author: SayHello
 * @Date: 2023/3/18 23:10
 * @Introduction:
 */
public interface CategoryService extends IService<Category> {

    /**
     * 检查分类信息是否已存在
     *
     * @param category
     */
    void checkIsExist(Category category);

    /**
     * 根据id删除分类
     *
     * @param id
     */
    void removeCategoryById(Long id);
}
