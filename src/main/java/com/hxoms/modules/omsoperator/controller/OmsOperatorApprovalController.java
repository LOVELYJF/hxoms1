package com.hxoms.modules.omsoperator.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.omsoperator.service.OmsOperatorApprovalService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能描述: <br>
 * 〈经办人审核，审批〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/5/13 9:16
 */
@RestController
@RequestMapping("/operatorApproval")
public class OmsOperatorApprovalController {

    @Autowired
    OmsOperatorApprovalService operatorApprovalService;

    /**
     * 功能描述: <br>
     * 〈获取审批信息〉
     * @Param: [pageNum, pageSize, keyWord, orgId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/13 9:34
     */
    @PostMapping("/getApprovalList")
    public Result getApprovalList (Integer pageNum, Integer pageSize, String keyWord,@RequestParam(value ="orgId",required = false) List<String> orgId){
        PageInfo pageInfo = operatorApprovalService.getApprovalList(pageNum,pageSize,keyWord,orgId);
        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }

    /**
     * 功能描述: <br>
     * 〈通过身份证读取设备匹配经办人〉
     * @Param: [idcard]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/15 9:56
     */
    @GetMapping("/getOperatorByIdCard")
    public Result getOperatorByIdCard(String idnumber){
        CfUser operator = operatorApprovalService.getOperatorByIdCard(idnumber);
        return Result.success(operator);
    }

    /**
     * 功能描述: <br>
     * 〈经办人身份信息重置——保存〉
     * @Param: [operator]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/14 15:28
     */
    @PostMapping("/saveOperator")
    public Result saveOperator(CfUser operator){
        operatorApprovalService.saveOperator(operator);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈指纹登记审批通过〉
     * @Param: [operator]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/14 9:23
     */
    @PostMapping("/agreeToApproval")
    public Result agreeToApproval(CfUser operator){
        operatorApprovalService.agreeToApproval(operator);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈审批不通过〉
     * @Param: [operator]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:48
     */
    @PostMapping("/refuseToApproval")
    public Result refuseToApproval(String operatorId){
        operatorApprovalService.refuseToApproval(operatorId);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈审批页面——审批通过〉
     * @Param: [operatorId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/14 11:37
     */
    @PostMapping("/agreeToApprovalById")
    public Result agreeToApprovalById(String operatorId){
        operatorApprovalService.agreeToApprovalById(operatorId);
        return Result.success();

    }

    /**
     * 功能描述: <br>
     * 〈获取经办人审批信息〉
     * @Param: [operatorId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/9/14 20:14
     */
    @GetMapping("/getApprovaByOperatorId")
    public Result getApprovaByOperatorId(String operatorId){
        List<OmsOperatorApproval> omsOperatorApproval = operatorApprovalService.getApprovaByOperatorId(operatorId);
        return Result.success(omsOperatorApproval);
    }
}
