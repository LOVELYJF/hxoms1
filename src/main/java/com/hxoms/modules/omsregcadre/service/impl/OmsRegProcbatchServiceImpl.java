package com.hxoms.modules.omsregcadre.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.hxoms.common.OmsCommonUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.ExcelModelORPinfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcbatchMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcbatchPersonMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchPersonService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.passportCard.admintorGet.entity.OmsCerAdmintorGetApply;
import com.hxoms.modules.passportCard.admintorGet.service.OmsAdmintorGetService;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity.OmsCerTransferOutLicense;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.service.OmsCerTransferOutLicenseService;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.service.OrgService;
import org.apache.logging.log4j.core.config.json.JsonConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.ssl.Debug;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsRegProcbatchServiceImpl extends ServiceImpl<OmsRegProcbatchMapper, OmsRegProcbatch> implements OmsRegProcbatchService {

    @Autowired
    private OmsRegProcbatchMapper regProcbatchMapper;
    @Autowired
    private OmsRegProcbatchPersonMapper regbatchPersonMapper;
    @Autowired
    private OmsRegProcpersoninfoMapper regpersonInfoMapper;
    @Autowired
    private OmsRegProcpersonInfoService regProcpersonInfoService;
    @Autowired
    private OmsRegProcbatchPersonService procbatchPersonService;
    @Autowired
    private OmsCerTransferOutLicenseService cerTransferOutLicenseService;
    @Autowired
    private CfCertificateService cfCertificateService;
    @Autowired
    private OmsCounterGetService counterGetService;
    @Autowired
    private OmsAdmintorGetService admintorGetService;
    @Autowired
    private OrgService orgService;

    /**
     * 启动登记备案
     *
     * @param regProcbatch
     * @return
     */
    @Override
    public Object startOmsReg(OmsRegProcbatch regProcbatch) {
        QueryWrapper<OmsRegProcbatch> queryWrapper = new QueryWrapper<OmsRegProcbatch>();
        Map<String, Object> map = new HashMap<String, Object>();
        //status 未备案0，未完成  1，已完成
        queryWrapper.eq("STATUS", "0");
        //查询是否有未备案批次
        int count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new CustomMessageException("已经存在未完成的批次不能启动新的批次");
        } else {
            UserInfo user = UserInfoUtil.getUserInfo();
            regProcbatch.setRfUcontacts(user.getUserName());
            regProcbatch.setRfUphone(user.getUserMobile());
            return regProcbatch;
        }

    }

    @Override
    public Result insertProcbatch(OmsRegProcbatch regProcbatch) {
        if (regProcbatch == null) {
            return Result.error("参数错误！");
        }
        if (regProcbatch.getRfB0000() == null) {
            return Result.error("没有设置备案机构！");
        }
        B01 b01 = orgService.selectOrgByB0111(regProcbatch.getRfB0000());
        if (b01 == null) {
            return Result.error("没有找到指定的备案机构！");
        }
        UserInfo user = UserInfoUtil.getUserInfo();
        //创建批次
        regProcbatch.setId(UUIDGenerator.getPrimaryKey());
        //组织机构代码加yyyyMMdd
        String organizationCode = b01.getOrganization_code();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String day = sdf.format(new Date());
        regProcbatch.setBatchNo(organizationCode + day);
        regProcbatch.setSubmitTime(new Date());
        regProcbatch.setCreateUser(user.getId());
        regProcbatch.setCreateDate(new Date());
        baseMapper.insert(regProcbatch);

        return Result.success();
    }

    @Override
    public int updateOrpbatch(OmsRegProcbatch batchinfo) {
        return baseMapper.updateById(batchinfo);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result determineRegFinish(String data) throws IOException {

        List<Object> batchPersons = OmsCommonUtil.Deserialization("yyyy.MM.dd",data,OmsRegProcbatchPerson.class);
//        List<OmsRegProcbatchPerson> batchPersons = JSONArray.parseArray(data, OmsRegProcbatchPerson.class);
        if (batchPersons == null || batchPersons.size() == 0) {
            return Result.error("没有要确定的登记备案人员！");
        }

        UserInfo user = UserInfoUtil.getUserInfo();

        //获取本次确定登记备案人员的备案主键
        List<String> rfIds = new ArrayList<>();
        for (Object o : batchPersons
        ) {
            OmsRegProcbatchPerson batchPerson = (OmsRegProcbatchPerson) o;
            rfIds.add(batchPerson.getRfId());
        }

        //查询本次确定登记备案人员并缓存到hash表
        QueryWrapper<OmsRegProcpersoninfo> personInfoWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        personInfoWrapper.in("ID", rfIds);
        List<OmsRegProcpersoninfo> personInfos = regpersonInfoMapper.selectList(personInfoWrapper);
        HashMap<String, OmsRegProcpersoninfo> hashMapRegInfo = new HashMap<>();
        for (OmsRegProcpersoninfo personInfo : personInfos
        ) {
            hashMapRegInfo.put(personInfo.getId(), personInfo);
        }

        //备案通过人员
        List<OmsRegProcpersoninfo> updates = new ArrayList<>();
        //需要转移证照人员
        HashMap<String, OmsRegProcpersoninfo> trans = new HashMap<>();
        //辞职、开除、解聘、挂职到期人员
        HashMap<String, OmsRegProcpersoninfo> exits = new HashMap<>();

        List<OmsRegProcbatchPerson> updateBatchPersons = new ArrayList<>();

        //循环处理登记备案确定结果，为通过的调出人员生成转出证照任务、管理员取证记录及取证任务，为退出人员生成取证任务
        for (Object o : batchPersons
        ) {
            OmsRegProcbatchPerson batchPerson = (OmsRegProcbatchPerson) o;
            if (StringUilt.stringIsNullOrEmpty(batchPerson.getErrorMsg())) continue;

            if ("通过".equals(batchPerson.getErrorMsg())) {
                batchPerson.setSuccess("1");
                OmsRegProcpersoninfo regProcpersoninfo = hashMapRegInfo.get(batchPerson.getRfId());
                regProcpersoninfo.setCheckStatus("1");
                regProcpersoninfo.setModifyTime(new Date());
                regProcpersoninfo.setModifyUser(user.getId());
                updates.add(regProcpersoninfo);

                int incumbencyStatus = Integer.parseInt(regProcpersoninfo.getIncumbencyStatus());
                //调出人员
                if ("D".equals(regProcpersoninfo.getInboundFlag()) &&
                        Constants.emIncumbencyStatus.Dispatch.getIndex() == incumbencyStatus) {

                    trans.put(regProcpersoninfo.getId(), regProcpersoninfo);
                }
                //辞职、开除、解聘、去世、挂职到期、免职撤职
                else if ("D".equals(regProcpersoninfo.getInboundFlag()) &&
                        (Constants.emIncumbencyStatus.Resignation.getIndex() == incumbencyStatus ||
                                Constants.emIncumbencyStatus.Expel.getIndex() == incumbencyStatus ||
                                Constants.emIncumbencyStatus.Dismissal.getIndex() == incumbencyStatus) ||
                        Constants.emIncumbencyStatus.Death.getIndex() == incumbencyStatus ||
                        Constants.emIncumbencyStatus.Secondment.getIndex() == incumbencyStatus ||
                        Constants.emIncumbencyStatus.Removal.getIndex() == incumbencyStatus) {

                    exits.put(regProcpersoninfo.getId(), regProcpersoninfo);
                }

            } else {
                batchPerson.setSuccess("0");
            }
            updateBatchPersons.add(batchPerson);
        }
        //生成证照转移记录
        GenerateCertificateTransfer(trans);

        //处理辞职、开除、解聘、去世、挂职到期、免职撤职，置为“待领取”状态，生成取证任务
        DealExit(exits);

        //保存备案通过人员
        if (updates.size() > 0) {
            regProcpersonInfoService.updateBatchById(updates);
        }
        //保存登记备案确定结果
        if (updateBatchPersons.size() > 0)
            procbatchPersonService.updateBatchById(updateBatchPersons);

        //判断登记备案批次是否完成
        if (updateBatchPersons.size() > 0)
            FinishBatch(updateBatchPersons.get(0).getBatchId());

        return Result.success();
    }

    /**
     * @description:查询退出人员证照
     * @author:杨波
     * @date:2020-10-01 * @param regProcpersoninfos 退出人员
     * @return:java.util.List<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>
     **/
    private List<CfCertificate> QueryRemovableCertificate(HashMap<String, OmsRegProcpersoninfo> regProcpersoninfos) {

        if(regProcpersoninfos.size()==0)
            return new ArrayList<>();
        //获取调出人员的所有在库证照
        QueryWrapper<CfCertificate> certificateWrapper = new QueryWrapper<CfCertificate>();
        certificateWrapper.in("OMS_ID", regProcpersoninfos.keySet());
        certificateWrapper.eq("SAVE_STATUS", "0");//正常保管
        certificateWrapper.in("CARD_STATUS", new String[]{"0", "1", "7"});//正常，过期，待领取
        List<CfCertificate> cfCertificates = cfCertificateService.list(certificateWrapper);

        return cfCertificates;
    }

    /**
     * @description:生成证照转出任务
     * @author:杨波
     * @date:2020-10-01 * @param regProcpersoninfos 调出人员
     * @return:void
     **/
    private void GenerateCertificateTransfer(HashMap<String, OmsRegProcpersoninfo> regProcpersoninfos) {

        List<CfCertificate> cfCertificates = QueryRemovableCertificate(regProcpersoninfos);

        //证照转出任务
        List<OmsCerTransferOutLicense> adds = new ArrayList<>();
        //证照领取任务
        List<OmsCerGetTask> getTasks = new ArrayList<>();
        //管理员取证任务
        List<OmsCerAdmintorGetApply> cerAdmintorGetApplies = new ArrayList<>();

        //将证照放入证照转出申请表中
        for (CfCertificate cfCertificate : cfCertificates
        ) {

            OmsRegProcpersoninfo regProcpersoninfo = regProcpersoninfos.get(cfCertificate.getOmsId());
            OmsCerTransferOutLicense cerTransferOutLicense = new OmsCerTransferOutLicense();

            cerTransferOutLicense.setId(UUIDGenerator.getPrimaryKey());
            cerTransferOutLicense.setOmsId(regProcpersoninfo.getId());
            cerTransferOutLicense.setB0100(regProcpersoninfo.getRfB0000());
            cerTransferOutLicense.setWorkUnit(regProcpersoninfo.getWorkUnit());
            cerTransferOutLicense.setName(regProcpersoninfo.getSurname() + regProcpersoninfo.getName());
            cerTransferOutLicense.setCreateTime(new Date());
            cerTransferOutLicense.setCreateUser(UserInfoUtil.getUserInfo().getUserName());
            cerTransferOutLicense.setExitWay(regProcpersoninfo.getIncumbencyStatus());
            cerTransferOutLicense.setExitTime(regProcpersoninfo.getExitDate());

            cerTransferOutLicense.setZjlx(String.valueOf(cfCertificate.getZjlx()));
            cerTransferOutLicense.setZjhm(cfCertificate.getZjhm());
            cerTransferOutLicense.setYxqz(cfCertificate.getYxqz());
            cerTransferOutLicense.setYear(String.valueOf(UtilDateTime.getYear(new Date())));

            adds.add(cerTransferOutLicense);

            //生成证照领取任务
            OmsCerGetTask cerGetTask = GenerateTakeOutCertificateTask(regProcpersoninfo, cfCertificate);
            getTasks.add(cerGetTask);

            //生成管理员取证任务
            OmsCerAdmintorGetApply cerAdmintorGetApply = GenerateManagerTakeOutTask(regProcpersoninfo, cfCertificate);
            cerAdmintorGetApplies.add(cerAdmintorGetApply);
        }
        //保存证照转出任务
        if (adds.size() > 0)
            cerTransferOutLicenseService.saveBatch(adds);
        //保存取证任务
        if (getTasks.size() > 0)
            counterGetService.saveBatch(getTasks);
        //生成管理员取证任务
        if (cerAdmintorGetApplies.size() > 0)
            admintorGetService.saveBatch(cerAdmintorGetApplies);
    }

    /**
     * @param cfCertificate 登记备案人持有证照
     * @description:生成管理员取证任务
     * @author:杨波
     * @date:2020-10-01 * @param regProcpersoninfo 登记备案人员
     * @return:void
     **/
    private OmsCerAdmintorGetApply GenerateManagerTakeOutTask(OmsRegProcpersoninfo regProcpersoninfo,
                                                              CfCertificate cfCertificate) {

        OmsCerAdmintorGetApply cerAdmintorGetApply = new OmsCerAdmintorGetApply();
        cerAdmintorGetApply.setId(UUIDGenerator.getPrimaryKey());
        cerAdmintorGetApply.setCerId(cfCertificate.getId());
        cerAdmintorGetApply.setGetCause("调出人员证照移交");
        cerAdmintorGetApply.setName(regProcpersoninfo.getSurname() + regProcpersoninfo.getName());
        cerAdmintorGetApply.setOmsId(regProcpersoninfo.getId());
        cerAdmintorGetApply.setOperateTime(new Date());
        cerAdmintorGetApply.setOperator(UserInfoUtil.getUserId());
        cerAdmintorGetApply.setRfB0000(regProcpersoninfo.getRfB0000());
        cerAdmintorGetApply.setZjhm(cfCertificate.getZjhm());
        cerAdmintorGetApply.setZjlx(cfCertificate.getZjlx());

        return cerAdmintorGetApply;
    }

    /**
     * @description:处理辞职、开除、解聘、去世、挂职到期、免职撤职，置为“待领取”状态，生成取证任务
     * @author:杨波
     * @date:2020-10-01 * @param regProcpersoninfos 辞职、开除、解聘、去世、挂职到期、免职撤职人员
     * @return:void
     **/
    private void DealExit(HashMap<String, OmsRegProcpersoninfo> regProcpersoninfos) {

        List<CfCertificate> cfCertificates = QueryRemovableCertificate(regProcpersoninfos);
        List<CfCertificate> updates = new ArrayList<>();
        List<OmsCerGetTask> getTasks = new ArrayList<>();
        for (CfCertificate cfCertificate : cfCertificates
        ) {
            cfCertificate.setCardStatus(String.valueOf(Constants.CER_STATUS[7]));//待领取
            cfCertificate.setUpdater(UserInfoUtil.getUserId());
            cfCertificate.setUpdateTime(new Date());

            updates.add(cfCertificate);

            OmsRegProcpersoninfo regProcpersoninfo = regProcpersoninfos.get(cfCertificate.getOmsId());

            //生成证照领取任务
            OmsCerGetTask cerGetTask = GenerateTakeOutCertificateTask(regProcpersoninfo, cfCertificate);
            getTasks.add(cerGetTask);
        }
        //修改证照状态为待领取
        if (updates.size() > 0)
            cfCertificateService.updateBatchById(updates);
        //保存取证任务
        if (getTasks.size() > 0)
            counterGetService.saveBatch(getTasks);

    }

    /**
     * @param cfCertificate 登记备案人持有证照
     * @description:生成取证任务
     * @author:杨波
     * @date:2020-10-01 * @param regProcpersoninfo 登记备案人员
     * @return:com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask
     **/
    private OmsCerGetTask GenerateTakeOutCertificateTask(OmsRegProcpersoninfo regProcpersoninfo,
                                                         CfCertificate cfCertificate) {

        List<OmsCerGetTask> cerGetTasks = new ArrayList<>();
        OmsCerGetTask cerGetTask = new OmsCerGetTask();
        cerGetTask.setId(UUIDGenerator.getPrimaryKey());
        cerGetTask.setGetStatus("0");
        cerGetTask.setCerId(cfCertificate.getId());
        cerGetTask.setCreateTime(new Date());
        cerGetTask.setCreator(UserInfoUtil.getUserId());
        cerGetTask.setDataSource("1");//撤销登记备案
        cerGetTask.setBusiId(regProcpersoninfo.getId());
        cerGetTask.setName(regProcpersoninfo.getSurname() + regProcpersoninfo.getName());
        cerGetTask.setOmsId(regProcpersoninfo.getId());
        cerGetTask.setRfB0000(regProcpersoninfo.getRfB0000());
        cerGetTask.setZjhm(cfCertificate.getZjhm());
        cerGetTask.setZjlx(cfCertificate.getZjlx());
        cerGetTask.setHappenDate(new Date());
        return cerGetTask;
    }

    @Override
    public List<String> getHistoryBatch() {
        return baseMapper.getHistoryBatch();
    }

    @Override
    public OmsRegProcbatch selectWbaByOrpbatch() {
        //查询待备案批次，默认取最后一个批次的设置，如果没有新建过批次，
        //取当前用户所在机构联系人和联系电话作为备案单位联系人和联系电话，
        //取当前用户联系电话作为报送单位联系人和联系电话，两个单位都默认为当前用户所在单位
        OmsRegProcbatch regbatch = baseMapper.selectWbaByOrpbatch();
        if (regbatch == null) {
            //默认取最后一个批次的设置
            List<OmsRegProcbatch> lastBatch = regProcbatchMapper.getLastBatch();

            UserInfo user = UserInfoUtil.getUserInfo();

            B01 b01 = orgService.selectOrgByB0111(user.getOrgId());

            regbatch = new OmsRegProcbatch();

            if (lastBatch.size() > 0) {
                BeanUtils.copyProperties(lastBatch.get(0), regbatch);
            } else {
                regbatch.setRfUnnit(b01.getB0101());
                regbatch.setRfUphone(b01.getPhone());
                regbatch.setRfUcontacts(b01.getContacts());
                regbatch.setSubmitPerson(user.getUserName());
                regbatch.setSubmitPhone(user.getUserMobile());
                regbatch.setSubmitUb0000(user.getOrgId());
                regbatch.setSubmitUcontacts(user.getUserName());
                regbatch.setSubmitUname(b01.getB0101());
                regbatch.setSubmitUcategory("10");
                regbatch.setRfB0000(b01.getB0100());
            }
            regbatch.setBatchNo((b01.getOrganization_code() == null ? "" : b01.getOrganization_code()) +
                    new SimpleDateFormat("yyyyMMdd").format(new Date()));
            regbatch.setCreateDate(new Date());
            regbatch.setId(UUIDGenerator.getPrimaryKey());
            regbatch.setSubmitTime(new Date());
            regbatch.setStatus("0");

            insertProcbatch(regbatch);
        }
        return regbatch;
    }

    @Override
    public Result getToBeConfirmed(String batchId) {
        if (StringUilt.stringIsNullOrEmpty(batchId)) {
            QueryWrapper<OmsRegProcbatch> queryWrapper = new QueryWrapper<OmsRegProcbatch>();
            //状态：待备案
            queryWrapper.eq("status", "0");
            //查询待备案批次
            OmsRegProcbatch regbatch = baseMapper.selectOne(queryWrapper);
            batchId = regbatch.getId();
        }

        List<OmsRegProcbatchPerson> batchPersons = regbatchPersonMapper.getToBeConfirmed(batchId);
        return Result.success(batchPersons);
    }

    @Override
    public Result getToBeCorrected(String b0100) {
        if (StringUilt.stringIsNullOrEmpty(b0100)) {
            return Result.error("参数错误！");
        }
        List<OmsRegProcbatchPerson> batchPersons = regbatchPersonMapper.getToBeCorrected(b0100);
        return Result.success(batchPersons);
    }

    @Override
    public void FinishBatch(String batchId) {
        regProcbatchMapper.FinishBatch(batchId);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveCorrected(String data) throws IOException {

        List<Object> batchPersons = OmsCommonUtil.Deserialization("yyyy.MM.dd",data,OmsRegProcbatchPerson.class);
        if (batchPersons == null || batchPersons.size() == 0) {
            return Result.error("没有要保存的登记备案人员！");
        }

        UserInfo user = UserInfoUtil.getUserInfo();

        //获取本次纠正登记备案人员的备案主键
        List<String> rfIds = new ArrayList<>();
        HashMap<String,OmsRegProcbatchPerson> hashMapBatchPerson=new HashMap<>();
        for (Object o : batchPersons
        ) {
            OmsRegProcbatchPerson batchPerson = (OmsRegProcbatchPerson) o;
            rfIds.add(batchPerson.getRfId());
            hashMapBatchPerson.put(batchPerson.getRfId(),batchPerson);
        }

        //查询本次确定登记备案人员并缓存到hash表
        QueryWrapper<OmsRegProcpersoninfo> personInfoWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        personInfoWrapper.in("ID", rfIds);
        List<OmsRegProcpersoninfo> personInfos = regpersonInfoMapper.selectList(personInfoWrapper);
        List<OmsRegProcbatchPerson> updateBatchPersons=new ArrayList<>();
        for (OmsRegProcpersoninfo personInfo:personInfos
             ) {
            OmsRegProcbatchPerson batchPerson = hashMapBatchPerson.get(personInfo.getId());
            updateBatchPersons.add(batchPerson);

            personInfo.setSurname(batchPerson.getSurname());
            personInfo.setName(batchPerson.getName());
            personInfo.setIdnumberGb(batchPerson.getIdnumberGb());
            personInfo.setBirthDateGb(batchPerson.getBirthDateGb());
            personInfo.setModifyTime(new Date());
            personInfo.setModifyUser(user.getId());
        }

        regProcpersonInfoService.updateBatchById(personInfos);
        procbatchPersonService.updateBatchById(updateBatchPersons);

       return Result.success();
    }
}
