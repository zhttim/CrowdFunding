package com.tim.crowdfunding.service.impl;

import com.tim.crowdfunding.entity.Menu;
import com.tim.crowdfunding.entity.MenuExample;
import com.tim.crowdfunding.mapper.MenuMapper;
import com.tim.crowdfunding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }
}
