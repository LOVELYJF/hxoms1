package com.hxoms.modules.passportCard.deviceInteraction.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.IdentityParam;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.*;
import com.hxoms.modules.passportCard.deviceInteraction.service.OmsDeviceInteractionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Desc: TODO
 * @Author: wangyunquan
 * @Param:
 * @Return:
 * @Date: 2020/8/14
 */
@Api(tags = "设备(证照机)交互")
@RestController
@RequestMapping("/deviceInteraction")
public class OmsDeviceInteractionController {

    @Autowired
    private OmsDeviceInteractionService omsDeviceInteractionService;

    @Autowired
    private OmsCounterGetService omsCounterGetService;


    @ApiOperation(value = "设备注册")
    @PostMapping("/deviceReg")
    public Result deviceRegister(@RequestBody @Validated DeviceInfo deviceInfo){
        omsDeviceInteractionService.deviceRegister(deviceInfo);
        return  Result.success();
    }
    /**
     * @Desc: 验证身份证
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/14
     */
    @ApiOperation(value = "验证身份证")
    @PostMapping("/verifyIdentity")
    public Result verifyIdentity(@RequestBody @Validated SimpIdentityParam simpIdentityParam){
        omsDeviceInteractionService.verifyIdentity(simpIdentityParam);
        return Result.success();
    }

    /**
     * @Desc: 验证指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark>
     * @Date: 2020/9/8
     */
    @ApiOperation(value = "验证指纹")
    @PostMapping("/verifyFingerMark")
    public Result<FingerMark> verifyFingerMark(@RequestBody @Validated IdentityParam identityParam){
        return Result.success(omsCounterGetService.verifyFingerMark(identityParam));
    }

    /**
     * @Desc: 查询可领取证件
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark>
     * @Date: 2020/9/8
     */
    @ApiOperation(value = "查询可领取证件")
    @PostMapping("/selectCanGetCer")
    public Result<RequestList<CerGetInfo>> selectCanGetCer(@RequestBody @Validated QrCodeInfo qrCodeInfo){
        return Result.success(omsDeviceInteractionService.selectCanGetCer(qrCodeInfo));
    }

   /**
    * @Desc: 证件已领取通知
    * @Author: wangyunquan
    * @Param: [cerGetNotice]
    * @Return: com.hxoms.common.utils.Result
    * @Date: 2020/10/12
    */
    @ApiOperation(value = "证件已领取通知")
    @PostMapping("/cerGetNotice")
    public Result cerGetNotice(@RequestBody @Validated CerGetNotice cerGetNotice){
        omsDeviceInteractionService.cerGetNotice(cerGetNotice);
        return Result.success();
    }
    /**
     * @Desc: 通过身份证查询可入柜证件
     * @Author: wangyunquan
     * @Param: [cerGetNotice]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/10/12
     */
  /*  @ApiOperation(value = "通过身份证查询可入柜证件")
    @PostMapping("/selectCanReturnCer")
    public Result selectCanReturnCer(@RequestBody @Validated CerGetNotice cerGetNotice){
        return Result.success(omsDeviceInteractionService.selectCanReturnCer(cerGetNotice));
    }*/

}
