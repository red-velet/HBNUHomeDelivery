package com.qxy.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.reggie.dao.OrdersMapper;
import com.qxy.reggie.entity.*;
import com.qxy.reggie.exception.CustomException;
import com.qxy.reggie.handler.BaseContextHandler;
import com.qxy.reggie.service.*;
import com.qxy.reggie.vo.OrdersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: SayHello
 * @Date: 2023/3/30 14:49
 * @Introduction:
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    OrdersMapper ordersMapper;

    @Transactional
    @Override
    @SuppressWarnings("all")
    public void submit(Orders orders) {
        //TODO 用户下单
        //1.查询用户购物车信息、地址信息、用户信息
        Long userId = BaseContextHandler.getUserId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> list = shoppingCartService.list(wrapper);
        if (list == null || list.size() == 0) {
            throw new CustomException("下单失败:购物车为空");
        }
        User user = userService.getById(userId);
        AddressBook addressBook = addressService.getById(orders.getAddressBookId());
        if (Objects.isNull(addressBook)) {
            throw new CustomException("下单失败:未填写/选择地址信息");
        }
        //2.添加订单信息
        long orderId = IdWorker.getId();
        orders.setId(orderId);
        orders.setNumber(String.valueOf(orderId));
        orders.setStatus(2);
        orders.setUserId(userId);
        orders.setAddressBookId(addressBook.getId());
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        //计算总金额并封装订单明细信息
        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ShoppingCart shoppingCart = list.get(i);
            Integer number = shoppingCart.getNumber();
            Double price = shoppingCart.getAmount();
            amount.addAndGet(new BigDecimal(number).multiply(new BigDecimal(price)).intValue());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(number);
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setSetmealId(shoppingCart.getSetmealId());
            orderDetail.setName(shoppingCart.getName());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setAmount(shoppingCart.getAmount());
            orderDetailList.add(orderDetail);
        }
        orders.setAmount(new BigDecimal(amount.get()).doubleValue());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        Orders ordersEntity = new Orders();
        BeanUtils.copyProperties(orders, ordersEntity);
        ordersMapper.insert(ordersEntity);

        //3.添加订单明细信息
        orderDetailService.saveBatch(orderDetailList);

        //4.清空购物车
        shoppingCartService.remove(wrapper);
    }

    @Override
    public List<OrdersVO> getOrderListWithOrderDetailsList(Long userId, Integer curr, Integer pageSize) {
        Page<Orders> page = new Page<>(curr, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId, userId);
        this.page(page, wrapper);
        List<Orders> ordersList = page.getRecords();
        List<OrdersVO> ordersVOList = new ArrayList<>();
        for (int i = 0; i < ordersList.size(); i++) {
            OrdersVO ordersVO = new OrdersVO();
            Orders orders = ordersList.get(i);
            BeanUtils.copyProperties(orders, ordersVO);
            LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderDetail::getOrderId, orders.getId());
            List<OrderDetail> list = orderDetailService.list(queryWrapper);
            ordersVO.setOrderDetails(list);
            ordersVOList.add(ordersVO);
        }
        return ordersVOList;
    }

    @Override
    public Page<OrdersVO> getOrderListWithOrderDetailsListByPage(Long userId, Integer curr, Integer pageSize) {

        return new Page<>();
    }

    @Override
    public List<OrderDetail> getOrderDetailListByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, orderId);
        //根据order表的条件查询出order_detail的数据，因为一个订单可能有多条菜品数据
        return orderDetailService.list(queryWrapper);
    }
}
