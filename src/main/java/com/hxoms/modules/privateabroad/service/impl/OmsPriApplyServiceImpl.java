package com.hxoms.modules.privateabroad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.mapper.OmsCreateFileMapper;
import com.hxoms.modules.file.service.OmsCreateFileService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.enums.CjDataSourceEnum;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.entity.enums.GetStatusEnum;
import com.hxoms.modules.passportCard.counterGet.service.OmsCerGetTaskService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateExtend;
import com.hxoms.modules.passportCard.initialise.entity.enums.CardStatusEnum;
import com.hxoms.modules.passportCard.initialise.entity.enums.SaveStatusEnum;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.privateabroad.entity.*;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyParam;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.mapper.OmsPriDelayApplyMapper;
import com.hxoms.modules.privateabroad.mapper.OmsPriTogetherpersonMapper;
import com.hxoms.modules.privateabroad.service.OmsAbroadApprovalService;
import com.hxoms.modules.privateabroad.service.OmsPriApplyService;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @desc: 因私出国境申请
 * @author: lijing
 * @date: 2020-05-15
 */
@Service
public class OmsPriApplyServiceImpl extends ServiceImpl<OmsPriApplyMapper, OmsPriApply> implements OmsPriApplyService {
    @Autowired
    private OmsPriApplyMapper omsPriApplyMapper;
    @Autowired
    private OmsPriTogetherpersonMapper omsPriTogetherpersonMapper;
    @Autowired
    private OmsSmrOldInfoMapper omsSmrOldInfoMapper;
    @Autowired
    private CfCertificateMapper cfCertificateMapper;
    @Autowired
    private OmsConditionService omsConditionService;
    @Autowired
    private OmsPriDelayApplyMapper omsPriDelayApplyMapper;
    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    private OmsAbroadApprovalService omsAbroadApprovalService;
    @Autowired
    private OmsCreateFileService omsCreateFileService;
    @Autowired
    private OmsEntryexitRecordService omsEntryexitRecordService;
    @Autowired
    private OmsCreateFileMapper omsCreateFileMapper;
    @Autowired
    private OmsRegProcpersonInfoService omsRegProcpersonInfoService;
    @Autowired
    private CfCertificateCollectionService cfCertificateCollectionService;
    @Autowired
    private OmsCerGetTaskService omsCerGetTaskService;

