package com.hxoms.modules.passportCard.omsCerTransferOutLicense.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.ListUtil;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity.OmsCerTransferOutLicense;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.mapper.OmsCerTransferOutLicenseMapper;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.service.OmsCerTransferOutLicenseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 转出证照业务层接口实现类
 * 省管变非省管，在撤销登记备案成功后，自动将该干部的证照写入转出证照申请表中，原单位到干部监督处领取证照办理交接手续
 * </b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/10 16:24
 */
@Service
public class OmsCerTransferOutLicenseServiceImpl extends ServiceImpl<OmsCerTransferOutLicenseMapper, OmsCerTransferOutLicense> implements OmsCerTransferOutLicenseService {


	@Autowired
	private OmsCerTransferOutLicenseMapper omsCerTransferOutLicenseMapper;
	/**
	 * <b>功能描述: 查询转出证照申请信息</b>
	 * @Param: [page,omsCerTransferOutLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	public Page<Map<String, Object>> selectTransferOutLicenseInfo(Page<Map<String,Object>> page ,OmsCerTransferOutLicense omsCerTransferOutLicense) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", omsCerTransferOutLicense.getName());
		map.put("zjlx", omsCerTransferOutLicense.getZjlx());
		map.put("BATCH_NUM", omsCerTransferOutLicense.getBatchNum());       //批次号
		map.put("handoverStartQueryTime", omsCerTransferOutLicense.getHandoverStartQueryTime());
		map.put("handoverEndQueryTime", omsCerTransferOutLicense.getHandoverEndQueryTime());
		map.put("exitStartQueryTime", omsCerTransferOutLicense.getExitStartQueryTime());
		map.put("exitEndQueryTime", omsCerTransferOutLicense.getExitEndQueryTime());
		PageHelper.startPage((int)page.getCurrent(), (int)page.getSize());
		List<Map<String,Object>> list = omsCerTransferOutLicenseMapper.selectTransferOutLicenseInfo(map);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
		page.setRecords(list);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		return page;
	}


	/**
	 * <b>功能描述: 查询转出证照申请的原单位信息</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	public List<OmsCerTransferOutLicense> getTransferOutWorkUnitInfo() {
		List<OmsCerTransferOutLicense> list = omsCerTransferOutLicenseMapper.getTransferOutWorkUnitInfo();
		return list;
	}



	/**
	 * <b>功能描述: 查询年份对应的批次号结构树</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/17 15:07
	 */
	public List<OmsCerTransferOutLicense> getBatchByYear() {
		List<OmsCerTransferOutLicense> list = omsCerTransferOutLicenseMapper.selectYearList();      //查询批次号的年份集合
		if(ListUtil.isEmpty(list)){
			for(OmsCerTransferOutLicense omsCerTransferOutLicense : list){
				String year = omsCerTransferOutLicense.getYear();
				List<OmsCerTransferOutLicense> list1 = omsCerTransferOutLicenseMapper.getBatchByYear(year);   //根据年份查询对应的批次号
				if(ListUtil.isEmpty(list1)){
					omsCerTransferOutLicense.setList(list1);
				}
			}
		}

		return list;
	}


	/**
	 * <b>功能描述: 打印后将年月日作为批次号，并记录移交人接手人</b>
	 * @Param: [list,omsCerTransferOutLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 15:07
	 */
	public void updateTransferOutRecord(List<String> list, OmsCerTransferOutLicense omsCerTransferOutLicense) {
		if(list == null || list.size() < 1 || StringUtils.isBlank(omsCerTransferOutLicense.getBatchNum())){
			throw new CustomMessageException("参数错误");
		}
		String year = null;
		try {
			year = omsCerTransferOutLicense.getBatchNum().substring(0, (omsCerTransferOutLicense.getBatchNum()).indexOf("年"));
		}catch (StringIndexOutOfBoundsException e){
			year = UtilDateTime.getYear(new Date());
			e.printStackTrace();
		}
		omsCerTransferOutLicense.setYear(year);
		omsCerTransferOutLicense.setTransferor(UserInfoUtil.getUserInfo().getName());
		omsCerTransferOutLicense.setModifyTime(new Date());
		omsCerTransferOutLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
		QueryWrapper<OmsCerTransferOutLicense> queryWrapper = new QueryWrapper<OmsCerTransferOutLicense>();
		queryWrapper.in(!ListUtil.isEmpty(list), "ID", list);
		int count = omsCerTransferOutLicenseMapper.update(omsCerTransferOutLicense, queryWrapper);
		if(count < 1){
			throw new CustomMessageException("保存记录失败");
		}

	}
}






