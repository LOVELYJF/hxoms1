package com.hxoms.modules.passportCard.omsCerInventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.mapper.OmsCerGetTaskMapper;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.exitEntryManage.mapper.OmsCerExitEntryRepertoryMapper;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.omsCerInventory.entity.OmsCerInventory;
import com.hxoms.modules.passportCard.omsCerInventory.mapper.OmsCerInventoryMapper;
import com.hxoms.modules.passportCard.omsCerInventory.service.OmsCerInventoryService;
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
 * <b>功能描述: 证照盘点业务层接口实现类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/19 11:47
 */
@Service
public class OmsCerInventoryServiceImpl implements OmsCerInventoryService {


	@Autowired
	private CfCertificateMapper cfCertificateMapper;
	@Autowired
	private OmsCerInventoryMapper omsCerInventoryMapper;
	@Autowired
	private OmsCerGetTaskMapper omsCerGetTaskMapper;
	@Autowired
	private OmsCerExitEntryRepertoryMapper omsCerExitEntryRepertoryMapper;
	/**
	 * <b>功能描述: （开始盘点、证照机盘点）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	@Transactional(rollbackFor = Exception.class)
	public void insertCerInventoryInfoForCabinet(OmsCerInventory omsCerInventory) {

		//在此处判断是否已经盘点过
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("cabinetNum", omsCerInventory.getCabinetNum());
		result.put("inventoryDate",UtilDateTime.formatCNMonth(new Date()));
		List<Map<String,Object>>  resultList = omsCerInventoryMapper.selectCerInventoryResultForCabinet(result);
		if(resultList.size() > 0){
			throw new CustomMessageException("该证照柜已经完成盘点，查询请点击统计盘点结果");
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cabinetNum", omsCerInventory.getCabinetNum());
		map.put("cardStatus", "0");
		List<CfCertificate> list = cfCertificateMapper.selectOmsCerInfo(map);       //查询正常状态且正常保管或已取出状态的证照信息

		//将查询结果保存到盘点表中
		for(CfCertificate cfCertificate : list){
			OmsCerInventory omsCerInventory1 = new OmsCerInventory();
			omsCerInventory1.setId(UUIDGenerator.getPrimaryKey());
			omsCerInventory1.setOmsId(cfCertificate.getOmsId());
			omsCerInventory1.setCfId(cfCertificate.getId());
			omsCerInventory1.setName(cfCertificate.getName());
			omsCerInventory1.setA0100(cfCertificate.getA0100());
			omsCerInventory1.setCsrq(cfCertificate.getCsrq());
			omsCerInventory1.setZjlx(String.valueOf(cfCertificate.getZjlx()));
			omsCerInventory1.setZjhm(cfCertificate.getZjhm());
			omsCerInventory1.setYxqz(cfCertificate.getYxqz());
			omsCerInventory1.setCabinetNum(cfCertificate.getCabinetNum());
			omsCerInventory1.setCardStatus(cfCertificate.getCardStatus());
			omsCerInventory1.setBeforeInventorySaveStatus(cfCertificate.getSaveStatus());
			omsCerInventory1.setInventoryDate(UtilDateTime.formatCNMonth(new Date()));         //盘点年月
			omsCerInventory1.setCreateTime(new Date());
			omsCerInventory1.setCreateUser(UserInfoUtil.getUserInfo().getId());
			int count = omsCerInventoryMapper.insert(omsCerInventory1);
			if(count < 1){
				throw new CustomMessageException("保存到证照盘点表失败");
			}else {
				cfCertificate.setCabinetNum("");
				cfCertificate.setPlace("");
				cfCertificate.setSaveStatus("1");           //全部将状态置为已取出
				int count1 = cfCertificateMapper.updateById(cfCertificate);
				if(count1 < 1){
					throw new CustomMessageException("清空该证件的机柜位置失败");
				}
			}
		}
	}


	/**
	 * <b>功能描述: 统计盘点结果（证照机）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> GetCerInventoryResultForCabinet(OmsCerInventory omsCerInventory) {

		//盘点后重新查询证照状态
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cabinetNum", omsCerInventory.getCabinetNum());
		map.put("cardStatus", "0");
		//查询证照主键
		List<String> idList = omsCerInventoryMapper.selectOmsCerIdList(map);
		QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<CfCertificate>();
		queryWrapper.in(idList != null && idList.size() > 0,"ID",idList);
		List<CfCertificate> list = cfCertificateMapper.selectList(queryWrapper);

		//同步盘点结果到盘点表
		for(CfCertificate cfCertificate : list){
			OmsCerInventory omsCerInventory1 = new OmsCerInventory();
			omsCerInventory1.setAfterInventorySaveStatus(cfCertificate.getSaveStatus());
			omsCerInventory1.setCabinetNum(cfCertificate.getCabinetNum());
			omsCerInventory1.setPlace(cfCertificate.getPlace());
			omsCerInventory1.setModifyTime(new Date());
			omsCerInventory1.setModifyUser(UserInfoUtil.getUserInfo().getId());

			//设置同步条件
			String inventoryDate = UtilDateTime.formatCNMonth(new Date());
			QueryWrapper<OmsCerInventory> wrapper = new QueryWrapper<OmsCerInventory>();
			wrapper.eq("INVENTORY_DATE", inventoryDate)            //盘点年月
					.eq("ZJHM", cfCertificate.getZjhm())                //证件号码
					.eq("YXQZ", cfCertificate.getYxqz());               //有效期至

			omsCerInventoryMapper.update(omsCerInventory1, wrapper);

		}

		//查询将盘点前后保管状态不一样的显示到界面
		map.put("inventoryDate",UtilDateTime.formatCNMonth(new Date()));
		map.put("sameStatus","1");          //盘点前后状态是否一致（仅做xml文件的条件）
		List<Map<String,Object>> resultList = omsCerInventoryMapper.selectCerInventoryResultForCabinet(map);

		Map<String,Object> inventoryResult = new HashMap<String,Object>();
		inventoryResult.put("resultList", resultList);          //将前后盘点结果不一样的添加到页面

		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("cabinetNum",omsCerInventory.getCabinetNum());
		String inventoryDate = UtilDateTime.formatCNMonth(new Date());
		map1.put("inventoryDate",inventoryDate);
		Map<String, Integer> resultmap = omsCerInventoryMapper.getCerInventoryStatisticsNum(map);

		inventoryResult.put("resultMap", resultmap);            //将盘点统计数量显示到页面
		return inventoryResult;
	}


	/**
	 * <b>功能描述: 保存盘点结果（证照机）</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	public void updateCerInventoryResultForCabinet(List<OmsCerInventory> list) {
		if(list != null && list.size() > 0){
			for(OmsCerInventory omsCerInventory : list){
				omsCerInventory.setModifyTime(new Date());
				omsCerInventory.setModifyUser(UserInfoUtil.getUserInfo().getId());
				int count = omsCerInventoryMapper.updateById(omsCerInventory);
				if(count < 1){
					throw new CustomMessageException("更新保存盘点结果失败");
				}
			}
		}
	}



	/**
	 * <b>功能描述: 导出盘点结果（证照机）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/19 14:38
	 */
	public void getCerInventoryResultForCabinetOut(OmsCerInventory omsCerInventory, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cabinetNum", omsCerInventory.getCabinetNum());
		map.put("cardStatus", "0");
		map.put("inventoryDate",UtilDateTime.formatCNMonth(new Date()));
		List<Map<String,Object>> list = omsCerInventoryMapper.selectCerInventoryResultForCabinet(map);

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
			HSSFSheet sheet=wb.createSheet(UtilDateTime.formatCNMonth(new Date()) + omsCerInventory.getCabinetNum() + "证照机盘点结果");
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
			cell.setCellValue(UtilDateTime.formatCNMonth(new Date()) + "（" + omsCerInventory.getCabinetNum() + "）证照机盘点结果");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,14));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(2);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("单位");
			row2.createCell(2).setCellValue("姓名");
			row2.createCell(3).setCellValue("性别");
			row2.createCell(4).setCellValue("出生日期");
			row2.createCell(5).setCellValue("职务");
			row2.createCell(6).setCellValue("证照类型");
			row2.createCell(7).setCellValue("证照号码");
			row2.createCell(8).setCellValue("有效期至");
			row2.createCell(9).setCellValue("证照状态");
			row2.createCell(10).setCellValue("盘点前保管状态");
			row2.createCell(11).setCellValue("盘点后保管状态");
			row2.createCell(12).setCellValue("机柜");
			row2.createCell(13).setCellValue("位置");
			row2.createCell(14).setCellValue("盘点结果");
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
				row.createCell(4).setCellValue(UtilDateTime.formatCNDate((Date) list.get(i).get("csrq")));
				row.createCell(5).setCellValue((String) list.get(i).get("post"));
				row.createCell(6).setCellValue(CerTypeUtil.getCnTypeLicence(Integer.parseInt((String)list.get(i).get("zjlx"))));
				row.createCell(7).setCellValue((String) list.get(i).get("zjhm"));
				row.createCell(8).setCellValue(UtilDateTime.formatCNDate((Date)list.get(i).get("yxqz")));
				row.createCell(9).setCellValue(Constants.CER_NAME[Integer.parseInt(list.get(i).get("cardStatus").toString())]);
				row.createCell(10).setCellValue(Constants.CER_SAVE_NAME[Integer.parseInt(list.get(i).get("beforeInventorySaveStatus").toString())]);
				row.createCell(11).setCellValue(Constants.CER_SAVE_NAME[Integer.parseInt(list.get(i).get("afterInventorySaveStatus").toString())]);
				row.createCell(12).setCellValue((String) list.get(i).get("cabinetNum"));
				row.createCell(13).setCellValue((String) list.get(i).get("place"));
				row.createCell(14).setCellValue((String) list.get(i).get("inventoryResult"));
				//设置单元格字体大小
				for(int j = 0;j < 15;j++){
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
	 * <b>功能描述: 开始盘点（柜台）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> insertCerInventoryInfoForCounter(OmsCerInventory omsCerInventory) {

		//查询是否有已经盘点的证照号码
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("counterStartQuery", omsCerInventory.getCounterStartQuery());
		result.put("counterEndQuery", omsCerInventory.getCounterEndQuery());
		result.put("inventoryDate",UtilDateTime.formatCNMonth(new Date()));
		result.put("cardStatus", "0");
		List<Map<String,Object>>  inventoryList = omsCerInventoryMapper.selectCerInventoryResultForCabinet(result);
		if(inventoryList.size() > 0){
			return inventoryList;
		}


		Map<String,Object> map = new HashMap<String,Object>();
		map.put("counterStartQuery", omsCerInventory.getCounterStartQuery());
		map.put("counterEndQuery", omsCerInventory.getCounterEndQuery());
		map.put("cardStatus", "0");
		map.put("isCounter", "1");              //取柜台的证照
		List<CfCertificate> list = cfCertificateMapper.selectOmsCerInfo(map); //查询号码范围内正常状态且正常保管或已取出状态的证照信息

		//将查询结果保存到盘点表中
		for(CfCertificate cfCertificate : list){
			OmsCerInventory omsCerInventory1 = new OmsCerInventory();
			omsCerInventory1.setId(UUIDGenerator.getPrimaryKey());
			omsCerInventory1.setOmsId(cfCertificate.getOmsId());
			omsCerInventory1.setName(cfCertificate.getName());
			omsCerInventory1.setA0100(cfCertificate.getA0100());
			omsCerInventory1.setCsrq(cfCertificate.getCsrq());
			omsCerInventory1.setZjlx(String.valueOf(cfCertificate.getZjlx()));
			omsCerInventory1.setZjhm(cfCertificate.getZjhm());
			omsCerInventory1.setYxqz(cfCertificate.getYxqz());
			omsCerInventory1.setCardStatus(cfCertificate.getCardStatus());
			omsCerInventory1.setCounterNum(cfCertificate.getCounterNum());
			omsCerInventory1.setBeforeInventorySaveStatus(cfCertificate.getSaveStatus());
			omsCerInventory1.setAfterInventorySaveStatus("1");                                 //将所有保管后状态置为已取出
			omsCerInventory1.setInventoryDate(UtilDateTime.formatCNMonth(new Date()));         //盘点年月
			omsCerInventory1.setCreateTime(new Date());
			omsCerInventory1.setCreateUser(UserInfoUtil.getUserInfo().getId());
			int count = omsCerInventoryMapper.insert(omsCerInventory1);
			if(count < 1){
				throw new CustomMessageException("保存到证照盘点表失败");
			}
		}

		map.put("inventoryDate",UtilDateTime.formatCNMonth(new Date()));
		List<Map<String,Object>> resultList = omsCerInventoryMapper.selectCerInventoryResultForCabinet(map);
		return resultList;
	}



	/**
	 * <b>功能描述: 保存盘点结果（柜台）</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Integer> updateCerInventoryResultForCounter(List<OmsCerInventory> list) {
		if(list != null && list.size() > 0){
			for(OmsCerInventory omsCerInventory : list){
				omsCerInventory.setModifyUser(UserInfoUtil.getUserInfo().getId());
				omsCerInventory.setModifyTime(new Date());
				int count = omsCerInventoryMapper.updateById(omsCerInventory);
				if(count < 1){
					throw new CustomMessageException("更新保存盘点结果失败");
				}else {
					//在证照信息表中更新柜台位置，存取状态
					CfCertificate cfCertificate = new CfCertificate();
					cfCertificate.setCounterNum(omsCerInventory.getCounterNum());
					cfCertificate.setSaveStatus(omsCerInventory.getAfterInventorySaveStatus());

					QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<CfCertificate>();
					queryWrapper.eq("ZJHM", omsCerInventory.getZjhm());
					int count1 = cfCertificateMapper.update(cfCertificate, queryWrapper);
					if(count1 < 1){
						throw new CustomMessageException("更新到证照信息表失败");
					}
				}
			}
		}

		//统计盘点数量
		Integer beforeNormal = 0;
		Integer beforeTakeOut = 0;
		Integer beforeTotal = 0;
		Integer afterNormal = 0;
		Integer afterTakeOut = 0;
		Integer afterTotal = 0;

		for(OmsCerInventory omsCerInventory : list){
			if(omsCerInventory.getBeforeInventorySaveStatus().equals("0")){
				beforeNormal++;
			}else if(omsCerInventory.getBeforeInventorySaveStatus().equals("1")){
				beforeTakeOut++;
			}

			if(omsCerInventory.getAfterInventorySaveStatus().equals("0")){
				afterNormal++;
			}else if(omsCerInventory.getAfterInventorySaveStatus().equals("1")){
				afterTakeOut++;
			}
		}
		beforeTotal = beforeNormal + beforeTakeOut;
		afterTotal = afterNormal + afterTakeOut;

		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("beforeNormal",beforeNormal);
		map.put("beforeTakeOut",beforeTakeOut);
		map.put("beforeTotal",beforeTotal);
		map.put("afterNormal",afterNormal);
		map.put("afterTakeOut",afterTakeOut);
		map.put("afterTotal",afterTotal);
		return map;
	}



	/**
	 * <b>功能描述: 导出盘点结果（柜台）</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	public void getCerInventoryResultForCounterOut(OmsCerInventory omsCerInventory, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("counterStartQuery", omsCerInventory.getCounterStartQuery());
		map.put("counterEndQuery", omsCerInventory.getCounterEndQuery());
		map.put("cardStatus", "0");
		map.put("inventoryDate",UtilDateTime.formatCNMonth(new Date()));
		List<Map<String,Object>> list = omsCerInventoryMapper.selectCerInventoryResultForCabinet(map);


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
			HSSFSheet sheet=wb.createSheet(UtilDateTime.formatCNMonth(new Date()) + "柜台证照盘点结果");
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
			cell.setCellValue(UtilDateTime.formatCNMonth(new Date()) + "柜台从 " + omsCerInventory.getCounterStartQuery()  + " 到 " + omsCerInventory.getCounterEndQuery() + " 号证照盘点结果");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,13));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(2);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("单位");
			row2.createCell(2).setCellValue("姓名");
			row2.createCell(3).setCellValue("性别");
			row2.createCell(4).setCellValue("出生日期");
			row2.createCell(5).setCellValue("职务");
			row2.createCell(6).setCellValue("证照类型");
			row2.createCell(7).setCellValue("证照号码");
			row2.createCell(8).setCellValue("有效期至");
			row2.createCell(9).setCellValue("证照状态");
			row2.createCell(10).setCellValue("盘点前保管状态");
			row2.createCell(11).setCellValue("盘点后保管状态");
			row2.createCell(12).setCellValue("柜台编号");
			row2.createCell(13).setCellValue("盘点结果");
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
				row.createCell(4).setCellValue(UtilDateTime.formatCNDate((Date) list.get(i).get("csrq")));
				row.createCell(5).setCellValue((String) list.get(i).get("post"));
				row.createCell(6).setCellValue(CerTypeUtil.getCnTypeLicence(Integer.parseInt((String)list.get(i).get("zjlx"))));
				row.createCell(7).setCellValue((String) list.get(i).get("zjhm"));
				row.createCell(8).setCellValue(UtilDateTime.formatCNDate((Date)list.get(i).get("yxqz")));
				row.createCell(9).setCellValue(Constants.CER_NAME[Integer.parseInt(list.get(i).get("cardStatus").toString())]);
				row.createCell(10).setCellValue(Constants.CER_SAVE_NAME[Integer.parseInt(list.get(i).get("beforeInventorySaveStatus").toString())]);
				row.createCell(11).setCellValue(Constants.CER_SAVE_NAME[Integer.parseInt(list.get(i).get("afterInventorySaveStatus").toString())]);
				row.createCell(12).setCellValue((String) list.get(i).get("counterNum"));
				row.createCell(13).setCellValue((String) list.get(i).get("inventoryResult"));
				//设置单元格字体大小
				for(int j = 0;j < 14;j++){
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
	 * <b>功能描述: 总体盘点结果统计查询</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	public Map<String,Object> GetCerInventoryResult(List<String> list,OmsCerInventory omsCerInventory) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("sameStatus", "1");
		map.put("inventoryDate", omsCerInventory.getInventoryDate());
		map.put("counterStartQuery", omsCerInventory.getCounterStartQuery());
		map.put("counterEndQuery", omsCerInventory.getCounterEndQuery());
		List<Map<String,Object>> resultList = omsCerInventoryMapper.GetCerInventoryResult(map);

		//统计盘点数量
		Integer beforeNormal = 0;
		Integer beforeTakeOut = 0;
		Integer beforeTotal = 0;
		Integer afterNormal = 0;
		Integer afterTakeOut = 0;
		Integer afterTotal = 0;

		for(Map<String,Object> map1 : resultList){
			if(map1.get("beforeInventorySaveStatus").toString().equals("0")){
				beforeNormal++;
			}else if(map1.get("beforeInventorySaveStatus").toString().equals("1")){
				beforeTakeOut++;
			}

			if(map1.get("afterInventorySaveStatus").toString().equals("0")){
				afterNormal++;
			}else if(map1.get("afterInventorySaveStatus").toString().equals("1")){
				afterTakeOut++;
			}
		}
		beforeTotal = beforeNormal + beforeTakeOut;
		afterTotal = afterNormal + afterTakeOut;

		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("beforeNormal",beforeNormal);
		resultMap.put("beforeTakeOut",beforeTakeOut);
		resultMap.put("beforeTotal",beforeTotal);
		resultMap.put("afterNormal",afterNormal);
		resultMap.put("afterTakeOut",afterTakeOut);
		resultMap.put("afterTotal",afterTotal);
		resultMap.put("resultList",resultList);

		return resultMap;
	}


	/**
	 * <b>功能描述: 总体查询导出盘点结果</b>
	 * @Param: [omsCerInventory]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	public void getCerInventoryResultOut(List<String> list, OmsCerInventory omsCerInventory, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("inventoryDate", omsCerInventory.getInventoryDate());
		map.put("counterStartQuery", omsCerInventory.getCounterStartQuery());
		map.put("counterEndQuery", omsCerInventory.getCounterEndQuery());
		List<Map<String,Object>> resultList = omsCerInventoryMapper.GetCerInventoryResult(map);


		if(resultList.size() < 1 || resultList == null){
			throw new CustomMessageException("操作失败");
		}else {
			//创建HSSFWorkbook对象(excel的文档对象)
			HSSFWorkbook wb = new HSSFWorkbook();
			//创建文件样式对象
			HSSFCellStyle style = wb.createCellStyle();
			//获得字体对象
			HSSFFont font = wb.createFont();
			//建立新的sheet对象（excel的表单）
			HSSFSheet sheet=wb.createSheet(UtilDateTime.formatCNMonth(new Date()) + "证照盘点结果");
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
			cell.setCellValue(UtilDateTime.formatCNMonth(new Date()) + "证照盘点结果");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,15));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(2);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("单位");
			row2.createCell(2).setCellValue("姓名");
			row2.createCell(3).setCellValue("性别");
			row2.createCell(4).setCellValue("出生日期");
			row2.createCell(5).setCellValue("职务");
			row2.createCell(6).setCellValue("证照类型");
			row2.createCell(7).setCellValue("证照号码");
			row2.createCell(8).setCellValue("有效期至");
			row2.createCell(9).setCellValue("证照状态");
			row2.createCell(10).setCellValue("盘点前保管状态");
			row2.createCell(11).setCellValue("盘点后保管状态");
			row2.createCell(12).setCellValue("机柜编号");
			row2.createCell(13).setCellValue("机柜位置");
			row2.createCell(14).setCellValue("柜台编号");
			row2.createCell(15).setCellValue("盘点结果");
			//在sheet里添加数据

			//创建文件样式对象
			HSSFCellStyle style1 = wb.createCellStyle();
			//获得字体对象
			HSSFFont font1 = wb.createFont();
			style1.setAlignment(HorizontalAlignment.LEFT); //居左
			style1.setFont(font1);

			HSSFRow row = null;
			for(int i = 0; i < resultList.size(); i++){
				row = sheet.createRow(i + 3);
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue((String) resultList.get(i).get("workUnit"));
				row.createCell(2).setCellValue((String) resultList.get(i).get("name"));
				row.createCell(3).setCellValue(String.valueOf(resultList.get(i).get("sex")).equals("1") ? "男" : "女");
				row.createCell(4).setCellValue(UtilDateTime.formatCNDate((Date) resultList.get(i).get("csrq")));
				row.createCell(5).setCellValue((String) resultList.get(i).get("post"));
				row.createCell(6).setCellValue(CerTypeUtil.getCnTypeLicence(Integer.parseInt((String)resultList.get(i).get("zjlx"))));
				row.createCell(7).setCellValue((String) resultList.get(i).get("zjhm"));
				row.createCell(8).setCellValue(UtilDateTime.formatCNDate((Date)resultList.get(i).get("yxqz")));
				row.createCell(9).setCellValue(Constants.CER_NAME[Integer.parseInt(resultList.get(i).get("cardStatus").toString())]);
				row.createCell(10).setCellValue(Constants.CER_SAVE_NAME[Integer.parseInt(resultList.get(i).get("beforeInventorySaveStatus").toString())]);
				row.createCell(11).setCellValue(Constants.CER_SAVE_NAME[Integer.parseInt(resultList.get(i).get("afterInventorySaveStatus").toString())]);
				row.createCell(12).setCellValue((String) resultList.get(i).get("cabinetNum"));
				row.createCell(13).setCellValue((String) resultList.get(i).get("place"));
				row.createCell(14).setCellValue((String) resultList.get(i).get("counterNum"));
				row.createCell(15).setCellValue((String) resultList.get(i).get("inventoryResult"));
				//设置单元格字体大小
				for(int j = 0;j < 16;j++){
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
	 * <b>功能描述: 查询所有的柜子集合</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	public List<String> getCerLicenseMachine() {
		List<String> list = cfCertificateMapper.getCerLicenseMachine();
		return list;
	}



	/**
	 * <b>功能描述: 存取记录</b>
	 * @Param: [cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/20 14:38
	 */
	public List<Map<String, Object>> getCerAccessRecord(CfCertificate cfCertificate) {
		//根据证件号码查询证件存取记录
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("zjhm", cfCertificate.getZjhm());
		List<Map<String,Object>> list = cfCertificateMapper.getCerAccessRecord(map);
		return list;
	}


	/**
	 * <b>功能描述: 补领取记录</b>
	 * @Param: [omsCerGetTask]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/24 14:38
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveRepairCollectionRecord(OmsCerGetTask omsCerGetTask) {
		omsCerGetTask.setId(UUIDGenerator.getPrimaryKey());
		//查询证照表的ID
		QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<CfCertificate>();
		queryWrapper.eq("ZJHM", omsCerGetTask.getZjhm());
		CfCertificate cfCertificate = cfCertificateMapper.selectOne(queryWrapper);
		omsCerGetTask.setCerId(cfCertificate.getId());
		omsCerGetTask.setGetStatus("1");    //任务表中状态置为已领取
		omsCerGetTask.setDataSource("6");
		omsCerGetTask.setCreator(UserInfoUtil.getUserInfo().getId());
		omsCerGetTask.setCreateTime(new Date());
		int count = omsCerGetTaskMapper.insert(omsCerGetTask);
		if(count < 1){
			throw new CustomMessageException("保存补录取证失败");
		}

		OmsCerExitEntryRepertory omsCerExitEntryRepertory = new OmsCerExitEntryRepertory();
		omsCerExitEntryRepertory.setId(UUIDGenerator.getPrimaryKey());
		omsCerExitEntryRepertory.setGetId(omsCerGetTask.getId());
		omsCerExitEntryRepertory.setName(omsCerGetTask.getName());
		omsCerExitEntryRepertory.setZjhm(omsCerGetTask.getZjhm());
		omsCerExitEntryRepertory.setZjlx(omsCerGetTask.getZjlx());
		omsCerExitEntryRepertory.setStatus("0");
		omsCerExitEntryRepertory.setMode("0");

		omsCerExitEntryRepertory.setOperator(UserInfoUtil.getUserInfo().getId());
		omsCerExitEntryRepertory.setOperateTime(new Date());
		int count1 = omsCerExitEntryRepertoryMapper.insert(omsCerExitEntryRepertory);
		if(count1 < 1){
			throw new CustomMessageException("保存到存取记录表失败");
		}

	}


}














