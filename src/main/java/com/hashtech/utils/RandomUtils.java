package com.hashtech.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author xujie
 * @description 生成随机数工具类
 * @create 2021-12-01 10:45
 **/
public class RandomUtils {

    private static final int DEFAULTLENGTH = 16;

    public static String getRandomExcludeNumber() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < DEFAULTLENGTH; i++) {

            int number = random.nextInt(str.length());

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomExcludeNumber(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {

            int number = random.nextInt(str.length());

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomExcludeNumber());
    }
}
