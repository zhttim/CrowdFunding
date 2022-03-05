package com.tim.crowdfunding.service.api;

import com.tim.crowdfunding.entity.po.MemberPO;
import org.apache.ibatis.annotations.Param;

public interface MemberService {

    MemberPO getMemberPObyLoginAcct(@Param("loginacct") String loginacct);
}
