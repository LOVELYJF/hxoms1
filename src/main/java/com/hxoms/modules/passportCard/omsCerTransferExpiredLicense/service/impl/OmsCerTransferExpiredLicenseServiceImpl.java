package com.hxoms.modules.passportCard.omsCerTransferExpiredLicense.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.omsCerTransferExpiredLicense.service.OmsCerTransferExpiredLicenseService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.service.ApiListing;
import sun.util.resources.cldr.zh.CalendarData_zh_Hans_MO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.MarshalledObject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 过期证照转存业务层接口实现类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/5 10:24
 */
@Service
public class OmsCerTransferExpiredLicenseServiceImpl implements OmsCerTransferExpiredLicenseService {



	@Autowired
	private CfCertificateMapper cfCertificateMapper;
	/**
	 * <b>功能描述: 查询过期证照信息</b>
	 * @Param: [page,list,expiredQueryStartTime,expiredQueryEndTime,cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/17 14:16
	 */
	public Page<Map<String, Object>> getTransferExpiredLicenseInfo(Page<Map<String,Object>> page, List<String> list, Date expiredQueryStartTime,
	                                                               Date expiredQueryEndTime, CfCertificate cfCertificate) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list",list);
		map.put("expiredQueryStartTime",expiredQueryStartTime);
		map.put("expiredQueryEndTime",expiredQueryEndTime);
		map.put("zjlx", cfCertificate.getZjlx());
		map.put("saveStatus", cfCertificate.getSaveStatus());
		PageHelper.startPage((int)page.getCurrent(), (int)page.getSize());
		List<Map<String,Object>> list1 = cfCertificateMapper.getTransferExpiredLicenseInfo(map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list1);
		page.setRecords(list1);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		return page;
	}


	/**
	 * <b>功能描述: 过期证照转存（导出）</b>
	 * @Param: [list,expiredQueryStartTime,expiredQueryEndTime,cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:16
	 */
	public void getTransferExpiredLicenseOut(List<String> list, Date expiredQueryStartTime, Date expiredQueryEndTime, CfCertificate cfCertificate, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list",list);
		map.put("expiredQueryStartTime",expiredQueryStartTime);
		map.put("expiredQueryEndTime",expiredQueryEndTime);
		map.put("zjlx", cfCertificate.getZjlx());
		map.put("saveStatus", cfCertificate.getSaveStatus());
		List<Map<String,Object>> list1 = cfCertificateMapper.getTransferExpiredLicenseInfo(map);

		if(list1.size() < 1 || list1 == null){
			throw new CustomMessageException("操作失败");
		}else {
			//创建HSSFWorkbook对象(excel的文档对象)
			HSSFWorkbook wb = new HSSFWorkbook();
			//创建文件样式对象
			HSSFCellStyle style = wb.createCellStyle();
			//获得字体对象
			HSSFFont font = wb.createFont();
			//建立新的sheet对象（excel的表单）
			HSSFSheet sheet=wb.createSheet("过期证照记录表");
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
			cell.setCellValue("过期证照记录表");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,16));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(2);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("姓名");
			row2.createCell(2).setCellValue("性别");
			row2.createCell(3).setCellValue("单位");
			row2.createCell(4).setCellValue("职务");
			row2.createCell(5).setCellValue("证照类型");
			row2.createCell(6).setCellValue("证件号码");
			row2.createCell(7).setCellValue("有效期至");
			row2.createCell(8).setCellValue("证照状态");
			row2.createCell(9).setCellValue("保管状态");
			row2.createCell(10).setCellValue("机柜");
			row2.createCell(11).setCellValue("位置");
			row2.createCell(12).setCellValue("出生日期");
			row2.createCell(13).setCellValue("签发单位");
			row2.createCell(14).setCellValue("签发日期");
			row2.createCell(15).setCellValue("出生地");
			row2.createCell(16).setCellValue("取出时间");

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
				row.createCell(1).setCellValue((String) list1.get(i).get("name"));
				row.createCell(2).setCellValue(((Integer) list1.get(i).get("sex")) == 1 ? "男" : "女");
				row.createCell(3).setCellValue((String) list1.get(i).get("workUnit"));
				row.createCell(4).setCellValue((String)list1.get(i).get("post"));
				row.createCell(5).setCellValue(Constants.CER_TYPE_NAME[(Integer)list1.get(i).get("zjlx") - 1]);
				row.createCell(6).setCellValue((String)list1.get(i).get("zjhm"));
				row.createCell(7).setCellValue(((String) list1.get(i).get("yxqz")));
				row.createCell(8).setCellValue(Constants.CER_NAME[(Integer)list1.get(i).get("cardStatus")]);
				row.createCell(9).setCellValue(Constants.CER_SAVE_NAME[(Integer)list1.get(i).get("saveStatus")]);
				row.createCell(10).setCellValue((String) list1.get(i).get("cabinetNum"));
				row.createCell(11).setCellValue((String) list1.get(i).get("place"));
				row.createCell(12).setCellValue((String) list1.get(i).get("csrq"));
				row.createCell(13).setCellValue((String) list1.get(i).get("qfjg"));
				row.createCell(14).setCellValue((String) list1.get(i).get("qfrq"));
				row.createCell(15).setCellValue((String) list1.get(i).get("csdd"));
				row.createCell(16).setCellValue((String) list1.get(i).get("getTime"));

				//设置单元格字体大小
				for(int j = 0;j < 17;j++){
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
	 * <b>功能描述: 转存</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:16
	 */
	public List<CfCertificate> getTransferExpiredLicenseDeposit(List<CfCertificate> list) {
		if(list != null && list.size() > 0){
			for(CfCertificate cfCertificate : list){
				if(cfCertificate.getSaveStatus().equals("1")){          //判断证件的取出状态，只有取出证照机的可以转存
					//根据查询证照是否存在柜台存放号码
					String counterNum = cfCertificateMapper.selectCounterNum(cfCertificate.getId());
					if(counterNum != null && counterNum != ""){         //存在柜台号码，不用重新生成柜台号码
						cfCertificate.setCounterNum(counterNum);
					}else {                                             //柜台号码不存在，生成柜台号码

						//根据卡式和本式进行号码的生成
						if(cfCertificate.getZjxs().equals("0")){            //本式证照





						}else if (cfCertificate.getZjxs().equals("1")){     //卡式证照






						}

					}
				}else {
					throw new CustomMessageException("选择的证照中有证照机中未取出的证照");
				}
			}
		}else {
			throw new CustomMessageException("未选择要转存的证照");
		}
		return list;
	}


	/**
	 * <b>功能描述: 转存（保存转存信息），修改证照保存状态</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/18 14:16
	 */
	public void getTransferExpiredLicenseSave(List<CfCertificate> list) {
		if(list != null && list.size() > 0){
			for(CfCertificate cfCertificate : list){
				cfCertificate.setSaveStatus(String.valueOf(Constants.CER_SAVE_STATUS[0]));      //状态置为正常保管
				int count = cfCertificateMapper.updateById(cfCertificate);
				if(count < 1){
					throw new CustomMessageException("转存更改状态失败");
				}
			}
		}else {
			throw new CustomMessageException("参数错误");
		}
	}
}
