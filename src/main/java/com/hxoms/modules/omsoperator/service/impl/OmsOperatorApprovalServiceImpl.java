package com.hxoms.modules.omsoperator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorApprovalMapper;
import com.hxoms.modules.omsoperator.service.OmsOperatorApprovalService;
import com.hxoms.modules.sysUser.entity.CfUser;
import com.hxoms.modules.sysUser.mapper.CfUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OmsOperatorApprovalServiceImpl implements OmsOperatorApprovalService {

    @Autowired
    private OmsOperatorApprovalMapper operatorApprovalMapper;
    @Autowired
    private CfUserMapper cfUserMapper;
    /**
     * 功能描述: <br>
     * 〈查询经办人审批列表〉
     * @Param: [pageNum, pageSize, keyWord, orgId]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/5/13 10:35
     */
    @Override
    public PageInfo getApprovalList(Integer pageNum, Integer pageSize, String keyWord, List<String> orgIds) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);   //设置传入页码，以及每页的大小
        //页面打开时，网格中显示“待审批”和“征求意见”的经办人。
        List<String> states = new ArrayList<>();
        states.add("3");
        states.add("4");
        String userType = "6";
        List<CfUser> users = cfUserMapper.getOperatorApprovalList(keyWord,orgIds,userType,states);
        if (users == null){
            throw new CustomMessageException("无经办人信息!");
        }
        return new PageInfo(users);
    }

    /**
     * 功能描述: <br>
     * 〈通过用户名或登录名及机构查询经办人〉
     * @Param: [keyWord, orgId]
     * @Return: java.util.List<com.hxoms.modules.sysUser.entity.CfUser>
     * @Author: 李逍遥
     * @Date: 2020/5/15 8:53
     */
    @Override
    public List<CfUser> getApprovalByName(String keyWord, List<String> orgId) {
        List<String> states = new ArrayList<>();
        states.add("3");
        states.add("4");
        String userType = "6";
        List<CfUser> users = cfUserMapper.getOperatorApprovalList(keyWord,orgId,userType,states);
        if (users == null){
            throw new CustomMessageException("无经办人信息!");
        }
        return users;
    }

    /**
     * 功能描述: <br>
     * 〈通过身份证读取设备匹配经办人〉
     * @Param: [idcard]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/5/15 9:57
     */
    @Override
    public CfUser getOperatorByIdCard(CfUser user) {
        if (user == null){
            throw new CustomMessageException("身份证信息为空!");
        }
        String idCard = user.getIdnumber();
        if(idCard == null){
            throw new CustomMessageException("身份证号为空!");
        }
        //从待审批中查找经办人
        CfUser operator = cfUserMapper.getOperatorApprovalByIdCard(idCard,"4","6");
        if (operator == null){
            throw new CustomMessageException(user.getUserName()+"同志的申请还未提交到干部监督处!");
        }
        return operator;
    }

    /**
     * 功能描述: <br>
     * 〈允许注册〉
     * @Param: [operator, result, loginUser]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/18 14:47
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String agreeToRegister(CfUser operator, String result, CfUser loginUser) {
        //点击【允许注册】，将申请状态置为“待审批”，将结果保存到经办人审批表的审批意见中，审批结论置为“同意”，
        if (operator == null){
            throw new CustomMessageException("经办人为空!");
        }
        //更改状态为"待审批4"
        cfUserMapper.updateUserState(operator.getUserId(),"4");
        //获取审批对象
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(operator.getUserId());
        if (approval == null){
             throw new CustomMessageException("此经办人无审批信息!");
        }
        //将结果保存到经办人审批表的审批意见中，审批结论置为“同意”
        approval.setApprovalopinion(result);
        approval.setApprovalresult("同意");
        // 同时记录审批人及审批时间，通知申请单位系统管理员：“请XXX同志持身份证及单位审批盖章的申请表到干部监督处办理审批手续”
        approval.setApprover(loginUser.getUserName());
        approval.setApprovaldate(new Date());
        operatorApprovalMapper.updateByPrimaryKeySelective(approval);
        return "请"+operator.getUserName()+"同志持身份证及单位审批盖章的申请表到干部监督处办理审批手续!";
    }

    /**
     * 功能描述: <br>
     * 〈不允许注册〉
     * @Param: [operator, result, loginUser]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/18 14:47
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public String refuseToRegister(CfUser operator, String result, CfUser loginUser) {
        //点击【不允许注册】，弹出拒绝理由填写界面，点【确定】按钮后将申请状态置为“拒绝”，
        if (operator == null){
            throw new CustomMessageException("经办人为空!");
        }
        //更改状态为"拒绝5"
        cfUserMapper.updateUserState(operator.getUserId(),"5");
        //获取审批对象
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(operator.getUserId());
        if (approval == null){
            throw new CustomMessageException("此经办人无审批信息!");
        }
        // 将结果保存到经办人审批表的审批意见中，审批结论置为“拒绝”，同时记录审批人及审批时间，
        // 通知申请单位：“XXX同志的经办人身份申请被拒绝”
        approval.setApprovalopinion(result);
        approval.setApprovalresult("拒绝");
        approval.setApprover(loginUser.getUserName());
        approval.setApprovaldate(new Date());
        operatorApprovalMapper.updateByPrimaryKeySelective(approval);
        return operator.getUserName()+"同志的经办人身份申请被拒绝!";
    }

    /**
     * 功能描述: <br>
     * 〈获取该经办人审批信息〉
     * @Param: [operatorId]
     * @Return: com.hxoms.modules.omsoperator.entity.OmsOperatorApproval
     * @Author: 李逍遥
     * @Date: 2020/5/18 14:48
     */
    @Override
    public OmsOperatorApproval getApprovalById(String operatorId) {
        if (operatorId == null || operatorId.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(operatorId);
        if (approval == null){
            throw new CustomMessageException("无此经办人的审批信息!");
        }
        return approval;
    }

    /**
     * 功能描述: <br>
     * 〈审批通过〉
     * @Param: [operator, result, loginUser]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:34
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void agreeToApproval(CfUser operator, String result, CfUser loginUser) {
        // 点击【通过】按钮，将经办人申请的状态置为“正常”
        // 并将身份证的户口所在地、签发机关、有效期开始时间、有效期结束时间、身份证物理ID写入用户表中。
        operator.setUserState("1");
        cfUserMapper.updateByPrimaryKeySelective(operator);
        //获取审批对象
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(operator.getUserId());
        if (approval == null){
            throw new CustomMessageException("此经办人无审批信息!");
        }
        approval.setApprovalopinion(result);
        approval.setApprovalresult("同意");
        approval.setApprover(loginUser.getUserName());
        approval.setApprovaldate(new Date());
        operatorApprovalMapper.updateByPrimaryKeySelective(approval);
    }

    /**
     * 功能描述: <br>
     * 〈审批不通过〉
     * @Param: [operator, result, loginUser]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:43
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void refuseToApproval(CfUser operator, String result, CfUser loginUser) {
        //点击【不通过】按钮，将经办人申请的状态置为“拒绝”。
        operator.setUserState("5");
        cfUserMapper.updateByPrimaryKeySelective(operator);
        //获取审批对象
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(operator.getUserId());
        if (approval == null){
            throw new CustomMessageException("此经办人无审批信息!");
        }
        approval.setApprovalopinion(result);
        approval.setApprovalresult("拒绝");
        approval.setApprover(loginUser.getUserName());
        approval.setApprovaldate(new Date());
        operatorApprovalMapper.updateByPrimaryKeySelective(approval);
    }
}
