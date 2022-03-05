package com.tim.crowdfunding.service.impl;

import com.tim.crowdfunding.entity.po.MemberPO;
import com.tim.crowdfunding.entity.po.MemberPOExample;
import com.tim.crowdfunding.mapper.MemberPOMapper;
import com.tim.crowdfunding.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPObyLoginAcct(String loginacct) {
        MemberPOExample memberPOExample = new MemberPOExample();
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        List<MemberPO> memberPOList = memberPOMapper.selectByExample(memberPOExample);
        return memberPOList.get(0);
    }
}
