package com.hxoms.modules.passportCard.initialise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateSeeRes;
import com.hxoms.modules.passportCard.initialise.entity.exportExcel.ExportNotProvicdeCer;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.ImportInterface;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.RegProcpersoninfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CfCertificateMapper extends BaseMapper<CfCertificate>{
	/**
	 * @Desc: 初始化证照，导出未上缴证照统计
	 * @Author: wuqingfan
	 * @Param: [ids]
	 * @Return: List<ExportNotProvicdeCer>
	 * @Date: 2020/9/10
	 */
	List<ExportNotProvicdeCer>  exportNotProvicdeCer(@Param("ids") List<String> ids);

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
     * @Param: [omsId]
     * @Return: java.util.List<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>
     * @Date: 2020/8/4
     */
    List<CfCertificate> selectZJExisByQua(String omsId);

    /**
     * @Desc: 通过A0100查询出国境信息
     * @Author: wangyunquan
     * @Param: [omsId]
     * @Return: java.util.List<com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord>
     * @Date: 2020/8/4
     */
    List<OmsEntryexitRecord> selectRecordExisByQua(String omsId);


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
	 * @Return: java.util.List<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.ImportInterface>
     * @Date: 2020/8/4
     */
    List<ImportInterface> selectAllCertificate();

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
	 * @Return: java.util.List<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.RegProcpersoninfo>
     * @Date: 2020/8/4
     */

    List<RegProcpersoninfo> selectRegPerson(String id, String name, Date csrq);


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


	/**
	 * <b>功能描述: 查询所有的柜子集合</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	List<String> getCerLicenseMachine();


	/**
	 * <b>功能描述: 存取记录</b>
	 * @Param: [cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	List<Map<String, Object>> getCerAccessRecord(Map<String, Object> map);

	/**
	 * @Desc: 查询催缴任务
	 * @Author: wangyunquan
	 * @Param: [omsId]
	 * @Return: java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection>
	 * @Date: 2020/8/28
	 */
    List<CfCertificateCollection> selectCjTask(String omsId);

    /**
     * @Desc: 查询催缴所需信息
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo
     * @Date: 2020/8/28
     */
	CfCertificateInfo selectCjNeedInfo(String id);

	/**
	 * @Desc: 查询用户类型
	 * @Author: wangyunquan
	 * @Param: [id]
	 * @Return: java.lang.String
	 * @Date: 2020/8/29
	 */
	String selectUserType(String id);
	
	
	 /**
     * 根据证件号查询证件信息
     * @param passportNum
     * @param a0100
     * @return
     * @Author: wuyezhen
     * @Date: 2020/9/04
     */
    CfCertificateSeeRes examineCertificate(@Param("passportNum")String passportNum,@Param("a0100")String a0100);

	/**
	 * 根据机构查询证照信息
	 * @param b0100
	 * @return
	 */
    List<ImportInterface> queryCertificateByOmsId(String b0100);
}