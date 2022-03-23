package com.tim.crowdfunding.handler;

import com.tim.crowdfunding.api.MySQLRemoteService;
import com.tim.crowdfunding.entity.vo.OrderProjectVO;
import com.tim.crwodfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class OrderHandler {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("projectId") Integer projectId,
                                        @PathVariable("returnId") Integer returnId,
                                        HttpSession session) {

        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVORemote(returnId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            OrderProjectVO orderProjectVO = resultEntity.getData();

            session.setAttribute("orderProjectVO", orderProjectVO);
        }

        return "confirm-return";
    }
}
