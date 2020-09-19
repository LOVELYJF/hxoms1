package com.hxoms.common.enums;
/**
 * <b>功能描述: 国内国外枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 11:05
 */
public enum IsDomesticEnum {
	DOMESTIC_ENUM("1","国内"),
	NOT_DOMESTIC_ENUM("2","国外");

	private String code;

	private String name;

	IsDomesticEnum(String code, String name) {
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
