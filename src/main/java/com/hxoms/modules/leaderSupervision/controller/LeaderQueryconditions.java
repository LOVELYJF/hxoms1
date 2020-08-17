package com.hxoms.modules.leaderSupervision.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.service.impl.LeaderEXportExcelService;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import com.hxoms.modules.publicity.taskSupervise.entity.FileInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/8/5 14:47  干部监督处查询 条件
 * @Description:
 ***/
@RestController
@RequestMapping("/leaderQueryconditions")
public class LeaderQueryconditions {
    @Autowired
    private LeaderDetailProcessingService leaderDetailProcessingService;
    @Autowired
    private LeaderEXportExcelService leaderEXportExcelService;

    /**
     * 查询申请 类型
     * **/
    @GetMapping("/bussinessType")
    public Result applicationType(){

      List<Map> map =  leaderDetailProcessingService.getApplicationType();

       return Result.success(map);

    }


    /**
     * 查询批次列表
     * **/
    @GetMapping("/selectBatch")
    public Result selectBatch(LeaderSupervisionVo leaderSupervisionVo){


        PageInfo pageInfo = leaderDetailProcessingService.selectOmsLeaderBatch(leaderSupervisionVo);

        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }


    /**
     * 修改 批次
     * **/
    @PostMapping("/updateBatch")
    public Result updateBatch(OmsLeaderBatch omsLeaderBatch){

        leaderDetailProcessingService.updateLeaderBatch(omsLeaderBatch);


        return Result.success();
    }

    /**
     * 获取 批次状态
     * ***/
    @GetMapping("/selectLeaderBatchStatus")
    public Result selectLeaderBatchStatus(){

       List<Map> mapList =  leaderDetailProcessingService.selectLeaderBatchStatus();

        return  Result.success(mapList);
    }

    /**
     *业务 处理 材料审核 的 (最后一个) 下一步 触发的事件
     * **/
    @PostMapping("/selectLeaderBatchStatus")
    public Result materialReviewNextStep(String applyId,String tableCode){

        leaderDetailProcessingService.materialReviewNextStep(applyId,tableCode);

        return Result.success();

    }

    /**
     *
     * 征求 纪委意见 保存附件
     * @param files  附件
     * @param leaderBatchIds  所选择的批次
     *
     * **/
    @PostMapping("/officialJiweiOpinion")
    public Result officialJiweiOpinion(@RequestParam("file") MultipartFile[] files,String[] leaderBatchIds, HttpServletRequest request){

        leaderDetailProcessingService.fileUpload(files,leaderBatchIds,"干部监督处",Constants.leader_business[2],Constants.leader_businessName[2],request);

        return Result.success();
    }

    /**
     * 征求纪委意见 查询条件
     * **/
    @GetMapping("/selectMaterialStatus")
    public Result selectMaterialStatus(){

      List<Map> lists=  leaderDetailProcessingService.selectMaterialStatus();

      return Result.success(lists);

    }

    /**
     * 查看 附件列表
     * **/
    @GetMapping("/selectAttachmentList")
    public Result selectAttachmentList(String[] leaderBatchIds){

     List<Map> lists =   leaderDetailProcessingService.selectAttachmentList(leaderBatchIds);

     return Result.success(lists);
    }
    /**
     * 附件下载
     *
     * **/
    @GetMapping ("/downloadAttachmentById")
    public ResponseEntity<byte[]> downloadAttachmentById(String id){



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

    /**
     * 纪委意见导出
     * **/
    @PostMapping("/jiweiWriteApplyExport")
    public void jiweiWriteApplyExport(@RequestBody LeaderSupervisionVo leaderSupervisionVo , HttpServletResponse response){

        try {
            HSSFWorkbook wb = leaderEXportExcelService.jiweiWriteApplyExport(leaderSupervisionVo);
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
