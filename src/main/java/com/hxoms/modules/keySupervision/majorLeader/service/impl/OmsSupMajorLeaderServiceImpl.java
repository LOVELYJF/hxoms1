package com.hxoms.modules.keySupervision.majorLeader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.modules.keySupervision.majorLeader.entity.PersonOrgOrder;
import com.hxoms.modules.keySupervision.majorLeader.mapper.A02Mapper;
import com.hxoms.modules.keySupervision.majorLeader.mapper.OmsSupMajorLeaderMapper;
import com.hxoms.modules.keySupervision.majorLeader.mapper.PersonOrgOrderMapper;
import com.hxoms.modules.keySupervision.majorLeader.service.OmsSupMajorLeaderService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersonInfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersonInfoMapper;
import com.hxoms.support.b01.mapper.B01Mapper;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * <b>主要领导信息业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/10 18:29
 */
@Service
public class OmsSupMajorLeaderServiceImpl implements OmsSupMajorLeaderService {

	@Autowired
	private OmsSupMajorLeaderMapper omsSupMajorLeaderMapper;
	@Autowired
	private OmsRegProcpersonInfoMapper omsRegProcpersonInfoMapper;
	@Autowired
	private B01Mapper b01Mapper;
	@Autowired
	private A01Mapper a01Mapper;
	@Autowired
	private PersonOrgOrderMapper personOrgOrderMapper;
	/**
	 * <b>查询主要领导信息</b>
	 * @param omsSupMajorLeader
	 * @param idList
	 * @param page
	 * @return
	 */
	public Page<OmsSupMajorLeader> getMajorLeader(Page<OmsSupMajorLeader> page, List<String> idList,
	                                              OmsSupMajorLeader omsSupMajorLeader) {
		//查询工作单位代码查询工作单位名称
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		List<String> list = b01Mapper.selectOrgByList(map);

		QueryWrapper<OmsSupMajorLeader> queryWrapper = new QueryWrapper<OmsSupMajorLeader>();
		queryWrapper.in(list != null && list.size() > 0,"WORK_UNIT", list)
				.like(omsSupMajorLeader.getName() != null && omsSupMajorLeader.getName() != "",
						"NAME", omsSupMajorLeader.getName())
				.or()
				.like(omsSupMajorLeader.getName() != null && omsSupMajorLeader.getName() != "",
						"PINYIN", omsSupMajorLeader.getName());

		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupMajorLeader> resultList = omsSupMajorLeaderMapper.selectList(queryWrapper);
		PageInfo<OmsSupMajorLeader> pageInfo = new PageInfo<OmsSupMajorLeader>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setPages(pageInfo.getPages());
		page.setRecords(resultList);
		return page;
	}



	/**
	 * <b>添加主要领导信息</b>
	 * @param omsSupMajorLeader
	 * @return
	 */
	public void addMajorLeader(OmsSupMajorLeader omsSupMajorLeader) {

		//查询主要领导是否已经存在
		QueryWrapper<OmsSupMajorLeader> queryLeader = new QueryWrapper<OmsSupMajorLeader>() ;
		queryLeader.eq("A0100", omsSupMajorLeader.getA0100());
		List<OmsSupMajorLeader> majorLeaderList = omsSupMajorLeaderMapper.selectList(queryLeader);
		if(majorLeaderList.size() < 1) {
			//查询政治面貌和人员拼音
			List<Map<String, Object>> list = a01Mapper.selectPiliticalAffi(omsSupMajorLeader.getA0100());

			omsSupMajorLeader.setPoliticalAffi((String) list.get(0).get("politicalAffi"));
			omsSupMajorLeader.setPinyin((String) list.get(0).get("a0102"));
			omsSupMajorLeader.setId(UUIDGenerator.getPrimaryKey());
			int count = omsSupMajorLeaderMapper.insert(omsSupMajorLeader);
			if (count < 1) {
				throw new CustomMessageException("操作失败");
			}else {
				//在备案库中设置该对象为主要领导
				OmsRegProcpersonInfo omsRegProcpersonInfo = new OmsRegProcpersonInfo();
				omsRegProcpersonInfo.setMainLeader("1");
				QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
				queryWrapper.eq("A0100", omsSupMajorLeader.getA0100());
				omsRegProcpersonInfoMapper.update(omsRegProcpersonInfo, queryWrapper);
			}
		}

	}



	/**
	 * <b>取消主要领导标识</b>
	 * @param omsSupMajorLeader
	 * @return
	 */
	public void removeMajorLeader(OmsSupMajorLeader omsSupMajorLeader) {

		int count  =  omsSupMajorLeaderMapper.deleteById(omsSupMajorLeader.getId());
		if(count < 1){
			throw new CustomMessageException("操作失败");
		}else {
			//在备案库中取消主要领导标识
			OmsRegProcpersonInfo omsRegProcpersonInfo = new OmsRegProcpersonInfo();
			omsRegProcpersonInfo.setMainLeader("0");
			QueryWrapper<OmsRegProcpersonInfo> queryWrapper = new QueryWrapper<OmsRegProcpersonInfo>();
			queryWrapper.eq("A0100", omsSupMajorLeader.getA0100());
			omsRegProcpersonInfoMapper.update(omsRegProcpersonInfo, queryWrapper);

		}
	}


