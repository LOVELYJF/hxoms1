package com.hxoms.modules.passportCard.deviceInteraction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.enums.CjStatusEnum;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.entity.enums.GetStatusEnum;
import com.hxoms.modules.passportCard.counterGet.entity.enums.ReceiveSourceEnum;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.IdentityParam;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import com.hxoms.modules.passportCard.deviceInteraction.entity.OmsCerDeviceInfo;
import com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.*;
import com.hxoms.modules.passportCard.deviceInteraction.mapper.OmsCerDeviceInfoMapper;
import com.hxoms.modules.passportCard.deviceInteraction.service.OmsDeviceInteractionService;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.exitEntryManage.entity.enums.InOutStatus;
import com.hxoms.modules.passportCard.exitEntryManage.service.OmsExitEntryManageService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.enums.CardStatusEnum;
import com.hxoms.modules.passportCard.initialise.entity.enums.SaveStatusEnum;
import com.hxoms.modules.passportCard.initialise.entity.enums.SurelyWayEnum;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.service.OmsPriApplyService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OmsDeviceInteractionServiceImpl extends ServiceImpl<OmsCerDeviceInfoMapper, OmsCerDeviceInfo> implements OmsDeviceInteractionService {

    @Autowired
    private OmsCerDeviceInfoMapper omsCerDeviceInfoMapper;

    @Autowired
    private OmsCounterGetService omsCounterGetService;

    @Autowired
    private CfCertificateMapper cfCertificateMapper;

    @Autowired
    private CfCertificateService cfCertificateService;

    @Autowired
    private OmsExitEntryManageService omsExitEntryManageService;

    @Autowired
    private OmsPriApplyService omsPriApplyService;

    @Autowired
    private CfCertificateCollectionService cfCertificateCollectionService;


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
     * @Desc: 身份验证
     * @Author: wangyunquan
     * @Param: [simpIdentityParam]
     * @Return: void
     * @Date: 2020/10/13
     */
    @Override
    public void verifyIdentity(SimpIdentityParam simpIdentityParam) {
        IdentityParam identityParam=new IdentityParam();
        BeanUtils.copyProperties(simpIdentityParam, identityParam);
        omsCounterGetService.verifyIdentity(identityParam);
    }

    /**
     * @Desc: 指纹验证，获取指纹模板
     * @Author: wangyunquan
     * @Param: [simpIdentityParam]
     * @Return: com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.FingerMark
     * @Date: 2020/10/13
     */
    @Override
    public FingerMark verifyFingerMark(SimpIdentityParam simpIdentityParam) {
        IdentityParam identityParam=new IdentityParam();
        BeanUtils.copyProperties(simpIdentityParam, identityParam);
        return  omsCounterGetService.verifyFingerMark(identityParam);
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
            throw new CustomMessageException("证件领取与打印二维码者不是同单位，不能操作领取，请核实！");
        return omsCerDeviceInfoMapper.selectCanGetCer(qrCodeInfo);
    }

    /**
     * @Desc: 证件出柜通知
     * @Author: wangyunquan
     * @Param: [cerGetNotice]
     * @Return: void
     * @Date: 2020/10/12
     */
    @Override
    public void cerGetNotice(CerGetNotice cerGetNotice) {
        List<CerGetEntity> cerInfo = cerGetNotice.getCerInfo();
        //查询领取人用户id
        String userId=omsCerDeviceInfoMapper.selectUserId(cerGetNotice.getIdNo(),cerGetNotice.getName());
        List<OmsCerGetTask> omsCerGetTaskList=new ArrayList<>();
        List<CfCertificate> cfCertificateList=new ArrayList<>();
        List<OmsCerExitEntryRepertory> omsCerExitEntryRepertoryArrayList=new ArrayList<>();
        List<OmsPriApply> omsPriApplyList=new ArrayList<>();
        Date currDate=new Date();
        for (CerGetEntity cerGetEntity : cerInfo) {
            OmsCerGetTask omsCerGetTask=new OmsCerGetTask();
            //已领取
            omsCerGetTask.setId(cerGetEntity.getId());
            omsCerGetTask.setGetStatus(GetStatusEnum.STATUS_ENUM_1.getCode());
            omsCerGetTask.setGetPeople(userId);
            omsCerGetTask.setGetTime(currDate);
            omsCerGetTask.setUpdator(userId);
            omsCerGetTask.setUpdateTime(currDate);
            omsCerGetTaskList.add(omsCerGetTask);
            //查询证件信息
            CerInfo cerInfoExist=omsCerDeviceInfoMapper.selectCerInfo(cerGetEntity.getId());
            //证照信息
            CfCertificate certificate=new CfCertificate();
            certificate.setId(cerInfoExist.getId());
            //已取出
            certificate.setSaveStatus(SaveStatusEnum.YQC.getCode());
            certificate.setCardStatus(CardStatusEnum.YLQ.getCode());
            certificate.setUpdater(userId);
            certificate.setUpdateTime(currDate);
            cfCertificateList.add(certificate);
            //出库信息
            OmsCerExitEntryRepertory omsCerExitEntryRepertory=new OmsCerExitEntryRepertory();
            omsCerExitEntryRepertory.setId(UUIDGenerator.getPrimaryKey());
            omsCerExitEntryRepertory.setCerId(cerInfoExist.getId());
            omsCerExitEntryRepertory.setGetId(cerGetEntity.getId());
            omsCerExitEntryRepertory.setName(cerInfoExist.getName());
            omsCerExitEntryRepertory.setZjlx(cerInfoExist.getZjlx());
            omsCerExitEntryRepertory.setZjhm(cerInfoExist.getZjhm());
            //出入库状态(0:出库,1:入库)
            omsCerExitEntryRepertory.setStatus(InOutStatus.OUT_STATUS.getCode());
            //存取方式(0:证照机,1:柜台)
            omsCerExitEntryRepertory.setMode(SurelyWayEnum.CABINET.getCode());
            omsCerExitEntryRepertory.setCabinetNum(cerInfoExist.getCabinetNum());
            omsCerExitEntryRepertory.setPlace(cerInfoExist.getPlace());
            omsCerExitEntryRepertory.setOperator(userId);
            omsCerExitEntryRepertory.setOperateTime(currDate);
            omsCerExitEntryRepertoryArrayList.add(omsCerExitEntryRepertory);

            //因私申请
            if(ReceiveSourceEnum.SOURCE_0.getCode().equals(cerInfoExist.getDataSource())){
                OmsPriApply omsPriApply=new OmsPriApply();
                omsPriApply.setId(cerInfoExist.getBusiId());
                omsPriApply.setApplyStatus(Integer.parseInt(CardStatusEnum.YLQ.getCode()));
                omsPriApply.setModifyUser(userId);
                omsPriApply.setModifyTime(currDate);
                omsPriApplyList.add(omsPriApply);
            }
        }
        if(!omsCounterGetService.updateBatchById(omsCerGetTaskList))
            throw new CustomMessageException("证照领取更新失败!");
        if(!cfCertificateService.updateBatchById(cfCertificateList))
            throw new CustomMessageException("证照更新失败!");
        if(!omsExitEntryManageService.saveBatch(omsCerExitEntryRepertoryArrayList))
            throw new CustomMessageException("出库记录保存失败!");
        if(!omsPriApplyList.isEmpty()) {
            if (!omsPriApplyService.updateBatchById(omsPriApplyList))
                throw new CustomMessageException("因私申请更新失败!");
        }
    }

    /**
     * @Desc: 查询证照信息集合
     * @Author: wangyunquan
     * @Param: [simpIdentityParam]
     * @Return: java.util.List<com.hxoms.modules.passportCard.deviceInteraction.entity.parameterEntiry.SimpCerInfo>
     * @Date: 2020/10/13
     */
    @Override
    public List<SimpCerInfo> selectCanReturnCer(SimpIdentityParam simpIdentityParam) {
        if(simpIdentityParam.getYxqz()==null)
            throw new CustomMessageException("参数错误，请核实！");
        //判断证件是否过期
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(simpIdentityParam.getYxqz().compareTo(simpleDateFormat.parse(simpleDateFormat.format(new Date())))==-1)
                throw  new CustomMessageException("证件已过期，请核实！");
        } catch (ParseException e) {
            e.printStackTrace();
            throw  new CustomMessageException(e.getMessage());
        }
        //查询身份信息
        CfUser cfUser =omsCerDeviceInfoMapper.selectUserInfo(simpIdentityParam);
        return omsCerDeviceInfoMapper.selectCanReturnCer(cfUser);
    }

    /**
     * @param cerReturnInfo
     * @Desc: 证件入柜通知
     * @Author: wangyunquan
     * @Param: [cerReturnInfo]
     * @Return: void
     * @Date: 2020/10/13
     */
    @Override
    public void cerReturnNotice(CerReturnInfo cerReturnInfo) {
        List<CerReturnEntity> cerInfo = cerReturnInfo.getCerInfo();
        //查询领取人用户id
        String userId=omsCerDeviceInfoMapper.selectUserId(cerReturnInfo.getIdNo(),cerReturnInfo.getName());
        Date currDate=new Date();
        List<CfCertificate> cfCertificateList=new ArrayList<>();
        List<OmsCerExitEntryRepertory> omsCerExitEntryRepertoryList=new ArrayList<>();
        List<CfCertificateCollection> cfCertificateCollectionList=new ArrayList<>();
        for (CerReturnEntity cerReturnEntity : cerInfo) {
            //查询证照信息
            CfCertificate certificateExist =omsCerDeviceInfoMapper.selectCerByQua(cerReturnEntity.getZjhm(),cerReturnEntity.getZjlx(),cerReturnEntity.getCabinetNum());
            CfCertificate certificate=new CfCertificate();
            //修改证照
            certificate.setId(certificateExist.getId());
            certificate.setSurelyUnit(certificateExist.getSurelyUnit());
            certificate.setSurelyWay(SurelyWayEnum.CABINET.getCode());
            certificate.setSaveStatus(SaveStatusEnum.ZCBG.getCode());
            certificate.setCardStatus(CardStatusEnum.ZC.getCode());
            certificate.setCabinetNum(cerReturnEntity.getCabinetNum());
            certificate.setPlace(cerReturnEntity.getPlace());
            certificate.setUpdater(userId);
            certificate.setUpdateTime(currDate);
            cfCertificateList.add(certificate);
            //入库信息
            OmsCerExitEntryRepertory omsCerExitEntryRepertory=new OmsCerExitEntryRepertory();
            omsCerExitEntryRepertory.setId(UUIDGenerator.getPrimaryKey());
            omsCerExitEntryRepertory.setCerId(certificateExist.getId());
            omsCerExitEntryRepertory.setName(certificateExist.getName());
            omsCerExitEntryRepertory.setZjlx(certificateExist.getZjlx());
            omsCerExitEntryRepertory.setZjhm(certificateExist.getZjhm());
            //出入库状态(0:出库,1:入库)
            omsCerExitEntryRepertory.setStatus(InOutStatus.IN_STATUS.getCode());
            //存取方式(0:证照机,1:柜台)
            omsCerExitEntryRepertory.setMode(SurelyWayEnum.CABINET.getCode());
            omsCerExitEntryRepertory.setCabinetNum(cerReturnEntity.getCabinetNum());
            omsCerExitEntryRepertory.setPlace(cerReturnEntity.getPlace());
            omsCerExitEntryRepertory.setOperator(userId);
            omsCerExitEntryRepertory.setOperateTime(currDate);
            omsCerExitEntryRepertoryList.add(omsCerExitEntryRepertory);

            //证照已上缴，取消催缴任务,查询证件是否存在催缴，不存在则按人员解除催缴证件类型和证件号码为空的催缴任务
            List<CfCertificateCollection> cfCertificateCollectionExistList = cfCertificateMapper.selectCjTask(certificateExist.getOmsId());
            boolean isExist=false;
            CfCertificateCollection cfCerCollection=null;
            for (CfCertificateCollection cfCertificateCollectionExist : cfCertificateCollectionExistList) {
                if(cerReturnEntity.getZjlx().equals(cfCertificateCollectionExist.getZjlx())&&cerReturnEntity.getZjhm().equals(cfCertificateCollectionExist.getZjhm())){
                    //按证件解除催缴
                    //0:手动解除,1;已上缴,2:未上缴,3:自动解除
                    cfCertificateCollectionExist.setCjStatus(CjStatusEnum.YSJ.getCode());
                    cfCertificateCollectionExist.setRealReturnDate(currDate);
                    cfCertificateCollectionExist.setUpdator(userId);
                    cfCertificateCollectionExist.setUpdatetime(currDate);
                    cfCertificateCollectionList.add(cfCertificateCollectionExist);
                    isExist=true;
                }
                if(cfCertificateCollectionExist.getZjlx()==null&& StringUtils.isBlank(cfCertificateCollectionExist.getZjhm()))
                    cfCerCollection=cfCertificateCollectionExist;
            }
            //按人员解除催缴证件类型和证件号码为空的催缴任务
            if(!isExist&&cfCerCollection!=null){
                cfCerCollection.setCjStatus(CjStatusEnum.YSJ.getCode());
                cfCerCollection.setRealReturnDate(currDate);
                cfCerCollection.setUpdator(userId);
                cfCerCollection.setUpdatetime(currDate);
                cfCertificateCollectionList.add(cfCerCollection);
            }
        }
        if(!cfCertificateService.updateBatchById(cfCertificateList))
            throw new CustomMessageException("证照更新失败!");
        if(!omsExitEntryManageService.saveBatch(omsCerExitEntryRepertoryList))
            throw new CustomMessageException("出库记录保存失败!");
        if(!cfCertificateCollectionService.updateBatchById(cfCertificateCollectionList))
            throw new CustomMessageException("证照催缴更新失败!");
    }
}
