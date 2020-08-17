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
import java.util.*;

@Service
public class OmsPubGroupServiceImpl extends ServiceImpl<OmsPubGroupMapper, OmsPubGroupPreApproval> implements OmsPubGroupService {


    @Autowired
    private OmsPubGroupMapper pubGroupMapper;
    @Autowired
    private OmsPubApplyMapper pubApplyMapper;

    @Override
    public PageInfo<OmsPubGroupPreApproval> getPubGroupList(Integer pageNum, Integer pageSize,Map<String,String> param) throws ParseException {
        List<OmsPubGroupPreApproval> resultList = pubGroupMapper.getPubGroupList(param);
        PageUtil.pageHelp(pageNum, pageSize);
        PageInfo<OmsPubGroupPreApproval> pageInfo = new PageInfo(resultList);
        return pageInfo;
    }

    @Override
    public String insertPubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList) {
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        List<OmsPubApplyVO> personList = pubGroupAndApplyList.getOmsPubApplyVOList();
        List<OmsPubApply> applyList = new ArrayList<>();
        int num = personList.size();
        if(num > 0){
            //登录用户信息
            UserInfo userInfo = UserInfoUtil.getUserInfo();
            //团组信息
            String id = UUIDGenerator.getPrimaryKey();
            pubGroup.setId(id);
            pubGroup.setTzrs(num);
            pubGroup.setSqzt(1);
            pubGroup.setCreateUser(userInfo.getId());
            pubGroup.setCreateTime(new Date());
            pubGroupMapper.insertPubGroup(pubGroup);
            //出国人员信息
            for(int i = 0; i < num; i++ ){
                OmsPubApply pubApply = new OmsPubApply();
                pubApply.setId(UUIDGenerator.getPrimaryKey());
                pubApply.setA0100(personList.get(i).getA0100());
                pubApply.setB0100(personList.get(i).getB0100());
                pubApply.setAge(personList.get(i).getAge());
                pubApply.setYspId(id);
                pubApply.setHealth(personList.get(i).getHealth());
                pubApply.setSfsmry(personList.get(i).getSfsmry());
                pubApply.setZjcgqk(personList.get(i).getZjcgqk());
                pubApply.setSqzt(1);
                pubApply.setCreateUser(userInfo.getId());
                pubApply.setCreateTime(new Date());
                applyList.add(pubApply);
            }
            pubApplyMapper.insertPubApplyList(applyList);
            return "添加成功";
        }else{
            return "未选择备案人员";
        }
    }

    @Override
    public void updatePubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList) {
        OmsPubGroupPreApproval pubGroup = pubGroupAndApplyList.getOmsPubGroupPreApproval();
        List<OmsPubApplyVO> personList = pubGroupAndApplyList.getOmsPubApplyVOList();
        List<OmsPubApply> applyList = new ArrayList<>();
        int num = personList.size();
        //登录用户信息
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        pubGroupMapper.updatePubGroup(pubGroup);
        /*if(num > 0){
            //出国人员信息
            for(int i = 0; i < num; i++ ){
                OmsPubApply pubApply = new OmsPubApply();
                pubApply.setId(UUIDGenerator.getPrimaryKey());
                pubApply.setA0100(personList.get(i).getA0100());
                pubApply.setB0100(personList.get(i).getB0100());
                pubApply.setAge(personList.get(i).getAge());
                pubApply.setYspId(id);
                pubApply.setHealth(personList.get(i).getHealth());
                pubApply.setSfsmry(personList.get(i).getSfsmry());
                pubApply.setZjcgqk(personList.get(i).getZjcgqk());
                pubApply.setSqzt(1);
                pubApply.setModifyUser(userInfo.getId());
                pubApply.setModifyTime(new Date());
                applyList.add(pubApply);
            }
            pubApplyMapper.updateById(applyList);
        }*/
    }

    @Override
    public void deletePubGroup(String id) {
        pubGroupMapper.deletePubGroup(id);
    }

    @Override
    public String uploadPubGroupExcel(MultipartFile file, String orgName, String orgId) {
        String msg = "";
        List<Map<String, Object>> list = readExcel(file);
        return msg;
    }

    @Override
    public String checkoutPerson(String idList) {
        return null;
    }

    @Override
    public void insertPerson(String a0100) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        OmsPubApply pubApply = new OmsPubApply();
        pubApply.setId(UUIDGenerator.getPrimaryKey());
        pubApply.setA0100(a0100);
        pubApply.setSqzt(1);
        pubApply.setCreateUser(userInfo.getId());
        pubApply.setCreateTime(new Date());
        pubApplyMapper.insert(pubApply);
    }

    @Override
    public void backoutPerson(String id,String cxyy) {
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.selectById(id);
        pubGroup.setSqzt(0);
        pubGroupMapper.updatePubGroup(pubGroup);
    }

    @Override
    public OmsPubGroupAndApplyList getPubGroupDetailById(String yspId) {
        OmsPubGroupAndApplyList beanList = new OmsPubGroupAndApplyList();
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.getPubGroupDetailById(yspId);
        List<OmsPubApplyVO> pubApplyVOList = pubApplyMapper.selectByYSPId(yspId);
        beanList.setOmsPubGroupPreApproval(pubGroup);
        beanList.setOmsPubApplyVOList(pubApplyVOList);
        return  beanList;
    }

    @Override
    public OmsPubApply getPersonDetailById(String id) {
        return pubApplyMapper.selectById(id);
    }

    @Override
    public void sendTask(String id) {
        OmsPubGroupPreApproval pubGroup = pubGroupMapper.selectById(id);
        pubGroup.setSqzt(2);
        pubGroupMapper.updatePubGroup(pubGroup);
    }

    @Override
    public List<Map<String, String>>  getFlowDetail(String id) {
        return pubGroupMapper.getFlowDetail(id);
    }

    @Override
    public String uploadApproval(MultipartFile file, String id) {
        return "";
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
