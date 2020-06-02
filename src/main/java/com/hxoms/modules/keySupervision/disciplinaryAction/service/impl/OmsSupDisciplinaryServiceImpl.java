package com.hxoms.modules.keySupervision.disciplinaryAction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.keySupervision.disciplinaryAction.entity.OmsSupDisciplinary;
import com.hxoms.modules.keySupervision.disciplinaryAction.mapper.OmsSupDisciplinaryMapper;
import com.hxoms.modules.keySupervision.disciplinaryAction.service.OmsSupDisciplinaryService;
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

/**
 * <b>处分信息模块业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/15 11:00
 */
@Service
public class OmsSupDisciplinaryServiceImpl implements OmsSupDisciplinaryService {


	@Autowired
	private OmsSupDisciplinaryMapper omsSupDisciplinaryMapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	@Autowired
	private B01Mapper b01Mapper;
	@Autowired
	private A01Mapper a01Mapper;

	/**
	 * <b>查询处分信息</b>
	 * @param idList
	 * @param omsSupDisciplinary
	 * @return
	 */
	public Page<OmsSupDisciplinary> getDisciplinaryInfo(Page<OmsSupDisciplinary> page, OmsSupDisciplinary omsSupDisciplinary, List<String> idList) {

		//查询工作单位代码查询工作单位名称
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		List<String> list = b01Mapper.selectOrgByList(map);

		QueryWrapper<OmsSupDisciplinary> queryWrapper = new QueryWrapper<OmsSupDisciplinary>();
		queryWrapper
				.in(list != null && list.size() > 0,"WORK_UNIT", list)
				.eq(omsSupDisciplinary.getDisciplinaryType() != null && omsSupDisciplinary.getDisciplinaryType() != "",
						"DISCIPLINARY_TYPE", omsSupDisciplinary.getDisciplinaryType())
				.between(omsSupDisciplinary.getDisciplinaryStartQuery() != null && omsSupDisciplinary.getDisciplinaryEndQuery() != null,
						"DISCIPLINARY_TIME", omsSupDisciplinary.getDisciplinaryStartQuery(), omsSupDisciplinary.getDisciplinaryEndQuery())
				.like(omsSupDisciplinary.getName() != null && omsSupDisciplinary.getName() != "" ,
						"NAME", omsSupDisciplinary.getName())
				.or()
				.like(omsSupDisciplinary.getName() != null && omsSupDisciplinary.getName() != "",
						"PINYIN", omsSupDisciplinary.getName());

		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupDisciplinary> resultList = omsSupDisciplinaryMapper.selectList(queryWrapper);
		PageInfo<OmsSupDisciplinary> pageInfo = new PageInfo<OmsSupDisciplinary>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setPages(pageInfo.getPages());
		page.setRecords(resultList);
		return page;
	}


	/**
	 * <b>增加处分信息</b>
	 * @param omsSupDisciplinary
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addDisciplinaryInfo(OmsSupDisciplinary omsSupDisciplinary) {
		//根据处分类型计算影响其和结束时间





		//查询人员拼音
		List<Map<String, Object>> list = a01Mapper.selectPiliticalAffi(omsSupDisciplinary.getA0100());
		omsSupDisciplinary.setPinyin((String)list.get(0).get("a0102"));
		omsSupDisciplinary.setId(UUIDGenerator.getPrimaryKey());

		int count = omsSupDisciplinaryMapper.insert(omsSupDisciplinary);
		if(count <= 0){
			throw new CustomMessageException("操作失败");
		}
	}



	/**
	 * <b>查询处分类型</b>
	 * @return
	 */
	public List<SysDictItem> getDisciplinaryActionType() {
		List<SysDictItem> list = sysDictItemMapper.selectDisciplinaryActionType();
		return list;
	}

	/**
	 * <b>修改处分信息</b>
	 * @param omsSupDisciplinary
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateDisciplinaryInfo(OmsSupDisciplinary omsSupDisciplinary) {

		//重新计算影响期（根据处分类型计算影响期和结束时间）





		int count = omsSupDisciplinaryMapper.updateById(omsSupDisciplinary);
		if(count <= 0){
			throw new CustomMessageException("操作失败");
		}
	}


	/**
	 * <b>删除处分信息</b>
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteDisciplinaryInfo(String id) {
		int count = omsSupDisciplinaryMapper.deleteById(id);
		if(count <= 0){
			throw new CustomMessageException("操作失败");
		}
	}


	/**
	 * <b>导出处分信息</b>
	 * @param list
	 * @return
	 */
	public void getDisciplinaryInfoOut(List<OmsSupDisciplinary> list, HttpServletResponse response) {
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
			HSSFSheet sheet=wb.createSheet("处分信息表");
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
			cell.setCellValue("处分信息表");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,8));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(1);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("单位");
			row2.createCell(2).setCellValue("姓名");
			row2.createCell(3).setCellValue("处分时职务");
			row2.createCell(4).setCellValue("处分时间");
			row2.createCell(5).setCellValue("处分文书号");
			row2.createCell(6).setCellValue("处分类型");
			row2.createCell(7).setCellValue("结束日期");
			row2.createCell(8).setCellValue("处分原因");
			//在sheet里添加数据

			//创建文件样式对象
			HSSFCellStyle style1 = wb.createCellStyle();
			//获得字体对象
			HSSFFont font1 = wb.createFont();
			//设置单元格字体大小
			font1.setFontHeightInPoints((short) 13);
			style1.setAlignment(HorizontalAlignment.LEFT); //居左
			style1.setFont(font1);

			HSSFRow row = null;
			for(int i = 0; i < list.size(); i++){
				row = sheet.createRow(i + 2);
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue(list.get(i).getWorkUnit());
				row.createCell(2).setCellValue(list.get(i).getName());
				row.createCell(3).setCellValue(list.get(i).getDisciplinaryPost());
				row.createCell(4).setCellValue(list.get(i).getDisciplinaryTime());
				row.createCell(5).setCellValue(list.get(i).getDisciplinaryDocumentNo());
				row.createCell(6).setCellValue(list.get(i).getDisciplinaryType());
				row.createCell(7).setCellValue(list.get(i).getDisciplinaryEndTime());
				row.createCell(8).setCellValue(list.get(i).getWhyDisciplinary());
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
				response.setHeader("Content-disposition", "attachment; " +
						"filename=" + new String( "处分人员信息表.xls".getBytes("gb2312"), "ISO8859-1" ));
				response.setContentType("application/msexcel");
				wb.write(output);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}
}