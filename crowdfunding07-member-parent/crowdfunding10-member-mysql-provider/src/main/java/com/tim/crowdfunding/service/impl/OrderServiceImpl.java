package com.tim.crowdfunding.service.impl;

import com.tim.crowdfunding.entity.po.AddressPO;
import com.tim.crowdfunding.entity.po.AddressPOExample;
import com.tim.crowdfunding.entity.po.OrderPO;
import com.tim.crowdfunding.entity.po.OrderProjectPO;
import com.tim.crowdfunding.entity.vo.AddressVO;
import com.tim.crowdfunding.entity.vo.OrderProjectVO;
import com.tim.crowdfunding.entity.vo.OrderVO;
import com.tim.crowdfunding.mapper.AddressPOMapper;
import com.tim.crowdfunding.mapper.OrderPOMapper;
import com.tim.crowdfunding.mapper.OrderProjectPOMapper;
import com.tim.crowdfunding.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveOrderVO(OrderVO orderVO) {
        OrderPO orderPO = new OrderPO();

        BeanUtils.copyProperties(orderVO, orderPO);

        OrderProjectPO orderProjectPO = new OrderProjectPO();

        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);

        orderPOMapper.insert(orderPO);

        // 保存OrderProjectPO需要orderPO的id作为外键
        Integer id = orderPO.getId();
        // 这里需要先修改mapper，
        orderProjectPO.setOrderId(id);

        orderProjectPOMapper.insert(orderProjectPO);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveAddressVO(AddressVO addressVO) {
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(addressVO, addressPO);
        addressPOMapper.insert(addressPO);
    }

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
