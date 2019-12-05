package com.baizhi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {

    //给定一个字符串
    static String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    static String strNum = "0123456789";

    public static String getSalt(){
        StringBuffer salt = new StringBuffer();
        for (int i = 0; i <= 7; i++) {
            salt.append(str.charAt(new Random().nextInt(62)));
        }
        return salt.toString();
    }
    
    public static String getNum(){
    	StringBuffer salt = new StringBuffer();
    	for (int i = 0; i <= 8; i++) {
            salt.append(str.charAt(new Random().nextInt(10)));
        }
    	return salt.toString();
    }

    public static String getPassword(String upassword) {
    	return DigestUtils.md5Hex(upassword);
    }

    public static String getCode(){
        StringBuffer code = new StringBuffer();
        for (int i = 0; i <= 5; i++) {
            code.append(strNum.charAt(new Random().nextInt(10)));
        }
        return code.toString();
    }

}

