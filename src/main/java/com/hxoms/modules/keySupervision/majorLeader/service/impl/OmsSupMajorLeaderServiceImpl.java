package com.hxoms.modules.keySupervision.majorLeader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.enums.SexEnum;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.ListUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.modules.keySupervision.majorLeader.mapper.OmsSupMajorLeaderMapper;
import com.hxoms.modules.keySupervision.majorLeader.mapper.PersonOrgOrderMapper;
import com.hxoms.modules.keySupervision.majorLeader.service.OmsSupMajorLeaderService;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.enums.YesOrNoEnum;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.modules.omsregcadre.service.impl.OmsRegProcbatchServiceImpl;
import com.hxoms.modules.omsregcadre.service.impl.OmsRegProcpersonInfoServiceImpl;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.CustomSQLExceptionTranslatorRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import javax.management.StringValueExp;
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
public class OmsSupMajorLeaderServiceImpl extends ServiceImpl<OmsSupMajorLeaderMapper,OmsSupMajorLeader> implements OmsSupMajorLeaderService {

	@Autowired
	private OmsSupMajorLeaderMapper omsSupMajorLeaderMapper;
	@Autowired
	private OmsRegProcpersoninfoMapper omsRegProcpersonInfoMapper;
	@Autowired
	private OmsRegProcpersonInfoServiceImpl omsRegProcpersonInfoService;
	@Autowired
	private OmsRegProcpersonInfoService omsRegProcpersonInfoService1;
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


