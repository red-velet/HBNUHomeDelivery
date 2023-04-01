package com.qxy.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.reggie.constants.OperateConstant;
import com.qxy.reggie.dao.CategoryMapper;
import com.qxy.reggie.dao.SetMealDishMapper;
import com.qxy.reggie.dao.SetMealMapper;
import com.qxy.reggie.dto.SetMealDTO;
import com.qxy.reggie.entity.Category;
import com.qxy.reggie.entity.SetMeal;
import com.qxy.reggie.entity.SetMealDish;
import com.qxy.reggie.exception.CustomException;
import com.qxy.reggie.service.SetMealDishService;
import com.qxy.reggie.service.SetMealService;
import com.qxy.reggie.vo.SetMealVO;
import org.springframework.beans.BeanUtils;
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
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal> implements SetMealService {
    @Autowired
    SetMealMapper setMealMapper;

    @Autowired
    SetMealDishMapper setMealDishMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    SetMealDishService setMealDishService;

    @Override
    @Transactional
    public void saveWithDishes(SetMealDTO setMealDTO) {
        //TODO 同时插入套餐信息和套餐关联的菜品信息
        //检查套餐是否已存在
        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMeal::getName, setMealDTO.getName());
        SetMeal setMeal = setMealMapper.selectOne(wrapper);
        if (Objects.nonNull(setMeal)) {
            throw new CustomException(OperateConstant.ADD_SETMEAL_FAIL + ":套餐名称已被使用");
        }
        //往套餐表插入数据
        SetMeal setMealEntity = new SetMeal();
        BeanUtils.copyProperties(setMealDTO, setMealEntity);
        setMealMapper.insert(setMealEntity);
        //往套餐菜品关联表插入数据
        //清空原有的
        LambdaQueryWrapper<SetMealDish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(SetMealDish::getSetmealId, setMealEntity.getId());
        setMealDishMapper.delete(dishLambdaQueryWrapper);
        //添加新的
        List<SetMealDish> setmealDishes = setMealDTO.getSetmealDishes();
        for (SetMealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setMealEntity.getId().toString());
            setmealDish.setCreateTime(setMealEntity.getCreateTime());
            setmealDish.setUpdateTime(setMealEntity.getUpdateTime());
            setmealDish.setCreateUser(setMealEntity.getCreateUser());
            setmealDish.setUpdateUser(setMealEntity.getUpdateUser());
            setMealDishMapper.insert(setmealDish);
        }
    }

    @Override
    public SetMealVO getSetMealWithDishesById(Long setMealId) {
        SetMeal setMeal = setMealMapper.selectById(setMealId);
        Category category = categoryMapper.selectById(setMeal.getCategoryId());
        SetMealVO setMealVO = new SetMealVO();
        BeanUtils.copyProperties(setMeal, setMealVO);
        setMealVO.setCategoryName(category.getName());
        LambdaQueryWrapper<SetMealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMealDish::getSetmealId, setMealId);

        List<SetMealDish> setMealDishes = setMealDishMapper.selectList(wrapper);
        setMealVO.setSetmealDishes(setMealDishes);
        return setMealVO;
    }

    @Override
    @Transactional
    @SuppressWarnings("all")
    public void updateWithDishes(SetMealDTO setMealDTO) {
        //TODO 修改套餐信息
        //1.检查套餐名是否被使用
        if (checkSetMealIsExist(setMealDTO)) {
            throw new CustomException(OperateConstant.UPDATE_SETMEAL_FAIL + ":套餐名称已被使用");
        }
        //2.修改
        //todo 2.1修改套餐表
        SetMeal setMealEntity = new SetMeal();
        setMealEntity.setId(setMealDTO.getId());
        setMealEntity.setCategoryId(setMealDTO.getCategoryId());
        setMealEntity.setName(setMealDTO.getName());
        setMealEntity.setPrice(setMealDTO.getPrice());
        setMealEntity.setDescription(setMealDTO.getDescription());
        setMealEntity.setImage(setMealDTO.getImage());
        setMealEntity.setCreateTime(setMealDTO.getCreateTime());
        setMealEntity.setCreateUser(setMealDTO.getCreateUser());
        setMealEntity.setUpdateTime(setMealDTO.getUpdateTime());
        setMealEntity.setUpdateUser(setMealDTO.getUpdateUser());
        setMealEntity.setCode("");
        setMealMapper.updateById(setMealDTO);
        //todo 2.2修改套餐关联菜品表
        //先删除原有的
        LambdaQueryWrapper<SetMealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMealDish::getSetmealId, setMealEntity.getId());
        setMealDishMapper.delete(wrapper);

        //再添加
        List<SetMealDish> setMealDishes = saveDishes(setMealDTO.getSetmealDishes(), setMealEntity);
        setMealDishService.saveBatch(setMealDishes);
    }

    @Override
    public void disableBatchByIds(Integer status, List<Long> ids) {
        for (Long id : ids) {
            LambdaUpdateWrapper<SetMeal> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(SetMeal::getId, id).set(SetMeal::getStatus, status);
            setMealMapper.update(null, wrapper);
        }
    }

    @Override
    public void deleteBatchByIds(List<Long> ids) {
        //todo 删除套餐:在售不能删除,停售才能删除
        //检查套餐是否已经停售
        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SetMeal::getId, ids).eq(SetMeal::getStatus, 1);
        Integer count = setMealMapper.selectCount(wrapper);
        if (count > 0) {
            throw new CustomException(OperateConstant.DELETE_SETMEAL_FAIL + ":套餐正在售出");
        }
        //删除套餐表数据
        setMealMapper.deleteBatchIds(ids);
        //删除套餐菜品关联表数据
        LambdaQueryWrapper<SetMealDish> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.in(SetMealDish::getSetmealId, ids);
        setMealDishService.remove(wrapper1);
    }

    private List<SetMealDish> saveDishes(List<SetMealDish> setMealDishes, SetMeal setMeal) {
        Long setMealId = setMeal.getId();
        for (int i = 0; i < setMealDishes.size(); i++) {
            setMealDishes.get(i).setSetmealId(setMealId.toString());
            setMealDishes.get(i).setCreateTime(setMeal.getCreateTime());
            setMealDishes.get(i).setUpdateTime(setMeal.getUpdateTime());
            setMealDishes.get(i).setCreateUser(setMeal.getCreateUser());
            setMealDishes.get(i).setUpdateUser(setMeal.getUpdateUser());
        }
        return setMealDishes;
    }

    /**
     * 判断套餐名称是否已被使用
     *
     * @param setMealDTO
     * @return
     */
    private Boolean checkSetMealIsExist(SetMealDTO setMealDTO) {
        String newName = setMealDTO.getName();
        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetMeal::getName, setMealDTO.getName());
        SetMeal setMeal = setMealMapper.selectById(setMealDTO.getId());
        String oldName = setMeal.getName();
        //如果新修改的套餐名称和原来的套餐名称不相等
        if (!newName.equals(oldName)) {
            //判断新的套餐名称是否已经被使用
            LambdaQueryWrapper<SetMeal> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(SetMeal::getName, newName);
            Integer count = setMealMapper.selectCount(wrapper1);
            return count > 0;
        }
        return false;
    }
}
