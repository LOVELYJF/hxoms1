package com.hxoms.modules.leaderSupervision.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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



}
