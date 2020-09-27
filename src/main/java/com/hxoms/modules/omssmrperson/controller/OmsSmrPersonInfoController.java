package com.hxoms.modules.omssmrperson.controller;

import com.alibaba.fastjson.JSONObject;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import com.hxoms.modules.omssmrperson.service.OmsSmrPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
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

    /**
     * 获取涉密人员基本信息列表
     * @param pageNum
     * @param pageSize
     * @param smrPersonInfo
     */
    @PostMapping("/getSmrPersonInfo")
    public Result getSmrPersonInfo(Integer pageNum, Integer pageSize,
                                   OmsSmrPersonInfo smrPersonInfo,
                                   @RequestParam(value = "idList",required = false) String idList) {
        try{
            return Result.success(smrPersonInfoService.getSmrPersonInfo(pageNum, pageSize,idList,smrPersonInfo));
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 导入涉密人员信息
     * @param importYear
     * @param b0100
     * @param smrPersonInfo
     */
    @PostMapping("/insertSmrPersonInfo")
    public Result insertSmrPersonInfo(String importYear, String b0100,
                                      String smrPersonInfo) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException, InvocationTargetException {
        List<OmsSmrOldInfoVO> smrPersonInfoList= JSONObject.parseArray (smrPersonInfo,OmsSmrOldInfoVO.class);
        return smrPersonInfoService.insertSmrPersonInfo(importYear,b0100,smrPersonInfoList);
    }

   /**
     * 修改涉密人员信息
     * @param smrPersonInfo
     */
    @PostMapping("/updateSmrPersonInfo")
    public Result updateSmrPersonInfo(@RequestBody OmsSmrPersonInfo smrPersonInfo) {
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
    public Result uploadSmrExcel(@RequestBody MultipartFile file, String importYear, String b0100) {
        try{
            return smrPersonInfoService.uploadSmrExcel(file,importYear,b0100);
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
    public void exportSmrPersonInfo(@RequestParam(value = "idList",required = false) String idList,
                                                       OmsSmrPersonInfo smrPersonInfo,
                                                       @ApiIgnore HttpServletResponse response){
            smrPersonInfoService.exportSmrPersonInfo(idList, smrPersonInfo,response);
    }

    /**
     * 获取漏报涉密人员机构
     * @param importYear
     */
    @GetMapping("/getFailReportOrg")
    public Result getFailReportOrg(String importYear){
        try{
            Result result = smrPersonInfoService.getFailReportOrg(importYear);;
            return Result.success(result);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 导出漏报涉密人员机构
     * @param importYear
     */
    @PostMapping("/exportFailReportOrg")
    public void exportFailReportOrg(String importYear){
        try{
            smrPersonInfoService.exportFailReportOrg(importYear);
        }catch (Exception e) {
            e.printStackTrace();
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
            boolean result = smrPersonInfoService.exportDifferentData();
            return Result.success(result);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导出失败");
        }
    }

    /**
     * 批量修改涉密人员信息（确认脱密期、涉密人员信息维护）
     * @param smrPersonInfoList
     */
    @PostMapping("/updateSmrPersonList")
    public Result updateSmrPersonList(@RequestBody List<OmsSmrPersonInfo> smrPersonInfoList) {
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
    public Result getSmrMaintainList(@RequestBody OmsSmrPersonInfo smrPersonInfo) {
        try{
            Map<String, Object> resultMap = smrPersonInfoService.getSmrMaintainList(smrPersonInfo);
            return Result.success(resultMap);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }
}









