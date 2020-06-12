package com.hxoms.modules.privateabroad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.keySupervision.caseInfo.entity.OmsSupCaseInfo;
import com.hxoms.modules.keySupervision.caseInfo.mapper.OmsSupCaseInfoMapper;
import com.hxoms.modules.keySupervision.dismissed.entity.OmsSupDismissed;
import com.hxoms.modules.keySupervision.dismissed.mapper.OmsSupDismissedMapper;
import com.hxoms.modules.keySupervision.violationDiscipline.entity.OmsSupViolationDiscipline;
import com.hxoms.modules.keySupervision.violationDiscipline.mapper.OmsSupViolationDisciplineMapper;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.mapper.CfCertificateMapper;
import com.hxoms.modules.privateabroad.entity.*;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyParam;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.mapper.OmsPriDelayApplyMapper;
import com.hxoms.modules.privateabroad.mapper.OmsPriTogetherpersonMapper;
import com.hxoms.modules.privateabroad.service.OmsPriApplyService;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @desc: 因私出国境申请
 *
 * @author: lijing
 * @date: 2020-05-15
 */
@Service
public class OmsPriApplyServiceImpl implements OmsPriApplyService {
    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;
    @Autowired
    private OmsPriTogetherpersonMapper omsPriTogetherpersonMapper;
    @Autowired
    private OmsSmrOldInfoMapper omsSmrOldInfoMapper;
    @Autowired
    private OmsSupCaseInfoMapper omsSupCaseInfoMapper;
    @Autowired
    private OmsSupViolationDisciplineMapper omsSupViolationDisciplineMapper;
    @Autowired
    private OmsSupDismissedMapper omsSupDissmissedMapper;
    @Autowired
    private CfCertificateMapper cfCertificateMapper;
    @Autowired
    private OmsConditionService omsConditionService;
    @Autowired
    private OmsPriDelayApplyMapper omsPriDelayApplyMapper;

