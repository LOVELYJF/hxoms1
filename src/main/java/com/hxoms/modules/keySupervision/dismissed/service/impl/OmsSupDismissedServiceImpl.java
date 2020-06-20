package com.hxoms.modules.keySupervision.dismissed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.keySupervision.dismissed.entity.OmsSupDismissed;
import com.hxoms.modules.keySupervision.dismissed.mapper.OmsSupDismissedMapper;
import com.hxoms.modules.keySupervision.dismissed.service.OmsSupDismissedService;
import com.hxoms.support.b01.mapper.B01Mapper;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b> 免职撤职模块业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/18 17:33
 */
@Service
public class OmsSupDismissedServiceImpl implements OmsSupDismissedService {


	@Autowired
	private B01Mapper b01Mapper;
	@Autowired
	private A01Mapper a01Mapper;
	@Autowired
	private OmsSupDismissedMapper omsSupDismissedMapper;
	/**
	 * <b>查询免职撤职信息</b>
	 * @param idList
	 * @param omsSupDismissed
	 * @param page
	 * @return
	 */
	public Page<OmsSupDismissed> getDismissedInfo(Page<OmsSupDismissed> page, OmsSupDismissed omsSupDismissed, List<String> idList) {
		//查询工作单位代码查询工作单位名称
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		List<String> list = b01Mapper.selectOrgByList(map);

		QueryWrapper<OmsSupDismissed> queryWrapper = new QueryWrapper<OmsSupDismissed>();
		queryWrapper
				.in(list != null && list.size() > 0,"WORK_UNIT", list)
				.between(omsSupDismissed.getDismissedTimeStartQuery() != null && omsSupDismissed.getDismissedTimeEndQuery() != null,
						"DISMISSED_TIME", omsSupDismissed.getDismissedTimeStartQuery(), omsSupDismissed.getDismissedTimeEndQuery())
				.like(omsSupDismissed.getName() != null && omsSupDismissed.getName() != "",
						"NAME", omsSupDismissed.getName())
				.or()
				.like(omsSupDismissed.getName() != null && omsSupDismissed.getName() != "",
						"PINYIN", omsSupDismissed.getName())
				.orderByDesc("DISMISSED_TIME");

		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupDismissed> resultList = omsSupDismissedMapper.selectList(queryWrapper);
		PageInfo<OmsSupDismissed> pageInfo = new PageInfo<OmsSupDismissed>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setRecords(resultList);
		return page;
	}



	/**
	 * <b>增加免职撤职信息</b>
	 * @param omsSupDismissed
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addDismissedInfo(OmsSupDismissed omsSupDismissed) {
		//查询人员拼音
		List<Map<String, Object>> list = a01Mapper.selectPiliticalAffi(omsSupDismissed.getA0100());
		omsSupDismissed.setPinyin((String)list.get(0).get("a0102"));
		omsSupDismissed.setId(UUIDGenerator.getPrimaryKey());
		omsSupDismissed.setCreateTime(new Date());
		omsSupDismissed.setCreateUser(UserInfoUtil.getUserInfo().getUserName());
		int count = omsSupDismissedMapper.insert(omsSupDismissed);
		if(count <= 0){
			throw new CustomMessageException("添加免职撤职人员失败");
		}
	}


	/**
	 * <b>修改免职撤职信息</b>
	 * @param omsSupDismissed
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateDismissedInfo(OmsSupDismissed omsSupDismissed) {
		omsSupDismissed.setModifyTime(new Date());
		omsSupDismissed.setModifyUser(UserInfoUtil.getUserInfo().getUserName());
		int count = omsSupDismissedMapper.updateById(omsSupDismissed);
		if(count <= 0){
			throw new CustomMessageException("修改免职撤职人员失败");
		}
	}


	/**
	 * <b>删除免职撤职信息</b>
	 * @param omsSupDismissed
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeDismissedInfo(OmsSupDismissed omsSupDismissed) {
		int count = omsSupDismissedMapper.deleteById(omsSupDismissed.getId());
		if(count <= 0){
			throw new CustomMessageException("删除免职撤职人员失败");
		}
	}



	/**
	 * <b>导出免职撤职人员信息</b>
	 * @param list
	 * @return
	 */
	public void getDismissedInfoOut(List<OmsSupDismissed> list, HttpServletResponse response) {
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
			HSSFSheet sheet=wb.createSheet("免职撤职人员信息表");
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
			cell.setCellValue("免职撤职人员信息表");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,6));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(1);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("单位");
			row2.createCell(2).setCellValue("姓名");
			row2.createCell(3).setCellValue("职务");
			row2.createCell(4).setCellValue("免职撤职时间");
			row2.createCell(5).setCellValue("文书号");
			row2.createCell(6).setCellValue("原因");
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
				row.createCell(3).setCellValue(list.get(i).getPost());
				row.createCell(4).setCellValue(list.get(i).getDismissedTime());
				row.createCell(5).setCellValue(list.get(i).getDocumentNo());
				row.createCell(6).setCellValue(list.get(i).getReason());
				//设置单元格字体大小
				for(int j = 0;j < 7;j++){
					row.getCell(j).setCellStyle(style1);
				}
			}

			//输出Excel文件
			OutputStream output= null;
			try {
				output = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment; " +
						"filename="  + new String( "免职撤职人员信息表.xls".getBytes("gb2312"), "ISO8859-1" ));
				response.setContentType("application/msexcel");
				wb.write(output);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
