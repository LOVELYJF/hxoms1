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


    // 代办业务管理模块 左边列表
    @GetMapping("/selectLeaderBussinessProcess")
    public Result selectLeaderBussinessProcess(){

        List<Map> businessFlowWithASNum = leaderCommonMapper.selectBusinessFlowWithASNum();


        return Result.success(businessFlowWithASNum);

    }

    // 批次列表
    @GetMapping("/selectBatchlist")
    public Result  selectBatchlist(){


        List<Map>  lists  =  leaderCommonMapper.selectLeaderBatch();


       return Result.success(lists);

    }





}
