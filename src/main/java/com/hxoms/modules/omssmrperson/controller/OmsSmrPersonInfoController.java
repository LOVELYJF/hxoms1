package com.hxoms.modules.omssmrperson.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import com.hxoms.modules.omssmrperson.service.OmsSmrPersonInfoService;
import com.hxoms.modules.omssmrperson.service.OmsSmrRecordInfoService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
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

    /** 导入涉密人员数据
     * @param
     */
    @PostMapping("/uploadSmrExcel")
    public Result uploadSmrExcel(String filePath, String importYear, String B0100) {
        try{
            String userId = UserInfoUtil.getUserId();
            String result = smrPersonInfoService.uploadSmrExcel(filePath,importYear,B0100,userId);
            return Result.success(result);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导入失败");
        }
    }

    /**
     * 导出涉密人员基本信息
     * @param list
     */
    @PostMapping("/exportSmrPersonInfo")
    public Result exportSmrPersonInfo(@RequestBody(required = false) List<OmsSmrPersonInfo> list){
        try{
            boolean result = smrPersonInfoService.exportSmrPersonInfo(list,response);;
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
}









