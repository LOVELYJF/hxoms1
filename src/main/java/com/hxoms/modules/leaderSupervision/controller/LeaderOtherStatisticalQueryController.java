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
import com.hxoms.modules.leaderSupervision.vo.JiweiStatisticsVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.publicity.entity.OmsPubApplyQueryParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    public void exportRfInfo(HttpServletResponse response,@RequestBody String idStr) throws IOException {
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
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            ServletOutputStream out = response.getOutputStream();
            OutputStreamWriter ow=new OutputStreamWriter(out,"UTF-8");
            String msg="导出失败，原因："+e.getMessage();
            ow.write(msg);
            ow.flush();
            ow.close();
            out.flush();
            out.close();
        }
    }


    /** 查询因私出国境申请管理**/

    @GetMapping("/selectAllOmsPriApplyManange")
    public Result selectAllOmsPriApplyManange(OmsPriApplyIPageParam omsPriApplyIPageParam){

      PageInfo pageInfo =  leaderOtherStatisticalQueryService.selectAllOmsPriApplyManange(omsPriApplyIPageParam);


        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }

    /**因私出国境申请管理 导出 **/

    @PostMapping("/exportAllOmsPriApplyManange")
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

    /** 因公出国境申请管理 ***/
    @GetMapping("/selectAllOmsPubApplyManange")
    public Result selectAllOmsPubApplyManange(OmsPubApplyQueryParam omsPubApplyQueryParam){

        PageInfo pageInfo =  leaderOtherStatisticalQueryService.selectAllOmsPubApplyManange(omsPubApplyQueryParam);

        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }

    /** 因公出国境申请管理 终止备案功能 **/
    @PostMapping("/terminationPutOnRecords")
    public Result terminationPutOnRecords(@RequestBody LeaderSupervisionVo leaderSupervisionVo){


        leaderOtherStatisticalQueryService.terminationPutOnRecords(leaderSupervisionVo);

        return Result.success();
    }

    /** 延期出国境(申请)管理 **/

    @GetMapping("/selectOmsDelayApplyIPage")
    public Result selectAllOmsPriDelayApplyManage(OmsPriApplyIPageParam omsPriApplyIPageParam){

        PageInfo pageInfo =  leaderOtherStatisticalQueryService.selectAllOmsPriDelayApplyManage(omsPriApplyIPageParam);

        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());

    }

    /** 延期出国境(申请)管理 导出 **/

    @PostMapping("/exportAllOmsPriDelayApplyManange")
    public void exportAllOmsPriDelayApplyManange(HttpServletResponse response,OmsPriApplyIPageParam omsPriApplyIPageParam){

        try {
            HSSFWorkbook wb = leaderEXportExcelService.exportAllOmsPriDelayApplyManange(omsPriApplyIPageParam);
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

    /** 征求纪委意见 统计查询**/
    @GetMapping("/jiweiOpionStatisticsQuery")
    public Result jiweiOpionStatisticsQuery(JiweiStatisticsVo JiweiStatisticsVo){

      List<Map> lists =    leaderCommonMapper.selectjieweiOpinionCase(JiweiStatisticsVo);

        return Result.success(lists);
    }


    /**征求纪委意见 明细查询 **/
    @GetMapping("/jiweiOpionDetailStatisticsQuery")
    public Result jiweiOpionDetailStatisticsQuery(JiweiStatisticsVo jiweiStatisticsVo){

//         Thread t = new Thread();
      PageInfo pageInfo =  leaderOtherStatisticalQueryService.selectjieweiOpinionDetail(jiweiStatisticsVo);

        return  Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }





}
