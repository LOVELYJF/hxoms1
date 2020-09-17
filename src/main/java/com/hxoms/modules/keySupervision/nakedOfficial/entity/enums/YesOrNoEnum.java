package com.hxoms.modules.keySupervision.nakedOfficial.entity.enums;
/**
 * <b>功能描述: 家属在限制性岗位</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/17 17:09
 */
public enum YesOrNoEnum {
	YES("1","是"),
	NO("0","否");

	private String code;

	private String name;

	YesOrNoEnum(String code, String name) {
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
