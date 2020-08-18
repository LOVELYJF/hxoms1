package com.hxoms.modules.passportCard.counterGet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.CerGetTaskInfo;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.CerGetTaskQueryParam;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.IdentityParam;
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.OmsCerGetTaskListParam;
import com.hxoms.modules.passportCard.counterGet.mapper.OmsCerGetTaskMapper;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class OmsCounterGetServiceImpl extends ServiceImpl<OmsCerGetTaskMapper, OmsCerGetTask> implements OmsCounterGetService {

    @Autowired
    private OmsCerGetTaskMapper omsCerGetTaskMapper;

    @Autowired
    private CfCertificateMapper cfCertificateMapper;
    /**
     * @param identityParam
     * @Desc: 验证身份证
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    @Override
    public void verifyIdentity(IdentityParam identityParam) {
        List<CfUser> userList=omsCerGetTaskMapper.selectUserByQua(identityParam);
        if(userList.size()==0)
            throw new CustomMessageException("系统无此人，请核实！");
    }

    /**
     * @param identityParam
     * @Desc: 验证指纹
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    @Override
    public void verifyFingerMark(IdentityParam identityParam) {
        List<CfUser> userList=omsCerGetTaskMapper.selectUserByQua(identityParam);
        //验证指纹
    }

    /**
     * @param identityParam
     * @Desc: 验证二维码
     * @Author: wangyunquan
     * @Param: [identityParam]
     * @Return: void
     * @Date: 2020/8/14
     */
    @Override
    public void verifyQRCode(IdentityParam identityParam) {

    }

    /**
     * @Desc: 查询可领取证照
     * @Author: wangyunquan
     * @Param: [cerGetTaskQueryParam]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/18
     */
    @Override
    public PageBean selectCanGetCer(CerGetTaskQueryParam cerGetTaskQueryParam) {
        PageBean pageBean= cerGetTaskQueryParam.getPageBean();
        PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageNum());
        PageInfo<CerGetTaskInfo> pageInfo=new PageInfo<CerGetTaskInfo>(omsCerGetTaskMapper.selectCanGetCer(cerGetTaskQueryParam));
        return PageUtil.packagePage(pageInfo);
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
    public void updateToCerGet(OmsCerGetTaskListParam omsCerGetTaskListParam) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户失败!");
        List<OmsCerGetTask> omsCerGetTaskList = omsCerGetTaskListParam.getOmsCerGetTaskList();
        for (OmsCerGetTask omsCerGetTask : omsCerGetTaskList) {
            //已领取
            omsCerGetTask.setGetStatus("1");
            omsCerGetTask.setUpdator(userInfo.getId());
            omsCerGetTask.setUpdateTime(new Date());
            if(omsCerGetTaskMapper.updateById(omsCerGetTask)==0)
                throw new CustomMessageException("保存失败！");
            CfCertificate certificate=new CfCertificate();
            certificate.setId(omsCerGetTask.getCerId());
            //已取出
            certificate.setSaveStatus("1");
            certificate.setUpdater(userInfo.getId());
            certificate.setUpdateTime(new Date());
            if(cfCertificateMapper.updateById(certificate)==0)
                throw new CustomMessageException("保存失败！");
        }
    }

    /**
     * @Desc: 确认领取审批表，保存签名
     * @Author: wangyunquan
     * @Param: [omsCerGetTaskListParam]
     * @Return: void
     * @Date: 2020/8/17
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateToSpbGet(OmsCerGetTaskListParam omsCerGetTaskListParam) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户失败!");
        List<OmsCerGetTask> omsCerGetTaskList = omsCerGetTaskListParam.getOmsCerGetTaskList();
        for (OmsCerGetTask omsCerGetTask : omsCerGetTaskList) {
            omsCerGetTask.setUpdator(userInfo.getId());
            omsCerGetTask.setUpdateTime(new Date());
            if(omsCerGetTaskMapper.updateById(omsCerGetTask)==0)
                throw new CustomMessageException("保存失败！");
        }
    }
}
