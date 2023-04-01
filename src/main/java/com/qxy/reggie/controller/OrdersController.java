package com.qxy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxy.reggie.common.R;
import com.qxy.reggie.dto.OrderPageDTO;
import com.qxy.reggie.entity.OrderDetail;
import com.qxy.reggie.entity.Orders;
import com.qxy.reggie.handler.BaseContextHandler;
import com.qxy.reggie.service.OrdersService;
import com.qxy.reggie.vo.OrdersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: SayHello
 * @Date: 2023/3/30 14:50
 * @Introduction:
 */
@RestController
@Api(tags = "订单管理")
@RequestMapping("order")
@Slf4j
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @PostMapping("submit")
    @ApiOperation("用户下单")
    @ApiImplicitParam(name = "orders", value = "订单参数")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单参数:{}", orders);
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    //@GetMapping("userPage")
    //@ApiOperation("获取当前用户的所有订单")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(name = "page", value = "当前页码,从1开始", required = true),
    //        @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", required = true)
    //})
    //public R<List<OrdersVO>> userPage(@RequestParam("page") Integer curr, @RequestParam("pageSize") Integer pageSize) {
    //    log.info("订单参数:{}-{}", curr, pageSize);
    //    List<OrdersVO> ordersVOList = ordersService.getOrderListWithOrderDetailsList(BaseContextHandler.getUserId(), curr, pageSize);
    //    return R.success(ordersVOList);
    //}

    @GetMapping("userPage")
    @ApiOperation("获取当前用户的所有订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码,从1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", required = true)
    })
    public R<Page> userPage(@RequestParam("page") Integer curr, @RequestParam("pageSize") Integer pageSize) {
        log.info("订单参数:{}-{}", curr, pageSize);
        //分页构造器对象
        Page<Orders> page = new Page<>(curr, pageSize);
        Page<OrdersVO> pageVo = new Page<>(curr, pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        Long userId = BaseContextHandler.getUserId();
        queryWrapper.eq(Orders::getUserId, userId);
        //这里是直接把当前用户分页的全部结果查询出来，要添加用户id作为查询条件，否则会出现用户可以查询到其他用户的订单情况
        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //这里是把所有的订单分页查询出来
        ordersService.page(page, queryWrapper);

        //对OrderDto进行属性赋值
        List<Orders> records = page.getRecords();
        List<OrdersVO> orderDtoList = records.stream().map((item) -> {//item其实就是分页查询出来的每个订单对象
            OrdersVO ordersVO = new OrdersVO();
            //此时的orderDto对象里面orderDetails属性还是空 下面准备为它赋值
            Long orderId = item.getId();//获取订单id
            //调用根据订单id条件查询订单明细数据的方法，把查询出来订单明细数据存入orderDetailList
            List<OrderDetail> orderDetailList = ordersService.getOrderDetailListByOrderId(orderId);

            BeanUtils.copyProperties(item, ordersVO);//把订单对象的数据复制到orderDto中
            //对orderDto进行OrderDetails属性的赋值
            ordersVO.setOrderDetails(orderDetailList);
            return ordersVO;
        }).collect(Collectors.toList());

        //将订单分页查询的订单数据以外的内容复制到pageDto中，不清楚可以对着图看
        BeanUtils.copyProperties(page, pageVo, "records");
        pageVo.setRecords(orderDtoList);
        return R.success(pageVo);
    }

    //@GetMapping("page")
    //@ApiOperation("获取所有订单明细")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(name = "page", value = "当前页码,从1开始", required = true),
    //        @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", required = true)
    //})
    //@ApiImplicitParam(name = "orders", value = "订单参数")
    //public R<Page> page(@RequestParam("page") Integer curr, @RequestParam("pageSize") Integer pageSize) {
    //    Page<Orders> page = new Page<>(curr, pageSize);
    //    LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
    //    wrapper.orderByDesc(Orders::getOrderTime);
    //    ordersService.page(page, wrapper);
    //    return R.success(page);
    //}

    @GetMapping("page")
    @ApiOperation("获取所有订单明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码,从1开始", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", required = true),
            @ApiImplicitParam(name = "number", value = "订单号"),
            @ApiImplicitParam(name = "beginTime", value = "起始时间"),
            @ApiImplicitParam(name = "endTime", value = "截止时间")
    })
    public R<Page> page(OrderPageDTO orderPageDTO) {
        Page<Orders> page = new Page<>(orderPageDTO.getPage(), orderPageDTO.getPageSize());
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(orderPageDTO.getNumber())) {
            wrapper.likeRight(Orders::getNumber, orderPageDTO.getNumber());
        }
        if (Objects.nonNull(orderPageDTO.getBeginTime())) {
            wrapper.between(Orders::getOrderTime, orderPageDTO.getBeginTime(), orderPageDTO.getEndTime());
        }
        wrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(page, wrapper);
        return R.success(page);
    }

    @PutMapping()
    @ApiOperation("修改订单状态:派送")
    @ApiImplicitParam(name = "orders", value = "分页条件查询参数")
    public R<String> send(@RequestBody Orders orders) {
        try {
            LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Orders::getId, orders.getId()).set(Orders::getStatus, orders.getStatus());
            ordersService.update(wrapper);
            return R.success("订单派送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("订单派送失败");
        }
    }

}
