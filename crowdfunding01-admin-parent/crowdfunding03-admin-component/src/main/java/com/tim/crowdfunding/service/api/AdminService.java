package com.tim.crowdfunding.service.api;

import com.tim.crowdfunding.entity.Admin;

public interface AdminService {
    void saveAdmin(Admin admin);

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);
}