    @Override
    public PageInfo<OmsPriApplyVO> selectOmsPriApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam) {
        if (!StringUtils.isBlank(omsPriApplyIPageParam.getApplyStatusString())) {
            String[] applyStatus = omsPriApplyIPageParam.getApplyStatusString().split(",");
            omsPriApplyIPageParam.setApplyStatus((Integer[]) ConvertUtils.convert(applyStatus, Integer.class));
        }
        //分页
        PageUtil.pageHelp(omsPriApplyIPageParam.getPageNum() == null ? 1 : omsPriApplyIPageParam.getPageNum(),
                omsPriApplyIPageParam.getPageSize() == null ? 10 : omsPriApplyIPageParam.getPageSize());
        List<OmsPriApplyVO> omsPriApplyVOS = omsPriApplyMapper.selectOmsPriApplyIPage(omsPriApplyIPageParam);
        //返回数据
        PageInfo<OmsPriApplyVO> pageInfo = new PageInfo(omsPriApplyVOS);
        //查询途径国家
        for (OmsPriApplyVO item : pageInfo.getList()) {
            //国家详情
            selectCountryDestail(item);
        }
        return pageInfo;
    }

    @Override
    public OmsPriApplyVO selectPersonById(String procpersonId) {
        if (StringUtils.isBlank(procpersonId)) {
            throw new CustomMessageException("参数错误");
        }
        Map<String, String> paramMap1 = new HashMap<>();
        paramMap1.put("procpersonId", procpersonId);
        //查询用户基本信息
        OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPersonInfoByA0100(paramMap1);
        if (omsPriApplyVO == null) {
            throw new CustomMessageException("该干部未备案");
        }
        //获取涉密信息
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("a0100", omsPriApplyVO.getA0100());
        paramMap.put("finishDate", "1");
        List<OmsSmrOldInfoVO> omsSmrOldInfoVOS = omsSmrOldInfoMapper.getSmrOldInfoVOList(paramMap);
        omsPriApplyVO.setOmsSmrOldInfoVOS(omsSmrOldInfoVOS);
        //证件信息
        if (omsPriApplyVO.getPaper() != null && omsPriApplyVO.getPaper() == 16) {
            //非省委管理证照
            omsPriApplyVO.setDescription("非省委管理证照");
        } else {
            List<CfCertificateExtend> cfCertificates = cfCertificateMapper.selectByOmsId(procpersonId, new String[]{"0", "4", "5", "6", "7", "8"});
            omsPriApplyVO.setCfCertificates(cfCertificates);
        }
        //约束条件
        /*List<Map<String, String>> condition = omsConditionService.checkConditionByA0100(a0100, Constants.oms_business[1]);
        omsPriApplyVO.setCondition(condition);*/
        return omsPriApplyVO;
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public Result insertOrUpdatePriApply(OmsPriApplyParam omsPriApplyParam) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //基本信息
        OmsPriApply omsPriApply = omsPriApplyParam.getOmsPriApply();
        if (omsPriApply.getAbroadTime() != null) {
            List<OmsPriApply> priApplies = omsPriApplyMapper.selectExistsAbroad(omsPriApply.getProcpersonId(),
                    new SimpleDateFormat("yyyy-MM-dd").format(omsPriApply.getAbroadTime()));
            if (priApplies.size() > 0) {
                String desc = "";
                for (OmsPriApply priApply : priApplies
                ) {
                    if (omsPriApply.getId() != null && priApply.getId().equals(omsPriApply.getId())) continue;
                    desc += "当前申请的因私出国（境）时间段内已经存在" + priApply.getDescription() + "出国（境）申请！";
                }
                if (!"".equals(desc))
                    return Result.error(desc);
            }
        }

        //随行人员
        List<OmsPriTogetherperson> omsPriTogetherpersonList = omsPriApplyParam.getOmsPriTogetherperson();
        if (StringUtils.isBlank(omsPriApply.getProcpersonId())) {
            return Result.error("请选择需要出国的人员");
        }
        if (StringUtils.isBlank(omsPriApply.getAbroadReasons())) {
            return Result.error("出国事由不能为空");
        }
        if (omsPriApply.getAbroadTime() == null) {
            return Result.error("出国时间不能为空");
        }
        if (omsPriApply.getReturnTime() == null) {
            return Result.error("回国时间不能为空");
        }
        if (!omsPriApply.getAbroadTime().before(omsPriApply.getReturnTime())) {
            return Result.error("出国时间不能晚于回国时间");
        }
        //基本信息保存
        //设置草稿状态
        omsPriApply.setApplyStatus(Constants.emPrivateGoAbroad.草稿.getIndex());
        //归还证照时间(回国后十天)
        Date revertLicenceTime = DateUtils.addDays(omsPriApply.getReturnTime(), 10);
        omsPriApply.setRevertLicenceTime(revertLicenceTime);
        if (StringUtils.isBlank(omsPriApply.getId())) {
            omsPriApply.setId(UUIDGenerator.getPrimaryKey());
            omsPriApply.setApplyTime(new Date());
            omsPriApply.setCreateTime(new Date());
            //后续可能要做修改
            omsPriApply.setCreateUser(userInfo.getId());
            omsPriApplyMapper.insert(omsPriApply);
        } else {
            omsPriApply.setModifyTime(new Date());
            omsPriApply.setModifyUser(userInfo.getId());
            omsPriApplyMapper.updateById(omsPriApply);
            //删除随行人员
            QueryWrapper<OmsPriTogetherperson> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("APPLY_ID", omsPriApply.getId());
            omsPriTogetherpersonMapper.delete(queryWrapper);
            //删除生成文件
            omsCreateFileService.deleteCreateFile(Constants.oms_business[1], omsPriApply.getId());
        }
        //随行人员信息保存
        for (OmsPriTogetherperson omsPriTogetherperson : omsPriTogetherpersonList) {
            omsPriTogetherperson.setId(UUIDGenerator.getPrimaryKey());
            omsPriTogetherperson.setApplyId(omsPriApply.getId());
            omsPriTogetherperson.setCreateTime(new Date());
            omsPriTogetherperson.setCreateUser(userInfo.getId());
            int result = omsPriTogetherpersonMapper.insert(omsPriTogetherperson);
            if (result < 1) {
                throw new CustomMessageException("申请失败");
            }
        }
        return Result.success(omsPriApply.getId());
    }

    @Override
    public String deletePriApply(String id) {
        //只能删除未上报的
        OmsPriApply omsPriApply = omsPriApplyMapper.selectById(id);
        if (omsPriApply.getApplyStatus() > Constants.emPrivateGoAbroad.自评.getIndex()) {
            throw new CustomMessageException("此申请不能删除");
        }
        if (omsPriApplyMapper.deleteById(id) < 1) {
            throw new CustomMessageException("删除失败");
        }
        return "删除成功";
    }

    @Override
    public Result updateApplyStatus(String id, String currentStep) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(currentStep)) {
            return Result.error("参数错误");
        }
        OmsPriApply omsPriApplyDestail = omsPriApplyMapper.selectById(id);
        if (omsPriApplyDestail == null) {
            return Result.error("申请记录为空");
        }

        Integer sqzt = omsPriApplyDestail.getApplyStatus();
        Integer cStep = Integer.parseInt(currentStep);
        if (cStep != sqzt) return Result.success();

        if (sqzt == Constants.emPrivateGoAbroad.草稿.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.生成材料.getIndex();
        } else if (sqzt == Constants.emPrivateGoAbroad.生成材料.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.打印材料清单.getIndex();
        } else if (sqzt == Constants.emPrivateGoAbroad.打印材料清单.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.自评.getIndex();
        } else if (sqzt == Constants.emPrivateGoAbroad.自评.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.业务受理.getIndex();
        } else if (sqzt == Constants.emPrivateGoAbroad.业务受理.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.征求意见.getIndex();
        }
        if (sqzt == Constants.emPrivateGoAbroad.征求意见.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.记录意见.getIndex();
        }
        if (sqzt == Constants.emPrivateGoAbroad.记录意见.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.做出审核意见.getIndex();
        }
        if (sqzt == Constants.emPrivateGoAbroad.做出审核意见.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.处领导审批.getIndex();
        }
        if (sqzt == Constants.emPrivateGoAbroad.处领导审批.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.部领导审批.getIndex();
        }
        if (sqzt == Constants.emPrivateGoAbroad.部领导审批.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.核实批件.getIndex();
        }
        if (sqzt == Constants.emPrivateGoAbroad.核实批件.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.制作备案表.getIndex();
        }
        if (sqzt == Constants.emPrivateGoAbroad.制作备案表.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.已办结.getIndex();
        }
        if (sqzt == Constants.emPrivateGoAbroad.已办结.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.待领证.getIndex();
        }
        if (sqzt == Constants.emPrivateGoAbroad.待领证.getIndex()) {
            sqzt = Constants.emPrivateGoAbroad.已领证.getIndex();
        }
        omsPriApplyDestail.setApplyStatus(sqzt);
        omsPriApplyMapper.updateById(omsPriApplyDestail);

        WriteApprovalStep(id, cStep, Constants.emPrivateGoAbroad.getNameByIndex(cStep), "1",
                "通过", Constants.oms_business[1]);
        return Result.success();
    }

    @Override
    public List<PersonInfoVO> selectPersonByKeyword(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new CustomMessageException("参数错误");
        }
        List<PersonInfoVO> personInfoVOS = omsPriApplyMapper.selectPersonByKeyword(keyword);
        return personInfoVOS;
    }

    @Override
    public OmsPriApplyVO selectPriApplyById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("参数错误");
        }
        OmsPriApplyVO omsPriApplyVO = omsPriApplyMapper.selectPriApplyById(id);
        if ("1".equals(omsPriApplyVO.getSex())) {
            omsPriApplyVO.setSex("男");
        } else if ("2".equals(omsPriApplyVO.getSex())) {
            omsPriApplyVO.setSex("女");
        }
        //随行人员
        QueryWrapper<OmsPriTogetherperson> togetherperson = new QueryWrapper<>();
        togetherperson.eq("APPLY_ID", omsPriApplyVO.getId());
        List<OmsPriTogetherperson> omsPriTogetherpersonList = omsPriTogetherpersonMapper.selectList(togetherperson);
        omsPriApplyVO.setOmsPriTogetherpeoples(omsPriTogetherpersonList);
        //获取涉密信息
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("a0100", omsPriApplyVO.getA0100());
        List<OmsSmrOldInfoVO> omsSmrOldInfoVOS = omsSmrOldInfoMapper.getSmrOldInfoVOList(paramMap);
        omsPriApplyVO.setOmsSmrOldInfoVOS(omsSmrOldInfoVOS);
        //证件信息
        List<CfCertificateExtend> cfCertificates = cfCertificateMapper.selectByOmsId(omsPriApplyVO.getProcpersonId(), new String[]{"0", "4", "5", "6", "7", "8"});
        omsPriApplyVO.setCfCertificates(cfCertificates);
        //国家详情
        selectCountryDestail(omsPriApplyVO);
        return omsPriApplyVO;
    }

    @Override
    public List<CountStatusResult> selectCountStatus(String type) {
        if (StringUtils.isBlank(type)) {
            throw new CustomMessageException("参数错误");
        }
        List<CountStatusResult> countStatusResults = null;
        if (Constants.oms_business[1].equals(type)) {
            //因私出国
            countStatusResults = omsPriApplyMapper.selectCountStatus();
        } else if (Constants.oms_business[2].equals(type)) {
            //延期出国
            countStatusResults = omsPriDelayApplyMapper.selectCountStatus();
        }

        return countStatusResults;
    }

    @Override
    public String nextCreateFile(String applyId, String type) {
        if (StringUtils.isBlank(applyId) || StringUtils.isBlank(type)) {
            throw new CustomMessageException("参数错误");
        }
        OmsAbroadApproval omsAbroadApproval = new OmsAbroadApproval();
        if (Constants.oms_business[1].equals(type)) {
            //因私
            omsAbroadApproval.setType(Constants.oms_business[1]);
            OmsPriApply omsPriApply = new OmsPriApply();
            omsPriApply.setApplyStatus(Constants.emPrivateGoAbroad.生成材料.getIndex());
            omsPriApply.setId(applyId);
            int updateStatus = omsPriApplyMapper.updateById(omsPriApply);
            if (updateStatus < 1) {
                throw new CustomMessageException("操作失败");
            }
        } else if (Constants.oms_business[2].equals(type)) {
            //延期回国
            omsAbroadApproval.setType(Constants.oms_business[2]);
            OmsPriDelayApply omsPriDelayApply = new OmsPriDelayApply();
            omsPriDelayApply.setId(applyId);
            omsPriDelayApply.setApplyStatus(Constants.emPrivateGoAbroad.生成材料.getIndex());
            int updateStatus = omsPriDelayApplyMapper.updateById(omsPriDelayApply);
            if (updateStatus < 1) {
                throw new CustomMessageException("操作失败");
            }
        }

        //查询是否存在数据
        List<OmsAbroadApproval> list = omsAbroadApprovalService.selcetByApplyIdAndStepCode(Constants.emPrivateGoAbroad.生成材料.getIndex(), applyId);
        if (list != null || list.size() != 0) {
            for (OmsAbroadApproval abroadApproval : list) {
                omsAbroadApprovalService.deleteById(abroadApproval.getId());
            }
        }
        //添加步骤
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        omsAbroadApproval.setApplyId(applyId);
        omsAbroadApproval.setStepCode(Constants.emPrivateGoAbroad.生成材料.getIndex());
        omsAbroadApproval.setStepName(Constants.emPrivateGoAbroad.生成材料.getName());
        omsAbroadApproval.setApprovalTime(new Date());
        omsAbroadApproval.setApprovalUser(userInfo.getId());
        omsAbroadApproval.setSubmitTime(new Date());
        omsAbroadApproval.setSubmitUser(userInfo.getId());
        omsAbroadApproval.setApprovalResult("1");
        omsAbroadApproval.setApprovalAdvice("通过");
        omsAbroadApprovalService.insertOmsAbroadApproval(omsAbroadApproval);
        //约束消息提醒
        omsConditionService.remindCondition(applyId, type);
        return "操作成功";
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String saveAbroadState(OmsPriApply omsPriApply) {
        if (StringUtils.isBlank(omsPriApply.getId())) {
            throw new CustomMessageException("参数错误");
        }
        if (omsPriApplyMapper.selectCount((new QueryWrapper<OmsPriApply>()).eq("ID", omsPriApply.getId())) < 1) {
            throw new CustomMessageException("该申请不存在");
        }
        if (omsPriApplyMapper.updateById(omsPriApply) < 1) {
            throw new CustomMessageException("操作失败");
        }
        //结果比对
        omsEntryexitRecordService.verifySituationReport(omsPriApplyMapper.selectById(omsPriApply.getId()));
        return "操作成功";
    }

    @Override
    public List<Map<String, Object>> countCancelPriApply(OmsPriApplyIPageParam omsPriApplyIPageParam) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> cancelInfor = omsPriApplyMapper.selectCancelInfor(omsPriApplyIPageParam);
        if (cancelInfor != null && cancelInfor.size() > 0) {
            Map<String, String> params = new HashMap<>();
            int acount = 0, bcount = 0, ccount = 0, dcount = 0;
            int aall = 0, ball = 0, call = 0, dall = 0;
            for (int i = 0; i < cancelInfor.size(); i++) {
                Map<String, Object> item = cancelInfor.get(i);
                params.put("step", "a1");//审核意见之前
                params.put("procpersonId", (String) item.get("procpersonId"));
                int a = omsPriApplyMapper.cancelCount(params);
                item.put("a", a);
                params.put("step", "b1");//作出审核意见
                int b = omsPriApplyMapper.cancelCount(params);
                item.put("b", b);
                params.put("step", "c1");//处领导审核
                int c = omsPriApplyMapper.cancelCount(params);
                item.put("c", c);
                params.put("step", "d1");//部领导审批
                int d = omsPriApplyMapper.cancelCount(params);
                item.put("d", d);
                result.add(item);
                acount += a;
                bcount += b;
                ccount += c;
                dcount += d;
                aall += a;
                ball += b;
                call += c;
                dall += d;
                if (i == cancelInfor.size() - 1) {
                    //不同插入合计
                    Map<String, Object> count = new HashMap<>();
                    count.put("b0101", item.get("b0101"));
                    count.put("post", "合计");
                    count.put("a", acount);
                    count.put("b", bcount);
                    count.put("c", ccount);
                    count.put("d", dcount);
                    result.add(count);
                    acount = 0;
                    bcount = 0;
                    ccount = 0;
                    dcount = 0;
                } else {
                    if (!item.get("b0111").equals(cancelInfor.get(i + 1).get("b0111"))) {
                        //不同插入合计
                        Map<String, Object> count = new HashMap<>();
                        count.put("b0101", item.get("b0101"));
                        count.put("post", "合计");
                        count.put("a", acount);
                        count.put("b", bcount);
                        count.put("c", ccount);
                        count.put("d", dcount);
                        result.add(count);
                        acount = 0;
                        bcount = 0;
                        ccount = 0;
                        dcount = 0;
                    }
                }
            }
            //插入总合计
            Map<String, Object> count = new HashMap<>();
            count.put("post", "总计");
            count.put("a", aall);
            count.put("b", ball);
            count.put("c", call);
            count.put("d", dall);
            result.add(count);
        } else {
            return result;
        }
        return result;
    }

    @Override
    public Map<String, Object> selectVisaSettingByCode(String infoId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("infoId", infoId);
        params.put("dictCode", "TAIWAN");
        List<Map<String, String>> taiwan = omsPriApplyMapper.selectVisaSettingByCode(params);
        params.put("dictCode", "HONGKONG");
        List<Map<String, String>> hongkong = omsPriApplyMapper.selectVisaSettingByCode(params);
        params.put("dictCode", "MAKAO");
        List<Map<String, String>> makao = omsPriApplyMapper.selectVisaSettingByCode(params);
        result.put("taiwan", taiwan);
        result.put("hongkong", hongkong);
        result.put("makao", makao);
        return result;
    }

    @Override
    public List<PassportResult> selectPassportByCountry(String countries, String procpersonId, String outDate) {
        if (StringUtils.isBlank(procpersonId)) {
            throw new CustomMessageException("请先选择出国人员");
        }
        if (StringUtils.isBlank(procpersonId)) {
            throw new CustomMessageException("请先选择国家");
        }
        if (outDate == null)
            outDate = "出国（境）";
        List<PassportResult> result = new ArrayList<>();
        //国外
        String[] country = countries.split(",");
        for (String item : country) {
            if (!"179".equals(item) && !"73".equals(item) && !"115".equals(item)) {
                PassportResult passportResult = new PassportResult();
                passportResult.setZjlx(1);
                result.add(passportResult);
                break;
            }
        }
        countries += ",";
        countries = "," + countries;
        if (countries.indexOf(",179,") != -1) {
            //台湾
            PassportResult passportResult = new PassportResult();
            passportResult.setZjlx(4);
            result.add(passportResult);
        }
        if (countries.indexOf(",73,") != -1 || countries.indexOf(",115,") != -1) {
            //香港、澳门
            PassportResult passportResult = new PassportResult();
            passportResult.setZjlx(2);
            result.add(passportResult);
        }
        QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<>();
        boolean changeCard = false;
        for (PassportResult item : result) {
            queryWrapper.clear();
            queryWrapper.eq("OMS_ID", procpersonId)
                    .eq("ZJLX", item.getZjlx())
                    .in("CARD_STATUS", "0", "4", "5", "7");
            CfCertificate cfCertificate = cfCertificateMapper.selectOne(queryWrapper);
            if (cfCertificate != null) {
                //半年后是否失效
                boolean flagSix = (DateUtils.addMonths(new Date(), 6)).after(cfCertificate.getYxqz());
                //是否失效
                boolean flag = cfCertificate.getYxqz().before(new Date());
                item.setNum(cfCertificate.getZjhm());

                if (flag || flagSix) changeCard = true;

                //护照
                if (item.getZjlx() == 1) {
                    if (flag) {
                        //失效
                        item.setType(3);
                        item.setDesc("可于" + outDate + "前因失效重新申领护照（" + cfCertificate.getZjhm() + "）");
                    } else if (flagSix) {
                        //换发
                        item.setType(2);
                        item.setDesc("可于" + outDate + "前换发护照（" + cfCertificate.getZjhm() + "）");
                    } else {
                        item.setDesc("");
                    }
                } else if (item.getZjlx() == 2) {
                    //港澳
                    if (flag) {
                        //失效
                        item.setType(3);
                        item.setDesc("可于" + outDate + "前因失效重新申领港澳通行证（" + cfCertificate.getZjhm() + "）");
                    } else if (flagSix) {
                        //换发
                        item.setType(2);
                        item.setDesc("可于" + outDate + "前换发港澳通行证（" + cfCertificate.getZjhm() + "）");
                    } else {
                        item.setDesc("");
                    }
                } else if (item.getZjlx() == 4) {
                    //台湾
                    if (flag) {
                        //失效
                        item.setType(3);
                        item.setDesc("可于" + outDate + "前因失效重新申领台湾通行证（" + cfCertificate.getZjhm() + "）");
                    } else if (flagSix) {
                        //换发
                        item.setType(2);
                        item.setDesc("可于" + outDate + "前换发台湾通行证（" + cfCertificate.getZjhm() + "）");
                    } else {
                        item.setDesc("");
                    }
                }
            } else {
                item.setNum("");
                //申领新证
                item.setType(1);
                //护照
                if (item.getZjlx() == 1) {
                    item.setDesc("可于" + outDate + "前申领护照");
                } else if (item.getZjlx() == 2) {
                    //港澳
                    item.setDesc("可于" + outDate + "前申领港澳通行证");
                } else if (item.getZjlx() == 4) {
                    //台湾
                    item.setDesc("可于" + outDate + "前申领台湾通行证");
                }
            }
        }
        return result;
    }

    @Override
    public OmsCreateFile printApproval(String applyId) {
        if (StringUtils.isBlank(applyId)) {
            throw new CustomMessageException("参数错误");
        }
        return omsCreateFileMapper.priApplyPrintApproval(applyId);
    }

    @Override
    public void WriteApprovalStep(String applyId, Integer stepCode, String stepName,
                                  String approvalResult, String approvalOpinion, String businessType) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        OmsAbroadApproval omsAbroadApproval = new OmsAbroadApproval();
        omsAbroadApproval.setStepCode(stepCode);
        omsAbroadApproval.setStepName(stepName);
        omsAbroadApproval.setApplyId(applyId);
        omsAbroadApproval.setApprovalTime(new Date());
        omsAbroadApproval.setApprovalUser(userInfo.getUserName());
        omsAbroadApproval.setApprovalResult(approvalResult);
        omsAbroadApproval.setApprovalAdvice(approvalOpinion);
        omsAbroadApproval.setType(businessType);
        omsAbroadApproval.setCreateTime(new Date());
        omsAbroadApproval.setCreateUser(userInfo.getId());
        omsAbroadApprovalService.insertOmsAbroadApproval(omsAbroadApproval);
    }

    @Override
    public Result recallApply(String applyId) {
        OmsPriApply omsPriApply = omsPriApplyMapper.selectPriApplyById(applyId);
        if (omsPriApply == null)
            return Result.error("该因私出国（境）申请不存在！");
        if (omsPriApply.getApplyStatus() != 20)
            return Result.error("只有受理状态的申请才能撤回！");

        omsPriApply.setApplyStatus(Constants.emPrivateGoAbroad.草稿.getIndex());
        omsPriApplyMapper.updateById(omsPriApply);

        WriteApprovalStep(applyId,Constants.emPrivateGoAbroad.草稿.getIndex(),
                Constants.emPrivateGoAbroad.草稿.getName(),"1",
                "",Constants.oms_business[1]);
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result cancelApply(OmsPriApply omsPriApply) {
        OmsPriApply priApply = omsPriApplyMapper.selectPriApplyById(omsPriApply.getId());
        if (priApply == null)
            return Result.error("该因私出国（境）申请不存在！");
        if (priApply.getApplyStatus() == Constants.emPrivateGoAbroad.撤销.getIndex())
            return Result.error("该申请已经撤消！");

        priApply.setApplyStatus(Constants.emPrivateGoAbroad.撤销.getIndex());
        priApply.setCancelReason(omsPriApply.getCancelReason());
        priApply.setModifyTime(new Date());
        priApply.setModifyUser(UserInfoUtil.getUserId());
        omsPriApplyMapper.updateById(priApply);

        //建立催缴任务
        List<CfCertificate> certificates = getPriApplyCertificates(priApply);
        if (certificates != null)
            GenerateCJ(certificates, priApply);

        //取消证照领取任务
        omsCerGetTaskService.deleteTaskByBusinessId(priApply.getId());

        //将待领取或已领取证照置为正常保管
        if (certificates != null) {
            for (CfCertificate cfCertificate : certificates
            ) {
                if (!cfCertificate.getSaveStatus().equals(SaveStatusEnum.YQC.getCode()) &&
                        !cfCertificate.getCardStatus().equals(CardStatusEnum.DLQ.getCode())) continue;

                cfCertificate.setSaveStatus(SaveStatusEnum.ZCBG.getCode());
                cfCertificate.setCardStatus(CardStatusEnum.ZC.getCode());

                cfCertificateMapper.updateById(cfCertificate);
            }
        }

        WriteApprovalStep(priApply.getId(),Constants.emPrivateGoAbroad.撤销.getIndex(),
                Constants.emPrivateGoAbroad.撤销.getName(),"1",
                priApply.getCancelReason(),Constants.oms_business[1]);
        return Result.success();
    }

    /**
     * @description:获取因私出国境申请涉及的证照
     * @author:杨波
     * @date:2020-10-21 * @param priApply 因私出国境申请
     * @return:java.util.List<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>
     **/
    @Override
    public List<CfCertificate> getPriApplyCertificates(OmsPriApply priApply) {

        List<String> cerIds = new ArrayList<>();
        if (!StringUilt.stringIsNullOrEmpty(priApply.getTaiwanPassportNum()))
            cerIds.add(priApply.getTaiwanPassportNum());
        if (!StringUilt.stringIsNullOrEmpty(priApply.getPassportNum()))
            cerIds.add(priApply.getPassportNum());
        if (!StringUilt.stringIsNullOrEmpty(priApply.getHongkongandmacaoPassportNum()))
            cerIds.add(priApply.getHongkongandmacaoPassportNum());

        List<CfCertificate> certificates = null;
        if (cerIds.size() > 0) {
            QueryWrapper<CfCertificate> certificateQueryWrapper = new QueryWrapper<>();
            certificateQueryWrapper.in(("id"), cerIds);
            certificates = cfCertificateMapper.selectList(certificateQueryWrapper);
        }
        return certificates;
    }

    /**
     * @param omsPriApply 因私出国境申请
     * @description:为因私出国境申请涉及证照创建催缴任务
     * @author:杨波
     * @date:2020-10-21 * @param certificates 因私出国境申请涉及证照
     * @return:void
     **/
    public void GenerateCJ(List<CfCertificate> certificates, OmsPriApply omsPriApply) {

        List<CfCertificateCollection> cfCertificateCollectionList = new ArrayList<>();
        OmsRegProcpersoninfo omsRegProcpersoninfo = omsRegProcpersonInfoService.getById(omsPriApply.getProcpersonId());
        for (CfCertificate cfCertificate : certificates) {
            if (cfCertificate.getSaveStatus() == null) continue;
            //非取出状态不产生催缴
            if (Integer.parseInt(cfCertificate.getSaveStatus()) != Constants.CER_SAVE_STATUS[1]) continue;

            CfCertificateCollection cfCertificateCollection = new CfCertificateCollection();
            cfCertificateCollection.setDataSource(CjDataSourceEnum.YSCG.getCode());
            cfCertificateCollection.setCerId(cfCertificate.getId());
            cfCertificateCollection.setHappenDate(new Date());
            cfCertificateCollection.setOmsId(cfCertificate.getOmsId());
            cfCertificateCollection.setBusiId(omsPriApply.getId());
            cfCertificateCollection.setName(cfCertificate.getName());
            cfCertificateCollection.setRfB0000(omsPriApply.getB0100());
            cfCertificateCollection.setWorkUnit(omsRegProcpersoninfo.getWorkUnit());
            cfCertificateCollection.setZjlx(cfCertificate.getZjlx());
            cfCertificateCollection.setZjhm(cfCertificate.getZjhm());
            cfCertificateCollectionList.add(cfCertificateCollection);
        }
        if (cfCertificateCollectionList.size() > 0) {
            cfCertificateCollectionService.createCjTask(cfCertificateCollectionList);
        }
    }

    @Override
    public void GenerateCerGetTask(List<CfCertificate> certificates, OmsPriApply omsPriApply) {
        List<OmsCerGetTask> omsCerGetTasks = new ArrayList<>();
        for (CfCertificate cfCertificate : certificates) {
            if (cfCertificate.getSaveStatus() == null) continue;
            if (cfCertificate.getCardStatus() == null) continue;

            if (Integer.parseInt(cfCertificate.getSaveStatus()) != Constants.CER_SAVE_STATUS[0])//正常保管
                continue;
            if (!cfCertificate.getCardStatus().equals(CardStatusEnum.ZC.getCode()) //正常
            )
                continue;

            OmsCerGetTask omsCerGetTask = new OmsCerGetTask();
            omsCerGetTask.setHappenDate(omsPriApply.getAbroadTime());
            omsCerGetTask.setZjlx(cfCertificate.getZjlx());
            omsCerGetTask.setZjhm(cfCertificate.getZjhm());
            omsCerGetTask.setRfB0000(omsPriApply.getB0100());
            omsCerGetTask.setOmsId(cfCertificate.getOmsId());
            omsCerGetTask.setName(cfCertificate.getName());
            omsCerGetTask.setBusiId(omsPriApply.getId());
            omsCerGetTask.setDataSource(CjDataSourceEnum.YSCG.getCode());
            omsCerGetTask.setGetStatus(GetStatusEnum.STATUS_ENUM_0.getCode());
            omsCerGetTask.setCreator(UserInfoUtil.getUserId());
            omsCerGetTask.setCreateTime(new Date());
            omsCerGetTask.setCerId(cfCertificate.getId());
            omsCerGetTask.setId(UUIDGenerator.getPrimaryKey());
            omsCerGetTasks.add(omsCerGetTask);
        }
        if (omsCerGetTasks.size() > 0) {
            omsCerGetTaskService.saveBatch(omsCerGetTasks);
        }
    }

    /**
     * 申请出国国家详情
     *
     * @param omsPriApplyVO
     */
    private void selectCountryDestail(OmsPriApplyVO omsPriApplyVO) {
        //国家列表
        QueryWrapper<Country> countryQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(omsPriApplyVO.getGoCountry())) {
            countryQueryWrapper.in("id", omsPriApplyVO.getGoCountry().split(","));
            List<Country> countries = countryMapper.selectList(countryQueryWrapper);
            omsPriApplyVO.setCountries(countries);
        }
        //实际去往国家
        if (!StringUtils.isBlank(omsPriApplyVO.getRealGoCountry())) {
            countryQueryWrapper.clear();
            countryQueryWrapper.in("ID", omsPriApplyVO.getRealGoCountry().split(","));
            omsPriApplyVO.setRealCountries(countryMapper.selectList(countryQueryWrapper));
        }
        //实际途径国家
        if (!StringUtils.isBlank(omsPriApplyVO.getRealPassCountry())) {
            countryQueryWrapper.clear();
            countryQueryWrapper.in("ID", omsPriApplyVO.getRealPassCountry().split(","));
            omsPriApplyVO.setRealPassCountries(countryMapper.selectList(countryQueryWrapper));
        }
    }
}
