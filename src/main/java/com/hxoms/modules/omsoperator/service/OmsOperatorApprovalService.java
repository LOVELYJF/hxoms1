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
     * 〈通过身份证读取设备匹配经办人〉
     * @Param: [idcard]
     * @Return: com.hxoms.modules.sysUser.entity.CfUser
     * @Author: 李逍遥
     * @Date: 2020/5/15 9:57
     */
    CfUser getOperatorByIdCard(String idnumber);

    /**
     * 功能描述: <br>
     * 〈指纹登记审批通过〉
     * @Param: [operator]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:27
     */
    void agreeToApproval(CfUser operator);

    /**
     * 功能描述: <br>
     * 〈审批不通过〉
     * @Param: [operator]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:42
     */
    void refuseToApproval(String operatorId);

    /**
     * 功能描述: <br>
     * 〈审批页面——审批通过〉
     * @Param: [operatorId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/14 11:43
     */
    void agreeToApprovalById(String operatorId);

    /**
     * 功能描述: <br>
     * 〈经办人身份信息重置——保存〉
     * @Param: [operator]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/14 15:29
     */
    void saveOperator(CfUser operator);
}