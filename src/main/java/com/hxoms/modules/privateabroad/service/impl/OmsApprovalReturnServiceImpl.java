package com.hxoms.modules.privateabroad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturn;
import com.hxoms.modules.privateabroad.entity.OmsApprovalReturnVO;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApprovalReturnIPageParam;
import com.hxoms.modules.privateabroad.mapper.OmsApprovalReturnMapper;
import com.hxoms.modules.privateabroad.service.OmsAbroadApprovalService;
import com.hxoms.modules.privateabroad.service.OmsApprovalReturnService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.service.SysDictItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * @desc: 因私出国审批回收管理
 * @author: lijing
 * @date: 2020-06-16
 */
@Service
public class OmsApprovalReturnServiceImpl implements OmsApprovalReturnService {
	@Autowired
	private OmsApprovalReturnMapper omsApprovalReturnMapper;
	@Autowired
	private CountryMapper countryMapper;

	@Autowired
	private SysDictItemService sysDictItemService;

	@Override
	public String savePriApprovalReturn(OmsApprovalReturn omsApprovalReturn) {
		if (StringUtils.isBlank(omsApprovalReturn.getApplyId())) {
			throw new CustomMessageException("参数错误");
		}
		// 登录用户信息
		UserInfo userInfo = UserInfoUtil.getUserInfo();
		if (StringUtils.isBlank(omsApprovalReturn.getId())) {
			// 新增
			omsApprovalReturn.setId(UUIDGenerator.getPrimaryKey());
			omsApprovalReturn.setCreateTime(new Date());
			omsApprovalReturn.setCreateUser(userInfo.getId());
			if (omsApprovalReturnMapper.insert(omsApprovalReturn) < 1) {
				throw new CustomMessageException("操作失败");
			}
		} else {
			// 编辑
			omsApprovalReturn.setModifyTime(new Date());
			omsApprovalReturn.setModifyUser(userInfo.getId());
			if (omsApprovalReturnMapper.updateById(omsApprovalReturn) < 1) {
				throw new CustomMessageException("操作失败");
			}
		}
		return "操作成功";
	}

	@Override
	public String deletePriApprovalReturn(OmsApprovalReturn omsApprovalReturn) {
		if (StringUtils.isBlank(omsApprovalReturn.getId())) {
			throw new CustomMessageException("参数错误");
		}
		if (omsApprovalReturnMapper.selectById(omsApprovalReturn.getId()) == null) {
			throw new CustomMessageException("该条信息不存在");
		}
		if (omsApprovalReturnMapper.deleteById(omsApprovalReturn.getId()) < 1) {
			throw new CustomMessageException("删除失败");
		}
		return "删除成功";
	}

	@Override
	public PageInfo<OmsApprovalReturnVO> selectPriApprovalReturnPagelist(
			OmsPriApprovalReturnIPageParam omsPriApprovalReturnIPageParam) {
		// 分页
		PageUtil.pageHelp(omsPriApprovalReturnIPageParam.getPageNum(), omsPriApprovalReturnIPageParam.getPageSize());
		List<OmsApprovalReturnVO> omsApprovalReturnVOS = omsApprovalReturnMapper
				.selectPriApprovalReturnPagelist(omsPriApprovalReturnIPageParam);
		// 查询途径国家
		for (OmsApprovalReturnVO item : omsApprovalReturnVOS) {
			QueryWrapper<Country> queryWrapper = new QueryWrapper<>();
			if (!StringUtils.isBlank(item.getOmsPriApplyVO().getGoCountry())) {
				queryWrapper.in("id", item.getOmsPriApplyVO().getGoCountry().split(","));
				List<Country> countries = countryMapper.selectList(queryWrapper);
				item.getOmsPriApplyVO().setCountries(countries);
			}
		}
		// 返回数据
		PageInfo<OmsApprovalReturnVO> pageInfo = new PageInfo(omsApprovalReturnVOS);
		return pageInfo;
	}

