package com.tim.crowdfunding.handler;

import com.tim.crowdfunding.api.MySQLRemoteService;
import com.tim.crowdfunding.api.RedisRemoteService;
import com.tim.crowdfunding.entity.po.MemberPO;
import com.tim.crowdfunding.entity.vo.MemberVO;
import com.tim.crwodfunding.constant.CrowdConstant;
import com.tim.crwodfunding.util.CrowdUtil;
import com.tim.crwodfunding.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {

    @Autowired
    private RedisRemoteService redisRemoteService;
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, ModelMap modelMap) {
        String phoneNum = memberVO.getPhoneNum();

        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

        ResultEntity<String> redisStringValueByKeyResultEntity = redisRemoteService.getRedisStringValueByKey(key);

        String result = redisStringValueByKeyResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)) {

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, redisStringValueByKeyResultEntity.getMessage());
            return "member-reg";
        }

        String redisCode = redisStringValueByKeyResultEntity.getData();
        if (redisCode == null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }

        String formCode = memberVO.getCode();
        if (!Objects.equals(formCode, redisCode)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
        }
        redisRemoteService.removeRedisKeyRemote(key);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String userpswd = memberVO.getUserpswd();
        String encodeUserpswd = bCryptPasswordEncoder.encode(userpswd);

        memberVO.setUserpswd(encodeUserpswd);

        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);
        ResultEntity<String> saveMemberResultEntity = mySQLRemoteService.saveMemberRemote(memberPO);
        if (ResultEntity.FAILED.equals(saveMemberResultEntity)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberResultEntity.getMessage());
            return "member-reg";
        }
        return "member-login";
    }

    @ResponseBody
    @RequestMapping("auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendMessage(phoneNum);
        if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
            String code = sendMessageResultEntity.getData();
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 5L, TimeUnit.MINUTES);
            if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())) {
                return ResultEntity.successWithoutData();
            } else {
                return saveCodeResultEntity;
            }
        } else {
            return sendMessageResultEntity;
        }
    }
}
