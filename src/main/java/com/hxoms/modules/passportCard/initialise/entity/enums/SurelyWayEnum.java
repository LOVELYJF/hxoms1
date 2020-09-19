package com.hxoms.modules.passportCard.initialise.entity.enums;
/**
 * <b>功能描述: 保管方式枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 14:23
 */
public enum SurelyWayEnum {

	CABINET("0", "证照机"),
	COUNTER("1","柜台");

	private String code;
	private String name;

	SurelyWayEnum(String code, String name) {
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