		QueryWrapper<OmsSupMajorLeader> queryWrapper = new QueryWrapper<OmsSupMajorLeader>();
		queryWrapper.in(!ListUtil.isEmpty(idList),"B0100", idList)
				.and(wrapper->wrapper.like(!StringUtils.isBlank(omsSupMajorLeader.getName()),
						"NAME", omsSupMajorLeader.getName())
						.or()
						.isNotNull("ID")
						.like(!StringUtils.isBlank(omsSupMajorLeader.getName()),
								"PINYIN", omsSupMajorLeader.getName()));
		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupMajorLeader> resultList = omsSupMajorLeaderMapper.selectList(queryWrapper);
		PageInfo<OmsSupMajorLeader> pageInfo = new PageInfo<OmsSupMajorLeader>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setRecords(resultList);
		return page;
	}



	/**
	 * <b>添加主要领导信息</b>
	 * @param omsSupMajorLeader
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addMajorLeader(OmsSupMajorLeader omsSupMajorLeader) {
		if(StringUtils.isBlank(omsSupMajorLeader.getA0100())){
			throw new CustomMessageException("参数错误");
		}

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
			omsSupMajorLeader.setCreateTime(new Date());
			omsSupMajorLeader.setCreateUser(UserInfoUtil.getUserInfo().getId());

			//在登记备案库中查询人员的身份证出生日期
			omsSupMajorLeader.setBirthDate(omsRegProcpersonInfoService.getOmsRegProcpersonBirthDate(omsSupMajorLeader.getA0100()));
			int count = omsSupMajorLeaderMapper.insert(omsSupMajorLeader);
			if (count < 1) {
				throw new CustomMessageException("添加主要领导失败");
			}else {
				//在备案库中设置该对象为主要领导
				OmsRegProcpersoninfo omsRegProcpersonInfo = new OmsRegProcpersoninfo();
				omsRegProcpersonInfo.setMainLeader(YesOrNoEnum.YES.getCode());
				omsRegProcpersonInfo.setModifyTime(new Date());
				omsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());
				QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
				queryWrapper.eq("A0100", omsSupMajorLeader.getA0100());
				int count1 = omsRegProcpersonInfoMapper.update(omsRegProcpersonInfo, queryWrapper);
				if(count1 < 1){
					throw new CustomMessageException("同步到备案信息表失败");
				}
			}
		}else{
			throw new CustomMessageException("该主要领导已经存在,请不要重复添加");
		}
	}



	/**
	 * <b>取消主要领导标识</b>
	 * @param omsSupMajorLeader
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeMajorLeader(OmsSupMajorLeader omsSupMajorLeader) {
		if(StringUtils.isBlank(omsSupMajorLeader.getId()) || StringUtils.isBlank(omsSupMajorLeader.getA0100())){
			throw new CustomMessageException("参数错误");
		}
		int count  =  omsSupMajorLeaderMapper.deleteById(omsSupMajorLeader.getId());
		if(count < 1){
			throw new CustomMessageException("取消主要领导信息失败");
		}else {
			//在备案库中取消主要领导标识
			OmsRegProcpersoninfo omsRegProcpersonInfo = new OmsRegProcpersoninfo();
			omsRegProcpersonInfo.setMainLeader(YesOrNoEnum.NO.getCode());
			omsRegProcpersonInfo.setModifyTime(new Date());
			omsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());
			QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
			queryWrapper.eq("A0100", omsSupMajorLeader.getA0100());
			int count1 = omsRegProcpersonInfoMapper.update(omsRegProcpersonInfo, queryWrapper);
			if(count1 < 1){
				throw new CustomMessageException("同步到备案信息表失败");
			}
		}
	}


	/**
	 * <b>自动识别主要领导（每个单位前两名）</b>
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addMajorLeaderAuto() {
		//查询每个单位的前两名作为主要领导
		List<OmsRegProcpersoninfo> maps = personOrgOrderMapper.selectMajorLeaderAuto();
		//查询主要领导信息表
		List<String> majorLeaderList = omsSupMajorLeaderMapper.selectA0100List();

		List<OmsSupMajorLeader> list = new ArrayList<OmsSupMajorLeader>();
		//查询领导信息
		for(OmsRegProcpersoninfo omsRegProcpersoninfo : maps){

			//获得领导主键
			String a0100 = omsRegProcpersoninfo.getA0100();
			if(majorLeaderList.contains(a0100) == false) {
				OmsSupMajorLeader omsSupMajorLeader = new OmsSupMajorLeader();
				omsSupMajorLeader.setId(UUIDGenerator.getPrimaryKey());
				omsSupMajorLeader.setA0100(omsRegProcpersoninfo.getA0100());
				omsSupMajorLeader.setB0100(omsRegProcpersoninfo.getRfB0000());
				omsSupMajorLeader.setWorkUnit(omsRegProcpersoninfo.getWorkUnit());
				omsSupMajorLeader.setName(omsRegProcpersoninfo.getSurname()+omsRegProcpersoninfo.getName());
				omsSupMajorLeader.setPinyin(omsRegProcpersoninfo.getPy());

				omsSupMajorLeader.setSex(omsRegProcpersoninfo.getSex().equals(SexEnum.MALE.getCode()) ? "男" : "女");
				omsSupMajorLeader.setPoliticalAffi(omsRegProcpersoninfo.getPoliticalAffiname());

				//在登记备案库中查询人员的身份证出生日期
				omsSupMajorLeader.setBirthDate(omsRegProcpersoninfo.getBirthDate());
				omsSupMajorLeader.setCreateTime(new Date());
				omsSupMajorLeader.setCreateUser(UserInfoUtil.getUserInfo().getId());

				omsSupMajorLeader.setPost(omsRegProcpersoninfo.getPost());
				//进行保存领导信息
				list.add(omsSupMajorLeader);

			}else {
				continue;
			}
		}

		List<OmsRegProcpersoninfo> list1 = new ArrayList<OmsRegProcpersoninfo>();
		for(OmsRegProcpersoninfo omsRegProcpersoninfo : maps){
			OmsRegProcpersoninfo omsRegProcpersoninfo1 = new OmsRegProcpersoninfo();
			omsRegProcpersoninfo1.setId(omsRegProcpersoninfo.getId());
			omsRegProcpersoninfo1.setMainLeader(YesOrNoEnum.YES.getCode());
			list1.add(omsRegProcpersoninfo1);
		}

		saveBatch(list);
		if(list != null && list.size() > 0){
			omsRegProcpersonInfoService1.updateBatchById(list1);
		}

	}


	/**
	 * <b>导出主要领导信息</b>
	 * @param idList
	 * @param omsSupMajorLeader
	 * @param response
	 * @return
	 */
	public void getMajorLeaderInfoOut(List<String> idList, OmsSupMajorLeader omsSupMajorLeader, HttpServletResponse response) {

		QueryWrapper<OmsSupMajorLeader> queryWrapper = new QueryWrapper<OmsSupMajorLeader>();
		queryWrapper.in(!ListUtil.isEmpty(idList),"B0100", idList)
				.and(wrapper->wrapper.like(!StringUtils.isBlank(omsSupMajorLeader.getName()),
						"NAME", omsSupMajorLeader.getName())
						.or()
						.isNotNull("ID")
						.like(!StringUtils.isBlank(omsSupMajorLeader.getName()),
								"PINYIN", omsSupMajorLeader.getName()));

		List<OmsSupMajorLeader> list = omsSupMajorLeaderMapper.selectList(queryWrapper);

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
			HSSFSheet sheet=wb.createSheet("主要领导信息表");
			//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
			HSSFRow row1=sheet.createRow(0);
			//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
			HSSFCell cell=row1.createCell(0);

			//设置标题字体大小
			font.setFontHeightInPoints((short) 14);
			style.setAlignment(HorizontalAlignment.CENTER);// 左右居中   
			style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中   
			style.setFont(font);
			cell.setCellStyle(style);
			//设置标题单元格内容
			cell.setCellValue("主要领导信息表");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,6));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(2);
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
			style1.setAlignment(HorizontalAlignment.LEFT);// 居左  
			style1.setFont(font1);

			HSSFRow row = null;
			for(int i = 0; i < list.size(); i++){
				row = sheet.createRow(i + 3);
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue(list.get(i).getWorkUnit());
				row.createCell(2).setCellValue(list.get(i).getName());
				row.createCell(3).setCellValue(list.get(i).getSex());
				row.createCell(4).setCellValue(UtilDateTime.toDateString(list.get(i).getBirthDate()));
				row.createCell(5).setCellValue(list.get(i).getPoliticalAffi());
				row.createCell(6).setCellValue(list.get(i).getPost());
				//设置单元格字体大小
				for(int j = 0;j < 7;j++){
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
