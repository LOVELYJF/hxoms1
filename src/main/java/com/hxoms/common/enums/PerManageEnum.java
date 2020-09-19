package com.hxoms.common.enums;
/**
 * <b>功能描述: 人员管理状态枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 9:57
 */
public enum PerManageEnum {
	PRESENT_EMPLOYMENT("1","现职人员"),
	NO_PRESENT_EMPLOYMENT("2","非现职人员");

	private String code;

	private String name;

	PerManageEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
