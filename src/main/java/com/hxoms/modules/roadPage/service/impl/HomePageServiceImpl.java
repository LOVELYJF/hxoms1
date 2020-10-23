package com.hxoms.modules.roadPage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.StringUtils;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.message.message.entity.Message;
import com.hxoms.message.message.mapper.MessageMapper;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.roadPage.entity.PersonnelPageParam;
import com.hxoms.modules.roadPage.mapper.HomePageMapper;
import com.hxoms.modules.roadPage.service.HomePageService;
import com.hxoms.modules.sysUser.mapper.CfUserMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private OmsRegProcpersoninfoMapper omsRegProcpersoninfoMapper;
    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;
    @Autowired
    private HomePageMapper homePageMapper;
    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    private CfCertificateMapper cfCertificateMapper;

    @Override
    public PageInfo<OmsRegProcpersoninfo> selectPersonnelRoster(PersonnelPageParam personnelPageParam) {
        if (personnelPageParam.getOrderIndex() == null){
            throw new CustomMessageException("参数错误");
        }
        //分页
        PageUtil.pageHelp(personnelPageParam.getPageNum() == null ? 1 : personnelPageParam.getPageNum(),
                personnelPageParam.getPageSize() == null ? 10 : personnelPageParam.getPageSize());
        List<OmsRegProcpersoninfo> omsRegProcpersoninfoList = null;
        //1全省经办人 2重点管理人员 3一把手 4纪委不回复意见人员 5退休人员 6核心涉密人员
        QueryWrapper<OmsRegProcpersoninfo> qWrapper = new QueryWrapper<>();
        if (personnelPageParam.getOrderIndex() == 2){
            //2重点管理人员
            omsRegProcpersoninfoList = omsRegProcpersoninfoMapper.selectImportantManagePerson();
        }else if (personnelPageParam.getOrderIndex() == 3){
            //3一把手
            qWrapper.eq("MAIN_LEADER", Constants.IS_YES);
            omsRegProcpersoninfoList = omsRegProcpersoninfoMapper.selectList(qWrapper);
        }else if (personnelPageParam.getOrderIndex() == 4){
            //4纪委不回复意见人员
            qWrapper.eq("REPLYOPINION", Constants.IS_YES);
            omsRegProcpersoninfoList = omsRegProcpersoninfoMapper.selectList(qWrapper);
        }else if (personnelPageParam.getOrderIndex() == 5){
            //5退休人员
            qWrapper.eq("INCUMBENCY_STATUS", Constants.emIncumbencyStatus.Retirement.getIndex());
            omsRegProcpersoninfoList = omsRegProcpersoninfoMapper.selectList(qWrapper);
        }else if (personnelPageParam.getOrderIndex() == 6){
            //6核心涉密人员
            qWrapper.eq("SECRET_LEVEL", Constants.emSecretLevel.核心.getIndex());
            omsRegProcpersoninfoList = omsRegProcpersoninfoMapper.selectList(qWrapper);
        }else{
            throw new CustomMessageException("参数错误");
        }

        PageInfo<OmsRegProcpersoninfo> pageInfo = new PageInfo(omsRegProcpersoninfoList);
        return pageInfo;
    }

    @Override
    public List<Integer> selectPrivateRealTimeState() {
        List<Integer> result = new ArrayList<>();
        //申请中
        Integer[] statusApply = {1,2,3,4,5};
        QueryWrapper<OmsPriApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("APPLY_STATUS", Arrays.asList(statusApply));
        result.add(omsPriApplyMapper.selectCount(queryWrapper));
        //征求意见中
        Integer[] statusAdvice = {21,22};
        queryWrapper.clear();
        queryWrapper.in("APPLY_STATUS", Arrays.asList(statusAdvice));
        result.add(omsPriApplyMapper.selectCount(queryWrapper));
        //已出国
        result.add(homePageMapper.selectCountGoAbroad());
        //已超期
        result.add(homePageMapper.selectCountTimeOut());
        //回国为归还
        result.add(homePageMapper.selectCountNoGiveBack());
        return result;
    }

    @Override
    public List<OmsPriApplyVO> selectPrivateRealTimeStateDestail(String type) {
        if (StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        List<OmsPriApplyVO> result = null;
        if ("申请中".equals(type)){
            Integer[] statusApply = {1,2,3,4,5};
            OmsPriApplyIPageParam omsPriApplyIPageParam = new OmsPriApplyIPageParam();
            omsPriApplyIPageParam.setApplyStatus(statusApply);
            result = omsPriApplyMapper.selectOmsPriApplyIPage(omsPriApplyIPageParam);
        } else if ("征求意见中".equals(type)){
            Integer[] statusAdvice = {21,22};
            OmsPriApplyIPageParam omsPriApplyIPageParam = new OmsPriApplyIPageParam();
            omsPriApplyIPageParam.setApplyStatus(statusAdvice);
            result = omsPriApplyMapper.selectOmsPriApplyIPage(omsPriApplyIPageParam);
        } else if ("已出国".equals(type)){
            result = homePageMapper.selectGoAbroadList();
        } else if ("已超期".equals(type)){
            result = homePageMapper.selectTimeOutList();
        } else if ("回国以归还".equals(type)){
            result = homePageMapper.selectNoGiveBackList();
        } else {
            throw new CustomMessageException("参数错误");
        }
        //查询途径国家
        for (OmsPriApplyVO item : result) {
            //国家详情
            selectCountryDestail(item);
        }
        return result;
    }

    @Override
    public Map<String, Integer> selectSeekAdviceCount() {
        Map<String, Integer> result = homePageMapper.selectSeekAdviceCount();
        return result;
    }

    @Override
    public List<OmsPriApplyVO> selectSeekAdviceDestail(String type) {
        return homePageMapper.selectSeekAdviceDestail(type);
    }

    @Override
    public Map<String, Integer> selectPriPoliceCount() {
        Map<String, Integer> result = new HashMap<>();
        //超出回国时间10天内，未申请延期
        result.put("delay",homePageMapper.selectPoliceCountDelay());
        //超出回国日期10天内，未归还证照
        result.put("returnCadre",homePageMapper.selectPoliceCountReturnCadre());
        //实际目的地与申请审批目的地不一致
        result.put("differentCountry",homePageMapper.selectPoliceDifferentCountry().size());
        //核心涉密人员申请出国
        result.put("corePerson",homePageMapper.selectCountCorePerson());
        return result;
    }

    @Override
    public List<OmsPriApplyVO> selectPriPoliceDestail(String type) {
        if (StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        List<OmsPriApplyVO> omsPriApplyVOS = null;
        if ("1".equals(type)){
            //超出回国时间10天内，未申请延期
            omsPriApplyVOS = homePageMapper.selectPoliceDelayList();
        } else if ("2".equals(type)){
            //超出回国日期10天内，未归还证照
            omsPriApplyVOS = homePageMapper.selectPoliceReturnCadreList();
        } else if ("3".equals(type)){
            //实际目的地与申请审批目的地不一致
            omsPriApplyVOS = homePageMapper.selectPoliceDifferentCountry();
        } else if ("4".equals(type)){
            //核心涉密人员申请出国
            omsPriApplyVOS = homePageMapper.selectCorePersonList();
        } else{
            throw new CustomMessageException("参数错误");
        }
        for (OmsPriApplyVO omsPriApplyVO : omsPriApplyVOS) {
            selectCountryDestail(omsPriApplyVO);
        }
        return omsPriApplyVOS;
    }

    @Override
    public Map<String, Integer> selectCertificateCount() {
        Map<String, Integer> result = new HashMap<>();
        QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<>();
        //证照
        queryWrapper.eq("ZJLX", Constants.emCertificate.护照.getIndex());
        result.put("passport", cfCertificateMapper.selectCount(queryWrapper));
        //港澳通行证
        queryWrapper.clear();
        queryWrapper.eq("ZJLX", Constants.emCertificate.港澳通行证.getIndex());
        result.put("macao", cfCertificateMapper.selectCount(queryWrapper));
        //台湾通行证
        queryWrapper.clear();
        queryWrapper.eq("ZJLX", Constants.emCertificate.台湾通行证.getIndex());
        result.put("taiwan", cfCertificateMapper.selectCount(queryWrapper));
        //已过期
        queryWrapper.clear();
        queryWrapper.lt("yxqz", new Date());
        result.put("timeout", cfCertificateMapper.selectCount(queryWrapper));
        //半年内过期
        queryWrapper.clear();
        queryWrapper.lt("yxqz", DateUtils.addMonths(new Date(), 6));
        result.put("halfyearTimeout", cfCertificateMapper.selectCount(queryWrapper));
        result.put("total", cfCertificateMapper.selectCount(null));
        return result;
    }

    @Override
    public List<CfCertificate> selectCertificateList(String type) {
        if (StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<>();
        List<CfCertificate> cfCertificates = null;
        if ("1".equals(type)){
            //护照
            queryWrapper.eq("ZJLX", Constants.emCertificate.护照.getIndex());
            cfCertificates = cfCertificateMapper.selectList(queryWrapper);
        } else if ("2".equals(type)){
            //港澳
            queryWrapper.eq("ZJLX", Constants.emCertificate.港澳通行证.getIndex());
            cfCertificates = cfCertificateMapper.selectList(queryWrapper);
        } else if ("3".equals(type)){
            //台湾
            queryWrapper.eq("ZJLX", Constants.emCertificate.台湾通行证.getIndex());
            cfCertificates = cfCertificateMapper.selectList(queryWrapper);
        } else if ("4".equals(type)){
            //已过期
            queryWrapper.lt("yxqz", new Date());
            cfCertificates = cfCertificateMapper.selectList(queryWrapper);
        } else if ("5".equals(type)){
            //半年内过期
            queryWrapper.lt("yxqz", DateUtils.addMonths(new Date(), 6));
            cfCertificates = cfCertificateMapper.selectList(queryWrapper);
        }  else{
            throw new CustomMessageException("参数错误");
        }
        return cfCertificates;
    }

    @Override
    public Map<String, List<Integer>> selectCertificateTurninCount() {
        Map<String, List<Integer>> result = new HashMap<>();
        QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<>();
        //总数
        List<Integer> total = new ArrayList<>();
        queryWrapper.eq("ZJLX", Constants.emCertificate.护照.getIndex());
        total.add(cfCertificateMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.eq("ZJLX", Constants.emCertificate.港澳通行证.getIndex());
        total.add(cfCertificateMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.eq("ZJLX", Constants.emCertificate.台湾通行证.getIndex());
        total.add(cfCertificateMapper.selectCount(queryWrapper));
        //实缴数
        List<Integer> turnin = new ArrayList<>();
        String[] status = {"0","1"};
        queryWrapper.clear();
        queryWrapper.in("SAVE_STATUS", Arrays.asList(status));
        queryWrapper.eq("ZJLX", Constants.emCertificate.护照.getIndex());
        turnin.add(cfCertificateMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.in("SAVE_STATUS", Arrays.asList(status));
        queryWrapper.eq("ZJLX", Constants.emCertificate.港澳通行证.getIndex());
        turnin.add(cfCertificateMapper.selectCount(queryWrapper));
        queryWrapper.clear();
        queryWrapper.in("SAVE_STATUS", Arrays.asList(status));
        queryWrapper.eq("ZJLX", Constants.emCertificate.台湾通行证.getIndex());
        turnin.add(cfCertificateMapper.selectCount(queryWrapper));

        result.put("total", total);
        result.put("turnin", turnin);
        return result;
    }

    @Override
    public List<CfCertificate> selectCertificateTurninList(String certificateType, String turninType) {
        if (StringUtils.isBlank(certificateType) || StringUtils.isBlank(turninType)){
            throw new CustomMessageException("参数错误");
        }
        QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<>();
        if ("护照".equals(certificateType)){
            queryWrapper.eq("ZJLX", Constants.emCertificate.护照.getIndex());
        } else if("港澳通行证".equals(certificateType)){
            queryWrapper.eq("ZJLX", Constants.emCertificate.港澳通行证.getIndex());
        } else if("台湾通行证".equals(certificateType)){
            queryWrapper.eq("ZJLX", Constants.emCertificate.台湾通行证.getIndex());
        }
        if ("实交数".equals(turninType)){
            String[] status = {"0","1"};
            queryWrapper.in("SAVE_STATUS", Arrays.asList(status));
        }
        return cfCertificateMapper.selectList(queryWrapper);
    }

    @Override
    public List<Integer>selectCertificateLendCount() {
        List<Integer> result = new ArrayList<>();
        //借出数量
        QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CARD_STATUS", Constants.CER_STATUS[6]);
        result.add(cfCertificateMapper.selectCount(queryWrapper));
        //到入境时间数量
        result.add(homePageMapper.selectCertificateLendCount("1"));
        //超期数量
        result.add(homePageMapper.selectCertificateLendCount("2"));
        return result;
    }

    @Override
    public List<CfCertificate> selectCertificateLendList(String type) {
        if (StringUtils.isBlank(type)){
            throw new CustomMessageException("参数错误");
        }
        List<CfCertificate> result = null;
        if ("借出数量".equals(type)){
            QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("CARD_STATUS", Constants.CER_STATUS[6]);
            result = cfCertificateMapper.selectList(queryWrapper);
        }else if("到入境时间数量".equals(type)){
            result = homePageMapper.selectCertificateLendList("1");
        }else if("超期数量".equals(type)){
            result = homePageMapper.selectCertificateLendList("2");
        }else{
            throw new CustomMessageException("参数错误");
        }
        return result;
    }

    /**
     * 申请出国国家详情
     *
     * @param omsPriApplyVO
     */
    private void selectCountryDestail(OmsPriApplyVO omsPriApplyVO) {
        //国家列表
        QueryWrapper<Country> countryQueryWrapper = new QueryWrapper<>();
        if (!org.apache.commons.lang3.StringUtils.isBlank(omsPriApplyVO.getGoCountry())) {
            countryQueryWrapper.in("id", omsPriApplyVO.getGoCountry().split(","));
            List<Country> countries = countryMapper.selectList(countryQueryWrapper);
            omsPriApplyVO.setCountries(countries);
        }
        //实际去往国家
        if (!org.apache.commons.lang3.StringUtils.isBlank(omsPriApplyVO.getRealGoCountry())) {
            countryQueryWrapper.clear();
            countryQueryWrapper.in("ID", omsPriApplyVO.getRealGoCountry().split(","));
            omsPriApplyVO.setRealCountries(countryMapper.selectList(countryQueryWrapper));
        }
        //实际途径国家
        if (!org.apache.commons.lang3.StringUtils.isBlank(omsPriApplyVO.getRealPassCountry())) {
            countryQueryWrapper.clear();
            countryQueryWrapper.in("ID", omsPriApplyVO.getRealPassCountry().split(","));
            omsPriApplyVO.setRealPassCountries(countryMapper.selectList(countryQueryWrapper));
        }
    }
}
