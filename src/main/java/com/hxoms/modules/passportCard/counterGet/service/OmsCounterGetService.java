package com.hxoms.modules.passportCard.counterGet.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.CerGetTaskQueryParam;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.IdentityParam;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.OmsCerGetTaskListParam;

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

    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [ cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/18
     */
    PageBean selectCanGetCer(CerGetTaskQueryParam cerGetTaskQueryParam);

    /**
     * @Desc: 确认领取证照，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: void
     * @Date: 2020/8/17
     */
    void updateToCerGet(OmsCerGetTaskListParam omsCerGetTaskListParam);

    /**
     * @Desc: 确认领取审批表，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: void
     * @Date: 2020/8/17
     */
    void updateToSpbGet(OmsCerGetTaskListParam omsCerGetTaskListParam);
}
