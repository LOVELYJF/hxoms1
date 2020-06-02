package com.hxoms.modules.omsspecialcasehandling.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsspecialcasehandling.entity.OmsSpecialcasehandling;
import com.hxoms.modules.omsspecialcasehandling.service.OmsSpecialCaseHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 〈新增特殊情况人员〉
     * @Param: [specialcasehandling]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/2 10:56
     */
    @RequestMapping("/insertSpecialCase")
    public Result insertSpecialCase(OmsSpecialcasehandling specialcasehandling){
        specialCaseHandlingService.insertSpecialCase(specialcasehandling);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈通过姓名查询〉
     * @Param: [name]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/2 10:58
     */
    @RequestMapping("/getSpecialCaseByName")
    public Result getSpecialCaseByName(String name){
        OmsSpecialcasehandling omsSpecialcasehandling = specialCaseHandlingService.getSpecialCaseByName(name);
        return Result.success(omsSpecialcasehandling);
    }
    /**
     * 功能描述: <br>
     * 〈修改特殊情况人员〉
     * @Param: [omsSpecialcasehandling]
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/2 11:05
     */
    @RequestMapping("/updateSpecialCase")
    public Result updateSpecialCase(OmsSpecialcasehandling omsSpecialcasehandling){
        specialCaseHandlingService.updateSpecialCase(omsSpecialcasehandling);
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
    @RequestMapping("/deleteSpecialCaseById")
    public Result deleteSpecialCaseById(String id){
        specialCaseHandlingService.deleteSpecialCaseById(id);
        return Result.success();
    }
    /**
     * 功能描述: <br>
     * 〈查询列表〉
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/6/2 11:16
     */
    @RequestMapping("/getAllSpecialCase")
    public Result getAllSpecialCase(){
        Map<String, Object> map = specialCaseHandlingService.getAllSpecialCase();
        return Result.success(map);
    }
}
