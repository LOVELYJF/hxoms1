package com.hxoms.modules.passportCard.counterGet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.entity.enums.GetStatusEnum;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.counterGet.mapper.OmsCerGetTaskMapper;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.exitEntryManage.entity.enums.InOutStatus;
import com.hxoms.modules.passportCard.exitEntryManage.service.OmsExitEntryManageService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.enums.CardStatusEnum;
import com.hxoms.modules.passportCard.initialise.entity.enums.SaveStatusEnum;
import com.hxoms.modules.passportCard.initialise.entity.enums.SurelyWayEnum;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OmsCounterGetServiceImpl extends ServiceImpl<OmsCerGetTaskMapper, OmsCerGetTask> implements OmsCounterGetService {

    @Autowired
    private OmsCerGetTaskMapper omsCerGetTaskMapper;

    @Autowired
    private OmsCounterGetService omsCounterGetService;

    @Autowired
    private CfCertificateService cfCertificateService;

    @Autowired
    private OmsExitEntryManageService omsExitEntryManageService;
    /**
     * @Desc: 验证身份证
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    @Override
    public void verifyIdentity(IdentityParam identityParam) {
        //判断证件是否过期
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(identityParam.getYxqz().compareTo(simpleDateFormat.parse(simpleDateFormat.format(new Date())))==-1)
                throw  new CustomMessageException("证件已过期，请核实！");
        } catch (ParseException e) {
            e.printStackTrace();
            throw  new CustomMessageException(e.getMessage());
        }
        List<CfUser> userList=omsCerGetTaskMapper.selectUserByQua(identityParam);
        if(userList.size()==0)
            throw new CustomMessageException("系统无此人，请核实！");
    }

    /**
     * @Desc: 验证左手指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/9/8
     */
    @Override
    public FingerMark verifyLeftFingerMark(IdentityParam identityParam) {
        List<CfUser> userList=omsCerGetTaskMapper.selectUserByQua(identityParam);
        FingerMark fingerMark=new FingerMark();
        fingerMark.setLeftFingerMark(userList.get(0).getFingerprint1());
        return fingerMark;
    }

    /**
     * @Desc: 验证右手指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/9/8
     */
    @Override
    public FingerMark verifyRightFingerMark(IdentityParam identityParam) {
        List<CfUser> userList=omsCerGetTaskMapper.selectUserByQua(identityParam);
        FingerMark fingerMark=new FingerMark();
        fingerMark.setRightFingerMark(userList.get(0).getFingerprint2());
        return fingerMark;
    }

    /**
     * @Desc: 验证指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    @Override
    public FingerMark verifyFingerMark(IdentityParam identityParam) {
        List<CfUser> userList=omsCerGetTaskMapper.selectUserByQua(identityParam);
        FingerMark fingerMark=new FingerMark();
        fingerMark.setLeftFingerMark(userList.get(0).getFingerprint1());
        fingerMark.setRightFingerMark(userList.get(0).getFingerprint2());
        return fingerMark;
    }

    /**
     * @Desc: 验证二维码
     * @Author: wangyunquan
     * @Param: [qrCodeUrl]
     * @Return: void
     * @Date: 2020/8/14
     */
    @Override
    public void verifyQRCode(String qrCodeUrl) {
        String exist=omsCerGetTaskMapper.selectQRCode(qrCodeUrl);
        if(exist==null)
            throw new CustomMessageException("系统未生成此二维码，请核实！");
    }

    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.CerGetTaskInfo>
     * @Date: 2020/8/18
     */
    @Override
    public List<CerGetTaskInfo> selectCanGetCer(CerGetTaskQueryParam cerGetTaskQueryParam) {
        //校验领取人与二维码打印者是否为同一个人
/*        String exist=omsCerGetTaskMapper.selectUserIsExist(cerGetTaskQueryParam);
        if(exist==null)
            throw new CustomMessageException("领取人与二维码打印者不是同一个人，请核实！");*/
        return omsCerGetTaskMapper.selectCanGetCer(cerGetTaskQueryParam);
    }

    /**
     * @Desc: 确认领取证照，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: void
     * @Date: 2020/8/17
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateToCerGet(List<GetConfirm> getConfirmList) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户失败!");
        List<OmsCerGetTask> omsCerGetTaskList=new ArrayList<>();
        List<CfCertificate> cfCertificateList=new ArrayList<>();
        List<OmsCerExitEntryRepertory> omsCerExitEntryRepertoryArrayList=new ArrayList<>();
        Date date = new Date();
        for (GetConfirm getConfirm : getConfirmList) {
            OmsCerGetTask omsCerGetTask=new OmsCerGetTask();
            BeanUtils.copyProperties(getConfirm,omsCerGetTask);
            //校验证件是否已领取，防止重复操作生成数据
            OmsCerGetTask omsCerGetTaskExist = omsCerGetTaskMapper.selectById(omsCerGetTask.getCerId());
            if(omsCerGetTaskExist!=null&&GetStatusEnum.STATUS_ENUM_1.getCode().equals(omsCerGetTaskExist.getGetStatus()))
                throw new CustomMessageException("证件号码为："+omsCerGetTaskExist.getZjhm()+"的证件已领取，不能重复操作!");
            //已领取
            omsCerGetTask.setGetStatus(GetStatusEnum.STATUS_ENUM_1.getCode());
            omsCerGetTask.setGetTime(date);
            omsCerGetTask.setUpdator(userInfo.getId());
            omsCerGetTask.setUpdateTime(date);
            omsCerGetTaskList.add(omsCerGetTask);

            //证照信息
            CfCertificate certificate=new CfCertificate();
            certificate.setId(getConfirm.getCerId());
            //已取出
            certificate.setSaveStatus(SaveStatusEnum.YQC.getCode());
            certificate.setCardStatus(CardStatusEnum.YLQ.getCode());
            certificate.setUpdater(userInfo.getId());
            certificate.setUpdateTime(date);
            cfCertificateList.add(certificate);

            //出库信息
            OmsCerExitEntryRepertory omsCerExitEntryRepertory=new OmsCerExitEntryRepertory();
            omsCerExitEntryRepertory.setGetId(UUIDGenerator.getPrimaryKey());
            omsCerExitEntryRepertory.setCerId(getConfirm.getCerId());
            omsCerExitEntryRepertory.setGetId(getConfirm.getId());
            omsCerExitEntryRepertory.setName(getConfirm.getName());
            omsCerExitEntryRepertory.setZjlx(getConfirm.getZjlx());
            omsCerExitEntryRepertory.setZjhm(getConfirm.getZjhm());
            //出入库状态(0:出库,1:入库)
            omsCerExitEntryRepertory.setStatus(InOutStatus.OUT_STATUS.getCode());
            //存取方式(0:证照机,1:柜台)
            omsCerExitEntryRepertory.setMode(SurelyWayEnum.COUNTER.getCode());
            omsCerExitEntryRepertory.setCounterNum(getConfirm.getCounterNum());
            omsCerExitEntryRepertory.setOperator(userInfo.getId());
            omsCerExitEntryRepertory.setOperateTime(date);
            omsCerExitEntryRepertoryArrayList.add(omsCerExitEntryRepertory);
        }
        if(!omsCounterGetService.updateBatchById(omsCerGetTaskList))
            throw new CustomMessageException("证照领取更新失败!");
        if(!cfCertificateService.updateBatchById(cfCertificateList))
            throw new CustomMessageException("证照更新失败!");
        if(!omsExitEntryManageService.saveBatch(omsCerExitEntryRepertoryArrayList))
            throw new CustomMessageException("出库记录保存失败!");
    }

    /**
     * @Desc: 确认领取审批表，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: void
     * @Date: 2020/8/17
     */
    @Override
    public void updateToSpbGet(List<GetSpb> getSpbList) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户失败!");
        List<OmsCerGetTask> omsCerGetTaskList=new ArrayList<>();
        for (GetSpb getSpb : getSpbList) {
            OmsCerGetTask omsCerGetTask=new OmsCerGetTask();
            BeanUtils.copyProperties(getSpb,omsCerGetTask);
            omsCerGetTask.setUpdator(userInfo.getId());
            omsCerGetTask.setUpdateTime(new Date());
            omsCerGetTaskList.add(omsCerGetTask);
        }
        if(!omsCounterGetService.updateBatchById(omsCerGetTaskList))
            throw new CustomMessageException("证照领取更新失败!");
    }
}
