package com.hxoms.modules.omsoperator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsoperator.entity.*;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorApprovalMapper;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorHandoverMapper;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorHandoverSubformMapper;
import com.hxoms.modules.omsoperator.service.OmsOperatorService;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.publicity.entity.OmsPubApplyVO;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.sysUser.entity.CfUser;
import com.hxoms.modules.sysUser.mapper.CfUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 功能描述: <br>
 * 〈经办人管理〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/7/16 20:07
 */
@Service
public class OmsOperatorServiceImpl implements OmsOperatorService {

    @Autowired
    private CfUserMapper cfUserMapper;
    @Autowired
    private OmsOperatorApprovalMapper operatorApprovalMapper;
    @Autowired
    private OmsOperatorHandoverMapper operatorHandoverMapper;
    @Autowired
    private OmsPubApplyMapper omsPubApplyMapper;
    @Autowired
    private OmsOperatorHandoverMapper omsOperatorHandoverMapper;
    @Autowired
    private OmsOperatorHandoverSubformMapper omsOperatorHandoverSubformMapper;
    /**
     * 功能描述: <br>
     * 〈保存经办人信息〉
     * @Param: [user]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/6 15:32
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String saveOperator(CfUser user) {
        //1、判断有没有选择经办人
        if (user == null) {
            throw new CustomMessageException("请先选择经办人!");
        }
        //提示信息
        String msc;
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        if (StringUilt.isStrOrnull(user.getUserId())) {
            //更新
            //修改人
            user.setModifyUser(loginUser.getId());
            //修改时间
            user.setModifyTime(new Date());
            cfUserMapper.updateByPrimaryKeySelective(user);
            msc="经办人未上报干部监督处，到经办人管理页面修改或上报!";
            return msc;
        } else {
            //新增经办人
            verify(user);
            //做保存业务，点击【保存】，校验通过后，将状态置为“注册”，提醒系统管理员：“经办人未上报干部监督处，到经办人管理页面修改或上报”。
            //设置ID
            user.setUserId(UUIDGenerator.getPrimaryKey());
            //设置初始密码
            user.setUserPassword(UUIDGenerator.encryptPwd(Constants.USER_PWD));
            //设置用户类型
            user.setUserType(Constants.USER_TYPES[6]);
            //设置用户状态
            user.setUserState(Constants.USER_STATUS[0]);
            //创建人
            user.setCreator(loginUser.getId());
            //创建时间
            user.setCreatetime(new Date());
            cfUserMapper.insertSelective(user);
            msc="经办人未上报干部监督处，到经办人管理页面修改或上报!";
            return msc;
        }
    }

    /**
     * 功能描述: <br>
     * 〈保存并上传经办人〉
     * @Param: [user]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/7 10:30
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String saveAndUploadOperator(CfUser user) {
        //1、判断有没有选择经办人
        if (user == null) {
            throw new CustomMessageException("请先选择经办人!");
        }
        String msc ="";
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        //创建审批对象
        OmsOperatorApproval  omsOperatorApproval = new OmsOperatorApproval();
        if (StringUilt.isStrOrnull(user.getUserId())) {
            //更新
            //修改人
            user.setModifyUser(loginUser.getId());
            //修改时间
            user.setModifyTime(new Date());
            //将状态置为"监督处审核"
            user.setUserState(Constants.USER_STATUS[3]);
            //更新经办人信息
            cfUserMapper.updateByPrimaryKeySelective(user);
        }else {
            //新增
            //校验
            verify(user);
            //设置ID
            user.setUserId(UUIDGenerator.getPrimaryKey());
            //设置初始密码
            user.setUserPassword(UUIDGenerator.encryptPwd(Constants.USER_PWD));
            //设置用户类型
            user.setUserType(Constants.USER_TYPES[6]);
            //将状态置为"监督处审核"
            user.setUserState(Constants.USER_STATUS[3]);
            //创建人
            user.setCreator(loginUser.getId());
            //创建时间
            user.setCreatetime(new Date());
            //新增经办人信息
            cfUserMapper.insertSelective(user);
        }
        //经办人审批表主键、
        omsOperatorApproval.setId(UUIDGenerator.getPrimaryKey());
        // 经办人主键、
        omsOperatorApproval.setOperatorid(user.getUserId());
        //步骤名称（1.监督处审核 2.处领导审批）、
        omsOperatorApproval.setStepname("监督处审核");
        // 提交时间
        omsOperatorApproval.setSubmissiontime(new Date());
        // 提交人
        omsOperatorApproval.setSubmitter(loginUser.getId());
        //新增经办人审批信息
        operatorApprovalMapper.insertSelective(omsOperatorApproval);
        return "请通知经办人持单位审批后的申请表及身份证到干部监督处办理审批手续";
    }

    /**
     * 功能描述: <br>
     * 〈根据UserID删除经办人〉
     * @Param: [userId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/7 19:48
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void deleteOperator(String userId) {
        if (StringUtils.isEmpty(userId)){
            throw new CustomMessageException("参数为空!");
        }
        cfUserMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 功能描述: <br>
     * 〈撤销经办人〉
     * @Param: [operatorId,handoverId]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/8 14:41
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String revokeOperator(String operatorId,String handoverId) {
        String msc = "";
        if (operatorId == null || "".equals(operatorId)) {
            throw new CustomMessageException("请先选择经办人!");
        }
        if (handoverId == null || "".equals(handoverId)){
            throw new CustomMessageException("接手人不能为空!");
        }
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        //查找原经办人
        CfUser user = cfUserMapper.selectByPrimaryKey(operatorId);
        String userState = user.getUserState();
        //用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)
        if (userState.equals(Constants.USER_STATUS[1])){
            //状态为正常时操作显示：【撤消】、【审批信息】，将状态置为“待撤消”，系统通知经办人：“请到经办人交接页面办理交接手续”
           cfUserMapper.updateUserState(user.getUserId(),Constants.USER_STATUS[6]);
            msc ="请到经办人交接页面办理交接手续!";
        } else if (userState.equals(Constants.USER_STATUS[3])){
            //状态为征求意见时操作显示：【撤消】，此操作直接将状态置为“撤消”，通知干部监督处：“经办人XXX的申请已被XXX单位撤消”
            cfUserMapper.updateUserState(user.getUserId(),Constants.USER_STATUS[2]);
            //需要当前操作人ID(未设置)
            msc = "经办人"+user.getUserName()+"的申请已被"+loginUser.getOrgName()+"单位撤消!";
        }else if (userState.equals(Constants.USER_STATUS[4])){
            //状态为待审批时操作显示：【撤消】、【打印申请表】，此操作直接将状态置为“撤消”，通知干部监督处：“经办人XXX的申请已被XXX单位撤消”
            cfUserMapper.updateUserState(user.getUserId(),Constants.USER_STATUS[2]);
            //需要当前操作人ID(未设置)
            msc = "经办人"+user.getUserName()+"的申请已被"+loginUser.getOrgName()+"单位撤消!";
        }
        //查询该经办人未完成的业务(往交接主表插入数据、查出数据后插入交接子表)
        /**证照*/
        //TODO
        /**因公出国境*/
        List<OmsPubApplyVO> omsPubApplyVOS = omsPubApplyMapper.selectPubAllyByStatusAndName(user.getUserId(), Constants.leader_business[8]);
        if (omsPubApplyVOS != null && omsPubApplyVOS.size()>0){
            for (OmsPubApplyVO omsPubApplyVo:omsPubApplyVOS) {
                //往交接主表插入数据、
                String primaryKey = UUIDGenerator.getPrimaryKey();
                Date date = new Date();
                OmsOperatorHandover omsOperatorHandover = new OmsOperatorHandover();
                //主键
                omsOperatorHandover.setId(primaryKey);
                //接手人ID
                omsOperatorHandover.setHandoverid(handoverId);
                //经办人主键
                omsOperatorHandover.setOperatorid(operatorId);
                //交接时间
                omsOperatorHandover.setHandovertime(date);
                //交接状态
                omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[0]));
                omsOperatorHandoverMapper.insertSelective(omsOperatorHandover);
                // 查出数据后插入交接子表
                OmsOperatorHandoverSubform omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                //交接子表主键
                omsOperatorHandoverSubform.setId(primaryKey);
                //交接表主表主键
                omsOperatorHandoverSubform.setHandoverformid(primaryKey);
                //业务主键
                omsOperatorHandoverSubform.setBusinessid(omsPubApplyVo.getId());
                //业务类别
                omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[1]);
                //人员主键
                omsOperatorHandoverSubform.setA0100(omsPubApplyVo.getA0100());
                //姓名
                omsOperatorHandoverSubform.setName(omsPubApplyVo.getName());
                //性别
                omsOperatorHandoverSubform.setSex(omsPubApplyVo.getSex());
                //出生日期
                omsOperatorHandoverSubform.setBirthday(omsPubApplyVo.getBirthDate());
                //身份证号
                omsOperatorHandoverSubform.setIdcard(omsPubApplyVo.getIdnumber());
                //政治面貌
                omsOperatorHandoverSubform.setPoliticsstatus(omsPubApplyVo.getPoliticalAff());
                //工作单位
                omsOperatorHandoverSubform.setCompany(omsPubApplyVo.getB0101());
                //职务（级）
                omsOperatorHandoverSubform.setDuty(omsPubApplyVo.getJob());
                //交接时间
                omsOperatorHandoverSubform.setHandovertime(date);
                //接手人ID
                omsOperatorHandoverSubform.setHandoverid(handoverId);
                //出国（境）时间
                omsOperatorHandoverSubform.setExitdate(omsPubApplyVo.getCgsj());
                //入境时间
                omsOperatorHandoverSubform.setEntrydate(omsPubApplyVo.getHgsj());
                //说明
                omsOperatorHandoverSubform.setExplain(omsPubApplyVo.getCfrw());
                omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
            }
        }
        /**因私出国境*/
        List<OmsPriApplyVO> omsPriApplyVOS = operatorHandoverMapper.selectOmsPriApplyByStatusAndName(user.getUserId(), Constants.leader_business[8]);
        if (omsPriApplyVOS != null && omsPriApplyVOS.size()>0){
            for (OmsPriApplyVO omsPriApplyVO:omsPriApplyVOS) {
                //往交接主表插入数据、
                String primaryKey = UUIDGenerator.getPrimaryKey();
                Date date = new Date();
                OmsOperatorHandover omsOperatorHandover = new OmsOperatorHandover();
                //主键
                omsOperatorHandover.setId(primaryKey);
                //接手人ID
                omsOperatorHandover.setHandoverid(handoverId);
                //经办人主键
                omsOperatorHandover.setOperatorid(operatorId);
                //交接时间
                omsOperatorHandover.setHandovertime(date);
                //交接状态
                omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[0]));
                omsOperatorHandoverMapper.insertSelective(omsOperatorHandover);
                // 查出数据后插入交接子表
                OmsOperatorHandoverSubform omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                //交接子表主键
                omsOperatorHandoverSubform.setId(primaryKey);
                //交接表主表主键
                omsOperatorHandoverSubform.setHandoverformid(primaryKey);
                //业务主键
                omsOperatorHandoverSubform.setBusinessid(omsPriApplyVO.getId());
                //业务类别
                omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[2]);
                //人员主键
                omsOperatorHandoverSubform.setA0100(omsPriApplyVO.getA0100());
                //姓名
                omsOperatorHandoverSubform.setName(omsPriApplyVO.getName());
                //性别
                omsOperatorHandoverSubform.setSex(omsPriApplyVO.getSex());
                //出生日期
                omsOperatorHandoverSubform.setBirthday(omsPriApplyVO.getBirthDate());
                //身份证号
                omsOperatorHandoverSubform.setIdcard(omsPriApplyVO.getIdnumber());
                //政治面貌
                omsOperatorHandoverSubform.setPoliticsstatus(omsPriApplyVO.getPoliticalOutlook());
                //工作单位
                omsOperatorHandoverSubform.setCompany(omsPriApplyVO.getB0101());
                //职务（级）
                omsOperatorHandoverSubform.setDuty(omsPriApplyVO.getPostrank());
                //交接时间
                omsOperatorHandoverSubform.setHandovertime(date);
                //接手人ID
                omsOperatorHandoverSubform.setHandoverid(handoverId);
                //出国（境）时间
                omsOperatorHandoverSubform.setExitdate(omsPriApplyVO.getAbroadTime());
                //入境时间
                omsOperatorHandoverSubform.setEntrydate(omsPriApplyVO.getReturnTime());
                //说明
                omsOperatorHandoverSubform.setExplain(omsPriApplyVO.getAbroadReasons());
                omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
            }
        }
        /**延期回国*/
        List<OmsPriDelayVO> omsPriDelayVOS = operatorHandoverMapper.selectOmsPriDelayApplyByStatusAndName(user.getUserId(),Constants.leader_business[8]);
        if (omsPriDelayVOS != null && omsPriDelayVOS.size()>0){
            for (OmsPriDelayVO omsPriDelayVO:omsPriDelayVOS) {
                //往交接主表插入数据、
                String primaryKey = UUIDGenerator.getPrimaryKey();
                Date date = new Date();
                OmsOperatorHandover omsOperatorHandover = new OmsOperatorHandover();
                //主键
                omsOperatorHandover.setId(primaryKey);
                //接手人ID
                omsOperatorHandover.setHandoverid(handoverId);
                //经办人主键
                omsOperatorHandover.setOperatorid(operatorId);
                //交接时间
                omsOperatorHandover.setHandovertime(date);
                //交接状态
                omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[0]));
                omsOperatorHandoverMapper.insertSelective(omsOperatorHandover);
                // 查出数据后插入交接子表
                OmsOperatorHandoverSubform omsOperatorHandoverSubform = new OmsOperatorHandoverSubform();
                //交接子表主键
                omsOperatorHandoverSubform.setId(primaryKey);
                //交接表主表主键
                omsOperatorHandoverSubform.setHandoverformid(primaryKey);
                //业务主键
                omsOperatorHandoverSubform.setBusinessid(omsPriDelayVO.getId());
                //业务类别
                omsOperatorHandoverSubform.setBusinesstype(Constants.handover_type[3]);
                //人员主键
                omsOperatorHandoverSubform.setA0100(omsPriDelayVO.getA0100());
                //姓名
                omsOperatorHandoverSubform.setName(omsPriDelayVO.getName());
                //性别
                omsOperatorHandoverSubform.setSex(omsPriDelayVO.getSex());
                //身份证号
                omsOperatorHandoverSubform.setIdcard(omsPriDelayVO.getIdnumber());
                //工作单位
                omsOperatorHandoverSubform.setCompany(omsPriDelayVO.getB0101());
                //职务（级）
                omsOperatorHandoverSubform.setDuty(omsPriDelayVO.getPostrank());
                //交接时间
                omsOperatorHandoverSubform.setHandovertime(date);
                //接手人ID
                omsOperatorHandoverSubform.setHandoverid(handoverId);
                //出国（境）时间
                omsOperatorHandoverSubform.setExitdate(omsPriDelayVO.getApplyTime());
                //入境时间
                omsOperatorHandoverSubform.setEntrydate(omsPriDelayVO.getEstimateReturntime());
                //说明
                omsOperatorHandoverSubform.setExplain(omsPriDelayVO.getDelayReason());
                omsOperatorHandoverSubformMapper.insertSelective(omsOperatorHandoverSubform);
            }
        }
        /**撤销登记备案*/
        //TODO
        return msc;
    }

    /**
     * 功能描述: <br>
     * 〈经办人审批信息查询〉
     * @Param: [userId]
     * @Return: com.hxoms.modules.omsoperator.entity.OmsOperatorApproval
     * @Author: 李逍遥
     * @Date: 2020/5/11 9:16
     */
    @Override
    public OmsOperatorApproval getOperatorApprovalByUid(String userId) {
        if (userId == null || "".equals(userId)) {
            throw new CustomMessageException("参数为空！");
        }
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(userId);
        return approval;
    }

    /**
     * 功能描述: <br>
     * 〈查询经办人交接记录〉
     * @Param: [userId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 李逍遥
     * @Date: 2020/5/11 11:44
     */
    @Override
    public List<OmsOperatorHandoverSubformVO>  getOperatorHandoverByUid(String userId) {
        if (userId == null || "".equals(userId)) {
            throw new CustomMessageException("参数为空！");
        }
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS  = operatorHandoverMapper.selectByUserId(userId);
        return omsOperatorHandoverSubformVOS;
    }

   /**
    * 功能描述: <br>
    * 〈获取经办人列表〉
    * @Param: [pageNum, pageSize, keyWord, orgId, state]
    * @Return: com.github.pagehelper.PageInfo
    * @Author: 李逍遥
    * @Date: 2020/7/8 15:30
    */
    @Override
    public PageInfo getAllOperator(Integer pageNum, Integer pageSize, String keyWord,List<String> state) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        /** 设置传入页码，以及每页的大小  */
        PageHelper.startPage(pageNum, pageSize);
        List<CfUser> users = cfUserMapper.getOperatorApprovalList(keyWord,null,Constants.USER_TYPES[6],state);
        PageInfo info = new PageInfo(users);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈经办人名单页面——获取经办人列表〉
     * @Param: [pageNum, pageSize, orgId, keyWord, state]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/14 16:11
     */
    @Override
    public PageInfo getOperatorList(Integer pageNum, Integer pageSize, List<String> orgId, String keyWord, List<String> state) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //获取当前登录人
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        if (orgId == null || orgId.size() <= 0){
            orgId.add(loginUser.getOrgId());
        }
        /**  设置传入页码，以及每页的大小  */
        PageHelper.startPage(pageNum, pageSize);
        List<CfUser> users = cfUserMapper.getOperatorApprovalList(keyWord,orgId,Constants.USER_TYPES[6],state);
        PageInfo info = new PageInfo(users);
        return info;
    }
    /**
     * 功能描述: <br>
     * 〈管理人员确定列表〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO>
     * @Author: 李逍遥
     * @Date: 2020/7/13 15:24
     */
    @Override
    public List<OmsOperatorHandoverSubformVO> getAllOperatorHandover() {
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        //通过机构id查询
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS = operatorHandoverMapper.getAllOperatorHandoverByOrgid(loginUser.getOrgId());
        return omsOperatorHandoverSubformVOS;
    }

    /**
     * 功能描述: <br>
     * 〈确认交接完成〉
     * @Param: [handoverformid]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/13 17:43
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void confirmDelivery(String handoverformid) {
        if (handoverformid == null || "".equals(handoverformid)){
            throw new CustomMessageException("参数为空!");
        }
        //获取当前登录人
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        OmsOperatorHandover omsOperatorHandover = operatorHandoverMapper.selectByPrimaryKey(handoverformid);
        //更改状态为完成
        omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[1]));
        operatorHandoverMapper.updateByPrimaryKeySelective(omsOperatorHandover);
        //更改经办人状态为撤销
        CfUser user = cfUserMapper.selectByPrimaryKey(omsOperatorHandover.getOperatorid());
        user.setUserState(Constants.USER_STATUS[2]);
        user.setModifyUser(loginUser.getId());
        user.setModifyTime(new Date());
        cfUserMapper.updateByPrimaryKeySelective(user);

    }

    /**
     * 功能描述: <br>
     * 〈中止交接〉
     * @Param: [handoverformid]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/13 18:02
     */
    @Override
    public void suspendHandover(String handoverformid) {
        if (handoverformid == null || "".equals(handoverformid)){
            throw new CustomMessageException("参数为空!");
        }
        //获取当前登录人
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        OmsOperatorHandover omsOperatorHandover = operatorHandoverMapper.selectByPrimaryKey(handoverformid);
        //更改状态为撤销
        omsOperatorHandover.setHandoverstatus(String.valueOf(Constants.handover_business[2]));
        operatorHandoverMapper.updateByPrimaryKeySelective(omsOperatorHandover);
        operatorHandoverMapper.deleteByPrimaryKey(omsOperatorHandover.getId());
        omsOperatorHandoverSubformMapper.deleteByPrimaryKey(omsOperatorHandover.getId());
        //更改经办人状态
        CfUser user = cfUserMapper.selectByPrimaryKey(omsOperatorHandover.getOperatorid());
       //将该经办人状态更改为正常
        user.setUserState(Constants.USER_STATUS[1]);
        user.setModifyUser(loginUser.getId());
        user.setModifyTime(new Date());
        cfUserMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 功能描述: <br>
     * 〈校验经办人〉
     * @Param: [user]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/7 14:32
     */
    private void verify(CfUser user) {

        //1、创建经办人时，要判断登录名是否重复，并且在在非撤消和拒绝状态
        CfUser selectUser = cfUserMapper.selectByUserCode(user.getUserCode());
        if(selectUser != null){
            //判断状态
            String userState = selectUser.getUserState();
            if (!userState.equals(Constants.USER_STATUS[2]) & !userState.equals(Constants.USER_STATUS[5])){
                throw new CustomMessageException("登录账号已经存在!");
            }
        }
        //2、一个机构下只允许有2个经办人,用户类型为 6经办人
        ArrayList<String> orgId = new ArrayList<>();
        orgId.add(user.getOrgId());
        List<CfUser> sysUserList = cfUserMapper.getSysUserListByParm(orgId,Constants.USER_TYPES[6],Constants.USER_STATUS[1]);
        if (sysUserList.size() >= 2){
            throw new CustomMessageException("您单位已有2名经办人，如须增加，请单独向干部监督处申请!");
        }
        ///3、检测该经办人是否在别的单位也注册成为了激活状态的经办人身份，如果有，提醒管理员：
        // “经办人XXX在XXX单位也是经办人，并处于正常办理业务状态，在XXX单位撤消他的经办人身份前，贵单位不能将其注册为经办人”
        //通过身份证号、用户类型、状态查找经办人
        CfUser operator = cfUserMapper.getOperatorByIdnumAndState(user.getIdnumber(),Constants.USER_TYPES[6],Constants.USER_STATUS[1]);
        if (operator != null && !user.getOrgId().equals(operator.getOrgId())){
            throw new CustomMessageException("经办人"+operator.getUserName()+"在"+operator.getOrgName()+"单位也是经办人，并处于正常办理业务状态，在"+operator.getOrgName()+"单位撤消他的经办人身份前，贵单位不能将其注册为经办人!");
        }
    }
}
