package com.hxoms.modules.selfestimate.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitem;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemResult;
import com.hxoms.modules.selfestimate.entity.paramentity.ResultListParam;
import com.hxoms.modules.selfestimate.service.OmsSelfestimateResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @desc: 自评项目结果维护
 * @author: lijing
 * @date: 2020-05-25
 */
@RestController
@RequestMapping("/omsSelfestimateResult")
public class OmsSelfestimateResultController {
    @Autowired
    private OmsSelfestimateResultService omsSelfestimateResultService;

    /**
     * 插入自评结果
     * @param omsSelfestimateResultitems
     * @return
     */
    @PostMapping("insertOrUpdateResult")
    public Result insertOrUpdateResult(List<OmsSelfestimateResultitem> omsSelfestimateResultitems){
        String result = omsSelfestimateResultService.insertOrUpdateResult(omsSelfestimateResultitems);
        return Result.success().setMsg(result);
    }

    /**
     * 查询结果集
     * @param resultListParam
     * @return
     */
    @GetMapping("selectResultList")
    public Result selectResultList(ResultListParam resultListParam){
        OmsSelfestimateResultitemResult omsSelfestimateResultitemResult = omsSelfestimateResultService.selectResultList(resultListParam);
        return Result.success(omsSelfestimateResultitemResult);
    }
}
