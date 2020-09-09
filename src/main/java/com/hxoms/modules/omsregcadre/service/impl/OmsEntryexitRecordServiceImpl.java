package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.keySupervision.suspendApproval.mapper.OmsSupSuspendUnitMapper;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.mapper.OmsEntryexitRecordCompbatchMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsEntryexitRecordMapper;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper.OmsCerCancellateLicenseMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApply;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.mapper.OmsPriDelayApplyMapper;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubApplyQueryParam;
import com.hxoms.modules.publicity.service.OmsPubApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OmsEntryexitRecordServiceImpl extends ServiceImpl<OmsEntryexitRecordMapper, OmsEntryexitRecord> implements OmsEntryexitRecordService {

    @Autowired
    private OmsPriApplyMapper priApplyMapper;
    @Autowired
    private OmsEntryexitRecordCompbatchMapper entryexitRecordCompbatchMapper;
    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    private OmsSupSuspendUnitMapper omsSupSuspendUnitMapper;
    @Autowired
    private OmsPubApplyService pubApplyService;
    @Autowired
    private OmsEntryexitRecordService omsEntryexitRecordService;
    @Autowired
    private OmsCerCancellateLicenseMapper cerCancellateLicenseMapper;
    @Autowired
    private OmsPriDelayApplyMapper omsPriDelayApplyMapper;


    @Override
    public PageInfo<OmsEntryexitRecord> getEntryexitRecordinfo(OmsEntryexitRecordIPagParam entryexitRecordIPagParam) {
        //分页
        PageUtil.pageHelp(entryexitRecordIPagParam.getPageNum(), entryexitRecordIPagParam.getPageSize());
        List<OmsEntryexitRecord> entryexitRecordsList = baseMapper.selectEntryexitRecordIPage(entryexitRecordIPagParam);
        //返回数据
        PageInfo<OmsEntryexitRecord> pageInfo = new PageInfo(entryexitRecordsList);
        return pageInfo;
    }



    @Override
    public Object insertEntryexitRecord(OmsEntryexitRecord entryexitRecord) {
        entryexitRecord.setId(UUIDGenerator.getPrimaryKey());
        //数据来源 1.手录  2导入
        entryexitRecord.setDataSource("1");
        return baseMapper.insert(entryexitRecord);
    }

    @Override
    public Object updateEntryexitRecord(OmsEntryexitRecord entryexitRecord) {
        return baseMapper.updateById(entryexitRecord);
    }

    @Override
    public Object deleteEntryexitRecord(String id) {
        return baseMapper.deleteById(id);
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> selectCompareInfo(OmsPriApply priapply, OmsEntryexitRecord outinfo, OmsEntryexitRecord joininfo) {
        Map<String, Object> map = new HashMap<>();
        if (priapply!=null && outinfo!=null && joininfo!=null){
            entryexitRecordCompare(null);
        }else{
            throw new CustomMessageException("请选择数据后进行匹配");
        }
        return map;
    }

    @Override
    public Object clockGoAbroadApply(OmsSupSuspendUnit supSuspendUnit) {
        int con=0;
        if (supSuspendUnit!=null && supSuspendUnit.getSuspendTime()!=null && supSuspendUnit.getPauseTime()!=null){
            //登录用户信息
            UserInfo userInfo = UserInfoUtil.getUserInfo();
            supSuspendUnit.setId(UUIDGenerator.getPrimaryKey());
            supSuspendUnit.setCreateUser(userInfo.getId());
            supSuspendUnit.setCreateTime(new Date());
            supSuspendUnit.setStatus("0");
            con = omsSupSuspendUnitMapper.insert(supSuspendUnit);
        }else{
            throw new CustomMessageException("缺少必要参数");
        }
        return con;
    }

    @Override
    public int cancelCompareInfo(String id) {
        OmsPriApply priapply = new OmsPriApply();
        priapply.setId(id);
        priapply.setIsComparison("0");
        int con = priApplyMapper.updateById(priapply);
        if (con > 0){
            UpdateWrapper<OmsEntryexitRecord> updateWrapper = new UpdateWrapper<OmsEntryexitRecord>();
            updateWrapper.eq("PRIAPPLY_ID",id);
            OmsEntryexitRecord entryexitRecord = new OmsEntryexitRecord();
            entryexitRecord.setPriapplyId(null);
            entryexitRecord.setComparisonResult(null);
            con = baseMapper.update(entryexitRecord,updateWrapper);
        }
        return con;
    }


    @Override
    public Map<String, Object> queryPriApplyList(String omsId) {
        entryexitRecordCompare(omsId);
        Map<String, Object> map = this.selectComparisionList(omsId);
        return map;
    }

    /**
     * 批量比对
     * @param omsIds
     * @return
     */
    @Override
    public Object batchPriApplyList (List<String> omsIds) {
        OmsEntryexitRecordCompbatch info = new OmsEntryexitRecordCompbatch();
        //查询批次表中是否存在进行中的批次
        QueryWrapper<OmsEntryexitRecordCompbatch> compbatchWrapper = new QueryWrapper<OmsEntryexitRecordCompbatch>();
        compbatchWrapper.eq("STATUS", 1);
        int coun = entryexitRecordCompbatchMapper.selectCount(compbatchWrapper);
        if (coun > 0) {
            throw new CustomMessageException("因为前一比对还在进行，不能进行新的比对工作");
        }
        //TODO:已取消的处理待定

        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //新建批次
        OmsEntryexitRecordCompbatch eRecordCompbatch = new OmsEntryexitRecordCompbatch();
        eRecordCompbatch.setId(UUIDGenerator.getPrimaryKey());
        eRecordCompbatch.setBatchNo("" + new Date().getTime());
        eRecordCompbatch.setCreateUser(userInfo.getId());
        eRecordCompbatch.setStartDate(new Date());
        eRecordCompbatch.setCompareSum(omsIds.size());
        int con = entryexitRecordCompbatchMapper.insert(eRecordCompbatch);
        if (con > 0) {
            //批量比对
            if (omsIds != null && omsIds.size() > 0) {
                for (int x = 0; x < omsIds.size(); x++) {
                    String omsId = omsIds.get(x);
                    OmsRegProcpersoninfo reg = new OmsRegProcpersoninfo();
                    reg.setId(omsId);
                    //调用比对方法
                    entryexitRecordCompare(omsId);
                    eRecordCompbatch.setCurrentFinishsum(x+1);
                    eRecordCompbatch.setStatus("2");
                    entryexitRecordCompbatchMapper.updateById(eRecordCompbatch);
                }
            }
        }
        QueryWrapper<OmsEntryexitRecordCompbatch>  queryWrapper = new QueryWrapper<OmsEntryexitRecordCompbatch>();
        queryWrapper.orderByDesc("START_DATE");
        List<OmsEntryexitRecordCompbatch> list = entryexitRecordCompbatchMapper.selectList(queryWrapper);
        if (list!=null && list.size()>0){
            info = list.get(0);
        }
        return info;
    }




    /**
     * 查询比对结果列表
     * @return
     */
    private Map<String, Object> selectComparisionList (String omsId){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<OmsPriApply> priApplyWrapper = new QueryWrapper<OmsPriApply>();
        priApplyWrapper.eq("PROCPERSON_ID", omsId);

        QueryWrapper<OmsEntryexitRecord> exitWrapper = new QueryWrapper<OmsEntryexitRecord>();
        exitWrapper.eq("PROCPERSON_ID", omsId);
        exitWrapper.isNotNull("PRIAPPLY_ID");

        //因私申请出国境记录查询
        List<OmsPriApply> priApplyList = priApplyMapper.selectList(priApplyWrapper);
        List<OmsEntryexitRecord> entryexitRecordslist = baseMapper.selectList(exitWrapper);
        List<OmsEntryexitRecord> noMatchList = baseMapper.selectNoMatchList(omsId);
        map.put("priApplyList", priApplyList);
        map.put("entryexitRecordslist", entryexitRecordslist);
        map.put("noMatchList", noMatchList);
        return map;
    }

    /**
     * @description: 填写有关情况报告时进行比对，检查出入境记录是否合规
     * @author:李姣姣
     * @date:2020-08-19
     *  * @param apply 因私出国申请
     * @return:void
     **/
    @Override
    public void verifySituationReport(OmsPriApply apply){
        //没有出国，不处理
        if("0".equals(apply.getIsAbroad())) return;

        //跟罗帅协商获取禁止性、限制性、敏感性国家和地区
        Map<String, String> sensitiveCountry = new HashMap<>();
        List<Map<String, String>> sensitiveCountrys = priApplyMapper.selectSensitiveCountry();
        if(sensitiveCountrys != null && sensitiveCountrys.size() > 0){
            for (Map<String, String> item : sensitiveCountrys){
                sensitiveCountry.put(item.get("name"),item.get("type"));
            }
        }
        //证照信息
        QueryWrapper<OmsCerCancellateLicense> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("OMS_ID",apply.getProcpersonId());
        List<Integer> zjlx = new ArrayList();
        if (apply.getPassport() != null){
            zjlx.add(1);
        }
        if (apply.getHongkongandmacaoPassport() != null){
            zjlx.add(2);
        }
        if (apply.getTaiwanPassport() != null){
            zjlx.add(4);
        }
        if (zjlx != null && zjlx.size() > 0){
            queryWrapper.in("ZJLX", zjlx);
        }
        //自己把出入境记录关联证照信息获取注销时间和证照状态
        List<OmsCerCancellateLicense> info = cerCancellateLicenseMapper.selectList(queryWrapper);
        //查询前往国家名称
        //国家列表
        String oldCountry = "";
        String newCountry = "";
        QueryWrapper<Country> countryQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(apply.getGoCountry())){
            countryQueryWrapper.in("id", apply.getGoCountry().split(","));
            List<Country> countries = countryMapper.selectList(countryQueryWrapper);
            for (Country country : countries) {
                oldCountry += country.getNameZh() + ",";
            }
        }
        //实际去往国家
        if (!StringUtils.isBlank(apply.getRealGoCountry())){
            String ids = apply.getRealGoCountry();
            if (!StringUtils.isBlank(apply.getRealPassCountry())){
                ids += "," + apply.getRealPassCountry();
            }
            countryQueryWrapper.clear();
            countryQueryWrapper.in("ID", ids.split(","));
            List<Country> countries = countryMapper.selectList(countryQueryWrapper);
            for (Country country : countries) {
                newCountry += country.getNameZh() + ",";
            }
        }
        String result =  EntryexitRecordChecking(apply.getApplyTime(),apply.getId(),
                apply.getAbroadTime(),apply.getReturnTime(),oldCountry.substring(0,oldCountry.length()-1),
                apply.getRealAbroadTime(),apply.getRealReturnTime(),newCountry.substring(0,newCountry.length()-1),
                sensitiveCountry,info);
        //保存比对结果isComparison是否已比对是以出入境记录比对时使用，这里不能设置值
        if (result == null){
            apply.setComparisonDate(new Date());
            apply.setComparisonResult("正常");
        }else {
            apply.setComparisonDate(new Date());
            apply.setComparisonResult(result);
        }

        priApplyMapper.updateById(apply);
    }
    /**
     * @description: 单人出入境记录比对
     * @author:李姣姣
     * @date:2020-08-20
     *  * @param reg 登记备案人员信息
     * @return:void
     **/
    @Override
    public void entryexitRecordCompare(String omsId) {
        int con=0;
        //查询未比对的因私出国境申请
        OmsPriApplyIPageParam pp=new OmsPriApplyIPageParam();
        //只查询已办结
        pp.setApplyStatus(new Integer[]{28});
        //查询指定备案人员的出国境申请
        pp.setProcpersonId(omsId);
        //只查询未比对的
        pp.setIsComparison("0");
        //注意要按申请时间升序排序，后面的出入境记录只取第一条申请记录申请时间之后的
        List<OmsPriApplyVO> apps = priApplyMapper.selectOmsPriApplyIPage(pp);
        //没有申请不做比对
        if(apps.size()==0) return;

        //查询未比对的因公赴台申请
        OmsPubApplyQueryParam pqp=new OmsPubApplyQueryParam();
        //只查询已办结
        List<Integer> status=new ArrayList<>(28);
        pqp.setStatus(status);
        //查询指定备案人员的出国境申请
        pqp.setProcpersonId(omsId);
        //只查询未比对的
        pqp.setIsComparison("0");
        //只查询赴台的
        pqp.setPwh("琼台赴字");
        List<OmsPubApply> omsPubApplies = pubApplyService.getPubAppListByCondition(pqp).getList();

        Date applyDate=apps.get(0).getApplyTime();
        //查询未比对的因私出国境记录
        OmsEntryexitRecordIPagParam entryexitRecordIPagParam=new OmsEntryexitRecordIPagParam();
        //注意修改SQL，用大于等于申请日期之后的出入境记录
        entryexitRecordIPagParam.setOgeDate(applyDate);
        //查询因私出国境申请id为空的记录（未比对）
        entryexitRecordIPagParam.setPriapplyId(null);
        //查询指定人员的出入境记录
        entryexitRecordIPagParam.setOmsId(omsId);
        //注意要按出国境时间排序（升序）
        List<OmsEntryexitRecord> omsEntryexitRecords = omsEntryexitRecordService.getEntryexitRecordinfo(entryexitRecordIPagParam).getList();
        //没有出入境记录不做比对
        if(omsEntryexitRecords.size()==0)return;

        //跟罗帅协商获取禁止性、限制性、敏感性国家和地区
        Map<String, String> plsCountry = new HashMap<>();
        List<Map<String, String>> plsCountrys = priApplyMapper.selectPLSCountry();
        if(plsCountrys != null && plsCountrys.size() > 0){
            for (Map<String, String> item : plsCountrys){
                plsCountry.put(item.get("name"),item.get("type"));
            }
        }

        for(int i=0;i<=omsEntryexitRecords.size()-1;)
        {
            OmsEntryexitRecord recOut=omsEntryexitRecords.get(i);
            OmsEntryexitRecord recIn = null;
            if(i<omsEntryexitRecords.size()-1)
                recIn=omsEntryexitRecords.get(i+1);

            Date exitDate=null;
            Date entryDate=null;
            String country="";
            recOut.setComparisonResult("");
            if(recIn!=null)recIn.setComparisonResult("");

            //出入境配对
            if(recOut.getOgeStatus()== Constants.OGE_STATUS_CODE[0] && recIn!=null && recIn.getOgeStatus()==Constants.OGE_STATUS_CODE[1])
            {
                exitDate=recOut.getOgeDate();
                entryDate=recIn.getOgeDate();
                country=recOut.getDestination()+","+recIn.getDestination();
                i+=2;
            }
            //入境记录丢失
            else if(recOut.getOgeStatus()==Constants.OGE_STATUS_CODE[0] &&(recIn==null ||recIn.getOgeStatus()==Constants.OGE_STATUS_CODE[0]))
            {
                exitDate=recOut.getOgeDate();
                country=recOut.getDestination();
                recOut.setComparisonResult("入境记录丢失");
                i+=1;
            }
            //出境记录丢失
            else if(recOut.getOgeStatus()==Constants.OGE_STATUS_CODE[1] )
            {
                entryDate=recOut.getOgeDate();
                country=recOut.getDestination();
                recOut.setComparisonResult("出境记录丢失");
                i+=1;
            }
            boolean hasApply=false;
            for (OmsPriApplyVO  app: apps){
                if(app.getApplyTime().before(exitDate) && app.getReturnTime().after(exitDate)){
                    hasApply=true;
                    QueryWrapper<OmsCerCancellateLicense> queryWrapper = new QueryWrapper<OmsCerCancellateLicense>();
                    queryWrapper.eq("OMS_ID",recOut.getOmsId());
                    List<Integer> zjlx = new ArrayList();
                    if (app.getPassport() != null){
                        zjlx.add(1);
                    }
                    if (app.getHongkongandmacaoPassport() != null){
                        zjlx.add(2);
                    }
                    if (app.getTaiwanPassport() != null){
                        zjlx.add(4);
                    }
                    if (zjlx != null && zjlx.size() > 0){
                        queryWrapper.in("ZJLX", zjlx);
                    }
                    //自己把出入境记录关联证照信息获取注销时间和证照状态
                    List<OmsCerCancellateLicense> zzlist = cerCancellateLicenseMapper.selectList(queryWrapper);
                    String result =  EntryexitRecordChecking(app.getApplyTime(),app.getId(),
                            app.getRealAbroadTime(),app.getRealReturnTime(),app.getRealGoCountry(),
                            exitDate,entryDate,country,
                            plsCountry,zzlist);
                    if (result == null){
                        recOut.setPriapplyId(app.getId());
                        recOut.setComparisonDate(new Date());
                        recOut.setComparisonResult("正常");
                    }else {
                        recOut.setPriapplyId(app.getId());
                        recOut.setComparisonDate(new Date());
                        recOut.setComparisonResult(recOut.getComparisonResult()+"\r\n"+result);
                    }
                    if(recIn!=null)
                    {
                        recIn.setComparisonResult(recIn.getComparisonResult()+"\r\n"+result);
                        recOut.setComparisonDate(new Date());
                        recIn.setPriapplyId(app.getId());
                    }
                }
            };
            //检查因公赴台记录
            if(hasApply==false && omsPubApplies.size()>0){
                for (OmsPubApply  app: omsPubApplies){
                    if(app.getCreateTime().before(exitDate) && app.getHgsj().after(exitDate))
                    {
                        hasApply=true;
                        //自己把出入境记录关联证照信息获取注销时间和证照状态
                        QueryWrapper<OmsCerCancellateLicense> queryWrapper = new QueryWrapper<OmsCerCancellateLicense>();
                        queryWrapper.eq("OMS_ID",recOut.getOmsId());
                        //自己把出入境记录关联证照信息获取注销时间和证照状态
                        List<OmsCerCancellateLicense> zzlist = cerCancellateLicenseMapper.selectList(queryWrapper);
                        String result =  EntryexitRecordChecking(app.getCreateTime(),app.getId(),
                                app.getSjcgsj(),app.getSjhgsj(),app.getSdgj(),
                                exitDate,entryDate,country,
                                plsCountry,zzlist);
                        if (result == null){
                            recOut.setPriapplyId(app.getId());
                            recOut.setComparisonDate(new Date());
                            recOut.setComparisonResult("正常");
                        }else {
                            recOut.setComparisonResult(recOut.getComparisonResult()+"\r\n"+result);
                            recOut.setPriapplyId(app.getId());
                            recOut.setComparisonDate(new Date());
                        }
                        if(recIn!=null)
                        {
                            recIn.setComparisonResult(recIn.getComparisonResult()+"\r\n"+result);
                            recIn.setComparisonDate(new Date());
                            recIn.setPriapplyId(app.getId());
                        }
                    }
                }
            }
            if(hasApply==false){
                recOut.setComparisonResult(recOut.getComparisonResult()+"\r\n未经申请的出国境");
                if(recIn!=null)
                {
                    recIn.setComparisonDate(new Date());
                    recIn.setComparisonResult(recOut.getComparisonResult()+"\r\n未经申请的出国境");
                }
            }
            //保存出入境记录
            omsEntryexitRecordService.updateEntryexitRecord(recOut);
            if(recIn!=null)
            {
                omsEntryexitRecordService.updateEntryexitRecord(recIn);
            }
        }
    }




    /**
     * @description:出入境记录检查
     * @author:李姣姣
     * @date:2020-08-19
     * @param applyDate 申请出国境日期
     * @param applyID 出国境申请主键
     * @param oldExit 被比较出境时间
     * @param oldEntry 被比较入境时间
     * @param oldDestination 被比较目的地
     * @param newExit 用于比较的出境时间
     * @param newEntry 用于比较的入境时间
     * @param newDestination 用于比较的目的地
     * @param sensitiveCountry 禁止性、限制性、敏感性国家或地区
     * @param zzlist 证照信息
     * @return:java.lang.String
     **/
    @Override
    public String EntryexitRecordChecking(Date applyDate,String applyID,
                                          Date oldExit,Date oldEntry,String oldDestination,
                                          Date newExit,Date newEntry,String newDestination,
                                          Map<String, String> sensitiveCountry,
                                          List<OmsCerCancellateLicense> zzlist){
        String result="";
        //检查是否持注销证照出入境
        if(zzlist!=null){
            for (int i=0;i<zzlist.size();i++){
                if (zzlist.get(i).getCreateTime()!=null
                        && zzlist.get(i).getCardStatus()=="注销"
                        && zzlist.get(i).getYxqz().before(oldExit)){
                    result="持已注销证照出入境\r\n";
                }
            }
        }
        //检查是否在申请时间之后，入境时间之前入境，程序不应该进入这个判断中，因为本方法之外已经做过判断
        if(newExit.before(applyDate)||newExit.after(oldEntry))
        {
            result+="未经审批的出入境\r\n";
        }

        //判断是否有延期入境申请
        boolean delay=CheckDelay(applyID,newEntry);
        //没有申请延期，并且入境时间超过计划入境时间5天以上
        if(delay==false && newEntry.compareTo(oldEntry)>5)
        {
            result+="未经申请延期入境\r\n";
        }
        String[] dests=newDestination.split(",");
        String oldDest=","+oldDestination+",";
        for(int i=0;i<dests.length;i++)
        {
            if(dests[i].isEmpty()==false && oldDest.contains(","+dests[i]+",")==false)
            {
                result+="擅自变更行程（"+dests[i]+"）\r\n";
            }
            if(sensitiveCountry.containsKey(dests[i]))
            {
                result+="前往了"+sensitiveCountry.get(dests[i])+"（"+dests[i]+"）\r\n";
            }
        }

        return result;
    }


    @Override
    public Map<String, Object> queryCompresultByYear(OmsEntryexitRecordIPagParam entryexitRecordIPagParam) {
        Map<String, Object> map = new HashMap<>();

        OmsEntryexitRecordVO info = baseMapper.queryCompresultByYear(entryexitRecordIPagParam.getYear());
        //分页
        PageUtil.pageHelp(entryexitRecordIPagParam.getPageNum(), entryexitRecordIPagParam.getPageSize());
        //查询异常list
        List<OmsEntryexitRecordVO> exceptionRecordsList = baseMapper.getExceptionPriApply(entryexitRecordIPagParam);
        //组装出入境记录后的list
        List<OmsEntryexitRecordVO> exceptionlist = getNewList(exceptionRecordsList);
        //返回数据
        PageInfo<OmsEntryexitRecordVO> pageInfo = new PageInfo(exceptionlist);
        map.put("info",info);
        map.put("pageInfo",pageInfo);
        return map;
    }

    private List<OmsEntryexitRecordVO> getNewList(List<OmsEntryexitRecordVO> exceptionRecordsList) {
        List<OmsEntryexitRecordVO> exceptionlist = new ArrayList<>();
        if (exceptionRecordsList!=null && exceptionRecordsList.size()>0){
            for(int i=0;i<=exceptionRecordsList.size()-1;) {
                OmsEntryexitRecordVO vo = new OmsEntryexitRecordVO();
                OmsEntryexitRecordVO recOut = exceptionRecordsList.get(i);
                OmsEntryexitRecordVO recIn = null;
                if (i < exceptionRecordsList.size() - 1){
                    recIn = exceptionRecordsList.get(i + 1);
                }
                //出入境配对
                if (recOut.getOgeStatus() == Constants.OGE_STATUS_CODE[0] && recIn != null && recIn.getOgeStatus() == Constants.OGE_STATUS_CODE[1]) {
                    vo=recOut;
                    vo.setRealAbroadTime(recOut.getOgeDate());
                    vo.setRealReturnTime(recIn.getOgeDate());
                    exceptionlist.add(vo);
                    i += 2;
                }
                //入境记录丢失
                else if(recOut.getOgeStatus()==Constants.OGE_STATUS_CODE[0] &&(recIn==null ||recIn.getOgeStatus()==Constants.OGE_STATUS_CODE[0]))
                {
                    vo=recOut;
                    vo.setRealAbroadTime(recOut.getOgeDate());
                    exceptionlist.add(vo);
                    i+=1;
                }
                //出境记录丢失
                else if(recOut.getOgeStatus()==Constants.OGE_STATUS_CODE[1] )
                {
                    vo=recOut;
                    vo.setRealReturnTime(recOut.getOgeDate());
                    exceptionlist.add(vo);
                    i+=1;
                }

            }
        }
        return exceptionlist;
    }




    @Override
    public List<OmsEntryexitRecordVO> queryExceptionPriApplyList(String omsId) {
        OmsEntryexitRecordIPagParam record = new OmsEntryexitRecordIPagParam();
        record.setOmsId(omsId);
        //查询异常list
        List<OmsEntryexitRecordVO> exceptionRecordsList = baseMapper.getExceptionPriApply(record);
        //组装出入境记录后的list
        List<OmsEntryexitRecordVO> exceptionPriApplylist = getNewList(exceptionRecordsList);
        return exceptionPriApplylist;
    }

    @Override
    public PageInfo<OmsEntryexitRecordVO> getExceptionRecord(OmsEntryexitRecordIPagParam entryexitRecordIPagParam) {
        //分页
        PageUtil.pageHelp(entryexitRecordIPagParam.getPageNum(), entryexitRecordIPagParam.getPageSize());
        List<OmsEntryexitRecordVO> exceptionRecordsList = baseMapper.getExceptionPriApply(entryexitRecordIPagParam);
        //返回数据
        PageInfo<OmsEntryexitRecordVO> pageInfo = new PageInfo(exceptionRecordsList);
        return pageInfo;
    }

    @Override
    public List<OmsEntryexitRecordModel> newexitRecordsList(List<String> ids) {
        return baseMapper.newexitRecordsList(ids);
    }

    private boolean CheckDelay(String applyID,Date newEntry) {
        boolean flag = false;
        QueryWrapper<OmsPriDelayApply> queryWrapper = new QueryWrapper<OmsPriDelayApply>();
        queryWrapper.eq("APPLY_ID",applyID);
        queryWrapper.orderByDesc("ESTIMATE_RETURNTIME");
        List<OmsPriDelayApply> list = omsPriDelayApplyMapper.selectList(queryWrapper);
        if (list!=null && list.size()>0){
            //若入境时间在申请回国时间之后（延期入境）
            if (list.get(0).getEstimateReturntime()!=null
                    && newEntry.after(list.get(0).getEstimateReturntime())){
                flag = false;
            }else{
                flag = true;
            }
        }
        return flag;
    }


}

