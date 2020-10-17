package com.hxoms.common.utils;

import com.hxoms.modules.omsregcadre.entity.enums.LicenceIdentityEnum;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
    /**
     * @Desc: 计算日期
     * @Author: wangyunquan
     * @Param: [date, value]
     * @Return: java.util.Date
     * @Date: 2020/8/11
     */
    public static Date calDate(Date date, int value){
        Calendar calendar  =   Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, value);
        return calendar.getTime();
    }
    /**
     * @Desc: 计算日期相差
     * @Author: wangyunquan
     * @Param: [startDate, endDate]
     * @Return: java.lang.String
     * @Date: 2020/9/1
     */
    public static int calDateDiff(String startDate,String endDate) {
        SimpleDateFormat formatter =   new SimpleDateFormat( "yyyy.MM.dd");
        Long l = 0L;
        try {
            long ts = formatter.parse(startDate).getTime();
            long ts1 = formatter.parse(endDate).getTime();
            l = (ts - ts1) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l.intValue();
    }
    /**
     * @Desc: 计算证照持有情况
     * @Author: wangyunquan
     * @Param: [oldLicenceIdentityValue：库里值, newLicenceIdentityValue：新增]
     * @Return: java.lang.Integer
     * @Date: 2020/10/14
     */
    public static Integer calLicenceIdentity(Integer oldLicenceIdentityValue,Integer newLicenceIdentityValue){
        if(newLicenceIdentityValue>0){
            if(LicenceIdentityEnum.CXZ.getCode().equals(oldLicenceIdentityValue)||LicenceIdentityEnum.WZZ.getCode().equals(oldLicenceIdentityValue)){
                return newLicenceIdentityValue;
            }else if(LicenceIdentityEnum.FSWGLZZ.getCode().equals(oldLicenceIdentityValue)){
                return oldLicenceIdentityValue;
            }else {
                List<String>  binaryList=new LinkedList<>();
                char[] oldLicenceIdentityBina = fillZero(Integer.toBinaryString(oldLicenceIdentityValue)).toCharArray();
                char[] newLicenceIdentityBina = fillZero(Integer.toBinaryString(newLicenceIdentityValue)).toCharArray();
                for (int i=0;i<Integer.SIZE;i++){
                    if(oldLicenceIdentityBina[i]==newLicenceIdentityBina[i]&&oldLicenceIdentityBina[i]=='1')
                        return oldLicenceIdentityValue;
                }
                return calValueAdd(oldLicenceIdentityValue,newLicenceIdentityValue);
            }
        }
         return oldLicenceIdentityValue;
    }

    /**
     * @Desc: 二进制值高位补0
     * @Author: wangyunquan
     * @Param: [value]
     * @Return: java.lang.String
     * @Date: 2020/10/14
     */
    public static String fillZero(String value){
        while (value.length()<Integer.SIZE){
            value="0"+value;
        }
        return value;
    }
    /**
     * @Desc: 计算Integer类型相加
     * @Author: wangyunquan
     * @Param: [value, value1]
     * @Return: java.lang.Integer
     * @Date: 2020/10/14
     */
    public static Integer calValueAdd(Integer value, Integer value1){
        BigDecimal bigDecimal=new BigDecimal(value);
        BigDecimal bigDecimal1=new BigDecimal(value1);
        return bigDecimal.add(bigDecimal1).intValue();
    }
}