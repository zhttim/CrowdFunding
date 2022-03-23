package com.tim.crowdfunding.service.api;

import com.tim.crowdfunding.entity.vo.OrderProjectVO;

public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer returnId);
}
