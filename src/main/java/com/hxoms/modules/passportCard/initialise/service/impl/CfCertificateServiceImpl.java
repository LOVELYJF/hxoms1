package com.hxoms.modules.passportCard.initialise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.util.PingYinUtil;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsEntryexitRecordMapper;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateReminder;
import com.hxoms.modules.passportCard.initialise.entity.OmsCerIssuePerson;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateExport;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateReminderParam;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.initialise.mapper.OmsCerIssuePersonMapper;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CfCertificateServiceImpl extends ServiceImpl<CfCertificateMapper,CfCertificate> implements CfCertificateService {

    @Autowired
    private CfCertificateMapper cfCertificateMapper;

    @Autowired
    private OmsEntryexitRecordMapper omsEntryexitRecordMapper;

    @Autowired
    private OmsCerIssuePersonMapper omsCerIssuePersonMapper;

    @Override
    public PageInfo<CfCertificate> selectCfCertificateIPage(CfCertificatePageParam cfCertificatePageParam) {


        /*if (cfCertificatePageParam.getPageNum() == null){
            cfCertificatePageParam.setPageNum(0);
        }
        if(cfCertificatePageParam.getPageSize() ==null){
            cfCertificatePageParam.setPageSize(cfCertificateList.size());
        }*/

        PageHelper.startPage(cfCertificatePageParam.getPageNum()==null?0 : cfCertificatePageParam.getPageNum(),
                            cfCertificatePageParam.getPageSize()==null?10 : cfCertificatePageParam.getPageSize());
        List<CfCertificate> cfCertificateList =cfCertificateMapper.selectCfCertificateIPage(cfCertificatePageParam);

        PageInfo<CfCertificate> pageInfo = new PageInfo(cfCertificateList);

        return pageInfo;
    }



   public boolean delete(String id){

        return cfCertificateMapper.delete(id);
    }

    @Override
    public PageInfo<CfCertificateReminder> findOverduePass(CfCertificateReminderParam cfCertificateReminderParam) {
        PageHelper.startPage(cfCertificateReminderParam.getPageNum()== null?0:cfCertificateReminderParam.getPageNum(),
                cfCertificateReminderParam.getPageSize()==null?10:cfCertificateReminderParam.getPageSize());
        List<CfCertificateReminder> cfCertificateReminderList= cfCertificateMapper.findOverduePass(cfCertificateReminderParam);
        PageInfo<CfCertificateReminder> pageInfo = new PageInfo(cfCertificateReminderList);
        return pageInfo;
    }

    public Integer findSuccessCf(){
        return cfCertificateMapper.findSuccessCf();
   }

 
   /**
    * @Desc: 导入公安的证照信息
    * @Author: wangyunquan
    * @Param: [multipartFile, dataSource]
    * @Return: com.hxoms.common.utils.PageBean
    * @Date: 2020/7/24
    */
    @Override
    public PageBean excelToDB(MultipartFile multipartFile, String dataSource) throws Exception {
        if (multipartFile==null || multipartFile.getSize() <= 0 ) {
           throw new CustomMessageException("参数不正确");
        }
        //读取Excel数据
        readExcel(multipartFile.getInputStream(),multipartFile.getOriginalFilename(),dataSource);
        return selectAllCertificate(new PageBean());
    }

    /**
     * @Desc: 查询所有证照
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/8/4
     */
    @Override
    public PageBean selectAllCertificate(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        PageInfo<CfCertificate> pageInfo= new PageInfo<CfCertificate>(cfCertificateMapper.selectAllCertificate());
        return PageUtil.packagePage(pageInfo);
    }

    /**
     * @Desc: 通过证照查询公安证照信息及人员信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate
     * @Date: 2020/8/4
     */
    @Override
    public CfCertificateValidate selectPersonInfo(CfCertificate cfCertificate) {
        if(cfCertificate==null||cfCertificate.getZjlx()==null||StringUtils.isBlank(cfCertificate.getZjhm()))
            throw new CustomMessageException("参数不正确");
        CfCertificate Recertificate=cfCertificateMapper.selectCertificateInfo(cfCertificate);
        List<OmsRegProcpersoninfo> omsRegProcpersoninfoList=null;
        //获取备案人员信息
        omsRegProcpersoninfoList=cfCertificateMapper.selectRegPerson(Recertificate!=null?Recertificate.getOmsId():null,cfCertificate.getName(),cfCertificate.getCsrq());
        CfCertificateValidate cfCertificateValidate=new CfCertificateValidate();
        cfCertificateValidate.setCfCertificate(Recertificate);
        cfCertificateValidate.setOmsRegProcpersoninfoList(omsRegProcpersoninfoList);
        return cfCertificateValidate;
    }

    /**
     * @Desc: 证照验证，有公安数据，则验证并更新状态，否则插入证照信息。
     * @Author: wangyunquan
     * @Param: [cfCertificateGa, cfCertificateZz]
     * @Return: void
     * @Date: 2020/8/5
     */
    @Override
    public void saveOrUpdate(CfCertificate cfCertificateGa, CfCertificate cfCertificateZz) {
        //有公安数据，验证并更新状态
        if(cfCertificateGa!=null&&!StringUtils.isBlank(cfCertificateGa.getOmsId())){
            //验证姓名、性别、国籍、出生日期、签发单位、签发日期、出生地、证件有效期至

        }else{

        }
    }

    /**
     * @Desc: 读取Excel数据
     * @Author: wangyunquan
     * @Param: [inputStream, fileName, dataSource]
     * @Return: java.lang.String
     * @Date: 2020/7/24
     */
    public void readExcel(InputStream inputStream, String fileName, String dataSource) throws IOException {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        boolean isE2007 = false;
        //判断是否是excel2007格式
        if(fileName.endsWith("xlsx")){
            isE2007 = true;
        }
        Workbook wb=null;
        try {
            //根据文件格式(2003或者2007)来初始化
            if(isE2007){
                wb = new XSSFWorkbook(inputStream);
            }else{
                wb = new HSSFWorkbook(inputStream);
            }
            int numberOfSheets = wb.getNumberOfSheets();
            for (int k = 0; k < numberOfSheets; k++) {
                Sheet sheet = wb.getSheetAt(k);
                int sheetMergeCount = sheet.getNumMergedRegions();
                List<CellRangeAddress> cellRangeAddressList=new LinkedList<>();
                //获取人员
                for (int i = 0; i < sheetMergeCount; i++) {
                    CellRangeAddress range = sheet.getMergedRegion(i);
                    int firstColumn = range.getFirstColumn();
                    int firstRow = range.getFirstRow();
                    if(0 == firstColumn&&firstRow>0){
                        cellRangeAddressList.add(range);
                    }
                }
                SimpleDateFormat dateParse=new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat timeParse=new SimpleDateFormat("HHmmss");
                SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm:ss");
                CfCertificateExport cfCertificateExport=new CfCertificateExport();
                //获取人员证照信息及对应出入境记录
                for (int j =0;j<cellRangeAddressList.size();j++) {
                    CellRangeAddress ca=cellRangeAddressList.get(j);
                    int firstRow = ca.getFirstRow();
                    int lastRow = ca.getLastRow();
                    if((lastRow-firstRow+1)==2)
                        continue;
                    Row firstRowData = sheet.getRow(firstRow);
                    List<OmsRegProcpersoninfo> omsRegProcpersoninfoList=null;
                    List<CfCertificate> cfCertificateExistList=new ArrayList<>();
                    List<OmsEntryexitRecord> omsEntryexitRecordExistList=new ArrayList<>();
                    if("查询条件".equals(firstRowData.getCell(1).toString())){
                        String idNo = firstRowData.getCell(2).toString();
                        String name = firstRowData.getCell(3).toString();
                        omsRegProcpersoninfoList=cfCertificateMapper.selectA0100ByQua(idNo,name);
                        int size = omsRegProcpersoninfoList.size();
                        //记录异常人员
                        if(size!=1){
                            OmsCerIssuePerson omsCerIssuePerson=new OmsCerIssuePerson();
                            omsCerIssuePerson.setId(UUIDGenerator.getPrimaryKey());
                            omsCerIssuePerson.setIdNo(idNo);
                            omsCerIssuePerson.setName(name);
                            omsCerIssuePerson.setImportPerson(userInfo.getId());
                            omsCerIssuePerson.setImportTime(new Date());
                            if(size==0){
                                omsCerIssuePerson.setDescription("登记备案表中查无此人");
                            }else{
                                StringBuffer a0100s=new StringBuffer();
                                for (OmsRegProcpersoninfo omsRegProcpersoninfo : omsRegProcpersoninfoList) {
                                    a0100s.append(omsRegProcpersoninfo.getA0100()).append("|");
                                }
                                omsCerIssuePerson.setA0100s(a0100s.toString());
                                omsCerIssuePerson.setDescription("登记备案表中匹配到"+size+"人");
                            }
                            int result=omsCerIssuePersonMapper.selectByEntity(omsCerIssuePerson);
                            if(result==0)
                                cfCertificateExport.setOmsCerIssuePerson(omsCerIssuePerson);
                            continue;
                        }
                    }
                    OmsRegProcpersoninfo omsRegProcpersoninfo = omsRegProcpersoninfoList.get(0);
                    //获取已存在证件信息
                    cfCertificateExistList=cfCertificateMapper.selectZJExisByQua(omsRegProcpersoninfo.getA0100());
                    //获取已存在出入境记录数据
                    omsEntryexitRecordExistList=cfCertificateMapper.selectRecordExisByQua(omsRegProcpersoninfo.getA0100());
                    //有效期存储集合
                    Map<String,Date> idTypeInfo= new HashedMap();
                    for (int i = firstRow+1; i <= lastRow; i++) {
                        //获取合并单元格值
                        String mergedRegionValue=getMergedRegionValue(sheet,i,1);;
                        if(!StringUtils.isBlank(mergedRegionValue)){
                            Row row =sheet.getRow(i);
                            int column=2;
                            //证件信息读取
                            if("证件信息".equals(mergedRegionValue)){
                                CfCertificate cfCertificate=new CfCertificate();
                                cfCertificate.setId(UUIDGenerator.getPrimaryKey());
                                cfCertificate.setImportPerson(userInfo.getId());
                                cfCertificate.setImportTime(new Date());
                                cfCertificate.setOmsId(omsRegProcpersoninfo.getId());
                                cfCertificate.setA0100(omsRegProcpersoninfo.getA0100());
                                cfCertificate.setA0184(row.getCell(column++).toString());
                                String name=row.getCell(column++).toString();
                                cfCertificate.setName(name);
                                cfCertificate.setPy(PingYinUtil.getFirstSpell(name));
                                cfCertificate.setSex(getSex(row.getCell(column++).toString()));
                                cfCertificate.setCsrq(dateParse.parse(getCellValue(row.getCell(column++))));
                                cfCertificate.setGj(row.getCell(column++).toString());
                                cfCertificate.setZjlx(getIdType(row.getCell(column++).toString()));
                                cfCertificate.setZjhm(row.getCell(column++).toString());
                                cfCertificate.setZwcsdd(row.getCell(column++).toString());
                                cfCertificate.setZwqfjg(row.getCell(column++).toString());
                                cfCertificate.setQfrq(dateParse.parse(getCellValue(row.getCell(column++))));
                                Date valiDate=dateParse.parse(getCellValue(row.getCell(column++)));
                                cfCertificate.setYxqz(valiDate);
                                //判断是否过期
                                boolean after = valiDate.after(dateParse.parse(dateParse.format(new Date())));
                                if(after){
                                    //是否有效，0：有效
                                    cfCertificate.setIsValid(0);
                                }else{
                                    //保管状态
                                    cfCertificate.setSaveStatus("3");
                                    //是否有效，1：无效
                                    cfCertificate.setIsValid(1);
                                }
                                //证照状态 1:正常保管,2:待领取，3:已取出，4:未上缴
                                cfCertificate.setDqzt(4);
                                //是否锁定 0:正常，1:锁定
                                cfCertificate.setIsLock(0);
                                //人员是否有效 0:有效，1:无效
                                cfCertificate.setIsCabinet(0);
                                idTypeInfo.put(cfCertificate.getZjlx()+cfCertificate.getZjhm(),cfCertificate.getYxqz());
                                //去重
                                if(cfCertificateExistList.size()>0){
                                    boolean flag=false;
                                    for (CfCertificate cfCertificateExist : cfCertificateExistList) {
                                        if(cfCertificateExist.getZjlx()==cfCertificate.getZjlx()
                                                &&cfCertificateExist.getZjhm().equals(cfCertificate.getZjhm())
                                                &&cfCertificateExist.getQfrq().getTime()==cfCertificate.getQfrq().getTime()){
                                            flag=true;
                                            break;
                                        }
                                    }
                                    if(flag)
                                        continue;
                                }
                                cfCertificateExport.setCfCertificate(cfCertificate);
                            }else if("出入境记录".equals(mergedRegionValue)){
                                //出入境记录读取
                                OmsEntryexitRecord omsEntryexitRecord=new OmsEntryexitRecord();
                                omsEntryexitRecord.setId(UUIDGenerator.getPrimaryKey());
                                omsEntryexitRecord.setPriapplyId("");
                                omsEntryexitRecord.setImportPerson(userInfo.getId());
                                omsEntryexitRecord.setImportTime(new Date());
                                omsEntryexitRecord.setDataSource(dataSource);
                                omsEntryexitRecord.setOmsId(omsRegProcpersoninfo.getId());
                                omsEntryexitRecord.setA0100(omsRegProcpersoninfo.getA0100());
                                omsEntryexitRecord.setDataSource(dataSource);
                                omsEntryexitRecord.setOgeStatus(getOgeStatus(row.getCell(column++).toString()));
                                omsEntryexitRecord.setName(row.getCell(column++).toString());
                                Integer sex=getSex(row.getCell(column++).toString());
                                omsEntryexitRecord.setSex(sex!=null?String.valueOf(sex):null);
                                omsEntryexitRecord.setBirthDate(dateParse.parse(getCellValue(row.getCell(column++))));
                                omsEntryexitRecord.setNationality(row.getCell(column++).toString());
                                omsEntryexitRecord.setIdType(getIdType(row.getCell(column++).toString()));
                                omsEntryexitRecord.setIdNumber(row.getCell(column++).toString());
                                omsEntryexitRecord.setDestination(row.getCell(column++).toString());
                                omsEntryexitRecord.setEntraceExit(row.getCell(column++).toString());
                                omsEntryexitRecord.setOgeDate(dateParse.parse(getCellValue(row.getCell(column++))));
                                omsEntryexitRecord.setOgeTime(timeFormat.format(timeParse.parse(row.getCell(column++).toString())));
                                omsEntryexitRecord.setValidUntil(idTypeInfo.get(omsEntryexitRecord.getIdType()+omsEntryexitRecord.getIdNumber()));
                                //去重
                                if(omsEntryexitRecordExistList.size()>0){
                                    boolean flag=false;
                                    for (OmsEntryexitRecord omsEntryexitRecordExist : omsEntryexitRecordExistList) {
                                        if(omsEntryexitRecordExist.getIdType()==omsEntryexitRecord.getIdType()
                                                &&omsEntryexitRecordExist.getIdNumber().equals(omsEntryexitRecord.getIdNumber())
                                                &&omsEntryexitRecordExist.getOgeDate().getTime()==omsEntryexitRecord.getOgeDate().getTime()
                                                &&omsEntryexitRecordExist.getOgeTime().equals(omsEntryexitRecord.getOgeTime())){
                                            flag=true;
                                            break;
                                        }
                                    }
                                    if(flag)
                                        continue;
                                }
                                cfCertificateExport.setOmsEntryexitRecord(omsEntryexitRecord);
                            }
                        }
                    }
                    //批量提交
                    if(j+1%50==0){
                        batchSaveEntity(cfCertificateExport);
                        cfCertificateExport=new CfCertificateExport();
                    }
                }
                batchSaveEntity(cfCertificateExport);
            }
        } catch (Exception ex) {
            if(wb!=null){
                wb.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }
            ex.printStackTrace();
            throw  new CustomMessageException(ex.getMessage());
        }
    }
    @Transactional(rollbackFor=Exception.class)
    public void batchSaveEntity(CfCertificateExport cfCertificateExport){
        int result = 1;
        //证件信息
        List<CfCertificate> cfCertificateList = cfCertificateExport.getCfCertificateList();
        if(cfCertificateList.size()>0)
            result=cfCertificateMapper.batchSaveEntity(cfCertificateList);
        if(result==0)
            throw  new CustomMessageException("插入失败");
        //出入境记录
        List<OmsEntryexitRecord> omsEntryexitRecordList = cfCertificateExport.getOmsEntryexitRecordList();
        if(omsEntryexitRecordList.size()>0)
            result=omsEntryexitRecordMapper.batchSaveEntity(omsEntryexitRecordList);
        if(result==0)
            throw  new CustomMessageException("插入失败");
        //异常人员
        List<OmsCerIssuePerson> omsCerIssuePersonList = cfCertificateExport.getOmsCerIssuePersonList();
        if(omsCerIssuePersonList.size()>0)
            result= omsCerIssuePersonMapper.batchSaveEntity(omsCerIssuePersonList);
        if(result==0)
            throw  new CustomMessageException("插入失败");
    }
    /**
     *
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell){
        if(cell == null)
            return "";
        if(cell.getCellType() == CellType.STRING){
            return cell.getStringCellValue();
        }else if(cell.getCellType() == CellType.BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }else if(cell.getCellType() == CellType.FORMULA){
            return cell.getCellFormula() ;
        }else if(cell.getCellType() == CellType.NUMERIC){
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                return DateFormatUtils.format(date, "yyyyMMdd");
            } else {
                return String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue());
            }
        }
        return "";
    }

    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public  String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }
        return null ;
    }
    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private  boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Desc: 获取性别,1：男，2：女
     * @Author: wangyunquan
     * @Param: [sex]
     * @Return: java.lang.String
     * @Date: 2020/7/27
     */
    private Integer getSex(String sex){
        return "男".equals(sex)?1:"女".equals(sex)?2:null;
    }
    /**
     * @Desc: 获取证件类型，1：护照，2：港澳通行证，4：台湾通行证
     * @Author: wangyunquan
     * @Param: [idType]
     * @Return: java.lang.Integer
     * @Date: 2020/7/27
     */
    private Integer getIdType(String idType){
        if(StringUtils.isBlank(idType))
            return null;
        return idType.contains("护照")?1:idType.contains("港澳")?2:idType.contains("台湾")?4:null;
    }

    /**
     * @Desc: 获取出入境状态，出境：1，入境：2
     * @Author: wangyunquan
     * @Param: [OgeStatus]
     * @Return: java.lang.Integer
     * @Date: 2020/7/27
     */
    private Integer getOgeStatus(String OgeStatus){
        return "出境".equals(OgeStatus)?1:"入境".equals(OgeStatus)?2:null;
    }

}
