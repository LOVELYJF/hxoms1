package com.hxoms.modules.passportCard.counterReceive.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.modules.passportCard.counterReceive.service.VerificationUserService;
import com.hxoms.modules.sysUser.entity.CfUser;
import com.hxoms.modules.sysUser.mapper.CfUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VerificationUserServiceImpl implements VerificationUserService {



    @Autowired
    private CfUserMapper cfUserMapper;

    /**
     * 对比经办人身份
     * 通过身份证进行对比
     * @return
     */
    @Override
    public CfUser verificationIdNum(String idNum) {

       CfUser cfUser= cfUserMapper.getOperatorApprovalByIdCard(idNum, Constants.USER_STATUS[1],Constants.USER_TYPES[6]);

        if (cfUser == null){

            throw new CustomMessageException("身份与经办人身份不符");

        }

       return cfUser;
    }

    @Override
    public boolean verificationFingerprint(String idNum, String fingerprint) {


        return false;

    }


}
