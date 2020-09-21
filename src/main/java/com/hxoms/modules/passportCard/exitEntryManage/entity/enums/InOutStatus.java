package com.hxoms.modules.passportCard.exitEntryManage.entity.enums;
/**
 * <b>功能描述: 出入库状态枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/21 9:13
 */
public enum InOutStatus {
	OUT_STATUS("0","出库"),
	IN_STATUS("1","入库");

	private String code;

	private String name;

	InOutStatus(String code, String name) {
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
