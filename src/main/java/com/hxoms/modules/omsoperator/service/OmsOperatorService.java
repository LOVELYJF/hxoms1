package com.hxoms.modules.omsoperator.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubform;
import com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO;
import com.hxoms.modules.omsoperator.entity.OmsOperatorJBYWQueryParam;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.sysUser.entity.CfUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈经办人管理〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/7/16 20:07
 */
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
     * @Param: [operatorId]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/5/8 14:40
     */
    String revokeOperator(String operatorId);

    /**
     * 功能描述: <br>
     * 〈经办人审批信息查询〉
     * @Param: [userId]
     * @Return: com.hxoms.modules.omsoperator.entity.OmsOperatorApproval
     * @Author: 李逍遥
     * @Date: 2020/5/11 9:14
     * @return
     */
    List<OmsOperatorApproval> getOperatorApprovalByUid(String userId);

    /**
     * 功能描述: <br>
     * 〈查询经办人交接记录〉
     * @Param: [userId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 李逍遥
     * @Date: 2020/5/11 11:32
     */
    List<OmsOperatorHandoverSubformVO>  getOperatorHandoverByUid(String userId);

    /**
     * 功能描述: <br>
     * 〈获取经办人列表〉
     * @Param: [pageNum, pageSize, keyWord, orgId, state]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/8 15:30
     */
    PageInfo getAllOperator(Integer pageNum, Integer pageSize, String keyWord, List<String> state);

    /**
     * 功能描述: <br>
     * 〈管理员确认列表〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO>
     * @Author: 李逍遥
     * @Date: 2020/7/13 15:19
     */
    List<OmsOperatorHandoverSubformVO> getAllOperatorHandover();

    /**
     * 功能描述: <br>
     * 〈确认交接完成〉
     * @Param: [handoverformid]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/13 17:42
     */
    void confirmDelivery(String handoverformid);

    /**
     * 功能描述: <br>
     * 〈中止交接〉
     * @Param: [handoverformid]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/13 18:01
     */
    void suspendHandover(String handoverformid);

    /**
     * 功能描述: <br>
     * 〈经办人名单页面——获取经办人列表〉
     * @Param: [pageNum, pageSize, orgId, keyWord, state]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/14 16:10
     */
    PageInfo getOperatorList(Integer pageNum, Integer pageSize, List<String> orgId, String keyWord, List<String> state);

    /**
     * 功能描述: <br>
     * 〈经办人——基本数据流程统计〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.CountStatusResult>
     * @Author: 李逍遥
     * @Date: 2020/7/17 15:32
     */
    List<CountStatusResult> selectCountStatus(String orgId);

    /**
     * 功能描述: <br>
     * 〈经办人名单导出〉
     * @Param: [keyWord, orgId, state]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/17 19:30
     */
    void exportOperator(String keyWord, List<String> orgId, List<String> state, HttpServletResponse response);

    /**
     * 功能描述: <br>
     * 〈经办人交接页面展示〉
     * @Param: [operatorId]
     * @Return: java.util.List<com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO>
     * @Author: 李逍遥
     * @Date: 2020/7/20 15:21
     */
    List<OmsOperatorHandoverSubformVO> getOperatorHandoverByOperatorId(String operatorId);

    /**
     * 功能描述: <br>
     * 〈经办人交接页面下一步〉
     * @Param: [omsOperatorHandoverSubforms]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/20 16:05
     */
    void nextByOperator(List<OmsOperatorHandoverSubform> omsOperatorHandoverSubforms);

    /**
     * 功能描述: <br>
     * 〈接手人确认页面展示〉
     * @Param: [handoverId]
     * @Return: java.util.List<com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO>
     * @Author: 李逍遥
     * @Date: 2020/7/20 16:20
     */
    List<OmsOperatorHandoverSubformVO> getOperatorHandoverByhandoverId(String handoverId);

    /**
     * 功能描述: <br>
     * 〈接手人确认页面下一步〉
     * @Param: [handoverformid]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/20 16:28
     */
    void nextByHandover(String handoverformid);

    /**
     * 功能描述: <br>
     * 〈经办人交接流程统计〉
     * @Param: [orgId]
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.CountStatusResult>
     * @Author: 李逍遥
     * @Date: 2020/7/20 18:08
     */
    List<CountStatusResult> selectCountStatusByHandover(String orgId);

    /**
     * 功能描述: <br>
     * 〈经办人交接流程页面展示〉
     * @Param: [orgId]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/23 14:51
     */
    PageInfo getAllOperatorHandoverByOrgId(Integer pageNum, Integer pageSize,String orgId);

    /**
     * 功能描述: <br>
     * 〈通过b0100查询经办人〉
     * @Param: [b0100]
     * @Return: java.util.List<com.hxoms.modules.sysUser.entity.CfUser>
     * @Author: 李逍遥
     * @Date: 2020/8/13 10:19
     */
    List<CfUser> getOperatorByB0100(String b0100);

    /**
     * 功能描述: <br>
     * 〈获取该撤销经办人未办结数据〉
     * @Param: [operatorId, handoverId]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/9/10 8:58
     */
    void getUnfinished(String operatorId, String handoverId);

    /**
     * 功能描述: <br>
     * 〈查询经办人经办业务列表〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/9/11 9:57
     */
    PageInfo getOperatorJBYW(OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam);

    /**
     * 功能描述: <br>
     * 〈通过条件查询经办人交接记录〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/9/11 16:43
     */
    PageInfo getOperatorWBJYW(OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam);

    /**
     * 功能描述: <br>
     * 〈经办人经办业务导出〉
     * @Param: [omsOperatorJBYWQueryParam, response]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/9/12 9:51
     */
    void exportOperatorByJBYW(OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam, HttpServletResponse response);

    /**
     * 功能描述: <br>
     * 〈经办人交接记录导出〉
     * @Param: [omsOperatorJBYWQueryParam, response]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/9/12 9:52
     */
    void exportOperatorByJJJL(OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam, HttpServletResponse response);

    /**
     * 功能描述: <br>
     * 〈导入任免表〉
     * @Param: [multipartFile, request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 李逍遥
     * @Date: 2020/9/26 10:11
     */
    Map<String, Object> rmTableImport(File multipartFile, HttpServletRequest request) throws Exception;
}
