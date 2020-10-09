package com.hxoms.common.utils;/*
 * @description:
 * @author 杨波
 * @date:2020-10-01
 */

import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanUtilSelf {
    public static Field getClassField(String fieldName,Class<?> cls){
        try {
            Field field = cls.getDeclaredField(fieldName);

            return field;
        } catch (Exception e) {
            if(cls.getSuperclass()!=null)
                return getClassField(fieldName,cls.getSuperclass());
            return null;
        }
    }
    public static Object getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = getClassField(fieldName,object.getClass());
            if(field==null) return null;
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            Object value = field.get(object);

            return value;
        } catch (Exception e) {
            return null;
        }
    }
    public static String getFieldStringValueByFieldName(String fieldName, Object object ,String dateFormat) {
        try {
            Object hisValue = getFieldValueByFieldName(fieldName,object);
            if (null == hisValue) {
                return "";
            }
            if (hisValue instanceof Date||
                    hisValue instanceof java.sql.Date) {
                SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
                return sdf.format((Date) hisValue) ;
            }
            return hisValue.toString();
        } catch (Exception e) {
            return "";
        }
    }
    public static Date getFieldDateValueByFieldName(String fieldName, Object object,String format) {
        try {
            Object hisValue = getFieldValueByFieldName(fieldName,object);
            if (null == hisValue) {
                return null;
            }

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(hisValue.toString());
        } catch (Exception e) {
            return null;
        }
    }
    public static String getFieldTypeByFieldName(String fieldName, Object object) {
        try {
            Field field = getClassField(fieldName,object.getClass());
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            String type = field.getType().toString();
            return type;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFieldAnnotation(String fieldName, Object object) {
        try {
            Field field = getClassField(fieldName,object.getClass());
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
            if (null == property) {
                return "";
            }

            return property.value();
        } catch (Exception e) {
            return "";
        }

    }
}
