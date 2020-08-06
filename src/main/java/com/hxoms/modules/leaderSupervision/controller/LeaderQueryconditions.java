package com.hxoms.modules.leaderSupervision.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.leaderSupervision.service.LeaderDetailProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public Result selectBatch(){


        return Result.success();
    }


    /**
     * 修改 批次
     * **/
    public Result updateBatch(){


        return Result.success();
    }




}
