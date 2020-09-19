package com.hxoms.modules.leaderSupervision.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.service.LeaderCommonService;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.service.LeaderOtherStatisticalQueryService;
import com.hxoms.modules.leaderSupervision.service.VerifyCheckService;
import com.hxoms.modules.leaderSupervision.service.impl.LeaderEXportExcelService;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @authore:wjf
 * @data 2020/9/3 11:36
 * @Description:  干部监督处 流程 以外的统计查询功能
 ***/
@RestController
@RequestMapping("/leaderOtherStatisticalQuery")
public class LeaderOtherStatisticalQueryController {

    @Autowired
    private LeaderCommonService leaderCommonService;
    @Autowired
    private LeaderCommonMapper leaderCommonMapper;

    @Autowired
    private VerifyCheckService verifyCheckService;

    @Autowired
    private LeaderDetailProcessingService leaderDetailProcessingService;

    @Autowired
    private LeaderEXportExcelService leaderEXportExcelService;
    @Autowired
    private LeaderOtherStatisticalQueryService leaderOtherStatisticalQueryService;


    // 代办业务管理模块 左边列表
    @GetMapping("/selectLeaderBussinessProcess")
    public Result selectLeaderBussinessProcess(){

        List<Map> businessFlowWithASNum = leaderCommonMapper.selectBusinessFlowWithASNum();


        return Result.success(businessFlowWithASNum);

    }

    // 批次列表 横向
    @GetMapping("/selectBatchlist")
    public Result  selectBatchlist(){


        List<Map>  lists  =  leaderCommonMapper.selectLeaderBatch();


       return Result.success(lists);

    }

    //批次列表 纵向
    @GetMapping("/selectBatchlistShapePortrait")
    public Result selectBatchlistShapePortrait(){

      Map map =  leaderOtherStatisticalQueryService.selectBatchlistShapePortrait();

      return Result.success(map);
    }


    /**
     *登记备案导出
     *
     * **/

    @PostMapping("/exportRfInfo")
    public void exportRfInfo(HttpServletResponse response,@RequestBody String idStr){
        try {
            HSSFWorkbook wb = leaderEXportExcelService.exportRfInfo(idStr);
            String date = new SimpleDateFormat("yyyy-MM-dd")
                    .format(new Date());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode("登记备案信息"+date+".xls", "utf-8")));
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomMessageException("导出失败，原因："+e.getMessage());
        }
    }


    /** 查询因私出国境申请管理**/

    @GetMapping("/selectAllOmsPriApplyManange")
    public Result selectAllOmsPriApplyManange(OmsPriApplyIPageParam omsPriApplyIPageParam){

      PageInfo pageInfo =  leaderOtherStatisticalQueryService.selectAllOmsPriApplyManange(omsPriApplyIPageParam);


        return Result.success(pageInfo);
    }

    /**因私出国境申请管理 导出 **/

    @GetMapping("/exportAllOmsPriApplyManange")
    public void exportAllOmsPriApplyManange(HttpServletResponse response,OmsPriApplyIPageParam omsPriApplyIPageParam){
        try {
            HSSFWorkbook wb = leaderEXportExcelService.exportAllOmsPriApplyManange(omsPriApplyIPageParam);
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



}
