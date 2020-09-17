package com.hxoms.modules.passportCard.counterReturn.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.counterReturn.service.OmsCounterReturnService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateSeeRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @Desc: TODO
 * @Author: wangyunquan
 * @Param:
 * @Return:
 * @Date: 2020/8/14
 */
@Api(tags = "柜台归还证照")
@RestController
@RequestMapping("/counterReturn")
public class OmsCounterReturnController {
    @Autowired
    private OmsCounterReturnService omsCounterReturnService;

    /**
     * @Desc: 读取证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/20
     */
    @ApiOperation(value = "读取证照信息")
    @PostMapping("/readCerInfo")
    public Result<CerAndPersonInfo> readCerInfo(@RequestBody @Validated ReadCerInfo readCerInfo){
        return Result.success(omsCounterReturnService.readCerInfo(readCerInfo));
    }

    /**
     * @Desc: 查询因私出国申请信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.PriApplyInfo>
     * @Date: 2020/8/20
     */

    @ApiOperation(value = "查询因私出国申请信息")
    @GetMapping("/selectPriApplyInfo")
    public Result<PriApplyInfo> selectPriApplyInfo(@Validated PriApplyQueryParams priApplyQueryParams){
        return  Result.success(omsCounterReturnService.selectPriApplyInfo(priApplyQueryParams));
    }

    /**
     * @Desc: 填写因私有关情况报告
     * @Author: wangyunquan
     * @Param: [omsPriApply]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/20
     */
    @ApiOperation(value = "填写因私有关情况报告")
    @PostMapping("/updatePriForFillReport")
    public Result updatePriForFillReport(PriApplyInfo priApplyInfo){
        omsCounterReturnService.updatePriForFillReport(priApplyInfo);
        return  Result.success();
    }

    /**
     * @Desc: 归还证照
     * @Author: wangyunquan
     * @Param: [returnCerInfo]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/20
     */
    @ApiOperation(value = "归还证照")
    @PostMapping("/returnCertificate")
    public Result returnCertificate(ReturnCerInfo returnCerInfo){
        omsCounterReturnService.returnCertificate(returnCerInfo);
        return Result.success();
    }
    
    
    /**
     * @Desc: 因私出国查看证照
     * @Author: wuyezhen
     * @Date: 2020/9/04
     */
    @GetMapping("/examineCertificate")
    @ApiOperation(value="因私出国查看证照", notes="因私出国查看证照")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "passportNum", value = "证件号）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "a0100", value = "人员主键）", required = true, dataType = "String")
    
    })
    public Result<CfCertificateSeeRes> examineCertificate(String passportNum,String a0100){
      
        return Result.success(omsCounterReturnService.examineCertificate(passportNum, a0100));
    }
}
