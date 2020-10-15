package com.hxoms.modules.leaderSupervision.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.service.LeaderCommonService;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.service.impl.LeaderEXportExcelService;
import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.BussinessTypeAndIdVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.leaderSupervision.vo.OmsJiweiOpinionVo;
import com.hxoms.modules.publicity.entity.OmsPubApplyQueryParam;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leaderSupervision")
public class  LeaderSupervisionController {

    @Autowired
    private LeaderCommonService leaderCommonService;
    @Autowired
    private LeaderEXportExcelService leaderEXportExcelService;
    @Autowired
    private LeaderDetailProcessingService leaderDetailProcessingService;




    /**
     *  选择业务人员 页面 （LeaderSupervisionVo） 有批次 待办业务管理 的 办理 业务
     *
     */
    @GetMapping("/selectBusinessUser")
    public Result selectBusinessUser(LeaderSupervisionVo leaderSupervisionVo){

        PageInfo pageInfo = leaderCommonService.selectBusinessUser(leaderSupervisionVo, Constants.leader_business[0]);

        return  Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }

    /**
     *  进入批次 新建 页面
     * */

    @GetMapping("/createBatchPage")
    public Result createBatchPage( ){


       Map dataMap =  leaderCommonService.createBacthByUsers();

        return Result.success(dataMap);
    }


    /**
     * 保存批次
     * **/
    @PostMapping("/saveBatch")
    public Result saveBatch(@RequestBody LeaderSupervisionVo leaderSupervisionVo){

        leaderCommonService.saveBatch(leaderSupervisionVo);

        return Result.success();
    }

    @GetMapping("/deleteLeaderBatch")
    public Result deleteLeaderBatch(OmsLeaderBatch omsLeaderBatch){

        leaderCommonService.deleteLeaderBatch(omsLeaderBatch);

        return Result.success();
    }




    /**
    * 材料审核 人员名单
    * **/
    @GetMapping("/selectMaterialReviewBusinessUser")
    public Result selectMaterialReviewBusinessUser(LeaderSupervisionVo leaderSupervisionVo){

        PageInfo pageInfo = leaderCommonService.selectMaterialReviewBusinessUser(leaderSupervisionVo);

        return  Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }

    /**
     *(选择人员)  纳入批次
     * **/
    @PostMapping("/leaderBatchAddApplyUser")
    public Result leaderBatchAddApplyUser(@RequestBody LeaderSupervisionVo leaderSupervisionVo){


        leaderCommonService.leaderBatchAddApplyUser(leaderSupervisionVo);

        return Result.success();

    }
    /**
     * 通知经办人重新递交备案函
     * **/
    @GetMapping("/sendMessageToAgent")
    public Result sendMessageToAgent(String applyId,String tableCode){

        leaderDetailProcessingService.sendMessageToAgent(applyId,tableCode);

        return Result.success();

    }

    /**
     * 征求纪委意见 查询页面
     *
     * **/
    @GetMapping("/selectjiweiBusinessUser")
    public Result selectjiweiBusinessUser(LeaderSupervisionVo leaderSupervisionVo){

        PageInfo pageInfo = leaderCommonService.selectjiweiBusinessUser(leaderSupervisionVo);

        return  Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }

    /** 再次征求纪委意见 **/
    @GetMapping("/againAskFor")
    public Result againAskFor(String bussinessType,String applyId){
          leaderCommonService.updateBussinessByagainAskFor(bussinessType,applyId);
      return  Result.success();
    }

    /**
     *  纪委意见录入 查询页面
     * **/
    @GetMapping("/selectjiweiWriteBusinessUser")
    public Result selectjiweiWriteBusinessUser(LeaderSupervisionVo leaderSupervisionVo){


        PageInfo pageInfo =  leaderCommonService.selectjiweiWriteBusinessUser(leaderSupervisionVo);

        return  Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }


//    /**
//     *
//     *  TODO 点击 纪委意见记录 触发的 事件
//     * **/
//    @PostMapping("/clickJieweiOpinion")
//     public Result  clickJieweiOpinion(@RequestBody OmsJiweiOpinionVo omsJiweiOpinionVo){
//
//         leaderCommonService.clickJieweiOpinion(omsJiweiOpinionVo);
//
//         return Result.success();
//     }


    /**
     *  录入口头纪委意见
     * **/
    @PostMapping("/saveJieweiOpinion")
    public Result saveJieweiOpinion(@RequestBody OmsJiweiOpinionVo omsJiweiOpinionVo){

        leaderCommonService.saveJieweiOpinion(omsJiweiOpinionVo);

        return Result.success();
    }

    /**
     * 查询 书面纪委意见 需要关联的批次
     * **/
    @GetMapping("/selectOffictJiiweiOpinionRelevanceLeaderBatch")
    public Result selectOffictJiiweiOpinionRelevanceLeaderBatch(){

        List lists= leaderCommonService.selectOffictJiiweiOpinionRelevanceLeaderBatch();

        return Result.success(lists);
    }

    /**
     * 处领导 查询页面
     * **/
    @GetMapping("/selectChuZhangBusinessUser")
    public Result selectChuZhangBusinessUser(LeaderSupervisionVo leaderSupervisionVo){

        PageInfo pageInfo =  leaderCommonService.selectChuZhangBusinessUser(leaderSupervisionVo);

        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }


    /**
     *  因公出国境管理 导出
     * **/

    @GetMapping("/exportPubApplyMangerExcel")
    public void exportPubApplyMangerExcel(OmsPubApplyQueryParam omsPubApplyQueryParam, HttpServletResponse response){
        try {
            HSSFWorkbook wb = leaderEXportExcelService.pubApplyMangerExport(omsPubApplyQueryParam);
            String date = new SimpleDateFormat("yyyy-MM-dd")
                    .format(new Date());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode("因公出国境管理"+date+".xls", "utf-8")));
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomMessageException("导出失败，原因："+e.getMessage());
        }
    }

    /**
     * 征求 纪委意见 导出
     *
     * **/

    @PostMapping("/exportJiweiExcel")
    public void exportJiweiExcel(@RequestBody LeaderSupervisionVo leaderSupervisionVo , HttpServletResponse response){



        try {
            HSSFWorkbook wb = leaderEXportExcelService.jiweiApplyExport(leaderSupervisionVo);
            leaderCommonService.updateBussinessFiledsByJiweiExport(leaderSupervisionVo);
            String date = new SimpleDateFormat("yyyy-MM-dd")
                    .format(new Date());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode("纪委意见"+date+".xls", "utf-8")));
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomMessageException("导出失败，原因："+e.getMessage());
        }
    }


    @PostMapping("/testsql")
    public void testsql(){

        leaderEXportExcelService.test();

    }







}
