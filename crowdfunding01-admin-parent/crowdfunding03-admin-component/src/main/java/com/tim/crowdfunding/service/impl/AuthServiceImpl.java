package com.tim.crowdfunding.service.impl;

import com.tim.crowdfunding.entity.Auth;
import com.tim.crowdfunding.entity.AuthExample;
import com.tim.crowdfunding.mapper.AuthMapper;
import com.tim.crowdfunding.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> roleAuthRelationshipMap) {
        List<Integer> roleIdList = roleAuthRelationshipMap.get("roleId");
        Integer roleId = roleIdList.get(0);
        authMapper.deleteOldRelationship(roleId);

        List<Integer> authIdList = roleAuthRelationshipMap.get("authIdArray");
        if(authIdList != null && !authIdList.isEmpty()){
            authMapper.insertNewRelationship(roleId,authIdList);
        }
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }
}
