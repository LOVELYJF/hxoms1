package com.hxoms.modules.passportCard.counterGet.entity.enums;
/**
 * <b>功能描述: 证照领取数据来源枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 14:35
 */
public enum ReceiveSourceEnum {

	SOURCE_0("0", "因私出国(境)"),
	SOURCE_1("1", "撤销登记备案"),
	SOURCE_2("2", "证照借出"),
	SOURCE_3("3", "证照过期"),
	SOURCE_4("4", "管理员取证"),
	SOURCE_5("5", "证照注销"),
	SOURCE_6("6", "盘亏");

	private String code;
	private String name;

	ReceiveSourceEnum(String code, String name) {
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
