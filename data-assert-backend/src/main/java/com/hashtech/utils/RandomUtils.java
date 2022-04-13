package com.hashtech.utils;

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

    public static String getRandomWithNumber(int length) {
        String str = "0123456789";
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {

            int number = random.nextInt(str.length());
            if (i == 0){
                while (number == 0){
                    number = random.nextInt(str.length());
                }
            }
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomExcludeNumber());
    }
}
