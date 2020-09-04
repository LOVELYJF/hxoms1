package com.hxoms.modules.privateabroad.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturn;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturnVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApprovalReturnIPageParam;
import com.hxoms.modules.privateabroad.service.OmsAbroadApprovalService;
import com.hxoms.modules.privateabroad.service.OmsApprovalReturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc: 因私出国审批回收管理
 * @author: lijing
 * @date: 2020-06-16
 */
@Api(tags="因私出国审批回收管理")
@RestController
@RequestMapping("/omsApprovalReturn")
public class OmsApprovalReturnController {
    @Autowired
    private OmsApprovalReturnService omsApprovalReturnService;

    /**
     * 因私出国审批表回收登记
     * @param omsApprovalReturn
     * @return
     * @throws Exception
     */
    @ApiOperation(value="因私出国审批表回收登记", notes="因私出国审批表回收登记")
    @PostMapping("/savePriApprovalReturn")
    public Result savePriApprovalReturn(OmsApprovalReturn omsApprovalReturn) throws Exception {
        String result = omsApprovalReturnService.savePriApprovalReturn(omsApprovalReturn);
        return Result.success().setMsg(result);
    }

    /**
     * 因私出国审批表回收登记删除
     * @param omsApprovalReturn
     * @return
     * @throws Exception
     */
    @ApiOperation(value="因私出国审批表回收登记删除", notes="因私出国审批表回收登记删除")
    @PostMapping("/deletePriApprovalReturn")
    public Result deletePriApprovalReturn(OmsApprovalReturn omsApprovalReturn) throws Exception {
        String result = omsApprovalReturnService.deletePriApprovalReturn(omsApprovalReturn);
        return Result.success().setMsg(result);
    }

    /**
     * 因私出国审批表回收登记列表
     * @param omsPriApprovalReturnIPageParam
     * @return
     * @throws Exception
     */
    @ApiOperation(value="因私出国审批表回收登记列表", notes="因私出国审批表回收登记列表")
    @GetMapping("/selectPriApprovalReturnPagelist")
    public Result selectPriApprovalReturnPagelist(OmsPriApprovalReturnIPageParam omsPriApprovalReturnIPageParam) throws Exception {
        PageInfo<OmsApprovalReturnVO> omsApprovalReturnVOPageInfo = omsApprovalReturnService.selectPriApprovalReturnPagelist(omsPriApprovalReturnIPageParam);
        return Result.success(omsApprovalReturnVOPageInfo);
    }
    
    
    /**
     * 导出因私出国审批表回收登记列表
     * @param omsPriApprovalReturnIPageParam
     * @return
     * @throws Exception
     */
    @ApiOperation(value="导出因私出国审批表回收登记列表", notes="导出因私出国审批表回收登记列表")
    @GetMapping("/exportPriApprovalReturn")
    public void exportPriApprovalReturn(OmsPriApprovalReturnIPageParam omsPriApprovalReturnIPageParam,HttpServletResponse response) throws Exception {
                omsApprovalReturnService.exportPriApprovalReturn(omsPriApprovalReturnIPageParam, response);
       
    }

    /**
     * 查询回收登记详情
     * @param applyId
     * @return
     * @throws Exception
     */
    @ApiOperation(value="查询回收登记详情", notes="查询回收登记详情")
    @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String")
    @GetMapping("/selectPriApprovalReturnDestail")
    public Result selectPriApprovalReturnDestail(String applyId) throws Exception {
        OmsApprovalReturn omsApprovalReturn = omsApprovalReturnService.selectPriApprovalReturnDestail(applyId);
        return Result.success(omsApprovalReturn);
    }
}