    @Override
    public PageInfo<OmsPriApplyVO> selectOmsPriApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam) {
        if (!StringUtils.isBlank(omsPriApplyIPageParam.getApplyStatusString())){
            String[] applyStatus = omsPriApplyIPageParam.getApplyStatusString().split(",");
            omsPriApplyIPageParam.setApplyStatus((Integer[]) ConvertUtils.convert(applyStatus, Integer.class));
        }
        //分页
        PageUtil.pageHelp(omsPriApplyIPageParam.getPageNum(), omsPriApplyIPageParam.getPageSize());
        List<OmsPriApplyVO> omsPriApplyVOS = omsPriApplyMapper.selectOmsPriApplyIPage(omsPriApplyIPageParam);
        //返回数据
        PageInfo<OmsPriApplyVO> pageInfo = new PageInfo(omsPriApplyVOS);
        return pageInfo;
    }

    @Override
    public OmsPriApplyVO selectPersonById(String b0100, String a0100) {
        if (StringUtils.isBlank(a0100) || StringUtils.isBlank(b0100)){
            throw new CustomMessageException("参数错误");
        }
        //查询用户基本信息
        OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPersonInfoByA0100(a0100);
        //获取涉密信息
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("a0100", a0100);
        List<OmsSmrOldInfoVO> omsSmrOldInfoVOS = omsSmrOldInfoMapper.getSmrOldInfoVOList(paramMap);
        omsPriApplyVO.setOmsSmrOldInfoVOS(omsSmrOldInfoVOS);
        //获取负面信息
        StringBuilder negativeInfo = new StringBuilder();
        //立案
        QueryWrapper<OmsSupCaseInfo> lian = new QueryWrapper<>();
        lian.eq("A0100", a0100);
        negativeInfo.append("立案：");
        if (omsSupCaseInfoMapper.selectCount(lian) > 0){
            negativeInfo.append("是\n");
        }else{
            negativeInfo.append("否\n");
        }
        //违反外事纪律
        QueryWrapper<OmsSupViolationDiscipline> waishi = new QueryWrapper<>();
        waishi.eq("A0100", a0100);
        negativeInfo.append("违反外事纪律");
        if (omsSupViolationDisciplineMapper.selectCount(waishi) > 0){
            negativeInfo.append("有\n");
        }else{
            negativeInfo.append("无\n");
        }
        //免职撤职
        QueryWrapper<OmsSupDismissed> mianzhi = new QueryWrapper<>();
        mianzhi.eq("A0100", a0100);
        negativeInfo.append("免职撤职");
        if (omsSupDissmissedMapper.selectCount(mianzhi) > 0){
            negativeInfo.append("是\n");
        }else{
            negativeInfo.append("否\n");
        }
        omsPriApplyVO.setNegativeInfo(negativeInfo.toString());
        //证件信息
        QueryWrapper<CfCertificate> cfCertificate = new QueryWrapper<>();
        cfCertificate.eq("A0100", a0100)
                .eq("IS_VALID", 0);
        List<CfCertificate> cfCertificates = cfCertificateMapper.selectList(cfCertificate);
        omsPriApplyVO.setCfCertificates(cfCertificates);
        //约束条件
        List<Map<String, String>> condition = omsConditionService.checkConditionByA0100(a0100, Constants.oms_business[1]);
        omsPriApplyVO.setCondition(condition);
        return omsPriApplyVO;
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public List<Map<String, String>> insertOrUpdatePriApply(OmsPriApplyParam omsPriApplyParam) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //基本信息
        OmsPriApply omsPriApply = omsPriApplyParam.getOmsPriApply();
        //随行人员
        List<OmsPriTogetherperson> omsPriTogetherpersonList = omsPriApplyParam.getOmsPriTogetherperson();
        if (StringUtils.isBlank(omsPriApply.getA0100())){
            throw new CustomMessageException("请选择需要出国的人员");
        }
        if (StringUtils.isBlank(omsPriApply.getAbroadReasons())){
            throw new CustomMessageException("出国事由不能为空");
        }
        if (omsPriApply.getAbroadTime() != null){
            throw new CustomMessageException("出国时间不能为空");
        }
        if (omsPriApply.getReturnTime() != null){
            throw new CustomMessageException("回国时间不能为空");
        }
        if(!omsPriApply.getAbroadTime().before(omsPriApply.getReturnTime())){
            throw new CustomMessageException("出国时间不能晚于回国时间");
        }
        //基本信息保存
        //设置草稿状态
        omsPriApply.setApplyStatus(Constants.private_business[0]);
        //出国时长
        long day = omsPriApply.getReturnTime().getTime() - omsPriApply.getAbroadTime().getTime()/DateUtils.MILLIS_PER_DAY;
        omsPriApply.setOutsideTime((int) day);
        //归还证照时间(回国后十天)
        Date revertLicenceTime = DateUtils.addDays(omsPriApply.getReturnTime(), 10);
        omsPriApply.setRevertLicenceTime(revertLicenceTime);
        if (StringUtils.isBlank(omsPriApply.getId())) {
            omsPriApply.setId(UUIDGenerator.getPrimaryKey());
            omsPriApplyMapper.insert(omsPriApply);
        } else {
            omsPriApplyMapper.updateById(omsPriApply);
        }
        int result = 0;
        //随行人员信息保存
        for (OmsPriTogetherperson omsPriTogetherperson : omsPriTogetherpersonList) {
            if (StringUtils.isBlank(omsPriTogetherperson.getId())){
                omsPriTogetherperson.setId(UUIDGenerator.getPrimaryKey());
                omsPriTogetherperson.setApplyId(omsPriApply.getId());
                omsPriTogetherperson.setCreateTime(new Date());
                omsPriTogetherperson.setCreateUser(userInfo.getId());
                result = omsPriTogetherpersonMapper.insert(omsPriTogetherperson);
            }else{
                omsPriTogetherperson.setModifyTime(new Date());
                omsPriTogetherperson.setCreateUser(userInfo.getId());
                result = omsPriTogetherpersonMapper.updateById(omsPriTogetherperson);
            }
        }
        if (result < 1){
            throw new CustomMessageException("申请失败");
        }
        //约束条件
        List<Map<String, String>> condition = omsConditionService.checkCondition(omsPriApply.getId(), Constants.oms_business[1]);
        return condition;
    }

    @Override
    public String deletePriApply(String id) {
        //只能删除草稿状态的
        QueryWrapper<OmsPriApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("applyStatus", Constants.private_business[0]);  //草稿
        queryWrapper.eq("id", id);
        int count = omsPriApplyMapper.selectCount(queryWrapper);
        if (count == 0){
            throw new CustomMessageException("只能删除草稿");
        }
        if (omsPriApplyMapper.deleteById(id) < 1){
            throw new CustomMessageException("删除失败");
        }
        return "删除成功";
    }

    @Override
    public String updateApplyStatus(OmsPriApply omsPriApply) {
        if (omsPriApply.getApplyStatus() != null && StringUtils.isBlank(omsPriApply.getId())){
            throw new CustomMessageException("参数错误");
        }
        OmsPriApply omsPriApplyDestail = omsPriApplyMapper.selectById(omsPriApply.getId());
        if (Constants.private_business[7] == omsPriApply.getApplyStatus()){
            //撤销

        } else if(Constants.private_business[0] == omsPriApply.getApplyStatus()){
            //撤回
            if (omsPriApplyDestail.getApplyStatus() > 20){
                throw new CustomMessageException("不能撤回");
            }
        }
        int updateStatus = omsPriApplyMapper.updateById(omsPriApply);
        if (updateStatus < 1){
            throw new CustomMessageException("操作失败");
        }
        return "操作成功";
    }

    @Override
    public List<PersonInfoVO> selectPersonByKeyword(String keyword) {
        if (StringUtils.isBlank(keyword)){
            throw new CustomMessageException("参数错误");
        }
        List<PersonInfoVO> personInfoVOS = omsPriApplyMapper.selectPersonByKeyword(keyword);
        return personInfoVOS;
    }

    @Override
    public OmsPriApplyVO selectPriApplyById(String id) {
        if (StringUtils.isBlank(id)){
            throw new CustomMessageException("参数错误");
        }
        OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPriApplyById(id);
        //获取涉密信息
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("a0100", omsPriApplyVO.getA0100());
        List<OmsSmrOldInfoVO> omsSmrOldInfoVOS = omsSmrOldInfoMapper.getSmrOldInfoVOList(paramMap);
        omsPriApplyVO.setOmsSmrOldInfoVOS(omsSmrOldInfoVOS);
        //证件信息
        QueryWrapper<CfCertificate> cfCertificate = new QueryWrapper<>();
        cfCertificate.eq("A0100", omsPriApplyVO.getA0100())
                .eq("IS_VALID", 0);
        List<CfCertificate> cfCertificates = cfCertificateMapper.selectList(cfCertificate);
        omsPriApplyVO.setCfCertificates(cfCertificates);
        return omsPriApplyVO;
    }

    @Override
    public List<CountStatusResult> selectCountStatus(String type) {
        if (StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        List<CountStatusResult> countStatusResults = null;
        if (Constants.oms_business[1].equals(type)){
            //因私出国
            countStatusResults = omsPriApplyMapper.selectCountStatus();
        }else if (Constants.oms_business[2].equals(type)){
            //延期出国
            countStatusResults = omsPriDelayApplyMapper.selectCountStatus();
        }

        return countStatusResults;
    }

    @Override
    public List<Map<String, String>> nextCreateFile(String applyId, String type) {
        if (StringUtils.isBlank(applyId)){
            throw new CustomMessageException("参数错误");
        }
        //查询约束条件
        List<Map<String, String>> condition = omsConditionService.checkCondition(applyId, type);
        for (Map<String, String> item : condition) {
            if ("0".equals(item.get("isFit"))){
                return condition;
            }
        }
        if (Constants.oms_business[1].equals(type)){
            //因私
            OmsPriApply omsPriApply = new OmsPriApply();
            omsPriApply.setApplyStatus(Constants.private_business[1]);
            omsPriApply.setId(applyId);
            int updateStatus = omsPriApplyMapper.updateById(omsPriApply);
            if (updateStatus < 1){
                throw new CustomMessageException("操作失败");
            }
        } else if (Constants.oms_business[2].equals(type)){
            //延期回国
            OmsPriDelayApply omsPriDelayApply = new OmsPriDelayApply();
            omsPriDelayApply.setId(applyId);
            omsPriDelayApply.setApplyStatus(Constants.private_business[1]);
            int updateStatus = omsPriDelayApplyMapper.updateById(omsPriDelayApply);
            if (updateStatus < 1){
                throw new CustomMessageException("操作失败");
            }
        }
        return condition;
    }
}
