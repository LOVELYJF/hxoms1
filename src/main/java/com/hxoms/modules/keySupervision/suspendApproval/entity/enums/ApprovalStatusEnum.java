package com.hxoms.modules.keySupervision.suspendApproval.entity.enums;
/**
 * <b>功能描述: 审批状态枚举类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/9/18 10:50
 */
public enum ApprovalStatusEnum {
	ALLOW_APPROVAL("1","允许审批"),
	NOT_ALLOW_APPROVAL("0","暂停审批");

	private String code;

	private String name;

	ApprovalStatusEnum(String code, String name) {
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
