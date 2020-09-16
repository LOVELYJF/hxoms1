package com.hxoms.modules.passportCard.admintorGet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.passportCard.admintorGet.entity.OmsCerAdmintorGetApply;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.*;
import com.hxoms.modules.passportCard.admintorGet.mapper.OmsCerAdmintorGetApplyMapper;
import com.hxoms.modules.passportCard.admintorGet.service.OmsAdmintorGetService;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.CreateQrCodeApply;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.QrCode;
import com.hxoms.modules.passportCard.printGetQrCode.service.OmsPrintGetQrCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class OmsAdmintorGetServiceImpl extends ServiceImpl<OmsCerAdmintorGetApplyMapper, OmsCerAdmintorGetApply> implements OmsAdmintorGetService {

    @Autowired
    private OmsCerAdmintorGetApplyMapper omsCerAdmintorGetApplyMapper;

    @Autowired
    private OmsAdmintorGetService omsAdmintorGetService;

    @Autowired
    private OmsCounterGetService omsCounterGetService;

    @Autowired
    private CfCertificateService cfCertificateService;

    @Autowired
    private OmsPrintGetQrCodeService omsPrintGetQrCodeService;
    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [pageBean, admintorGetQueryParam]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetCerInfo>
     * @Date: 2020/8/18
     */
    @Override
    public PageBean<AdmintorGetCerInfo> selectCerInfo(PageBean pageBean, AdmintorGetQueryParam admintorGetQueryParam) {
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize());
        if(!StringUtils.isBlank(admintorGetQueryParam.getName()))
            admintorGetQueryParam.setNameList(Arrays.asList(admintorGetQueryParam.getName().split("，")));
        PageInfo<AdmintorGetCerInfo> pageInfo=new PageInfo<AdmintorGetCerInfo>(omsCerAdmintorGetApplyMapper.selectCerInfo(admintorGetQueryParam));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 查询人员证照
     * @Author: wangyunquan
     * @Param: [omsId]
     * @Return: java.util.List<com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.PersonInfo>
     * @Date: 2020/9/14
     */
    @Override
    public List<PersonInfo> selectInfoByOmsId(String omsId) {
        return omsCerAdmintorGetApplyMapper.selectInfoByOmsId(omsId);
    }

    /**
     * @Desc: 保存管理员取证申请并打印二维码
     * @Author: wangyunquan
     * @Param: [admintorGetApplyList]
     * @Return: void
     * @Date: 2020/8/18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QrCode insertAdmintorGetApply(List<AdminGetCerApply> adminGetCerApplyList) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        List<OmsCerGetTask> omsCerGetTaskList=new ArrayList<>();
        List<OmsCerAdmintorGetApply> omsCerAdmintorGetApplyList=new ArrayList<>();
        List<CfCertificate> cfCertificateList=new ArrayList<>();
        for (AdminGetCerApply adminGetCerApply : adminGetCerApplyList) {
            Date date=new Date();
            OmsCerAdmintorGetApply omsCerAdmintorGetApply=new OmsCerAdmintorGetApply();
            BeanUtils.copyProperties(adminGetCerApply,omsCerAdmintorGetApply);
            omsCerAdmintorGetApply.setId(UUIDGenerator.getPrimaryKey());
            omsCerAdmintorGetApply.setOperator(userInfo.getId());
            omsCerAdmintorGetApplyList.add(omsCerAdmintorGetApply);
            //新增领取任务
            OmsCerGetTask omsCerGetTask=new OmsCerGetTask();
            omsCerGetTask.setId(UUIDGenerator.getPrimaryKey());
            omsCerGetTask.setOmsId(omsCerAdmintorGetApply.getOmsId());
            omsCerGetTask.setCerId(omsCerAdmintorGetApply.getCerId());
            omsCerGetTask.setBusiId(omsCerAdmintorGetApply.getId());
            omsCerGetTask.setName(omsCerAdmintorGetApply.getName());
            omsCerGetTask.setRfB0000(omsCerAdmintorGetApply.getRfB0000());
            omsCerGetTask.setZjlx(omsCerAdmintorGetApply.getZjlx());
            omsCerGetTask.setZjhm(omsCerAdmintorGetApply.getZjhm());
            omsCerGetTask.setGetStatus("0");
            omsCerGetTask.setDataSource("4");
            omsCerGetTask.setCreator(userInfo.getId());
            omsCerGetTask.setCreateTime(date);
            omsCerGetTaskList.add(omsCerGetTask);
            //修改证照表状态
            CfCertificate certificate=new CfCertificate();
            certificate.setId(omsCerAdmintorGetApply.getCerId());
            //待领取
            certificate.setCardStatus("7");
            certificate.setUpdater(userInfo.getId());
            certificate.setUpdateTime(date);
            cfCertificateList.add(certificate);
        }
        //保存管理员取证申请
        if(!omsAdmintorGetService.saveBatch(omsCerAdmintorGetApplyList))
            throw new CustomMessageException("管理员取证申请保存失败！");
        //保存领取任务
        if(!omsCounterGetService.saveBatch(omsCerGetTaskList))
            throw new CustomMessageException("证照领取任务保存失败！");
        //更新证照状态
        if(!cfCertificateService.updateBatchById(cfCertificateList))
            throw new CustomMessageException("证照表更新失败！");
        //查询证照并打印生成二维码

        return null;
    }

    /**
     * @Desc: 打印二维码
     * @Author: wangyunquan
     * @Param: [printQrCodeParamsList]
     * @Return: com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.GetCerInfoAndQrCode
     * @Date: 2020/9/15
     */
    @Override
    public GetCerInfoAndQrCode createPrintQrCode(List<PrintQrCodeParams> printQrCodeParamsList) throws IOException {
        List<String> ids=new ArrayList<>();
        for (PrintQrCodeParams printQrCodeParams : printQrCodeParamsList) {
            if(!"7".equals(printQrCodeParams.getCardStatus()))
                throw new CustomMessageException(printQrCodeParams.getZjlxName()+"("+ printQrCodeParams.getZjhm()+")"+"不是未领取状态，不能打印二维码，请重新选择！");
            ids.add(printQrCodeParams.getId());
        }
        List<CanGetCerInfo> canGetCerInfoList=omsCerAdmintorGetApplyMapper.selectPrintCerInfo(ids);
        List<CreateQrCodeApply> createQrCodeApplies=new ArrayList<>();
        for (CanGetCerInfo canGetCerInfo : canGetCerInfoList) {
            CreateQrCodeApply createQrCodeApply=new CreateQrCodeApply();
            BeanUtils.copyProperties(canGetCerInfo,createQrCodeApply);
            createQrCodeApplies.add(createQrCodeApply);
        }
        QrCode printQrCode = omsPrintGetQrCodeService.createPrintQrCode(createQrCodeApplies);
        GetCerInfoAndQrCode getCerInfoAndQrCode=new GetCerInfoAndQrCode();
        getCerInfoAndQrCode.setCanGetCerInfoList(canGetCerInfoList);
        getCerInfoAndQrCode.setQrCode(printQrCode.getQrCodeStr());
        return getCerInfoAndQrCode;
    }
}
