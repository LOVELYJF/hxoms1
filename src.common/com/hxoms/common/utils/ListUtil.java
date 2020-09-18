package com.hxoms.common.utils;

import java.util.List;
/**
 * <b>功能描述: 集合判断工具类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/17 9:14
 */
public class ListUtil {


	/**
	 * <b>功能描述: 判断集合是否为空</b>
	 * @Param: [list]
	 * @Return: boolean
	 * @Author: luoshuai
	 * @Date: 2020/9/17 9:14
	 */
	public static boolean isEmpty(List list) {
		if (list != null && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
}
