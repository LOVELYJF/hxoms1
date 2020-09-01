package com.hxoms.modules.leaderSupervision.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.leaderSupervision.service.LeaderCommonService;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.service.impl.LeaderEXportExcelService;
import com.hxoms.modules.leaderSupervision.vo.AuditOpinionVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
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

/**
 * @authore:wjf
 * @data 2020/8/17 9:44
 * @Description:  做出审核意见
 ***/
@RestController
@RequestMapping("/makeCheckOpinion")
public class MakeCheckOpinionController {

    @Autowired
    private LeaderCommonService leaderCommonService;
    @Autowired
    private LeaderEXportExcelService leaderEXportExcelService;
    @Autowired
    private LeaderDetailProcessingService leaderDetailProcessingService;

    /**
     *
     * 做出审核意见 查询页面
     *
     * **/

    @GetMapping("/makeCheckOpinionPage")
    public Result makeCheckOpinionPage(AuditOpinionVo auditOpinionVo){

        PageInfo pageInfo =  leaderCommonService.selectAuditOpinionBusinessUser(auditOpinionVo);


        return  Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }

    /**
     *
     * 做出审核意见 导出
     *
     * **/
    @PostMapping("/makeCheckOpinionByExport")
    public void makeCheckOpinionByExport(AuditOpinionVo auditOpinionVo, HttpServletResponse response){


        try {
            HSSFWorkbook wb = leaderEXportExcelService.makeCheckOpinionExport(auditOpinionVo);
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
     *
     * 做出审核意见【生成审批单】
     * **/
    @PostMapping("/makeApprovalFor")
    public Result makeApprovalFor(@RequestBody LeaderSupervisionVo leaderSupervisionVo){

       List<Map> lists= leaderDetailProcessingService.makeApprovalFor(leaderSupervisionVo);

        return Result.success(lists);
    }

    /**
     * 保存或者更新
     *
     */
    @PostMapping("/insertOrUpadateCreateFileAndUpdateStaus")
    public Result insertOrUpadateCreateFileAndUpdateStaus(OmsCreateFile omsCreateFile,String bussinessId,String type,String pass){
//        OmsCreateFile result = omsCreateFileService.insertOrUpdate(omsCreateFile);
        OmsCreateFile result = leaderDetailProcessingService.insertOrUpadateCreateFileAndUpdateStaus(omsCreateFile,bussinessId,type,pass);

        return Result.success(result);
    }
    @PostMapping("/chuzhangBatchApprove")
    public Result chuzhangBatchApprove(@RequestBody LeaderSupervisionVo leaderSupervisionVo){

        leaderDetailProcessingService.chuzhangAbroadApprovalBatch(leaderSupervisionVo);


        return Result.success();
    }


}
