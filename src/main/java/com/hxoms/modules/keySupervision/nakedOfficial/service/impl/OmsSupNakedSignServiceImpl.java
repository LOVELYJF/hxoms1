package com.hxoms.modules.keySupervision.nakedOfficial.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.keySupervision.familyMember.service.OmsSupFamilyMemberService;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;
import com.hxoms.modules.keySupervision.nakedOfficial.mapper.OmsSupNakedSignMapper;
import com.hxoms.modules.keySupervision.nakedOfficial.service.OmsSupNakedSignService;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
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
 * <b>裸官信息业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/8 22:21
 */
@Service
public class OmsSupNakedSignServiceImpl extends ServiceImpl<OmsSupNakedSignMapper, OmsSupNakedSign> implements OmsSupNakedSignService {


	@Autowired
	private OmsSupNakedSignMapper omsSupNakedSignMapper;
	@Autowired
	private B01Mapper b01Mapper;
	@Autowired
	private A01Mapper a01Mapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	@Autowired
	private OmsSupFamilyMemberService omsSupFamilyMemberService;
	@Autowired
	private OmsRegProcpersoninfoMapper omsRegProcpersonInfoMapper;
	@Autowired
	private OmsRegProcpersonInfoService omsRegProcpersonInfoService;


	/**
	 * <b>查询裸官信息</b>
	 * @param omsSupNakedSign
	 * @param idList
	 * @param page
	 * @return
	 */
	public Page<OmsSupNakedSign> getNakedOfficialList(Page<OmsSupNakedSign> page, OmsSupNakedSign omsSupNakedSign,
	                                                   List<String> idList) {

		QueryWrapper<OmsSupNakedSign> queryWrapper = new QueryWrapper<OmsSupNakedSign>();
		queryWrapper
				.in(idList != null && idList.size() > 0,"B0100", idList)
				.eq(omsSupNakedSign.getXzxgw() != null && omsSupNakedSign.getXzxgw() != "",
						"XZXGW", omsSupNakedSign.getXzxgw())
				.eq(omsSupNakedSign.getFjgnf() != null && omsSupNakedSign.getFjgnf() != "",
						"FJGNF",omsSupNakedSign.getFjgnf())
				.eq(omsSupNakedSign.getIsDelete() != null && omsSupNakedSign.getIsDelete() != "",
						"IS_DELETE", omsSupNakedSign.getIsDelete())
				.and(wrapper->wrapper.like(omsSupNakedSign.getName() != null && omsSupNakedSign.getName() != "",
						"NAME", omsSupNakedSign.getName())
						.or()
						.isNotNull("ID")
						.like(omsSupNakedSign.getName() != null && omsSupNakedSign.getName() != "",
								"PINYIN", omsSupNakedSign.getName()));

		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsSupNakedSign> resultList = omsSupNakedSignMapper.selectList(queryWrapper);
		PageInfo<OmsSupNakedSign> pageInfo = new PageInfo<OmsSupNakedSign>(resultList);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		page.setRecords(resultList);
		return page;
	}


