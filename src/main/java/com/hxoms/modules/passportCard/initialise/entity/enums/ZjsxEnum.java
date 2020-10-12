package com.hxoms.modules.passportCard.initialise.entity.enums;
/**
 * @Desc：证件形式
 * @Author: wangyunquan
 * @Date: 2020/9/18
 */
public enum ZjsxEnum {
	BOOK("0","本式"),
	CARD("1","卡式");

	private String code;

	private String name;

	ZjsxEnum(String code, String name) {
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
