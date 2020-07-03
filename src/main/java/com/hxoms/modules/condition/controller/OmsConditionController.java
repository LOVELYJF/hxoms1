package com.hxoms.modules.condition.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.condition.service.OmsConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc: 约束条件管理
 * @author: lijing
 * @date: 2020-05-28
 */
@RestController
@RequestMapping("/omsCondition")
public class OmsConditionController {
    @Autowired
    private OmsConditionService omsConditionService;

    /**
     * 检查约束条件
     * @param applyId 申请id
     * @param type 类型（因公，因私，延期回国）
     * @return
     */
    @GetMapping("/checkCondition")
    public Result checkCondition(String applyId, String type){
        List<Map<String, String>> result = omsConditionService.checkCondition(applyId, type);
        return Result.success(result);
    }

    /**
     * 提醒条件消息发送
     * @param applyId 申请id
     * @param type 类型（因公，因私，延期回国）
     * @return
     */
    @GetMapping("/remindCondition")
    public Result remindCondition(String applyId, String type){
        omsConditionService.remindCondition(applyId, type);
        return Result.success().setMsg("成功");
    }

    /**
     * 检查约束条件(选择人员)
     * @param a0100 人员id
     * @param type 类型（因公，因私，延期回国）
     * @return
     */
    @GetMapping("/checkConditionByA0100")
    public Result checkConditionByA0100(String a0100, String type){
        List<Map<String, String>> result = omsConditionService.checkConditionByA0100(a0100, type);
        return Result.success(result);
    }

    /**
     * 负面信息
     * @param a0100 人员id
     * @param abroadTime 出国时间
     * @return
     */
    @GetMapping("/selectNegativeInfo")
    public Result selectNegativeInfo(String a0100, @JsonFormat(pattern = "yyyy.MM.dd")
            @DateTimeFormat(pattern = "yyyy.MM.dd") Date abroadTime){
        String result = omsConditionService.selectNegativeInfo(a0100, abroadTime);
        return Result.success(result);
    }
}
