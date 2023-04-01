package com.qxy.homedelivery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.homedelivery.dao.AddressMapper;
import com.qxy.homedelivery.entity.AddressBook;
import com.qxy.homedelivery.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 22:19
 * @Introduction:
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressBook> implements AddressService {
}
