package com.tim.crowdfunding.service.api;

import com.github.pagehelper.PageInfo;
import com.tim.crowdfunding.entity.Role;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);

    void updateRole(Role role);
}
