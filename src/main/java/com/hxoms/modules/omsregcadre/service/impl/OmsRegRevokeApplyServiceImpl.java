package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegRevokeApplyIPagParam;
import com.hxoms.modules.omsregcadre.mapper.*;
import com.hxoms.modules.omsregcadre.service.OmsRegRevokeApplyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OmsRegRevokeApplyServiceImpl extends ServiceImpl<OmsRegRevokeApplyMapper, OmsRegRevokeapply> implements OmsRegRevokeApplyService {

    @Autowired
    private A01EntityMapper a01Mapper;
    @Autowired
    private A30Mapper a30Mapper;
    @Autowired
    private OmsRegProcpersoninfoMapper regProcpersonInfoMapper;
    @Autowired
    private OmsRegRevokeapprovalMapper regRevokeApprovalMapper;

    @Override
    public PageInfo<OmsRegRevokeapply> queryRevokeApplyList(OmsRegRevokeApplyIPagParam revokeApplyIPagParam) {
        //分页
        PageUtil.pageHelp(revokeApplyIPagParam.getPageNum(), revokeApplyIPagParam.getPageSize());
        List<OmsRegRevokeapply> list = baseMapper.selectRevokeApplyList(revokeApplyIPagParam);
        PageInfo<OmsRegRevokeapply> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public Object searchRevokeRegPerson() throws ParseException {
        int con=0;
        QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
        //数据类型 为干部的
        queryWrapper.eq("DATA_TYPE","1");
        //入库标识为非撤销的
        queryWrapper.notIn("INBOUND_FLAG","D");
        //辞职、开除、解聘、退休、去世、挂职到期等状态
        //1.在职、2.辞职、3.开除 4.解聘 5.免职撤职 6.退休 7.去世 8.调出（省管变中管、省管变非省管、调到外省）9.挂职到期 10.其他
        queryWrapper.notIn("INCUMBENCY_STATUS","1,7");
        //且不在撤销登记备案申请表的人员（排除已登记备案状态）
        List<String> rfIds = baseMapper.selectrfIdList();
        String rfId = String.join(",", rfIds);
        if (!StringUtils.isBlank(rfId)){
            queryWrapper.notIn("ID",rfId);
        }
        //省管干部登记备案查询
        List<OmsRegProcpersoninfo> reginfolist = regProcpersonInfoMapper.selectList(queryWrapper);
        List<String> a0100s = new ArrayList<>();
        for (int i=0;i<reginfolist.size();i++){
            OmsRegProcpersoninfo info = reginfolist.get(i);
            //将日期格式化
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMM");
            //脱密开始日期
            String secretStartDate = sd.format(info.getDecryptStartdate());
            //脱密结束日期
            String secretEndDate = sd.format(info.getDecryptEnddate());
            String currentDate = sd.format(new Date());
            //计算脱密期年数
            int tmyear = OmsRegInitUtil.yearDateDiff(secretEndDate, currentDate);
            //退出日期
            String exitDate = sd.format(info.getExitDate());
            //退出日期年数
            int tcyear = OmsRegInitUtil.yearDateDiff(exitDate, currentDate);
            //撤销登记备案申请表
            OmsRegRevokeapply applyinfo = new OmsRegRevokeapply();
            //判断辞职、开除、解聘人员是否满3年（根据系统参数设置判断）
            if (info.getIncumbencyStatus().equals("2") || info.getIncumbencyStatus().equals("3") || info.getIncumbencyStatus().equals("4")){
                //将满足要求的人员放到撤销登记备案申请表
                if (tcyear > OmsRegInitUtil.czyear && tmyear < 1) {
                    con = copyApplyInfo(info,applyinfo);
                }
            } else if (info.getIncumbencyStatus().equals("6")) {
                //将满足要求的人员放到撤销登记备案申请表
                if (tcyear > OmsRegInitUtil.txyear) {
                    con = copyApplyInfo(info,applyinfo);
                }
            } else if (info.getIncumbencyStatus().equals("9")) {
                //将满足要求的人员放到撤销登记备案申请表
                if (tmyear < 1) {
                    con = copyApplyInfo(info, applyinfo);
                }
            }else if (info.getIncumbencyStatus().equals("5") || info.getIncumbencyStatus().equals("8")){
                con = copyApplyInfo(info, applyinfo);
            }
        }
        return con;
    }


    /**
     * 添加撤销备案人员
     * @param revokeApply
     * @return
     * @throws ParseException
     */
    @Override
    public int insertRevokeRegPerson(OmsRegRevokeapply revokeApply){
        int con=0;
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        revokeApply.setId(UUIDGenerator.getPrimaryKey());
        revokeApply.setCreateDate(new Date());
        revokeApply.setCreateUser(userInfo.getId());
        return con = baseMapper.insert(revokeApply);
    }


    private int copyApplyInfo(OmsRegProcpersoninfo info, OmsRegRevokeapply applyinfo) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        int con=0;
        //复制登记备案相同字段的数据到撤销登记申请表
        BeanUtils.copyProperties(info, applyinfo);
        if (info.getRfStatus().equals("0") || info.getRfStatus().equals("1")){
            applyinfo.setStatus(info.getRfStatus());
        }
        applyinfo.setId(UUIDGenerator.getPrimaryKey());
        applyinfo.setRfId(info.getId());
        applyinfo.setCreateDate(new Date());
        applyinfo.setCreateUser(userInfo.getId());
        return con = baseMapper.insert(applyinfo);
    }

    /**
     * 批量审批撤销申请
     * @param regRevokeApproval
     * @param applyIds
     * @return
     */
    @Override
    public Object approvalRevokeRegPerson(OmsRegRevokeapproval regRevokeApproval, String applyIds) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        String[] num = applyIds.split(",");
        int con=0;
        for (int i = 0; i < num.length; i++) {
            OmsRegRevokeapply revokeApply = new OmsRegRevokeapply();
            QueryWrapper<OmsRegRevokeapply> queryWrapper = new QueryWrapper<OmsRegRevokeapply>();
            queryWrapper.eq("ID",num[i]);
            revokeApply.setStatus("3");
            con = baseMapper.update(revokeApply,queryWrapper);
            if (con > 0){
                regRevokeApproval.setApplyId(num[i]);
                regRevokeApproval.setId(UUIDGenerator.getPrimaryKey());
                regRevokeApproval.setApprovalTime(new Date());
                regRevokeApproval.setApprovalUser(userInfo.getId());
                regRevokeApproval.setSubmitTime(new Date());
                regRevokeApproval.setSubmitUser(userInfo.getId());
                con =  regRevokeApprovalMapper.insert(regRevokeApproval);
            }
        }
        return con;
    }



    @Override
    public Object searchRevokeRegPersonList(OmsRegProcpersoninfo regProcpersonInfo) {
        //查询公安信息可撤销登记备案人员
        List<OmsRegProcpersoninfo> reginfolist = regProcpersonInfoMapper.searchRevokeRegPersonList(regProcpersonInfo);
        return reginfolist;
    }

    @Override
    public Object updateApplyStatus(OmsRegRevokeapply revokeApply) {
        QueryWrapper<OmsRegRevokeapply> queryWrapper = new QueryWrapper<OmsRegRevokeapply>();
        queryWrapper.eq("ID",revokeApply.getId());
        revokeApply.setStatus(revokeApply.getStatus());
        return baseMapper.update(revokeApply,queryWrapper);
    }


    @Override
    public Object updateApplyStatusByCLD(OmsRegRevokeapply revokeApply,String applyIds) {
        String[] num = applyIds.split(",");
        QueryWrapper<OmsRegRevokeapply> queryWrapper = new QueryWrapper<OmsRegRevokeapply>();
        queryWrapper.in("ID",num);
        return baseMapper.update(revokeApply,queryWrapper);
    }

}



