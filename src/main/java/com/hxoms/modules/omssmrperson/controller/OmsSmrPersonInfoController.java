package com.hxoms.modules.omssmrperson.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import com.hxoms.modules.omssmrperson.service.OmsSmrPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 涉密人员信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@RestController
@RequestMapping("/smrPersonInfo")
public class OmsSmrPersonInfoController {

    @Autowired
    private OmsSmrPersonInfoService smrPersonInfoService;

    private HttpServletResponse response;

    /**
     * 获取涉密人员基本信息列表
     * @param pageNum
     * @param pageSize
     * @param smrPersonInfo
     */
    @GetMapping("/getSmrPersonInfo")
    public Result getSmrPersonInfo(Integer pageNum, Integer pageSize, @RequestParam(value = "idList",required = false) List<String> idList,
                                   OmsSmrPersonInfo smrPersonInfo) {
        try{
            Map<String, Object> resultMap = smrPersonInfoService.getSmrPersonInfo(pageNum, pageSize,idList,smrPersonInfo);
            return Result.success(resultMap);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 导入涉密人员信息
     * @param smrPersonInfoList
     */
    @PostMapping("/insertSmrPersonInfo")
    public Result insertSmrPersonInfo(String importYear, String b0100,List<OmsSmrPersonInfo> smrPersonInfoList) {
        return Result.success(smrPersonInfoService.insertSmrPersonInfo(importYear,b0100,smrPersonInfoList));
    }

   /**
     * 修改涉密人员信息
     * @param smrPersonInfo
     */
    @PostMapping("/updateSmrPersonInfo")
    public Result updateSmrPersonInfo(OmsSmrPersonInfo smrPersonInfo) {
        return Result.success(smrPersonInfoService.updateSmrPersonInfo(smrPersonInfo));
    }

    /**
     * 删除涉密人员信息
     * @param id
     */
    @PostMapping("/deleteSmrPersonInfo")
    public Result deleteSmrPersonInfo(String id) {
        return Result.success(smrPersonInfoService.deleteSmrPersonInfo(id));
    }

    /** 上传涉密人员数据
     * @param
     */
    @PostMapping("/uploadSmrExcel")
    public Result uploadSmrExcel(MultipartFile file, String importYear, String b0100) {
        try{
            Map<String, Object> resultMap = smrPersonInfoService.uploadSmrExcel(file,importYear,b0100);
            return Result.success(resultMap);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导入失败");
        }
    }

    /**
     * 导出涉密人员基本信息
     * @param idList
     * @param smrPersonInfo
     */
    @PostMapping("/exportSmrPersonInfo")
    public Result exportSmrPersonInfo(@RequestParam(value = "idList",required = false) List<String> idList, OmsSmrPersonInfo smrPersonInfo){
        try{
            boolean result = smrPersonInfoService.exportSmrPersonInfo(idList, smrPersonInfo, this.response);;
            return Result.success(result);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导出失败");
        }
    }

    /**
     * 获取漏报涉密人员机构
     * @return
     */
    @GetMapping("/getFailReportOrg")
    public Result getFailReportOrg(){
        try{
            List<String> list= smrPersonInfoService.getFailReportOrg();;
            return Result.success(list);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 导出漏报涉密人员机构
     * @return
     */
    @PostMapping("/exportFailReportOrg")
    public Result exportFailReportOrg(){
        try{
            boolean result = smrPersonInfoService.exportFailReportOrg(response);;
            return Result.success(result);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导出失败");
        }
    }

    /**
     * 获取差异数据列表
     * @return
     */
    @GetMapping("/getDifferentData")
    public Result getDifferentData(){
        try{
            List<OmsSmrPersonInfo> list= smrPersonInfoService.getDifferentData();;
            return Result.success(list);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 导出差异数据列表
     * @return
     */
    @PostMapping("/exportDifferentData")
    public Result exportDifferentData(){
        try{
            boolean result = smrPersonInfoService.exportDifferentData(response);;
            return Result.success(result);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导出失败");
        }
    }

    /**
     * 获取脱密期确认列表
     */
    @GetMapping("/getConfirmPeriodList")
    public Result getConfirmPeriodList() {
        try{
            Map<String, Object> resultMap = smrPersonInfoService.getConfirmPeriodList();
            return Result.success(resultMap);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 批量修改涉密人员信息（确认脱密期、涉密人员信息维护）
     * @param smrPersonInfoList
     */
    @PostMapping("/updateSmrPersonList")
    public Result updateSmrPersonList(List<OmsSmrPersonInfo> smrPersonInfoList) {
        try {
            boolean result = smrPersonInfoService.updateSmrPersonList(smrPersonInfoList);
            return Result.success(result);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 获取涉密人员信息维护列表
     */
    @GetMapping("/getSmrMaintainList")
    public Result getSmrMaintainList(OmsSmrPersonInfo smrPersonInfo) {
        try{
            Map<String, Object> resultMap = smrPersonInfoService.getSmrMaintainList(smrPersonInfo);
            return Result.success(resultMap);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }
}









