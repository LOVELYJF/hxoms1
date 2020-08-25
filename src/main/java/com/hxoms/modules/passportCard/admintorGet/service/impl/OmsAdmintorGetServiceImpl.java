package com.hxoms.modules.passportCard.admintorGet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.passportCard.admintorGet.entity.OmsCerAdmintorGetApply;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetApplyList;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetCerInfo;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetQueryParam;
import com.hxoms.modules.passportCard.admintorGet.mapper.OmsCerAdmintorGetApplyMapper;
import com.hxoms.modules.passportCard.admintorGet.service.OmsAdmintorGetService;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private CfCertificateMapper cfCertificateMapper;

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
        PageInfo<AdmintorGetCerInfo> pageInfo=new PageInfo<AdmintorGetCerInfo>(omsCerAdmintorGetApplyMapper.selectCerInfo(admintorGetQueryParam));
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 保存管理员取证申请
     * @Author: wangyunquan
     * @Param: [admintorGetApplyList]
     * @Return: void
     * @Date: 2020/8/18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertAdmintorGetApply(AdmintorGetApplyList admintorGetApplyList) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户信息失败！");
        List<OmsCerAdmintorGetApply> omsCerAdmintorGetApplyList = admintorGetApplyList.getOmsCerAdmintorGetApplyList();
        List<OmsCerGetTask> omsCerGetTaskList=new ArrayList<>();
        for (OmsCerAdmintorGetApply omsCerAdmintorGetApply : omsCerAdmintorGetApplyList) {
            Date date=new Date();
            omsCerAdmintorGetApply.setId(UUIDGenerator.getPrimaryKey());
            omsCerAdmintorGetApply.setOperator(userInfo.getId());
            omsCerAdmintorGetApply.setOperateTime(date);
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
            if(cfCertificateMapper.updateById(certificate)==0)
                throw new CustomMessageException("证照表更新失败！");
        }
        //保存管理员取证申请
        if(!omsAdmintorGetService.saveBatch(omsCerAdmintorGetApplyList))
            throw new CustomMessageException("管理员取证申请保存失败！");
        //保存领取任务
        if(!omsCounterGetService.saveBatch(omsCerGetTaskList))
            throw new CustomMessageException("证照领取任务保存失败！");
    }
}
