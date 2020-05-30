package com.hxoms.modules.condition.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.condition.service.OmsConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
