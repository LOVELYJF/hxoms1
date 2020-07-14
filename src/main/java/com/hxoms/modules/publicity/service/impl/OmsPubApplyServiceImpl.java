package com.hxoms.modules.publicity.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.b01temp.entity.OmsB01Temp;
import com.hxoms.modules.b01temp.mapper.OmsB01TempMapper;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.publicity.entity.*;
import com.hxoms.modules.publicity.mapper.OmsPubApplyChangeMapper;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.publicity.mapper.OmsPubGroupPreApprovalMapper;
import com.hxoms.modules.publicity.service.OmsPubApplyService;
import com.hxoms.support.b01.entity.B01Tree;
import com.hxoms.support.b01.service.OrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsPubApplyServiceImpl implements OmsPubApplyService {
    @Autowired
    private OmsPubApplyMapper omsPubApplyMapper;
    @Autowired
    private OrgService orgService;
    @Autowired
    private OmsSmrOldInfoMapper omsSmrOldInfoMapper;
    @Autowired
    private OmsB01TempMapper omsB01TempMapper;
    @Autowired
    private OmsConditionService omsConditionService;
    @Autowired
    private OmsPubGroupPreApprovalMapper omsPubGroupPreApprovalMapper;
    @Autowired
    private OmsPubApplyChangeMapper omsPubApplyChangeMapper;
    @Override
    public List<PersonInfoVO> selectPersonListByOrg(String b0100) {
        return omsPubApplyMapper.selectPersonListByOrg(b0100);
    }

    @Override
    public OmsPubApplyVO selectPubApplyPersonInfo(String b0100, String a0100) {
        OmsPubApplyVO omsPubApply = new OmsPubApplyVO();
        //备案表中获取基本信息
        Map<String, Object> personInfo = omsPubApplyMapper.selectBasePersonInfo(b0100, a0100);
        omsPubApply.setName((String) personInfo.get("NAME"));//姓名
        omsPubApply.setA0100((String) personInfo.get("A0100"));//主键
        omsPubApply.setB0100((String) personInfo.get("B0100"));//工作单位
        omsPubApply.setB0101((String) personInfo.get("B0101"));//机构名称
        omsPubApply.setBirthDate((Date) personInfo.get("BIRTH_DATE"));//出生年月
        omsPubApply.setAge((String) personInfo.get("AGE"));//年龄
        omsPubApply.setSex((String) personInfo.get("SEX"));//性别
        omsPubApply.setHealth((String) personInfo.get("HEALTH"));//健康状况
        omsPubApply.setPoliticalAff((String) personInfo.get("POLITICAL_AFFI"));//政治面貌
        omsPubApply.setJob((String) personInfo.get("JOB"));//职务
        omsPubApply.setSfsmry("0");//是否为涉密人员
        String smdj = (String) personInfo.get("SECRET_LEVEL");
        if (!StringUtils.isBlank(smdj) && !smdj.equals("非涉密")) {
            omsPubApply.setSfsmry("1");
            omsPubApply.setSmdj((String) personInfo.get("SECRET_LEVEL"));//涉密等级
        }
        omsPubApply.setSflg((String) personInfo.get("NF"));//是否裸官
        //获取最近一次出国情况
        List<OmsPubApply> latestInfoList = omsPubApplyMapper.selectPubAbroadLatestInfo(a0100);
        if (latestInfoList != null && !latestInfoList.isEmpty()) {
            OmsPubApply latestInfo = latestInfoList.get(0);
            StringBuilder sb = new StringBuilder();
            Date cgsj = latestInfo.getCgsj();
            Date hgsj = latestInfo.getHgsj();
            String sdgj = latestInfo.getSdgj();
            String cfrw = latestInfo.getCfrw();
            if (cgsj != null && hgsj != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd号");
                sb.append(sdf.format(cgsj)).append("至").append(sdf.format(hgsj)).append("，");
            }
            if (!StringUtils.isBlank(sdgj)) {
                sb.append("赴").append(sdgj);
            }
            if (!StringUtils.isBlank(cfrw)) {
                sb.append("进行").append(cfrw).append("。");
            }
            omsPubApply.setZjcgqk(sb.toString());
        }
        //获取涉密信息

        //获取负面信息
        //TODO
        //单位接收巡视
        //TODO
        //主要家庭人员信息
        //TODO
        return omsPubApply;
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String insertOrUpdatePubApply(OmsPubApply omsPubApply) {
        if (StringUtils.isBlank(omsPubApply.getA0100())) {
            throw new CustomMessageException("请先选择申请的干部");
        }

        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        String result = "";//返回信息
        String primaryKey = UUIDGenerator.getPrimaryKey();
        if (StringUtils.isBlank(omsPubApply.getId())) {
            omsPubApply.setId(primaryKey);
            //创建人
            omsPubApply.setCreateUser(loginUser.getId());
            //创建时间
            omsPubApply.setCreateTime(new Date());
            //申请状态
            omsPubApply.setSqzt(0);
            //如果是琼赴台，往预备案表插入数据
            if (omsPubApply.getPwh().contains("琼台赴")){
                OmsPubGroupPreApproval selectOmsPubGroupPreApproval = omsPubGroupPreApprovalMapper.selectByPrimaryKey(omsPubApply.getPwh());
                if (selectOmsPubGroupPreApproval == null) {
                    OmsPubGroupPreApproval omsPubGroupPreApproval = new OmsPubGroupPreApproval();
                    //将台办通知书文号作为主键
                    omsPubGroupPreApproval.setId(omsPubApply.getPwh());
                    //出境时间、
                    omsPubGroupPreApproval.setCgsj(omsPubApply.getCgsj());
                    // 入境时间、
                    omsPubGroupPreApproval.setHgsj(omsPubApply.getHgsj());
                    // 任务、
                    omsPubGroupPreApproval.setCfrw(omsPubApply.getCfrw());
                    // 事由
                    omsPubGroupPreApproval.setCfsy(omsPubApply.getCfsy());
                    //创建人
                    omsPubGroupPreApproval.setCreateUser(loginUser.getId());
                    //创建时间
                    omsPubGroupPreApproval.setCreateTime(new Date());
                    //申请状态
                    omsPubGroupPreApproval.setSqzt(omsPubApply.getSqzt());
                    omsPubGroupPreApprovalMapper.insertSelective(omsPubGroupPreApproval);
                }
            }
            omsPubApplyMapper.insert(omsPubApply);
        } else {
            primaryKey = omsPubApply.getId();
            omsPubApplyMapper.updateById(omsPubApply);
        }
        //        int i = omsPubApplyMapper.excuteSelectSql("select count(1) from oms_pub_apply where id='" + primaryKey + "'");
        //特殊情况处理

        //判断校验类型
        result = checkPersonApply(omsPubApply.getA0100(), primaryKey, "1");
        if (StringUtils.isBlank(result)) {
            result = "保存成功";
        }
        return result;
    }

    @Override
    public List<OmsPubApply> selectPubApplyList() {
        //获取当前人员的机构和子机构
        List<Tree> treeList = orgService.selectOrgTreeList();
        List<OmsPubApply> applyList = null;
        if (treeList == null || treeList.isEmpty()) {
            return applyList;
        }
        String batchId = UUIDGenerator.getPrimaryKey();
        List<OmsB01Temp> b01TempList = new ArrayList<>();
        for (Tree tree : treeList) {
            OmsB01Temp omsB01Temp = new OmsB01Temp();
            omsB01Temp.setBatchId(batchId);
            omsB01Temp.setB0100(((B01Tree) tree).getB0100());
            b01TempList.add(omsB01Temp);
        }
        //分批次查询,每次查询500机构
        int batch = 500;
        int size = b01TempList.size();
        for (int i = 0; i < size / batch; i++) {
            int count = (i + 1) * batch;
            if (count <= size) {
                omsB01TempMapper.insertBatch(b01TempList.subList(i * batch, count));
            } else {
                omsB01TempMapper.insertBatch(b01TempList.subList(i * batch, size));
            }
        }
        //关联表查询机构下的人员
        applyList = omsPubApplyMapper.selectPubApplyList(batchId);
        //删除临时表中的当前批次的数据
        omsB01TempMapper.deleteByBatchId(batchId);
        return applyList;
    }

    @Override
    public OmsPubApply selectPubApplyById(String id) {
        return omsPubApplyMapper.selectById(id);
    }

    /**
     * 功能描述: <br>
     * 〈根据id删除备案人员〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/24 9:06
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void deletePubApplyById(String id) {
        if (id == null || id.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        omsPubApplyMapper.deletePubApplyById(id);
    }

    /**
     * 功能描述: <br>
     * 〈根据条件查询备案申请列表〉
     * @Param: [omsPubApplyQueryParam]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/6/28 18:04
     */
    @Override
    public PageInfo getPubAppListByCondition(OmsPubApplyQueryParam omsPubApplyQueryParam) {
        Integer pageNum = omsPubApplyQueryParam.getPageNum();
        if (pageNum == null) {
            pageNum = 1;
        }
        Integer pageSize = omsPubApplyQueryParam.getPageSize();
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);   //设置传入页码，以及每页的大小
        /**申请状态集合 */
        List<String> status = omsPubApplyQueryParam.getStatus();
        /**组团单位*/
        String ztdw = omsPubApplyQueryParam.getZtdw();
        /** 出国时间*/
        Date cgsj = omsPubApplyQueryParam.getCgsj();
        /** 回国时间*/
        Date hgsj = omsPubApplyQueryParam.getHgsj();
        /** 姓名*/
        String name = omsPubApplyQueryParam.getName();
        /** 通知书文号*/
        String pwh = omsPubApplyQueryParam.getPwh();
        List<OmsPubApplyVO> list = omsPubApplyMapper.getPubAppListByCondition(status,name,cgsj,hgsj,ztdw,pwh);
        PageInfo info = new PageInfo(list);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈撤销通知书文号相同的备案申请〉
     * @Param: [pwh, cxyy]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/1 9:02
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void repealAllPubApplyByPwh(String pwh, String cxyy) {
        if (pwh == null || pwh.equals("")){
            throw new CustomMessageException("通知书文号为空!");
        }
        if (cxyy == null || cxyy.equals("")){
            throw new CustomMessageException("撤销原因为空!");
        }
        //查询相关通知书文号的备案申请
        List<OmsPubApplyVO> list = omsPubApplyMapper.selectPubApplyListByPwh(pwh);
        if (list != null && list.size()>0){
            String sqzt = String.valueOf(Constants.private_business[31]);
            omsPubApplyMapper.repealAllPubApplyByPwh(pwh,cxyy,sqzt);
        }else {
            throw new CustomMessageException("该通知书文号下没有相关数据!");
        }

    }

    /***
     * 功能描述: <br>
     * 〈通过id撤销备案申请〉
     * @Param: [id, cxyy]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/1 10:33
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void repealPubApplyById(String id, String cxyy) {
        if (id == null || id.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        if (cxyy == null || cxyy.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubApply omsPubApply = omsPubApplyMapper.selectById(id);
        if (omsPubApply != null){
            String sqzt = String.valueOf(Constants.private_business[31]);
            omsPubApplyMapper.repealPubApplyById(id,cxyy,sqzt);
        }
    }

    /**
     * 功能描述: <br>
     * 〈获取台办变更前信息〉
     * @Param: [pwh]
     * @Return: com.hxoms.modules.publicity.entity.OmsPubApplyChange
     * @Author: 李逍遥
     * @Date: 2020/7/3 11:46
     */
    @Override
    public OmsPubApplyChange getPubApplyChangeByPwh(String pwh) {
        if (pwh == null || pwh.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubApplyChange omsPubApplyChange = new OmsPubApplyChange();
        //通过批文号查询预备案信息
        OmsPubGroupPreApproval omsPubGroupPreApproval = omsPubGroupPreApprovalMapper.selectByPrimaryKey(pwh);
        if (omsPubGroupPreApproval != null){
            //备案ID
            omsPubApplyChange.setBaId(pwh);
            //原出国时间
            omsPubApplyChange.setYcgsj(omsPubGroupPreApproval.getCgsj());
            //原入境时间、
            omsPubApplyChange.setYhgsj(omsPubGroupPreApproval.getHgsj());
            // 原出访任务、
            omsPubApplyChange.setYcfrw(omsPubGroupPreApproval.getCfrw());
            // 原出访事由
            omsPubApplyChange.setYcfsy(omsPubGroupPreApproval.getCfsy());
        }
        return omsPubApplyChange;
    }

    /**
     * 功能描述: <br>
     * 〈保存台办变更后信息〉
     * @Param: [omsPubApplyChange]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/3 11:46
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void insertPubApplyChange(OmsPubApplyChange omsPubApplyChange) {
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        if (omsPubApplyChange == null){
            throw new CustomMessageException("参数为空");
        }
        //保存台办变更信息、
        omsPubApplyChange.setId(UUIDGenerator.getPrimaryKey());
        omsPubApplyChange.setModifyUser(loginUser.getId());
        omsPubApplyChange.setModifyTime(new Date());
        omsPubApplyChangeMapper.insertSelective(omsPubApplyChange);
        // 更新预备案申请表、
        OmsPubGroupPreApproval omsPubGroupPreApproval = new OmsPubGroupPreApproval();
        //备案号
        omsPubGroupPreApproval.setId(omsPubApplyChange.getBaId());
        //现出国时间
        omsPubGroupPreApproval.setCgsj(omsPubApplyChange.getXcgsj());
        //现回国时间
        omsPubGroupPreApproval.setHgsj(omsPubApplyChange.getXhgsj());
        //现出访任务
        omsPubGroupPreApproval.setCfrw(omsPubApplyChange.getXcfrw());
        //现出访事由
        omsPubGroupPreApproval.setCfsy(omsPubApplyChange.getXcfsy());
        //现申请状态
        omsPubGroupPreApproval.setSqzt(omsPubApplyChange.getSqzt());
        omsPubGroupPreApprovalMapper.updateByPrimaryKeySelective(omsPubGroupPreApproval);
        // 更新备案申请表
        OmsPubApply omsPubApply = new OmsPubApply();
        //批文号
        omsPubApply.setPwh(omsPubApplyChange.getBaId());
        //现出国时间、
        omsPubApply.setCgsj(omsPubApplyChange.getXcgsj());
        // 现回国时间、
        omsPubApply.setHgsj(omsPubApplyChange.getXhgsj());
        // 现出访事由、
        omsPubApply.setCfsy(omsPubApplyChange.getXcfsy());
        // 现出访任务、
        omsPubApply.setCfrw(omsPubApplyChange.getXcfrw());
        // 修改时间、
        omsPubApply.setModifyTime(new Date());
        // 修改人、
        omsPubApply.setModifyUser(loginUser.getId());
        // 修改时申请状态
        omsPubApply.setSqzt(omsPubApplyChange.getSqzt());
        omsPubApplyMapper.updateByPwh(omsPubApply);
    }

    /**
     * 功能描述: <br>
     * 〈通过批文号获取变更前后信息〉
     * @Param: [pwh]
     * @Return: com.hxoms.modules.publicity.entity.OmsPubApplyChange
     * @Author: 李逍遥
     * @Date: 2020/7/6 10:08
     */
    @Override
    public OmsPubApplyChange getPubApplyChange(String pwh) {
        if (pwh == null || pwh.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubApplyChange omsPubApplyChange = omsPubApplyChangeMapper.selectByPrimaryPwh(pwh);
        return omsPubApplyChange;
    }

    /**
     * 功能描述: <br>
     * 〈添加干教因公出国备案申请〉
     * @Param: [omsPubGroupPreApproval]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/7/6 15:52
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String insertOrUpdatePubGroupPreApproval(OmsPubGroupPreApprovalVO omsPubGroupPreApproval) {
        //保存预审批
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        String result = "";//返回信息
        //预审批主键
        String id = omsPubGroupPreApproval.getId();
        if (id == null || id.equals("")){
            //新增预审批信息
            String primaryKey = UUIDGenerator.getPrimaryKey();
            omsPubGroupPreApproval.setId(primaryKey);
            //备案主体
            omsPubGroupPreApproval.setBazt(1);
            //创建人、
            omsPubGroupPreApproval.setCreateUser(loginUser.getId());
            // 创建时间
            omsPubGroupPreApproval.setCreateTime(new Date());
            //申请状态

            omsPubGroupPreApprovalMapper.insertSelective(omsPubGroupPreApproval);
            //将预审批人员添加到备案申请
            List<PersonInfoVO> personInfoVOS = omsPubGroupPreApproval.getPersonInfoVOS();
            if (personInfoVOS != null && personInfoVOS.size() > 0){
                for (PersonInfoVO personInfoVO: personInfoVOS) {
                    OmsPubApply omsPubApply = new OmsPubApply();
                    //备案表中获取基本信息
                    Map<String, Object> personInfo = omsPubApplyMapper.selectBasePersonInfo(personInfoVO.getB0100(), personInfoVO.getA0100());
                    omsPubApply.setId(UUIDGenerator.getPrimaryKey());//主键
                    omsPubApply.setA0100(personInfoVO.getA0100());//A0100
                    omsPubApply.setB0100(personInfoVO.getB0100());//B0100
                    omsPubApply.setYspId(primaryKey);//预审批主键
                    omsPubApply.setYspdwId(omsPubGroupPreApproval.getB0100());//预审批发起单位
                    omsPubApply.setAge((String) personInfo.get("AGE"));//年龄
                    omsPubApply.setPoliticalAff((String) personInfo.get("POLITICAL_AFFI"));//政治面貌
                    omsPubApply.setJob(personInfoVO.getJob());//职务
                    omsPubApply.setHealth((String) personInfo.get("HEALTH"));//健康状况
                    omsPubApply.setSfsmry("0");//是否为涉密人员
                    String smdj = (String) personInfo.get("SECRET_LEVEL");
                    if (!StringUtils.isBlank(smdj) && !smdj.equals("非涉密")) {
                        omsPubApply.setSfsmry("1");
                        omsPubApply.setSmdj((String) personInfo.get("SECRET_LEVEL"));//涉密等级
                    }
                    omsPubApply.setZtdw(omsPubGroupPreApproval.getZtdw());//组团单位
                    omsPubApply.setCgsj(omsPubGroupPreApproval.getCgsj());//出国时间
                    omsPubApply.setHgsj(omsPubGroupPreApproval.getHgsj());//回国时间
                    omsPubApply.setSdgj(omsPubGroupPreApproval.getSdgj());//所到国境
                    omsPubApply.setTlsj(String.valueOf(omsPubGroupPreApproval.getZwtlsj()));//在外停留时间
                    omsPubApply.setCfrw(omsPubGroupPreApproval.getCfrw());//出访任务
                    omsPubApply.setCfsy(omsPubGroupPreApproval.getCfsy());//出访事由
                    //申请状态

                    omsPubApply.setCreateUser(loginUser.getId());//创建人
                    omsPubApply.setCreateTime(new Date());//创建时间
                    omsPubApplyMapper.insert(omsPubApply);
                    //判断校验类型
                    result = checkPersonApply(omsPubApply.getA0100(), omsPubApply.getId(), "1");
                    if (StringUtils.isBlank(result)) {
                        result = "保存成功";
                    }
                }
            }else {
                throw new CustomMessageException("参数为空!");
            }
        }else {
            //更新
            //查找原有预审批内容
            OmsPubGroupPreApproval selectOmsPubGroupPreApproval = omsPubGroupPreApprovalMapper.selectByPrimaryKey(id);
            //申请状态

            omsPubGroupPreApprovalMapper.updateByPrimaryKeySelective(omsPubGroupPreApproval);
            //新增预审批更改表信息
            OmsPubApplyChange omsPubApplyChange = new OmsPubApplyChange();
            omsPubApplyChange.setId(UUIDGenerator.getPrimaryKey());//主键
            omsPubApplyChange.setBaId(id);//备案表主键
            omsPubApplyChange.setYcgsj(selectOmsPubGroupPreApproval.getCgsj());//原出国时间
            omsPubApplyChange.setYhgsj(selectOmsPubGroupPreApproval.getHgsj());//原回国时间
            omsPubApplyChange.setYcfrw(selectOmsPubGroupPreApproval.getCfrw());//原出访任务
            omsPubApplyChange.setYcfsy(selectOmsPubGroupPreApproval.getCfsy());//原出访事由
            omsPubApplyChange.setYsdgj(selectOmsPubGroupPreApproval.getSdgj());//原出访国
            omsPubApplyChange.setYtjgj(selectOmsPubGroupPreApproval.getTjgj());//原途径国家
            omsPubApplyChange.setXcgsj(omsPubGroupPreApproval.getCgsj());//现出国时间
            omsPubApplyChange.setXhgsj(omsPubGroupPreApproval.getHgsj());//现回国时间
            omsPubApplyChange.setXcfrw(omsPubGroupPreApproval.getCfrw());//现出访任务
            omsPubApplyChange.setXcfsy(omsPubGroupPreApproval.getCfsy());//现出访事由
            omsPubApplyChange.setXsdgj(omsPubGroupPreApproval.getSdgj());//现出访国
            omsPubApplyChange.setXtjgj(omsPubGroupPreApproval.getTjgj());//现途径国
            omsPubApplyChange.setModifyUser(loginUser.getId());//修改人
            omsPubApplyChange.setModifyTime(new Date());//修改时间
            //修改时状态

            omsPubApplyChangeMapper.insertSelective(omsPubApplyChange);
            //更新团体人员
            List<OmsPubApplyVO> list = omsPubApplyMapper.selectByYSPId(id);
            if (list != null && list.size()>0){
                for (OmsPubApply omsPubApply: list) {
                    omsPubApply.setCgsj(omsPubGroupPreApproval.getCgsj());//现出国时间
                    omsPubApply.setHgsj(omsPubGroupPreApproval.getHgsj());//现回国时间
                    omsPubApply.setCfrw(omsPubGroupPreApproval.getCfrw());//现出访任务
                    omsPubApply.setCfsy(omsPubGroupPreApproval.getCfsy());//现出访事由
                    omsPubApply.setSdgj(omsPubGroupPreApproval.getSdgj());//现出访国
                    omsPubApply.setModifyUser(loginUser.getId());//修改人
                    omsPubApply.setModifyTime(new Date());//修改时间
                    //修改时状态

                    omsPubApplyMapper.updateById(omsPubApply);
                    //判断校验类型
                    result = checkPersonApply(omsPubApply.getA0100(), omsPubApply.getId(), "1");
                    if (StringUtils.isBlank(result)) {
                        result = "保存成功";
                    }
                }
            }

        }
        return result;
    }

    @Override
    public PageInfo getPubGroupPreApprovalByCondition(OmsPubApplyQueryParam omsPubApplyQueryParam) {
        Integer pageNum = omsPubApplyQueryParam.getPageNum();
        if (pageNum == null) {
            pageNum = 1;
        }
        Integer pageSize = omsPubApplyQueryParam.getPageSize();
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);   //设置传入页码，以及每页的大小
         /** 团组名称*/
        String tzmc = omsPubApplyQueryParam.getTzmc();
        /**申请状态集合 */
        List<String> status = omsPubApplyQueryParam.getStatus();
        /**组团单位*/
        String ztdw = "省干教处";
        /** 出国时间*/
        Date cgsj = omsPubApplyQueryParam.getCgsj();
        /** 回国时间*/
        Date hgsj = omsPubApplyQueryParam.getHgsj();
        List<OmsPubGroupPreApprovalVO> OmsPubGroupPreApproval = omsPubGroupPreApprovalMapper.selectByCondition(tzmc,status,ztdw,cgsj,hgsj);
        if (OmsPubGroupPreApproval != null && OmsPubGroupPreApproval.size()>0){
            for (OmsPubGroupPreApprovalVO omsPubGroupPreApproval:OmsPubGroupPreApproval) {
                String id = omsPubGroupPreApproval.getId();
                List<PersonInfoVO> personInfoVOS = omsPubGroupPreApproval.getPersonInfoVOS();
                List<OmsPubApplyVO> list = omsPubApplyMapper.selectByYSPId(id);
                getPersonInfoVO(personInfoVOS, list);
            }
        }
        PageInfo info = new PageInfo(OmsPubGroupPreApproval);
        return info;
    }
    /**
     * 功能描述: <br>
     * 〈根据ID查看干教信息〉
     * @Param: [id]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/7 17:48
     */
    @Override
    public OmsPubGroupPreApprovalVO getPubGroupPreApprovalById(String id) {
        if (id == null || id.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        OmsPubGroupPreApprovalVO OmsPubGroupPreApproval = omsPubGroupPreApprovalMapper.selectByPrimaryKey(id);
        if (OmsPubGroupPreApproval != null) {
            String id1 = OmsPubGroupPreApproval.getId();
            List<PersonInfoVO> personInfoVOS = OmsPubGroupPreApproval.getPersonInfoVOS();
            List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.selectByYSPId(id1);
            getPersonInfoVO(personInfoVOS, omsPubApplyVOS);
        }
        return OmsPubGroupPreApproval;
    }

    /** 封装方法——封装干教人员信息*/
    private void getPersonInfoVO(List<PersonInfoVO> personInfoVOS, List<OmsPubApplyVO> omsPubApplyVOS) {
        for (OmsPubApplyVO omsPubApplyVO : omsPubApplyVOS) {
            PersonInfoVO personInfoVO = new PersonInfoVO();
            personInfoVO.setB0101(omsPubApplyVO.getB0101());//单位名称
            personInfoVO.setBirthDate(omsPubApplyVO.getBirthDate());//出生日期
            personInfoVO.setJob(omsPubApplyVO.getJob());//职务
            personInfoVO.setName(omsPubApplyVO.getName());//姓名
            personInfoVO.setSex(omsPubApplyVO.getSex());//性别
            //状态

            personInfoVOS.add(personInfoVO);
        }
    }

    /**
     * 功能描述: <br>
     * 〈根据ID删除干教信息〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/7 17:36
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void deletePubGroupPreApprovalById(String id) {
        if (id == null || id.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        //删除预备案信息
        omsPubGroupPreApprovalMapper.deleteByPrimaryKey(id);
        //删除预备案更改信息
        omsPubApplyChangeMapper.deleteByPrimaryBAID(id);
        //删除备案申请表人员信息
        omsPubApplyMapper.deletePubApplyByYSPId(id);
    }



    /**
     * 校验用户信息
     *
     * @author sunqian
     * @date 2020/4/28 11:01
     */
    public String checkPersonApply(String a0100, String id, String conditionType) {
        //返回的校验信息集合
        List<String> stringList = new ArrayList<>();
        String result = null;
        //查询所有的校验信息
        List<OmsCondition> list = omsPubApplyMapper.selectCheckCondition(conditionType);
        //保存前校验
        List<OmsCondition> preList = new ArrayList<>();
        //保存后校验
        List<OmsCondition> sufList = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (OmsCondition omsCondition : list) {
            if ("1".equals(omsCondition.getCheckType())) {
                preList.add(omsCondition);
            } else {
                sufList.add(omsCondition);
            }
        }
        //先进行保存前校验
        if (!preList.isEmpty()) {
            for (OmsCondition omsCondition : preList) {
                String sql = omsCondition.getSqlContent();
                if (StringUtils.isBlank(sql)) {
                    continue;
                }
                sql = sql.replace("@personId", a0100).replace("@id", id);
                int count = omsPubApplyMapper.excuteSelectSql(sql);
                if (count > 0) {
                    stringList.add(omsCondition.getName());
                    //添加记录
                }
            }
            if (!stringList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String string : stringList) {
                    sb.append(string).append("<br/>");
                }
                //保存前校验,直接抛出异常,中断执行,上层方法捕获异常后回滚事务
                throw new CustomMessageException(sb.toString());
            }
        }
        //保存后校验
        if (!sufList.isEmpty()) {
            for (OmsCondition omsCondition : sufList) {
                String sql = omsCondition.getSqlContent();
                if (StringUtils.isBlank(sql)) {
                    continue;
                }
                sql = sql.replace("@personId", a0100).replace("@id", id);
                int count = omsPubApplyMapper.excuteSelectSql(sql);
                if (count > 0) {
                    stringList.add(omsCondition.getName());
                    //添加记录
                }
            }
            if (!stringList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String string : stringList) {
                    sb.append(string).append("<br/>");
                }
                result = sb.toString();
            }
        }
        return result;
    }
}
