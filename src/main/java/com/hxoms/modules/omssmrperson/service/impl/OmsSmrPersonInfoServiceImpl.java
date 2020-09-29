package com.hxoms.modules.omssmrperson.service.impl;

import  com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsCommonUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.omssmrperson.entity.*;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrCompareMapper;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrOldInfoMapper;
import com.hxoms.modules.omssmrperson.mapper.OmsSmrPersonInfoMapper;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import com.hxoms.modules.omssmrperson.service.OmsSmrPersonInfoService;
import com.hxoms.modules.omssmrperson.service.OmsSmrRecordInfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsSmrPersonInfoServiceImpl extends ServiceImpl<OmsSmrPersonInfoMapper, OmsSmrPersonInfo> implements OmsSmrPersonInfoService {


    @Autowired
    private OmsSmrPersonInfoMapper smrPersonInfoMapper;
    @Autowired
    private OmsSmrRecordInfoService smrRecordInfoService;
    @Autowired
    private OmsSmrCompareMapper smrCompareMapper;
    @Autowired
    private OmsSmrOldInfoService smrOldInfoService;
    @Autowired
    private OmsSmrOldInfoMapper smrOldInfoMapper;
    @Autowired
    private OmsRegProcpersonInfoService regProcpersonInfoService;
    @Autowired
    private OmsRegProcpersoninfoMapper regProcpersoninfoMapper;

    /**
     * 获取涉密人员信息列表
     */
    @Override
    public PageInfo<OmsRegProcpersoninfo> getSmrPersonInfo(Integer pageNum, Integer pageSize, String idList, OmsSmrPersonInfo omsSmrPersonInfo){
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(omsSmrPersonInfo.getName())) {
            if (isPinyin(omsSmrPersonInfo.getName())) {
                param.put("namePy", "%" + omsSmrPersonInfo.getName() + "%");
            } else {
                param.put("name", "%" + omsSmrPersonInfo.getName() + "%");
            }
        }

        if (!StringUtils.isEmpty(omsSmrPersonInfo.getB0100()))
            param.put("b0100", omsSmrPersonInfo.getB0100());

        if (!StringUtils.isEmpty(omsSmrPersonInfo.getSecretRelatedLevel()))
            param.put("secretLevel", omsSmrPersonInfo.getSecretRelatedLevel());

        if (!StringUtils.isEmpty(omsSmrPersonInfo.getSecretRelatedPost()))
            param.put("secretPost", omsSmrPersonInfo.getSecretRelatedPost());

        if (!StringUtils.isEmpty(omsSmrPersonInfo.getPersonState()))
            param.put("incumbencyStatus", omsSmrPersonInfo.getPersonState());

        if (!StringUtils.isBlank(idList)) {
            String[] idLists = idList.split(",");
            param.put("idList",idLists);
        }
        List<OmsRegProcpersoninfo> resultList = regProcpersoninfoMapper.getSmrPersonInfo(param);
        PageUtil.pageHelp(pageNum, pageSize);
        PageInfo<OmsRegProcpersoninfo> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }

    /**
     * 插入涉密人员信息
     */
    @Override
    public Result insertSmrPersonInfo(String importYear, String b0100, List<OmsSmrOldInfoVO> smrPersonInfoList) throws ParseException, InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        //缓存登记备案人员，用于导入涉密信息后更新综合涉密信息
        HashMap<String, OmsRegProcpersoninfo> hashMapRegs = regProcpersonInfoService.CacheRegProcpersonInfo(null);
        //缓存已有涉密信息，用于导入时比对
        List<OmsSmrOldInfoVO> smrPersonInfos = smrOldInfoService.getSmrOldInfoById(1, 10000, "").getList();
        //以hash表缓存涉密信息，提高检索速度
        HashMap<String, List<OmsSmrOldInfoVO>> hashMapSmrOldInfos = CacheSmrPersonInfo(hashMapRegs, smrPersonInfos);
        boolean firstFlag = smrPersonInfos.size() == 0;//判断是否初次导入

        List<OmsSmrOldInfo> updates = new ArrayList<>();
        List<OmsSmrOldInfo> adds = new ArrayList<>();
        List<OmsSmrRecordInfo> smrRecordInfos = new ArrayList<>();
        HashMap<String, OmsRegProcpersoninfo> calcLeaveSecretPersons = new HashMap<>();

        String sendMessages = "";
        String b0101 = "";

        for (int i = 0; i < smrPersonInfoList.size(); i++) {
            OmsSmrOldInfoVO imp =  smrPersonInfoList.get(i);
            if(StringUilt.stringIsNullOrEmpty(imp.getB0101())==false)
            b0101 = imp.getB0101();

            // 保存涉密信息导入历史记录
            imp.setB0100(b0100);
            imp.setImportYear(importYear);
            OmsSmrRecordInfo smrRecordInfo = CreateSmrRecordInfo(null, imp);
            smrRecordInfos.add(smrRecordInfo);

            //未登记备案人员
            String a0100=imp.getA0100();
            if (StringUilt.stringIsNullOrEmpty(a0100)) continue;

            //如果不是首次导入，并且不在职，不导入
            if (firstFlag == false && imp.getPersonState().equals("在编") == false) continue;

            //如果不在编，并且脱密结束时间不为空，且已经过了脱密期，不导入
            if (imp.getPersonState().equals("在编") == false &&
                    imp.getFinishDate() != null &&
                    imp.getFinishDate().before(new Date())) continue;


            //检查当前单位是否存在该涉密人员信息
            OmsSmrOldInfoVO existsSmr = null;
            List<OmsSmrOldInfoVO> smrOldInfoVO = hashMapSmrOldInfos.get(a0100);
            for (OmsSmrOldInfoVO smr : smrOldInfoVO
            ) {
                if (smr.getB0100().equals(b0100)) {
                    existsSmr = smr;
                    break;
                }
            }

            //汉字涉密等级转换成编码
            imp.setSecretRelatedLevel(getSecretLevel(imp.getSecretRelatedLevel()));

            //单位和涉密等级没有改变，不做任何操作
            if (existsSmr != null &&
                    StringUilt.equalsWithNull(existsSmr.getSecretRelatedLevel(),imp.getSecretRelatedLevel())) continue;


            //不存在该涉密信息
            if (existsSmr == null) {
                imp.setId(UUIDGenerator.getPrimaryKey());
                adds.add(imp);
            }
            //涉密等级提高了,更新涉密等级,登记备案库的涉密为null也认为是提高了
            else if (existsSmr.getSecretRelatedLevel()==null||
                    (existsSmr.getSecretRelatedLevel()!=null&&imp.getSecretRelatedLevel()!=null&&
                    Integer.parseInt(existsSmr.getSecretRelatedLevel()) < Integer.parseInt(imp.getSecretRelatedLevel()))) {
                existsSmr.setSecretRelatedLevel(imp.getSecretRelatedLevel());
                updates.add(existsSmr);
            }
            //涉密等级降低了,启动脱密期
            else if (existsSmr.getSecretRelatedLevel()!=null&&imp.getSecretRelatedLevel()!=null&&
                    Integer.parseInt(existsSmr.getSecretRelatedLevel()) > Integer.parseInt(imp.getSecretRelatedLevel())) {
                //涉密等级发生变化，插入新的涉密信息
                OmsSmrOldInfoVO newSmr = new OmsSmrOldInfoVO();
                BeanUtils.copyProperties(existsSmr, newSmr);
                newSmr.setId(UUIDGenerator.getPrimaryKey());
                newSmr.setSecretRelatedLevel(imp.getSecretRelatedLevel());
                newSmr.setPost(imp.getPost());
                newSmr.setSecretRelatedPost(imp.getSecretRelatedPost());
                newSmr.setImportYear(importYear);
                adds.add(newSmr);

                //涉密等级发生变化，原涉密信息启动脱密期
                existsSmr.setSfqr("0");
                existsSmr.setStartDate(new Date());
                existsSmr.setFinishDate(CalcLeaveSecretDeadline(existsSmr.getStartDate(), existsSmr.getSecretRelatedLevel()));
                existsSmr.setQrFinishDate(existsSmr.getFinishDate());
                existsSmr.setQrSecretRelatedLevel(existsSmr.getSecretRelatedLevel());
                existsSmr.setQrSecretRelatedPost(existsSmr.getSecretRelatedPost());
                existsSmr.setQrStartDate(existsSmr.getStartDate());
                updates.add(existsSmr);

                sendMessages = "贵单位有干部产生了新的脱密期，请点此链接前往确认。";
            }
            if (calcLeaveSecretPersons.containsKey(a0100) == false) {
                OmsRegProcpersoninfo regProcpersoninfo = hashMapRegs.get(a0100);
                calcLeaveSecretPersons.put(imp.getA0100(), regProcpersoninfo);
            }
        }
        if (smrRecordInfos.size() > 0) {
            smrRecordInfoService.deleteByB0100AndYear(b0100,importYear);
            smrRecordInfoService.saveBatch(smrRecordInfos);
        }
        if (adds.size() > 0)
            smrOldInfoService.saveBatch(adds);
        if (updates.size() > 0)
            smrOldInfoService.updateBatchById(updates);

        //通知经办人确认脱密期
        if (sendMessages.equals("") == false)
            OmsCommonUtil.SendMessage(sendMessages, "3", b0100, b0101);

        //计算综合涉密信息
        if (calcLeaveSecretPersons.size() > 0)
            CalcOverall(hashMapRegs, calcLeaveSecretPersons);

        return Result.success("本次新增涉密信息(" + adds.size() + ")条，更新涉密信息(" + updates.size() + ")条。");
    }

    public Date CalcLeaveSecretDeadline(Date startDate, String secretLevel) {
        if(StringUilt.stringIsNullOrEmpty(secretLevel)) return startDate;
        int deadline = Integer.parseInt(secretLevel);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, deadline);
        return calendar.getTime();
    }

    public String getSecretLevel(String srLevel) {
        String result = OmsCommonUtil.SECRET_LEVEL_STATUS[0];
        if (StringUtils.isNotBlank(srLevel)) {
            if (OmsCommonUtil.SECRET_LEVEL_STATUS_NAME[1].equals(srLevel)) {
                result = OmsCommonUtil.SECRET_LEVEL_STATUS[1];
            }
            if (OmsCommonUtil.SECRET_LEVEL_STATUS_NAME[2].equals(srLevel)) {
                result = OmsCommonUtil.SECRET_LEVEL_STATUS[2];
            }
            if (OmsCommonUtil.SECRET_LEVEL_STATUS_NAME[3].equals(srLevel)) {
                result = OmsCommonUtil.SECRET_LEVEL_STATUS[3];
            }
        }
        return result;
    }

    /**
     * @param smrOldInfoVO
     * @description:记录本次导入的涉密人员信息
     * @author:杨波
     * @date:2020-09-19 * @param smrRecordInfo
     * @return:com.hxoms.modules.omssmrperson.entity.OmsSmrRecordInfo
     **/
    public OmsSmrRecordInfo CreateSmrRecordInfo(OmsSmrRecordInfo smrRecordInfo,
                                                OmsSmrOldInfoVO smrOldInfoVO) {

        if (smrRecordInfo == null)
            smrRecordInfo = new OmsSmrRecordInfo();
        if (smrOldInfoVO == null) return null;

        smrRecordInfo.setId(UUIDGenerator.getPrimaryKey());
        smrRecordInfo.setB0100(smrOldInfoVO.getB0100());
        smrRecordInfo.setName(smrOldInfoVO.getA0101());
        smrRecordInfo.setSex(smrOldInfoVO.getSex());
        smrRecordInfo.setBirthDate(smrOldInfoVO.getBirthDay());
        smrRecordInfo.setIdCardNumber(smrOldInfoVO.getIdCardNumber());
        smrRecordInfo.setNation(smrOldInfoVO.getNation());
        smrRecordInfo.setA0141(smrOldInfoVO.getA0141());
        smrRecordInfo.setPost(smrOldInfoVO.getPost());
        smrRecordInfo.setSecretRelatedPost(smrOldInfoVO.getSecretRelatedPost());
        smrRecordInfo.setSecretRelatedLevel(smrOldInfoVO.getSecretRelatedLevel());
        smrRecordInfo.setStartDate(smrOldInfoVO.getStartDate());
        smrRecordInfo.setFinishDate(smrOldInfoVO.getFinishDate());
        smrRecordInfo.setImportUserId(UserInfoUtil.getUserId());
        smrRecordInfo.setImportDate(new Date());
        smrRecordInfo.setImportYear(smrOldInfoVO.getImportYear());
//      String isMatching = (StringUilt.stringIsNullOrEmpty(smrOldInfoVO.getA0100()) ? "0" : "1");
        smrRecordInfo.setIsMatching(isMatching(smrOldInfoVO.getA0100(),smrOldInfoVO.getB0100(),smrOldInfoVO.getIdCardNumber()));

        return smrRecordInfo;
    }

    /**
     * @description:计算综合涉密信息
     * @author:杨波
     * @date:2020-09-19 * @param hashMapRegs 登记备案人员，如果为空，系统自己查询所有登记备案人员
     * * @param calcLeaveSecretPersons 需要重新计算综合涉密信息的登记备案人员
     * @return:void
     **/
    public void CalcOverall(HashMap<String, OmsRegProcpersoninfo> hashMapRegs,
                            HashMap<String, OmsRegProcpersoninfo> calcLeaveSecretPersons) throws ParseException {
        //以hash表缓存涉密信息，提高检索速度
        HashMap<String, List<OmsSmrOldInfoVO>> hashMapSmrOldInfos = CacheSmrPersonInfo(hashMapRegs, null);

        List<OmsRegProcpersoninfo> updates = new ArrayList<>();
        Set<String> keys = calcLeaveSecretPersons.keySet();
        for (String key : keys
        ) {
            OmsRegProcpersoninfo regProcpersoninfo = calcLeaveSecretPersons.get(key);

            List<OmsSmrOldInfoVO> smrOldInfoVOS = hashMapSmrOldInfos.get(regProcpersoninfo.getA0100());
            if (smrOldInfoVOS == null || smrOldInfoVOS.size() == 0) continue;

            int maxSecretLevel = 0;
            OmsSmrOldInfoVO maxSmr = null;
            for (OmsSmrOldInfoVO smrOldInfoVO : smrOldInfoVOS
            ) {
                //过了脱密期
                if (smrOldInfoVO.getFinishDate() != null &&
                        smrOldInfoVO.getFinishDate().before(new Date())) continue;

                //有确认过的涉密等级，以确认过的为准
                int secretLevel = Integer.parseInt(smrOldInfoVO.getSecretRelatedLevel());
                if (StringUilt.stringIsNullOrEmpty(smrOldInfoVO.getQrSecretRelatedLevel()) == false)
                    secretLevel = Integer.parseInt(smrOldInfoVO.getQrSecretRelatedLevel());

                if (secretLevel > maxSecretLevel) {

                    maxSmr = smrOldInfoVO;
                    maxSecretLevel = secretLevel;

                } else if (secretLevel == maxSecretLevel &&
                        "在编".equals(smrOldInfoVO.getPersonState())) {
                    maxSmr = smrOldInfoVO;
                }
            }
            if (maxSmr != null) {
                regProcpersoninfo.setSecretLevel(String.valueOf(maxSecretLevel));
                regProcpersoninfo.setSecretPost(maxSmr.getSecretRelatedPost());
                updates.add(regProcpersoninfo);
            }
        }
        if (updates.size() > 0)
            regProcpersonInfoService.updateBatchById(updates);
    }

    /**
     * 修改涉密人员信息
     */
    @Override
    public Object updateSmrPersonInfo(OmsSmrPersonInfo smrPersonInfo) {
        return baseMapper.updateById(smrPersonInfo);
    }

    /**
     * 删除涉密人员信息
     */
    @Override
    public Object deleteSmrPersonInfo(String id) {
        return baseMapper.deleteById(id);
    }

    /**
     * @param smrPersonInfos 为null时，系统自动查询所有涉密人员
     * @description:缓存涉密人员
     * @author:杨波
     * @date:2020-09-19 * @param hashMapRegs 登记备案人员，为null时，自动查询所有登记备案人员
     * @return:java.util.HashMap<java.lang.String,java.util.List<com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO>>
     **/
    public HashMap<String, List<OmsSmrOldInfoVO>> CacheSmrPersonInfo(HashMap<String, OmsRegProcpersoninfo> hashMapRegs,
                                                                     List<OmsSmrOldInfoVO> smrPersonInfos) throws ParseException {

        if (hashMapRegs == null)
            hashMapRegs = regProcpersonInfoService.CacheRegProcpersonInfo(null);
        if (smrPersonInfos == null)
            smrPersonInfos = smrOldInfoService.getSmrOldInfoById(1, 10000, "").getList();

        //为每个登记备案干部建立涉密信息集合缓存
        HashMap<String, List<OmsSmrOldInfoVO>> hashMapSmrOldInfos = new HashMap<>();
        Set<String> keys = hashMapRegs.keySet();
        for (String key : keys
        ) {
            OmsRegProcpersoninfo regProcpersoninfo = hashMapRegs.get(key);
            if (StringUilt.stringIsNullOrEmpty(regProcpersoninfo.getA0100())) continue;//排除配偶子女和公安数据
            hashMapSmrOldInfos.put(regProcpersoninfo.getA0100(), new ArrayList<>());
        }
        //根据A0100获取涉密信息集合后，将涉密信息加入该集合
        for (OmsSmrOldInfoVO smrOldInfoVO : smrPersonInfos
        ) {
            List<OmsSmrOldInfoVO> smrOldInfos = hashMapSmrOldInfos.get(smrOldInfoVO.getA0100());
            if (smrOldInfos == null) continue;
            smrOldInfos.add(smrOldInfoVO);
        }
        return hashMapSmrOldInfos;
    }

    /**
     * 导入涉密人员信息列表
     */
    @Override
    public Result uploadSmrExcel(MultipartFile file, String importYear, String b0100) {

        //判断是否首次导入，首次导入时需要导入非在职人员未过脱密期的涉密信息
        boolean firstFlag = Integer.parseInt(smrOldInfoMapper.getSmrCount()) == 0;

        //缓存登记备案人员，用于通过姓名和身份号判断是否省管干部
        HashMap<String, OmsRegProcpersoninfo> hashMapRegs = new HashMap<>();
        regProcpersonInfoService.CacheRegProcpersonInfo(hashMapRegs);

        //缓存登记备案人员职务，用于判断是否在当前机构任职
        HashMap<String, Map> hashMapPosts = regProcpersonInfoService.CachePost();

        //从excel读取涉密人员信息
        List<OmsSmrOldInfoVO> list = readExcel(file);

        if (list.size() == 0) {
            return Result.error("选择的涉密人员统计表格式不正确，请下载模板后，按模板格式调整！");
        }

        int OrgNum = 0;
        for (int i = 0; i < list.size(); i++) {
            OmsSmrOldInfoVO dataMap = list.get(i);

            OmsRegProcpersoninfo regProcpersoninfo = hashMapRegs.get(dataMap.getA0101() + dataMap.getIdCardNumber());
            if (regProcpersoninfo == null) {
                dataMap.setMsg("未在本系统登记备案，" + dataMap.getMsg());
                continue;
            }
            Map omsRegProcpersoninfo = hashMapPosts.get(b0100 + regProcpersoninfo.getA0100());
            if (omsRegProcpersoninfo == null) {
                dataMap.setMsg("未在当前机构任职，" + dataMap.getMsg());
                continue;
            }
            OrgNum++;
            dataMap.setImportYear(importYear);
            dataMap.setB0100(b0100);
            dataMap.setB0101(omsRegProcpersoninfo.get("b0101").toString());
            dataMap.setA0100(omsRegProcpersoninfo.get("a0100").toString());

            //如果不是首次导入，并且不在职，不导入
            if (firstFlag == false && dataMap.getPersonState().equals("在编") == false) {
                dataMap.setMsg("非首次导入且非在职人员不导入，" + dataMap.getMsg());
            }

            //如果不在编，并且脱密结束时间不为空，且已经过了脱密期，不导入
            if (dataMap.getPersonState().equals("在编") == false &&
                    dataMap.getFinishDate() != null &&
                    dataMap.getFinishDate().before(new Date())) {
                dataMap.setMsg("已经过了脱密期不导入，" + dataMap.getMsg());
            }

        }
        if (OrgNum < 1) {
            return Result.error("所导入涉密人员均不在选择的单位，请确认所选单位是否正确！");
        }
        return Result.success(list);
    }

    /**
     * 导出涉密人员信息
     */
    @Override
    public void exportSmrPersonInfo(String idList, OmsSmrPersonInfo smrPersonInfo,HttpServletResponse response){
        List<OmsRegProcpersoninfo> list = getSmrPersonInfo(1,10000,idList,smrPersonInfo).getList();

        /*---------------------------------------------导出------------------------------------------------------------*/
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("涉密人员信息表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);

        //设置标题字体大小
        font.setFontHeightInPoints((short) 16);
        font.setBold(true); //加粗
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中   
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中   
        style.setFont(font);
        cell.setCellStyle(style);
        //设置标题单元格内容
        cell.setCellValue("涉密人员信息表");

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 10));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(2);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("序号");
        row2.createCell(1).setCellValue("单位");
        row2.createCell(2).setCellValue("姓名");
        row2.createCell(3).setCellValue("性别");
        row2.createCell(4).setCellValue("职务职级");
        row2.createCell(5).setCellValue("政治面貌");
        row2.createCell(6).setCellValue("在职状态");
        row2.createCell(7).setCellValue("涉密岗位");
        row2.createCell(8).setCellValue("涉密等级");
        row2.createCell(9).setCellValue("脱密期管理开始日期");
        row2.createCell(10).setCellValue("脱密期管理终止日期");
        //在sheet里添加数据

        //创建文件样式对象
        HSSFCellStyle style1 = wb.createCellStyle();
        //获得字体对象
        HSSFFont font1 = wb.createFont();
        //设置单元格字体大小
        font1.setFontHeightInPoints((short) 13);
        style1.setAlignment(HorizontalAlignment.LEFT);// 居左  
        style1.setFont(font1);

        HSSFRow row = null;
        for (int i = 0; i < list.size(); i++) {
            OmsRegProcpersoninfo regProcpersoninfo = list.get(i);
            row = sheet.createRow(i + 3);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(regProcpersoninfo.getWorkUnit());
            row.createCell(2).setCellValue(regProcpersoninfo.getName());
            String sex = "";
            if(!StringUtils.isBlank(regProcpersoninfo.getSex())){
                if(regProcpersoninfo.getSex().equals("1"))  sex = "男";
                else sex = "女";
            }
            row.createCell(3).setCellValue(sex);
            row.createCell(4).setCellValue(regProcpersoninfo.getPost());
            row.createCell(5).setCellValue(regProcpersoninfo.getPoliticalAffiname());
            row.createCell(6).setCellValue(regProcpersoninfo.getIncumbencyStatus());
            row.createCell(7).setCellValue(regProcpersoninfo.getSecretPost());
            row.createCell(8).setCellValue(regProcpersoninfo.getSecretLevel());
            row.createCell(9).setCellValue(regProcpersoninfo.getDecryptStartdate());
            row.createCell(10).setCellValue(regProcpersoninfo.getDecryptEnddate());
            //设置单元格字体大小
            for (int j = 0; j < 8; j++) {
                row.getCell(j).setCellStyle(style1);
            }
        }
        try{
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode("涉密人员信息"+date+".xls", "utf-8")));
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
            throw new CustomMessageException("导出失败，原因："+e.getMessage());
        }
    }

    /**
     * 获取漏报涉密人员机构
     */
    @Override
    public Result getFailReportOrg(String importYear) {
        if(StringUtils.isBlank(importYear)){
            return Result.error("请填写汇总年份后查询！");
        }
        List<OmsSmrPersonInfo> results = smrPersonInfoMapper.getFailReportOrg(importYear);
        return Result.success(results);
    }

    /**
     * 导出漏报涉密人员机构
     */
    @Override
    public void exportFailReportOrg(String importYear, HttpServletResponse response) {
        if(StringUtils.isBlank(importYear)){
            throw new CustomMessageException("参数为空！");
        }
        List<OmsSmrPersonInfo> list = smrPersonInfoMapper.getFailReportOrg(importYear);
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建文件样式对象
        HSSFCellStyle style = wb.createCellStyle();
        //获得字体对象
        HSSFFont font = wb.createFont();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("漏报涉密人员机构");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);

        //设置标题字体大小
        font.setFontHeightInPoints((short) 16);
        font.setBold(true); //加粗
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中   
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中   
        style.setFont(font);
        cell.setCellStyle(style);
        //设置标题单元格内容
        cell.setCellValue("漏报涉密人员机构");

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("序号");
        row2.createCell(1).setCellValue("单位");
        //在sheet里添加数据

        //创建文件样式对象
        HSSFCellStyle style1 = wb.createCellStyle();
        //获得字体对象
        HSSFFont font1 = wb.createFont();
        //设置单元格字体大小
        font1.setFontHeightInPoints((short) 13);
        style1.setAlignment(HorizontalAlignment.LEFT);// 居左  
        style1.setFont(font1);

        HSSFRow row = null;
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(list.get(i).getB0101());
            //设置单元格字体大小
            for (int j = 0; j < 8; j++) {
                row.getCell(j).setCellStyle(style1);
            }
        }

        //输出Excel文件
        try{
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode("漏报涉密人员机构"+date+".xls", "utf-8")));
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
            throw new CustomMessageException("导出失败，原因："+e.getMessage());
        }
    }

    /**
     * 批量修改涉密人员信息（确认脱密期、涉密人员信息维护）
     */
    @Override
    public boolean updateSmrPersonList(List<OmsSmrPersonInfo> smrPersonInfoList) {
        return smrPersonInfoMapper.updateSmrPersonList(smrPersonInfoList) > 0 ? true : false;
    }

    /**
     * 获取涉密人员信息维护列表
     */
    @Override
    public Map<String, Object> getSmrMaintainList(OmsSmrPersonInfo smrPersonInfo) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Map<String, Object> param = new HashMap<>();
        if (!StringUtils.isEmpty(smrPersonInfo.getName())) {
            if (isPinyin(smrPersonInfo.getName())) {
                param.put("namePy", "%" + smrPersonInfo.getName() + "%");
            } else {
                param.put("name", "%" + smrPersonInfo.getName() + "%");
            }
        }
        if (!StringUtils.isEmpty(smrPersonInfo.getSecretRelatedPost())) {
            param.put("secretRelatedPost", smrPersonInfo.getSecretRelatedPost());
        }
        param.put("orgId", UserInfoUtil.getUserInfo().getOrgId());
        List<OmsSmrPersonInfo> list = smrPersonInfoMapper.getSmrMaintainList(param);
        resultMap.put("result", list);
        return resultMap;
    }

    /**
     * 读取到Excel表格的数据(导入用)
     *
     * @return List<OmsSmrPersonInfo>
     */
    public static List<OmsSmrOldInfoVO> readExcel(MultipartFile file) {
        List<OmsSmrOldInfoVO> list = new ArrayList<>();
        HSSFWorkbook workbook = null;
        try {
            // 读取Excel文件
            POIFSFileSystem inputStream = new POIFSFileSystem(file.getInputStream());
            //InputStream inputStream = new FileInputStream(filePath);
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
            //判断导入模板是否正确
            if (!"姓名".equals(hssfSheet.getRow(2).getCell(1).toString())) {
                return list;
            }
            if (!"身份证号码".equals(hssfSheet.getRow(2).getCell(6).toString())) {
                return list;
            }
            if (!"涉密岗位".equals(hssfSheet.getRow(2).getCell(7).toString())) {
                return list;
            }
            if (!"涉密等级".equals(hssfSheet.getRow(2).getCell(9).toString())) {
                return list;
            }
            if (!"保密复审时间".equals(hssfSheet.getRow(2).getCell(12).toString())) {
                return list;
            }
            if (!"脱密期管理开始日期".equals(hssfSheet.getRow(2).getCell(18).toString())) {
                return list;
            }
            if (!"脱密期管理终止日期".equals(hssfSheet.getRow(2).getCell(19).toString())) {
                return list;
            }
            // 循环行
            for (int rowNum = 3; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                //姓名
                HSSFCell cell = hssfRow.getCell(1);
                if (cell == null) {
                    continue;
                }
                //丢掉空行
                if(StringUilt.stringIsNullOrEmpty(cell.getStringCellValue())) continue;

                // 将单元格中的内容存入集合
                OmsSmrOldInfoVO map = new OmsSmrOldInfoVO();
                String msg = "";

                map.setA0101(cell.getStringCellValue());
                //性别
                cell = hssfRow.getCell(2);
                if (cell == null) {
                    continue;
                }
                map.setSex(cell.getStringCellValue());
                //出生年月
                cell = hssfRow.getCell(3);
                if (cell == null) {
                    continue;
                }
                String birthday=cell.getStringCellValue();
                if(StringUilt.stringIsNullOrEmpty(birthday)==false)
                    birthday=birthday.replaceAll("-",".");
                map.setBirthDay(birthday);
                //民族
                cell = hssfRow.getCell(4);
                if (cell == null) {
                    continue;
                }
                map.setNation(cell.getStringCellValue());
                //政治面貌
                cell = hssfRow.getCell(5);
                if (cell == null) {
                    continue;
                }
                map.setA0141(cell.getStringCellValue());
                //身份证号
                cell = hssfRow.getCell(6);
                if (cell == null) {
                    continue;
                }
                map.setIdCardNumber(cell.getStringCellValue());
                if (map.getIdCardNumber() == null || map.getIdCardNumber().length() != 18)
                    msg += "身份证号格式不正确，";

                //涉密岗位
                cell = hssfRow.getCell(7);
                if (cell == null) {
                    continue;
                }
                map.setSecretRelatedPost(cell.getStringCellValue());
                //职务（级）或职称
                cell = hssfRow.getCell(8);
                if (cell == null) {
                    continue;
                }
                map.setPost(cell.getStringCellValue());
                //涉密等级
                cell = hssfRow.getCell(9);
                if (cell == null) {
                    continue;
                }
                map.setSecretRelatedLevel(cell.getStringCellValue());
                //人员类型
                cell = hssfRow.getCell(11);
                if (cell == null) {
                    continue;
                }
                map.setPersonState(cell.getStringCellValue());
                //保密复审时间
                cell = hssfRow.getCell(12);
                if (cell == null) {
                    continue;
                }
                if (StringUilt.stringIsNullOrEmpty(cell.getStringCellValue()) == false) {
                    Date date = formatDate(cell.getStringCellValue());
                    if (date != null)
                        map.setSecretReviewDate(date);
                    else
                        msg += "保密复审时间格式不正确，";
                }
                //脱密期管理开始日期
                cell = hssfRow.getCell(18);
                if (cell == null) {
                    continue;
                }
                if (StringUilt.stringIsNullOrEmpty(cell.getStringCellValue()) == false) {
                    Date date = formatDate(cell.getStringCellValue());
                    if (date != null)
                        map.setStartDate(date);
                    else
                        msg += "脱密期管理开始日期格式不正确，";
                }
                //脱密期管理终止日期
                cell = hssfRow.getCell(19);
                if (cell == null) {
                    continue;
                }
                if (StringUilt.stringIsNullOrEmpty(cell.getStringCellValue()) == false) {
                    Date date = formatDate(cell.getStringCellValue());
                    if (date != null)
                        map.setFinishDate(date);
                    else
                        msg += "脱密期管理终止日期格式不正确，";
                }

                if ("".equals(msg) == false)
                    map.setMsg(msg);

                map.setSfqr("0");
                map.setB0101("");//解决null排序随机性错误问题
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 判断是否是姓名是否为拼音
     *
     * @param keyword
     */
    public boolean isPinyin(String keyword) {
        int len = keyword.toLowerCase().length();
        for (int i = 0; i < len; i++) {
            char ch = keyword.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * 格式化日期为'/'
     *
     * @param date
     * @return Date
     */
    public static Date formatDate(String date) {
        //转换日期格式为我们需要的格式
        String srDateNew = "";
        if (date.contains("-")) {
            srDateNew = date.replaceAll("-", "/");
        }
        if (date.contains(".")) {
            srDateNew = date.replaceAll(".", "/");
        }
        if (date.contains("年")) {
            srDateNew = date.replaceAll("年", "/");
            if (date.contains("月")) {
                srDateNew = date.replaceAll("月", "/");
                if (date.contains("日")) {
                    srDateNew = date.replaceAll("日", "");
                }
            }
        }
        //格式化日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date newDate = null;
        try {
            if (!"".equals(srDateNew)) {
                newDate = simpleDateFormat.parse(srDateNew);
            } else {
                newDate = simpleDateFormat.parse(date);
            }
        } catch (ParseException px) {
            px.printStackTrace();
        }
        return newDate;
    }

    /**
     * 格式化日期为'/'
     *
     * @param
     * @return String
     */
    private String isMatching(String a0100, String b0100, String idCardNumber){
        if(StringUilt.stringIsNullOrEmpty(a0100)){
            OmsSmrCompare smrCompare = smrCompareMapper.getMatchingDate(b0100,idCardNumber);
            if(smrCompare == null)
                return "0";
        }
        return "1";
    }
}
