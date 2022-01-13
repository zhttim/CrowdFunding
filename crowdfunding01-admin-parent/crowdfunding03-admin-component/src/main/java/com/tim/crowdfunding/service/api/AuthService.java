package com.tim.crowdfunding.service.api;

import com.tim.crowdfunding.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> roleAuthRelationshipMap);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
