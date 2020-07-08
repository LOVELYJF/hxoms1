package com.hxoms.modules.omsoperator.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.sysUser.entity.CfUser;

import java.util.List;
import java.util.Map;

public interface OmsOperatorService {
    /**
     * 功能描述: <br>
     * 〈保存经办人〉
     * @Param: [user]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/7 14:36
     */
    String saveOperator(CfUser user);

    /**
     * 功能描述: <br>
     * 〈保存并上传经办人〉
     * @Param: [user]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/7 14:36
     */
    String saveAndUploadOperator(CfUser user);

    /**
     * 功能描述: <br>
     * 〈根据userID删除经办人〉
     * @Param: [userId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/7 19:47
     */
    void deleteOperator(String userId);

    /**
     * 功能描述: <br>
     * 〈撤销经办人〉
     * @Param: [user]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/8 14:40
     */
    String revokeOperator(CfUser user,CfUser loginUser);

    /**
     * 功能描述: <br>
     * 〈经办人审批信息查询〉
     * @Param: [userId]
     * @Return: com.hxoms.modules.omsoperator.entity.OmsOperatorApproval
     * @Author: 李逍遥
     * @Date: 2020/5/11 9:14
     */
    OmsOperatorApproval getOperatorApprovalByUid(String userId);

    /**
     * 功能描述: <br>
     * 〈查询经办人交接记录〉
     * @Param: [userId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 李逍遥
     * @Date: 2020/5/11 11:32
     */
    Map<String, Object> getOperatorHandoverByUid(String userId);

    /**
     * 功能描述: <br>
     * 〈获取经办人列表〉
     * @Param: [pageNum, pageSize, keyWord, orgId, state]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/8 15:30
     */
    PageInfo getOperatorList(Integer pageNum, Integer pageSize, String keyWord, List<String> orgId,List<String> state);
}
