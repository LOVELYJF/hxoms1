package com.hxoms.modules.omsoperator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorApprovalMapper;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorHandoverMapper;
import com.hxoms.modules.omsoperator.service.OmsOperatorService;
import com.hxoms.modules.sysUser.entity.CfUser;
import com.hxoms.modules.sysUser.mapper.CfUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OmsOperatorServiceImpl implements OmsOperatorService {

    @Autowired
    private CfUserMapper cfUserMapper;
    @Autowired
    private OmsOperatorApprovalMapper operatorApprovalMapper;
    @Autowired
    private OmsOperatorHandoverMapper operatorHandoverMapper;
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
    public String saveOperator(CfUser user,CfUser loginUser) {
        //1、判断有没有选择经办人
        if (user == null) {
            throw new CustomMessageException("请先选择经办人!");
        }
        //提示信息
        String msc;

        if (StringUilt.isStrOrnull(user.getUserId())) {
            //更新
            //修改人
            user.setModifyUser(loginUser.getUserName());
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
            user.setUserType("6");
            //设置状态
            user.setUserState("0");
            //创建人
            user.setCreator(loginUser.getUserName());
            //创建时间
            user.setCreatetime(new Date());
            cfUserMapper.insert(user);
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
    public String saveAndUploadOperator(CfUser user,CfUser loginUser) {
        //1、判断有没有选择经办人
        if (user == null) {
            throw new CustomMessageException("请先选择经办人!");
        }
        String msc ="";
        //创建审批对象
        OmsOperatorApproval  omsOperatorApproval = new OmsOperatorApproval();
        if (StringUilt.isStrOrnull(user.getUserId())) {
            //更新
            //修改人（未设置，1、从前端传过来 2、后台获取目前登录人员角色）
            user.setModifyUser(loginUser.getUserName());
            //修改时间
            user.setModifyTime(new Date());
            return getString(user, msc, omsOperatorApproval,loginUser);
        }else {
            //新增
            //校验
            verify(user);
            //设置ID
            user.setUserId(UUIDGenerator.getPrimaryKey());
            //设置初始密码
            user.setUserPassword(UUIDGenerator.encryptPwd(Constants.USER_PWD));
            //设置用户类型（1超级管理员、2系统管理员、3安全审计管理员、4安全保密管理员、5监督处工作人员、6经办人、7相关处室、8其他）
            user.setUserType("6");
            //创建人（未设置，1、从前端传过来 2、后台获取当前登录人，）
            user.setCreator(loginUser.getUserName());
            //创建时间
            user.setCreatetime(new Date());
            //经办人审批表主键、
            omsOperatorApproval.setId(UUIDGenerator.getPrimaryKey());
            // 经办人主键、
            omsOperatorApproval.setOperatorid(user.getUserId());
            //系统自动判断该经办人是否是党员
            return getString(user, msc, omsOperatorApproval,loginUser);
        }
    }

    /**
     * 功能描述: <br>
     * 〈判断是否是党员并获取返回信息〉
     * @Param: [user, msc, omsOperatorApproval]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/8 10:57
     */
    private String getString(CfUser user, String msc, OmsOperatorApproval omsOperatorApproval,CfUser loginUser) {
        OmsOperatorApproval omsOperatorApproval1 = operatorApprovalMapper.selectByUserId(user.getUserId());
        CfUser user1 = cfUserMapper.selectByPrimaryKey(user.getUserId());
        if (user.getPoliticalAffi().equals("中共党员") || user.getPoliticalAffi().equals("党员")) {
            //如果是，将状态置为“待审批”，用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)
            user.setUserState("4");
            // 往经办人审批表插入步骤为“干部监督处审批”的审批记录，提醒系统管理员：“请通知经办人持单位审批后的申请表及身份证到干部监督处办理审批手续”
            //步骤名称（1.征求意见 2.干部监督处审批）、
            omsOperatorApproval.setStepname("干部监督处审批");
            // 提交时间
            omsOperatorApproval.setSubmissiontime(new Date());
            // 提交人（未设置，当前登录人）
            omsOperatorApproval.setSubmitter(loginUser.getUserName());

            if (omsOperatorApproval1 != null){
                //更新
                operatorApprovalMapper.updateByPrimaryKey(omsOperatorApproval);
            }else {
                //新增
                operatorApprovalMapper.insertSelective(omsOperatorApproval);
            }
            if (user1 != null){
                //更新
                cfUserMapper.updateByPrimaryKeySelective(user);
            }else {
                //新增
                cfUserMapper.insert(user);
            }
            msc = "请通知经办人持单位审批后的申请表及身份证到干部监督处办理审批手续!";
            return msc;

        } else {
            //否则置为“征求意见”
            user.setUserState("3");
            // 往经办人审批表插入步骤为“征求意见”的审批记录。
            omsOperatorApproval.setStepname("征求意见");
            // 提交时间
            omsOperatorApproval.setSubmissiontime(new Date());
            // 提交人（未设置，当前登录人）
            omsOperatorApproval.setSubmitter(loginUser.getUserName());
            if (omsOperatorApproval1 != null){
                //更新
                operatorApprovalMapper.updateByPrimaryKey(omsOperatorApproval);
            }else {
                //新增
                operatorApprovalMapper.insertSelective(omsOperatorApproval);
            }
            if (user1 != null){
                //更新
                cfUserMapper.updateByPrimaryKeySelective(user);
            }else {
                //新增
                cfUserMapper.insert(user);
            }
            return msc;
        }
    }

    /**
     * 功能描述: <br>
     * 〈根据姓名或者状态查询经办人列表〉
     * @Param: [name, state]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/5/7 16:00
     */
    @Override
    public List<CfUser> getOperatorByNameOrState(String name, List<String> state, List<String> orgIds) {
        return cfUserMapper.getOperatorByNameOrState(name,state,orgIds);
    }

    /**
     * 功能描述: <br>
     * 〈根据UserID删除经办人〉
     * @Param: [userId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/7 19:48
     */
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
     * @Param: [user]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/8 14:41
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String revokeOperator(CfUser user,CfUser loginUser) {
        String msc = "";
        if (user == null) {
            throw new CustomMessageException("请先选择经办人!");
        }
        String userState = user.getUserState();
        //用户状态(注册0、正常1、撤销2、征求意见3、待审批4、拒绝5、待撤消6、暂停7)
        if (userState.equals("1")){
            //状态为正常时操作显示：【撤消】、【审批信息】，将状态置为“待撤消”，系统通知经办人：“请到经办人交接页面办理交接手续”
            cfUserMapper.updateUserState(user.getUserId(),"6");
            msc ="请到经办人交接页面办理交接手续!";
        } else if (userState.equals("3")){
            //状态为征求意见时操作显示：【撤消】，此操作直接将状态置为“撤消”，通知干部监督处：“经办人XXX的申请已被XXX单位撤消”
            cfUserMapper.updateUserState(user.getUserId(),"2");
            //需要当前操作人ID(未设置)
            msc = "经办人"+user.getUserName()+"的申请已被"+loginUser.getOrgName()+"单位撤消!";
        }else if (userState.equals("4")){
            //状态为待审批时操作显示：【撤消】、【打印申请表】，此操作直接将状态置为“撤消”，通知干部监督处：“经办人XXX的申请已被XXX单位撤消”
            cfUserMapper.updateUserState(user.getUserId(),"2");
            //需要当前操作人ID(未设置)
            msc = "经办人"+user.getUserName()+"的申请已被"+loginUser.getOrgName()+"单位撤消!";
        }
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
        if (userId == null) {
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
    public Map<String, Object> getOperatorHandoverByUid(String userId) {
        if (userId == null) {
            throw new CustomMessageException("参数为空！");
        }
        //结果map
        Map<String, Object> resultMap = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> list1 = operatorHandoverMapper.selectByUserId(userId);
        PageInfo info1 = new PageInfo(list1);
        resultMap.put("info",info1);
        return resultMap;
    }

    /**
     * 功能描述: <br>
     * 〈获取经办人列表〉
     * @Param: [pageNum, pageSize, keyWord, orgId]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/5/13 9:42
     */
    @Override
    public PageInfo getOperatorList(Integer pageNum, Integer pageSize, String keyWord, List<String> orgId) {

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);   //设置传入页码，以及每页的大小
        List<CfUser> users = cfUserMapper.getSysUserListByParm(orgId, "6");
        PageInfo info = new PageInfo(users);
        return info;
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
            if (userState.equals("2") || userState.equals("5")){
                throw new CustomMessageException("登录账号已经存在!");
            }
        }
        //2、一个机构下只允许有2个经办人,用户类型为 6经办人
        ArrayList<String> orgId = new ArrayList<>();
        orgId.add(user.getOrgId());
        List<CfUser> sysUserList = cfUserMapper.getSysUserListByParm(orgId,"6");
        if (sysUserList.size() >= 2){
            throw new CustomMessageException("您单位已有2名经办人，如须增加，请单独向干部监督处申请!");
        }
        ///3、检测该经办人是否在别的单位也注册成为了激活状态的经办人身份，如果有，提醒管理员：
        // “经办人XXX在XXX单位也是经办人，并处于正常办理业务状态，在XXX单位撤消他的经办人身份前，贵单位不能将其注册为经办人”
        //通过身份证号、用户类型、状态查找经办人
        CfUser operator = cfUserMapper.getOperatorByIdnumAndState(user.getIdnumber(),"6","1");
        if (operator != null && !user.getOrgId().equals(operator.getOrgId())){
            throw new CustomMessageException("经办人"+operator.getUserName()+"在"+operator.getOrgName()+"单位也是经办人，并处于正常办理业务状态，在"+operator.getOrgName()+"单位撤消他的经办人身份前，贵单位不能将其注册为经办人!");
        }
    }
}
