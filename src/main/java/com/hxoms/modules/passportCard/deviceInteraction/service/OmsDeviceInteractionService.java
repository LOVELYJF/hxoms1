package com.hxoms.modules.passportCard.deviceInteraction.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark;
import com.hxoms.modules.passportCard.deviceInteraction.entity.OmsCerDeviceInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.*;

import java.util.List;

public interface OmsDeviceInteractionService extends IService<OmsCerDeviceInfo> {

    /**
     * @Desc: 判断证件是否可存放于设备
     * @Author: wangyunquan
     * @Param: [surelyUnit, zjxs]
     * @Return: java.lang.String
     * @Date: 2020/10/10
     */
    boolean isStoreDevice(String surelyUnit,String zjxs);

    /**
     * @Desc: 设备注册
     * @Author: wangyunquan
     * @Param: [deviceInfo]
     * @Return: void
     * @Date: 2020/10/10
     */
    void deviceRegister(DeviceInfo deviceInfo);

    /**
     * @Desc: 身份验证
     * @Author: wangyunquan
     * @Param: [simpIdentityParam]
     * @Return: void
     * @Date: 2020/10/13
     */
    void verifyIdentity(SimpIdentityParam simpIdentityParam);

    /**
     * @Desc: 指纹验证，获取指纹模板
     * @Author: wangyunquan
     * @Param: [simpIdentityParam]
     * @Return: com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark
     * @Date: 2020/10/13
     */
    FingerMark verifyFingerMark(SimpIdentityParam simpIdentityParam);

    /**
     * @Desc: 查询可领取证件
     * @Author: wangyunquan
     * @Param: [qrCodeInfo]
     * @Return: com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.CerGetInfo
     * @Date: 2020/10/10
     */
    List<CerGetInfo> selectCanGetCer(QrCodeInfo qrCodeInfo);

    /**
     * @Desc: 证件出柜通知
     * @Author: wangyunquan
     * @Param: [cerGetNotice]
     * @Return: void
     * @Date: 2020/10/12
     */
    void cerGetNotice(CerGetNotice cerGetNotice);

    /**
     * @Desc: 查询证照信息集合
     * @Author: wangyunquan
     * @Param: [simpIdentityParam]
     * @Return: java.util.List<com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.SimpCerInfo>
     * @Date: 2020/10/13
     */
    List<SimpCerInfo> selectCanReturnCer(SimpIdentityParam simpIdentityParam);

    /**
     * @Desc: 证件入柜通知
     * @Author: wangyunquan
     * @Param: [cerReturnInfo]
     * @Return: void
     * @Date: 2020/10/13
     */
    void cerReturnNotice(CerReturnInfo cerReturnInfo);
}
