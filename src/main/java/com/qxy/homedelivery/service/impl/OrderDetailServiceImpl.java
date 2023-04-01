package com.qxy.homedelivery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.homedelivery.dao.OrderDetailMapper;
import com.qxy.homedelivery.entity.OrderDetail;
import com.qxy.homedelivery.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @Author: SayHello
 * @Date: 2023/3/30 14:50
 * @Introduction:
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
