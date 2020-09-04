package com.hxoms.modules.leaderSupervision.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.modules.leaderSupervision.mapper.LeaderCommonMapper;
import com.hxoms.modules.leaderSupervision.service.LeaderCommonService;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.service.VerifyCheckService;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

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



    /**批件核实查询列表**/
    @GetMapping("/selectVerifyCheckList")
    public Result selectVerifyCheckList(LeaderSupervisionVo leaderSupervisionVo){


        PageInfo pageInfo = leaderCommonService.selectInstructionsVerify(leaderSupervisionVo);

        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }
    // 组团 单位查询条件
    @GetMapping("/selectGroupConditions")
    public Result selectGroupConditions(LeaderSupervisionVo leaderSupervisionVo){

        List<Map> lists =leaderCommonMapper.selectGroupConditions();

        return Result.success(lists);
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

//    public Reuslt


}
