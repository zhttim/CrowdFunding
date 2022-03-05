package com.tim.crowdfunding.api;

import com.tim.crowdfunding.entity.po.MemberPO;
import com.tim.crwodfunding.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("tim-crowd-mysql")
public interface MySQLRemoteService {
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPObyLoginAcctRemote(@RequestParam("loginacct")String loginacct);
}
