package com.tim.crowdfunding.service.impl;

import com.tim.crowdfunding.entity.Admin;
import com.tim.crowdfunding.entity.AdminExample;
import com.tim.crowdfunding.mapper.AdminMapper;
import com.tim.crowdfunding.service.api.AdminService;
import com.tim.crwodfunding.constant.CrowdConstant;
import com.tim.crwodfunding.exception.LoginFailedException;
import com.tim.crwodfunding.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 根据登录账号查询Admin对象
        // 创建AdminExample对象
        AdminExample adminExample = new AdminExample();
        // 创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // 在Criteria对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
        // 调用AdminMapper的方法执行查询
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        // 判断Admin对象是否为null
        if (adminList == null || adminList.isEmpty()) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (adminList.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        //从adminList中取出admin
        Admin admin = adminList.get(0);

        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // Admin对象不为null，取出密码
        String userPswdDB = admin.getUserPswd();
        // 将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);
        // 比较密码
        if (!Objects.equals(userPswdDB, userPswdForm)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 如果一致则返回Admin对象
        return admin;
    }
}
