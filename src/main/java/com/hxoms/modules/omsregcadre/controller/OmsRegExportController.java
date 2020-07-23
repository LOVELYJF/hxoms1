package com.hxoms.modules.omsregcadre.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.hxoms.common.utils.DomainObjectUtil;
import com.hxoms.modules.omsregcadre.entity.ExcelModelORPinfo;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping("/exportRfInfo")
    public void  exportRfInfo(String idStr) throws IOException {

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
        excelWriter.write(createModelList(idStr),sheet);
        excelWriter.finish();
        outputStream.close();
    }


    private List<ExcelModelORPinfo> createModelList (String idStr){
        List<ExcelModelORPinfo> list = new ArrayList<>();
        //查询登记备案信息根据备案id
        List<ExcelModelORPinfo> rflist = mrpinfoService.selectListById(idStr);
        //查询批次相关信息
        ExcelModelORPinfo batchinfo = orpbatchService.selectWbaByOrpbatch();
        for(int i=0; i<rflist.size();i++){
            ExcelModelORPinfo info = rflist.get(i);
            ExcelModelORPinfo excelMode = new ExcelModelORPinfo();
            //登记备案信息录入
            excelMode.setNo(i);
            excelMode.setSurname(info.getSurname());
            excelMode.setName(info.getName());
            excelMode.setSex(info.getSex());
            excelMode.setIdnumberGb(info.getIdnumberGb());
            excelMode.setRegisteResidenceCode(info.getRegisteResidenceCode());
            excelMode.setInboundFlag(info.getInboundFlag());
            excelMode.setWorkUnit(info.getWorkUnit());
            excelMode.setPostCode(info.getPostCode());
            excelMode.setPersonManager(info.getPersonManager());
            //批次相关信息录入
            excelMode.setSubmitUb0000(batchinfo.getSubmitUb0000());
            excelMode.setSurname(batchinfo.getSurname());
            excelMode.setSubmitUcategory(batchinfo.getSubmitUcategory());
            excelMode.setSubmitUcontacts(batchinfo.getSubmitUcontacts());
            excelMode.setSubmitPhone(batchinfo.getSubmitPhone());
            list.add(excelMode);
        }
        return list;
    }






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
        excelWriter.write(createModelList(idStr),sheet);
        excelWriter.finish();
        outputStream.close();
    }
}









