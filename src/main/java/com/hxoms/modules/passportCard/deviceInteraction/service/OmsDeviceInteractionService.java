package com.hxoms.modules.passportCard.deviceInteraction.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.passportCard.deviceInteraction.entity.OmsCerDeviceInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.CerGetInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.CerGetNotice;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.DeviceInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.QrCodeInfo;

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
     * @Desc: 查询可领取证件
     * @Author: wangyunquan
     * @Param: [qrCodeInfo]
     * @Return: com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.CerGetInfo
     * @Date: 2020/10/10
     */
    List<CerGetInfo> selectCanGetCer(QrCodeInfo qrCodeInfo);

    /**
     * @Desc: 证件已领取通知
     * @Author: wangyunquan
     * @Param: [cerGetNotice]
     * @Return: void
     * @Date: 2020/10/12
     */
    void cerGetNotice(CerGetNotice cerGetNotice);
}
