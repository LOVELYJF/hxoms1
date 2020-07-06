package com.hxoms.modules.keySupervision.violationDiscipline.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.modules.keySupervision.violationDiscipline.entity.OmsSupViolationDiscipline;
import com.hxoms.modules.keySupervision.violationDiscipline.mapper.OmsSupViolationDisciplineMapper;
import com.hxoms.modules.keySupervision.violationDiscipline.service.OmsSupViolationDisciplineService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.support.b01.mapper.B01Mapper;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**b>
 * @author luoshuai
 * @date 2020/5/18 14:53
 * <b>违反外事纪律模块业务层接口实现类</
 */
@Service
public class OmsSupViolationDisciplineServiceImpl implements OmsSupViolationDisciplineService {


	@Autowired
	private B01Mapper b01Mapper;
	@Autowired
	private A01Mapper a01Mapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	@Autowired
	private OmsSupViolationDisciplineMapper omsSupViolationDisciplineMapper;
	@Autowired
	private OmsRegProcpersoninfoMapper omsRegProcpersonInfoMapper;
	/**
	 * <b>查询违反外事纪律人员信息</b>
	 * @param idList
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	public Page<OmsSupViolationDiscipline> getViolationDisciplineInfo(Page<OmsSupViolationDiscipline> page,
	                                                                  OmsSupViolationDiscipline omsSupViolationDiscipline, List<String> idList) {

		//根据工作单位代码查询工作单位名称
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		List<String> list = b01Mapper.selectOrgByList(map);

		QueryWrapper<OmsSupViolationDiscipline> queryWrapper = new QueryWrapper<OmsSupViolationDiscipline>();
		queryWrapper
				.in(list != null && list.size() > 0,"WORK_UNIT", list)
				.eq(omsSupViolationDiscipline.getViolationDisType() != null && omsSupViolationDiscipline.getViolationDisType() != "",
						"VIOLATION_DIS_TYPE", omsSupViolationDiscipline.getViolationDisType())
				.between(omsSupViolationDiscipline.getViolationTimeStartQuery() != null && omsSupViolationDiscipline.getViolationTimeEndQuery() != null,
						"VIOLATION_DIS_TIME", omsSupViolationDiscipline.getViolationTimeStartQuery(), omsSupViolationDiscipline.getViolationTimeEndQuery())
				.like(omsSupViolationDiscipline.getName() != null && omsSupViolationDiscipline.getName() != "",
						"NAME", omsSupViolationDiscipline.getName())
				.or()
				.like(omsSupViolationDiscipline.getName() != null && omsSupViolationDiscipline.getName() != "",
						"PINYIN", omsSupViolationDiscipline.getName())
				.orderByDesc("VIOLATION_DIS_TIME");

		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupViolationDiscipline> resultList = omsSupViolationDisciplineMapper.selectList(queryWrapper);
		PageInfo<OmsSupViolationDiscipline> pageInfo = new PageInfo<OmsSupViolationDiscipline>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setRecords(resultList);
		return page;
	}


	/**
	 * <b>新增违反外事纪律人员信息</b>
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addViolationDisciplineInfo(OmsSupViolationDiscipline omsSupViolationDiscipline) {

		//根据违反外事记录类型计算影响期及结束时间
		SysDictItem sysDictItem = sysDictItemMapper.selectItemAllById(omsSupViolationDiscipline.getViolationDisType());
		omsSupViolationDiscipline.setInfluenceTime(sysDictItem.getItemNum() + "个月");
		Date date = UtilDateTime.getEndDateByMonth(omsSupViolationDiscipline.getViolationDisTime(), sysDictItem.getItemNum());
		omsSupViolationDiscipline.setViolationEndTime(date);

		//查询人员拼音
		List<Map<String, Object>> list = a01Mapper.selectPiliticalAffi(omsSupViolationDiscipline.getA0100());
		omsSupViolationDiscipline.setPinyin((String)list.get(0).get("a0102"));
		//生成违反外事人员信息主键
		omsSupViolationDiscipline.setId(UUIDGenerator.getPrimaryKey());
		omsSupViolationDiscipline.setCreateTime(new Date());
		omsSupViolationDiscipline.setCreateUser(UserInfoUtil.getUserInfo().getUserName());
		int count =  omsSupViolationDisciplineMapper.insert(omsSupViolationDiscipline);
		if(count < 1){
			throw new CustomMessageException("新增违反外事纪律人员失败");
		}
	}

	/**
	 * <b>修改违反外事人员信息</b>
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateViolationDisciplineInfo(OmsSupViolationDiscipline omsSupViolationDiscipline) {

		//根据违反外事记录类型计算影响期及结束时间
		SysDictItem sysDictItem = sysDictItemMapper.selectItemAllById(omsSupViolationDiscipline.getViolationDisType());
		omsSupViolationDiscipline.setInfluenceTime(sysDictItem.getItemNum() + "个月");
		Date date = UtilDateTime.getEndDateByMonth(omsSupViolationDiscipline.getViolationDisTime(), sysDictItem.getItemNum());
		omsSupViolationDiscipline.setViolationEndTime(date);

		omsSupViolationDiscipline.setModifyTime(new Date());
		omsSupViolationDiscipline.setCreateUser(UserInfoUtil.getUserInfo().getUserName());
		int count = omsSupViolationDisciplineMapper.updateById(omsSupViolationDiscipline);
		if(count <= 0){
			throw new CustomMessageException("修改违反外事纪律人员失败");
		}
	}


	/**
	 * <b>删除违反外事人员信息</b>
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeViolationDiscipline(OmsSupViolationDiscipline omsSupViolationDiscipline) {
		int count = omsSupViolationDisciplineMapper.deleteById(omsSupViolationDiscipline.getId());
		if(count < 1){
			throw new CustomMessageException("删除违反外事纪律人员失败");
		}
	}



	/**
	 * <b>导出违反外事纪律人员信息</b>
	 * @param idList
	 * @param response
	 * @param omsSupViolationDiscipline
	 * @return
	 */
	public void getViolationDisciplineInfoOut(List<String> idList, OmsSupViolationDiscipline omsSupViolationDiscipline, HttpServletResponse response) {

		//根据工作单位代码查询工作单位名称
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		List<String> list1 = b01Mapper.selectOrgByList(map);

		QueryWrapper<OmsSupViolationDiscipline> queryWrapper = new QueryWrapper<OmsSupViolationDiscipline>();
		queryWrapper
				.in(list1 != null && list1.size() > 0,"WORK_UNIT", list1)
				.eq(omsSupViolationDiscipline.getViolationDisType() != null && omsSupViolationDiscipline.getViolationDisType() != "",
						"VIOLATION_DIS_TYPE", omsSupViolationDiscipline.getViolationDisType())
				.between(omsSupViolationDiscipline.getViolationTimeStartQuery() != null && omsSupViolationDiscipline.getViolationTimeEndQuery() != null,
						"VIOLATION_DIS_TIME", omsSupViolationDiscipline.getViolationTimeStartQuery(), omsSupViolationDiscipline.getViolationTimeEndQuery())
				.like(omsSupViolationDiscipline.getName() != null && omsSupViolationDiscipline.getName() != "",
						"NAME", omsSupViolationDiscipline.getName())
				.or()
				.like(omsSupViolationDiscipline.getName() != null && omsSupViolationDiscipline.getName() != "",
						"PINYIN", omsSupViolationDiscipline.getName())
				.orderByDesc("VIOLATION_DIS_TIME");

		List<OmsSupViolationDiscipline> list = omsSupViolationDisciplineMapper.selectList(queryWrapper);


		if(list.size() < 1 || list == null){
			throw new CustomMessageException("操作失败");
		}else {
			//创建HSSFWorkbook对象(excel的文档对象)
			HSSFWorkbook wb = new HSSFWorkbook();
			//创建文件样式对象
			HSSFCellStyle style = wb.createCellStyle();
			//获得字体对象
			HSSFFont font = wb.createFont();
			//建立新的sheet对象（excel的表单）
			HSSFSheet sheet=wb.createSheet("违反外事纪律信息表");
			//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
			HSSFRow row1=sheet.createRow(0);
			//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
			HSSFCell cell=row1.createCell(0);

			//设置标题字体大小
			font.setFontHeightInPoints((short) 16);
			font.setBold(true); //加粗
			style.setAlignment(HorizontalAlignment.CENTER);// 左右居中   
			style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中   
			style.setFont(font);
			cell.setCellStyle(style);
			//设置标题单元格内容
			cell.setCellValue("违反外事纪律信息表");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,8));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(1);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("单位");
			row2.createCell(2).setCellValue("姓名");
			row2.createCell(3).setCellValue("时任职务");
			row2.createCell(4).setCellValue("违反时间");
			row2.createCell(5).setCellValue("文书号");
			row2.createCell(6).setCellValue("影响期");
			row2.createCell(7).setCellValue("结束日期");
			row2.createCell(8).setCellValue("描述");
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
			for(int i = 0; i < list.size(); i++){
				row = sheet.createRow(i + 2);
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue(list.get(i).getWorkUnit());
				row.createCell(2).setCellValue(list.get(i).getName());
				row.createCell(3).setCellValue(list.get(i).getViolationDisPost());
				row.createCell(4).setCellValue(list.get(i).getViolationDisTime());
				row.createCell(5).setCellValue(list.get(i).getViolationDocumentNo());
				row.createCell(6).setCellValue(list.get(i).getInfluenceTime());
				row.createCell(7).setCellValue(list.get(i).getViolationEndTime());
				row.createCell(8).setCellValue(list.get(i).getDescription());
				//设置单元格字体大小
				for(int j = 0;j < 9;j++){
					row.getCell(j).setCellStyle(style1);
				}
			}

			//输出Excel文件
			OutputStream output= null;
			try {
				output = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment;" +
						"filename=" + new String( "违反外事纪律人员信息表.xls".getBytes("gb2312"), "ISO8859-1" ));
				response.setContentType("application/msexcel");
				wb.write(output);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
