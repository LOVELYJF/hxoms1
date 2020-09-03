package com.hxoms.common.utils;

import com.sun.javafx.logging.PulseLogger;

/**
 * <b>功能描述: 证照类型转换工具类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/3 15:46
 */
public class CerTypeUtil {

	public static String getCnTypeLicence(int zjlx){
		for(int i = 0 ; i < Constants.CER_TYPE_CODE.length ; i++){
			if(zjlx == Constants.CER_TYPE_CODE[i]){
				return Constants.CER_TYPE_NAME[i];
			}
		}
		return "";
	}
}
