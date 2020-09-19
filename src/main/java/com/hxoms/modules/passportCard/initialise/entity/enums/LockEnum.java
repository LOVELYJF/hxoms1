package com.hxoms.modules.passportCard.initialise.entity.enums;
/**
 * <b>功能描述: 锁定状态枚举类</b>
 * @Param: 
 * @Return: 
 * @Author: luoshuai
 * @Date: 2020/9/18 11:26
 */
public enum LockEnum {
	NOT_LOCK("0","未锁定"),
	LOCK("1","锁定");

	private String code;

	private String name;

	LockEnum(String code, String name) {
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
