package com.hxoms.modules.leaderSupervision.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.service.LeaderCommonService;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.service.VerifyCheckService;
import com.hxoms.modules.leaderSupervision.service.impl.LeaderEXportExcelService;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
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
 * @Description:
 ***/
@RestController
@RequestMapping("/verifyCheck")
public class VerifyCheckController {

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



    /**批件核实查询列表**/
    @GetMapping("/selectVerifyCheckList")
    public Result selectVerifyCheckList(LeaderSupervisionVo leaderSupervisionVo){


        PageInfo pageInfo = leaderCommonService.selectInstructionsVerify(leaderSupervisionVo);

        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }
    // 组团 单位查询条件
    @GetMapping("/selectGroupConditions")
    public Result selectGroupConditions(){

        Set<Map> lists =new HashSet<>(leaderDetailProcessingService.selectGroupConditions());

        return Result.success(lists!=null?lists:"");
    }

    /**
     * 附件下载
     *
     * **/
    @GetMapping ("/downloadAttachmentById")
    public ResponseEntity<byte[]> downloadAttachmentById(String id){

        if(StringUilt.stringIsNullOrEmpty(id)){

            //如果id 不存在  查询与列表保持一致的 批文id
          Map map =  leaderCommonMapper.selectDefaultId();
            id =  map.get("defaultId").toString();
        }

        Map map = leaderDetailProcessingService.downloadAttachmentById(id);

        ResponseEntity<byte[]> responseEntity=null;
        try {

            responseEntity=ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode(map.get("fileName").toString(), "utf-8")))
                    .body((byte[]) map.get("array"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomMessageException("下载失败，原因："+e.getMessage());
        }
        return responseEntity;



    }

    @PostMapping("/verifyCheckApprove")
    public Result verifyCheckApprove(LeaderSupervisionVo leaderSupervisionVo){


        verifyCheckService.verifyCheckApprove(leaderSupervisionVo);

        return Result.success();


    }

    //生成备案表 查询流程列表
    @GetMapping("/putOnRecordsTable")
    public Result putOnRecordsTable(LeaderSupervisionVo leaderSupervisionVo){

        PageInfo pageInfo =  leaderDetailProcessingService.createPutOnRecordList(leaderSupervisionVo,null);


        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());

    }

    /**
     *  生成 人员备案表
     * **/

    @PostMapping("/createPutOnRecord")
    public Result createPutOnRecord(@RequestBody LeaderSupervisionVo leaderSupervisionVo){

        List<Map> lists= leaderDetailProcessingService.createPutOnRecord(leaderSupervisionVo);

        return Result.success(lists);
    }

    /**
     * 保存或者更新(人员备案表)
     *
     */
    @PostMapping("/insertOrUpdatePutOnRecordFile")
    public Result insertOrUpdatePutOnRecordFile(OmsCreateFile omsCreateFile,String userName){
//        OmsCreateFile result = omsCreateFileService.insertOrUpdate(omsCreateFile);
        OmsCreateFile result = leaderDetailProcessingService.insertOrUpdatePutOnRecordFile(omsCreateFile,userName);

        return Result.success(result);
    }

    /**
     * 批量下载 人员备案表
     * **/
    @PostMapping("/batchDownloadPutOnRecord")
    public ResponseEntity<byte[]> batchDownloadPutOnRecord(@RequestBody LeaderSupervisionVo leaderSupervisionVo ){


        Map map = leaderDetailProcessingService.batchDownloadPutOnRecord(leaderSupervisionVo);

        ResponseEntity<byte[]> responseEntity=null;
        try {

            responseEntity=ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode(map.get("fileName").toString(), "utf-8")))
                    .body((byte[]) map.get("array"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomMessageException("下载失败，原因："+e.getMessage());
        }
        return responseEntity;
    }

    /** 导出人员备案表 列表信息 ***/
    @PostMapping("exportPutOnRecord")
    public void exportPutOnRecord(@RequestBody LeaderSupervisionVo leaderSupervisionVo,HttpServletResponse response){



        try {
            HSSFWorkbook wb = leaderEXportExcelService.putonRecordExport(leaderSupervisionVo);
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
