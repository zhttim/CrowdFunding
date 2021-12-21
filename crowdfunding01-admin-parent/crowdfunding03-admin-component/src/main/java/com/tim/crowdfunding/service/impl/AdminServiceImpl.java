package com.tim.crowdfunding.service.impl;

import com.tim.crowdfunding.entity.Admin;
import com.tim.crowdfunding.mapper.AdminMapper;
import com.tim.crowdfunding.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }
}
