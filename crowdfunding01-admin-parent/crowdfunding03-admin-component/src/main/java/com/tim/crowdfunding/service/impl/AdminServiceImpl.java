package com.tim.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tim.crowdfunding.entity.Admin;
import com.tim.crowdfunding.entity.AdminExample;
import com.tim.crowdfunding.mapper.AdminMapper;
import com.tim.crowdfunding.service.api.AdminService;
import com.tim.crwodfunding.constant.CrowdConstant;
import com.tim.crwodfunding.exception.LoginAcctAlreadyInUseException;
import com.tim.crwodfunding.exception.LoginFailedException;
import com.tim.crwodfunding.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void remove(Integer adminId) {
        //根据id删除数据库中admin数据
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 调用PageHelper的静态方法开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 执行查询
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);
        // 封装到PageInfo对象
        return new PageInfo<Admin>(adminList);
    }
    @Override
    public void saveAdmin(Admin admin) {
        // 密码加密
        admin.setUserPswd(CrowdUtil.md5(admin.getUserPswd()));
        //设置创建时间
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        admin.setCreateTime(createTime);

        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
             if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
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
