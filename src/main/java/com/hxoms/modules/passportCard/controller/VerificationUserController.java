package com.hxoms.modules.passportCard.controller;


import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.service.VerificationUserService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 经办人信息对比模块
 */
@RestController
@RequestMapping("/verificationUser")
public class VerificationUserController {


    @Autowired
    private VerificationUserService verificationUserService;

    /**
     * 对比经办人身份
     * 通过身份证进行对比
     * @return
     */
    @GetMapping("/verificationIdNum")
    public Result verificationIdNum(String idNum){

        CfUser cfUser=verificationUserService.verificationIdNum(idNum);

        return Result.success(cfUser);

    }

    @GetMapping("/verificationFingerprint")
    public Result verificationFingerprint(String idNum,String fingerprint){

       boolean flag =  verificationUserService.verificationFingerprint(idNum,fingerprint);

        return Result.success(flag);

    }

}
