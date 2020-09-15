package com.hxoms.modules.omsmobilizingcadres.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsmobilizingcadres.entity.OmsMobilizingcadre;
import com.hxoms.modules.omsmobilizingcadres.mapper.OmsMobilizingcadreMapper;
import com.hxoms.modules.omsmobilizingcadres.service.MobilizingcadreService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class MobilizingcadreServiceImpl implements MobilizingcadreService {

    @Autowired
    OmsMobilizingcadreMapper mobilizingcadreMapper;

    /**
     * 功能描述: <br>
     * 〈添加调整期干部〉
     * @Param:
     * @Return:
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:09
     */
    @Override
    public void insertMobilizingCadre(OmsMobilizingcadre mobilizingCadre) {
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        if (mobilizingCadre == null){
            throw new CustomMessageException("调整期干部为空!");
        }
        if (loginUser == null){
            throw new CustomMessageException("当前登录用户为空!");
        }
        if (mobilizingCadre.getId() != null){
            throw new CustomMessageException("id重复!");
        }
        mobilizingCadre.setId(UUIDGenerator.getPrimaryKey());
        mobilizingCadre.setCreateDate(new Date());
        mobilizingCadre.setCreateUser(loginUser.getId());
        //添加时将状态更改为调整期
        mobilizingCadre.setStatus(String.valueOf(Constants.mobilizing_business[0]));
        mobilizingcadreMapper.insertSelective(mobilizingCadre);

    }

    /**
     * 功能描述: <br>
     * 〈根据ID删除调整期干部〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:15
     */
    @Override
    public void deleteMobilizingCadre(String id) {
        if (id == null){
            throw new CustomMessageException("参数为空!");
        }
        mobilizingcadreMapper.deleteByPrimaryKey(id);

    }

    /**
     * 功能描述: <br>
     * 〈通过人员主键查找调整期干部〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:23
     */
    @Override
    public OmsMobilizingcadre getMobilizingCadreByID(String id) {

        if (id == null){
            throw new CustomMessageException("参数为空!");
        }
        OmsMobilizingcadre omsMobilizingcadre = mobilizingcadreMapper.selectByPrimaryKey(id);
        return omsMobilizingcadre;
    }

    /**
     * 功能描述: <br>
     * 〈通过机构或者条件查找全部调整期干部列表〉
     * @Param: [orgIds, name, status]
     * @Return: java.util.List<com.hxoms.modules.omsmobilizingcadres.entity.OmsMobilizingcadre>
     * @Author: 李逍遥
     * @Date: 2020/5/29 9:45
     */
    @Override
    public PageInfo getAllMobilizingCadre(Integer pageNum, Integer pageSize,List<String> orgIds, String name, String status) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //设置传入页码，以及每页的大小
        PageHelper.startPage(pageNum, pageSize);
        List<LinkedHashMap<String, Object>> list = mobilizingcadreMapper.selectAllMobilizingCadre(orgIds,name,status);
        PageInfo info = new PageInfo(list);
        return info;
    }

    /**
     * 功能描述: <br>
     * 〈每天自动拉取干部信息库信息更改调整期状态〉
     * @Param: [a0100]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/23 11:22
     */
    @Override
    public void updateStatus(String a0100) {
        if (a0100 == null || a0100.equals("")){
            throw new CustomMessageException("参数为空!");
        }
        //进入方法将状态更改为 调整完成
        String s = String.valueOf(Constants.mobilizing_business[1]);
        mobilizingcadreMapper.updateStatusByA0100(a0100,s);

    }

    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [orgIds, name, status, response]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/9/14 11:07
     */
    @Override
    public void exportMobilizingCadre(List<String> orgIds, String name, String status, HttpServletResponse response) {
        List<LinkedHashMap<String, Object>> list = mobilizingcadreMapper.selectAllMobilizingCadre(orgIds,name,status);
        if (list == null || list.size() < 1){
            throw new CustomMessageException("操作失败");
        }else {
            /** 开始导出 */
            //创建HSSFWorkbook对象(excel的文档对象)
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建文件样式对象
            HSSFCellStyle style = wb.createCellStyle();
            //获得字体对象
            HSSFFont font = wb.createFont();
            //建立新的sheet对象（excel的表单）
            HSSFSheet sheet=wb.createSheet("调整期干部名单");
            //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row1=sheet.createRow(0);
            //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            HSSFCell cell=row1.createCell(0);

            //设置标题字体大小
            font.setFontHeightInPoints((short) 16);
            //加粗
            font.setBold(true);
            // 左右居中 
            style.setAlignment(HorizontalAlignment.CENTER);
            // 上下居中 
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFont(font);
            cell.setCellStyle(style);
            //设置标题单元格内容
            cell.setCellValue("调整期干部名单");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0,1,0,9));
            //在sheet里创建第二行
            HSSFRow row2=sheet.createRow(2);
            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("序号");
            row2.createCell(1).setCellValue("单位");
            row2.createCell(2).setCellValue("姓名");
            row2.createCell(3).setCellValue("性别");
            row2.createCell(4).setCellValue("出生年月");
            row2.createCell(5).setCellValue("职务（级）");
            row2.createCell(6).setCellValue("政治面貌");
            row2.createCell(7).setCellValue("调整开始时间");
            row2.createCell(8).setCellValue("调整结束时间");
            row2.createCell(9).setCellValue("状态");
            //在sheet里添加数据

            //创建文件样式对象
            HSSFCellStyle style1 = wb.createCellStyle();
            //获得字体对象
            HSSFFont font1 = wb.createFont();
            //设置单元格字体大小
            font1.setFontHeightInPoints((short) 12);
            //居左
            style1.setAlignment(HorizontalAlignment.LEFT);
            style1.setFont(font1);

            HSSFRow row = null;
            for(int i = 0; i < list.size(); i++){
                row = sheet.createRow(i + 3);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue((String) list.get(i).get("WORK_UNIT"));
                row.createCell(2).setCellValue((String) list.get(i).get("NAME"));
                if ("1".equals((String) list.get(i).get("SEX"))){
                    row.createCell(3).setCellValue("男");
                }else if("2".equals((String) list.get(i).get("SEX"))){
                    row.createCell(3).setCellValue("女");
                }else {
                    row.createCell(3).setCellValue("");
                }
                row.createCell(4).setCellValue((String) list.get(i).get("BIRTH_DATE"));
                row.createCell(5).setCellValue((String) list.get(i).get("JOB"));
                row.createCell(6).setCellValue((String) list.get(i).get("POLITICAL_AFFI"));
                if (list.get(i).get("Adjustment_time") != null){
                    row.createCell(7).setCellValue(sdf.format((Date) list.get(i).get("Adjustment_time")));
                }else {
                    row.createCell(7).setCellValue("");
                }
                if (list.get(i).get("Completion_time") != null){
                    row.createCell(8).setCellValue(sdf.format((Date) list.get(i).get("Completion_time")));
                }else {
                    row.createCell(8).setCellValue("");
                }

                row.createCell(9).setCellValue(Constants.mobilizing_businessName[Integer.parseInt((String) list.get(i).get("status"))]);

                //设置单元格字体大小
                for(int j = 0;j < 9;j++){
                    row.getCell(j).setCellStyle(style1);
                }
            }

            //输出Excel文件
            OutputStream output= null;
            try {
                output = response.getOutputStream();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "utf-8");

                wb.write(output);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
