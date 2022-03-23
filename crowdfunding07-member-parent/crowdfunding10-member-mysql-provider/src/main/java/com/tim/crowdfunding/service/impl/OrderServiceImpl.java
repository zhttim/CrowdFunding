package com.tim.crowdfunding.service.impl;

import com.tim.crowdfunding.entity.po.AddressPO;
import com.tim.crowdfunding.entity.po.AddressPOExample;
import com.tim.crowdfunding.entity.vo.AddressVO;
import com.tim.crowdfunding.entity.vo.OrderProjectVO;
import com.tim.crowdfunding.mapper.AddressPOMapper;
import com.tim.crowdfunding.mapper.OrderPOMapper;
import com.tim.crowdfunding.mapper.OrderProjectPOMapper;
import com.tim.crowdfunding.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public List<AddressVO> getAddressVOList(Integer memberId) {
        AddressPOExample addressPOExample = new AddressPOExample();
        AddressPOExample.Criteria addressPOExampleCriteria = addressPOExample.createCriteria();
        addressPOExampleCriteria.andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(addressPOExample);
        ArrayList<AddressVO> addressVOList = new ArrayList();
        for (AddressPO addressPO : addressPOList) {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO, addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Override
    public OrderProjectVO getOrderProjectVO(Integer returnId) {

        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }
}
