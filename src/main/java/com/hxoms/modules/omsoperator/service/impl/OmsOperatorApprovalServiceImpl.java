package com.hxoms.modules.omsoperator.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.omsoperator.mapper.OmsOperatorApprovalMapper;
import com.hxoms.modules.omsoperator.service.OmsOperatorApprovalService;
import com.hxoms.modules.sysUser.entity.CfUser;
import com.hxoms.modules.sysUser.mapper.CfUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 功能描述: <br>
 * 〈经办人审核，审批〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/7/16 20:06
 */
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
        //设置传入页码，以及每页的大小
        PageHelper.startPage(pageNum, pageSize);
        List<String> states = new ArrayList<>();
        List<CfUser> users = null;
        //判断当前登录人身份
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        String loginUserType = loginUser.getUserType();
        if (loginUserType.equals(Constants.USER_TYPES[5])){
            //显示监督处审核状态的数据
            states.add(Constants.USER_STATUS[3]);
            states.add(Constants.USER_STATUS[8]);
            users = cfUserMapper.getOperatorApprovalList(keyWord, orgIds, Constants.USER_TYPES[6], states);
        }else if (loginUserType.equals(Constants.USER_TYPES[13])){
            //显示处领导审批状态的数据
            states.add(Constants.USER_STATUS[4]);
            users = cfUserMapper.getOperatorApprovalList(keyWord, orgIds, Constants.USER_TYPES[6], states);
        }else if (loginUserType.equals(Constants.USER_TYPES[0])){
            states.add(Constants.USER_STATUS[3]);
            states.add(Constants.USER_STATUS[4]);
            states.add(Constants.USER_STATUS[8]);
            users = cfUserMapper.getOperatorApprovalList(keyWord, orgIds, Constants.USER_TYPES[6], states);
        }
        if (users == null){
            throw new CustomMessageException("无经办人信息!");
        }
        return new PageInfo(users);
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
    public CfUser getOperatorByIdCard(String idnumber) {
        if (StringUtils.isBlank(idnumber)){
            throw new CustomMessageException("身份证号为空!");
        }
        CfUser operator = cfUserMapper.getOperatorApprovalByIdCard(idnumber,"",Constants.USER_TYPES[6]);
        if (operator == null){
            throw new CustomMessageException("未匹配到经办人!");
        }
        return operator;
    }


    /**
     * 功能描述: <br>
     * 〈指纹登记审批通过〉
     * @Param: [operator, result, loginUser]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:34
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void agreeToApproval(CfUser operator) {
        if (operator == null){
            throw new CustomMessageException("经办人信息为空!");
        }
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        // 点击【通过】按钮，将经办人申请的状态置为“正常”
        // 并将身份证的户口所在地、签发机关、有效期开始时间、有效期结束时间、身份证物理ID写入用户表中。
        operator.setUserState(Constants.USER_STATUS[1]);
        cfUserMapper.updateByPrimaryKeySelective(operator);
        //获取审批对象
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(operator.getUserId());
        if (approval == null){
            throw new CustomMessageException("此经办人无审批信息!");
        }
        approval.setApprovalresult("同意");
        approval.setApprover(loginUser.getUserName());
        approval.setApprovaldate(new Date());
        operatorApprovalMapper.updateByPrimaryKeySelective(approval);
    }

    /**
     * 功能描述: <br>
     * 〈审批不通过〉
     * @Param: [operator]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:43
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void refuseToApproval(String operatorId) {
        if (StringUtils.isBlank(operatorId)){
            throw new CustomMessageException("经办人为空!");
        }
        //点击【不通过】按钮，将经办人申请的状态置为“拒绝”。
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        CfUser operator = cfUserMapper.selectByPrimaryKey(operatorId);
        operator.setUserState(Constants.USER_STATUS[5]);
        cfUserMapper.updateByPrimaryKeySelective(operator);
        //获取审批对象
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(operatorId);
        if (approval == null){
            throw new CustomMessageException("此经办人无审批信息!");
        }
        approval.setApprovalopinion("拒绝");
        approval.setApprovalresult("拒绝");
        approval.setApprover(loginUser.getUserName());
        approval.setApprovaldate(new Date());
        operatorApprovalMapper.updateByPrimaryKeySelective(approval);
    }

    /**
     * 功能描述: <br>
     * 〈审批页面——审批通过〉
     * @Param: [operatorId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/14 11:44
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void agreeToApprovalById(String operatorId) {
        if (StringUtils.isBlank(operatorId)){
            throw new CustomMessageException("参数为空!");
        }
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        CfUser operator = cfUserMapper.selectByPrimaryKey(operatorId);
        //获取审批对象
        OmsOperatorApproval approval = operatorApprovalMapper.selectByUserId(operatorId);
        if (approval == null){
            throw new CustomMessageException("此经办人无审批信息!");
        }
        //判断审批人身份
        String loginUserType = loginUser.getUserType();
        if (loginUserType.equals(Constants.USER_TYPES[5])){
            //将状态更改为处领导审批
            operator.setUserState(Constants.USER_STATUS[4]);
            cfUserMapper.updateByPrimaryKeySelective(operator);
            approval.setStepname("处领导审批");
            approval.setApprovalopinion("同意");
            approval.setApprover(loginUser.getUserName());
            approval.setApprovaldate(new Date());
            operatorApprovalMapper.updateByPrimaryKeySelective(approval);
        }else if (loginUserType.equals(Constants.USER_TYPES[13])){
            //将状态改为身份认证
            operator.setUserState(Constants.USER_STATUS[8]);
            cfUserMapper.updateByPrimaryKeySelective(operator);
            approval.setApprovalresult("同意");
            approval.setApprover(loginUser.getUserName());
            approval.setApprovaldate(new Date());
            operatorApprovalMapper.updateByPrimaryKeySelective(approval);
        }else {
            throw new CustomMessageException("无操作权限!");
        }
    }

    /**
     * 功能描述: <br>
     * 〈经办人身份信息重置——保存〉
     * @Param: [operator]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/14 15:29
     */
    @Transactional(rollbackFor = CustomMessageException.class)
    @Override
    public void saveOperator(CfUser operator) {
        if (operator == null){
            throw new CustomMessageException("参数为空!");
        }
        cfUserMapper.updateByPrimaryKeySelective(operator);
    }

    /**
     * 功能描述: <br>
     * 〈获取经办人审批信息〉
     * @Param: [operatorId]
     * @Return: com.hxoms.modules.omsoperator.entity.OmsOperatorApproval
     * @Author: 李逍遥
     * @Date: 2020/9/14 20:15
     */
    @Override
    public OmsOperatorApproval getApprovaByOperatorId(String operatorId) {
        if (StringUtils.isBlank(operatorId)){
            throw new CustomMessageException("参数为空!");
        }
        OmsOperatorApproval omsOperatorApproval = operatorApprovalMapper.selectByUserId(operatorId);
        return omsOperatorApproval;
    }
}
