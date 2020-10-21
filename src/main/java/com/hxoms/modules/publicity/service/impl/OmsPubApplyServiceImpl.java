package com.hxoms.modules.publicity.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.utils.*;
import com.hxoms.modules.b01temp.entity.OmsB01Temp;
import com.hxoms.modules.b01temp.mapper.OmsB01TempMapper;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.modules.keySupervision.familyMember.mapper.A36Mapper;
import com.hxoms.modules.keySupervision.patrolUnit.service.OmsSupPatrolUnitService;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.service.OmsPriApplyService;
import com.hxoms.modules.publicity.entity.*;
import com.hxoms.modules.publicity.mapper.OmsPubApplyChangeMapper;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.publicity.mapper.OmsPubGroupPreApprovalMapper;
import com.hxoms.modules.publicity.service.OmsPubApplyService;
import com.hxoms.support.b01.entity.B01Tree;
import com.hxoms.support.b01.service.OrgService;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsPubApplyServiceImpl implements OmsPubApplyService {
    @Autowired
    private OmsPubApplyMapper omsPubApplyMapper;
    @Autowired
    private OrgService orgService;
    @Autowired
    private OmsB01TempMapper omsB01TempMapper;
    @Autowired
    private OmsPubGroupPreApprovalMapper omsPubGroupPreApprovalMapper;
    @Autowired
    private OmsPubApplyChangeMapper omsPubApplyChangeMapper;
    @Autowired
    private OmsConditionService omsConditionService;
    @Autowired
    private OmsSupPatrolUnitService omsSupPatrolUnitService;
    @Autowired
    private A36Mapper a36Mapper;
    @Autowired
    private OmsSmrOldInfoMapper omsSmrOldInfoMapper;
    @Autowired
    private OmsPriApplyService omsPriApplyService;

    @Override
    public List<PersonInfoVO> selectPersonListByOrg(List<String> b0100, String keyword, String type) {
        /** type 说明（1-经办人，2、管理员维护，3、因公、因私、特殊人员，4、调整期干部） */
        List<PersonInfoVO> personInfoVOS = null;
        ArrayList<String> a0165 = new ArrayList<>();
        if ("1".equals(type)) {
            //经办人、只能查询非省管、中管干部
            a0165.add("01");
            a0165.add("02");
            personInfoVOS = omsPubApplyMapper.selectPersonListForOperator(a0165, b0100, keyword);
        } else if ("2".equals(type)) {
            //管理员维护：查询所有人员
            personInfoVOS = omsPubApplyMapper.selectPersonListForOperator(a0165, b0100, keyword);
        } else if ("3".equals(type)) {
            //因公、因私、特殊人员查询登记备案库人员
            personInfoVOS = omsPubApplyMapper.selectPersonListByOrg(b0100, keyword);
        } else if ("4".equals(type)) {
            //调整期干部：查询省管干部
            personInfoVOS = omsPubApplyMapper.selectPersonListForTZQGB(b0100, keyword);
        }
        return personInfoVOS;
    }

    @Override
    public OmsPubApplyVO selectPubApplyPersonInfo(String b0100, String a0100) {
        OmsPubApplyVO omsPubApply = new OmsPubApplyVO();
        //备案表中获取基本信息
        Map<String, Object> personInfo = omsPubApplyMapper.selectBasePersonInfo(b0100, a0100);
        //姓名
        omsPubApply.setName((String) personInfo.get("NAME"));
        //主键
        omsPubApply.setA0100((String) personInfo.get("A0100"));
        //备案表主键
        omsPubApply.setProcpersonId((String) personInfo.get("procpersonId"));
        //工作单位
        omsPubApply.setB0100((String) personInfo.get("B0100"));
        //机构名称
        omsPubApply.setB0101((String) personInfo.get("B0101"));
        //出生年月
        omsPubApply.setBirthDate((Date) personInfo.get("BIRTH_DATE_GB"));
        //年龄
        omsPubApply.setAge((String) personInfo.get("AGE"));
        //性别
        omsPubApply.setSex((String) personInfo.get("SEX"));
        //健康状况
        omsPubApply.setHealth((String) personInfo.get("HEALTH"));
        //政治面貌
        omsPubApply.setPoliticalAff((String) personInfo.get("POLITICAL_AFFINAME"));
        //职务
        omsPubApply.setJob((String) personInfo.get("JOB"));
        //核心涉密人员年审
        omsPubApply.setNssj((Date) personInfo.get("SECRET_REVIEW_DATE"));
        //是否为涉密人员
        omsPubApply.setSfsmry("0");
        String smdj = (String) personInfo.get("SECRET_LEVEL");
        if (!StringUtils.isBlank(smdj) && !"非涉密".equals(smdj)) {
            omsPubApply.setSfsmry("1");
            //涉密等级
            omsPubApply.setSmdj((String) personInfo.get("SECRET_LEVEL"));
        }
        //是否裸官
        omsPubApply.setSflg((String) personInfo.get("NF"));
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
        return omsPubApply;
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public Result insertPubApply(OmsPubApply omsPubApply) {
        if (StringUtils.isBlank(omsPubApply.getA0100())) {
            Result.error("请先选择申请的干部");
        }
        if (omsPubApply.getCgsj() != null) {
            String desc = selectExistsAbroad(omsPubApply.getProcpersonId(),omsPubApply.getId(), omsPubApply.getCgsj());

            if (!"".equals(desc))
                return Result.error(desc);
        }

        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        //返回信息
        String result = "";
        String primaryKey = UUIDGenerator.getPrimaryKey();
        if (StringUtils.isBlank(omsPubApply.getId())) {
            omsPubApply.setId(primaryKey);
            //创建人
            omsPubApply.setCreateUser(loginUser.getId());
            //创建时间
            omsPubApply.setCreateTime(new Date());
            //申请状态
            omsPubApply.setSqzt(Constants.emPrivateGoAbroad.生成材料.getIndex());//Constants.private_business[1]
            //是否下达(1-是,0-否)
            omsPubApply.setSfxd(1);
            //数据来源（0：填写，1：上传）
            omsPubApply.setSource("0");
            omsPubApplyMapper.insert(omsPubApply);
        } else {
            //更新
            omsPubApply.setModifyUser(loginUser.getId());
            omsPubApply.setModifyTime(new Date());
            omsPubApplyMapper.updateById(omsPubApply);
        }
        //判断校验类型
        //result = checkPersonApply(omsPubApply.getA0100(), primaryKey, "1");
        result = omsPubApply.getId();
        return Result.success(result);
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

    /**
     * 功能描述: <br>
     * 〈根据id查询申请记录〉
     *
     * @Param: [id]
     * @Return: com.hxoms.modules.publicity.entity.OmsPubApply
     * @Author: 李逍遥
     * @Date: 2020/7/28 10:30
     */
    @Override
    public OmsPubApplyVO selectPubApplyById(String id) {
        return omsPubApplyMapper.selectById(id);
    }

    /**
     * 功能描述: <br>
     * 〈根据id删除备案人员〉
     *
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/24 9:06
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void deletePubApplyById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("参数为空!");
        }

        omsPubApplyMapper.deletePubApplyById(id);
    }

    /**
     * 功能描述: <br>
     * 〈根据条件查询备案申请列表〉
     *
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
        //设置传入页码，以及每页的大小
        PageHelper.startPage(pageNum, pageSize);
        List<OmsPubApplyVO> list = omsPubApplyMapper.getPubAppListByCondition(omsPubApplyQueryParam);
        PageInfo info = new PageInfo(list);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈撤销通知书文号相同的备案申请〉
     *
     * @Param: [pwh, cxyy]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/1 9:02
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void repealAllPubApplyByPwh(String pwh, String cxyy) {
        if (StringUtils.isBlank(pwh) || StringUtils.isBlank(cxyy)) {
            throw new CustomMessageException("参数为空!");
        }
        //查询相关通知书文号的备案申请
        List<OmsPubApplyVO> list = omsPubApplyMapper.selectPubApplyListByPwh(pwh);
        if (list != null && list.size() > 0) {
            omsPubApplyMapper.repealAllPubApplyByPwh(pwh, cxyy, Constants.emPrivateGoAbroad.撤销.getIndex());//Constants.private_business[7]
        } else {
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
        if (StringUtils.isBlank(id) || StringUtils.isBlank(cxyy)) {
            throw new CustomMessageException("参数为空!");
        }
        OmsPubApply omsPubApply = omsPubApplyMapper.selectById(id);
        if (omsPubApply != null) {
            omsPubApplyMapper.repealPubApplyById(id, cxyy, Constants.emPrivateGoAbroad.撤销.getIndex());//Constants.private_business[7]
        }
    }

    /**
     * 功能描述: <br>
     * 〈保存台办变更后信息〉
     *
     * @Param: [omsPubApplyChange]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/3 11:46
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void insertPubApplyChange(OmsPubApplyChange omsPubApplyChange, List<String> ids) {
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        if (omsPubApplyChange == null) {
            throw new CustomMessageException("参数为空");
        }
        if (ids == null || ids.size() < 0) {
            throw new CustomMessageException("参数为空");
        }
        // 获取备案申请表
        for (String id : ids) {
            OmsPubApply omsPubApply = omsPubApplyMapper.selectById(id);
            Integer sqzt = omsPubApply.getSqzt();
            if (sqzt >= Constants.emPrivateGoAbroad.业务受理.getIndex()) {
                throw new CustomMessageException("该业务已经提交干部监督处，请先撤销再重新提交!");
            }
            //保存台办变更信息、
            omsPubApplyChange.setId(UUIDGenerator.getPrimaryKey());
            omsPubApplyChange.setModifyUser(loginUser.getId());
            omsPubApplyChange.setModifyTime(new Date());
            omsPubApplyChangeMapper.insertSelective(omsPubApplyChange);
            // 更新备案申请表
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
            // 是否变更
            omsPubApply.setSfbg("1");
            omsPubApplyMapper.updateById(omsPubApply);
        }
    }

    /**
     * 功能描述: <br>
     * 〈通过id获取变更前后信息〉
     *
     * @return
     * @Param: [pwh]
     * @Return: com.hxoms.modules.publicity.entity.OmsPubApplyChange
     * @Author: 李逍遥
     * @Date: 2020/7/6 10:08
     */
    @Override
    public List<OmsPubApplyChange> getPubApplyChange(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("参数为空!");
        }
        List<OmsPubApplyChange> omsPubApplyChange = omsPubApplyChangeMapper.selectByPrimaryPwh(id);
        return omsPubApplyChange;
    }

    /**
     * 功能描述: <br>
     * 〈添加干教因公出国备案申请〉
     *
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
        //返回信息
        String result = "";
        //预审批主键
        String id = omsPubGroupPreApproval.getId();
        if (StringUtils.isBlank(id)) {
            //新增预审批信息
            id = UUIDGenerator.getPrimaryKey();
            omsPubGroupPreApproval.setId(id);
            //备案主体
            omsPubGroupPreApproval.setBazt(Constants.PubGroupPreApproval_business[0]);
            //创建人
            omsPubGroupPreApproval.setCreateUser(loginUser.getId());
            // 创建时间
            omsPubGroupPreApproval.setCreateTime(new Date());
            omsPubGroupPreApproval.setSource("0");
            omsPubGroupPreApproval.setSqzt(Constants.GJ_business[0]);
            omsPubGroupPreApprovalMapper.insertSelective(omsPubGroupPreApproval);
        } else {
            //更新
            //查找原有预审批内容
            OmsPubGroupPreApprovalVO selectOmsPubGroupPreApproval = omsPubGroupPreApprovalMapper.selectByPrimaryKey(id);
            omsPubGroupPreApprovalMapper.updateByPrimaryKeySelective(omsPubGroupPreApproval);
            //新增预审批更改表信息
            OmsPubApplyChange omsPubApplyChange = new OmsPubApplyChange();
            //主键
            omsPubApplyChange.setId(UUIDGenerator.getPrimaryKey());
            //备案表主键
            omsPubApplyChange.setBaId(id);
            //原出国时间
            omsPubApplyChange.setYcgsj(selectOmsPubGroupPreApproval.getCgsj());
            //原回国时间
            omsPubApplyChange.setYhgsj(selectOmsPubGroupPreApproval.getHgsj());
            //原出访任务
            omsPubApplyChange.setYcfrw(selectOmsPubGroupPreApproval.getCfrw());
            //原出访事由
            omsPubApplyChange.setYcfsy(selectOmsPubGroupPreApproval.getCfsy());
            //原出访国
            omsPubApplyChange.setYsdgj(selectOmsPubGroupPreApproval.getSdgj());
            //原途径国家
            omsPubApplyChange.setYtjgj(selectOmsPubGroupPreApproval.getTjgj());
            //现出国时间
            omsPubApplyChange.setXcgsj(omsPubGroupPreApproval.getCgsj());
            //现回国时间
            omsPubApplyChange.setXhgsj(omsPubGroupPreApproval.getHgsj());
            //现出访任务
            omsPubApplyChange.setXcfrw(omsPubGroupPreApproval.getCfrw());
            //现出访事由
            omsPubApplyChange.setXcfsy(omsPubGroupPreApproval.getCfsy());
            //现出访国
            omsPubApplyChange.setXsdgj(omsPubGroupPreApproval.getSdgj());
            //现途径国
            omsPubApplyChange.setXtjgj(omsPubGroupPreApproval.getTjgj());
            //修改人
            omsPubApplyChange.setModifyUser(loginUser.getId());
            //修改时间
            omsPubApplyChange.setModifyTime(new Date());
            omsPubApplyChangeMapper.insertSelective(omsPubApplyChange);
        }
        //将预审批人员添加到备案申请
        List<PersonInfoVO> personInfoVOS = omsPubGroupPreApproval.getPersonInfoVOS();
        if (personInfoVOS != null && personInfoVOS.size() > 0) {
            for (PersonInfoVO personInfoVO : personInfoVOS) {
                OmsPubApply omsPubApply = new OmsPubApply();
                //备案表中获取基本信息
                Map<String, Object> personInfo = omsPubApplyMapper.selectBasePersonInfo(personInfoVO.getB0100(), personInfoVO.getA0100());
                //主键
                omsPubApply.setId(UUIDGenerator.getPrimaryKey());
                //A0100
                omsPubApply.setA0100(personInfoVO.getA0100());
                //B0100
                omsPubApply.setB0100(personInfoVO.getB0100());
                //预审批主键
                omsPubApply.setYspId(id);
                //预审批发起单位
                omsPubApply.setYspdwId(omsPubGroupPreApproval.getB0100());
                //年龄
                omsPubApply.setAge((String) personInfo.get("AGE"));
                //政治面貌
                omsPubApply.setPoliticalAff((String) personInfo.get("POLITICAL_AFFINAME"));
                //职务
                omsPubApply.setJob(personInfoVO.getJob());
                //健康状况
                omsPubApply.setHealth((String) personInfo.get("HEALTH"));
                //是否为涉密人员
                omsPubApply.setSfsmry("0");
                String smdj = (String) personInfo.get("SECRET_LEVEL");
                if (!StringUtils.isBlank(smdj) && !"0".equals(smdj)) {
                    omsPubApply.setSfsmry("1");
                    //涉密等级
                    omsPubApply.setSmdj((String) personInfo.get("SECRET_LEVEL"));
                }
                //组团单位
                omsPubApply.setZtdw(omsPubGroupPreApproval.getZtdw());
                //出国时间
                omsPubApply.setCgsj(omsPubGroupPreApproval.getCgsj());
                //回国时间
                omsPubApply.setHgsj(omsPubGroupPreApproval.getHgsj());
                //所到国境
                omsPubApply.setSdgj(omsPubGroupPreApproval.getSdgj());
                //在外停留时间
                omsPubApply.setTlsj(String.valueOf(omsPubGroupPreApproval.getZwtlsj()));
                //出访任务
                omsPubApply.setCfrw(omsPubGroupPreApproval.getCfrw());
                //出访事由
                omsPubApply.setCfsy(omsPubGroupPreApproval.getCfsy());
                //是否下达
                omsPubApply.setSfxd(Constants.pub_business[0]);
                //申请状态
                //omsPubApply.setSqzt(Constants.leader_business[0]);
                //创建人
                omsPubApply.setCreateUser(loginUser.getId());
                //创建时间
                omsPubApply.setCreateTime(new Date());
                omsPubApplyMapper.insert(omsPubApply);
                //判断校验类型
                //result = checkPersonApply(omsPubApply.getA0100(), omsPubApply.getId(), "1");
                if (StringUtils.isBlank(result)) {
                    result = "保存成功";
                }
            }
        } else {
            throw new CustomMessageException("参数为空!");
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
        //设置传入页码，以及每页的大小
        PageHelper.startPage(pageNum, pageSize);
        /** 团组名称*/
        String tzmc = omsPubApplyQueryParam.getTzmc();
        /**申请状态集合 */
        List<Integer> status = omsPubApplyQueryParam.getStatus();
        /**组团单位*/
        String ztdw = omsPubApplyQueryParam.getZtdw();
        if (StringUtils.isBlank(ztdw)) {
            ztdw = "省干部教育处";
        }
        /** 出国时间*/
        Date cgsj = omsPubApplyQueryParam.getCgsj();
        /** 回国时间*/
        Date hgsj = omsPubApplyQueryParam.getHgsj();
        List<OmsPubGroupPreApprovalVO> OmsPubGroupPreApproval = omsPubGroupPreApprovalMapper.selectByCondition(tzmc, status, ztdw, cgsj, hgsj);
        if (OmsPubGroupPreApproval != null && OmsPubGroupPreApproval.size() > 0) {
            for (OmsPubGroupPreApprovalVO omsPubGroupPreApproval : OmsPubGroupPreApproval) {
                String id = omsPubGroupPreApproval.getId();
                List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.selectByYSPId(id);
                omsPubGroupPreApproval.setOmsPubApplyVOS(omsPubApplyVOS);
            }
        }
        PageInfo info = new PageInfo(OmsPubGroupPreApproval);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈根据ID查看干教信息〉
     *
     * @Param: [id]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/7 17:48
     */
    @Override
    public OmsPubGroupPreApprovalVO getPubGroupPreApprovalById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("参数为空!");
        }
        OmsPubGroupPreApprovalVO omsPubGroupPreApproval = omsPubGroupPreApprovalMapper.selectByPrimaryKey(id);
        if (omsPubGroupPreApproval != null) {
            List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.selectByYSPId(id);
            omsPubGroupPreApproval.setOmsPubApplyVOS(omsPubApplyVOS);
        } else {
            throw new CustomMessageException("数据为空!");
        }
        return omsPubGroupPreApproval;
    }

    /**
     * 功能描述: <br>
     * 〈获取负面信息、家庭主要成员、单位是否接收巡视等信息〉
     *
     * @Param: [b0100, a0100, cgsj]
     * @Return: com.hxoms.modules.publicity.entity.OtherPubApply
     * @Author: 李逍遥
     * @Date: 2020/7/21 9:42
     */
    @Override
    public OtherPubApply getOtherPubApply(String b0100, String a0100, Date cgsj) {
        OtherPubApply otherPubApply = new OtherPubApply();
        //获取涉密信息
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("a0100", a0100);
        paramMap.put("finishDate", "1");
        List<OmsSmrOldInfoVO> omsSmrOldInfoVOS = omsSmrOldInfoMapper.getSmrOldInfoVOList(paramMap);
        otherPubApply.setOmsSmrOldInfoVOS(omsSmrOldInfoVOS);
        //获取负面信息
        String result = omsConditionService.selectNegativeInfo(a0100, cgsj);
        otherPubApply.setFmxx(result);
        //单位接收巡视
        boolean patrolUnit = omsSupPatrolUnitService.getPatrolUnit(b0100, cgsj);
        otherPubApply.setPatrolUnit(patrolUnit);
        //主要家庭成员信息
        List<A36> a36s = a36Mapper.selectFamilyMember(a0100);
        otherPubApply.setA36List(a36s);
        return otherPubApply;
    }

    /**
     * 功能描述: <br>
     * 〈因公备案申请步骤统计〉
     *
     * @Param: [orgId]
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.CountStatusResult>
     * @Author: 李逍遥
     * @Date: 2020/7/27 15:04
     */
    @Override
    public List<CountStatusResult> selectPubCountStatus() {
        List<CountStatusResult> countStatusResults = omsPubApplyMapper.selectPubCountStatus();
        return countStatusResults;
    }

    /**
     * 功能描述: <br>
     * 〈因公下一步操作根据id更改状态〉
     *
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/28 9:48
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void updateSQZTById(String id, String currentStep) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("参数为空!");
        }
        OmsPubApply omsPubApply = omsPubApplyMapper.selectById(id);
        if (omsPubApply == null) {
            throw new CustomMessageException("申请记录为空");
        }
        Integer sqzt = omsPubApply.getSqzt();
        Integer cStep = Integer.parseInt(currentStep);
        if (cStep != sqzt) return;

        if (sqzt == Constants.emPublicGoAbroad.预备案填写中.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.草稿.getIndex();
        } else if (sqzt == Constants.emPublicGoAbroad.草稿.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.生成材料.getIndex();
        } else if (sqzt == Constants.emPublicGoAbroad.生成材料.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.打印材料清单.getIndex();
        } else if (sqzt == Constants.emPublicGoAbroad.打印材料清单.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.自评.getIndex();
        } else if (sqzt == Constants.emPublicGoAbroad.自评.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.业务受理.getIndex();
        } else if (sqzt == Constants.emPublicGoAbroad.业务受理.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.征求意见.getIndex();
        }
        if (sqzt == Constants.emPublicGoAbroad.征求意见.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.记录意见.getIndex();
        }
        if (sqzt == Constants.emPublicGoAbroad.记录意见.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.做出审核意见.getIndex();
        }
        if (sqzt == Constants.emPublicGoAbroad.做出审核意见.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.处领导审批.getIndex();
        }
        if (sqzt == Constants.emPublicGoAbroad.处领导审批.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.部领导审批.getIndex();
        }
        if (sqzt == Constants.emPublicGoAbroad.部领导审批.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.核实批件.getIndex();
        }
        if (sqzt == Constants.emPublicGoAbroad.核实批件.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.制作备案表.getIndex();
        }
        if (sqzt == Constants.emPublicGoAbroad.制作备案表.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.已办结.getIndex();
        }
        if (sqzt == Constants.emPublicGoAbroad.已办结.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.待领证.getIndex();
        }
        if (sqzt == Constants.emPublicGoAbroad.待领证.getIndex()) {
            sqzt = Constants.emPublicGoAbroad.已领证.getIndex();
        }
        omsPubApply.setSqzt(sqzt);
        omsPubApplyMapper.updateById(omsPubApply);

        omsPriApplyService.WriteApprovalStep(id,cStep,Constants.emPublicGoAbroad.getNameByIndex(cStep),
                "1","通过",Constants.oms_business[0]);
    }

    /**
     * 功能描述: <br>
     * 〈台办获取相同批文号的人员〉
     *
     * @Param: [pwh]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubApplyVO>
     * @Author: 李逍遥
     * @Date: 2020/7/28 17:31
     */
    @Override
    public List<OmsPubApplyVO> getPubApplyByPwh(String pwh) {
        if (StringUtils.isBlank(pwh)) {
            throw new CustomMessageException("参数为空!");
        }
        List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.selectPubApplyListByPwh(pwh);
        return omsPubApplyVOS;
    }

    /**
     * 功能描述: <br>
     * 〈因公出国境申请列表导出〉
     *
     * @Param: [omsPubApplyQueryParam, response]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/8/3 9:57
     */
    @Override
    public void exportPubApply(OmsPubApplyQueryParam omsPubApplyQueryParam, HttpServletResponse response) {
        List<OmsPubApplyVO> list = omsPubApplyMapper.getPubAppListByCondition(omsPubApplyQueryParam);
        if (list == null || list.size() < 1) {
            throw new CustomMessageException("操作失败");
        } else {
            /** 开始导出 */
            //创建HSSFWorkbook对象(excel的文档对象)
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建文件样式对象
            HSSFCellStyle style = wb.createCellStyle();
            //获得字体对象
            HSSFFont font = wb.createFont();
            //建立新的sheet对象（excel的表单）
            HSSFSheet sheet = wb.createSheet("因公出国境备案申请名单");
            //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row1 = sheet.createRow(0);
            //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            HSSFCell cell = row1.createCell(0);
            //设置标题字体大小
            font.setFontHeightInPoints((short) 16);
            //加粗
            font.setBold(true);
            // 左右居中 
            style.setAlignment(HorizontalAlignment.CENTER);
            // 上下居中 
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFont(font);
            cell.setCellStyle(style);
            //设置标题单元格内容
            cell.setCellValue("因公出国境备案申请名单");
            //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 19));
            //在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(2);
            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("序号");
            row2.createCell(1).setCellValue("姓名");
            row2.createCell(2).setCellValue("出境日期");
            row2.createCell(3).setCellValue("入境日期");
            row2.createCell(4).setCellValue("目的地");
            row2.createCell(5).setCellValue("出访任务");
            row2.createCell(6).setCellValue("审批单位");
            row2.createCell(7).setCellValue("性别");
            row2.createCell(8).setCellValue("出生日期");
            row2.createCell(9).setCellValue("政治面貌");
            row2.createCell(10).setCellValue("职务");
            row2.createCell(11).setCellValue("申请日期");
            row2.createCell(12).setCellValue("是否裸官");
            row2.createCell(13).setCellValue("涉密等级");
            row2.createCell(14).setCellValue("是否主要领导");
            row2.createCell(15).setCellValue("负面信息");
            row2.createCell(16).setCellValue("是否变更");
            row2.createCell(17).setCellValue("审批单位下达");
            row2.createCell(18).setCellValue("审批状态");
            row2.createCell(19).setCellValue("最终结论");
            //在sheet里添加数据
            //创建文件样式对象
            HSSFCellStyle style1 = wb.createCellStyle();
            //获得字体对象
            HSSFFont font1 = wb.createFont();
            //设置单元格字体大小
            font1.setFontHeightInPoints((short) 12);
            //居左
            style1.setAlignment(HorizontalAlignment.LEFT);
            style1.setFont(font1);
            HSSFRow row = null;
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 3);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(list.get(i).getName());
                row.createCell(2).setCellValue(UtilDateTime.toDateString(list.get(i).getCgsj()));
                row.createCell(3).setCellValue(UtilDateTime.toDateString(list.get(i).getHgsj()));
                row.createCell(4).setCellValue(list.get(i).getSdgj());
                row.createCell(5).setCellValue(list.get(i).getCfrw());
                row.createCell(6).setCellValue(list.get(i).getCgspdw());
                String sex = list.get(i).getSex();
                if ("1".equals(sex)) {
                    row.createCell(7).setCellValue("男");
                } else if ("2".equals(sex)) {
                    row.createCell(7).setCellValue("女");
                } else {
                    row.createCell(7).setCellValue(sex);
                }
                row.createCell(8).setCellValue(UtilDateTime.toDateString(list.get(i).getBirthDate()));
                row.createCell(9).setCellValue(list.get(i).getPoliticalAff());
                row.createCell(10).setCellValue(list.get(i).getJob());
                row.createCell(11).setCellValue(UtilDateTime.toDateString(list.get(i).getCreateTime()));
                if ("1".equals(list.get(i).getSflg())) {
                    row.createCell(12).setCellValue("是");
                } else {
                    row.createCell(12).setCellValue("否");
                }
                row.createCell(13).setCellValue(list.get(i).getSmdj());
                if ("1".equals(list.get(i).getSfzyld())) {
                    row.createCell(14).setCellValue("是");
                } else {
                    row.createCell(14).setCellValue("否");
                }
                row.createCell(15).setCellValue(list.get(i).getFmxx());
                String sfbg = list.get(i).getSfbg();
                if ("1".equals(list.get(i).getSfbg())) {
                    row.createCell(16).setCellValue("是");
                } else {
                    row.createCell(16).setCellValue("否");
                }
                Integer sfxd = list.get(i).getSfxd();
                if (1 == list.get(i).getSfxd()) {
                    row.createCell(17).setCellValue("是");
                } else {
                    row.createCell(17).setCellValue("否");
                }
                Integer sqzt = list.get(i).getSqzt();
                row.createCell(18).setCellValue(Constants.emPrivateGoAbroad.getNameByIndex(sqzt));

                row.createCell(19).setCellValue(list.get(i).getZzjl());
                //设置单元格字体大小
                for (int j = 0; j < 19; j++) {
                    row.getCell(j).setCellStyle(style1);
                }
            }
            //输出Excel文件
            OutputStream output = null;
            try {
                output = response.getOutputStream();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "utf-8");
                wb.write(output);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 功能描述: <br>
     * 〈更改时通过批文号模糊查询添加人员〉
     *
     * @Param: [pwh]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubApplyVO>
     * @Author: 李逍遥
     * @Date: 2020/8/3 16:48
     */
    @Override
    public List<OmsPubApplyVO> getPubApplyList() {
        //要把申请赴台了且未到出国日期的未撤销的全部人员查一下
        String pwh = "琼台赴";
        //原来取的是private_bussiness[7]
        List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.getPubApplyList(pwh, Constants.emPrivateGoAbroad.撤销.getIndex());
        return omsPubApplyVOS;
    }

    /**
     * 功能描述: <br>
     * 〈上报干部监督处〉
     *
     * @param ids
     * @Param: [ids]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/8/7 9:55
     */
    @Override
    public void reportPubGroupPreApproval(List<String> ids) {
        if (ids == null || ids.size() < 1) {
            throw new CustomMessageException("参数为空!");
        }
        for (String id : ids) {
            OmsPubGroupPreApprovalVO omsPubGroupPreApprovalVO = omsPubGroupPreApprovalMapper.selectByPrimaryKey(id);
            omsPubGroupPreApprovalVO.setSqzt(Constants.GJ_business[1]);
            List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.selectByYSPId(id);
            for (OmsPubApplyVO omsPubApplyVO : omsPubApplyVOS) {
                omsPubApplyVO.setSqzt(Constants.emPrivateGoAbroad.业务受理.getIndex());//Constants.leader_business[0]
                omsPubApplyMapper.updateById(omsPubApplyVO);
            }
        }
    }

    /**
     * 功能描述: <br>
     * 〈撤销整个干教申请〉
     *
     * @Param: [id, cxyy]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/8/10 10:09
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void repealGJ(String id, String cxyy) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(cxyy)) {
            throw new CustomMessageException("参数为空!");
        }
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        OmsPubGroupPreApprovalVO omsPubGroupPreApprovalVO = omsPubGroupPreApprovalMapper.selectByPrimaryKey(id);
        omsPubGroupPreApprovalVO.setSqzt(Constants.GJ_business[3]);
        //更新预审批表状态为撤销
        omsPubGroupPreApprovalMapper.updateByPrimaryKeySelective(omsPubGroupPreApprovalVO);
        //更新该申请下的所有人员状态为撤销
        List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.selectByYSPId(id);
        for (OmsPubApplyVO omsPubApplyVO : omsPubApplyVOS) {
            omsPubApplyVO.setSqzt(Constants.emPrivateGoAbroad.撤销.getIndex());//Constants.private_business[7]
            omsPubApplyVO.setCxyy(cxyy);
            omsPubApplyVO.setModifyUser(loginUser.getId());
            omsPubApplyVO.setModifyTime(new Date());
            omsPubApplyMapper.updateById(omsPubApplyVO);
        }
    }

    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void updatePWH(String yPWH, String xPWH) {
        if (StringUtils.isBlank(yPWH)) {
            throw new CustomMessageException("原通知书文号为空!");
        }
        if (StringUtils.isBlank(xPWH)) {
            throw new CustomMessageException("现通知书文号为空!");
        }
        omsPubApplyMapper.updatePWH(yPWH, xPWH);
    }

    /**
     * 功能描述: <br>
     * 〈获取台办批文号树〉
     *
     * @Param: []
     * @Return: com.hxoms.modules.publicity.entity.PWHVO
     * @Author: 李逍遥
     * @Date: 2020/8/31 10:43
     */
    @Override
    public List<PWHTreeVO> getPWHList() {
        List<PWHTreeVO> PWHTreeVOS = omsPubApplyMapper.getPWHList();
        List<PWHTreeVO> pwhvos1 = new ArrayList<>();
        for (PWHTreeVO PWHTreeVO : PWHTreeVOS) {
            String year = PWHTreeVO.getYear();
            PWHTreeVO PWHTreeVO2 = new PWHTreeVO();
            List<PWHVO> pwhs = new ArrayList<>();
            for (PWHTreeVO PWHTreeVO1 : PWHTreeVOS) {
                if (year.equals(PWHTreeVO1.getYear())) {
                    PWHTreeVO2.setYear(year);
                    PWHVO pwhvo = new PWHVO();
                    pwhvo.setPwh(PWHTreeVO1.getPwh());
                    pwhs.add(pwhvo);
                }
            }
            PWHTreeVO2.setPwhs(pwhs);
            pwhvos1.add(PWHTreeVO2);
        }
        return pwhvos1;
    }

    @Override
    public String selectExistsAbroad(String id,String appId, Date cgsj) {
        List<OmsPubApply> pubApplies = omsPubApplyMapper.selectExistsAbroad(id,
                new SimpleDateFormat("yyyy-MM-dd").format(cgsj));
        String desc = "";
        if (pubApplies.size() > 0) {
            for (OmsPubApply pubApply : pubApplies
            ) {
                if (appId != null && pubApply.getId().equals(appId)) continue;
                desc += "当前申请的因公出国（境）时间段内已经存在" + pubApply.getBz() + "出国（境）申请！";
            }

        }
        return (desc);
    }

    /**
     * 功能描述: <br>
     * 〈根据ID删除干教信息〉
     *
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/7 17:36
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void deletePubGroupPreApprovalById(String id) {
        if (StringUtils.isBlank(id)) {
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
