package com.hxoms.modules.condition.service;

import java.util.Date;
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
     * @param procpersonId 备案人员id
     * @param type 类型（因公，因私，延期回国）
     */
    List<Map<String, String>> checkConditionByA0100(String procpersonId, String type);

    /**
     * 负面信息
     * @param a0100 人员id
     * @param abroadTime 出国时间
     */
    String selectNegativeInfo(String a0100, Date abroadTime);

    /**
     * 提醒条件消息发送
     * @param applyId 申请id
     * @param type 类型（因公，因私，延期回国）
     */
    void remindCondition(String applyId, String type);
}
