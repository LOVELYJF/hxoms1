package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity.OmsCerApplyLendingLicense;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.mapper.OmsCerApplyLendingLicenseMapper;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service.OmsCerApplyLendingLicenseService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 借出证照申请业务层接口实现类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/10 17:53
 */
@Service
public class OmsCerApplyLendingLicenseServiceImpl implements OmsCerApplyLendingLicenseService {

	@Autowired
	private CfCertificateMapper cfCertificateMapper;
	@Autowired
	private OmsCerApplyLendingLicenseMapper omsCerApplyLendingLicenseMapper;
	/**
	 * <b>功能描述: 根据主键查询该人员的证照信息</b>
	 * @Param: [a0100]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	public List<Map<String, Object>> getCfCertificateByA0100(String a0100) {
		List<Map<String, Object>> list = cfCertificateMapper.getCfCertificateByA0100(a0100);
		return list;
	}

	/**
	 * <b>功能描述: 保存申请借出的证照信息</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	public void saveApplyLendingLicenseInfo(List<OmsCerApplyLendingLicense> list) {
		if(list != null && list.size() > 0){
			for(OmsCerApplyLendingLicense omsCerApplyLendingLicense : list){
				omsCerApplyLendingLicense.setId(UUIDGenerator.getPrimaryKey());
				omsCerApplyLendingLicense.setIsCommit("0");
				omsCerApplyLendingLicense.setCreateTime(new Date());
				omsCerApplyLendingLicense.setSqjczt("0");       //状态：申请
				omsCerApplyLendingLicense.setCreateUser(UserInfoUtil.getUserInfo().getId());
				omsCerApplyLendingLicense.setYear(UtilDateTime.nowYear());
				omsCerApplyLendingLicense.setDocumentNum(UtilDateTime.formatCNDate(new Date()));
				int count = omsCerApplyLendingLicenseMapper.insert(omsCerApplyLendingLicense);
				if(count < 1){
					throw new CustomMessageException("借出申请保存失败");
				}
			}
		}else {
			throw new CustomMessageException("未选择借出的证照信息");
		}

	}

	/**
	 * <b>功能描述: 查询证照借出申请信息（首页查询）</b>
	 * @Param: [page,omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 * @return
	 */
	public Page<Map<String, Object>> selectApplyLendingLicenseInfo(Page<Map<String,Object>> page, OmsCerApplyLendingLicense omsCerApplyLendingLicense) {
		PageHelper.startPage((int)page.getCurrent(), (int)page.getSize());
		List<Map<String, Object>> list = omsCerApplyLendingLicenseMapper.selectApplyLendingLicenseInfo(omsCerApplyLendingLicense);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
		page.setTotal(pageInfo.getTotal());
		page.setPages(pageInfo.getPages());
		page.setRecords(list);
		return page;
	}

	/**
	 * <b>功能描述: 借出证照申请导出</b>
	 * @Param: [omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 14:41
	 */
	public void getApplyLendingLicenseInfoOut(OmsCerApplyLendingLicense omsCerApplyLendingLicense, HttpServletResponse response) {
		//查询证照借出情况
		List<Map<String, Object>> list = omsCerApplyLendingLicenseMapper.selectApplyLendingLicenseInfo(omsCerApplyLendingLicense);

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
			HSSFSheet sheet=wb.createSheet("证照借出申请信息表");
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
			cell.setCellValue("证照借出申请信息表");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,7));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(2);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("单位");
			row2.createCell(2).setCellValue("姓名");
			row2.createCell(3).setCellValue("性别");
			row2.createCell(4).setCellValue("任职状态");
			row2.createCell(5).setCellValue("职务");
			row2.createCell(6).setCellValue("证照类型");
			row2.createCell(7).setCellValue("申请状态");
			//在sheet里添加数据

			//创建文件样式对象
			HSSFCellStyle style1 = wb.createCellStyle();
			//获得字体对象
			HSSFFont font1 = wb.createFont();
			style1.setAlignment(HorizontalAlignment.LEFT); //居左
			style1.setFont(font1);

			HSSFRow row = null;
			for(int i = 0; i < list.size(); i++){
				row = sheet.createRow(i + 3);
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue((String) list.get(i).get("workUnit"));
				row.createCell(2).setCellValue((String) list.get(i).get("name"));
				row.createCell(3).setCellValue(String.valueOf(list.get(i).get("sex")).equals("1") ? "男" : "女");
				row.createCell(4).setCellValue(Constants.INCUMBENCY_STATUS_NAME[Integer.parseInt((String) list.get(i).get("incumbencyStatus")) - 1]);
				row.createCell(5).setCellValue((String) list.get(i).get("post"));
				row.createCell(6).setCellValue(Constants.CER_TYPE_NAME[Integer.parseInt((String) list.get(i).get("zjlx")) - 1]);
				row.createCell(7).setCellValue(Constants.CER_LENDING_NAME[Integer.parseInt((String) list.get(i).get("sqjczt"))]);
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
	 * <b>功能描述: 提交申请</b>
	 * @Param: [idList]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:41
	 */
	public void updateApplyLendingLicenseCommit(List<String> idList) {
		OmsCerApplyLendingLicense omsCerApplyLendingLicense = new OmsCerApplyLendingLicense();
		omsCerApplyLendingLicense.setIsCommit("1");
		omsCerApplyLendingLicense.setSqjczt("1");       //状态：审批
		omsCerApplyLendingLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
		omsCerApplyLendingLicense.setModyfyTime(new Date());
		if(idList != null && idList.size() > 0){
			QueryWrapper<OmsCerApplyLendingLicense> queryWrapper = new QueryWrapper<OmsCerApplyLendingLicense>();
			queryWrapper.in("ID", idList);
			int count = omsCerApplyLendingLicenseMapper.update(omsCerApplyLendingLicense, queryWrapper);
			if(count < 1){
				throw new CustomMessageException("提交失败");
			}
		}else {
			throw new CustomMessageException("请选择要提交的信息");
		}
	}


	/**
	 * <b>功能描述: 撤销申请借出的证照</b>
	 * @Param: [omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 8:41
	 */
	public void updateApplyLendingLicenseRevoke(OmsCerApplyLendingLicense omsCerApplyLendingLicense) {
		omsCerApplyLendingLicense.setSqjczt("4");
		omsCerApplyLendingLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
		omsCerApplyLendingLicense.setModyfyTime(new Date());
		int count = omsCerApplyLendingLicenseMapper.updateById(omsCerApplyLendingLicense);
		if(count < 1){
			throw new CustomMessageException("撤销失败");
		}
	}


}