	/**
	 * <b>添加裸官信息</b>
	 * @param omsSupNakedSign
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addOmsNaked(OmsSupNakedSign omsSupNakedSign) {
		//查询裸官是否已经存在
		QueryWrapper<OmsSupNakedSign> queryNakedSign = new QueryWrapper<OmsSupNakedSign>();
		queryNakedSign.eq("A0100", omsSupNakedSign.getA0100())
					  .eq("IS_DELETE", "0");
		List<OmsSupNakedSign> nakedSignList = omsSupNakedSignMapper.selectList(queryNakedSign);
		if(nakedSignList.size() < 1){
			//查询政治面貌和人员拼音
			List<Map<String, Object>> list = a01Mapper.selectPiliticalAffi(omsSupNakedSign.getA0100());
			//根据人员主键应该只能查到一个人员信息，因此取第一个
			omsSupNakedSign.setPoliticalAffi((String) list.get(0).get("politicalAffi"));
			omsSupNakedSign.setPinyin((String)list.get(0).get("a0102"));
			omsSupNakedSign.setXzxgw("0");
			omsSupNakedSign.setFjgnf("0");
			omsSupNakedSign.setIsDelete("0");
			omsSupNakedSign.setCreateTime(new Date());
			omsSupNakedSign.setCreateUser(UserInfoUtil.getUserInfo().getId());
			//在登记备案库中查询人员的身份证出生日期
			omsSupNakedSign.setBirthDate(omsRegProcpersonInfoService.getOmsRegProcpersonBirthDate(omsSupNakedSign.getA0100()));
			//生成裸官信息主键
			omsSupNakedSign.setId(UUIDGenerator.getPrimaryKey());
			int count = omsSupNakedSignMapper.insert(omsSupNakedSign);
			if(count < 1){
				throw new CustomMessageException("添加裸官信息失败");
			}else {
				//将裸官信息在备案表中进行同步更新
				OmsRegProcpersoninfo omsRegProcpersonInfo = new OmsRegProcpersoninfo();
				omsRegProcpersonInfo.setNf("1");

				omsRegProcpersonInfo.setXrxgw("0");
				//默认在非限入性岗位
				omsRegProcpersonInfo.setFjgnf("0");
				omsRegProcpersonInfo.setModifyTime(new Date());
				omsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());
				QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
				queryWrapper.eq("A0100", omsSupNakedSign.getA0100());
				int count1 = omsRegProcpersonInfoMapper.update(omsRegProcpersonInfo, queryWrapper);
				if(count1 < 1){
					throw new CustomMessageException("同步裸官信息到登记备案库失败");
				}
			}
		}
	}


	/**
	 * <b>修改限制性岗位信息和家属是否受监管信息</b>
	 * @param omsSupNakedSign
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateOmsNaked(OmsSupNakedSign omsSupNakedSign) {
		omsSupNakedSign.setModifyTime(new Date());
		omsSupNakedSign.setModifyUser(UserInfoUtil.getUserInfo().getId());
		int count =  omsSupNakedSignMapper.updateById(omsSupNakedSign);
		if(count < 1){
			throw new CustomMessageException("修改裸官限制性岗位失败");
		}else {
			if(omsSupNakedSign.getXzxgw() != null){
				//将裸官信息在备案表中进行同步更新
				OmsRegProcpersoninfo omsRegProcpersonInfo = new OmsRegProcpersoninfo();
				omsRegProcpersonInfo.setXrxgw(omsSupNakedSign.getXzxgw());
				omsRegProcpersonInfo.setFjgnf(omsSupNakedSign.getFjgnf());
				omsRegProcpersonInfo.setModifyTime(new Date());
				omsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());
				//在登记备案表中进行更新登记备案信息
				QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
				queryWrapper.eq("A0100", omsSupNakedSign.getA0100());
				int num = omsRegProcpersonInfoMapper.update(omsRegProcpersonInfo, queryWrapper);
				if(omsSupNakedSign.getXzxgw().equals("0") && num > 0) {
					//取消裸官的限制性岗位，同时撤销其家庭成员的登记备案，转到撤销登记备案表中
					omsSupFamilyMemberService.removeToRegistration(omsSupNakedSign.getA0100());
				}
			}
		}
	}


	/**
	 * <b>取消裸官标识</b>
	 * @param omsSupNakedSign
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeOmsNaked(OmsSupNakedSign omsSupNakedSign) {
		omsSupNakedSign.setIsDelete("1");
		omsSupNakedSign.setDeleteTime(new Date());
		int count =  omsSupNakedSignMapper.updateById(omsSupNakedSign);
		if(count < 1){
			throw new CustomMessageException("取消裸官标识失败");
		}else {
			//在备案信息表中设置取消裸官信息
			OmsRegProcpersoninfo omsRegProcpersonInfo = new OmsRegProcpersoninfo();
			omsRegProcpersonInfo.setNf("0");
			omsRegProcpersonInfo.setXrxgw("0");
			omsRegProcpersonInfo.setFjgnf("0");
			omsRegProcpersonInfo.setModifyTime(new Date());
			omsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());

			QueryWrapper<OmsRegProcpersoninfo> queryWrapper = new QueryWrapper<OmsRegProcpersoninfo>();
			queryWrapper.eq("A0100", omsSupNakedSign.getA0100());
			int num = omsRegProcpersonInfoMapper.update(omsRegProcpersonInfo, queryWrapper);
			//同时撤销其家庭成员的登记备案，转到撤销登记备案表中
			if(num > 0){
				omsSupFamilyMemberService.removeToRegistration(omsSupNakedSign.getA0100());
			}

		}
	}




	/**
	 * <b>导出裸官信息</b>
	 * @param idList
	 * @param omsSupNakedSign
	 * @return
	 */
	public void getNakedOfficialOut(List<String> idList,OmsSupNakedSign omsSupNakedSign,HttpServletResponse response) {
		QueryWrapper<OmsSupNakedSign> queryWrapper = new QueryWrapper<OmsSupNakedSign>();
		queryWrapper
				.in(idList != null && idList.size() > 0,"B0100", idList)
				.eq(omsSupNakedSign.getXzxgw() != null && omsSupNakedSign.getXzxgw() != "",
						"XZXGW", omsSupNakedSign.getXzxgw())
				.eq(omsSupNakedSign.getFjgnf() != null && omsSupNakedSign.getFjgnf() != "",
						"FJGNF",omsSupNakedSign.getFjgnf())
				.eq("IS_DELETE", "0")
				.and(wrapper->wrapper.like(omsSupNakedSign.getName() != null && omsSupNakedSign.getName() != "",
						"NAME", omsSupNakedSign.getName())
						.or()
						.isNotNull("ID")
						.like(omsSupNakedSign.getName() != null && omsSupNakedSign.getName() != "",
								"PINYIN", omsSupNakedSign.getName()));

		List<OmsSupNakedSign> list = omsSupNakedSignMapper.selectList(queryWrapper);


		if(list.size() < 1 || list == null){
			throw new CustomMessageException("不能导出空列表");
		}else{
			//创建HSSFWorkbook对象(excel的文档对象)
			HSSFWorkbook wb = new HSSFWorkbook();
			//创建文件样式对象
			HSSFCellStyle style = wb.createCellStyle();
			//获得字体对象
			HSSFFont font = wb.createFont();
			//建立新的sheet对象（excel的表单）
			HSSFSheet sheet=wb.createSheet("裸官信息表");
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
			cell.setCellValue("裸官信息表");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,7));
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
			row2.createCell(7).setCellValue("限制性岗位");
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
				row.createCell(7).setCellValue(list.get(i).getXzxgw().equals("1") ? "是" : "否");
				//设置单元格字体大小
				for(int j = 0;j < 8;j++){
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


	/**
	 * <b>查询限制性岗位</b>
	 * @return
	 */
	public List<SysDictItem> getXzxgwInfo() {
		List<SysDictItem> list = sysDictItemMapper.selectSysdictItemListByDictCode("XZXGW");
		if(list != null && list.size() > 0){
			return list;
		}
		return new ArrayList<SysDictItem>();

	}
}
