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
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecordCompbatch;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.mapper.OmsEntryexitRecordCompbatchMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsEntryexitRecordMapper;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.mapper.CfCertificateMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class OmsEntryexitRecordServiceImpl extends ServiceImpl<OmsEntryexitRecordMapper, OmsEntryexitRecord> implements OmsEntryexitRecordService {

    @Autowired
    private OmsPriApplyMapper priApplyMapper;
    @Autowired
    private CfCertificateMapper cfCertificateMapper;
    @Autowired
    private OmsEntryexitRecordCompbatchMapper entryexitRecordCompbatchMapper;


    @Override
    public PageInfo<OmsEntryexitRecord> getEntryexitRecordinfo(OmsEntryexitRecordIPagParam entryexitRecordIPagParam) {
        //分页
        PageUtil.pageHelp(entryexitRecordIPagParam.getPageNum(), entryexitRecordIPagParam.getPageSize());
        List<OmsEntryexitRecord> entryexitRecordsList = baseMapper.selectEntryexitRecordIPage(entryexitRecordIPagParam);
        //返回数据
        PageInfo<OmsEntryexitRecord> pageInfo = new PageInfo(entryexitRecordsList);
        return pageInfo;
    }

    // @Override
    @Transactional(rollbackFor=Exception.class)
    public Map<String, Object> queryPriApplyList(String a0100) {
        QueryWrapper<OmsPriApply> priApplyWrapper = new QueryWrapper<OmsPriApply>();
        priApplyWrapper.eq("A0100", a0100);
        //比对标识，默认为未比对
        priApplyWrapper.eq("IS_COMPARISON", '0');
        //因私申请出国境记录查询
        List<OmsPriApply> priApplyList = priApplyMapper.selectList(priApplyWrapper);

        QueryWrapper<OmsEntryexitRecord> exitWrapper = new QueryWrapper<OmsEntryexitRecord>();
        exitWrapper.eq("A0100", a0100);
        //比对结果为null的出入境记录查询
        exitWrapper.eq("COMPARISON_RESULT", null);
        List<OmsEntryexitRecord> entryexitRecordsList = baseMapper.selectList(exitWrapper);

        //证件类型（护照1、港澳通行证2、台湾通行证4 ）
        CfCertificate certificate = new CfCertificate();
        QueryWrapper<CfCertificate> certificateWrapper = new QueryWrapper<CfCertificate>();

        for (int i = 0; i < entryexitRecordsList.size(); i++) {
            OmsEntryexitRecord entryexit = entryexitRecordsList.get(i);
            for (int j = 0; j < priApplyList.size(); j++) {
                OmsPriApply priapply = priApplyList.get(i);
                //若证照号不为空
                if (!StringUtils.isBlank(entryexit.getIdNumber())) {
                    //如果出入境记录中的证照号与因私出国证照号匹配上
                    if (entryexit.getIdNumber().equals(priapply.getPassportNum())
                            || entryexit.getIdNumber().equals(priapply.getHongkongandmacaoPassportNum())
                            || entryexit.getIdNumber().equals(priapply.getTaiwanPassportNum())) {
                        certificateWrapper.eq("ZJHM", entryexit.getIdNumber());
                        certificate = cfCertificateMapper.selectOne(certificateWrapper);
                        //如果证照保管状态为“已注销”,出入境时间又在注销日期之后
                        //提醒干部监督处：“XXX单位XXX同志持注销证照于XXXX年XX月XX日前往XX国家（地区）。”
                        if (certificate.getSaveStatus().equals("3") && entryexit.getOgeDate().compareTo(certificate.getUpdateTime()) > 0) {
                            throw new CustomMessageException(entryexit.getB0100() + "单位" + entryexit.getName() + "同志持注销证照于" + entryexit.getOgeDate() + "前往" + entryexit.getDestination());
                        } else {
                            //非“注销”证照
                            //出入境状态 出1
                            if (entryexit.getOgeStatus().equals("1")) {
                                //比对出境时间：出境记录出国时间与因私出国实际出国时间一致
                                if (entryexit.getOgeDate().compareTo(priapply.getRealAbroadTime()) == 0) {
                                    //进行出访目的地比对 将出入境信息中的地点（国家、地区）与系统中经经办人审核后的实际因私出国（境）目的地和中转地进行比对，包含在其中的，记为一致
                                    if (entryexit.getDestination().contains(priapply.getRealGoCountry())) {
                                        //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                        priapply.setIsComparison("1");
                                        entryexit.setComparisionResult("正确");
                                    } else {
                                        //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                        priapply.setIsComparison("1");
                                        entryexit.setComparisionResult("出访目的地不匹配");
                                    }

                                } else {
                                    //出境时间不一致的（并且前后相差超过5天的），提醒干部监督处核查，
                                    if (OmsRegInitUtil.dayDiff(entryexit.getOgeDate(), priapply.getRealAbroadTime()) >= 5
                                            || OmsRegInitUtil.dayDiff(entryexit.getOgeDate(), priapply.getRealAbroadTime()) <= 5) {
                                        //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                        priapply.setIsComparison("1");
                                        //将出入境记录的比对结果置为“出入境时间不匹配，并且没有申请变更”，
                                        entryexit.setComparisionResult("出境时间不匹配，并且没有申请变更");
                                    }
                                }
                                entryexit.setPriapplyId(priapply.getId());
                                priApplyMapper.updateById(priapply);
                                baseMapper.updateById(entryexit);
                            } else {
                                //出入境状态入2
                                //比对入境时间：入境记录回国时间与因私出国实际回国时间一致
                                if (entryexit.getOgeDate().compareTo(priapply.getRealReturnTime()) == 0) {
                                    //进行出访目的地比对 将出入境信息中的地点（国家、地区）与系统中经经办人审核后的实际因私出国（境）目的地和中转地进行比对，包含在其中的，记为一致
                                    if (entryexit.getDestination().contains(priapply.getRealGoCountry())) {
                                        //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                        priapply.setIsComparison("1");
                                        entryexit.setComparisionResult("正确");
                                    } else {
                                        //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                        priapply.setIsComparison("1");
                                        entryexit.setComparisionResult("目的地不匹配");
                                    }
                                } else {
                                    //入境时间不一致的（并且前后相差超过5天的），提醒干部监督处核查，
                                    if (OmsRegInitUtil.dayDiff(entryexit.getOgeDate(), priapply.getRealReturnTime()) >= 5
                                            || OmsRegInitUtil.dayDiff(entryexit.getOgeDate(), priapply.getRealReturnTime()) <= 5) {
                                        //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                        priapply.setIsComparison("1");
                                        //将出入境记录的比对结果置为“出入境时间不匹配，并且没有申请变更”，
                                        entryexit.setComparisionResult("入境时间不匹配，并且没有申请变更");

                                        //进行出访目的地比对 将出入境信息中的地点（国家、地区）与系统中经经办人审核后的实际因私出国（境）目的地和中转地进行比对，包含在其中的，记为一致
                                        if (!entryexit.getDestination().contains(priapply.getRealGoCountry())) {
                                            //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                            entryexit.setComparisionResult(entryexit.getComparisionResult() + "；出访目的地不匹配");
                                        }
                                    }
                                }
                                entryexit.setPriapplyId(priapply.getId());
                                priApplyMapper.updateById(priapply);
                                baseMapper.updateById(entryexit);
                            }
                        }
                    } else {
                        entryexit.setComparisionResult("未经申请的出国（境）");
                        baseMapper.updateById(entryexit);
                    }

                } else {
                    entryexit.setComparisionResult("未经申请的出国（境）");
                    baseMapper.updateById(entryexit);
                    throw new CustomMessageException("当前人员证照号为空不能进行比对。");
                }
            }
        }
        Map<String, Object> map = this.selectComparisionList(a0100);
        return map;
    }


    /**
     * 查询比对结果列表
     * @return
     */
    private Map<String, Object> selectComparisionList(String a0100) {
        Map<String, Object> map = new HashMap<>();
        List<OmsPriApplyVO> comparisionList = priApplyMapper.selectComparisionList();
        List<OmsEntryexitRecord> noMatchList = baseMapper.selectNoMatchList(a0100);
        map.put("comparisionList",comparisionList);
        map.put("noMatchList",noMatchList);
        return map;
    }

    /**
     * 批量比对
     *
     * @param a0100s
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Object batchPriApplyList(List<String> a0100s) {
        //查询批次表中是否存在进行中的批次
        QueryWrapper<OmsEntryexitRecordCompbatch> compbatchWrapper = new QueryWrapper<OmsEntryexitRecordCompbatch>();
        compbatchWrapper.eq("STATUS", 1);
        int coun = entryexitRecordCompbatchMapper.selectCount(compbatchWrapper);
        if (coun > 0){
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
            if(a0100s!=null && a0100s.size() > 0){
                for (int x=0; x<a0100s.size();x++){
                    String a0100 = a0100s.get(x);
                    QueryWrapper<OmsPriApply> priApplyWrapper = new QueryWrapper<OmsPriApply>();
                    priApplyWrapper.eq("A0100", a0100);
                    //比对标识，默认为未比对
                    priApplyWrapper.eq("IS_COMPARISON", '0');
                    //因私申请出国境记录查询
                    List<OmsPriApply> priApplyList = priApplyMapper.selectList(priApplyWrapper);

                    QueryWrapper<OmsEntryexitRecord> exitWrapper = new QueryWrapper<OmsEntryexitRecord>();
                    exitWrapper.eq("A0100", a0100);
                    //比对结果为null的出入境记录查询
                    exitWrapper.eq("COMPARISON_RESULT", null);
                    List<OmsEntryexitRecord> entryexitRecordsList = baseMapper.selectList(exitWrapper);

                    //证件类型（护照1、港澳通行证2、台湾通行证4 ）
                    CfCertificate certificate = new CfCertificate();
                    QueryWrapper<CfCertificate> certificateWrapper = new QueryWrapper<CfCertificate>();

                    for (int i = 0; i < entryexitRecordsList.size(); i++) {
                        OmsEntryexitRecord entryexit = entryexitRecordsList.get(i);
                        for (int j = 0; j < priApplyList.size(); j++) {
                            OmsPriApply priapply = priApplyList.get(i);
                            //若证照号不为空
                            if (!StringUtils.isBlank(entryexit.getIdNumber())) {
                                //如果出入境记录中的证照号与因私出国证照号匹配上
                                if (entryexit.getIdNumber().equals(priapply.getPassportNum())
                                        || entryexit.getIdNumber().equals(priapply.getHongkongandmacaoPassportNum())
                                        || entryexit.getIdNumber().equals(priapply.getTaiwanPassportNum())) {
                                    certificateWrapper.eq("ZJHM", entryexit.getIdNumber());
                                    certificate = cfCertificateMapper.selectOne(certificateWrapper);
                                    //如果证照保管状态为“已注销”,出入境时间又在注销日期之后
                                    //提醒干部监督处：“XXX单位XXX同志持注销证照于XXXX年XX月XX日前往XX国家（地区）。”
                                    if (certificate.getSaveStatus().equals("3") && entryexit.getOgeDate().compareTo(certificate.getUpdateTime()) > 0) {
                                        throw new CustomMessageException(entryexit.getB0100() + "单位" + entryexit.getName() + "同志持注销证照于" + entryexit.getOgeDate() + "前往" + entryexit.getDestination());
                                    } else {
                                        //非“注销”证照
                                        //出入境状态 出1
                                        if (entryexit.getOgeStatus().equals("1")) {
                                            //比对出境时间：出境记录出国时间与因私出国实际出国时间一致
                                            if (entryexit.getOgeDate().compareTo(priapply.getRealAbroadTime()) == 0) {
                                                //进行出访目的地比对 将出入境信息中的地点（国家、地区）与系统中经经办人审核后的实际因私出国（境）目的地和中转地进行比对，包含在其中的，记为一致
                                                if (entryexit.getDestination().contains(priapply.getRealGoCountry())) {
                                                    //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )

                                                    priapply.setIsComparison("1");
                                                    entryexit.setComparisionResult("正确");
                                                } else {
                                                    //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                                    priapply.setIsComparison("1");
                                                    entryexit.setComparisionResult("出访目的地不匹配");
                                                }

                                            } else {
                                                //出境时间不一致的（并且前后相差超过5天的），提醒干部监督处核查，
                                                if (OmsRegInitUtil.dayDiff(entryexit.getOgeDate(), priapply.getRealAbroadTime()) >= 5
                                                        || OmsRegInitUtil.dayDiff(entryexit.getOgeDate(), priapply.getRealAbroadTime()) <= 5) {
                                                    //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                                    priapply.setIsComparison("1");
                                                    //将出入境记录的比对结果置为“出入境时间不匹配，并且没有申请变更”，
                                                    entryexit.setComparisionResult("出境时间不匹配，并且没有申请变更");
                                                }
                                            }
                                            entryexit.setPriapplyId(priapply.getId());
                                            priApplyMapper.updateById(priapply);
                                            baseMapper.updateById(entryexit);
                                        } else {
                                            //出入境状态入2
                                            //比对入境时间：入境记录回国时间与因私出国实际回国时间一致
                                            if (entryexit.getOgeDate().compareTo(priapply.getRealReturnTime()) == 0) {
                                                //进行出访目的地比对 将出入境信息中的地点（国家、地区）与系统中经经办人审核后的实际因私出国（境）目的地和中转地进行比对，包含在其中的，记为一致
                                                if (entryexit.getDestination().contains(priapply.getRealGoCountry())) {
                                                    //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                                    priapply.setIsComparison("1");
                                                    entryexit.setComparisionResult("正确");
                                                } else {
                                                    //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                                    priapply.setIsComparison("1");
                                                    entryexit.setComparisionResult("目的地不匹配");
                                                }
                                            } else {
                                                //入境时间不一致的（并且前后相差超过5天的），提醒干部监督处核查，
                                                if (OmsRegInitUtil.dayDiff(entryexit.getOgeDate(), priapply.getRealReturnTime()) >= 5
                                                        || OmsRegInitUtil.dayDiff(entryexit.getOgeDate(), priapply.getRealReturnTime()) <= 5) {
                                                    //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                                    priapply.setIsComparison("1");
                                                    //将出入境记录的比对结果置为“出入境时间不匹配，并且没有申请变更”，
                                                    entryexit.setComparisionResult("入境时间不匹配，并且没有申请变更");

                                                    //进行出访目的地比对 将出入境信息中的地点（国家、地区）与系统中经经办人审核后的实际因私出国（境）目的地和中转地进行比对，包含在其中的，记为一致
                                                    if (!entryexit.getDestination().contains(priapply.getRealGoCountry())) {
                                                        //将因私出国（境）审批信息表中的比对标识置为“已比对” 是否已比对(1是、0否  )
                                                        entryexit.setComparisionResult(entryexit.getComparisionResult() + "；出访目的地不匹配");
                                                    }
                                                }
                                            }
                                            entryexit.setPriapplyId(priapply.getId());
                                            priApplyMapper.updateById(priapply);
                                            baseMapper.updateById(entryexit);
                                        }
                                    }
                                } else {
                                    entryexit.setComparisionResult("未经申请的出国（境）");
                                    baseMapper.updateById(entryexit);
                                }

                            } else {
                                entryexit.setComparisionResult("未经申请的出国（境）");
                                baseMapper.updateById(entryexit);
                                throw new CustomMessageException("当前人员证照号为空不能进行比对。");
                            }
                        }
                    }
                    eRecordCompbatch.setCurrentFinishsum(Integer.parseInt(a0100s.get(x)));
                    eRecordCompbatch.setStatus("2");
                   entryexitRecordCompbatchMapper.updateById(eRecordCompbatch);
                }
            }

        }
        return null;
    }


}
