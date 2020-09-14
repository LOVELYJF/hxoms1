package com.hxoms.modules.passportCard.counterGet.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.*;

import java.util.List;

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
     * @Desc: 验证左手指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark
     * @Date: 2020/9/8
     */
    FingerMark verifyLeftFingerMark(IdentityParam identityParam);

    /**
     * @Desc: 验证右手指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark
     * @Date: 2020/9/8
     */
    FingerMark verifyRightFingerMark(IdentityParam identityParam);
    /**
     * @Desc: 验证指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark
     * @Date: 2020/8/14
     */

    FingerMark verifyFingerMark(IdentityParam identityParam);

    /**
     * @Desc: 验证二维码
     * @Author: wangyunquan
     * @Param: [qrCodeUrl]
     * @Return: void
     * @Date: 2020/8/14
     */
    void verifyQRCode(String qrCodeUrl);

    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [ cerGetTaskQueryParam]
     * @Return: java.util.List<com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.CerGetTaskInfo>
     * @Date: 2020/8/18
     */

    List<CerGetTaskInfo> selectCanGetCer(CerGetTaskQueryParam cerGetTaskQueryParam);

    /**
     * @Desc: 确认领取证照，保存签名
     * @Author: wangyunquan
     * @Param: [getConfirmList]
     * @Return: void
     * @Date: 2020/8/17
     */
    void updateToCerGet(List<GetConfirm> getConfirmList);

    /**
     * @Desc: 确认领取审批表，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: void
     * @Date: 2020/8/17
     */
    void updateToSpbGet(List<GetSpb> getSpbList);

}
