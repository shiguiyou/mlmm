package com.wanquan.mlmmx.mlmm.utils;

import com.alibaba.fastjson.JSON;

/**
 * CopyRight All Reserves
 * Created by ZmlLucky on 2016/10/16 15:54
 * 类介绍：JSON工具类，Json字符串与Java对象互相转换（FastJson解析）
 * 邮箱：1060199499@qq.com
 */
public class JSONUtils{

    /**
     * import com.alibaba.fastjson.JSON;
        public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray
        public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject
        public static final <T> T parseObject(String text, Class<T> clazz); // 把JSON文本parse为JavaBean
        public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray
        public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合
        public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本
        public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本
        public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray。
     */

    public static <T> T parseToJavaBean(String json,Class<T> javaBean){
        return (T) JSON.parseObject(json,javaBean);
    }

    public static String parseToJsonStr(Object javaBean){
        return JSON.toJSONString(javaBean);
    }

    public static <T> T parseToJavaList(String json,Class<T> javaBean){
        return (T)JSON.parseArray(json,javaBean);
    }


}