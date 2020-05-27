package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.mapper.*;
import com.hxoms.modules.omsregcadre.service.OmsRegRevokeApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OmsRegRevokeApplyServiceImpl extends ServiceImpl<OmsRegRevokeApplyMapper,OmsRegRevokeApply> implements OmsRegRevokeApplyService {

    @Autowired
    private A01EntityMapper a01Mapper;
    @Autowired
    private A30Mapper a30Mapper;
    @Autowired
    private OmsRegProcpersonInfoMapper regProcpersonInfoMapper;
    @Autowired
    private OmsRegRevokeApprovalMapper regRevokeApprovalMapper;

    @Override
    public IPage<OmsRegRevokeApply> queryRevokeApplyList(Page page) {
        return baseMapper.selectRevokeApplyList(page);
    }

    @Override
    public Object searchRevokeRegPerson() throws ParseException {
        int con=0;
        QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
        queryWrapper.eq("DATA_TYPE","1");

        //数据类型 为干部的省管干部登记备案查询
        List<OmsRegProcpersonInfo> reginfolist = regProcpersonInfoMapper.selectList(queryWrapper);
        //干综档案人员基本信息查询
        List<A01Entity> a01list = a01Mapper.selectList(null);
        List<String> a0100s =null;
        for (int i=0;i<reginfolist.size();i++){
            a0100s.add(reginfolist.get(i).getA0100());
        }
        OmsRegProcpersonInfo orpInfo = null;
        for (A01Entity a01:a01list) {
            if (a0100s.contains(a01.getA0100())) {
                //获取当前a0100对应的下标
                int index = a0100s.indexOf(a01.getA0100());
                OmsRegProcpersonInfo omsreginfo = reginfolist.get(index);
                QueryWrapper<A30> queryWrapper1 = new QueryWrapper<A30>();
                queryWrapper1.eq("A0100", omsreginfo.getA0100());
                //退出方式
                A30 a30 = a30Mapper.selectOne(queryWrapper1);
                //在职状态变更为辞职、免职、退休、开除、去世、挂职等
                if (!StringUtils.isEmpty(a30.getA3001())) {
                    //撤销登记备案申请表
                    OmsRegRevokeApply applyinfo = new OmsRegRevokeApply();

                    //将日期格式化
                    SimpleDateFormat sd = new SimpleDateFormat("yyyyMM");
                    //脱密开始日期
                    String secretStartDate = sd.format(omsreginfo.getDecryptStartdate());
                    //脱密结束日期
                    String secretEndDate = sd.format(omsreginfo.getDecryptEnddate());
                    //计算两个日期相差年数
                    int year = OmsRegInitUtil.yearDateDiff(secretStartDate, secretEndDate);

                    if (a30.getA3001().equals("辞职") || a30.getA3001().equals("开除")) {
                        //将满足要求的人员放到撤销登记备案申请表
                        if (year > OmsRegInitUtil.czyear) {
                            //复制登记备案相同字段的数据到撤销登记申请表
                            BeanUtils.copyProperties(omsreginfo, applyinfo);
                            applyinfo.setId(UUIDGenerator.getPrimaryKey());
                            applyinfo.setCreateDate(new Date());
                            applyinfo.setCreateUser("");
                            con = baseMapper.insert(applyinfo);
                        }
                    } else if (a30.getA3001().equals("退休")) {
                        //将满足要求的人员放到撤销登记备案申请表
                        if (year > OmsRegInitUtil.txyear) {
                            //复制登记备案相同字段的数据到撤销登记申请表
                            BeanUtils.copyProperties(omsreginfo, applyinfo);
                            applyinfo.setId(UUIDGenerator.getPrimaryKey());
                            applyinfo.setCreateDate(new Date());
                            applyinfo.setCreateUser("");
                            con = baseMapper.insert(applyinfo);
                        }
                    } else if (a30.getA3001().equals("到琼挂职")) {
                        //将满足要求的人员放到撤销登记备案申请表
                        if (year > 0) {
                            //复制登记备案相同字段的数据到撤销登记申请表
                            BeanUtils.copyProperties(omsreginfo, applyinfo);
                            applyinfo.setId(UUIDGenerator.getPrimaryKey());
                            applyinfo.setCreateDate(new Date());
                            applyinfo.setCreateUser("");
                            con = baseMapper.insert(applyinfo);
                        }
                    } else if (a30.getA3001().equals("去世")) {
                        //去世的，立即启动撤销备案及
                        omsreginfo.setInboundFlag("D");
                        //备案状态  0未备案，1已备案，2已确认
                        omsreginfo.setRfStatus("1");
                        //验收状态  1已验收，0待验收
                        omsreginfo.setCheckStatus("1");
                        omsreginfo.setModifyTime(new Date());
                        con = regProcpersonInfoMapper.updateById(omsreginfo);
                        //TODO:归还证照程序
                    }
                }
            }
        }
        return con;
    }

    /**
     * 添加撤销备案人员
     * @param omsreginfo
     * @return
     * @throws ParseException
     */
    @Override
    public Object insertRevokeRegPerson(OmsRegProcpersonInfo omsreginfo) throws ParseException {
        int con=0;
        QueryWrapper<A30> queryWrapper1 = new QueryWrapper<A30>();
        queryWrapper1.eq("A0100", omsreginfo.getA0100());
        //退出方式
        A30 a30 = a30Mapper.selectOne(queryWrapper1);
        //在职状态变更为辞职、免职、退休、开除、去世、挂职等
        if (!StringUtils.isEmpty(a30.getA3001())) {
            //撤销登记备案申请表
            OmsRegRevokeApply applyinfo = new OmsRegRevokeApply();

            //将日期格式化
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMM");
            //脱密开始日期
            String secretStartDate = sd.format(omsreginfo.getDecryptStartdate());
            //脱密结束日期
            String secretEndDate = sd.format(omsreginfo.getDecryptEnddate());
            //计算两个日期相差年数
            int year = OmsRegInitUtil.yearDateDiff(secretStartDate, secretEndDate);

            if (a30.getA3001().equals("辞职") || a30.getA3001().equals("开除")) {
                //将满足要求的人员放到撤销登记备案申请表
                if (year > OmsRegInitUtil.czyear) {
                    //复制登记备案相同字段的数据到撤销登记申请表
                    BeanUtils.copyProperties(omsreginfo, applyinfo);
                    applyinfo.setId(UUIDGenerator.getPrimaryKey());
                    applyinfo.setCreateDate(new Date());
                    applyinfo.setCreateUser("");
                    con = baseMapper.insert(applyinfo);
                }
            } else if (a30.getA3001().equals("退休")) {
                //将满足要求的人员放到撤销登记备案申请表
                if (year > OmsRegInitUtil.txyear) {
                    //复制登记备案相同字段的数据到撤销登记申请表
                    BeanUtils.copyProperties(omsreginfo, applyinfo);
                    applyinfo.setId(UUIDGenerator.getPrimaryKey());
                    applyinfo.setCreateDate(new Date());
                    applyinfo.setCreateUser("");
                    con = baseMapper.insert(applyinfo);
                }
            } else if (a30.getA3001().equals("到琼挂职")) {
                //将满足要求的人员放到撤销登记备案申请表
                if (year > 0) {
                    //复制登记备案相同字段的数据到撤销登记申请表
                    BeanUtils.copyProperties(omsreginfo, applyinfo);
                    applyinfo.setId(UUIDGenerator.getPrimaryKey());
                    applyinfo.setCreateDate(new Date());
                    applyinfo.setCreateUser("");
                    con = baseMapper.insert(applyinfo);
                }
            } else if (a30.getA3001().equals("去世")) {
                //去世的，立即启动撤销备案及
                omsreginfo.setInboundFlag("D");
                //备案状态  0未备案，1已备案，2已确认
                omsreginfo.setRfStatus("1");
                //验收状态  1已验收，0待验收
                omsreginfo.setCheckStatus("1");
                omsreginfo.setModifyTime(new Date());
                con = regProcpersonInfoMapper.updateById(omsreginfo);
                //TODO:归还证照程序
            }else{
                throw new CustomMessageException("当前选择人员不满足撤销登记备案条件。");
            }
        }else{
            throw new CustomMessageException("当前选择人员不满足撤销登记备案条件。");
        }
        return con;
    }

    @Override
    public Object approvalRevokeRegPerson(OmsRegRevokeApproval regRevokeApproval) {
        regRevokeApproval.setId(UUIDGenerator.getPrimaryKey());
        regRevokeApproval.setApprovalTime(new Date());
        return regRevokeApprovalMapper.insert(regRevokeApproval);
    }


}


