package com.tim.crowdfunding.test;

import com.tim.crowdfunding.entity.Admin;
import com.tim.crowdfunding.mapper.AdminMapper;
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
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testLog(){
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(null, "tom", "123456", "汤姆", "tom@qq.com", null);
        int i = adminMapper.insert(admin);
        System.out.println("受影响行数: i");
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

}
