package com.tim.crowdfunding.handler;

import com.tim.crowdfunding.entity.vo.OrderProjectVO;
import com.tim.crowdfunding.service.api.OrderService;
import com.tim.crwodfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderProviderHandler {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId") Integer returnId) {
        try {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(returnId);

            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }
}
