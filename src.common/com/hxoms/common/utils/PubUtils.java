package com.hxoms.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc：工具类
 * @Author: wangyunquan
 * @Date: 2020/8/3
 */
public class PubUtils {
    /**
     * @Desc: 替换占位字符串，格式：${字段名}
     * @Author: wangyunquan
     * @Param: [object, msgTemplate]
     * @Return: java.lang.String
     * @Date: 2020/8/3
     */
    public static String replaceWord(Object object,String msgTemplate) throws IllegalAccessException, InvocationTargetException {
        Class clazz=object.getClass();
        StringBuffer sb = new StringBuffer();
        Matcher matcher = Pattern.compile("\\$\\{\\w+\\}").matcher(msgTemplate);
        while (matcher.find()) {
            String param = matcher.group();
            String field=param.substring(2, param.length() - 1);
            Object value = null;
            try {
                value = clazz.getDeclaredMethod("get"+field.substring(0,1).toUpperCase()+field.substring(1,field.length())).invoke(object);
            } catch (NoSuchMethodException e) {
                //e.printStackTrace();
            }finally {
                matcher.appendReplacement(sb, value == null ? "null" : value.toString());
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
