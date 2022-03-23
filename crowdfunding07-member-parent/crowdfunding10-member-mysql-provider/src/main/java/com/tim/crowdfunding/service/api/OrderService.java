package com.tim.crowdfunding.service.api;

import com.tim.crowdfunding.entity.vo.AddressVO;
import com.tim.crowdfunding.entity.vo.OrderProjectVO;

import java.util.List;

public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);
}
