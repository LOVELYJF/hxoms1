package com.hxoms.modules.condition.service;

import java.util.List;
import java.util.Map;

public interface OmsConditionService {
    /**
     * 检查约束条件
     * @param applyId 申请id
     * @param type 类型（因公，因私，延期回国）
     * @return
     */
    List<Map<String, String>> checkCondition(String applyId, String type);
    /**
     * 检查约束条件(选择人员)
     * @param a0100 人员id
     * @param type 类型（因公，因私，延期回国）
     * @return
     */
    List<Map<String, String>> checkConditionByA0100(String a0100, String type);
}
