package com.hxoms.modules.passportCard.counterGet.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.IdentityParam;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result verifyIdentity(IdentityParam identityParam){
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
    public Result verifyFingerMark(IdentityParam identityParam){
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
    public Result verifyQRCode(IdentityParam identityParam){
        omsCounterGetService.verifyQRCode(identityParam);
        return Result.success();
    }
}
