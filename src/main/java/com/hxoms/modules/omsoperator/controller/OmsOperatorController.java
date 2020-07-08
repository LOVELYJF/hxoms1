package com.hxoms.modules.omsoperator.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.omsoperator.service.OmsOperatorService;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    * @Param: [pageNum, pageSize, keyWord, orgId, state]
    * @Return: com.hxoms.common.utils.Result
    * @Author: 李逍遥
    * @Date: 2020/7/8 15:30
    */
    @GetMapping("/getOperatorList")
    public Result getOperatorList(Integer pageNum, Integer pageSize, String keyWord,
                                  @RequestParam(value ="orgId",required = false) List<String> orgId,
                                  @RequestParam(value ="state",required = false) List<String> state){
        PageInfo pageInfo = operatorService.getOperatorList(pageNum,pageSize,keyWord,orgId,state);
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
    @PostMapping("/saveOperator")
    public Result saveOperator(CfUser user) {
        String msc = operatorService.saveOperator(user);
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
    public Result saveAndUploadOperator(CfUser user){
        String msc = operatorService.saveAndUploadOperator(user);
        return Result.success(msc);
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
