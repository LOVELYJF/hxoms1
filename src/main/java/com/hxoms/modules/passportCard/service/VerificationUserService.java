package com.hxoms.modules.passportCard.service;


import com.hxoms.modules.sysUser.entity.CfUser;

public interface VerificationUserService {

    CfUser verificationIdNum(String idNum);

    boolean verificationFingerprint(String idNum,String fingerprint);

}
