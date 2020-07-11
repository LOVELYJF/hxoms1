package com.hxoms.modules.keySupervision.caseInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.keySupervision.caseInfo.entity.OmsSupCaseInfo;
import com.hxoms.modules.keySupervision.caseInfo.mapper.OmsSupCaseInfoMapper;
import com.hxoms.modules.keySupervision.caseInfo.service.OmsSupCaseInfoService;
import com.hxoms.modules.keySupervision.disciplinaryAction.entity.OmsSupDisciplinary;
import com.hxoms.modules.keySupervision.disciplinaryAction.mapper.OmsSupDisciplinaryMapper;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>立案信息业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/14 16:46
 */
@Service
public class OmsSupCaseInfoServiceImpl implements OmsSupCaseInfoService {

	@Autowired
	private B01Mapper b01Mapper;
	@Autowired
	private A01Mapper a01Mapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	@Autowired
	private OmsSupCaseInfoMapper omsSupCaseInfoMapper;
	@Autowired
	private OmsSupDisciplinaryMapper omsSupDisciplinaryMapper;

	/**
	 * <b>根据用户输入查询立案信息</b>
	 * @param idList
	 * @param omsSupCaseInfo
	 * @param page
	 * @return
	 */
	public Page<OmsSupCaseInfo> getCasePersonInfo(Page<OmsSupCaseInfo> page, OmsSupCaseInfo omsSupCaseInfo, List<String> idList) {


		//根据工作单位代码查询工作单位名称
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		List<String> list = b01Mapper.selectOrgByList(map);
		QueryWrapper<OmsSupCaseInfo> queryWrapper = new QueryWrapper<OmsSupCaseInfo>();
		queryWrapper
				.in(list != null && list.size() > 0,"WORK_UNIT", list)
				.eq(omsSupCaseInfo.getDisciplinaryAction() != null && omsSupCaseInfo.getDisciplinaryAction() != "",
						"DISCIPLINARY_ACTION", omsSupCaseInfo.getDisciplinaryAction())
				.between(omsSupCaseInfo.getCaseTimeStart() != null && omsSupCaseInfo.getCaseTimeEnd() != null ,
						"CASE_TIME",omsSupCaseInfo.getCaseTimeStart() , omsSupCaseInfo.getCaseTimeEnd())
				.like(omsSupCaseInfo.getName() != null && omsSupCaseInfo.getName() != "",
						"NAME", omsSupCaseInfo.getName())
				.or()
				.like(omsSupCaseInfo.getName() != null && omsSupCaseInfo.getName() != "",
						"PINYIN", omsSupCaseInfo.getName())
				.orderByDesc("CASE_TIME");

		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupCaseInfo> resultList = omsSupCaseInfoMapper.selectList(queryWrapper);
		PageInfo<OmsSupCaseInfo> pageInfo = new PageInfo<OmsSupCaseInfo>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setRecords(resultList);
		return page;
	}



