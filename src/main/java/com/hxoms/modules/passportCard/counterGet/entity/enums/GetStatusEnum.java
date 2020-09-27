package com.hxoms.modules.passportCard.counterGet.entity.enums;
/**
 * <b>功能描述: 证照领取状态枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 14:43
 */
public enum GetStatusEnum {

	STATUS_ENUM_0("0", "未领取"),
	STATUS_ENUM_1("1", "已领取"),
	STATUS_ENUM_2("2", "取消领取");

	private String code;
	private String name;

	GetStatusEnum(String code, String name) {
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
