package com.hxoms.modules.omsregcadre.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import com.hxoms.modules.omsregcadre.service.OmsRegRevokeApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/OmsRegRevokeApply")
public class OmsRegRevokeApplyController {
    @Autowired
    private OmsRegRevokeApplyService revokeApplyService;

    /**
     * 查询申请撤销登记备案列表
     * @param revokeApply
     * @return
     */
    @PostMapping("/queryRevokeApplyList")
    public Result queryRevokeApplyList(Page page, OmsRegRevokeApply revokeApply) {
        try {
            IPage<OmsRegRevokeApply> revokeApplyList = revokeApplyService.queryRevokeApplyList(page, revokeApply);
            return Result.success(revokeApplyList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /**
     * 搜索可撤销备案人员
     * @param
     * @return
     */
    @PostMapping("/searchRevokeRegPerson")
    public Result searchRevokeRegPerson() throws ParseException {
        return Result.success(revokeApplyService.searchRevokeRegPerson());
    }




    /**
     * 添加撤销备案人员
     * @param
     * @return
     */
    @PostMapping("/insertRevokeRegPerson")
    public Result insertRevokeRegPerson(OmsRegProcpersonInfo regProcpersonInfo) throws ParseException {
        return Result.success(revokeApplyService.insertRevokeRegPerson(regProcpersonInfo));
    }

    /**
     * 撤销备案申请
     * @param revokeApply
     * @return
     * @throws ParseException
     */
    @PostMapping("/revokeRegApply")
    public Result revokeRegApply(OmsRegRevokeApply revokeApply) throws ParseException {
        return Result.success(revokeApplyService.revokeRegApply(revokeApply));
    }


    /**
     * 报处长审批
     * @param revokeApply
     * @return
     * @throws ParseException
     */
    @PostMapping("/reportLeaderApply")
    public Result reportLeaderApply(OmsRegRevokeApply revokeApply) throws ParseException {
        return Result.success(revokeApplyService.reportLeaderApply(revokeApply));
    }


    /**
     * 审批撤销备案人员(同意，不同意)
     * @param
     * @return
     */
    @PostMapping("/approvalRevokeRegPerson")
    public Result approvalRevokeRegPerson(OmsRegRevokeApproval regRevokeApproval,String applyIds) {
        if (!StringUtils.isEmpty(applyIds)
                && !StringUtils.isBlank(regRevokeApproval.getApprovalUser())
                && !StringUtils.isBlank(regRevokeApproval.getApprovalOpinion())
                && !StringUtils.isBlank(regRevokeApproval.getApprovalConclusion())) {
            return Result.success(revokeApplyService.approvalRevokeRegPerson(regRevokeApproval,applyIds));
        } else {
            return Result.error("必传参数为空");
        }
    }

}
