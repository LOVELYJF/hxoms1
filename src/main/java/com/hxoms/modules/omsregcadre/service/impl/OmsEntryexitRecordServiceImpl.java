package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.keySupervision.suspendApproval.mapper.OmsSupSuspendUnitMapper;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecordCompbatch;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.mapper.OmsEntryexitRecordCompbatchMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsEntryexitRecordMapper;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriPubApply;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.sensitiveCountry.sensitiveLimited.mapper.OmsSensitiveLimitMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OmsEntryexitRecordServiceImpl extends ServiceImpl<OmsEntryexitRecordMapper, OmsEntryexitRecord> implements OmsEntryexitRecordService {

    @Autowired
    private OmsPriApplyMapper priApplyMapper;
    @Autowired
    private OmsEntryexitRecordCompbatchMapper entryexitRecordCompbatchMapper;
    @Autowired
    private CountryMapper countryMapper;
    @Autowired
    private OmsSensitiveLimitMapper omsSensitiveLimitMapper;
    @Autowired
    private OmsSupSuspendUnitMapper omsSupSuspendUnitMapper;


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
            int con = compareInfo(priapply, outinfo, joininfo);
            if(con > 0){
                map = this.selectComparisionList(priapply.getA0100());
            }else{
                throw new CustomMessageException("当前选择数据无法进行匹配");
            }
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
    public Map<String, Object> cancelCompareInfo(String id) {
        OmsPriApply priapply = new OmsPriApply();
        priapply.setId(id);
        priapply.setIsComparison("0");
        int con = priApplyMapper.updateById(priapply);
        if (con > 0){
            OmsEntryexitRecord entryexitRecord = new OmsEntryexitRecord();
            entryexitRecord.setPriapplyId(null);
            baseMapper.update();
        }
        return null;
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> queryPriApplyList(String a0100) {
        int con = this.selectAndCompareInfo(a0100);
        Map<String, Object> map = this.selectComparisionList(a0100);
        return map;
    }

    private int selectAndCompareInfo(String a0100) {
        int con=0;
        Map<String,Object> param = new HashMap<>();
        param.put("a0100",a0100);
        //比对标识，默认为未比对
        param.put("isComparison",'0');
        //因私申请出国境记录查询
        List<OmsPriPubApply> pripubApplyList = priApplyMapper.selectPriPubList(a0100);
        QueryWrapper<OmsEntryexitRecord> exitWrapper = new QueryWrapper<OmsEntryexitRecord>();
        exitWrapper.eq("A0100", a0100);
        //比对结果为null的出入境记录查询
        exitWrapper.eq("COMPARISON_RESULT", null);
        exitWrapper.eq("OGE_STATUS", '1');
        exitWrapper.orderByAsc("OGE_DATE");
        //出境记录list
        List<OmsEntryexitRecord> outlist = baseMapper.selectList(exitWrapper);
        //入境记录list
        exitWrapper.eq("OGE_STATUS", '2');
        List<OmsEntryexitRecord> joinlist = baseMapper.selectList(exitWrapper);
        OmsEntryexitRecord outinfo = new OmsEntryexitRecord();
        OmsEntryexitRecord joininfo = new OmsEntryexitRecord();
        if (pripubApplyList != null && pripubApplyList.size() > 0 && outlist != null && outlist.size() > 0) {
            for (int i = 0; i < pripubApplyList.size(); i++) {
                OmsPriApply priapply = new OmsPriApply();
                OmsPriPubApply pripubapply = pripubApplyList.get(i);
                //只有公安出入境记录，没有填写出入境记录
                if (pripubapply.getCgsj() != null && pripubapply.getSjcgsj() == null) {
                    pripubapply.setIsComparison("1");
                    //TODO:请检查是否有两本护照
                    BeanUtils.copyProperties(pripubapply, priapply);
                    con = priApplyMapper.updateById(priapply);
                } else {
                    boolean out = false;
                    boolean join = false;
                    for (int j = 0; j < outlist.size(); j++) {
                        outinfo = outlist.get(j);
                        if (outinfo.getOgeDate().after(pripubapply.getApplyTime())
                                && outinfo.getOgeDate().before(pripubapply.getSjhgsj())) {
                            out = true;
                            outlist.remove(j);
                            break;
                        }
                    }
                    for (int x = 0; x < joinlist.size(); x++) {
                        joininfo = outlist.get(x);
                        if (x < joinlist.size() + 1) {
                            if (joininfo.getOgeDate().after(outinfo.getOgeDate())
                                    && joininfo.getOgeDate().after(pripubapply.getSjcgsj())) {
                                join = true;
                                joinlist.remove(x);
                                break;
                            }
                        } else {
                            if (joininfo.getOgeDate().after(outinfo.getOgeDate())) {
                                join = true;
                                joinlist.remove(x);
                                break;
                            }
                        }
                    }
                    if (out = join = true) {
                        BeanUtils.copyProperties(pripubapply, priapply);
                        con = compareInfo(priapply, outinfo, joininfo);
                    } else {
                        BeanUtils.copyProperties(pripubapply, priapply);
                        priapply.setIsComparison("1");
                        outinfo.setComparisonResult("记录不完整");
                        outinfo.setPriapplyId(priapply.getId());

                        joininfo.setComparisonResult("记录不完整");
                        joininfo.setPriapplyId(priapply.getId());
                        con = priApplyMapper.updateById(priapply);
                        if (con > 0) {
                            baseMapper.updateById(outinfo);
                            baseMapper.updateById(joininfo);
                        }

                    }

                }
            }
        }
        return con;
    }

    private int compareInfo (OmsPriApply priapply, OmsEntryexitRecord outinfo, OmsEntryexitRecord joininfo){
            int con = 0;
            //比对出境时间：出入境时间与因私出国实际出入国境时间一致
            if (outinfo.getOgeDate().compareTo(priapply.getRealAbroadTime()) == 0 && joininfo.getOgeDate().compareTo(priapply.getRealAbroadTime()) == 0) {
                //根据出入境国家查询对应国家id
                String outPlaceId = countryMapper.selectIdByName(outinfo.getDestination());
                String joinPlaceId = countryMapper.selectIdByName(joininfo.getDestination());
                //查询禁止性国家
                List<Integer> jzlist = omsSensitiveLimitMapper.getSensitiveMaintain("1");
                //查询限制性国家
                List<Integer> xzlist = omsSensitiveLimitMapper.getSensitiveMaintain("2");
                //查询敏感性国家
                List<Integer> mglist = omsSensitiveLimitMapper.getSensitiveMaintain("3");
                if (jzlist.contains(outPlaceId)) {
                    priapply.setIsComparison("1");
                    outinfo.setComparisonResult("到达禁止性国家或地区");
                } else if (jzlist.contains(joinPlaceId)) {
                    priapply.setIsComparison("1");
                    joininfo.setComparisonResult("到达禁止性国家或地区");
                } else if (xzlist.contains(outPlaceId)) {
                    priapply.setIsComparison("1");
                    outinfo.setComparisonResult("到达限制性国家或地区");
                } else if (xzlist.contains(joinPlaceId)) {
                    priapply.setIsComparison("1");
                    joininfo.setComparisonResult("到达限制性国家或地区");
                } else if (mglist.contains(outPlaceId)) {
                    priapply.setIsComparison("1");
                    outinfo.setComparisonResult("到达敏感性国家或地区");
                } else if (mglist.contains(joinPlaceId)) {
                    priapply.setIsComparison("1");
                    joininfo.setComparisonResult("到达敏感性国家或地区");
                } else {
                    String mudd = ","+priapply.getRealPassCountry()+","+priapply.getRealGoCountry()+",";
                    String cjmudd = ","+outPlaceId+",";
                    String rjmudd = ","+joinPlaceId+",";
                    if (mudd.contains(cjmudd) && mudd.contains(rjmudd)) {
                        //进行出访目的地比对
                        priapply.setIsComparison("1");
                        outinfo.setComparisonResult("正确");
                        joininfo.setComparisonResult("正确");
                    } else {
                        //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                        priapply.setIsComparison("1");
                        outinfo.setComparisonResult("出访目的地不匹配");
                        joininfo.setComparisonResult("出访目的地不匹配");
                    }
                }
            } else {
                //出境时间不一致的（并且前后相差超过5天的），提醒干部监督处核查，
                if (OmsRegInitUtil.dayDiff(outinfo.getOgeDate(), priapply.getRealAbroadTime()) >= 5) {
                    //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                    priapply.setIsComparison("1");
                    //将出入境记录的比对结果置为“出入境时间不匹配，并且没有申请变更”，
                    outinfo.setComparisonResult("出境时间不匹配，并且没有申请变更");
                }
            }
            outinfo.setPriapplyId(priapply.getId());
            joininfo.setPriapplyId(priapply.getId());
            con = priApplyMapper.updateById(priapply);
            if (con > 0) {
                baseMapper.updateById(outinfo);
                baseMapper.updateById(joininfo);
            }
            return con;

        }


        /**
         * 查询比对结果列表
         * @return
         */
        private Map<String, Object> selectComparisionList (String a0100){
            Map<String, Object> map = new HashMap<>();
            QueryWrapper<OmsPriApply> priApplyWrapper = new QueryWrapper<OmsPriApply>();
            priApplyWrapper.eq("A0100", a0100);

            QueryWrapper<OmsEntryexitRecord> exitWrapper = new QueryWrapper<OmsEntryexitRecord>();
            exitWrapper.eq("A0100", a0100);
            exitWrapper.isNotNull("PRIAPPLY_ID");

            //因私申请出国境记录查询
            List<OmsPriApply> priApplyList = priApplyMapper.selectList(priApplyWrapper);
            List<OmsEntryexitRecord> entryexitRecordslist = baseMapper.selectList(exitWrapper);
            List<OmsEntryexitRecord> noMatchList = baseMapper.selectNoMatchList(a0100);
            map.put("priApplyList", priApplyList);
            map.put("entryexitRecordslist", entryexitRecordslist);
            map.put("noMatchList", noMatchList);
            return map;
        }

        /**
         * 批量比对
         *
         * @param a0100s
         * @return
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        public Object batchPriApplyList (List < String > a0100s) {
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
            eRecordCompbatch.setCompareSum(a0100s.size());
            int con = entryexitRecordCompbatchMapper.insert(eRecordCompbatch);
            if (con > 0) {
                //批量比对
                if (a0100s != null && a0100s.size() > 0) {
                    for (int x = 0; x < a0100s.size(); x++) {
                        String a0100 = a0100s.get(x);
                        int conn = this.selectAndCompareInfo(a0100);
                        if (conn > 0){
                            eRecordCompbatch.setCurrentFinishsum(Integer.parseInt(a0100s.get(x)));
                            eRecordCompbatch.setStatus("2");
                            entryexitRecordCompbatchMapper.updateById(eRecordCompbatch);
                        }
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

    }

