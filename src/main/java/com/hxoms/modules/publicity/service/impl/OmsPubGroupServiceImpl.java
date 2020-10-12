package com.hxoms.modules.publicity.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsCommonUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.leaderSupervision.entity.OmsAttachment;
import com.hxoms.modules.leaderSupervision.mapper.OmsAttachmentMapper;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.publicity.destroyReg.entity.OmsPubDestroydetail;
import com.hxoms.modules.publicity.destroyReg.mapper.OmsPubDestroydetailMapper;
import com.hxoms.modules.publicity.docCallback.entity.OmsPubDoccallbackdetail;
import com.hxoms.modules.publicity.docCallback.mapper.OmsPubDoccallbackdetailMapper;
import com.hxoms.modules.publicity.entity.*;
import com.hxoms.modules.publicity.mapper.OmsPubApplyChangeMapper;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.publicity.mapper.OmsPubGroupMapper;
import com.hxoms.modules.publicity.service.OmsPubGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsPubGroupServiceImpl extends ServiceImpl<OmsPubGroupMapper, OmsPubGroupPreApproval> implements OmsPubGroupService {

    @Autowired
    private OmsPubGroupMapper pubGroupMapper;
    @Autowired
    private OmsPubApplyMapper pubApplyMapper;
    @Autowired
    private OmsRegProcpersoninfoMapper regProcpersoninfoMapper;
    @Autowired
    private OmsPubApplyChangeMapper pubApplyChangeMapper;
    @Autowired
    private OmsPubDestroydetailMapper pubDestroydetailMapper;
    @Autowired
    private OmsPubDoccallbackdetailMapper pubDoccallbackdetailMapper;
    @Autowired
    private OmsConditionService omsConditionService;
    @Autowired
    private OmsAttachmentMapper attachmentMapper;

    @Value("${omsAttachment.baseDir}")
    private String attachmentPath;

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    //人员下达
    private static final int IS_ASSIGN = 1;
    //人员未下达
    private static final int IS_NOT_ASSIGN = 0;

    @Override
    public PageInfo<OmsPubGroupPreApproval> getPubGroupList(Integer pageNum, Integer pageSize,Map<String,String> param) {
        List<OmsPubGroupPreApproval> resultList = pubGroupMapper.getPubGroupList(param);
        PageUtil.pageHelp(pageNum, pageSize);
        PageInfo<OmsPubGroupPreApproval> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }

    @Override
    public Result insertPubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList){
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        List<OmsPubApplyVO> applyVOList = pubGroupAndApplyList.getOmsPubApplyVOList();
        List<OmsPubApply> applyList = new ArrayList<>();
        int num = applyVOList.size();
        if(num > 0){
            //登录用户信息
            UserInfo userInfo = UserInfoUtil.getUserInfo();
            //团组信息
            String id = UUIDGenerator.getPrimaryKey();
            pubGroup.setId(id);
            pubGroup.setSqzt(Constants.PUB_GROUP_STATUS_CODE[1]);
            pubGroup.setCreateUser(userInfo.getId());
            pubGroup.setCreateTime(new Date());
            //出国人员信息
            for(int i = 0; i < num; i++ ){
                OmsPubApply pubApply = applyVOList.get(i);
                for (int j = 0; j < num; j++) {
                    if(i != j && pubApply.getProcpersonId().equals(applyVOList.get(j).getProcpersonId())){
                        return Result.error("人员选择重复，请查证！");
                    }
                }
                pubApply = getInsertOmsPubApply(pubApply.getProcpersonId(),pubGroup,pubApply.getB0100());
                pubApply.setYspId(id);
                pubApply.setZtdw(pubGroup.getZtdw());
                pubApply.setCgsj(pubGroup.getCgsj());
                pubApply.setHgsj(pubGroup.getHgsj());
                pubApply.setSdgj(pubGroup.getSdgj());
                pubApply.setTlsj(String.valueOf(pubGroup.getZwtlsj()));
                pubApply.setCfrw(pubGroup.getCfrw());
                pubApply.setCfsy(pubGroup.getCfsy());
                pubApply.setYspdwId(pubGroup.getB0100());
                pubApply.setSortId(i);
                applyList.add(pubApply);
            }
            if(pubGroupMapper.insertPubGroup(pubGroup) > 0){
                pubApplyMapper.insertPubApplyList(applyList);
                sendTask(pubGroupAndApplyList,String.valueOf(pubGroup.getBazt()));
            }
            return Result.success(pubGroup);
        }else{
            return Result.error("未选择备案人员");
        }
    }

    @Override
    public Result updateTimeTask(OmsPubGroupAndApplyList pubGroupAndApplyList,String bgyy) {
        if (pubGroupAndApplyList == null || StringUtils.isBlank(bgyy)){
            return Result.error("参数为空!");
        }
        //修改团组及人员信息
        updatePubGroup(pubGroupAndApplyList);

        //创建所需对象
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        OmsPubGroupPreApproval pubGroupOld = pubGroupMapper.getPubGroupDetailById(pubGroup.getId());

        //插入变更记录
        if(insertPubChange(pubGroupOld,pubGroup,bgyy) < 1){
            return Result.error("变更记录插入失败！");
        }
        return Result.success();
    }

    @Override
    public Result updatePubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList) {
        if (pubGroupAndApplyList == null){
            return Result.error("参数为空!");
        }
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //创建所需对象
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        List<OmsPubApplyVO> applyVOList = pubGroupAndApplyList.getOmsPubApplyVOList();
        List<OmsPubApply> applyUpdateList = new ArrayList<>();
        List<OmsPubApply> applyInsertList = new ArrayList<>();
        int num = applyVOList.size();
        //修改团组信息
        if(pubGroup != null){
            pubGroupMapper.updatePubGroup(pubGroup);
            //批量修改人员信息
            if(num > 0){
                //出国人员信息
                for(int i = 0; i < num; i++ ){
                    OmsPubApply pubApply = applyVOList.get(i);
                    for (int j = 0; j < num; j++) {
                        if(i != j && pubApply.getProcpersonId().equals(applyVOList.get(j).getProcpersonId())){
                            return Result.error("人员选择重复，请查证！");
                        }
                    }
                    if(StringUtils.isBlank(pubApply.getId())){
                        pubApply = getInsertOmsPubApply(pubApply.getProcpersonId(),pubGroup,pubApply.getB0100());
                        applyInsertList.add(pubApply);
                    }else{
                        pubApply.setModifyUser(userInfo.getId());
                        pubApply.setModifyTime(new Date());
                        applyUpdateList.add(pubApply);
                    }
                }
                if(applyInsertList.size() > 0){
                    pubApplyMapper.insertPubApplyList(applyInsertList);
                }
                if(applyUpdateList.size() > 0){
                    pubApplyMapper.updatePubApplyList(applyUpdateList);
                }
            }
        }
        return Result.success();

    }

    @Override
    public Result deletePubGroup(String id) {
        if (StringUtils.isBlank(id)){
           return Result.error("参数为空!");
        }
        pubApplyMapper.deletePubApplyByYSPId(id);
        pubGroupMapper.deletePubGroup(id);
        return Result.success();
    }

    @Override
    public Result uploadPubGroupJson(MultipartFile file,String orgName,String orgId,String bazt) throws IOException {
        if (file == null || StringUtils.isBlank(bazt)){
            return Result.error("参数为空!");
        }
        try {
            //解析json数据
            Result result = new Result();
            result = readJsonData(file);
            if("0".equals(result.getCode())){
                return result;
            }else{
                OmsPubGroupAndApplyList omsPubGroupAndApplyList = (OmsPubGroupAndApplyList) result.getData();
                if(StringUtils.isBlank(orgId)){
                    omsPubGroupAndApplyList.getOmsPubGroupPreApproval().setB0100(orgName);
                }else{
                    omsPubGroupAndApplyList.getOmsPubGroupPreApproval().setB0100(orgId);
                }
                omsPubGroupAndApplyList.getOmsPubGroupPreApproval().setBazt(Integer.parseInt(bazt));
                insertPubGroup(omsPubGroupAndApplyList);
                return Result.success(omsPubGroupAndApplyList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success();
    }

    @Override
    public Result checkoutPerson(OmsPubGroupAndApplyList pubGroupAndApplyList) {
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        List<OmsPubApplyVO> personList = pubGroupAndApplyList.getOmsPubApplyVOList();
        int num = personList.size();
        if(num > 0){
            //出国人员信息
            for(int i = 0; i < num; i++ ){
                OmsPubApplyVO pubApplyVO = personList.get(i);
                String fmxx = omsConditionService.selectNegativeInfo(pubApplyVO.getA0100(),pubGroup.getCgsj());
                pubApplyVO.setFmxx(fmxx);
                pubApplyVO.setCheckResult(getCheckResult(pubApplyVO.getProcpersonId(),Constants.oms_business[0]));
            }
            return Result.success(personList);
        }else{
            return Result.error("参数为空!");
        }
    }

    @Override
    public Result insertPerson(String personId,String pubId,String b0100){
        if (StringUtils.isBlank(personId) || StringUtils.isBlank(pubId) || StringUtils.isBlank(b0100)){
            return Result.error("参数为空!");
        }
        String msg = "";
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(pubId);
        List<OmsPubApplyVO> applyVOList = pubApplyMapper.selectByYSPId(pubId);
        if(applyVOList.size() > 0){
            for (int i = 0; i < applyVOList.size(); i++) {
                if(personId.equals(applyVOList.get(i).getProcpersonId())
                        && Constants.private_business[7] != applyVOList.get(i).getSqzt()){
                    msg = "当前团组已经存在该人员，请不要重复添加！";
                    return Result.error(msg);
                }
            }
        }
        OmsPubApply pubApply = getInsertOmsPubApply(personId,pubGroup,b0100);
        pubApply.setYspId(pubId);
        pubApply.setZtdw(pubGroup.getZtdw());
        pubApply.setCgsj(pubGroup.getCgsj());
        pubApply.setHgsj(pubGroup.getHgsj());
        pubApply.setSdgj(pubGroup.getSdgj());
        pubApply.setTlsj(String.valueOf(pubGroup.getZwtlsj()));
        pubApply.setCfrw(pubGroup.getCfrw());
        pubApply.setCfsy(pubGroup.getCfsy());
        if(Constants.PUB_GROUP_STATUS_CODE[1] != pubGroup.getSqzt()){
            pubApply.setSqzt(Constants.private_business[0]);
            pubApply.setSfzb(Constants.IS_YES);
            pubApply.setSfxd(IS_ASSIGN);
        }
        if(pubApplyMapper.insert(pubApply) < 1){
            return Result.error("添加失败!");
        }
        return Result.success(msg);
    }

    @Override
    public Result backoutPerson(String id,String cxyy) {
        StringBuffer msg = new StringBuffer();
        if (StringUtils.isBlank(id) || StringUtils.isBlank(cxyy)){
            return Result.error("参数为空!");
        }
        //获取人员详情
        OmsPubApply pubApply = pubApplyMapper.selectById(id);
        if(StringUtils.isBlank(pubApply.getJdcjl()) || Constants.IS_NOT.equals(pubApply.getJdcjl())){
            //撤销人员
            pubApplyMapper.repealPubApplyById(id,cxyy, Constants.private_business[7]);
        }else{
            msg.append("干部监督处已审核完成，不能变更该人员，请先撤消原申请后重新发起新的申请!");
            return Result.error(msg.toString());
        }
        return Result.success();
    }

    @Override
    public Result backoutGroup(String id,String cxyy) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(cxyy)){
            return Result.error("参数为空!");
        }
        //撤销团组
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(id);
        if(pubGroup != null){
            pubGroup.setSqzt(Constants.PUB_GROUP_STATUS_CODE[0]);
            pubGroup.setCxyy(cxyy);
            if(pubGroupMapper.updatePubGroup(pubGroup) < 1){
                return Result.error("撤销失败!");
            }
        }
        //批量撤销人员
        List<OmsPubApply> applylist = pubGroupMapper.getPubApplyByYspId(id);
        if(applylist.size()>0){
            for (int i = 0; i < applylist.size(); i++) {
                pubApplyMapper.repealPubApplyById(applylist.get(i).getId(),cxyy, Constants.private_business[7]);
            }
        }
        return Result.success();
    }

    @Override
    public Result regainGroup(String id) {
        if (StringUtils.isBlank(id)){
            return Result.error("参数为空!");
        }
        //恢复团组
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(id);
        if(pubGroup != null){
            pubGroup.setSqzt(Constants.PUB_GROUP_STATUS_CODE[1]);
            if(pubGroupMapper.updatePubGroup(pubGroup) < 1){
                return Result.error("恢复失败!");
            }
            //批量恢复人员
            List<OmsPubApply> applylist = pubGroupMapper.getPubApplyByYspId(id);
            if(applylist.size()>0){
                for (int i = 0; i < applylist.size(); i++) {
                    if(pubGroup.getCxyy().equals(applylist.get(i).getCxyy())){
                        pubApplyMapper.repealPubApplyById(applylist.get(i).getId(),null, Constants.private_business[0]);
                    }
                }
            }
        }else{
            return Result.error("未获取到当前团组数据！");
        }
        return Result.success();
    }

    @Override
    public Result getPubGroupDetailById(String yspId) {
        if (StringUtils.isBlank(yspId)){
            return Result.error("参数为空!");
        }
        OmsPubGroupAndApplyList beanList = new OmsPubGroupAndApplyList();
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(yspId);
        List<OmsPubApplyVO> applyList = pubApplyMapper.selectByYSPId(yspId);
        StringBuffer tzcy = new StringBuffer();
        for (int i = 0; i < applyList.size(); i++) {
            tzcy.append(applyList.get(i).getName());
            if(i != applyList.size()-1){
                tzcy.append(",");
            }
            //获取校验结果
            applyList.get(i).setCheckResult(getCheckResult(applyList.get(i).getProcpersonId(),Constants.oms_business[0]));
        }
        pubGroup.setTzcy(tzcy.toString());
        beanList.setOmsPubGroupPreApproval(pubGroup);
        beanList.setOmsPubApplyVOList(applyList);
        return Result.success(beanList);
    }

    @Override
    public Result getPersonDetailById(String id) {
        if (StringUtils.isBlank(id)){
            return Result.error("参数为空!");
        }
        return Result.success(pubApplyMapper.selectById(id));
    }

    @Override
    public Result getAuditOpinion(String id) {
        if (StringUtils.isBlank(id)){
            return Result.error("参数为空!");
        }
        List<OmsPubApplyVO> applyVOList = pubApplyMapper.selectByYSPId(id);
        if(applyVOList.size() > 0){
            for (int i = 0; i < applyVOList.size(); i++) {
                OmsPubApplyVO applyVO = applyVOList.get(i);
                if(Constants.private_business[7] == applyVO.getSqzt()){
                    applyVO.setApplyStatus("撤销");
                }
                if(Constants.private_business[7] != applyVO.getSqzt() && Constants.IS_YES.equals(applyVO.getSfzb())){
                    applyVO.setApplyStatus("增补");
                }
            }
        }else{
            return Result.error("未获取到相应数据！");
        }
        return Result.success(applyVOList);
    }

    @Override
    public Result getBackoutDetailById(String id) {
        if (StringUtils.isBlank(id)){
            return Result.error("参数为空!");
        }
        Map<String, Object> resultMap = new HashMap<>();
        //获取团组详情
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(id);
        //获取撤销原因
        String cxyy = pubGroup.getCxyy();
        //获取销毁记录
        List<OmsPubDestroydetail> pubDestroydetailList = pubDestroydetailMapper.getPubDestroydetailByYspId(id);
        //获取回收记录
        List<OmsPubDoccallbackdetail> pubDoccallbackdetailList= pubDoccallbackdetailMapper.getPubDoccallbackdetailByYspId(id);
        //返回结果
        resultMap.put("cxyy",cxyy);
        resultMap.put("pubDestroydetailList",pubDestroydetailList);
        resultMap.put("pubDoccallbackdetailList",pubDoccallbackdetailList);
        return Result.success(resultMap);
    }

    @Override
    public Result sendTask(OmsPubGroupAndApplyList pubGroupAndApplyList,String bazt) {
        StringBuffer msg = new StringBuffer();
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        List<OmsPubApplyVO> applyVOList = pubApplyMapper.selectByYSPId(pubGroup.getId());
        if(pubGroup == null || applyVOList.size() < 1){
            return Result.error("参数为空!");
        }
        try{
            pubGroup.setSqzt(Constants.PUB_GROUP_STATUS_CODE[2]);
            //填写时添加
            if(Constants.IS_NOT.equals(pubGroup.getSource())){
                pubGroup.setBazt(Integer.parseInt(bazt));
                for (int i = 0; i < applyVOList.size(); i++) {
                    if(Constants.private_business[7] != applyVOList.get(i).getSqzt()){
                        if(!StringUtils.isBlank(getCheckResult(applyVOList.get(i).getA0100(),Constants.oms_business[0])) &&
                            Constants.IS_NOT.equals(applyVOList.get(i).getSftsry())){
                            msg.append("姓名：'");
                            msg.append(applyVOList.get(i).getName());
                            msg.append("',校验结果不通过,不能进行下一步操作,请检查!");
                            return Result.error(msg.toString());
                        }
                        applyVOList.get(i).setSqzt(Constants.private_business[0]);
                        applyVOList.get(i).setSfxd(IS_ASSIGN);
                    }
                }
            }
            //上传时修改
            if(Constants.IS_YES.equals(pubGroup.getSource())){
                for (int i = 0; i < applyVOList.size(); i++) {
                    if(!StringUtils.isBlank(getCheckResult(applyVOList.get(i).getA0100(),Constants.oms_business[0])) &&
                            Constants.IS_NOT.equals(applyVOList.get(i).getSftsry())){
                        msg.append("姓名：'");
                        msg.append(applyVOList.get(i).getName());
                        msg.append("',校验结果不通过,不能进行下一步操作,请检查!");
                        return Result.error(msg.toString());
                    }
                    applyVOList.get(i).setSqzt(Constants.private_business[0]);
                    applyVOList.get(i).setSfxd(IS_ASSIGN);
                }
            }
            pubGroupAndApplyList.setOmsPubGroupPreApproval(pubGroup);
            if(Constants.IS_YES.equals(updatePubGroup(pubGroupAndApplyList).getCode())){
                //发送消息
                String b0100 = pubGroup.getB0100();
                String b0101 = pubGroup.getZtdw();
                String message = "因公出国境预备案申请:"+pubGroup.getTzmc()+"，已下发，请审批";
                OmsCommonUtil.SendMessage(message,"3",b0100,b0101);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success();
    }

    @Override
    public Result goToUploadApproval(String id) {
        String msg = "";
        OmsPubGroupPreApproval pubGroup = new OmsPubGroupPreApproval();
        List<OmsPubApplyVO> applyVOList = pubApplyMapper.selectByYSPId(id);
        for (int i = 0; i < applyVOList.size(); i++) {
            if(Constants.private_business[7] != applyVOList.get(i).getSqzt()){
                if(StringUtils.isBlank(applyVOList.get(i).getZzjl())){
                    msg = "还未审核完毕，不能进行下一步操作！";
                    return Result.error(msg);
                }else{
                    if(Constants.IS_NOT.equals(applyVOList.get(i).getZzjl())){
                        msg = "请撤消未通过审核的人员!";
                        return Result.error(msg);
                    }
                }
                applyVOList.get(i).setSqzt(Constants.leader_business[6]);
            }
        }
        //修改流转状态
        OmsPubGroupAndApplyList omsPubGroupAndApplyList = new OmsPubGroupAndApplyList();
        omsPubGroupAndApplyList.setOmsPubGroupPreApproval(pubGroup);
        omsPubGroupAndApplyList.setOmsPubApplyVOList(applyVOList);
        Result result = updatePubGroup(omsPubGroupAndApplyList);
        return result;
    }

    @Override
    public Result uploadApproval(MultipartFile file, String id) {
        if (StringUtils.isBlank(id) ){
            return Result.error("参数为空!");
        }
        try {
            //获得文件的名称
            String fileAllName = file.getOriginalFilename();
            //获得文件的扩展名称
            String fileName = null;
            if ((fileAllName != null) && (fileAllName.length() > 0)) {
                int dot = fileAllName.lastIndexOf('.');
                if ((dot >-1) && (dot < (fileAllName.length()))) {
                    fileName = fileAllName.substring(0, dot);
                }
                //更新批文号
                OmsPubGroupPreApproval pubGroup = new OmsPubGroupPreApproval();
                pubGroup.setId(id);
                pubGroup.setPwh(fileName);
                pubGroup.setSqzt(Constants.PUB_GROUP_STATUS_CODE[3]);
                OmsPubGroupAndApplyList omsPubGroupAndApplyList = new OmsPubGroupAndApplyList();
                List<OmsPubApplyVO> applyVOList = pubApplyMapper.selectByYSPId(id);
                for (int i = 0; i < applyVOList.size(); i++) {
                    applyVOList.get(i).setSqzt(Constants.leader_business[7]);
                }
                omsPubGroupAndApplyList.setOmsPubApplyVOList(applyVOList);
                omsPubGroupAndApplyList.setOmsPubGroupPreApproval(pubGroup);
                updatePubGroup(omsPubGroupAndApplyList);
                //保存批文
                if(saveFile(file,fileAllName,fileName,id) < 1){
                    return Result.error("批文号上传失败！");
                }
                //发送消息
                String b0100 = pubGroup.getB0100();
                String b0101 = pubGroup.getZtdw();
                String message = "因公出国境申请："+pubGroup.getTzmc()+"，批文已上传，请核实批文";
                OmsCommonUtil.SendMessage(message,"3",b0100,b0101);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success();
    }

    @Override
    public Result updateApproval(String pwh, String id) {
        if (StringUtils.isBlank(pwh) || StringUtils.isBlank(id)){
            return Result.error("参数为空!");
        }
        try {
            OmsPubGroupPreApproval pubGroup = new OmsPubGroupPreApproval();
            pubGroup.setId(id);
            pubGroup.setPwh(pwh);
            if(pubGroupMapper.updatePubGroup(pubGroup) < 1){
                return Result.error("批文号更新失败！");
            }
            //发送消息
            String b0100 = pubGroup.getB0100();
            String b0101 = pubGroup.getZtdw();
            String message = "因公出国境申请："+pubGroup.getTzmc()+"，批文号更新为："+pubGroup.getPwh()+"；";
            OmsCommonUtil.SendMessage(message,"3",b0100,b0101);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success("批文号更新成功！");
    }

    @Override
    public Result getNumByStatus(String bazt) {
        if(StringUtils.isBlank(bazt)){
            return Result.error("参数为空");
        }
        return Result.success(pubGroupMapper.getNumByStatus(bazt));
    }


    /**
     * 判断流程是否完结（给三凡用）
     * @param id(团组id)
     */
    @Override
    public void isGroupBeOver(String id){
        if(StringUtils.isBlank(id)){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubGroupPreApproval pubGroup = new OmsPubGroupPreApproval();
        List<OmsPubApplyVO> applyVOList = pubApplyMapper.selectByYSPId(id);
        int size = applyVOList.size();
        int temp = 0;//如果人员完结则+1
        if(size > 0){
            for (int i = 0; i < size; i++) {
                if(Constants.leader_business[Constants.leader_business.length-1] == applyVOList.get(i).getSqzt()){
                    temp += 1;
                }
            }
        }
        if(temp != 0 && temp == size){
            pubGroup.setId(id);
            pubGroup.setSqzt(Constants.PUB_GROUP_STATUS_CODE[4]);
            pubGroupMapper.updatePubGroup(pubGroup);
        }
    }

    /* -------------------------------------------自定义非接口方法------------------------------------------------------- */

    /**
     * 插入团组变更记录
     * @param pubGroupOld(原记录)
     * @param pubGroup(现记录)
     * @param bgyy(变更原因)
     * @return int
     */
    private int insertPubChange(OmsPubGroupPreApproval pubGroupOld,OmsPubGroupPreApproval pubGroup,String bgyy){
        //获取人员信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //添加变更记录
        OmsPubApplyChange pubApplyChange = new OmsPubApplyChange();
        pubApplyChange.setId(UUIDGenerator.getPrimaryKey());
        pubApplyChange.setTtId(pubGroup.getId());
        pubApplyChange.setYcgsj(pubGroupOld.getCgsj());
        pubApplyChange.setYhgsj(pubGroupOld.getHgsj());
        pubApplyChange.setYsdgj(pubGroupOld.getSdgj());
        pubApplyChange.setYtjgj(pubGroupOld.getTjgj());
        pubApplyChange.setYcfsy(pubGroupOld.getCfsy());
        pubApplyChange.setYcfrw(pubGroupOld.getCfrw());
        pubApplyChange.setXcgsj(pubGroup.getCgsj());
        pubApplyChange.setXhgsj(pubGroup.getHgsj());
        pubApplyChange.setXsdgj(pubGroup.getSdgj());
        pubApplyChange.setXtjgj(pubGroup.getTjgj());
        pubApplyChange.setXcfsy(pubGroup.getCfsy());
        pubApplyChange.setXcfrw(pubGroup.getCfrw());
        pubApplyChange.setSqzt(pubGroupOld.getSqzt());
        pubApplyChange.setBgyy(bgyy);
        pubApplyChange.setModifyUser(userInfo.getId());
        pubApplyChange.setModifyTime(new Date());

        return pubApplyChangeMapper.insertSelective(pubApplyChange);
    }

    /**
     * 读取上传Json的数据(导入用)
     * @return OmsPubGroupAndApplyList
     */
    private Result readJsonData(MultipartFile file) throws IOException {
        //读取数据
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        String tempStr;
        while ((tempStr = reader.readLine()) != null) {
            sbf.append(tempStr);
        }
        reader.close();

        //转化数据
        String obj = sbf.toString().trim();
        JSONObject jsonObj = new JSONObject();
        if("[".equals(obj.substring(0,1))){
            jsonObj = JSONObject.parseObject(obj.substring(1,obj.length()-1));
        }else{
            jsonObj = JSONObject.parseObject(obj);
        }
        //map对象
        Map<String, Object> jsonData =new HashMap<>();
        //循环转换
        Iterator it =jsonObj.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            jsonData.put(entry.getKey(), entry.getValue());
        }

        //创建对象
        OmsPubGroupPreApproval omsPubGroupPreApproval = new OmsPubGroupPreApproval();
        List<OmsPubApplyVO> applyVOList = new ArrayList<>();
        OmsPubGroupAndApplyList omsPubGroupAndApplyList = new OmsPubGroupAndApplyList();

        //团组解析
        omsPubGroupPreApproval.setTzmc(jsonData.get("团组名称").toString());
        omsPubGroupPreApproval.setTzcy(jsonData.get("团组成员").toString());
        omsPubGroupPreApproval.setTzrs(Integer.parseInt(jsonData.get("团组人数").toString()));
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        try{
            omsPubGroupPreApproval.setCgsj(sdf.parse(jsonData.get("出国时间").toString()));
        }catch (Exception e){
            e.printStackTrace();
        }
        omsPubGroupPreApproval.setZwtlsj(Integer.parseInt(jsonData.get("在外天数").toString()));
        omsPubGroupPreApproval.setCfsy(jsonData.get("出访事由").toString());
        omsPubGroupPreApproval.setCfrw(jsonData.get("出访任务").toString());
        omsPubGroupPreApproval.setSdgj(jsonData.get("出访国家").toString());
        omsPubGroupPreApproval.setTjgj(jsonData.get("途经国家").toString());
        omsPubGroupPreApproval.setZtdw(jsonData.get("组团单位").toString());
        omsPubGroupPreApproval.setFylykzxm(jsonData.get("费用来源开支项目").toString());
        omsPubGroupPreApproval.setSource("1");

        //团组人员解析
        JSONArray jsonArray = (JSONArray) jsonData.get("省管干部");
        for (int i = 0; i < jsonArray.size(); i++){
            OmsPubApplyVO omsPubApplyVO = new OmsPubApplyVO();
            omsPubApplyVO.setName(jsonArray.getJSONObject(i).get("姓名").toString());
            //根据身份证号获取人员A0100等信息
            String idCardNum = jsonArray.getJSONObject(i).get("身份证号").toString();
            if(idCardNum != null){
                OmsRegProcpersoninfo regProcpersoninfo = regProcpersoninfoMapper.selectPersonInfoByIdCard(idCardNum);
                if(regProcpersoninfo != null){
                    omsPubApplyVO.setProcpersonId(regProcpersoninfo.getId());
                    omsPubApplyVO.setA0100(regProcpersoninfo.getA0100());
                    omsPubApplyVO.setStatus(regProcpersoninfo.getIncumbencyStatus());
                }
                omsPubApplyVO.setIdnumber(idCardNum);
                omsPubApplyVO.setB0101(jsonArray.getJSONObject(i).get("工作单位").toString());
                omsPubApplyVO.setJob(jsonArray.getJSONObject(i).get("职务").toString());
                omsPubApplyVO.setZtnrzw(jsonArray.getJSONObject(i).get("在团职务").toString());
                omsPubApplyVO.setZjcgqk(jsonArray.getJSONObject(i).get("最近一次因公出国信息").toString());
                applyVOList.add(omsPubApplyVO);
            }else{
                return Result.error("身份证号为空！");
            }
        }
        //封装整合对象
        omsPubGroupAndApplyList.setOmsPubGroupPreApproval(omsPubGroupPreApproval);
        omsPubGroupAndApplyList.setOmsPubApplyVOList(applyVOList);
        return Result.success(omsPubGroupAndApplyList);
    }

    /**
     * 封装插入时所需人员实体类
     * @param id(人员id)
     * @return OmsPubApply
     */
    private OmsPubApply getInsertOmsPubApply(String id,OmsPubGroupPreApproval pubGroup,String b0100){
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        OmsPubApply pubApply = new OmsPubApply();
        OmsRegProcpersoninfo personInfo = regProcpersoninfoMapper.selectById(id);
        if(personInfo == null){
            return pubApply;
        }
        pubApply.setId(UUIDGenerator.getPrimaryKey());
        pubApply.setProcpersonId(id);
        pubApply.setA0100(personInfo.getA0100());
        pubApply.setB0100(b0100);
        pubApply.setHealth(personInfo.getHealth());
        pubApply.setPoliticalAff(personInfo.getPoliticalAffiname());
        pubApply.setJob(personInfo.getPost());
        pubApply.setSfzyld(personInfo.getMainLeader());
        Date birthDay = personInfo.getBirthDateGb();
        if(birthDay != null){
            pubApply.setAge(getAge(birthDay));
        }
        if(StringUtils.isBlank(personInfo.getSecretLevel())){
            pubApply.setSfsmry(Constants.IS_NOT);
        }else{
            pubApply.setSmdj(personInfo.getSecretLevel());
            pubApply.setSfsmry(Constants.IS_YES);
        }
        List<OmsPubApply> list = pubApplyMapper.selectPubAbroadLatestInfo(personInfo.getA0100());
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        if(list.size() > 0){
            StringBuffer zjcgqk = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                zjcgqk.append("出国时间："+sdf.format(list.get(i).getCgsj())+",所赴国家："+list.get(i).getSdgj()+",出访任务："+list.get(i).getCfrw()+";");
            }
            pubApply.setZjcgqk(zjcgqk.toString());
        }
        //是否裸官
        pubApply.setSflg(StringUtils.isBlank(personInfo.getNf())?Constants.IS_NOT:personInfo.getNf());
        //负面信息
        String fmxx = omsConditionService.selectNegativeInfo(pubApply.getA0100(),pubGroup.getCgsj());
        pubApply.setFmxx(fmxx);
        pubApply.setSfysp(Constants.IS_YES);
        pubApply.setSqzt(Constants.private_business[Constants.private_business.length-1]);
        pubApply.setSfxd(IS_NOT_ASSIGN);
        pubApply.setSfbg(Constants.IS_NOT);
        pubApply.setSfzb(Constants.IS_NOT);
        pubApply.setSftsry(Constants.IS_NOT);
        pubApply.setDwjsxs(Constants.IS_NOT);
        pubApply.setSource(pubGroup.getSource());
        pubApply.setCreateUser(userInfo.getId());
        pubApply.setCreateTime(new Date());
        return pubApply;
    }

    /**
     * 根据出生日期计算年龄
     * @param birthDay
     * @return int
     */
    private static String getAge(Date birthDay){
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "出生日期晚于当前时间，无法计算!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            }else{
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return String.valueOf(age);
    }

    /**
     * 保存上传文件
     * @param file(MultipartFile)
     * @param fileAllName(文件名全称)
     * @param fileName(文件名)
     * @param bussinessId(业务id)
     * @return int(是否操作成功)
     */
    private int saveFile(MultipartFile file,String fileAllName,String fileName,String bussinessId){
        //保存文件的绝对路径
        String filePath = attachmentPath+ File.separator+"static"+File.separator;
        //获得文件的扩展名称
        String extensionName = fileAllName.substring(fileAllName.lastIndexOf(".")+1).toLowerCase();
        try{
            //上传文件
            LeaderSupervisionUntil.uploadFile(file.getBytes(),filePath,fileAllName);
            //登录用户信息
            UserInfo userInfo = UserInfoUtil.getUserInfo();
            //查看是否上传过批文，如果已上传则替换（先删除再插入）
            OmsAttachment attachmentOld = attachmentMapper.getAttachmentByBussinessId(bussinessId);
            //封装附件实体类
            OmsAttachment attachment = new OmsAttachment();
            if(attachmentOld != null){
                attachmentMapper.deleteById(attachmentOld.getId());
            }

            attachment.setId(UUIDGenerator.getPrimaryKey());
            attachment.setType(extensionName);
            attachment.setSize(String.valueOf(file.getSize()));
            attachment.setName(fileName);
            attachment.setUrl(filePath + fileAllName);
            attachment.setBussinessOccureStpet(String.valueOf(Constants.PUB_GROUP_STATUS_CODE[3]));
            attachment.setBussinessOccureStpetName(Constants.PUB_GROUP_STATUS_NAME[3]);
            attachment.setBussinessid(bussinessId);
            attachment.setCreateUser(userInfo.getId());
            attachment.setCreateTime(new Date());
            return attachmentMapper.insert(attachment);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            throw new CustomMessageException("文件上传失败");
        }
    }

    /**
     * 获取检验结果
     * @param id(人员A0100)
     * @param type(类型oms_pub_apply)
     * @return String(校验结果)
     */
    private String getCheckResult(String id,String type){
        StringBuffer result = new StringBuffer();
        Map<String,String> map = new HashMap<>();
        List<Map<String,String>> resultList = omsConditionService.checkConditionByA0100(id,type);
        if(resultList.size() > 0){
            for (int i = 0; i < resultList.size(); i++) {
                map = resultList.get(i);
                if(Constants.IS_NOT.equals(map.get("isFit"))){
                    result.append(map.get("title")+";");
                }
            }
        }
        return result.toString();
    }
}
