package com.tim.crowdfunding.mvc.handler;

import com.tim.crowdfunding.entity.Menu;
import com.tim.crowdfunding.service.api.MenuService;
import com.tim.crwodfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class MenuHandler {
    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTree(){
        //获取全部Menu对象
        List<Menu> menuList = menuService.getAll();
        //设置空根节点
        Menu root = null;
        // 创建 Map 对象用来存储 id 和 Menu 对象的对应关系便于查找父节点
        Map<Integer,Menu> menuMap = new HashMap<>();

        // 遍历 menuList 填充 menuMap
        for (Menu menu: menuList) {

            Integer id = menu.getId();

            menuMap.put(id, menu);
        }

        // 再次遍历 menuList 查找根节点、组装父子节点
        for (Menu menu: menuList) {

            // 获取当前对象pid
            Integer pid = menu.getPid();

            //如果父节点id为空则此Menu对象为根节点
            if (pid == null) {
                // 将当前对象赋给root
                root = menu;
                continue;
            }
            // 如果pid不为null，说明当前节点有父节点，找到父节点就可以进行组装
            menuMap.get(pid).getChildren().add(menu);
        }
        //返回根节点对象
        return ResultEntity.successWithData(root);
    }

    @ResponseBody
    @RequestMapping("menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }


}
