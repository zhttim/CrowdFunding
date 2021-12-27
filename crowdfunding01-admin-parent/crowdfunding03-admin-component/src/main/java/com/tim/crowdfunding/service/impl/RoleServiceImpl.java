package com.tim.crowdfunding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tim.crowdfunding.entity.Role;
import com.tim.crowdfunding.mapper.RoleMapper;
import com.tim.crowdfunding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        //开启分页
        PageHelper.startPage(pageNum,pageSize);
        //执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        //分装为PageInfo
        return new PageInfo<>(roleList);
    }
}
