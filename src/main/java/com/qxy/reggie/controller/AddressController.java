package com.qxy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qxy.reggie.annotation.CommonFields;
import com.qxy.reggie.common.R;
import com.qxy.reggie.entity.AddressBook;
import com.qxy.reggie.handler.BaseContextHandler;
import com.qxy.reggie.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 22:17
 * @Introduction:
 */
@RestController
@RequestMapping("addressBook")
@Api(tags = "地址管理")
@Slf4j
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("list")
    @ApiOperation("显示当前用户收货地址")
    public R<List<AddressBook>> list() {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(AddressBook::getUserId, BaseContextHandler.getUserId());
        List<AddressBook> list = addressService.list(wrapper);
        return R.success(list);
    }

    @PostMapping()
    @ApiOperation("新增收货地址")
    @CommonFields
    @ApiImplicitParam(name = "addressBook", value = "地址信息")
    public R<String> save(@RequestBody AddressBook addressBook) {
        log.info("新增收货地址: {}", addressBook);
        addressBook.setUserId(BaseContextHandler.getUserId());
        addressService.save(addressBook);
        return R.success("新增收货地址成功");
    }

    @PutMapping("default")
    @ApiOperation("修改默认收货地址")
    @ApiImplicitParam(name = "addressBook", value = "使用addressBook接收前端json数据")
    public R<String> updatedefaultAddr(@RequestBody AddressBook addressBook) {
        Long id = addressBook.getId();
        log.info("修改默认收货地址: {}", id);
        //设置当前用户全部收货地址为未非默认
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContextHandler.getUserId())
                .set(AddressBook::getIsDefault, 0);
        addressService.update(wrapper);

        //设置该地址为默认
        wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getId, id)
                .set(AddressBook::getIsDefault, 1);
        addressService.update(wrapper);
        return R.success("修改默认收货地址成功");
    }


    @GetMapping("{id}")
    @ApiOperation("根据id查询地址信息")
    @ApiImplicitParam(name = "id", value = "地址id")
    public R<AddressBook> findById(@PathVariable("id") Long addressId) {
        //TODO 根据地址id查地址信息
        AddressBook addressBook = addressService.getById(addressId);
        return R.success(addressBook);
    }

    @PutMapping()
    @ApiOperation("修改收货地址")
    @CommonFields
    @ApiImplicitParam(name = "addressBook", value = "修改后的地址信息")
    public R<String> update(@RequestBody AddressBook addressBook) {
        Long id = addressBook.getId();
        log.info("修改收货地址: {}", id);
        addressService.updateById(addressBook);
        return R.success("修改收货地址成功");
    }

    @DeleteMapping()
    @ApiOperation("根据id删除地址信息")
    @ApiImplicitParam(name = "ids", value = "id集合")
    public R<String> delete(@RequestParam("ids") List<Long> addressIdList) {
        //TODO 根据地址id删除地址信息
        addressService.removeByIds(addressIdList);
        return R.success("删除地址信息成功");
    }

    @GetMapping("default")
    @ApiOperation("获取当前用户默认收货地址")
    public R<AddressBook> getdefaultAddr() {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContextHandler.getUserId())
                .eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressService.getOne(wrapper);
        return R.success(addressBook);
    }
}
