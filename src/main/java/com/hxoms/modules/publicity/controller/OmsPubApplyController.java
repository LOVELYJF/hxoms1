package com.hxoms.modules.publicity.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.publicity.entity.*;
import com.hxoms.modules.publicity.service.OmsPubApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 因公出国境干部申请
 *
 * @author sunqian
 * @date 2020/4/21 17:15
 */
@RestController
@RequestMapping("/omsPubApply")
public class OmsPubApplyController {

    @Autowired
    private OmsPubApplyService omsPubApplyService;


    /**
     * 根据机构id查询人员
     *
     * @author sunqian
     * @date 2020/4/21 17:16
     */
    @GetMapping("/selectPersonListByOrg")
    public Result selectPersonListByOrg(String b0100, String keyword) {
        List<PersonInfoVO> list = omsPubApplyService.selectPersonListByOrg(b0100, keyword);
        return Result.success(list);
    }

    /**
     * 功能描述: <br>
     * 〈新增时返回给前端（根据人员id查询申请的人员基本信息）〉
     * @Param: [b0100, a0100]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/24 8:58
     */
    @GetMapping("/selectPubApplyPersonInfo")
    public Result selectPubApplyPersonInfo(String b0100, String a0100) {
        OmsPubApplyVO omsPubApply = omsPubApplyService.selectPubApplyPersonInfo(b0100, a0100);
        return Result.success(omsPubApply);
    }
    /**
     * 功能描述: <br>
     * 〈获取负面信息、家庭主要成员、单位是否接收巡视等信息〉
     * @Param: [b0100, a0100, cgsj]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/21 9:26
     */
    @GetMapping("/getOtherPubApply")
    public Result getOtherPubApply(String b0100, String a0100,@DateTimeFormat(pattern = "yyyy.MM.dd") Date cgsj){
        OtherPubApply otherPubApply = omsPubApplyService.getOtherPubApply(b0100,a0100,cgsj);
        return Result.success(otherPubApply);
    }

    /**
     * 查询单位下申请人员的列表
     *
     * @author sunqian
     * @date 2020/4/29 18:22
     */
    @GetMapping("/selectPubApplyList")
    public Result selectPubApplyList() {
        List<OmsPubApply> list = omsPubApplyService.selectPubApplyList();
        return Result.success(list);
    }
    /**
     * 功能描述: <br>
     * 〈根据条件查询备案申请列表〉
     * @Param: [omsPubApplyQueryParam]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/28 18:03
     */
    @GetMapping("/getPubAppListByCondition")
    public Result getPubAppListByCondition(OmsPubApplyQueryParam omsPubApplyQueryParam){
        PageInfo info = omsPubApplyService.getPubAppListByCondition(omsPubApplyQueryParam);
        return Result.success(info);
    }
    /**
     * 功能描述: <br>
     * 〈因公备案申请步骤统计〉
     * @Param: [orgId]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/27 15:04
     */
    @GetMapping("/selectPubCountStatus")
    public Result selectPubCountStatus(){
        List<CountStatusResult> countStatusResults = omsPubApplyService.selectPubCountStatus();
        return Result.success(countStatusResults);
    }
    /**
     * 新增备案申请
     *
     * @author sunqian
     * @date 2020/4/26 17:21
     */
    @PostMapping("/insertPubApply")
    public Result insertPubApply(OmsPubApply omsPubApply) throws Exception {
        String result = omsPubApplyService.insertPubApply(omsPubApply);
        return Result.success().setMsg(result);
    }
    /**
     * 功能描述: <br>
     * 〈因公下一步操作根据id更改状态〉
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/28 9:47
     */
    @PostMapping("/updateSQZTById")
    public Result updateSQZTById(String id){
        omsPubApplyService.updateSQZTById(id);
        return Result.success();
    }