	/**
	 * <b>自动识别主要领导（每个单位前两名）</b>
	 * @return
	 */
	public void addMajorLeaderAuto() {
		List<PersonOrgOrder> list = personOrgOrderMapper.selectMajorLeaderAuto();
		//查询领导信息
		for(PersonOrgOrder person : list){
			OmsSupMajorLeader omsSupMajorLeader = new OmsSupMajorLeader();
			//将查询到的主要领导保存到主要领导信息表中
			String a0100 = person.getA01000();
			List<Map<String,Object>> mapList = a01Mapper.selectPersonInfo(a0100);
			if(mapList.size() > 0){
				omsSupMajorLeader.setId(UUIDGenerator.getPrimaryKey());
				omsSupMajorLeader.setA0100((String) mapList.get(0).get("a0100"));
				omsSupMajorLeader.setWorkUnit((String) mapList.get(0).get("b0101"));
				omsSupMajorLeader.setName((String) mapList.get(0).get("a0101"));
				omsSupMajorLeader.setPinyin((String) mapList.get(0).get("a0102"));

				omsSupMajorLeader.setSex((String) mapList.get(0).get("sex"));
				omsSupMajorLeader.setPoliticalAffi((String) mapList.get(0).get("politicalAffi"));

				Date date = UtilDateTime.toDateFormat((String) mapList.get(0).get("a0107"), "yy-MM-dd");
				omsSupMajorLeader.setBirthDate(date);


				omsSupMajorLeader.setPost((String) mapList.get(0).get("a0215a"));
				omsSupMajorLeader.setRank((String) mapList.get(0).get("A0221"));
			}

			//查询主要领导是否已经存在
			QueryWrapper<OmsSupMajorLeader> queryWrapper = new QueryWrapper<OmsSupMajorLeader>() ;
			queryWrapper.eq("A0100", omsSupMajorLeader.getA0100());
			List<OmsSupMajorLeader> majorLeaderList = omsSupMajorLeaderMapper.selectList(queryWrapper);
			if(majorLeaderList.size() < 1) {
				omsSupMajorLeaderMapper.insert(omsSupMajorLeader);
			}else {
				//在备案库中设置该对象为主要领导
				OmsRegProcpersonInfo omsRegProcpersonInfo = new OmsRegProcpersonInfo();
				omsRegProcpersonInfo.setMainLeader("1");
				QueryWrapper<OmsRegProcpersonInfo> wrapper = new QueryWrapper<OmsRegProcpersonInfo>();
				wrapper.eq("A0100", omsSupMajorLeader.getA0100());
				omsRegProcpersonInfoMapper.update(omsRegProcpersonInfo, wrapper);
			}
		}
	}

	/**
	 * <b>导出主要领导信息</b>
	 * @param list
	 * @return
	 */
	public void getMajorLeaderInfoOut(List<OmsSupMajorLeader> list, HttpServletResponse response) {
		if(list.size() < 1){
			throw new CustomMessageException("操作失败");
		}
		//创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建文件样式对象
		HSSFCellStyle style = wb.createCellStyle();
		//获得字体对象
		HSSFFont font = wb.createFont();
		//建立新的sheet对象（excel的表单）
		HSSFSheet sheet=wb.createSheet("主要领导信息表");
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
		cell.setCellValue("主要领导信息表");

		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,6));
		//在sheet里创建第二行
		HSSFRow row2=sheet.createRow(1);
		//创建单元格并设置单元格内容
		row2.createCell(0).setCellValue("序号");
		row2.createCell(1).setCellValue("单位");
		row2.createCell(2).setCellValue("姓名");
		row2.createCell(3).setCellValue("性别");
		row2.createCell(4).setCellValue("出生日期");
		row2.createCell(5).setCellValue("政治面貌");
		row2.createCell(6).setCellValue("职务职级");
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
			row.createCell(3).setCellValue(list.get(i).getSex());
			row.createCell(4).setCellValue(list.get(i).getBirthDate());
			row.createCell(5).setCellValue(list.get(i).getPoliticalAffi());
			row.createCell(6).setCellValue(list.get(i).getPost());
			//设置单元格字体大小
			for(int j = 0;j < 6;j++){
				row.getCell(j).setCellStyle(style1);
			}
		}

		//输出Excel文件
		OutputStream output= null;
		try {
			output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; " +
					"filename=" + new String( "主要领导信息表.xls".getBytes("gb2312"), "ISO8859-1" ));
			response.setContentType("application/msexcel");
			wb.write(output);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



}