package com.hxoms.modules.passportCard.counterReturn.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.counterReturn.service.OmsCounterReturnService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
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
    @GetMapping("/readCerInfo")
    public Result readCerInfo(CfCertificate cfCertificate){
        return Result.success(omsCounterReturnService.readCerInfo(cfCertificate));
    }

    /**
     * @Desc: 查询因私出国申请信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/20
     */
    @GetMapping("/selectPriApplyInfo")
    public Result selectPriApplyInfo(CfCertificate cfCertificate){
        return  Result.success(omsCounterReturnService.selectPriApplyInfo(cfCertificate));
    }

    /**
     * @Desc: 填写因私有关情况报告
     * @Author: wangyunquan
     * @Param: [omsPriApply]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/20
     */
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
    @PostMapping("/returnCertificate")
    public Result returnCertificate(CfCertificate cfCertificate){
        omsCounterReturnService.returnCertificate(cfCertificate);
        return Result.success();
    }
}
