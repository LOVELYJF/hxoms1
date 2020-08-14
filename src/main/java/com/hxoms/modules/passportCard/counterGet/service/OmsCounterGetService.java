package com.hxoms.modules.passportCard.counterGet.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.IdentityParam;

public interface OmsCounterGetService extends IService<OmsCerGetTask> {

    /**
     * @Desc: 验证身份证
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    void verifyIdentity(IdentityParam identityParam);

    /**
     * @Desc: 验证指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    void verifyFingerMark(IdentityParam identityParam);

    /**
     * @Desc: 验证二维码
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    void verifyQRCode(IdentityParam identityParam);
}
