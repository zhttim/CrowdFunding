package com.tim.crowdfunding.service.api;

import com.github.pagehelper.PageInfo;
import com.tim.crowdfunding.entity.Admin;

import java.util.List;

public interface AdminService {

    void saveAdmin(Admin admin);

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
