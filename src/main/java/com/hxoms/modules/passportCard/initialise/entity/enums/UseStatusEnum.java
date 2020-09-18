package com.hxoms.modules.passportCard.initialise.entity.enums;
/**
 * <b>功能描述: 使用状态枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 11:26
 */
public enum UseStatusEnum {
	NOT_USE("0","未使用"),
	ON_USE("1","已使用");

	private String code;

	private String name;

	UseStatusEnum(String code, String name) {
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
