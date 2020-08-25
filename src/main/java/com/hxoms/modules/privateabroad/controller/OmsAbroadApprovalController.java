package com.hxoms.modules.privateabroad.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.privateabroad.entity.OmsAbroadApproval;
import com.hxoms.modules.privateabroad.service.OmsAbroadApprovalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @desc: 出国审批管理
 * @author: lijing
 * @date: 2020-06-15
 */
@Api(tags="出国审批管理")
@RestController
@RequestMapping("/omsAbroadApproval")
public class OmsAbroadApprovalController {
    @Autowired
    private OmsAbroadApprovalService omsAbroadApprovalService;


    /**
     * 审批列表
     * @param applyId  申请id
     * @param type 类型（因公 因私 延期回国）
     * @return
     * @throws Exception
     */
    @ApiOperation(value="审批列表", notes="审批列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型（因公 因私 延期回国）", required = true, dataType = "String"),
    })
    @GetMapping("/selectOmsAbroadApprovalList")
    public Result selectOmsAbroadApprovalList(String applyId, String type) throws Exception {
        List<OmsAbroadApproval> omsAbroadApprovalVOS = omsAbroadApprovalService.selectOmsAbroadApprovalList(applyId, type);
        return Result.success(omsAbroadApprovalVOS);
    }

}
