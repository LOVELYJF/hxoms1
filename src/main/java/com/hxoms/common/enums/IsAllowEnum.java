package com.hxoms.common.enums;
/**
 * <b>功能描述: 是否通过枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 11:32
 */
public enum IsAllowEnum {
	NOT_ALLOW("0","不通过"),
	ALLOW("1","通过");

	private String code;

	private String name;

	IsAllowEnum(String code, String name) {
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
