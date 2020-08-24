package com.hxoms.modules.passportCard.counterGet.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.CerGetTaskQueryParam;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.IdentityParam;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.OmsCerGetTaskListParam;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/conuterGet")
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
    @PostMapping("/verifyIdentity")
    public Result verifyIdentity(@RequestBody IdentityParam identityParam){
        omsCounterGetService.verifyIdentity(identityParam);
        return Result.success();
    }
    /**
     * @Desc: 验证指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/14
     */
    @PostMapping("/verifyFingerMark")
    public Result verifyFingerMark(@RequestBody IdentityParam identityParam){
        omsCounterGetService.verifyFingerMark(identityParam);
        return Result.success();
    }/**
     * @Desc: 验证二维码
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/14
     */
    @PostMapping("/verifyQRCode")
    public Result verifyQRCode(@RequestBody IdentityParam identityParam){
        omsCounterGetService.verifyQRCode(identityParam);
        return Result.success();
    }

    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [pageBean, cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/18
     */
    @PostMapping("/selectCanGetCer")
    public Result selectCanGetCer(@RequestBody CerGetTaskQueryParam cerGetTaskQueryParam){
        return Result.success(omsCounterGetService.selectCanGetCer(cerGetTaskQueryParam));
    }

    /**
     * @Desc: 确认领取证照，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @PostMapping("/updateCerGetTask")
    public Result updateToCerGet(@RequestBody OmsCerGetTaskListParam omsCerGetTaskListParam){
        omsCounterGetService.updateToCerGet(omsCerGetTaskListParam);
        return Result.success();
    }
    /**
     * @Desc: 确认领取审批表，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/17
     */
    @PostMapping("/updateToSpbGet")
    public Result updateToSpbGet(@RequestBody OmsCerGetTaskListParam omsCerGetTaskListParam){
        omsCounterGetService.updateToSpbGet(omsCerGetTaskListParam);
        return Result.success();
    }
}