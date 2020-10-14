package com.hxoms.modules.passportCard.counterGet.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * @Desc: TODO
 * @Author: wangyunquan
 * @Param:
 * @Return:
 * @Date: 2020/8/14
 */
@Api(tags = "柜台领取证照")
@RestController
@RequestMapping("/conuterGet")
@Validated
public class OmsConuterGetController {


    @Autowired
    private OmsCounterGetService omsCounterGetService;

    /**
     * @Desc: 验证身份证
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/14
     */
    @ApiOperation(value = "验证身份证")
    @PostMapping("/verifyIdentity")
    public Result verifyIdentity(@RequestBody @Validated IdentityParam identityParam){
        omsCounterGetService.verifyIdentity(identityParam);
        return Result.success();
    }
    /**
     * @Desc: 验证左手指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark>
     * @Date: 2020/8/14
     */

    @ApiOperation(value = "验证左手指纹")
    @PostMapping("/verifyLeftFingerMark")
    public Result<FingerMark> verifyLeftFingerMark(@RequestBody @Validated IdentityParam identityParam){
        return Result.success(omsCounterGetService.verifyLeftFingerMark(identityParam));
    }
    /**
     * @Desc: 验证右手指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark>
     * @Date: 2020/8/14
     */
    @ApiOperation(value = "验证右手指纹")
    @PostMapping("/verifyRightFingerMark")
    public Result<FingerMark> verifyRightFingerMark(@RequestBody @Validated IdentityParam identityParam){
        return Result.success(omsCounterGetService.verifyRightFingerMark(identityParam));
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
     * @Desc: 验证二维码
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/14
     */
    @ApiOperation(value = "验证二维码")
    @ApiImplicitParam(value = "二维码URL",name = "qrCodeUrl",required = true,paramType = "query")
    @PostMapping("/verifyQRCode")
    public Result verifyQRCode(@NotBlank(message = "二维码URL不能为空") String qrCodeUrl){
        omsCounterGetService.verifyQRCode(qrCodeUrl);
        return Result.success();
    }

    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.CerGetTaskInfo>>
     * @Date: 2020/8/18
     */

    @ApiOperation(value = "查询可领取证照")
    @GetMapping("/selectCanGetCer")
    public Result<List<CerGetTaskInfo>> selectCanGetCer(@Validated CerGetTaskQueryParam cerGetTaskQueryParam){
        return Result.success(omsCounterGetService.selectCanGetCer(cerGetTaskQueryParam));
    }

    /**
     * @Desc: 确认领取证照，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @ApiOperation(value = "确认领取证照，保存签名")
    @PostMapping("/updateCerGetTask")
    public Result updateToCerGet(@RequestBody @Validated RequestList<GetConfirm> requestList){
        omsCounterGetService.updateToCerGet(requestList.getList());
        return Result.success();
    }
    /**
     * @Desc: 确认领取审批表，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @ApiOperation(value = "确认领取审批表，保存签名")
    @PostMapping("/updateToSpbGet")
    public Result updateToSpbGet(@RequestBody @Validated RequestList<GetSpb> requestList){
        omsCounterGetService.updateToSpbGet(requestList.getList());
        return Result.success();
    }
}
