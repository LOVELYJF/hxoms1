package com.hxoms.modules.passportCard.counterReturn.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.PingYinUtil;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.mapper.CfCertificateCollectionMapper;
import com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.counterReturn.mapper.OmsCounterReturnMapper;
import com.hxoms.modules.passportCard.counterReturn.service.OmsCounterReturnService;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.exitEntryManage.mapper.OmsCerExitEntryRepertoryMapper;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateSeeRes;
import com.hxoms.modules.passportCard.initialise.entity.OmsCerCounterNumber;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.RegProcpersoninfo;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.initialise.mapper.OmsCerConuterNumberMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
public class OmsCounterReturnServiceImpl implements OmsCounterReturnService {

    @Autowired
    private OmsCounterReturnMapper omsCounterReturnMapper;

    @Autowired
    private CfCertificateMapper cfCertificateMapper;

    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;

    @Autowired
    private OmsCerConuterNumberMapper omsCerConuterNumberMapper;

    @Autowired
    private CfCertificateCollectionMapper cfCertificateCollectionMapper;

    @Autowired
    private OmsRegProcpersoninfoMapper omsRegProcpersoninfoMapper;

    @Autowired
    private OmsCerExitEntryRepertoryMapper omsCerExitEntryRepertoryMapper;
    /**
     * @Desc: 读取证照信息
     * @Author: wangyunquan
     * @Param: [readCerInfo]
     * @Return: com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.ReturnCertificateInfo
     * @Date: 2020/8/20
     */
    @Override
    public CerAndPersonInfo readCerInfo(ReadCerInfo readCerInfo) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo==null)
            throw new CustomMessageException("获取登陆用户信息失败！");
        CfCertificate storeCfCertificate=omsCounterReturnMapper.selectCerByQua(readCerInfo.getZjlx(),readCerInfo.getZjhm());
        //根据登陆用户设置保管单位
        //0:干部监督处,1:省委统战部(台办)
        readCerInfo.setSurelyUnit(cfCertificateMapper.selectUserType(userInfo.getId()));
        //保管方式(0:证照机,1:柜台)
        readCerInfo.setSurelyWay("1");
        if(storeCfCertificate!=null){
            readCerInfo.setCardStatus(storeCfCertificate.getCardStatus());
            readCerInfo.setCardStatusName(Constants.CER_NAME[Integer.parseInt(storeCfCertificate.getCardStatus())]);
            if(storeCfCertificate.getCounterNum()!=null){
                //判断保管单位是否一致，一致则使用原有柜台号，否则重新生成柜台号。
                if(readCerInfo.getSurelyUnit().equals(storeCfCertificate.getSurelyUnit())){
                    //获取柜台号
                    readCerInfo.setCounterNum(storeCfCertificate.getCounterNum());
                }else{
                    //获取位置
                    OmsCerCounterNumber omsCerCounterNumber=omsCerConuterNumberMapper.selectCounterNum(readCerInfo.getSurelyUnit(),readCerInfo.getZjlx(),readCerInfo.getZjxs());
                    //将位置置为已锁定
                    omsCerCounterNumber.setIsLock("1");
                    if(omsCerConuterNumberMapper.updateById(omsCerCounterNumber)==0)
                        throw new CustomMessageException("锁定柜台号失败！");
                    readCerInfo.setCounterNumId(omsCerCounterNumber.getId());
                }
            }
        }else{
            //第一次获取柜台号
            OmsCerCounterNumber omsCerCounterNumber=omsCerConuterNumberMapper.selectCounterNum(readCerInfo.getSurelyUnit(),readCerInfo.getZjlx(),readCerInfo.getZjxs());
            //将位置置为已锁定
            omsCerCounterNumber.setIsLock("1");
            if(omsCerConuterNumberMapper.updateById(omsCerCounterNumber)==0)
                throw new CustomMessageException("锁定柜台号失败！");
            readCerInfo.setCounterNumId(omsCerCounterNumber.getId());
        }
        //获取备案人员信息
        List<RegProcpersoninfo> regProcpersoninfoList=cfCertificateMapper.selectRegPerson(storeCfCertificate!=null?storeCfCertificate.getOmsId():null,readCerInfo.getName(),readCerInfo.getCsrq());
        CerAndPersonInfo cerAndPersonInfo =new CerAndPersonInfo();
        cerAndPersonInfo.setReadCerInfo(readCerInfo);
        cerAndPersonInfo.setRegProcpersoninfoList(regProcpersoninfoList);
        return cerAndPersonInfo;
    }

    /**
     * @Desc: 查询因私出国申请信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.modules.passportCard.counterReturn.entity.parameterEntity.PriApplyInfo
     * @Date: 2020/8/20
     */
    @Override
    public PriApplyInfo selectPriApplyInfo(PriApplyQueryParams priApplyQueryParams) {
        return omsCounterReturnMapper.selectPriApplyInfo(priApplyQueryParams);
    }

    /**
     * @Desc: 填写因私有关情况报告
     * @Author: wangyunquan
     * @Param: [omsPriApply]
     * @Return: void
     * @Date: 2020/8/20
     */
    @Override
    public void updatePriForFillReport(PriApplyInfo priApplyInfo) {
        OmsPriApply omsPriApply=new OmsPriApply();
        BeanUtils.copyProperties(priApplyInfo,omsPriApply);
        if(omsPriApplyMapper.updateById(omsPriApply)==0)
            throw new CustomMessageException("保存失败！");
    }

    /**
     * @Desc: 1、归还处理：①修改证照状态 ② 将柜台号置为已使用 ③解除催缴 ④保存入库记录
     *        2、新增处理：①新增证照 ② 将柜台号置为已使用 ③解除催缴 ④修改人员的证件持有情况
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: void
     * @Date: 2020/8/20
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnCertificate(ReturnCerInfo returnCerInfo) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(userInfo!=null)
            throw new CustomMessageException("查询登陆用户");
        Date date=new Date();
        CfCertificate storeCfCertificate=omsCounterReturnMapper.selectCerByQua(returnCerInfo.getZjlx(),returnCerInfo.getZjhm());
        if(storeCfCertificate!=null){
            //归还处理
            CfCertificate cfCertificate=new CfCertificate();
            cfCertificate.setId(storeCfCertificate.getId());
            //正常保管
            cfCertificate.setSaveStatus("0");
            //正常
            cfCertificate.setCardStatus("0");
            cfCertificate.setUpdater(userInfo.getId());
            cfCertificate.setUpdateTime(date);

            //入库信息
            OmsCerExitEntryRepertory omsCerExitEntryRepertory=new OmsCerExitEntryRepertory();
            omsCerExitEntryRepertory.setGetId(UUIDGenerator.getPrimaryKey());
            omsCerExitEntryRepertory.setCerId(storeCfCertificate.getId());
            omsCerExitEntryRepertory.setName(storeCfCertificate.getName());
            omsCerExitEntryRepertory.setZjlx(storeCfCertificate.getZjlx());
            omsCerExitEntryRepertory.setZjhm(storeCfCertificate.getZjhm());
            //出入库状态(0:出库,1:入库)
            omsCerExitEntryRepertory.setStatus("1");
            //存取方式(0:证照机,1:柜台)
            omsCerExitEntryRepertory.setMode("1");
            omsCerExitEntryRepertory.setCounterNum(returnCerInfo.getCounterNum());
            omsCerExitEntryRepertory.setOperator(userInfo.getOrgId());
            omsCerExitEntryRepertory.setOperateTime(date);

            if(cfCertificateMapper.updateById(cfCertificate)==0)
                throw new CustomMessageException("证照更新失败！");
            if(omsCerExitEntryRepertoryMapper.updateById(omsCerExitEntryRepertory)==0)
                throw new CustomMessageException("入库保存失败！");

        }else{
            //新增处理
            CfCertificate cfCertificate=new CfCertificate();
            BeanUtils.copyProperties(returnCerInfo,cfCertificate);
            cfCertificate.setId(UUIDGenerator.getPrimaryKey());
            cfCertificate.setPy(PingYinUtil.getFirstSpell(cfCertificate.getName()));
            //已取出
            cfCertificate.setSaveStatus("1");
            //待验证
            cfCertificate.setCardStatus("5");
            cfCertificate.setUpdater(userInfo.getId());
            cfCertificate.setUpdateTime(new Date());
            if(cfCertificateMapper.insert(cfCertificate)==0)
                throw new CustomMessageException("保存失败！");
            //修改人员证件持有情况
            OmsRegProcpersoninfo regProcpersoninfo = omsRegProcpersoninfoMapper.selectById(returnCerInfo.getOmsId());
            OmsRegProcpersoninfo omsRegProcper=new OmsRegProcpersoninfo();
            omsRegProcper.setId(regProcpersoninfo.getId());
            BigDecimal bigDecimal1=new BigDecimal(regProcpersoninfo.getLicenceIdentity());
            BigDecimal bigDecimal2=new BigDecimal(cfCertificate.getZjlx());
            omsRegProcper.setLicenceIdentity(bigDecimal1.add(bigDecimal2).intValue());
            if(omsRegProcpersoninfoMapper.updateById(omsRegProcper)==0)
                throw new CustomMessageException("人员的证件持有情况更新失败！");
        }
        //将柜台号码置为已使用
        OmsCerCounterNumber omsCerCounterNumber=new OmsCerCounterNumber();
        omsCerCounterNumber.setId(returnCerInfo.getCounterNumId());
        //将位置置为已锁定
        omsCerCounterNumber.setStatus("1");
        if(omsCerConuterNumberMapper.updateById(omsCerCounterNumber)==0)
            throw new CustomMessageException("柜台号使用失败！");
        //证照已上缴，取消催缴任务,查询证件是否存在催缴，不存在则按人员解除催缴证件类型和证件号码为空的催缴任务
        List<CfCertificateCollection> cfCertificateCollectionList = cfCertificateMapper.selectCjTask(returnCerInfo.getOmsId());
        boolean isExist=false;
        CfCertificateCollection cfCerCollection=null;
        for (CfCertificateCollection cfCertificateCollection : cfCertificateCollectionList) {
            if(returnCerInfo.getZjlx().equals(cfCertificateCollection.getZjlx())&&returnCerInfo.getZjhm().equals(cfCertificateCollection.getZjhm())){
                //按证件解除催缴
                //0:手动解除,1;已上缴,2:未上缴,3:自动解除
                cfCertificateCollection.setCjStatus("1");
                cfCertificateCollection.setUpdator(userInfo.getId());
                cfCertificateCollection.setUpdatetime(date);
                cfCertificateCollectionMapper.updateById(cfCertificateCollection);
                isExist=true;
            }
            if(cfCertificateCollection.getZjlx()==null&& StringUtils.isBlank(cfCertificateCollection.getZjhm()))
                cfCerCollection=cfCertificateCollection;
        }
        //按人员解除催缴证件类型和证件号码为空的催缴任务
        if(!isExist&&cfCerCollection!=null){
            cfCerCollection.setCjStatus("1");
            cfCerCollection.setUpdator(userInfo.getId());
            cfCerCollection.setUpdatetime(date);
            cfCertificateCollectionMapper.updateById(cfCerCollection);
        }
    }

    @Override
	public CfCertificateSeeRes examineCertificate(String passportNum,String a0100) {
		if (StringUtils.isEmpty(passportNum) ||StringUtils.isEmpty(a0100)) {
			 throw new CustomMessageException("参数错误！");
		}
		return cfCertificateMapper.examineCertificate(passportNum,a0100);
	}

}
