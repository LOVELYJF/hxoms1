package com.hxoms.modules.omsoperator.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsoperator.entity.OmsOperatorApproval;
import com.hxoms.modules.omsoperator.entity.OmsOperatorHandoverSubformVO;
import com.hxoms.modules.omsoperator.service.OmsOperatorService;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.sysUser.entity.CfUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    * 〈经办人管理页面获取经办人列表〉
    * @Param: [pageNum, pageSize, keyWord, orgId, state]
    * @Return: com.hxoms.common.utils.Result
    * @Author: 李逍遥
    * @Date: 2020/7/8 15:30
    */
    @GetMapping("/getAllOperator")
    public Result getAllOperator(Integer pageNum, Integer pageSize, String keyWord,
                                  @RequestParam(value ="state",required = false) List<String> state){
        PageInfo pageInfo = operatorService.getAllOperator(pageNum,pageSize,keyWord,state);
        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }
    /**
     * 功能描述: <br>
     * 〈经办人名单页面——获取经办人列表〉
     * @Param: [pageNum, pageSize, keyWord, orgId, state]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/14 16:09
     */
    @GetMapping("/getOperatorList")
    public Result getOperatorList(Integer pageNum, Integer pageSize, String keyWord,
                                  @RequestParam(value ="orgId",required = false) List<String> orgId,
                                  @RequestParam(value ="state",required = false) List<String> state){
        PageInfo pageInfo = operatorService.getOperatorList(pageNum,pageSize,orgId,keyWord,state);
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
        return Result.success().setMsg(msc);
    }
    /**
     * 功能描述: <br>
     * 〈新增经办人信息,保存并上传经办人〉
     * @Param: [user]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/7 10:20
     */
    @PostMapping("/saveAndUploadOperator")
    public Result saveAndUploadOperator(CfUser user){
        String msc = operatorService.saveAndUploadOperator(user);
        return Result.success().setMsg(msc);
    }

    /**
     * 功能描述: <br>
     * 〈根据UserId删除经办人〉
     * @Param: [userId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/7 19:46
     */
    @PostMapping("/deleteOperator")
    public  Result deleteOperator(String userId){
        operatorService.deleteOperator(userId);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈撤销经办人〉
     * @Param: [operatorId,handoverId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/8 14:39
     */
    @PostMapping("/revokeOperator")
    public Result revokeOperator(String operatorId,String handoverId){
        String smc = operatorService.revokeOperator(operatorId,handoverId);
        return Result.success(smc);
    }
    /**
     * 功能描述: <br>
     * 〈查询该经办人交接记录〉
     * @Param: [userId  交接人id或者接手人id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/11 11:32
     */
    @GetMapping("/getOperatorHandoverByUid")
    public Result getOperatorHandoverByUid(String userId) {
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS = operatorService.getOperatorHandoverByUid(userId);
        return Result.success(omsOperatorHandoverSubformVOS);
    }

    /**
     * 功能描述: <br>
     * 〈管理员确认列表〉
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/13 15:16
     */
    @GetMapping("/getAllOperatorHandover")
    public Result getAllOperatorHandover(){
        List<OmsOperatorHandoverSubformVO> omsOperatorHandoverSubformVOS = operatorService.getAllOperatorHandover();
        return Result.success(omsOperatorHandoverSubformVOS);
    }
    /**
     * 功能描述: <br>
     * 〈确认交接完成〉
     * @Param: [handoverformid]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/13 17:39
     */
    @PostMapping("/confirmDelivery")
    public Result confirmDelivery(String handoverformid){
        operatorService.confirmDelivery(handoverformid);
    return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈中止交接〉
     * @Param: [handoverformid]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/13 18:01
     */
    @PostMapping("/suspendHandover")
    public Result suspendHandover(String handoverformid){
        operatorService.suspendHandover(handoverformid);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈查询该经办人审批信息〉
     * @Param: [userId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/5/11 8:58
     */
    @GetMapping("/getOperatorApprovalByUid")
    public Result getOperatorApprovalByUid(String userId){
        OmsOperatorApproval approval = operatorService.getOperatorApprovalByUid(userId);
        return Result.success(approval);
    }
    /**
     * 功能描述: <br>
     * 〈经办人——基本数据流程统计〉
     * @Param: [orgId 1、管理界面传当前登录机构的id。2、审核界面不用传]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/17 15:31
     */
    @GetMapping("/selectCountStatus")
    public Result selectCountStatus(String orgId){
        List<CountStatusResult> countStatusResults = operatorService.selectCountStatus(orgId);
        return Result.success(countStatusResults);
    }
    /**
     * 功能描述: <br>
     * 〈经办人名单导出〉
     * @Param: [keyWord, orgId, state]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/17 19:30
     */
    @PostMapping("/exportOperator")
    public void exportOperator(String keyWord,@RequestParam(value ="orgId",required = false) List<String> orgId,
                                 @RequestParam(value ="state",required = false) List<String> state, HttpServletResponse response){
        operatorService.exportOperator(keyWord,orgId,state,response);
    }
}
