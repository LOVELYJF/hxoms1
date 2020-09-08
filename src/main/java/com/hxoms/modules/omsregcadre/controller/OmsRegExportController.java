package com.hxoms.modules.omsregcadre.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.hxoms.common.utils.DomainObjectUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/omsRegExport")
public class OmsRegExportController {
    @Autowired
    private OmsRegProcpersonInfoService mrpinfoService;

    @Autowired
    private OmsRegProcbatchService orpbatchService;

    @Autowired
    private OmsEntryexitRecordService entryexitRecordService;


    /**
     * 下载 省管干部登记备案表
     * @param idStr
     * @throws IOException
     */
    @GetMapping("/exportRfInfo")
    @Transactional(rollbackFor=Exception.class)
    @ApiOperation(value = "下载省管干部登记备案表", notes = "export", produces = "application/octet-stream")
    public void  exportRfInfo(String idStr) throws IOException {

        HttpServletResponse response = DomainObjectUtil.getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=datax.xlsx");
        OutputStream outputStream = response.getOutputStream();

        //指定文件输出位置
        ExcelWriter excelWriter = EasyExcelFactory.getWriter(outputStream);
        //将要输出的内容填充到Sheet里
        Sheet sheet =new Sheet(1,0, ExcelModelORPinfo.class );
        //设置sheet表名
        //sheet.setSheetName("my_excel");
        /**
         * 写数据到Write上下文中
         * 第一个参数：要写入的内容
         * 第二个参数：要写入的sheet目标
         */
        excelWriter.write(createModelList(idStr),sheet);
        excelWriter.finish();
        outputStream.close();
    }


    private List<ExcelModelORPinfo> createModelList (String idStr){
        List<ExcelModelORPinfo> list = new ArrayList<>();
        List<OmsRegProcbatchPerson> orpbplist = new  ArrayList<>();
        //查询登记备案信息根据备案id
        List<OmsRegProcpersoninfo> rflist = mrpinfoService.selectListById(idStr);
        //查询批次相关信息
        OmsRegProcbatch batchinfo = orpbatchService.selectWbaByOrpbatch();
        for(int i=0; i<rflist.size();i++){
            OmsRegProcpersoninfo info = rflist.get(i);
            OmsRegProcbatchPerson batchperson = new OmsRegProcbatchPerson();
            //为批次人员表复制相同字段的数据
            BeanUtils.copyProperties(rflist, batchperson);
            batchperson.setId(UUIDGenerator.getPrimaryKey());
            batchperson.setRfId(info.getId());
            batchperson.setBatchId(batchinfo.getBatchNo());
            orpbplist.add(batchperson);
            //为excel录入数据
            ExcelModelORPinfo excelMode = new ExcelModelORPinfo();
            //登记备案信息录入
            BeanUtils.copyProperties(info, excelMode);
            excelMode.setNo(i);
            //批次相关信息录入
            BeanUtils.copyProperties(batchinfo, excelMode);
            list.add(excelMode);
        }
        int con = orpbatchService.batchinsertInfo(orpbplist);
        if (con > 0){
            //修改批次表备案状态0未备案，1已备案，2已确认
            batchinfo.setStatus("1");
            int con1 = orpbatchService.updateOrpbatch(batchinfo);
            if (con1 > 0){
                mrpinfoService.updateRegProcpersoninfo(idStr);
            }
        }
        return list;
    }





    /**
     * 导出备案大检查列表信息
     * @param year
     * @throws IOException
     */
    @GetMapping("/exportCheckInfo")
    @Transactional(rollbackFor=Exception.class)
    @ApiOperation(value = "导出备案大检查列表信息", notes = "export", produces = "application/octet-stream")
    public void  exportCheckInfo(String year) throws IOException {

        HttpServletResponse response = DomainObjectUtil.getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=datax.xlsx");
        OutputStream outputStream = response.getOutputStream();

        //指定文件输出位置
        ExcelWriter excelWriter = EasyExcelFactory.getWriter(outputStream);
        //将要输出的内容填充到Sheet里
        Sheet sheet =new Sheet(1,0, ExcelCheckModelORPinfo.class );
        //设置sheet表名
        //sheet.setSheetName("my_excel");
        /**
         * 写数据到Write上下文中
         * 第一个参数：要写入的内容
         * 第二个参数：要写入的sheet目标
         */
        excelWriter.write(createCheckModelList(year),sheet);
        excelWriter.finish();
        outputStream.close();
    }
    private List<ExcelCheckModelORPinfo> createCheckModelList (String year){
        //查询年度大检查列表根据年度
        List<ExcelCheckModelORPinfo> list = mrpinfoService.selectCheckModelList(year);
        return list;
    }


    /**
     * 导入出入境记录
     * @param idStr
     * @throws IOException
     */
    @PostMapping("/exportZzCrjInfo")
    public void  exportZzCrjInfo(String idStr) throws IOException {

        HttpServletResponse response = DomainObjectUtil.getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "utf-8");
        OutputStream outputStream = response.getOutputStream();

        //指定文件输出位置
        ExcelWriter excelWriter = EasyExcelFactory.getWriter(outputStream);
        //将要输出的内容填充到Sheet里
        Sheet sheet =new Sheet(1,0, ExcelModelORPinfo.class );
        //设置sheet表名
        //sheet.setSheetName("my_excel");
        /**
         * 写数据到Write上下文中
         * 第一个参数：要写入的内容
         * 第二个参数：要写入的sheet目标
         */
        excelWriter.write(exportEntryexitRecord(idStr),sheet);
        excelWriter.finish();
        outputStream.close();
    }


    /**
     * 导出出入境记录
     * @param idStr
     */
    private List exportEntryexitRecord(String idStr) {
        OmsEntryexitRecordIPagParam entryexitRecordIPagParam = new OmsEntryexitRecordIPagParam();
        entryexitRecordIPagParam.setIds(idStr);
        List<OmsEntryexitRecord> entryexitRecordsList =  entryexitRecordService.newexitRecordsList(entryexitRecordIPagParam);
        return entryexitRecordsList;
    }


}









