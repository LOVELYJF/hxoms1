package com.hxoms.modules.omsregcadre.controller;

import com.alibaba.excel.EasyExcel;

import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateHistoryRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/omsRegExport")
public class OmsRegExportController {
    @Autowired
    private OmsRegProcpersonInfoService mrpinfoService;

    @Autowired
    private OmsEntryexitRecordService entryexitRecordService;

    @Autowired
    private CfCertificateHistoryRecordService cfHistoryRecordService;

    /**
     * 导出备案大检查列表信息
     * @param year
     * @throws IOException
     */
    @PostMapping ("/exportCheckInfo")
    @Transactional(rollbackFor=Exception.class)
    @ApiOperation(value = "导出备案大检查列表信息", notes = "export", produces = "application/octet-stream")
    public void  exportCheckInfo(String year){
        HttpServletResponse response = DomainObjectUtil.getResponse();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<ExcelCheckModelORPinfo> list = mrpinfoService.selectCheckModelList(year);
            //导出
            String fileName = UtilDateTime.nowDate()+"备案大检查";
            //设置输出的格式
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition","attachment;filename="
                    + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            EasyExcel.write(response.getOutputStream(), ExcelCheckModelORPinfo.class)
                    .sheet(fileName)
                    .doWrite(list);
        } catch (IOException e) {
            resultMap.put("code", "2");
            resultMap.put("msg", "操作失败");
            e.printStackTrace();
        }
    }





    /**
     * 导出出入境记录
     * @param ids
     * @throws IOException
     */
    @PostMapping("/exportZzCrjInfo")
    public void  exportZzCrjInfo(@RequestBody List<String> ids){
        HttpServletResponse response = DomainObjectUtil.getResponse();
        String content = "导入出入境记录";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<OmsEntryexitRecordModel> entryexitRecordsList =  entryexitRecordService.newexitRecordsList(ids);
            //导出
            String fileName = UtilDateTime.nowDate()+"出入境记录";
            //设置输出的格式
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition","attachment;filename="
                    + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            EasyExcel.write(response.getOutputStream(), OmsEntryexitRecordModel.class)
                    .sheet("模板")
                    .doWrite(entryexitRecordsList);
        } catch (IOException e) {
            resultMap.put("code", "2");
            resultMap.put("msg", "操作失败");
            e.printStackTrace();
        }
    }




    /**
     * 登记备案信息浏览导出
     * @param idStr
     * @throws IOException
     */
    @PostMapping("/exportLookInfo")
    @Transactional(rollbackFor=Exception.class)
    @ApiOperation(value = "导出登记备案信息浏览", notes = "export", produces = "application/octet-stream")
    public void exportLookInfo(String idStr)throws IOException {
        HttpServletResponse response = DomainObjectUtil.getResponse();
        String content = "导出登记备案信息浏览";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //查询登记备案信息根据备案id
            List<OmsRegProcpersoninfo> rflist = mrpinfoService.selectListById(idStr);
            //导出
            String fileName = UtilDateTime.nowDate() + "登记备案信息浏览导出";
            // 设置输出的格式
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            EasyExcel.write(response.getOutputStream(), ExcelModelORPinfo.class)
                    .sheet(fileName)
                    .doWrite(rflist);
        } catch (IOException e) {
            resultMap.put("code", "2");
            resultMap.put("msg", "操作失败");
            e.printStackTrace();
        }
    }


    /**
     * 导出证照比对历史记录
     * @param type
     * @throws IOException
     */
    @PostMapping("/exportZzHistoryInfo")
    @Transactional(rollbackFor=Exception.class)
    @ApiOperation(value = "导出证照比对历史记录", notes = "export", produces = "application/octet-stream")
    public void exportZzHistoryInfo(String year,String type){
        HttpServletResponse response = DomainObjectUtil.getResponse();
        String content = "导出年度问题证照记录";
        String fileName ="";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<CfCertificateHistoryRecordModel> entryexitRecordsList = new ArrayList<>();
        try {
            if (type.equals("1")){
                entryexitRecordsList = cfHistoryRecordService.selectNotProvicdeCerRecords(year);
                fileName = UtilDateTime.nowDate()+"导出"+year+"年度未上缴证照记录";
            }else{
                entryexitRecordsList = cfHistoryRecordService.selectExceptionCerRecords(year);
                fileName = UtilDateTime.nowDate()+"导出"+year+"年度存疑证照记录";
            }
            //设置输出的格式
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition","attachment;filename="
                    + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            EasyExcel.write(response.getOutputStream(), CfCertificateHistoryRecordModel.class)
                    .sheet("模板")
                    .doWrite(entryexitRecordsList);
        } catch (IOException e) {
            resultMap.put("code", "2");
            resultMap.put("msg", "操作失败");
            e.printStackTrace();
        }
    }
}









