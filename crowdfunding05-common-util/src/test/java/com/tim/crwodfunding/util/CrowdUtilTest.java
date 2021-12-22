package com.tim.crwodfunding.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class CrowdUtilTest {

    @Test
    public void md5() {
        String source = "123456";
        String s = CrowdUtil.md5(source);
        System.out.println(s);
    }
}