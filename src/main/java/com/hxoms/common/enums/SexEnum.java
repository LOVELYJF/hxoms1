package com.hxoms.common.enums;

/**
 * <b>功能描述: 性别枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 14:18
 */
public enum SexEnum {
	MALE("1", "男"),
	getMale("2","女");

	private String code;
	private String name;

	SexEnum(String code, String name) {
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
