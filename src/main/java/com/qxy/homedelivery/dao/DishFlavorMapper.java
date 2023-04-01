package com.qxy.homedelivery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxy.homedelivery.entity.DishFlavor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/26 17:17
 * @Introduction:
 */
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
    List<DishFlavor> findById(@Param("id") Integer id);
}
