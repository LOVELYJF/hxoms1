package com.hxoms.modules.passportCard.initialise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CfCertificateMapper extends BaseMapper<CfCertificate>{


    /**
     * 查询所有证照信息
     * @return
     */

    List<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam);

    /**
     * @Desc: 通过身份证号码和名字查询人员
     * @Author: wangyunquan
     * @Param: [idNo, name]
     * @Return: java.util.List<com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo>
     * @Date: 2020/8/4
     */
    List<OmsRegProcpersoninfo> selectA0100ByQua(String idNo, String name);

    /**
     * @Desc: 通过A0100查询证件信息
     * @Author: wangyunquan
     * @Param: [a0100]
     * @Return: java.util.List<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>
     * @Date: 2020/8/4
     */
    List<CfCertificate> selectZJExisByQua(String a0100);

    /**
     * @Desc: 通过A0100查询出国境信息
     * @Author: wangyunquan
     * @Param: [a0100]
     * @Return: java.util.List<com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord>
     * @Date: 2020/8/4
     */
    List<OmsEntryexitRecord> selectRecordExisByQua(String a0100);


    /**
     * <b>功能描述: 选择注销证照查询</b>
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/5 10:29
     */
    List<Map<String, Object>> getCancellateLicense(Map<String, Object> map);

    /**
     * @Desc: 查询所有证照信息
     * @Author: wangyunquan
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>
     * @Date: 2020/8/4
     */
    List<CfCertificate> selectAllCertificate();

    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.modules.passportCard.initialise.entity.CfCertificate
     * @Date: 2020/8/4
     */
    CfCertificate selectCertificateInfo(CfCertificate cfCertificate);

    /**
     * @Desc: 查询登记备案人员信息
     * @Author: wangyunquan
     * @Param: [id, name, csrq]
     * @Return: java.util.List<com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo>
     * @Date: 2020/8/4
     */
    List<OmsRegProcpersoninfo> selectRegPerson(String id, String name, Date csrq);


    /**
     * <b>功能描述: 根据主键查询该人员的证照信息</b>
     * @Param: [a0100]
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/11 8:41
     */
    List<Map<String, Object>> getCfCertificateByA0100(String a0100);

    /**
     * @Desc: 未上缴证照统计
     * @Author: wangyunquan
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>
     * @Date: 2020/8/7
     */
    List<CfCertificateInfo> selectNotProvicdeCer();

    /**
     * @Desc: 已上缴未入库统计
     * @Author: wangyunquan
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>
     * @Date: 2020/8/7
     */
    List<CfCertificateInfo> selectProNotstorCer();

    /**
     * @Desc: 存疑证照统计
     * @Author: wangyunquan
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>
     * @Date: 2020/8/7
     */
    List<CfCertificateInfo> selectExceptionCer();

    /**
     * <b>功能描述: 查询过期证照信息</b>
     * @Param: [page,list,expiredQueryStartTime,expiredQueryEndTime,cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/17 14:16
     */
	List<Map<String, Object>> getTransferExpiredLicenseInfo(Map<String, Object> map);


	/**
	 * <b>功能描述: 查询柜台号码</b>
	 * @Param: [id]
	 * @Return: java.lang.String
	 * @Author: luoshuai
	 * @Date: 2020/8/18 10:13
	 */
    Integer selectCounterNum(String id);


    /**
     * <b>功能描述: （盘点）查询证照信息</b>
     * @Param: [map]
     * @Return: java.util.List<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>
     * @Author: luoshuai
     * @Date: 2020/8/19 15:08
     */
	List<CfCertificate> selectOmsCerInfo(Map<String, Object> map);
}