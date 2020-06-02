package com.hxoms.modules.omsoperator.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.omsoperator.service.OmsOperatorService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈经办人管理〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/5/6 11:02
 */
@RestController
@RequestMapping("/omsOperator")
public class OmsOperatorController {

    @Autowired
    private OmsOperatorService operatorService;

    /**
     * 功能描述: <br>
     * 〈获取经办人列表〉
     * @Param: [pageNum, pageSize, keyWord, orgId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/18 14:28
     */
    @RequestMapping("/getOperatorList")
    public Result getOperatorList(Integer pageNum, Integer pageSize, String keyWord, List<String> orgId){
        PageInfo pageInfo = operatorService.getOperatorList(pageNum,pageSize,keyWord,orgId);
        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }
    /**
     * 功能描述: <br>
     * 〈新增经办人信息,保存经办人〉
     * @Param: [user]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/6 11:46
     */
    @RequestMapping("/saveOperator")
    public Result saveOperator(CfUser user,CfUser loginUser) {
        String msc = operatorService.saveOperator(user,loginUser);
        return Result.success(msc);
    }
    /**
     * 功能描述: <br>
     * 〈新增经办人信息,保存并上传经办人〉
     * @Param: [user]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/7 10:20
     */
    @RequestMapping("/saveAndUploadOperator")
    public Result saveAndUploadOperator(CfUser user,CfUser loginUser){
        String msc = operatorService.saveAndUploadOperator(user,loginUser);
        return Result.success(msc);
    }

    /**
     * 功能描述: <br>
     * 〈通过姓名、状态列表、机构列表查询经办人〉
     * @Param: [name, state,orgIds]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/7 15:21
     */
    @RequestMapping("/getOperatorByNameOrState")
    public Result getOperatorByNameOrState(String name, List<String> state, List<String> orgIds){
        List<CfUser> userList = operatorService.getOperatorByNameOrState(name,state,orgIds);
        return Result.success(userList);
    }

    /**
     * 功能描述: <br>
     * 〈根据UserId删除经办人〉
     * @Param: [userId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/7 19:46
     */
    @RequestMapping("/deleteOperator")
    public  Result deleteOperator(String userId){
        operatorService.deleteOperator(userId);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈撤销经办人〉
     * @Param: [user]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/8 14:39
     */
    @RequestMapping("/revokeOperator")
    public Result revokeOperator(CfUser user,CfUser loginUser){
        String smc = operatorService.revokeOperator(user,loginUser);
        return Result.success(smc);
    }
    /**
     * 功能描述: <br>
     * 〈经办人审批信息查询〉
     * @Param: [userId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/11 8:58
     */
    @RequestMapping("/getOperatorApprovalByUid")
    public Result getOperatorApprovalByUid(String userId){
        OmsOperatorApproval approval = operatorService.getOperatorApprovalByUid(userId);
        return Result.success(approval);
    }
    /**
     * 功能描述: <br>
     * 〈查询经办人交接记录〉
     * @Param: [userId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/11 11:32
     */
    @RequestMapping("/getOperatorHandoverByUid")
    public Result getOperatorHandoverByUid(String userId) {
        Map<String,Object> map = operatorService.getOperatorHandoverByUid(userId);
        return Result.success(map);
    }
}
