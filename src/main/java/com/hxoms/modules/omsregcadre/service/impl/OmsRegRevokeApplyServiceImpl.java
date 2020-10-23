package com.hxoms.modules.omsregcadre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.mapper.OmsCreateFileMapper;
import com.hxoms.modules.file.mapper.OmsFileMapper;
import com.hxoms.modules.file.service.OmsCreateFileService;
import com.hxoms.modules.file.service.impl.OmsFileServiceImpl;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegRevokeApplyIPagParam;
import com.hxoms.modules.omsregcadre.mapper.*;
import com.hxoms.modules.omsregcadre.service.OmsRegRevokeApplyService;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.mapper.B01Mapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsRegRevokeApplyServiceImpl extends ServiceImpl<OmsRegRevokeApplyMapper, OmsRegRevokeapply> implements OmsRegRevokeApplyService {

    @Autowired
    private OmsRegProcpersoninfoMapper regProcpersonInfoMapper;
    @Autowired
    private OmsRegRevokeapprovalMapper regRevokeApprovalMapper;
    @Autowired
    private SelectMapper selectMapper;
    @Autowired
    private OmsFileMapper omsFileMapper;
    @Autowired
    private B01Mapper b01Mapper;
    @Autowired
    private OmsCreateFileService omsCreateFileService;
    @Autowired
    private OmsCreateFileMapper omsCreateFileMapper;

    @Autowired
    private OmsFileServiceImpl omsFileServiceImpl;

    private final static String[] types ={"辞职","开除"};

    @Override
    public PageInfo<OmsRegRevokeapply> queryRevokeApplyList(OmsRegRevokeApplyIPagParam revokeApplyIPagParam) {
        //分页
        PageUtil.pageHelp(revokeApplyIPagParam.getPageNum(), revokeApplyIPagParam.getPageSize());
        List<OmsRegRevokeapply> list = baseMapper.selectRevokeApplyList(revokeApplyIPagParam);
        PageInfo<OmsRegRevokeapply> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result searchRevokeRegPerson() {
        StringBuffer msg = new StringBuffer();
        //自动搜索辞职、开除、解聘、退休、去世、挂职到期等状态，且不在撤销登记备案申请表的人员（排除已登记备案状态）
        List<OmsRegProcpersoninfo> reginfolist = regProcpersonInfoMapper.selectAllowRevokePerson(UserInfoUtil.getUserInfo().getOrgId());
        if (reginfolist != null && reginfolist.size() > 0) {
            for (int i = 0; i < reginfolist.size(); i++) {
                OmsRegProcpersoninfo info = reginfolist.get(i);
                if (info.getExitDate() == null) continue;
                //辞职日期
                Calendar calExit = Calendar.getInstance();
                calExit.setTime(info.getExitDate());
                calExit.add(Calendar.YEAR, OmsRegInitUtil.czyear);
                //退休日期
                Calendar txExit = Calendar.getInstance();
                txExit.setTime(info.getExitDate());
                txExit.add(Calendar.YEAR, OmsRegInitUtil.txyear);

                //撤销登记备案申请表
                OmsRegRevokeapply applyinfo = new OmsRegRevokeapply();
                //1.在职、2.辞职、3.开除、4.解聘，5.免职撤职，6.退休，7.去世，8.调出，9.挂职到期，10.其他，99.未匹配
                //判断辞职、开除、解聘人员是否满3年（根据系统参数设置判断），且已过脱密结束日期 ,将满足要求的人员放到撤销登记备案申请表
                int incumbencyStatus = Integer.parseInt(info.getIncumbencyStatus());
                if (incumbencyStatus == Constants.emIncumbencyStatus.Resignation.getIndex()
                        || incumbencyStatus == Constants.emIncumbencyStatus.Expel.getIndex()
                        || incumbencyStatus == Constants.emIncumbencyStatus.Dismissal.getIndex()) {
                    if (calExit.before(new Date()) && info.getDecryptEnddate().before(new Date())) {
                        copyApplyInfo(info, applyinfo);
                    }
                    //判断退休人员是否满系统参数设置的退休年限,将满足要求的人员放到撤销登记备案申请表
                } else if (incumbencyStatus == Constants.emIncumbencyStatus.Retirement.getIndex()) {
                    if (txExit.before(new Date())) {
                        copyApplyInfo(info, applyinfo);
                    }
                    //判断挂职到期人员是否过脱密日期，将满足要求的人员放到撤销登记备案申请表
                } else if (incumbencyStatus == Constants.emIncumbencyStatus.Secondment.getIndex()) {
                    if (info.getDecryptEnddate().before(new Date())) {
                        copyApplyInfo(info, applyinfo);
                    }
                } else if (incumbencyStatus == Constants.emIncumbencyStatus.Dispatch.getIndex()) {
                    copyApplyInfo(info, applyinfo);
                }
            }
        } else {
            msg.append("暂无可提取的撤销的备案人员!");
            return Result.error(msg.toString());
        }
        return Result.success();
    }


    /**
     * 添加撤销备案人员
     *
     * @param revokeApply
     * @return
     * @throws ParseException
     */
    @Override
    public int insertRevokeRegPerson(OmsRegRevokeapply revokeApply) {
        int con = 0;
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        revokeApply.setId(UUIDGenerator.getPrimaryKey());
        revokeApply.setCreateDate(new Date());
        revokeApply.setCreateUser(userInfo.getId());
        if (revokeApply.getRfB0000() == null)
            revokeApply.setRfB0000(UserInfoUtil.getUserInfo().getOrgId());
        con = baseMapper.insert(revokeApply);
        if (con > 0) {
            InsertApprovalRecord(revokeApply.getId(),
                    "填写撤销备案申请", "", "");
        }
        return con;
    }

    protected void InsertApprovalRecord(String appID, String stepName, String result, String opinion) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        OmsRegRevokeapproval regRevokeApproval = new OmsRegRevokeapproval();
        regRevokeApproval.setApplyId(appID);
        regRevokeApproval.setId(UUIDGenerator.getPrimaryKey());
        regRevokeApproval.setApprovalTime(new Date());
        regRevokeApproval.setApprovalUser(userInfo.getId());
        regRevokeApproval.setSubmitTime(new Date());
        regRevokeApproval.setSubmitUser(userInfo.getId());
        regRevokeApproval.setStepName(stepName);
        regRevokeApproval.setApprovalOpinion(opinion);
        regRevokeApproval.setApprovalConclusion(result);
        regRevokeApprovalMapper.insert(regRevokeApproval);
    }

    private int copyApplyInfo(OmsRegProcpersoninfo info, OmsRegRevokeapply applyinfo) {
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        int con = 0;
        //复制登记备案相同字段的数据到撤销登记申请表
        BeanUtils.copyProperties(info, applyinfo);
        applyinfo.setStatus(String.valueOf(Constants.emRevokeRegister.申请.getIndex()));
        applyinfo.setId(UUIDGenerator.getPrimaryKey());
        applyinfo.setRfId(info.getId());
        applyinfo.setCreateDate(new Date());
        applyinfo.setCreateUser(userInfo.getId());
        applyinfo.setExitDate(new SimpleDateFormat("yyyy.MM.dd").format(info.getExitDate()));
        applyinfo.setExitType(info.getIncumbencyStatus());
        InsertApprovalRecord(applyinfo.getId(),
                "填写撤销备案申请", "", "");
        return con = baseMapper.insert(applyinfo);
    }

    protected List<OmsRegRevokeapply> QueryByIds(String applyIds) {
        String[] num = applyIds.split(",");

        QueryWrapper<OmsRegRevokeapply> queryWrapper = new QueryWrapper<OmsRegRevokeapply>();
        queryWrapper.in("ID", num);
        List<OmsRegRevokeapply> list = baseMapper.selectList(queryWrapper);

        return list;
    }

    /**
     * 批量审批撤销申请
     *
     * @param regRevokeApproval
     * @param applyIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result approvalRevokeRegPerson(OmsRegRevokeapproval regRevokeApproval, String applyIds) {
        List<OmsRegRevokeapply> list = QueryByIds(applyIds);
        int count = 0;
        for (OmsRegRevokeapply regRevokeapply : list) {

            //不处理处领导、部领导审批之外的数据
            if (!"3".equals(regRevokeapply.getStatus()) &&
                    !"4".equals(regRevokeapply.getStatus())) continue;

            count++;
            String stepName = getStepName(regRevokeapply.getStatus());

            QueryWrapper<OmsRegRevokeapply> queryWrapper = new QueryWrapper<OmsRegRevokeapply>();
            queryWrapper.eq("ID", regRevokeapply.getId());
            if ("3".equals(regRevokeapply.getStatus()))
                regRevokeapply.setStatus("4");
            else if ("4".equals(regRevokeapply.getStatus()))
                regRevokeapply.setStatus("7");
            int con = baseMapper.update(regRevokeapply, queryWrapper);
            if (con > 0) {
                InsertApprovalRecord(regRevokeapply.getId(), stepName,
                        regRevokeApproval.getApprovalConclusion(),
                        regRevokeApproval.getApprovalOpinion());
            }
        }
        return Result.success().setMsg("共收到(" + list.size() + ")条审批，成功保存(" + count + ")条！");
    }


    @Override
    public Object searchRevokeRegPersonList(OmsRegProcpersoninfo regProcpersonInfo) {
        //查询公安信息可撤销登记备案人员
        List<OmsRegProcpersoninfoVO> reginfolist = regProcpersonInfoMapper.searchRevokeRegPersonList(regProcpersonInfo);
        List<OmsRegRevokeapply> regRevokeapplies = new ArrayList<>();
        for (OmsRegProcpersoninfoVO regProcpersoninfoVO : reginfolist
        ) {
            OmsRegRevokeapplyV0 omsRegRevokeapply = new OmsRegRevokeapplyV0();
            BeanUtils.copyProperties(regProcpersoninfoVO, omsRegRevokeapply);
            omsRegRevokeapply.setStatus(String.valueOf(Constants.emRevokeRegister.申请.getIndex()));
            omsRegRevokeapply.setStatusName(Constants.emRevokeRegister.申请.getName());
            omsRegRevokeapply.setRfId(regProcpersoninfoVO.getId());
            omsRegRevokeapply.setExitType(regProcpersoninfoVO.getIncumbencyStatus());
            omsRegRevokeapply.setExitTypeName(regProcpersoninfoVO.getIncumbencyStatusName());
            omsRegRevokeapply.setCurrentOrg(regProcpersoninfoVO.getWorkUnit());
            omsRegRevokeapply.setCurrentPost(regProcpersoninfoVO.getPost());
            regRevokeapplies.add(omsRegRevokeapply);
        }
        return regRevokeapplies;
    }

    private String getStepName(String status) {
        int pStatus = Integer.parseInt(status);
        String stepName = "";
        switch (pStatus) {
            case 1:
                stepName = "提交干部监督处";
                break;
            case 2:
                stepName = "干部监督处受理";
                break;
            case 3:
                stepName = "处领导审批";
                break;
            case 4:
                stepName = "部领导审批";
                break;
            case 5:
                stepName = "撤销";
                break;
            case 6:
                stepName = "拒批";
                break;
            case 7:
                stepName = "待备案";
                break;
            case 8:
                stepName = "已备案";
                break;
        }
        return stepName;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateApplyStatus(OmsRegRevokeapply revokeApply) {
        QueryWrapper<OmsRegRevokeapply> queryWrapper = new QueryWrapper<OmsRegRevokeapply>();
        queryWrapper.eq("ID", revokeApply.getId());
        revokeApply.setStatus(revokeApply.getStatus());
        String stepName = getStepName(revokeApply.getStatus());

        InsertApprovalRecord(revokeApply.getId(), stepName, "", "");
        return baseMapper.update(revokeApply, queryWrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateApplyStatusByCLD(String status, String applyIds) {
        List<OmsRegRevokeapply> list = QueryByIds(applyIds);

        List<OmsRegRevokeapply> updates = new ArrayList<>();
        int pStatus = Integer.parseInt(status);
        for (OmsRegRevokeapply regRevokeapply : list
        ) {

            int iStatus = Integer.parseInt(regRevokeapply.getStatus());
            if (iStatus != pStatus) continue;

            if (iStatus == Constants.emRevokeRegister.申请.getIndex() ||
                    iStatus == Constants.emRevokeRegister.受理.getIndex() ||
                    iStatus == Constants.emRevokeRegister.处领导审核.getIndex() ||
                    iStatus == Constants.emRevokeRegister.部领导审批.getIndex()) {

                iStatus++;

                regRevokeapply.setStatus(String.valueOf(iStatus));
                updates.add(regRevokeapply);

                String stepName = getStepName(status);

                InsertApprovalRecord(regRevokeapply.getId(), stepName, "", "");
            }
        }

        if (updates.size() > 0) {
            this.updateBatchById(updates);
            return Result.success("成功处理了（" + updates.size() + "）条申请！");
        }
        return Result.error("没有要处理的申请！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteRevokeRegPerson(String id) {
        String sql = "delete from oms_reg_revokeapproval where APPLY_ID='" + id + "'";
        SqlVo instance = SqlVo.getInstance(sql);
        selectMapper.delete(instance);
        return baseMapper.deleteById(id);
    }


    /**
     * 生成撤销函
     * @param lists
     * @return
     */
    @Override
    public List<CancellationLetter> createCancellationLetter(List<CancellationLetter> lists) {

        if(lists!=null && lists.size()>0){

            for(CancellationLetter cancellationLetter : lists){

                String tableCode =cancellationLetter.getTableCode();
                String fileShortname = cancellationLetter.getFileType();
                String applyId  =cancellationLetter.getId();
                // 在职状态
                String exitType  = cancellationLetter.getExitType();
                //登录用户信息
                UserInfo userInfo = UserInfoUtil.getUserInfo();
                //查询机构信息
                B01 b01 = b01Mapper.selectOrgByB0111(userInfo.getOrgId());
                if (b01 == null){
                    throw new CustomMessageException("数据异常");
                }
                QueryWrapper<OmsFile> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("TABLE_CODE", tableCode)
                        .eq("B0100", b01.getB0100())
                        .eq("FILE_SHORTNAME",fileShortname)
                        .orderByAsc("SORT_ID");
                List<OmsFile> omsFiles = omsFileMapper.selectList(queryWrapper);
                if (omsFiles == null || omsFiles.size() < 1) {
                    //初始化机构文件
                    queryWrapper.clear();
                    queryWrapper.eq("TABLE_CODE", tableCode)
                            .eq("FILE_SHORTNAME",fileShortname)
                            .and(wrapper->wrapper.eq("B0100", "")
                                    .or()
                                    .isNull("B0100"))
                            .orderByAsc("SORT_ID");
                    List<OmsFile> omsFileSystem = omsFileMapper.selectList(queryWrapper);
                    if (omsFileSystem != null && omsFileSystem.size() > 0) {
                        //插入
                        for (OmsFile omsfile : omsFileSystem) {
                            omsfile.setFileId(omsfile.getId());
                            omsfile.setId(UUIDGenerator.getPrimaryKey());
                            omsfile.setB0100(b01.getB0100());
                            omsfile.setCreateUser(userInfo.getId());
                            omsfile.setCreateTime(new Date());
                            omsFileMapper.insert(omsfile);
                        }
                    }
                    queryWrapper.clear();
                    //重新查询
                    queryWrapper.eq("TABLE_CODE", tableCode)
                            .eq("B0100", b01.getB0100())
                            .eq("FILE_SHORTNAME",fileShortname)
                            .orderByAsc("SORT_ID");
                    omsFiles = omsFileMapper.selectList(queryWrapper);
                }
                cancellationLetter.setOmsFiles(omsFiles);
                //生成文件
                if (!StringUtils.isBlank(applyId)){
                    QueryWrapper<OmsCreateFile> createFile = new QueryWrapper<>();
                    createFile.eq("TABLE_CODE", tableCode)
                            .eq("APPLY_ID", applyId);
                    int count = omsCreateFileMapper.selectCount(createFile);
                    //没有生成时生成文件
                    if (count < 1){
                        for (OmsFile omsFile : omsFiles){
                            OmsCreateFile omsCreateFile = omsFileServiceImpl.createFile(omsFile,applyId);// new OmsCreateFile();
                            //替换关键词
                            omsFileServiceImpl.replaceFile(omsFile, applyId, tableCode,null);
                            if(Arrays.asList(types).indexOf(exitType)!=-1){
                                omsCreateFile.setFrontContent(omsFile.getFrontContent().replaceAll("在本单位已登记备案","已退出满三年"));
                            }else{
                                omsCreateFile.setFrontContent(omsFile.getFrontContent());

                            }
                            omsCreateFile.setFrontContent(omsFile.getFrontContent());
                            omsCreateFile.setBankContent(omsFile.getBankContent());
                            omsCreateFileMapper.insert(omsCreateFile);
                            cancellationLetter.setOmsCreateFile(omsCreateFile);
                        }
                    }else{
                        cancellationLetter.setOmsCreateFile(omsCreateFileMapper.selectList(createFile).get(0));
                    }
                }
            }
        }else{
            throw new CustomMessageException("参数为空，请仔细检查");
        }
        return lists;

    }

}



