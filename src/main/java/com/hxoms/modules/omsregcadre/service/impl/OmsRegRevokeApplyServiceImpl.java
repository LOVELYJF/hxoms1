package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegRevokeApplyIPagParam;
import com.hxoms.modules.omsregcadre.mapper.*;
import com.hxoms.modules.omsregcadre.service.OmsRegRevokeApplyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OmsRegRevokeApplyServiceImpl extends ServiceImpl<OmsRegRevokeApplyMapper, OmsRegRevokeapply> implements OmsRegRevokeApplyService {

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
    public Result searchRevokeRegPerson(){
        StringBuffer msg = new StringBuffer();
        //自动搜索辞职、开除、解聘、退休、去世、挂职到期等状态，且不在撤销登记备案申请表的人员（排除已登记备案状态）
        List<OmsRegProcpersoninfo> reginfolist = regProcpersonInfoMapper.selectAllowRevokePerson();
        if (reginfolist!=null && reginfolist.size()>0){
            for (int i=0;i<reginfolist.size();i++){
                OmsRegProcpersoninfo info = reginfolist.get(i);
                //辞职日期
                Calendar calExit = Calendar.getInstance();
                calExit.setTime(info.getExitDate());
                calExit.add(Calendar.YEAR,OmsRegInitUtil.czyear);
                //退休日期
                Calendar txExit = Calendar.getInstance();
                txExit.setTime(info.getExitDate());
                txExit.add(Calendar.YEAR,OmsRegInitUtil.txyear);

                //撤销登记备案申请表
                OmsRegRevokeapply applyinfo = new OmsRegRevokeapply();
                //1.在职、2.辞职、3.开除、4.解聘，5.免职撤职，6.退休，7.去世，8.调出，9.挂职到期，10.未匹配，99.其他
                //判断辞职、开除、解聘人员是否满3年（根据系统参数设置判断），且已过脱密结束日期 ,将满足要求的人员放到撤销登记备案申请表
                if (info.getIncumbencyStatus().equals(Constants.emIncumbencyStatus.Resignation)
                        || info.getIncumbencyStatus().equals(Constants.emIncumbencyStatus.Expel)
                        || info.getIncumbencyStatus().equals(Constants.emIncumbencyStatus.Dismissal)){
                    if(calExit.before(new Date()) && info.getDecryptEnddate().before(new Date())){
                        copyApplyInfo(info,applyinfo);
                    }
                    //判断退休人员是否满系统参数设置的退休年限,将满足要求的人员放到撤销登记备案申请表
                }else if (info.getIncumbencyStatus().equals(Constants.emIncumbencyStatus.Retirement)) {
                    if (txExit.before(new Date())) {
                        copyApplyInfo(info,applyinfo);
                    }
                    //判断挂职到期人员是否过脱密日期，将满足要求的人员放到撤销登记备案申请表
                }else if (info.getIncumbencyStatus().equals(Constants.emIncumbencyStatus.Secondment)){
                    if (info.getDecryptEnddate().before(new Date())) {
                        copyApplyInfo(info, applyinfo);
                    }
                }else{
                    msg.append("暂无可提取的撤销的备案人员!");
                    return Result.error(msg.toString());
                }
            }
        }else{
            msg.append("暂无可提取的撤销的备案人员!");
            return Result.error(msg.toString());
        }
        return Result.success();
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
        applyinfo.setStatus("1");
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
        List<OmsRegProcpersoninfoVO> reginfolist = regProcpersonInfoMapper.searchRevokeRegPersonList(regProcpersonInfo);
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
    public Object updateApplyStatusByCLD(String status,String applyIds) {
        String[] num = applyIds.split(",");
        OmsRegRevokeapply apply = new OmsRegRevokeapply();
        apply.setStatus(status);
        QueryWrapper<OmsRegRevokeapply> queryWrapper = new QueryWrapper<OmsRegRevokeapply>();
        queryWrapper.in("ID",num);
        return baseMapper.update(apply,queryWrapper);
    }

    @Override
    public Object deleteRevokeRegPerson(String id) {
        return baseMapper.deleteById(id);
    }

}



