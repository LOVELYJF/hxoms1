package com.hxoms.modules.omsregcadre.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.hxoms.common.utils.*;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.omsregcadre.entity.*;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import io.swagger.annotations.ApiOperation;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
    private OmsRegProcbatchService orpbatchService;

    @Autowired
    private OmsEntryexitRecordService entryexitRecordService;

    /**
     * 下载 省管干部登记备案表
     * @param idStr
     * @throws IOException
     */
   /* @PostMapping("/exportRfInfo")
    @Transactional(rollbackFor=Exception.class)
    @ApiOperation(value = "导出省管干部登记备案表", notes = "export", produces = "application/octet-stream")
    public void exportRfInfo(String idStr) throws IOException {
        HttpServletResponse response = DomainObjectUtil.getResponse();
        try {
            List<ExcelModelORPinfo> list = new ArrayList<>();
            List<OmsRegProcbatchPerson> orpbplist = new ArrayList<>();
            //查询登记备案信息根据备案id
            List<OmsRegProcpersoninfo> rflist = mrpinfoService.selectListById(idStr);
            //查询批次相关信息
            OmsRegProcbatch batchinfo = orpbatchService.selectWbaByOrpbatch();
            for (int i = 0; i < rflist.size(); i++) {
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
            if (con > 0) {
                //修改批次表备案状态0未备案，1已备案，2已确认
                batchinfo.setStatus("1");
                int con1 = orpbatchService.updateOrpbatch(batchinfo);
                if (con1 > 0) {
                    mrpinfoService.updateRegProcpersoninfo(idStr);
                }
            }
            //导出
            String fileName = UtilDateTime.nowDate() + "省管干部登记备案表";
            // 设置输出的格式
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();



            //exportSheet2();
            // WriteSheet writeSheet1 = EasyExcel.writerSheet(0, "表1（纸）").head(CustInfo.class).build();
            WriteSheet writeSheet2 = EasyExcel.writerSheet(1, "表2（电子版）").head(ExcelModelORPinfo.class).build();
            //  excelWriter.write(data, writeSheet1);
            excelWriter.write(list, writeSheet2);
            excelWriter.finish();
        } catch (Exception var10) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + var10.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }


    }
*/
    @PostMapping("/exportRfInfo")
    public HSSFWorkbook exportRfInfo1(String idStr) {

//            List<ExcelModelORPinfo> list = new ArrayList<>();
//            List<OmsRegProcbatchPerson> orpbplist = new ArrayList<>();
//            //查询登记备案信息根据备案id
//            List<OmsRegProcpersoninfo> rflist = mrpinfoService.selectListById(idStr);
//            //查询批次相关信息
//            OmsRegProcbatch batchinfo = orpbatchService.selectWbaByOrpbatch();
//            for (int i = 0; i < rflist.size(); i++) {
//                OmsRegProcpersoninfo info = rflist.get(i);
//                OmsRegProcbatchPerson batchperson = new OmsRegProcbatchPerson();
//                //为批次人员表复制相同字段的数据
//                BeanUtils.copyProperties(rflist, batchperson);
//                batchperson.setId(UUIDGenerator.getPrimaryKey());
//                batchperson.setRfId(info.getId());
//                batchperson.setBatchId(batchinfo.getBatchNo());
//                orpbplist.add(batchperson);
//                //为excel录入数据
//                ExcelModelORPinfo excelMode = new ExcelModelORPinfo();
//                //登记备案信息录入
//                BeanUtils.copyProperties(info, excelMode);
//                excelMode.setNo(i);
//                //批次相关信息录入
//                BeanUtils.copyProperties(batchinfo, excelMode);
//                list.add(excelMode);
//            }
//            int con = orpbatchService.batchinsertInfo(orpbplist);
//            if (con > 0) {
//                //修改批次表备案状态0未备案，1已备案，2已确认
//                batchinfo.setStatus("1");
//                int con1 = orpbatchService.updateOrpbatch(batchinfo);
//                if (con1 > 0) {
//                    mrpinfoService.updateRegProcpersoninfo(idStr);
//                }
//            }


            List<Map> dataList  = mrpinfoService.selectRegInfoListById(idStr);
            List listK = new ArrayList();
            List listV = new ArrayList();
            listK.add("num");listV.add("序号");
            listK.add("surname");listV.add("中文姓");
            listK.add("name");listV.add("中文名");
            listK.add("sex");listV.add("性别");
            listK.add("birthDateGb");listV.add("出生日期");  // 少一列 健康情况
            listK.add("idnumberGb");listV.add("身份证号");               // 少一列 备案号
            listK.add("registeResidence");listV.add("户口所在地");
            listK.add("inboundFlag");listV.add("入库标识");
            listK.add("workUnit");listV.add("工作单位");
            listK.add("post");listV.add("职务(级)或职称");  //  少一列 状态
            listK.add("personManager");listV.add("人事主管单位");
//            listK.add("");listV.add("报送单位组织机构代码");
//            listK.add("");listV.add("报送单位名称");
//            listK.add("");listV.add("报送单位类别");
//            listK.add("");listV.add("报送单位联系人");
//            listK.add("");listV.add("联系电话");
//            listK.add("");listV.add("入库批号");
            return LeaderSupervisionUntil.exportRfInfoByListMap(listK,listV,dataList,"wjf");
    }



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
     * 导入出入境记录
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
            String fileName = UtilDateTime.nowDate() + "省管干部登记备案表";
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

}









