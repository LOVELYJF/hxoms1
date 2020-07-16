package com.hxoms.modules.omsregcadre.controller;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegYearcheckInfo;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegProcpersoninfoIPagParam;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/omsRegProcpersonInfo")
public class OmsRegProcpersoninfoController {

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    @Autowired
    private OmsRegProcpersonInfoService mrpinfoService;
    @Autowired
    private Environment environment;
    /**
     * 初始化
     * @author lijiaojiao
     * @date 2020/4/16 18:01
     */
    @GetMapping("/getInitialReginfo")
    public Result getInitialReginfo(OmsRegProcpersoninfoIPagParam personInfoIPagParam) {
        try{

            PageInfo<OmsRegProcpersoninfo> mrpinfoList = mrpinfoService.getInitialReginfo(personInfoIPagParam);
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
    public Result insertRpinfo(OmsRegProcpersoninfo orpInfo) {
        return Result.success(mrpinfoService.insertRpinfo(orpInfo));
    }


    /**
     * 修改登记备案人员
     * @author lijiaojiao
     * @date 2020/4/18 14:01
     */
    @PostMapping("/updateRpinfo")
    public Result updateRpinfo(OmsRegProcpersoninfo orpInfo) {
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
    public Result uploadOmsRegGongAn(MultipartFile file) {
        // 读取Excel表格
        try{
            //查询是否存在公安数据
            String dataType="2";
            int count = mrpinfoService.selectCountGongAn(dataType);
            if (count < 0){

                //获取缓存
                /*Cache<String,Object> cache = GuavaCache.getCache();
                cache.put("uploadOmsRegGongAnlist", uploadOmsRegGongAnlist);*/

                List<OmsRegProcpersoninfo> uploadOmsRegGongAnlist = readOmsDataGA();
                int con = mrpinfoService.insertOmsRegGongAn(uploadOmsRegGongAnlist);
                return Result.success(con);
            }else{
                return Result.error("存在待处理的公安数据，请勿多次上传");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    @PostMapping("/selectMergeList")
    public Result selectMergeList(String dataType){
        List<OmsRegProcpersoninfo> list = mrpinfoService.selectMergeList(dataType);
        return Result.success(list);
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
     * @param personInfoIPagParam
     * @return
     */
    @GetMapping("/getProvinceCadreRegInfo")
    public Result getProvinceCadreRegInfo(OmsRegProcpersoninfoIPagParam personInfoIPagParam) {
        try{
            PageInfo<OmsRegProcpersoninfo> mrpinfoList = mrpinfoService.getProvinceCadreRegInfo(personInfoIPagParam);
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
     * @param procpersonInfoIPagParam
     * @return
     */
    @GetMapping("/getRegPersonInfoList")
    public Result getRegPersonInfoList(OmsRegProcpersoninfoIPagParam procpersonInfoIPagParam) {
        try{
            PageInfo<OmsRegProcpersoninfo> mrpinfoList = mrpinfoService.getRegPersonInfoList(procpersonInfoIPagParam);
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
            List<OmsRegProcpersoninfo> personlist = mrpinfoService.selectPersonByBatchNo(batchNo);
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
    @GetMapping("/checkUploadRegRecord")
    public Result checkUploadRegRecord(Date year,MultipartFile file) {
        // 读取Excel表格
        try{
            //登记备案大检查上传登记备案记录
            List<OmsRegProcpersoninfo> list = readOmsDataGA();
            //查询年度列表
            List<OmsRegYearcheckInfo> yearList = mrpinfoService.queryYearList(list);

            // 匹配未备案人员 新增到大检查表中
            int con = mrpinfoService.checkUploadRegRecord(list);
            List<OmsRegYearcheckInfo> yearcheckinfoList = null;
            if (con >0){
                //查询大检查中未备案人员列表（可根据年度进行查询）
                yearcheckinfoList = mrpinfoService.queryYearCheckList(year);
            }
            return Result.success(yearcheckinfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 查询所有满足撤销登记备案条件人员
     * @param
     * @return
     */
    @GetMapping("/selectPersonAndAllowRevoke")
    public Result selectPersonAndAllowRevoke(OmsRegProcpersoninfo msRegProcpersonInfo){
        return Result.success(mrpinfoService.selectPersonAndAllowRevoke(msRegProcpersonInfo));
    }

    /**
     * 根据人员编号查询对应备案申请
     * @return
     */
    @GetMapping("/selectInfoByA0100")
    public Result selectInfoByA0100(String a0100){
        return Result.success(mrpinfoService.selectInfoByA0100(a0100));
    }


    /**
     * 上传公安数据
     * @return
     */
    @PostMapping("/readOmsDataGA")
    private static List<OmsRegProcpersoninfo> readOmsDataGA() throws IOException {

        // 读取Excel文件
        InputStream inputStream = new FileInputStream("D:/example.xls");
        Workbook workbook = new HSSFWorkbook(inputStream);
       /* //检查文件
        checkFile(inputStream);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(inputStream);*/
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        //List<String[]> list = new ArrayList<String[]>();
        List<OmsRegProcpersoninfo> list = new ArrayList<OmsRegProcpersoninfo>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行,去掉最后三行
                for (int rowNum = firstRowNum + 3; rowNum <= lastRowNum - 3; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }

                    // 将单元格中的内容存入集合
                    OmsRegProcpersoninfo orpInfo = new OmsRegProcpersoninfo();
                    //姓
                    Cell cell = row.getCell(1);
                    if (cell == null) {
                        continue;
                    }
                    orpInfo.setSurname(cell.getStringCellValue());

                    cell = row.getCell(2);
                    if (cell == null) {
                        continue;
                    }
                    orpInfo.setName(cell.getStringCellValue());

                    cell = row.getCell(3);
                    if (cell == null) {
                        continue;
                    }
                    orpInfo.setSex(cell.getStringCellValue());

                    cell = row.getCell(4);
                    if (cell == null) {
                        continue;
                    }
                    orpInfo.setSex(cell.getStringCellValue());

                    cell = row.getCell(5);
                    if (cell == null) {
                        continue;
                    }
                    orpInfo.setSex(cell.getStringCellValue());

                    cell = row.getCell(6);
                    if (cell == null) {
                        continue;
                    }
                    orpInfo.setSex(cell.getStringCellValue());

                    cell = row.getCell(7);
                    if (cell == null) {
                        continue;
                    }
                    orpInfo.setSex(cell.getStringCellValue());

                    cell = row.getCell(8);
                    if (cell == null) {
                        continue;
                    }
                    orpInfo.setSex(cell.getStringCellValue());






                    list.add(orpInfo);
                }
            }
            workbook.close();
        }
        return list;
    }

    public static void checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "不是excel文件");
        }
    }

    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return workbook;
    }


   /*     // 循环工作表
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
                OmsRegProcpersoninfo orpInfo = new OmsRegProcpersoninfo();
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
                orpInfo.setBirthDateSfz(cell.getDateCellValue());

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
    }*/




    /**
     *登记备案人员统计
     * @return
     */
    @GetMapping("/selectStatisticsCount")
    public Result selectStatisticsCount() {
        try{
            Map<String, Object> map =  mrpinfoService.selectStatisticsCount();
            return Result.success(map);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


}









