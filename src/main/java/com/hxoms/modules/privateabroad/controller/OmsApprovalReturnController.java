package com.hxoms.modules.privateabroad.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturn;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturnVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApprovalReturnIPageParam;
import com.hxoms.modules.privateabroad.service.OmsAbroadApprovalService;
import com.hxoms.modules.privateabroad.service.OmsApprovalReturnService;
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
    @GetMapping("/selectPriApprovalReturnPagelist")
    public Result selectPriApprovalReturnPagelist(OmsPriApprovalReturnIPageParam omsPriApprovalReturnIPageParam) throws Exception {
        PageInfo<OmsApprovalReturnVO> omsApprovalReturnVOPageInfo = omsApprovalReturnService.selectPriApprovalReturnPagelist(omsPriApprovalReturnIPageParam);
        return Result.success(omsApprovalReturnVOPageInfo);
    }

    /**
     * 查询回收登记详情
     * @param applyId
     * @return
     * @throws Exception
     */
    @GetMapping("/selectPriApprovalReturnDestail")
    public Result selectPriApprovalReturnDestail(String applyId) throws Exception {
        OmsApprovalReturn omsApprovalReturn = omsApprovalReturnService.selectPriApprovalReturnDestail(applyId);
        return Result.success(omsApprovalReturn);
    }
}
