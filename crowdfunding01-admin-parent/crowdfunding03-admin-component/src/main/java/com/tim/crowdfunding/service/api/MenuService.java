package com.tim.crowdfunding.service.api;

import com.tim.crowdfunding.entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getAll();

    void saveMenu(Menu menu);

    void editMenu(Menu menu);

    void removeMenu(Integer id);
}
