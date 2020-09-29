package com.hxoms.modules.omsregcadre.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegRevokeApplyIPagParam;
import com.hxoms.modules.omsregcadre.service.OmsRegRevokeApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/omsRegRevokeApply")
public class OmsRegRevokeApplyController {
    @Autowired
    private OmsRegRevokeApplyService revokeApplyService;

    /**
     * 查询申请撤销登记备案列表
     * @param revokeApplyIPagParam
     * @return
     */
    @PostMapping("/queryRevokeApplyList")
    public Result queryRevokeApplyList(OmsRegRevokeApplyIPagParam revokeApplyIPagParam) {
        try {
            PageInfo<OmsRegRevokeapply> revokeApplyList = revokeApplyService.queryRevokeApplyList(revokeApplyIPagParam);
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
        return revokeApplyService.searchRevokeRegPerson();
    }




    /**
     * 添加撤销备案人员
     * @param
     * @return
     */
    @PostMapping("/insertRevokeRegPerson")
    public Result insertRevokeRegPerson(OmsRegRevokeapply revokeApply){
        return Result.success(revokeApplyService.insertRevokeRegPerson(revokeApply));
    }


    /**
     * 删除撤销备案人员
     * @param
     * @return
     */
    @PostMapping("/deleteRevokeRegPerson")
    public Result deleteRevokeRegPerson(String id){
        if(id==null)
            return Result.error("参数不能为空！");
        return Result.success(revokeApplyService.deleteRevokeRegPerson(id));
    }


    /**
     * 搜索撤销登记备案人员根据姓名，身份证号码
     * @return
     * @throws ParseException
     */
    @GetMapping("/searchRevokeRegPersonList")
    public Result searchRevokeRegPersonList(OmsRegProcpersoninfo regProcpersonInfo){
        return Result.success(revokeApplyService.searchRevokeRegPersonList(regProcpersonInfo));
    }


    /**
     * 修改备案申请状态
     * 1.申请 2.受理 3.撤销 4.已备案 5.处领导审批 6.部领导审批 7.拒批
     * @param revokeApply
     * @return
     * @throws ParseException
     */
    @PostMapping("/updateApplyStatus")
    public Result updateApplyStatus(OmsRegRevokeapply revokeApply){
        return Result.success(revokeApplyService.updateApplyStatus(revokeApply));
    }


    /**
     * 录入处领导审批结果（上报干部监督处）
     * @param
     * @return
     */
    @PostMapping("/updateApplyStatusByCLD")
    public Result updateApplyStatusByCLD(String status, String applyIds) {
        return revokeApplyService.updateApplyStatusByCLD(status,applyIds);
    }


    /**
     * 录入部领导审批结果
     * 审批撤销备案人员(同意，不同意)
     * @param
     * @return
     */
    @PostMapping("/approvalRevokeRegPerson")
    public Result approvalRevokeRegPerson(OmsRegRevokeapproval regRevokeApproval, String applyIds) {
        if (!StringUtils.isEmpty(applyIds)
                && !StringUtils.isBlank(regRevokeApproval.getApprovalOpinion())
                && !StringUtils.isBlank(regRevokeApproval.getApprovalConclusion())) {
            return Result.success(revokeApplyService.approvalRevokeRegPerson(regRevokeApproval,applyIds));
        } else {
            return Result.error("必传参数为空");
        }
    }

}
