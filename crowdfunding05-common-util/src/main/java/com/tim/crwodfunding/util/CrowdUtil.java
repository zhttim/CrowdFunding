package com.tim.crwodfunding.util;

import com.tim.crwodfunding.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * CrowdFunding项目通用工具类
 */
public class CrowdUtil {

    /**
     * 对明文字符串进行MD5加密
     * @param source 传入的明文字符串
     * @return 加密后的字符串
     */
    public static String md5(String source) {
        //判断source是否有效
        if (source == null || source.length() == 0){
            //无效字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            //获取MessageDigest对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            //获取明文密码对应的字符串
            byte[] input = source.getBytes(StandardCharsets.UTF_8);
            //执行加密
            byte[] output = messageDigest.digest(input);
            //创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            //按照16进制将BigInteger转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase() ;
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断当前请求是否为Ajax请求
     *
     * @param request 请求对象
     * @return true：当前请求为Ajax请求
     * false：当前请求不是Ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {

        // 1. 获取请求头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Request-With");

        // 2. 判断
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                (xRequestHeader != null && "XMLHttpRequest".equals(xRequestHeader));
    }

}
