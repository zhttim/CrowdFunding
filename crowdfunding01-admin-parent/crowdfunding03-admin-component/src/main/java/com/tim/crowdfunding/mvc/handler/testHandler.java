package com.tim.crowdfunding.mvc.handler;

import com.tim.crowdfunding.entity.Admin;
import com.tim.crowdfunding.entity.AdminExample;
import com.tim.crowdfunding.mapper.AdminMapper;
import com.tim.crwodfunding.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class testHandler {
    @Autowired
    private AdminMapper adminMapper;

    @RequestMapping("/test/ssm.html")
    public String doTest(Model model){
        List<Admin> admins = adminMapper.selectByExample(new AdminExample());
        model.addAttribute("admins", admins);
        return "target";
    }

    @ResponseBody
    @RequestMapping("/send/array1.json")
    public ResultEntity testReceiveArray(@RequestBody List<Integer> array){
        Logger logger = LoggerFactory.getLogger(this.getClass());

        for (Integer number : array) {
            logger.info("number: " + number);
        }
        return ResultEntity.successWithoutData();
    }
}
