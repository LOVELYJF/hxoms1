package com.hxoms.modules.omsoperator.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.omsoperator.service.OmsOperatorApprovalService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述: <br>
 * 〈经办人审核，审批〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/5/13 9:16
 */
@RestController
@RequestMapping("/operatorApproval")
public class OmsOperatorApprovalController {

    @Autowired
    OmsOperatorApprovalService operatorApprovalService;

    /**
     * 功能描述: <br>
     * 〈获取审批信息〉
     * @Param: [pageNum, pageSize, keyWord, orgId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/13 9:34
     */
    @RequestMapping("/getApprovalList")
    public Result getApprovalList (Integer pageNum, Integer pageSize, String keyWord, List<String> orgIds){
        PageInfo pageInfo = operatorApprovalService.getApprovalList(pageNum,pageSize,keyWord,orgIds);
        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }

    /**
     * 功能描述: <br>
     * 〈通过用户名或登录名及机构查询经办人〉
     * @Param: [keyWord, orgId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/15 8:51
     */
    @RequestMapping("/getApprovalByName")
    public Result getApprovalByName(String keyWord, List<String> orgId){
        List<CfUser> userList =  operatorApprovalService.getApprovalByName(keyWord,orgId);
        return Result.success(userList);
    }

    /**
     * 功能描述: <br>
     * 〈通过ID查询本经办人审批信息〉
     * @Param: [operatorId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/18 14:41
     */
    @RequestMapping("/getApprovalById")
    public Result getApprovalById(String operatorId){
        OmsOperatorApproval approval = operatorApprovalService.getApprovalById(operatorId);
        return Result.success(approval);
    }

    /**
     * 功能描述: <br>
     * 〈通过身份证读取设备匹配经办人〉
     * @Param: [idcard]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/15 9:56
     */
    @RequestMapping("/getOperatorByIdCard")
    public Result getOperatorByIdCard(CfUser user){
        CfUser operator = operatorApprovalService.getOperatorByIdCard(user);
        return Result.success(operator);
    }

    /**
     * 功能描述: <br>
     * 〈允许注册〉
     * @Param: [operator（经办人）, result（审批结果）, loginUser（当前登录用户）]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/18 9:21
     */
    @RequestMapping("/agreeToRegister")
    public Result agreeToRegister(CfUser operator,String result,CfUser loginUser){
        String msc = operatorApprovalService.agreeToRegister(operator,result,loginUser);
        return Result.success(msc);
    }

    /**
     * 功能描述: <br>
     * 〈不允许注册〉
     * @Param: [operator(经办人), result（审批结果）, loginUser（当前登录用户）]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/18 9:57
     */
    @RequestMapping("/refuseToRegister")
    public Result refuseToRegister(CfUser operator,String result,CfUser loginUser){
        String msc = operatorApprovalService.refuseToRegister(operator,result,loginUser);
        return Result.success(msc);
    }

    /**
     * 功能描述: <br>
     * 〈审批通过〉
     * @Param: [operator, result, loginUser]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:40
     */
    @RequestMapping("/agreeToApproval")
    public Result agreeToApproval(CfUser operator,String result,CfUser loginUser){
        operatorApprovalService.agreeToApproval(operator,result,loginUser);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈审批不通过〉
     * @Param: [operator, result, loginUser]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/18 15:48
     */
    @RequestMapping("/refuseToApproval")
    public Result refuseToApproval(CfUser operator,String result,CfUser loginUser){
        operatorApprovalService.refuseToApproval(operator,result,loginUser);
        return Result.success();
    }
}