	/**
	 * <b>增加立案信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String addCaseInfo(OmsSupCaseInfo omsSupCaseInfo) {

		//查询人员拼音
		List<Map<String, Object>> list = a01Mapper.selectPiliticalAffi(omsSupCaseInfo.getA0100());
		omsSupCaseInfo.setPinyin((String)list.get(0).get("a0102"));

		//检查立案信息的文书号是否出现缺号
		checkCaseDocumentNo(omsSupCaseInfo.getCaseDocumentNo());

		omsSupCaseInfo.setId(UUIDGenerator.getPrimaryKey());
		omsSupCaseInfo.setCreateTime(new Date());
		omsSupCaseInfo.setCreateUser(UserInfoUtil.getUserInfo().getUserName());
		int count = omsSupCaseInfoMapper.insert(omsSupCaseInfo);
		if(count < 1){
			throw new CustomMessageException("操作失败");
		}
		return omsSupCaseInfo.getId();
	}


	/**
	 * <b>保存并转到处分信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addToDisciplinary(OmsSupCaseInfo omsSupCaseInfo) {

		//检查是否已经保存
		QueryWrapper<OmsSupCaseInfo> queryWrapper = new QueryWrapper<OmsSupCaseInfo>();
		queryWrapper.eq("A0100", omsSupCaseInfo.getA0100())
				.eq("CASE_DOCUMENT_NO", omsSupCaseInfo.getCaseDocumentNo());
		OmsSupCaseInfo omsSupCaseInfo1 = omsSupCaseInfoMapper.selectOne(queryWrapper);
		if(omsSupCaseInfo1 == null){
			//保存到立案信息
			String id = addCaseInfo(omsSupCaseInfo);
			omsSupCaseInfo.setId(id);
			saveToDisciplinary(omsSupCaseInfo);
		}else {
			//在立案信息表中已经保存,判断是否受处分及是否已经添加到处分表
			if(omsSupCaseInfo.getDisciplinaryAction().equals("1")){
				//根据人员主键和处分时间查询是否重复
				QueryWrapper<OmsSupDisciplinary> queryWrapper1 = new QueryWrapper<OmsSupDisciplinary>();
				queryWrapper1.eq("A0100", omsSupCaseInfo.getA0100())
						.eq("DISCIPLINARY_TIME", omsSupCaseInfo.getDisciplinaryTime());
				OmsSupDisciplinary omsSupDisciplinary = omsSupDisciplinaryMapper.selectOne(queryWrapper1);
				if(omsSupDisciplinary == null){
					omsSupCaseInfo.setId(omsSupCaseInfo1.getId());
					//处分表中不存在，进行添加
					saveToDisciplinary(omsSupCaseInfo);
				}
			}else {
				throw new CustomMessageException("请选择受处分及处分类型");
			}
		}
	}



	/**
	 * <b保存修改的立案信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateSaveCaseInfo(OmsSupCaseInfo omsSupCaseInfo) {
		omsSupCaseInfo.setModifyTime(new Date());
		omsSupCaseInfo.setModifyUser(UserInfoUtil.getUserInfo().getUserName());
		int count = omsSupCaseInfoMapper.updateById(omsSupCaseInfo);
		if(count < 1){
			throw new CustomMessageException("修改立案信息失败");
		}
	}

	/**
	 * <b保存修改的立案信息并转到处分信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateCaseInfoToDisciplinary(OmsSupCaseInfo omsSupCaseInfo) {
		//更新保存立案信息
		updateSaveCaseInfo(omsSupCaseInfo);

		if(omsSupCaseInfo.getDisciplinaryAction().equals("1")){
			//根据id查处分信息表中是否存在该人员信息
			QueryWrapper<OmsSupDisciplinary> queryWrapper = new QueryWrapper<OmsSupDisciplinary>();
			queryWrapper.eq("ID", omsSupCaseInfo.getId());

			if(omsSupDisciplinaryMapper.selectOne(queryWrapper) != null){
				//处分信息已经存在，进行修改
				OmsSupDisciplinary omsSupDisciplinary = new OmsSupDisciplinary();
				//在立案信息处只能修改处分的类型和修改时间信息
				omsSupDisciplinary.setDisciplinaryType(omsSupCaseInfo.getDisciplinaryActionType());
				omsSupDisciplinary.setModifyTime(new Date());
				omsSupDisciplinary.setModifyUser(UserInfoUtil.getUserInfo().getUserName());
				omsSupDisciplinary.setId(omsSupCaseInfo.getId());

				//重新计算影响期
				SysDictItem sysDictItem = sysDictItemMapper.selectItemAllById(omsSupCaseInfo.getDisciplinaryActionType());
				omsSupDisciplinary.setInfluenceTime(sysDictItem.getItemNum() + "个月");
				Date date = UtilDateTime.getEndDateByMonth(omsSupCaseInfo.getDisciplinaryTime(), sysDictItem.getItemNum());
				omsSupDisciplinary.setDisciplinaryEndTime(date);

				int updateCount = omsSupDisciplinaryMapper.updateById(omsSupDisciplinary);
				if(updateCount < 1){
					throw new CustomMessageException("更新保存到处分信息失败");
				}
			}else {

				saveToDisciplinary(omsSupCaseInfo);
			}
		}else {
			throw new CustomMessageException("请选择受处分及处分类型");
		}

	}


	/**
	 * <b>删除立案信息</b>
	 * @param omsSupCaseInfo
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeCaseInfo(OmsSupCaseInfo omsSupCaseInfo) {
		int count = omsSupCaseInfoMapper.deleteById(omsSupCaseInfo.getId());
		if(count < 1){
			throw new CustomMessageException("删除立案信息失败");
		}
	}


	/**
	 * <b>导出立案信息</b>
	 * @param idList
	 * @param omsSupCaseInfo
	 * @param response
	 * @return
	 */
	public void getCaseInfoOut(List<String> idList, OmsSupCaseInfo omsSupCaseInfo, HttpServletResponse response) {

		//根据工作单位代码查询工作单位名称
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		List<String> list1 = b01Mapper.selectOrgByList(map);
		QueryWrapper<OmsSupCaseInfo> queryWrapper = new QueryWrapper<OmsSupCaseInfo>();
		queryWrapper
				.in(list1 != null && list1.size() > 0,"WORK_UNIT", list1)
				.eq(omsSupCaseInfo.getDisciplinaryAction() != null && omsSupCaseInfo.getDisciplinaryAction() != "",
						"DISCIPLINARY_ACTION", omsSupCaseInfo.getDisciplinaryAction())
				.between(omsSupCaseInfo.getCaseTimeStart() != null && omsSupCaseInfo.getCaseTimeEnd() != null ,
						"CASE_TIME",omsSupCaseInfo.getCaseTimeStart() , omsSupCaseInfo.getCaseTimeEnd())
				.like(omsSupCaseInfo.getName() != null && omsSupCaseInfo.getName() != "",
						"NAME", omsSupCaseInfo.getName())
				.or()
				.like(omsSupCaseInfo.getName() != null && omsSupCaseInfo.getName() != "",
						"PINYIN", omsSupCaseInfo.getName())
				.orderByDesc("CASE_TIME");

		List<OmsSupCaseInfo> list = omsSupCaseInfoMapper.selectList(queryWrapper);

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
			HSSFSheet sheet=wb.createSheet("立案信息表");
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
			cell.setCellValue("立案信息表");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,7));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(1);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("单位");
			row2.createCell(2).setCellValue("姓名");
			row2.createCell(3).setCellValue("立案时职务");
			row2.createCell(4).setCellValue("立案时间");
			row2.createCell(5).setCellValue("立案文书号");
			row2.createCell(6).setCellValue("是否已受处分");
			row2.createCell(7).setCellValue("立案原因");
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
				row.createCell(3).setCellValue(list.get(i).getCasePost());
				row.createCell(4).setCellValue(list.get(i).getCaseTime());
				row.createCell(5).setCellValue(list.get(i).getCaseDocumentNo());
				row.createCell(6).setCellValue((list.get(i).getDisciplinaryAction()).equals("1") ? "是" : "否");
				row.createCell(7).setCellValue(list.get(i).getWhyCase());
				//设置单元格字体大小
				for(int j = 0;j < 8;j++){
					row.getCell(j).setCellStyle(style1);
				}
			}

			//输出Excel文件
			OutputStream output= null;
			try {
				output = response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment; " +
						"filename=" + new String( "立案人员信息表.xls".getBytes("gb2312"), "ISO8859-1" ));
				response.setContentType("application/msexcel");
				wb.write(output);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * <b>检查立案信息的文书号是否连续</b>
	 * @param documentNum
	 */
	 public void checkCaseDocumentNo(String documentNum){
		 //检查立案信息的文书号是否出现缺号
		 String yearNum = documentNum.substring(documentNum.indexOf("[") + 1, documentNum.indexOf("]"));
		 String no = documentNum.substring(documentNum.indexOf("]") + 1, documentNum.indexOf("号"));
		 Integer num = Integer.parseInt(no);
		 if(UtilDateTime.nowYear().equals(yearNum)){
			 //根据当前年份查询数据库中的文书号数量
			 QueryWrapper<OmsSupCaseInfo> queryWrapper = new QueryWrapper<OmsSupCaseInfo>();
			 queryWrapper.like(yearNum != null, "CASE_DOCUMENT_NO", "[" + yearNum + "]");
			 int resultCount = omsSupCaseInfoMapper.selectCount(queryWrapper);
			 if(resultCount != 0){
				 if((resultCount + 1) != num){
					 throw new CustomMessageException("立案文书号不连续,当前文书号到" +
							 documentNum.substring(0, documentNum.indexOf("]") + 1) + resultCount + "号");
				 }
			 }else{
				 if(num != 1){
					 throw new CustomMessageException("立案文书号应该从当年1号开始");
				 }
			 }
		 }else {
			 throw new CustomMessageException("立案年份不正确");
		 }
	 }


	/**
	 * <b>保存处分信息</b>
	 * @param omsSupCaseInfo
	 */
	 public void saveToDisciplinary(OmsSupCaseInfo omsSupCaseInfo){
		 //保存到处分信息
		 List<Map<String, Object>> list = a01Mapper.selectPiliticalAffi(omsSupCaseInfo.getA0100());
		 OmsSupDisciplinary omsSupDisciplinary = new OmsSupDisciplinary();
		 omsSupDisciplinary.setPinyin((String)list.get(0).get("a0102"));
		 //将立案信息的主键设置成处分信息的主键
		 omsSupDisciplinary.setId(omsSupCaseInfo.getId());
		 omsSupDisciplinary.setA0100(omsSupCaseInfo.getA0100());
		 omsSupDisciplinary.setWorkUnit(omsSupCaseInfo.getWorkUnit());
		 omsSupDisciplinary.setName(omsSupCaseInfo.getName());
		 omsSupDisciplinary.setDisciplinaryPost(omsSupCaseInfo.getCasePost());
		 omsSupDisciplinary.setDisciplinaryType(omsSupCaseInfo.getDisciplinaryActionType());
		 omsSupDisciplinary.setCreateTime(new Date());
		 omsSupDisciplinary.setDisciplinaryTime(omsSupCaseInfo.getDisciplinaryTime());
		 omsSupDisciplinary.setCreateUser(UserInfoUtil.getUserInfo().getUserName());

		 //根据处分类型计算影响期及结束时间
		 SysDictItem sysDictItem = sysDictItemMapper.selectItemAllById(omsSupCaseInfo.getDisciplinaryActionType());
		 omsSupDisciplinary.setInfluenceTime(sysDictItem.getItemNum() + "个月");
		 Date date = UtilDateTime.getEndDateByMonth(omsSupCaseInfo.getDisciplinaryTime(), sysDictItem.getItemNum());
		 omsSupDisciplinary.setDisciplinaryEndTime(date);

		 int count = omsSupDisciplinaryMapper.insert(omsSupDisciplinary);
		 if(count < 1){
			 throw new CustomMessageException("保存到处分信息失败");
		 }
	 }
}
