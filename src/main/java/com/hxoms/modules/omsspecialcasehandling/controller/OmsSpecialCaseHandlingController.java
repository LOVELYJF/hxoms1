package com.hxoms.modules.omsspecialcasehandling.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsspecialcasehandling.entity.OmsSpecialcasehandling;
import com.hxoms.modules.omsspecialcasehandling.service.OmsSpecialCaseHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈特殊情况处理〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/6/2 10:35
 */
@RestController
@RequestMapping("/specialCase")
public class OmsSpecialCaseHandlingController {

    @Autowired
    private OmsSpecialCaseHandlingService specialCaseHandlingService;
    /**
     * 功能描述: <br>
     * 〈新增或修改特殊情况人员〉
     * @Param: [specialcasehandling]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/2 10:56
     */
    @PostMapping("/insertOrUpdateSpecialCase")
    public Result insertOrUpdateSpecialCase(OmsSpecialcasehandling specialCaseHandling){
        specialCaseHandlingService.insertOrUpdateSpecialCase(specialCaseHandling);
        return Result.success();
    }

    /**
     * 功能描述: <br>
     * 〈通过ID删除特殊情况人员〉
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/2 11:10
     */
    @PostMapping("/deleteSpecialCaseById")
    public Result deleteSpecialCaseById(String id){
        specialCaseHandlingService.deleteSpecialCaseById(id);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈获取特殊人员列表〉
     * @Param: [pageNum, pageSize, keyWord]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/22 16:01
     */
    @GetMapping("/getAllSpecialCase")
    public Result getAllSpecialCase(Integer pageNum, Integer pageSize, String keyWord){
        PageInfo pageInfo = specialCaseHandlingService.getAllSpecialCase(pageNum,pageSize,keyWord);
        return Result.success(pageInfo.getList()).setTotal(pageInfo.getTotal());
    }
}
