package com.tim.crowdfunding.mvc.handler;

import com.tim.crowdfunding.entity.Auth;
import com.tim.crowdfunding.entity.Role;
import com.tim.crowdfunding.service.api.AdminService;
import com.tim.crowdfunding.service.api.AuthService;
import com.tim.crowdfunding.service.api.RoleService;
import com.tim.crwodfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship(@RequestBody Map<String,List<Integer>> roleAuthRelationshipMap){
        authService.saveRoleAuthRelationship(roleAuthRelationshipMap);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId
    ) {
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("assign/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("assign/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ) {
        // ????????????????????????
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // ????????????????????????
        List<Role> unassignedRoleList = roleService.getUnassignedRole(adminId);
        // ????????????
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unassignedRoleList", unassignedRoleList);

        return "assign-role";
    }

    @RequestMapping("assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            //????????????????????????????????????????????????????????????????????????
            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList
    ) {
        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
