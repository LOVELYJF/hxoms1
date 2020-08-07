package com.hxoms.modules.leaderSupervision.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.leaderSupervision.service.LeaderCommonService;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.service.impl.LeaderEXportExcelService;
import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.BussinessTypeAndIdVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
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
    @GetMapping("/leaderBatchAddApplyUser")
    public Result leaderBatchAddApplyUser(LeaderSupervisionVo leaderSupervisionVo){


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
     *  因公出国境管理 导出
     * **/

    @PostMapping("/exportPubApplyMangerExcel")
    public void exportExcel(LeaderSupervisionVo leaderSupervisionVo , HttpServletResponse response){
        try {
            HSSFWorkbook wb = leaderEXportExcelService.pubApplyMangerExport();
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
    public void exportJiweiExcel(AuditOpinionVo auditOpinionVo , HttpServletResponse response){

            leaderCommonService.updateBussinessFiledsByJiweiExport(auditOpinionVo);

        try {
            HSSFWorkbook wb = leaderEXportExcelService.jiweiApplyExport();
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


    @PostMapping("/testsql")
    public void testsql(){

        leaderEXportExcelService.test();

    }





}
