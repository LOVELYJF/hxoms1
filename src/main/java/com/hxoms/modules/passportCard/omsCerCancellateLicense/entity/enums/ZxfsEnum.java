package com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.enums;
/**
 * <b>功能描述: 注销方式枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 11:18
 */
public enum ZxfsEnum {

	CELF_CANCELLATE("0","自行注销"),
	ENTRUST_CANCELLATE("1","委托");

	private String code;

	private String name;

	ZxfsEnum(String code, String name) {
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
