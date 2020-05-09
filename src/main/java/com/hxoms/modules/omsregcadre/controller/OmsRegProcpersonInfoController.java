package com.hxoms.modules.omsregcadre.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersonInfo;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/OmsRegProcpersonInfo")
public class OmsRegProcpersonInfoController {

    @Autowired
    private OmsRegProcpersonInfoService mrpinfoService;

    /**
     * 初始化
     * @author lijiaojiao
     * @date 2020/4/16 18:01
     */
    @GetMapping("/getInitialReginfo")
    public Result getInitialReginfo(Page page,OmsRegProcpersonInfo msRegProcpersonInfo) {
        try{
            IPage<OmsRegProcpersonInfo> mrpinfoList = mrpinfoService.getInitialReginfo(page,msRegProcpersonInfo);
            return Result.success(mrpinfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /**
     * 新增登记备案人员
     * @author lijiaojiao
     * @date 2020/4/18 14:01
     */
    @PostMapping("/insertRpinfo")
    public Result insertRpinfo(OmsRegProcpersonInfo orpInfo) {
        return Result.success(mrpinfoService.insertRpinfo(orpInfo));
    }


    /**
     * 修改登记备案人员
     * @author lijiaojiao
     * @date 2020/4/18 14:01
     */
    @PostMapping("/updateRpinfo")
    public Result updateRpinfo(OmsRegProcpersonInfo orpInfo) {
        return Result.success(mrpinfoService.updateRpinfo(orpInfo));
    }


    /**
     * 删除登记备案人员
     * @author lijiaojiao
     * @date 2020/4/18 14:01
     */
    @PostMapping("/deleteRpinfo")
    public Result deleteRpinfo(String id) {
        return Result.success(mrpinfoService.deleteRpinfo(id));
    }



    /* 上传登记备案记录(出入境（公安）)
     * @param
     * @return
     */
    @PostMapping("/uploadOmsRegGongAn")
    public Result uploadOmsRegGongAn() {
        // 读取Excel表格
        try{
            //查询是否存在公安数据
            String dataType="2";
            int count = mrpinfoService.selectCountGongAn(dataType);
            if (count < 0){
                List<OmsRegProcpersonInfo> list = readOmsDataGA();
                List<OmsRegProcpersonInfo> mepinfoList = mrpinfoService.insertOmsRegGongAn(list);
                return Result.success(mepinfoList);
            }else{
                return Result.error("存在待处理的公安数据，请勿多次上传");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }



    /**
     * 合并干部和公安数据
     * @param idStr
     * @return
     */
    @PostMapping("/mergeDataGBandGA")
    public Result mergeDataGBandGA(String idStr) {
        return Result.success(mrpinfoService.mergeDataGBandGA(idStr));
    }


    /**
     * 查询省管干部登记备案信息
     * （打开登记备案页面）
     * @param page
     * @param msRegProcpersonInfo
     * @return
     */
    @GetMapping("/getProvinceCadreRegInfo")
    public Result getProvinceCadreRegInfo(Page page,OmsRegProcpersonInfo msRegProcpersonInfo) {
        try{
            IPage<OmsRegProcpersonInfo> mrpinfoList = mrpinfoService.getProvinceCadreRegInfo(page,msRegProcpersonInfo);
            return Result.success(mrpinfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /**
     * 提取备案人员
     * @author lijiaojiao
     * @date 2020/4/22 14:01
     */
    @PostMapping("/extractRegPersonInfo")
    public Result extractRegPersonInfo() throws ParseException {
        return Result.success(mrpinfoService.extractRegPersonInfo());
    }



    /**
     * 登记备案数据浏览
     * @param page
     * @param msRegProcpersonInfo
     * @return
     */
    @GetMapping("/getRegPersonInfoList")
    public Result getRegPersonInfoList(Page page,OmsRegProcpersonInfo msRegProcpersonInfo) {
        try{
            IPage<OmsRegProcpersonInfo> mrpinfoList = mrpinfoService.getRegPersonInfoList(page,msRegProcpersonInfo);
            return Result.success(mrpinfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /**
     * 根据批次编号查询批次所对应的人员
     * @return
     */
    @GetMapping("/selectPersonByBatchNo")
    public Result selectPersonByBatchNo(String batchNo) {
        try{
            List<OmsRegProcpersonInfo> personlist = mrpinfoService.selectPersonByBatchNo(batchNo);
            return Result.success(personlist);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /* 大检查上传登记备案记录(出入境（公安）)
     * @param
     * @return
     */
    @PostMapping("/checkUploadRegRecord")
    public Result checkUploadRegRecord() {
        // 读取Excel表格
        try{
            List<OmsRegProcpersonInfo> list = readOmsDataGA();
            List<OmsRegProcpersonInfo> mepinfoList = mrpinfoService.checkUploadRegRecord(list);
            return Result.success(mepinfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }



    /**
     * 上传公安数据
     * @return
     */
    private static List<OmsRegProcpersonInfo> readOmsDataGA() {

        List<OmsRegProcpersonInfo> list = new ArrayList<OmsRegProcpersonInfo>();
        HSSFWorkbook workbook = null;
        try {
            // 读取Excel文件
            InputStream inputStream = new FileInputStream("D:/msdataga.xls");
            workbook = new HSSFWorkbook(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 循环工作表
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }

                // 将单元格中的内容存入集合
                OmsRegProcpersonInfo orpInfo = new OmsRegProcpersonInfo();
                //姓名
                HSSFCell cell = hssfRow.getCell(1);
                if (cell == null) {
                    continue;
                }

                //是否复姓。拆除
                boolean isCompoundSurname = OmsRegInitUtil.isCompoundSurname(cell.getStringCellValue());
                if (isCompoundSurname){//是
                    orpInfo.setSurname(cell.getStringCellValue().substring(0,2));//姓
                    orpInfo.setName(cell.getStringCellValue().substring(2,cell.getStringCellValue().length()));//名
                }else{
                    orpInfo.setSurname(cell.getStringCellValue().substring(0,1));
                    orpInfo.setName(cell.getStringCellValue().substring(1,cell.getStringCellValue().length()));
                }

                //性别
                cell = hssfRow.getCell(2);
                if (cell == null) {
                    continue;
                }
                orpInfo.setSex(cell.getStringCellValue());

                //出生日期
                cell = hssfRow.getCell(3);
                if (cell == null) {
                    continue;
                }
                orpInfo.setBirthDate(cell.getDateCellValue());

                //身份证号
                cell = hssfRow.getCell(4);
                if (cell == null) {
                    continue;
                }
                orpInfo.setIdnumber(cell.getStringCellValue());

                //户口所在地
                cell = hssfRow.getCell(5);
                if (cell == null) {
                    continue;
                }
                orpInfo.setRegisteResidence(cell.getStringCellValue());

                //入库标识
                cell = hssfRow.getCell(6);
                if (cell == null) {
                    continue;
                }
                orpInfo.setInboundFlag(cell.getStringCellValue());

                //工作单位
                cell = hssfRow.getCell(7);
                if (cell == null) {
                    continue;
                }
                orpInfo.setWorkUnit(cell.getStringCellValue());


                //职务（级）或职称
                cell = hssfRow.getCell(8);
                if (cell == null) {
                    continue;
                }
                orpInfo.setPost(cell.getStringCellValue());


                //人事主管单位
                cell = hssfRow.getCell(9);
                if (cell == null) {
                    continue;
                }
                orpInfo.setPersonManager(cell.getStringCellValue());

                //身份情况代码
                cell = hssfRow.getCell(10);
                if (cell == null) {
                    continue;
                }
                orpInfo.setIdentityCode(cell.getStringCellValue());


                //身份情况
                cell = hssfRow.getCell(11);
                if (cell == null) {
                    continue;
                }
                orpInfo.setIdentity(cell.getStringCellValue());

                list.add(orpInfo);
            }
        }
        return list;
    }
}









