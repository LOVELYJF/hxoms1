package com.hxoms.modules.passportCard.counterGet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.IdentityParam;
import com.hxoms.modules.passportCard.counterGet.mapper.OmsCerGetTaskMapper;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OmsCounterGetServiceImpl extends ServiceImpl<OmsCerGetTaskMapper, OmsCerGetTask> implements OmsCounterGetService {

    @Autowired
    private OmsCerGetTaskMapper omsCerGetTaskMapper;

    /**
     * @param identityParam
     * @Desc: 验证身份证
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    @Override
    public void verifyIdentity(IdentityParam identityParam) {
        List<CfUser> userList=omsCerGetTaskMapper.selectUserByQua(identityParam);
        if(userList.size()==0)
            throw new CustomMessageException("系统无此人，请核实！");
    }

    /**
     * @param identityParam
     * @Desc: 验证指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    @Override
    public void verifyFingerMark(IdentityParam identityParam) {
        List<CfUser> userList=omsCerGetTaskMapper.selectUserByQua(identityParam);
        //验证指纹
    }

    /**
     * @param identityParam
     * @Desc: 验证二维码
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    @Override
    public void verifyQRCode(IdentityParam identityParam) {

    }
}
