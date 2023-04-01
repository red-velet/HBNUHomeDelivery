package com.qxy.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qxy.reggie.entity.OrderDetail;
import com.qxy.reggie.entity.Orders;
import com.qxy.reggie.vo.OrdersVO;

import java.util.List;

/**
 * @Author: SayHello
 * @Date: 2023/3/30 14:48
 * @Introduction:
 */
public interface OrdersService extends IService<Orders> {
    /**
     * 用户下单操作
     *
     * @param orders
     */
    void submit(Orders orders);

    /**
     * 分页查询当前用户所有订单
     *
     * @param userId
     * @param curr
     * @param pageSize
     * @return
     */
    List<OrdersVO> getOrderListWithOrderDetailsList(Long userId, Integer curr, Integer pageSize);

    Page<OrdersVO> getOrderListWithOrderDetailsListByPage(Long userId, Integer curr, Integer pageSize);

    List<OrderDetail> getOrderDetailListByOrderId(Long orderId);
}
