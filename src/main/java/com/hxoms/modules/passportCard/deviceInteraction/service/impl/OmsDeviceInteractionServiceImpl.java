package com.hxoms.modules.passportCard.deviceInteraction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.passportCard.deviceInteraction.entity.OmsCerDeviceInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.CerGetInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.DeviceInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.QrCodeInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.StorageInfo;
import com.hxoms.modules.passportCard.deviceInteraction.mapper.OmsCerDeviceInfoMapper;
import com.hxoms.modules.passportCard.deviceInteraction.service.OmsDeviceInteractionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OmsDeviceInteractionServiceImpl extends ServiceImpl<OmsCerDeviceInfoMapper, OmsCerDeviceInfo> implements OmsDeviceInteractionService {

    @Autowired OmsCerDeviceInfoMapper omsCerDeviceInfoMapper;
    /**
     * @Desc: 判断证件是否可存放于设备
     * @Author: wangyunquan
     * @Param: [surelyUnit, zjxs]
     * @Return: java.lang.String
     * @Date: 2020/10/10
     */
    public boolean isStoreDevice(String surelyUnit,String zjxs){
        if(StringUtils.isBlank(surelyUnit)||StringUtils.isBlank(zjxs))
            throw  new CustomMessageException("参数错误，请核实！");
        return Boolean.valueOf(omsCerDeviceInfoMapper.selectIsStoreDevice(surelyUnit, zjxs)).booleanValue();
    }

    /**
     * @Desc: 设备注册
     * @Author: wangyunquan
     * @Param: [deviceInfo]
     * @Return: void
     * @Date: 2020/10/10
     */
    @Override
    public void deviceRegister(DeviceInfo deviceInfo) {
        String isExist=omsCerDeviceInfoMapper.selectbyDeviceSn(deviceInfo.getDeviceSn());
        if(isExist!=null)
            throw  new CustomMessageException("设备已注册，不能重复操作，请核实！");
        StorageInfo storageInfo = deviceInfo.getStorageInfo();
        OmsCerDeviceInfo omsCerDeviceInfo=new OmsCerDeviceInfo();
        BeanUtils.copyProperties(deviceInfo,omsCerDeviceInfo);
        BeanUtils.copyProperties(storageInfo,omsCerDeviceInfo);
        omsCerDeviceInfo.setId(UUIDGenerator.getPrimaryKey());
        omsCerDeviceInfo.setCreateTime(new Date());
        if(omsCerDeviceInfoMapper.insert(omsCerDeviceInfo)==0)
            throw  new CustomMessageException("设备注册失败，请联系工作人员！");
    }

    /**
     * @Desc: 查询可领取证件
     * @Author: wangyunquan
     * @Param: [qrCodeInfo]
     * @Return: com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.CerGetInfo
     * @Date: 2020/10/10
     */
    @Override
    public List<CerGetInfo> selectCanGetCer(QrCodeInfo qrCodeInfo) {
        //校验领取人与二维码打印者是否属同一家单位
        if(!Boolean.valueOf(omsCerDeviceInfoMapper.validateUser(qrCodeInfo)).booleanValue())
            throw new CustomMessageException("领取人与被领取人不是同单位，不能领取，请核实！");
        return omsCerDeviceInfoMapper.selectCanGetCer(qrCodeInfo);
    }
}
