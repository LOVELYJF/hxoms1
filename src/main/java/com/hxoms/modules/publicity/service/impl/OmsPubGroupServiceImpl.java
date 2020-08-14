package com.hxoms.modules.publicity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsCommonUtil;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.publicity.entity.*;
import com.hxoms.modules.publicity.mapper.OmsPubApplyMapper;
import com.hxoms.modules.publicity.mapper.OmsPubGroupMapper;
import com.hxoms.modules.publicity.service.OmsPubGroupService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OmsPubGroupServiceImpl extends ServiceImpl<OmsPubGroupMapper, OmsPubGroupPreApproval> implements OmsPubGroupService {


    @Autowired
    private OmsPubGroupMapper pubGroupMapper;

    private OmsPubApplyMapper pubApplyMapper;

    @Override
    public PageInfo<OmsPubGroupPreApproval> getPubGroupList(Integer pageNum, Integer pageSize,Map<String,String> param) throws ParseException {
        List<OmsPubGroupPreApproval> resultList = pubGroupMapper.getPubGroupList(param);
        PageUtil.pageHelp(pageNum, pageSize);
        PageInfo<OmsPubGroupPreApproval> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }

    @Override
    public Object insertPubGroup(OmsPubGroupPreApproval pubGroup, List<OmsPubApply> personList) {
        int num = personList.size();
        if(num > 0){
            //登录用户信息
            UserInfo userInfo = UserInfoUtil.getUserInfo();
            //团组信息
            pubGroup.setId(UUIDGenerator.getPrimaryKey());
            pubGroup.setTzrs(num);
            pubGroup.setSqzt(1);
            pubGroup.setCreateUser(userInfo.getId());
            pubGroup.setCreateTime(new Date());
            pubGroupMapper.insertPubGroup(pubGroup);
            //出国人员信息
            for(int i = 0; i < num; i++ ){
                personList.get(i).setId(UUIDGenerator.getPrimaryKey());
                personList.get(i).setSqzt(1);
                personList.get(i).setCreateUser(userInfo.getId());
                personList.get(i).setCreateTime(new Date());
            }
            pubApplyMapper.insertPubApplyList(personList);
            return "添加成功";
        }else{
            return "未选择备案人员";
        }
    }

    @Override
    public Object updatePubGroup(OmsPubGroupPreApproval pubGroup, List<OmsPubApply> personList) {
        return pubGroupMapper.updatePubGroup(pubGroup);
    }

    @Override
    public Object deletePubGroup(String id) {
        return pubGroupMapper.deletePubGroup(id);
    }

    @Override
    public Object uploadPubGroupExcel(MultipartFile file, String orgName, String orgId) {
        String msg = "";
        List<Map<String, Object>> list = readExcel(file);
        return msg;
    }

    @Override
    public Object checkoutPerson(String idList) {
        return null;
    }

    @Override
    public Object insertPerson(String a0100) {
        OmsPubApply pubApply = new OmsPubApply();
        pubApply.setA0100(a0100);
        return pubApplyMapper.insert(pubApply);
    }

    @Override
    public void backoutPerson(String id,String cxyy) {
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.selectById(id);
        pubGroup.setSqzt(0);
        pubGroupMapper.updatePubGroup(pubGroup);
    }

    @Override
    public OmsPubGroupAndApplyList getPubGroupDetailById(String id) {
        OmsPubGroupAndApplyList beanList = new OmsPubGroupAndApplyList();
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.selectById(id);
        List<OmsPubApplyVO> pubApplyVOList = pubApplyMapper.selectByYSPId(id);
        beanList.setOmsPubGroupPreApproval(pubGroup);
        beanList.setOmsPubApplyVOList(pubApplyVOList);
        return  beanList;
    }

    @Override
    public OmsPubApply getPersonDetailById(String id) {
        return pubApplyMapper.selectById(id);
    }

    @Override
    public Object sendTask(String id) {
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.selectById(id);
        pubGroup.setSqzt(2);
        return pubGroupMapper.updatePubGroup(pubGroup);
    }

    @Override
    public List<Map<String, String>>  getFlowDetail(String id) {
        return pubGroupMapper.getFlowDetail(id);
    }

    @Override
    public Object uploadApproval(MultipartFile file, String id) {
        return null;
    }

    @Override
    public List<Map<String,String>> getNumByStatus(String bazt) {
        return pubGroupMapper.getNumByStatus(bazt);
    }

    /**
     * 读取到Excel表格的数据(导入用)
     * @return List<OmsSmrPersonInfo>
     */
    public static List<Map<String, Object>> readExcel(MultipartFile file) {
        List<Map<String, Object>> list = new ArrayList<>();
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
            if(!"姓名".equals(hssfSheet.getRow(2).getCell(1).toString())){
                return list;
            }
            if(!"身份证号码".equals(hssfSheet.getRow(2).getCell(6).toString())){
                return list;
            }
            if(!"涉密岗位".equals(hssfSheet.getRow(2).getCell(7).toString())){
                return list;
            }
            if(!"涉密等级".equals(hssfSheet.getRow(2).getCell(9).toString())){
                return list;
            }
            if(!"保密复审时间".equals(hssfSheet.getRow(2).getCell(12).toString())){
                return list;
            }
            if(!"脱密期管理开始日期".equals(hssfSheet.getRow(2).getCell(18).toString())){
                return list;
            }
            if(!"脱密期管理终止日期".equals(hssfSheet.getRow(2).getCell(19).toString())){
                return list;
            }
            // 循环行
            for (int rowNum = 3; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }
                // 将单元格中的内容存入集合
                Map<String, Object> map = new HashMap<String, Object>();
                //姓名
                HSSFCell cell = hssfRow.getCell(1);
                if (cell == null) {
                    continue;
                }
                map.put("name",cell.getStringCellValue());
                //性别
                cell = hssfRow.getCell(2);
                if(cell == null){
                    continue;
                }
                map.put("sex",cell.getStringCellValue());
                //出生年月
                cell = hssfRow.getCell(3);
                if(cell == null){
                    continue;
                }
                map.put("birthDay",cell.getStringCellValue());
                //民族
                cell = hssfRow.getCell(3);
                if(cell == null){
                    continue;
                }
                map.put("nation",cell.getStringCellValue());
                //政治面貌
                cell = hssfRow.getCell(5);
                if(cell == null){
                    continue;
                }
                map.put("a0141",cell.getStringCellValue());
                //身份证号
                cell = hssfRow.getCell(6);
                if (cell == null) {
                    continue;
                }
                map.put("idCardNumber",cell.getStringCellValue());
                //涉密岗位
                cell = hssfRow.getCell(7);
                if (cell == null) {
                    continue;
                }
                map.put("secretRelatedPost",cell.getStringCellValue());
                //职务（级）或职称
                cell = hssfRow.getCell(8);
                if (cell == null) {
                    continue;
                }
                map.put("post",cell.getStringCellValue());
                //涉密等级
                cell = hssfRow.getCell(9);
                if (cell == null) {
                    continue;
                }
                map.put("secretRelatedLevel", OmsCommonUtil.toSecretLevelStatus(cell.getStringCellValue()));
                //人员类型
                cell = hssfRow.getCell(11);
                if (cell == null) {
                    continue;
                }
                map.put("personState",cell.getStringCellValue());
                //保密复审时间
                cell = hssfRow.getCell(12);
                if (cell == null) {
                    continue;
                }
                map.put("secretReviewDate",cell.getStringCellValue());
                //脱密期管理开始日期
                cell = hssfRow.getCell(18);
                if (cell == null) {
                    continue;
                }
                map.put("startDate", cell.getStringCellValue());
                //脱密期管理终止日期
                cell = hssfRow.getCell(19);
                if (cell == null) {
                    continue;
                }
                map.put("finishDate", cell.getStringCellValue());

                list.add(map);
            }
        }
        return list;
    }
}
