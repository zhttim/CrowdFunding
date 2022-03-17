package com.tim.crowdfunding.test;

import com.tim.crowdfunding.config.OSSProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTest {
    @Autowired
    OSSProperties ossProperties;

    @Test
    public void testProperties() {
        System.out.println(ossProperties.getAccessKeyId());
    }
}
