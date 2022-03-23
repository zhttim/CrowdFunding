package com.tim.crowdfunding.service.impl;

import com.tim.crowdfunding.entity.vo.OrderProjectVO;
import com.tim.crowdfunding.mapper.AddressPOMapper;
import com.tim.crowdfunding.mapper.OrderPOMapper;
import com.tim.crowdfunding.mapper.OrderProjectPOMapper;
import com.tim.crowdfunding.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;
    @Autowired
    private AddressPOMapper addressPOMapper;
    @Autowired
    private OrderPOMapper orderPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer returnId) {

        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }
}
