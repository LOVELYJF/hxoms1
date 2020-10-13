package com.hxoms.modules.passportCard.deviceInteraction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.deviceInteraction.entity.OmsCerDeviceInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.CerGetInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.CerInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.QrCodeInfo;

import java.util.List;

public interface OmsCerDeviceInfoMapper extends BaseMapper<OmsCerDeviceInfo> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_device_info
     *
     * @mbg.generated Sat Oct 10 10:32:20 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_device_info
     *
     * @mbg.generated Sat Oct 10 10:32:20 CST 2020
     */
    int insert(OmsCerDeviceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_device_info
     *
     * @mbg.generated Sat Oct 10 10:32:20 CST 2020
     */
    int insertSelective(OmsCerDeviceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_device_info
     *
     * @mbg.generated Sat Oct 10 10:32:20 CST 2020
     */
    OmsCerDeviceInfo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_device_info
     *
     * @mbg.generated Sat Oct 10 10:32:20 CST 2020
     */
    int updateByPrimaryKeySelective(OmsCerDeviceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_device_info
     *
     * @mbg.generated Sat Oct 10 10:32:20 CST 2020
     */
    int updateByPrimaryKey(OmsCerDeviceInfo record);

    /**
     * @Desc: 判断证件是否可存放于设备
     * @Author: wangyunquan
     * @Param: [surelyUnit, zjxs]
     * @Return: java.lang.String
     * @Date: 2020/10/10
     */
    String selectIsStoreDevice(String surelyUnit, String zjxs);

    /**
     * @Desc: 查询设备是否已注册
     * @Author: wangyunquan
     * @Param: [deviceSn]
     * @Return: java.lang.String
     * @Date: 2020/10/10
     */
    String selectbyDeviceSn(String deviceSn);

    /**
     * @Desc: 验证领取人
     * @Author: wangyunquan
     * @Param: [qrCodeInfo]
     * @Return: java.lang.String
     * @Date: 2020/10/10
     */
    String validateUser(QrCodeInfo qrCodeInfo);



    /**
     * @Desc: 查询可领取证件
     * @Author: wangyunquan
     * @Param: [qrCodeInfo]
     * @Return: java.util.List<com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.CerGetInfo>
     * @Date: 2020/10/10
     */
    List<CerGetInfo> selectCanGetCer(QrCodeInfo qrCodeInfo);

    /**
     * @Desc: 查询用户id
     * @Author: wangyunquan
     * @Param: [idNo, name]
     * @Return: java.lang.String
     * @Date: 2020/10/12
     */
    String selectUserId(String idNo, String name);

    /**
     * @Desc: 查询证件信息
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: CerInfo
     * @Date: 2020/10/12
     */
    CerInfo selectCerInfo(String id);
}