	@Override
	public OmsApprovalReturn selectPriApprovalReturnDestail(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			throw new CustomMessageException("参数错误");
		}
		QueryWrapper<OmsApprovalReturn> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("ID", "APPLY_ID", "RETURN_TIME", "RETURN_USER", "RETURN_DESC").eq("APPLY_ID", applyId);
		OmsApprovalReturn omsApprovalReturn = omsApprovalReturnMapper.selectOne(queryWrapper);
		if (omsApprovalReturn == null) {
			omsApprovalReturn = new OmsApprovalReturn();
			omsApprovalReturn.setApplyId(applyId);
		}
		return omsApprovalReturn;
	}

	@Override
	public void exportPriApprovalReturn(OmsPriApprovalReturnIPageParam iPageParam, HttpServletResponse response) {
		if (iPageParam == null) {
			throw new CustomMessageException("参数错误");
		}

		List<OmsApprovalReturnVO> list = omsApprovalReturnMapper.selectPriApprovalReturnPagelist(iPageParam);

		if (list.size() < 1 || list == null) {
			throw new CustomMessageException("操作失败");
		} else {
			List<Country> countries = new ArrayList<>();
			// 查询目的地国家
			for (OmsApprovalReturnVO item : list) {
				QueryWrapper<Country> queryWrapper = new QueryWrapper<>();
				if (!StringUtils.isBlank(item.getOmsPriApplyVO().getGoCountry())) {
					queryWrapper.in("id", item.getOmsPriApplyVO().getGoCountry().split(","));
					countries = countryMapper.selectList(queryWrapper);
				}
			}
			// 创建HSSFWorkbook对象(excel的文档对象)
			HSSFWorkbook wb = new HSSFWorkbook();
			// 建立新的sheet对象（excel的表单）
			HSSFSheet sheet = wb.createSheet("因私出国审批表回收登记表");
			// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
			HSSFRow row1 = sheet.createRow(0);
			// 创建单元格并设置单元格内容
			row1.createCell(0).setCellValue("序号");
			row1.createCell(1).setCellValue("单位");
			row1.createCell(2).setCellValue("姓名");
			row1.createCell(3).setCellValue("性别");
			row1.createCell(4).setCellValue("出境时间");
			row1.createCell(5).setCellValue("入境时间");
			row1.createCell(6).setCellValue("目的地");
			row1.createCell(7).setCellValue("事由");
			row1.createCell(8).setCellValue("回收时间");
			row1.createCell(9).setCellValue("回收人");
			// 在sheet里添加数据

			// 创建文件样式对象
			HSSFCellStyle style1 = wb.createCellStyle();
			// 获得字体对象
			HSSFFont font1 = wb.createFont();
			style1.setAlignment(HorizontalAlignment.LEFT); // 居左
			style1.setFont(font1);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			HSSFRow row = null;
			for (int i = 0; i < list.size(); i++) {
				OmsApprovalReturnVO omsApprovalReturnVO = list.get(i);
				OmsPriApplyVO omsPriApplyVO = omsApprovalReturnVO.getOmsPriApplyVO();
				row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(i + 1); // 序号
				row.createCell(1).setCellValue(omsPriApplyVO.getB0101()); // 单位
				row.createCell(2).setCellValue(omsPriApplyVO.getName());
				row.createCell(3).setCellValue(omsPriApplyVO.getSex().equals("1") ? "男" : "女"); // 性别
				row.createCell(4).setCellValue(sdf.format(omsPriApplyVO.getAbroadTime())); // 出境时间
				row.createCell(5).setCellValue(sdf.format(omsPriApplyVO.getReturnTime())); // 入境时间
				if (countries != null && !countries.isEmpty()) {
					String sendDocNum = "";
					for (Country country : countries) {
						String nameZh = country.getNameZh();
						if (nameZh != null) {
							sendDocNum += nameZh + ",";
						}
						if (nameZh.length() > 0) {
							sendDocNum = sendDocNum.substring(0, sendDocNum.length() - 1);
						}
					}

					row.createCell(6).setCellValue(sendDocNum); // 目的地
				}
				// 根据编码查询
				SysDictItem sysDictItem = sysDictItemService.selectByDictCodeAndItemCode("YSCGLY",
						omsPriApplyVO.getAbroadReasons());
				row.createCell(7).setCellValue(sysDictItem.getItemName()); // 事由
				row.createCell(8).setCellValue(sdf.format(omsApprovalReturnVO.getReturnTime())); // 回收时间
				row.createCell(9).setCellValue(omsApprovalReturnVO.getReturnUser()); // 回收人
				// 设置单元格字体大小
				for (int j = 0; j < 9; j++) {
					row.getCell(j).setCellStyle(style1);
				}
			}
			// excel文件名
			String fileName = "因私出国审批表回收登记表" + System.currentTimeMillis();
			// 输出Excel文件
			OutputStream output = null;
			try {
				output = response.getOutputStream();

				response.setContentType("application/x-download");// 下面三行是关键代码，处理乱码问题
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1") + ".xls");
				wb.write(output);
				output.flush();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
