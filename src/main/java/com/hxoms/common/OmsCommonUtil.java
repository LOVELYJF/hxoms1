package com.hxoms.common;


/**
 * 涉密等级工具类
 * @author gaozhenyuan
 * @date 2020/05/28
 */
public class OmsCommonUtil {

    /** 涉密等级状态 */
    public static final String[] SECRET_LEVEL_STATUS = {"0","1","2","3"};
    public static final String[] SECRET_LEVEL_STATUS_NAME = {"非涉密","一般","重要","核心"};

    /**
     * 转换涉密等级名称为状态
     * @param name
     * @return String
     */
    public static final String toSecretLevelStatus(String name){
        String result = "";
        if("非涉密".equals(name)){
            result = SECRET_LEVEL_STATUS[0];
        }
        if("一般".equals(name) || "一般涉密".equals(name)){
            result = SECRET_LEVEL_STATUS[1];
        }
        if("重要".equals(name) || "重要涉密".equals(name)){
            result = SECRET_LEVEL_STATUS[2];
        }
        if("核心".equals(name) || "核心涉密".equals(name)){
            result = SECRET_LEVEL_STATUS[3];
        }
        return result;
    }

    /**
     * 转换涉密等级状态为名称
     * @param status
     * @return String
     */
    public static final String toSecretLevelStatusName(String status){
        String result = "";
        if(SECRET_LEVEL_STATUS[0].equals(status)){
            result = SECRET_LEVEL_STATUS_NAME[0];
        }
        if(SECRET_LEVEL_STATUS[1].equals(status)){
            result = SECRET_LEVEL_STATUS_NAME[1];
        }
        if(SECRET_LEVEL_STATUS[2].equals(status)){
            result = SECRET_LEVEL_STATUS_NAME[2];
        }
        if(SECRET_LEVEL_STATUS[3].equals(status)){
            result = SECRET_LEVEL_STATUS_NAME[3];
        }
        return result;
    }
}



