package com.hxoms.modules.omssmrperson.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 涉密人员原涉密信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@RestController
@RequestMapping("/smrOldInfo")
public class OmsSmrOldInfoController {

    @Autowired
    private OmsSmrOldInfoService smrOldInfoService;

    /**
     * 获取涉密人员原涉密信息列表
     * @param id(登记备案人员主键)
     */
    @GetMapping("/getSmrOldInfoById")
    public Result getSmrOldInfoById(Integer pageNum, Integer pageSize,String id) {
        try{
            PageInfo<OmsSmrOldInfoVO> oldInfoList = smrOldInfoService.getSmrOldInfoById(pageNum,pageSize,id);
            return Result.success(oldInfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 获取脱密期确认列表
     */
    @GetMapping("/getConfirmPeriodList")
    public Result getConfirmPeriodList() {
        try{
            Map<String, Object> resultMap = smrOldInfoService.getConfirmPeriodList();
            return Result.success(resultMap);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }
    /**
     * 获取涉密人员信息维护列表
     */
    @GetMapping("/getSmrMaintainList")
    public Result getSmrMaintainList() {
        try{
            Map<String, Object> resultMap = smrOldInfoService.getSmrMaintainList();
            return Result.success(resultMap);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 批量修改涉密人员信息（确认脱密期、涉密人员信息维护）
     * @param SmrOldInfo
     */
    @PostMapping("/updateSmrOldInfos")
    public Result updateSmrOldInfos(String SmrOldInfo) {
        try{
            List<OmsSmrOldInfoVO> smrOldInfos = JSONArray.parseArray(SmrOldInfo,OmsSmrOldInfoVO.class);
            Result result = smrOldInfoService.updateSmrOldInfo(smrOldInfos);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /**
     * 获取差异数据列表
     * @param importYear（导入年份）
     * @param b0100（单位id）
     */
    @GetMapping("/getDifferentData")
    public Result getDifferentData(String importYear, String b0100){
        try{
            Result result = smrOldInfoService.getDifferentData(importYear,b0100);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 导出差异数据列表
     * @param jsonParam( importYear（导入年份）,b0100（单位id）)
     */
    @PostMapping("/exportDifferentData")
    public void exportDifferentData(@RequestBody String jsonParam,@ApiIgnore HttpServletResponse response){
        try{
            smrOldInfoService.exportDifferentData(jsonParam, response);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 差异数据纠正确认（差异数据纠正--同一人）
     * @param SmrOldInfo
     */
    @PostMapping("/confirmDifferentData")
    public Result confirmDifferentData(String SmrOldInfo) {
        try{
            List<OmsSmrOldInfoVO> smrOldInfos = JSONArray.parseArray(SmrOldInfo,OmsSmrOldInfoVO.class);
            Result result = smrOldInfoService.updateDifferentData(smrOldInfos);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }
    /**
     * @description:根据人员ID获取未过脱密期的涉密信息
     * @author:杨波
     * @date:2020-10-10
     *  * @param A0100 人员ID
     * @return:com.hxoms.common.utils.Result
     **/
    @GetMapping("/getSmrOldInfoByA0100")
    public Result getSmrOldInfoByA0100(String A0100){

       return smrOldInfoService.getSmrOldInfoByA0100(A0100);
    }
}









