package com.hxoms.modules.passportCard.counterReturn.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.ReturnCertificateInfo;
import com.hxoms.modules.passportCard.counterReturn.service.OmsCounterReturnService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateSeeRes;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    @GetMapping("/readCerInfo")
    public Result<ReturnCertificateInfo> readCerInfo(CfCertificate cfCertificate){
        return Result.success(omsCounterReturnService.readCerInfo(cfCertificate));
    }

    /**
     * @Desc: 查询因私出国申请信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/20
     */
    @ApiOperation(value = "查询因私出国申请信息")
    @GetMapping("/selectPriApplyInfo")
    public Result<OmsPriApply> selectPriApplyInfo(CfCertificate cfCertificate){
        return  Result.success(omsCounterReturnService.selectPriApplyInfo(cfCertificate));
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
    public Result updatePriForFillReport(OmsPriApply omsPriApply){
        omsCounterReturnService.updatePriForFillReport(omsPriApply);
        return  Result.success();
    }

    /**
     * @Desc: 归还证照
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/20
     */
    @ApiOperation(value = "归还证照")
    @PostMapping("/returnCertificate")
    public Result returnCertificate(CfCertificate cfCertificate){
        omsCounterReturnService.returnCertificate(cfCertificate);
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
