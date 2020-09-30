package com.hxoms.modules.omsregcadre.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.service.OmsCerTransferOutLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsRegProcbatchServiceImpl extends ServiceImpl<OmsRegProcbatchMapper, OmsRegProcbatch> implements OmsRegProcbatchService {


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
        //status 未备案0，已备案1，已确认2，
        queryWrapper.eq("STATUS", "0");
        //查询是否有未备案批次
        int count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new CustomMessageException("已经存在未确定备案完成的批次不能启动新的批次");
        } else {
            UserInfo user = UserInfoUtil.getUserInfo();
            regProcbatch.setRfUcontacts(user.getUserName());
            regProcbatch.setRfUphone(user.getUserMobile());
            return regProcbatch;
        }

    }

    @Override
    public Object insertProcbatch(OmsRegProcbatch regProcbatch) {
        UserInfo user = UserInfoUtil.getUserInfo();
        //创建批次
        regProcbatch.setId(UUIDGenerator.getPrimaryKey());
        //组织机构代码加yyyyMMdd
        String rfB0000 = regProcbatch.getRfB0000();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String day = sdf.format(new Date());
        regProcbatch.setBatchNo(rfB0000 + day);
        regProcbatch.setSubmitTime(new Date());
        regProcbatch.setCreateUser(user.getId());
        regProcbatch.setCreateDate(new Date());
        return baseMapper.insert(regProcbatch);

    }

    @Override
    public int batchinsertInfo(List<OmsRegProcbatchPerson> orpbplist) {
        int con = 0;
        //批量添加的方法
        int count = 30;
        int n1 = orpbplist.size() / count;
        int n2 = orpbplist.size() % count;
        if (n2 > 0) {
            n1 = n1 + 1;
        }
        for (int j = 0; j < n1; j++) {
            if ((j + 1) * count > orpbplist.size()) {
                //批量保存
                con = regbatchPersonMapper.batchinsertInfo(orpbplist.subList(j * count, orpbplist.size()));
            } else {
                con = regbatchPersonMapper.batchinsertInfo(orpbplist.subList(j * count, (j + 1) * count));
            }
        }
        return con;
    }

    @Override
    public int updateOrpbatch(OmsRegProcbatch batchinfo) {
        return baseMapper.updateById(batchinfo);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result determineRegFinish(String data) {
        List<OmsRegProcbatchPerson> batchPersons = JSONArray.parseArray(data, OmsRegProcbatchPerson.class);

        UserInfo user = UserInfoUtil.getUserInfo();

        //获取本次确定登记备案人员的备案主键
        List<String> rfIds = new ArrayList<>();
        for (OmsRegProcbatchPerson batchPerson:batchPersons
             ) {
            rfIds.add(batchPerson.getRfId());
        }

        //查询本次确定登记备案人员并缓存到hash表
        QueryWrapper<OmsRegProcpersoninfo> personInfoWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        personInfoWrapper.in("ID", rfIds);
        List<OmsRegProcpersoninfo> personInfos=regpersonInfoMapper.selectList(personInfoWrapper);
        HashMap<String,OmsRegProcpersoninfo> hashMapRegInfo=new HashMap<>();
        for (OmsRegProcpersoninfo personInfo:personInfos
             ) {
            hashMapRegInfo.put(personInfo.getId(),personInfo);
        }

        //备案通过人员
        List<OmsRegProcpersoninfo> updates=new ArrayList<>();
        //需要转移证照人员
        List<OmsRegProcpersoninfo> trans=new ArrayList<>();
        //转移证照人员主键
        List<String> transIds = new ArrayList<>();

        for (OmsRegProcbatchPerson batchPerson:batchPersons
        ) {
            if(StringUilt.stringIsNullOrEmpty(batchPerson.getErrorMsg())) continue;

            if("通过".equals(batchPerson.getErrorMsg())){
                batchPerson.setSuccess("1");
                OmsRegProcpersoninfo regProcpersoninfo = hashMapRegInfo.get(batchPerson.getRfId());
                regProcpersoninfo.setCheckStatus("1");
                regProcpersoninfo.setModifyTime(new Date());
                regProcpersoninfo.setModifyUser(user.getId());
                updates.add(regProcpersoninfo);

                //调出人员
                if(Constants.emIncumbencyStatus.Dispatch.getIndex()==Integer.parseInt(regProcpersoninfo.getIncumbencyStatus())){
                    trans.add(regProcpersoninfo);
                    transIds.add(regProcpersoninfo.getId());
                }
            }else{
                batchPerson.setSuccess("0");
            }
        }
        //生成证照转移记录
        GenerateCertificateTransfer(updates,transIds);

        if(updates.size()>0){
            regProcpersonInfoService.updateBatchById(updates);
        }
        procbatchPersonService.updateBatchById(batchPersons);

        return Result.success();
    }

    private void GenerateCertificateTransfer(List<OmsRegProcpersoninfo> regProcpersoninfos,
                                             List<String> rfIds){

    }

    @Override
    public List<String> getHistoryBatch() {
        return baseMapper.getHistoryBatch();
    }

    @Override
    public OmsRegProcbatch selectWbaByOrpbatch() {
        //查询待备案批次
        OmsRegProcbatch regbatch = baseMapper.selectWbaByOrpbatch();
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


}
