package com.hxoms.modules.passportCard.counterReturn.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.ReturnCertificateInfo;
import com.hxoms.modules.passportCard.counterReturn.mapper.OmsCounterReturnMapper;
import com.hxoms.modules.passportCard.counterReturn.service.OmsCounterReturnService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateSeeRes;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class OmsCounterReturnServiceImpl implements OmsCounterReturnService {

    @Autowired
    private OmsCounterReturnMapper omsCounterReturnMapper;

    @Autowired
    private CfCertificateMapper cfCertificateMapper;

    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;

    /**
     * @param cfCertificate
     * @Desc: 读取证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.ReturnCertificateInfo
     * @Date: 2020/8/20
     */
    @Override
    public ReturnCertificateInfo readCerInfo(CfCertificate cfCertificate) {
        CfCertificate storeCfCertificate=omsCounterReturnMapper.selectCerByQua(cfCertificate);
        //设置保管单位
        cfCertificate.setSurelyUnit("");
        //设置保管方式
        cfCertificate.setSurelyWay("1");
        if(storeCfCertificate!=null){
            cfCertificate.setId(storeCfCertificate.getId());
            cfCertificate.setCardStatus(storeCfCertificate.getCardStatus());
            //获取柜台号
            storeCfCertificate.setCounterNum(1);
        }
        //获取备案人员信息
        //List<OmsRegProcpersoninfo> omsRegProcpersoninfoList=cfCertificateMapper.selectRegPerson(storeCfCertificate!=null?storeCfCertificate.getOmsId():null,cfCertificate.getName(),cfCertificate.getCsrq());
        ReturnCertificateInfo returnCertificateInfo=new ReturnCertificateInfo();
        returnCertificateInfo.setCfCertificate(cfCertificate);
        //returnCertificateInfo.setOmsRegProcpersoninfoList(omsRegProcpersoninfoList);
        return returnCertificateInfo;
    }

    /**
     * @param cfCertificate
     * @Desc: 查询因私出国申请信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.modules.privateabroad.entity.OmsPriApply
     * @Date: 2020/8/20
     */
    @Override
    public OmsPriApply selectPriApplyInfo(CfCertificate cfCertificate) {
        return omsCounterReturnMapper.selectPriApplyInfo(cfCertificate);
    }

    /**
     * @param omsPriApply
     * @Desc: 填写因私有关情况报告
     * @Author: wangyunquan
     * @Param: [omsPriApply]
     * @Return: void
     * @Date: 2020/8/20
     */
    @Override
    public void updatePriForFillReport(OmsPriApply omsPriApply) {
        if(StringUtils.isBlank(omsPriApply.getId())){
            throw new CustomMessageException("参数错误");
        }
        if(omsPriApplyMapper.updateById(omsPriApply)==0)
            throw new CustomMessageException("保存失败！");
    }

    /**
     * @param cfCertificate
     * @Desc: 归还证照
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: void
     * @Date: 2020/8/20
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnCertificate(CfCertificate cfCertificate) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo!=null)
            throw new CustomMessageException("查询登陆用户");
        Date date=new Date();
        //根据id判断是归还还是新增
        if(!StringUtils.isBlank(cfCertificate.getId())){
            //归还
            cfCertificate.setSaveStatus("1");
            cfCertificate.setCardStatus("0");
            cfCertificate.setUpdater(userInfo.getId());
            cfCertificate.setUpdateTime(date);
            if(cfCertificateMapper.updateById(cfCertificate)==0)
                throw new CustomMessageException("证照更新失败！");
        }else{
            //新增
            if(cfCertificateMapper.insert(cfCertificate)==0)
                throw new CustomMessageException("证照保存失败！");
        }
        //证照已上缴，取消证照催缴
    }
    
    @Override
	public CfCertificateSeeRes examineCertificate(String passportNum,String a0100) {
		if (StringUtils.isEmpty(passportNum) ||StringUtils.isEmpty(a0100)) {
			 throw new CustomMessageException("参数错误！");
		}
		return cfCertificateMapper.examineCertificate(passportNum,a0100);
	}
}
