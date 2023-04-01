package com.qxy.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qxy.reggie.dao.AddressMapper;
import com.qxy.reggie.entity.AddressBook;
import com.qxy.reggie.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * @Author: SayHello
 * @Date: 2023/3/28 22:19
 * @Introduction:
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressBook> implements AddressService {
}
