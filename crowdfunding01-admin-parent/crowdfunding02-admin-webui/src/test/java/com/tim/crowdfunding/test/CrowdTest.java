package com.tim.crowdfunding.test;

import com.tim.crowdfunding.entity.Admin;
import com.tim.crowdfunding.entity.Role;
import com.tim.crowdfunding.mapper.AdminMapper;
import com.tim.crowdfunding.mapper.RoleMapper;
import com.tim.crowdfunding.service.api.AdminService;
import com.tim.crwodfunding.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//指定spring给junit提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
//加载spring配置文件注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testLog() {
//        获取logger类
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
//        测试logger输出
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

    @Test
//    测试添加数据
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "tom", "123456", "汤姆", "tom@qq.com", null);
        int i = adminMapper.insert(admin);
        System.out.println("受影响行数: i");
    }

    @Test
    public void testConnection() throws SQLException {
//        通过数据源对象获取连接
        Connection connection = dataSource.getConnection();
//        打印数据库连接
        System.out.println(connection);
    }

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "jerry@qq.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    //添加模拟用户数据
    public void testCreatData() {
        for (int i = 1000; i < 1100; i++) {
            String loginAcct = "testUser" + i;
            String userPswd = CrowdUtil.md5("123456");
            String userName = "User" + i;
            String email = loginAcct + "@qq.com";
            Admin admin = new Admin(null, loginAcct, userPswd, userName, email,null);
            adminMapper.insert(admin);
        }
    }

    @Test
    public void testCreateRoleData(){
        for (int i = 100; i < 131; i++) {
            String name = "testRole" + i;
            Role role = new Role(null, name);
            roleMapper.insert(role);
        }
    }

}