   /**
    * 功能描述: <br>
    * 〈根据id查询申请记录〉
    * @Param: [id]
    * @Return: com.hxoms.common.utils.Result
    * @Author: 李逍遥
    * @Date: 2020/7/28 10:30
    */
    @GetMapping("/selectPubApplyById")
    public Result selectPubApplyById(String id) {
        OmsPubApply omsPubApply = omsPubApplyService.selectPubApplyById(id);
        return Result.success(omsPubApply);
    }
    /**
     * 功能描述: <br>
     * 〈根据id删除申请人员〉
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/28 11:59
     */
    @PostMapping("/deletePubApplyById")
    public Result deletePubApplyById(String id){
        omsPubApplyService.deletePubApplyById(id);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈保存变更后信息〉
     * @Param: [omsPubApplyChange]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/3 11:45
     */
    @PostMapping("/insertPubApplyChange")
    public Result insertPubApplyChange(OmsPubApplyChange omsPubApplyChange,@RequestParam(value ="ids",required = false) List<String> ids){
        omsPubApplyService.insertPubApplyChange(omsPubApplyChange,ids);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈通过id获取变更前后信息〉
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/6 10:05
     */
    @GetMapping("/getPubApplyChange")
    public Result getPubApplyChange(String id){
        List<OmsPubApplyChange> omsPubApplyChange = omsPubApplyService.getPubApplyChange(id);
        return Result.success(omsPubApplyChange);
    }

    /**
     * 功能描述: <br>
     * 〈因公出国境申请列表导出〉
     * @Param: [omsPubApplyQueryParam, response]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/8/3 9:55
     */
    @PostMapping("/exportPubApply")
    public void exportPubApply(OmsPubApplyQueryParam omsPubApplyQueryParam, HttpServletResponse response){
        omsPubApplyService.exportPubApply(omsPubApplyQueryParam,response);
    }
    /**
     * 功能描述: <br>
     * 〈台办获取相同批文号的人员〉
     * @Param: [pwh]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/28 17:30
     */
    @GetMapping("/getPubApplyByPwh")
    public Result getPubApplyByPwh(String pwh){
        List<OmsPubApplyVO> list = omsPubApplyService.getPubApplyByPwh(pwh);
        return Result.success(list);
    }

    /**
     * 功能描述: <br>
     * 〈更改时通过批文号模糊查询添加人员〉
     * @Param: [pwh]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/8/3 15:03
     */
    @GetMapping("/getPubApplyList")
    public Result getPubApplyList(){
        List<OmsPubApplyVO> list = omsPubApplyService.getPubApplyList();
        return Result.success(list);
    }
    /**
     * 功能描述: <br>
     * 〈撤销通知书文号相同的备案申请〉
     * @Param: [pwh(通知书文号), cxyy(撤销原因)]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/1 9:00
     */
    @PostMapping("/repealAllPubApplyByPwh")
    public Result repealAllPubApplyByPwh(String pwh, String cxyy){
        omsPubApplyService.repealAllPubApplyByPwh(pwh,cxyy);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈通过id撤销备案申请〉
     * @Param: [id,cxyy]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/1 10:31
     */
    @PostMapping("/repealPubApplyById")
    public Result repealPubApplyById(String id, String cxyy){
        omsPubApplyService.repealPubApplyById(id,cxyy);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈添加或修改干教因公出国备案申请〉
     * @Param: [omsPubGroupPreApproval]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/6 15:52
     */
    @PostMapping("/insertOrUpdatePubGroupPreApproval")
    public Result insertOrUpdatePubGroupPreApproval(@RequestBody OmsPubGroupPreApprovalVO omsPubGroupPreApproval){
        String result = omsPubApplyService.insertOrUpdatePubGroupPreApproval(omsPubGroupPreApproval);
        return Result.success().setMsg(result);
    }
    /**
     * 功能描述: <br>
     * 〈根据ID查看干教信息〉
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/7 17:47
     */
    @GetMapping("/getPubGroupPreApprovalById")
    public Result getPubGroupPreApprovalById(String id){
        OmsPubGroupPreApprovalVO info =omsPubApplyService.getPubGroupPreApprovalById(id);
        return Result.success(info);
    }

    /**
     * 功能描述: <br>
     * 〈上报干部监督处〉
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/8/7 9:54
     */
    @PostMapping("/reportPubGroupPreApproval")
    public Result reportPubGroupPreApproval(@RequestParam(value ="id",required = false) List<String> id){
        omsPubApplyService.reportPubGroupPreApproval(id);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈根据条件查看干教列表〉
     * @Param: [omsPubApplyQueryParam]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/7 15:50
     */
    @GetMapping("/getPubGroupPreApprovalByCondition")
    public Result getPubGroupPreApprovalByCondition(OmsPubApplyQueryParam omsPubApplyQueryParam){
        PageInfo info = omsPubApplyService.getPubGroupPreApprovalByCondition(omsPubApplyQueryParam);
        return Result.success(info);
    }

   /**
    * 功能描述: <br>
    * 〈撤销整个干教申请〉
    * @Param: [id, cxyy]
    * @Return: com.hxoms.common.utils.Result
    * @Author: 李逍遥
    * @Date: 2020/8/10 10:09
    */
    @PostMapping("/repealGJ")
    public Result repealGJ(String id,String cxyy){
        omsPubApplyService.repealGJ(id,cxyy);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈根据ID删除干教信息〉
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/7/7 17:35
     */
    @PostMapping("/deletePubGroupPreApprovalById")
    public Result deletePubGroupPreApprovalById(String id){
        omsPubApplyService.deletePubGroupPreApprovalById(id);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈修改通知书文号〉
     * @Param: [yPWH, xPWH]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/8/31 8:54
     */
    @PostMapping("/updataPWH")
    public Result updataPWH(String yPWH , String xPWH){
        omsPubApplyService.updatePWH(yPWH , xPWH);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈获取台办批文号树〉
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/8/31 10:38
     */
    @GetMapping("/getPWHList")
    public Result getPWHList(){
        List<PWHVO> pwhList = omsPubApplyService.getPWHList();
        return Result.success(pwhList);
    }
}
