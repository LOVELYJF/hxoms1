package com.hxoms.modules.omsregcadre.controller;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.util.PingYinUtil;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.omsregcadre.entity.OmsBaseinfoConfig;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegYearcheckinfo;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegProcpersoninfoIPagParam;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegYearCheckIPagParam;
import com.hxoms.modules.omsregcadre.mapper.OmsBaseinfoConfigMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/omsRegProcpersonInfo")
public class OmsRegProcpersoninfoController {

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    @Autowired
    private OmsRegProcpersonInfoService mrpinfoService;
    @Autowired
    private Environment environment;
    @Autowired
    private SysDictItemMapper sysDictItemMapper;
    @Autowired
    private OmsBaseinfoConfigMapper omsBaseinfoConfigMapper;
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
            List<OmsRegProcpersoninfo> uploadOmsRegGongAnlist = readOmsDataGA(file);
            if (uploadOmsRegGongAnlist!=null && uploadOmsRegGongAnlist.size()>0){
                String result = mrpinfoService.insertOmsRegGongAn(uploadOmsRegGongAnlist);
                return Result.success(result);
            }else{
                return Result.error("上传文件为空，请检查后再上传");
            }

        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传成功失败,原因："+e.getMessage());
        }
    }

    /**
     * 手工匹配列表查询
     * 显示待备案干部数据 和 在职状态为“未匹配”的公安数据
     * @return
     */
    @PostMapping("/selectMergeList")
    public Result selectMergeList(String sortType){
        List<OmsRegProcpersoninfo> list = mrpinfoService.selectMergeList(sortType);
        return Result.success(list);
    }

    /**
     * 合并干部和公安数据
     * @param idStr
     * @return
     */
    @PostMapping("/mergeDataGBandGA")
    public Result mergeDataGBandGA(String idStr) {
        return mrpinfoService.mergeDataGBandGA(idStr);
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
        return mrpinfoService.extractRegPersonInfo();
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
    public Result selectPersonByBatchNo(String batchNo,Integer pageNum,Integer pageSize) {
        try{
            PageInfo<OmsRegProcbatchPerson> personlist = mrpinfoService.selectPersonByBatchNo(batchNo,pageNum,pageSize);
            return Result.success(personlist);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /* 大检查上传登记备案记录(出入境（公安）)c
     * @param
     * @return
     */
    @PostMapping("/checkUploadRegRecord")
    public Result checkUploadRegRecord(MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 读取Excel表格
        try{
            //登记备案大检查上传登记备案记录
            List<OmsRegProcpersoninfo> list = readOmsDataGA(file);
            // 匹配未备案人员 新增到大检查表中
            int con = mrpinfoService.checkUploadRegRecord(list);
            return Result.success(con);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }
    /**
     * 查询年度列表
     * @param
     * @return
     */
    @GetMapping("/selectYearList")
    public Result selectYearList(){
        return Result.success(mrpinfoService.queryYearList());
    }

    /**
     * 查询大检查中未备案人员列表（可根据年度进行查询）
     * @param
     * @return
     */
    @GetMapping("/selectYearCheckList")
    public Result selectYearCheckList(OmsRegYearCheckIPagParam regYearCheckIPagParam){
        PageInfo<OmsRegYearcheckinfo> list = mrpinfoService.queryYearCheckList(regYearCheckIPagParam);
        return Result.success(list);
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
    private List<OmsRegProcpersoninfo> readOmsDataGA(MultipartFile file) throws IOException, ParseException {
        //缓存行政区划
        List<SysDictItem> xzqhs = sysDictItemMapper.selectItemCodeByDictCode("ZB01");
        HashMap<String,SysDictItem> hashMapXZQH=new HashMap<>();
        for (SysDictItem xzqh:xzqhs
             ) {
            hashMapXZQH.put(xzqh.getItemName(),xzqh);
        }

        //缓存职务映射关系
        Map<String, Object> params = new HashMap<String, Object>();
        List<OmsBaseinfoConfig> baseInfos = omsBaseinfoConfigMapper.selectPostInfo(params);
        HashMap<String, OmsBaseinfoConfig> hashMapBaseInfo = new HashMap<>();
        for (OmsBaseinfoConfig baseInfo : baseInfos
        ) {
                hashMapBaseInfo.put(baseInfo.getInfoName(), baseInfo);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        // 读取Excel文件
//        InputStream inputStream = new FileInputStream("D:/example.xls");
//        Workbook workbook = new HSSFWorkbook(inputStream);
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        //List<String[]> list = new ArrayList<String[]>();
        List<OmsRegProcpersoninfo> list = new ArrayList<OmsRegProcpersoninfo>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) continue;
                if(sheet.getRow(0).getCell(1).getStringCellValue().equals("中文姓名")==false) continue;
                if(sheet.getRow(0).getCell(2).getStringCellValue().equals("性别")==false) continue;
                if(sheet.getRow(0).getCell(3).getStringCellValue().equals("出生日期")==false) continue;
                if(sheet.getRow(0).getCell(4).getStringCellValue().equals("身份证号码")==false) continue;
                if(sheet.getRow(0).getCell(5).getStringCellValue().equals("户口所在地")==false) continue;

                    //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行,去掉最后三行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum ; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null)   continue;

                    // 将单元格中的内容存入集合
                    OmsRegProcpersoninfo orpInfo = new OmsRegProcpersoninfo();

                    //姓名
                    Cell cell = row.getCell(1);
                    if (cell == null)  continue;
                    mrpinfoService.SplitName(orpInfo,cell.getStringCellValue().trim());

                    //性别
                    cell = row.getCell(2);
                    if (cell == null) continue;
                    orpInfo.setSex(cell.getStringCellValue().equals("男")?"1":"2");

                    //出生日期(身份证号(公安))
                    cell = row.getCell(3);
                    if (cell == null)  continue;
                    orpInfo.setBirthDate(simpleDateFormat.parse(cell.getStringCellValue()));

                    //身份证号（公安）
                    cell = row.getCell(4);
                    if (cell == null)   continue;
                    orpInfo.setIdnumberGa(cell.getStringCellValue());

                    //户口所在地
                    cell = row.getCell(5);
                    if (cell == null)  continue;
                    SysDictItem hk=hashMapXZQH.get(cell.getStringCellValue());
                    if(hk!=null)
                    {
                        orpInfo.setRegisteResidenceCode(hk.getItemCode());
                    }
                    orpInfo.setRegisteResidence(cell.getStringCellValue());

                    //入库标识
                    cell = row.getCell(6);
                    if (cell == null)   continue;
                    orpInfo.setInboundFlag(cell.getStringCellValue());

                    //工作单位
                    cell = row.getCell(7);
                    if (cell == null)  continue;
                    orpInfo.setWorkUnit(cell.getStringCellValue());

                    //职务(级)或职称
                    cell = row.getCell(8);
                    if (cell == null)  continue;
                    OmsBaseinfoConfig baseinfoConfig = hashMapBaseInfo.get(cell.getStringCellValue());
                    if(baseinfoConfig!=null)
                        orpInfo.setPostCode(baseinfoConfig.getParentId());
                    orpInfo.setPost(cell.getStringCellValue());

                    //人事主管单位
                    cell = row.getCell(9);
                    if (cell == null)   continue;
                    orpInfo.setPersonManager(cell.getStringCellValue());

                    orpInfo.setId(UUIDGenerator.getPrimaryKey());
                    //数据来源标注为“公安”，备案状态标记为“已备案”，验收状态标注为“未验收”。
                    orpInfo.setDataType("2");
                    orpInfo.setRfStatus("1");
                    orpInfo.setCheckStatus("0");
                    orpInfo.setIncumbencyStatus(String.valueOf(Constants.emIncumbencyStatus.Unmatched.getIndex()) );
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

    private String getCellValue(Cell cell) {
        String cellValue="";
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                cellValue = DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
            } else {
                NumberFormat nf = NumberFormat.getInstance();
                cellValue = String.valueOf(nf.format(cell.getNumericCellValue())).replace(",", "");
            }
        } else if (cell.getCellTypeEnum() == CellType.STRING) {
            cellValue = String.valueOf(cell.getStringCellValue());
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellTypeEnum() == CellType.ERROR) {
            cellValue = "错误类型";
        } else {
            cellValue = "";
        }
        return cellValue;
    }





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

    /**
     * 获取出国境职务树
     * @return
     */
    @GetMapping("/selectBaseInfoTree")
    public Result selectCGJPostTree(String dictCode) {
        return Result.success(mrpinfoService.selectCGJPostTree(dictCode));
    }

    /**
     * 获取干综职务树
     * @return
     */
    @GetMapping("/selectGZPostTree")
    public Result selectGZPostTree() {
        return Result.success(mrpinfoService.selectGZPostTree());
    }

    /**
     * 新增配置信息
     * @author lijiaojiao
     * @date 2020/07/27 14:01
     */
    @PostMapping("/insertBaseInfoConfig")
    public Result insertBaseInfoConfig(@RequestBody List<OmsBaseinfoConfig> list) {
        return Result.success(mrpinfoService.insertBaseInfoConfig(list));
    }

    /**
     * 删除配置信息
     * @author lijiaojiao
     * @date 2020/07/27 14:01
     */
    @PostMapping("/deleteBaseInfoConfig")
    public Result deleteBaseInfoConfig(@RequestBody List<String> Ids) {
        return Result.success(mrpinfoService.deleteBaseInfoConfig(Ids));
    }



}









