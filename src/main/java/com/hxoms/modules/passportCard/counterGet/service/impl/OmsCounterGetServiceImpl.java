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
import com.hxoms.modules.passportCard.counterGet.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.counterGet.mapper.OmsCerGetTaskMapper;
import com.hxoms.modules.passportCard.counterGet.service.OmsCounterGetService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OmsCounterGetServiceImpl extends ServiceImpl<OmsCerGetTaskMapper, OmsCerGetTask> implements OmsCounterGetService {

    @Autowired
    private OmsCerGetTaskMapper omsCerGetTaskMapper;

    private OmsCounterGetService omsCounterGetService;
    @Autowired
    private CfCertificateService cfCertificateService;
    /**
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
    public PageBean<CerGetTaskInfo> selectCanGetCer(PageBean pageBean,CerGetTaskQueryParam cerGetTaskQueryParam) {
        //校验领取人与二维码打印者是否为同一个人
/*        String exist=omsCerGetTaskMapper.selectUserIsExist(cerGetTaskQueryParam);
        if(exist==null)
            throw new CustomMessageException("领取人与二维码打印者不是同一个人，请核实！");*/
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
    public void updateToCerGet(List<GetConfirm> getConfirmList) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("查询登陆用户失败!");
        List<OmsCerGetTask> omsCerGetTaskList=new ArrayList<>();
        List<CfCertificate> cfCertificateList=new ArrayList<>();
        Date date = new Date();
        for (GetConfirm getConfirm : getConfirmList) {
            OmsCerGetTask omsCerGetTask=new OmsCerGetTask();
            BeanUtils.copyProperties(getConfirm,omsCerGetTask);
            //已领取
            omsCerGetTask.setGetStatus("1");
            omsCerGetTask.setGetTime(date);
            omsCerGetTask.setUpdator(userInfo.getId());
            omsCerGetTask.setUpdateTime(date);
            omsCerGetTaskList.add(omsCerGetTask);

            CfCertificate certificate=new CfCertificate();
            certificate.setId(omsCerGetTask.getCerId());
            //已取出
            certificate.setSaveStatus("1");
            certificate.setUpdater(userInfo.getId());
            certificate.setUpdateTime(date);
            cfCertificateList.add(certificate);
        }
        if(!omsCounterGetService.updateBatchById(omsCerGetTaskList))
            throw new CustomMessageException("证照领取更新失败!");
        if(!cfCertificateService.updateBatchById(cfCertificateList))
            throw new CustomMessageException("证照更新失败!");
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
