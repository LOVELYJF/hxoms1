package com.hxoms.modules.omsoperator.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.sysUser.entity.CfUser;

import java.util.List;

public interface OmsOperatorApprovalService {
    /**
     * 功能描述: <br>
     * 〈查询经办人审批列表〉
     * @Param: [pageNum, pageSize, keyWord, orgId]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/5/13 10:34
     */
    PageInfo getApprovalList(Integer pageNum, Integer pageSize, String keyWord, List<String> orgIds);

    /**
     * 功能描述: <br>
     * 〈通过用户名或登录名及机构查询经办人〉
     * @Param: [keyWord, orgId]
     * @Return: java.util.List<com.hxoms.modules.sysUser.entity.CfUser>
     * @Author: 李逍遥
     * @Date: 2020/5/15 8:53
     */
    List<CfUser> getApprovalByName(String keyWord, List<String> orgId);

    /**
     * 功能描述: <br>
     * 〈通过身份证读取设备匹配经办人〉
     * @Param: [idcard]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/5/15 9:57
     */
    CfUser getOperatorByIdCard(CfUser user);

    /**
     * 功能描述: <br>
     * 〈允许注册〉
     * @Param: [operatorId, result, loginUser]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/18 9:26
     */
    String agreeToRegister(CfUser operator, String result, CfUser loginUser);

    /**
     * 功能描述: <br>
     * 〈不允许注册〉
     * @Param: [operator, result, loginUser]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/18 9:58
     */
    String refuseToRegister(CfUser operator, String result, CfUser loginUser);

    /**
     * 功能描述: <br>
     * 〈获取该经办人审批信息〉
     * @Param: [operatorId]
     * @Return: com.hxoms.modules.omsoperator.entity.OmsOperatorApproval
     * @Author: 李逍遥
     * @Date: 2020/5/18 14:49
     */
    OmsOperatorApproval getApprovalById(String operatorId);

    /**
     * 功能描述: <br>
     * 〈审批通过〉
     * @Param: [operator, result, loginUser]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:27
     */
    void agreeToApproval(CfUser operator, String result, CfUser loginUser);

    /**
     * 功能描述: <br>
     * 〈审批不通过〉
     * @Param: [operator, result, loginUser]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:42
     */
    void refuseToApproval(CfUser operator, String result, CfUser loginUser);
}