package com.hashtech.utils;

import com.hashtech.businessframework.utils.StringUtils;

import java.util.*;

/**
 * @author xujie
 * @description 处理rpc的url的工具类
 * @create 2021-12-06 16:02
 **/
public class URLProcessUtils {
    public static final String SEPARATOR = "?";
    public static final String JOINT_MARK = "&amp;";
    public static final String CONDITION_MARK = "=";


    /**
     * @param requestUrl：示例：http://192.168.110.118:9080/jyEVosUfSAlRJmGU?id&status
     * @return：jyEVosUfSAlRJmGU
     */
    public static String getRequestUrl(String requestUrl) {
        int endIndex = requestUrl.indexOf(SEPARATOR);
        String tempStr = requestUrl.substring(0, endIndex);
        return tempStr.substring(tempStr.lastIndexOf("/") + 1);
    }

    public static String getRequestUrl(String requestUrl, String separator) {
        int endIndex = requestUrl.indexOf(separator);
        String tempStr = requestUrl.substring(0, endIndex);
        return tempStr.substring(tempStr.lastIndexOf("/") + 1);
    }

    /**
     * @param requestUrl:示例：http://192.168.110.118:9080/jyEVosUfSAlRJmGU?id=xxx&status=xxx
     * @return
     */
    public static List<Map<String, Object>> getParamList(String requestUrl) {
        List<Map<String, Object>> maps = new LinkedList<>();
        String tempStr = requestUrl.substring(requestUrl.lastIndexOf(SEPARATOR) + 1);
        if (StringUtils.isBlank(tempStr)) {
            return Collections.emptyList();
        }
        String[] split = tempStr.split(JOINT_MARK);
        for (String s : split) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put(s.substring(0, s.indexOf(CONDITION_MARK)), s.substring(s.indexOf(CONDITION_MARK) + 1));
            maps.add(map);
        }
        return maps;
    }

    public static void main(String[] args) {
        String str = "http://192.168.110.118:9080/jyEVosUfSAlRJmGU?id=xxx&status=xxx";
//        System.out.println(getRequestUrl(str));
        System.out.println(getParamList(str));
    }

}
