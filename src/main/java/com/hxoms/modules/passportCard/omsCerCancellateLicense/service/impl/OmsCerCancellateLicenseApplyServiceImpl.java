package com.hxoms.modules.passportCard.omsCerCancellateLicense.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.keySupervision.caseInfo.entity.OmsSupCaseInfo;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.OmsCerCounterNumber;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateApply;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateRecords;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper.OmsCerCancellateApplyMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper.OmsCerCancellateLicenseMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper.OmsCerCancellateRecordsMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.service.OmsCerCancellateLicenseApplyService;
import com.hxoms.support.b01.entity.B01;
import com.hxoms.support.b01.mapper.B01Mapper;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import com.hxoms.support.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 注销证照申请业务层实现类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/5 10:24
 */
@Service
public class OmsCerCancellateLicenseApplyServiceImpl implements OmsCerCancellateLicenseApplyService {

	@Autowired
	private CfCertificateMapper cfCertificateMapper;
	@Autowired
	private OmsCerCancellateLicenseMapper omsCerCancellateLicenseMapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	@Autowired
	private B01Mapper b01Mapper;
	@Autowired
	private OmsCerCancellateRecordsMapper omsCerCancellateRecordsMapper;
	@Autowired
	private OmsCerCancellateApplyMapper omsCerCancellateApplyMapper;
	/**
	 * <b>功能描述: 填写注销申请（查询）</b>
	 * @Param: [cfCertificate]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 10:29
	 */
	public List<Map<String, Object>> getCancellateLicense(CfCertificate cfCertificate) {
		if((cfCertificate.getName() == null && cfCertificate.getZjhm() == null) ||
				(cfCertificate.getName().equals("") && cfCertificate.getZjhm().equals(""))){
			throw new CustomMessageException("请输入姓名或证件号码进行查询");
		}


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", cfCertificate.getName());
		map.put("zjhm", cfCertificate.getZjhm());
		List<Map<String,Object>> list = cfCertificateMapper.getCancellateLicense(map);
		if(list == null || list.size() < 1){
			throw new CustomMessageException("没有查询到相关的证照信息");
		}
		return list;
	}


	/**
	 * <b>功能描述: 填写注销申请进行下一步</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 11:50
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String,Object> saveCancellateLicenseChoose(List<OmsCerCancellateLicense> list) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableCode", "oms_cer_cancellate");

		if(list == null || list.size() < 1){
			throw new CustomMessageException("未选择注销的证照信息");
		}

		//判断选择的是否是同一个人
		String omsId = list.get(0).getOmsId();
		for(OmsCerCancellateLicense omsCerCancellateLicense : list){
			if(!omsCerCancellateLicense.getOmsId().equals(omsId)){
				throw new CustomMessageException("选中的多个人员不是同一个人");
			}
		}
		map.put("procpersonId", list.get(0).getOmsId());

		//将集合中的多个证照注销信息合并
		StringBuffer cerInfo = new StringBuffer();
		if(list != null && list.size() > 0){
			for(OmsCerCancellateLicense omsCerCancellateLicense : list){
				cerInfo.append(Constants.CER_TYPE_NAME[omsCerCancellateLicense.getZjlx()] + ":" + omsCerCancellateLicense.getZjhm() + "、");
			}
		}
		//去掉结尾的“、”号
		String applyCerInfo = cerInfo.toString().substring(0, cerInfo.toString().length() - 1);

		//加入到注销证照申请表中
		OmsCerCancellateApply omsCerCancellateApply = new OmsCerCancellateApply();
		omsCerCancellateApply.setId(UUIDGenerator.getPrimaryKey());
		omsCerCancellateApply.setOmsId(list.get(0).getOmsId());
		omsCerCancellateApply.setApplyCerInfo(applyCerInfo);
		omsCerCancellateApply.setAppendPlace(list.get(0).getAppendPlace().equals("1") ? "国内" : "国外");
		omsCerCancellateApply.setZxyy(Constants.CANCELL_REASON_NAME[Integer.parseInt(list.get(0).getZxyy())]);
		omsCerCancellateApply.setCreateTime(new Date());
		omsCerCancellateApply.setCreateUser(UserInfoUtil.getUserInfo().getId());
		int count = omsCerCancellateApplyMapper.insert(omsCerCancellateApply);
		if(count < 1){
			throw new CustomMessageException("保存到注销证照申请表失败");
		}
		map.put("applyId", omsCerCancellateApply.getId());

		//将注销申请信息保存，修改状态
		if(list != null && list.size() > 0){
			for(OmsCerCancellateLicense omsCerCancellateLicense : list){
				omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[1]));        //证照申请注销状态（生成材料）
				omsCerCancellateLicense.setZxfs(String.valueOf(Constants.CANCELL_MODE_STATUS[0]));          //注销方式（自行注销）
				omsCerCancellateLicense.setId(UUIDGenerator.getPrimaryKey());
				omsCerCancellateLicense.setCancellateApplyId(omsCerCancellateApply.getId());
				omsCerCancellateLicense.setCreateTime(new Date());
				omsCerCancellateLicense.setCreateUser(UserInfoUtil.getUserInfo().getId());
				int count1 = omsCerCancellateLicenseMapper.insert(omsCerCancellateLicense);
				if(count1 < 1){
					throw new CustomMessageException("将信息保存到注销证照申请表中失败");
				}else {
					//将注销证照信息过程插入到证照注销记录表
					OmsCerCancellateRecords omsCerCancellateRecords = new OmsCerCancellateRecords();
					omsCerCancellateRecords.setId(UUIDGenerator.getPrimaryKey());
					omsCerCancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
					omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[1]));
					omsCerCancellateRecords.setSperator(UserInfoUtil.getUserInfo().getName());
					omsCerCancellateRecords.setSperatorTime(new Date());
					omsCerCancellateRecords.setCreateTime(new Date());
					omsCerCancellateRecords.setCreateUser(UserInfoUtil.getUserInfo().getId());
					int count2 = omsCerCancellateRecordsMapper.insert(omsCerCancellateRecords);
					if(count2 < 1){
						throw new CustomMessageException("将记录插入到注销记录表失败");
					}
				}
			}
		}else {
			throw new CustomMessageException("请选择相关的人员证照信息再进行下一步");
		}

		return map;
	}


	/**
	 * <b>功能描述: 查询注销证照申请列表</b>
	 * @Param: [omsCerCancellateLicense,page]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 13:37
	 */
	public Page<OmsCerCancellateLicense> getCancellateLicenseApply(Page<OmsCerCancellateLicense> page, OmsCerCancellateLicense omsCerCancellateLicense) {
		QueryWrapper<OmsCerCancellateLicense> queryWrapper = new QueryWrapper<OmsCerCancellateLicense>();
		queryWrapper.eq(omsCerCancellateLicense.getName() != null && omsCerCancellateLicense.getName() != "",
				"NAME",omsCerCancellateLicense.getName())
				.eq(omsCerCancellateLicense.getZjhm() != null && omsCerCancellateLicense.getZjhm() != "",
						"ZJHM",omsCerCancellateLicense.getZjhm())
				.eq(omsCerCancellateLicense.getZhzxzt() != null && omsCerCancellateLicense.getZhzxzt() != "",
						"ZHZXZT", omsCerCancellateLicense.getZhzxzt())
				.between(omsCerCancellateLicense.getApplyQueryStartTime() != null && omsCerCancellateLicense.getApplyQueryEndTime() != null,
						"CREATE_TIME", omsCerCancellateLicense.getApplyQueryStartTime(), omsCerCancellateLicense.getApplyQueryEndTime())
				.orderByDesc("CREATE_TIME");
		PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
		List<OmsCerCancellateLicense> list = omsCerCancellateLicenseMapper.selectList(queryWrapper);
		PageInfo<OmsCerCancellateLicense> pageInfo = new PageInfo<OmsCerCancellateLicense>(list);
		page.setTotal(pageInfo.getTotal());
		page.setPages(pageInfo.getPages());
		page.setRecords(pageInfo.getList());
		return page;
	}


	/**
	 * <b>功能描述: 删除注销证照申请</b>
	 * @Param: [id]
	 * @Return: void
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:04
	 */
	public void deleteCancellateLicenseApply(String id) {
		if(id != null && id != ""){
			int count = omsCerCancellateLicenseMapper.deleteById(id);
			if(count < 1){
				throw new CustomMessageException("删除失败");
			}
		}else {
			throw new CustomMessageException("参数错误");
		}
	}


	/**
	 * <b>功能描述: 撤销注销证照申请</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeCancellateLicenseApply(List<OmsCerCancellateLicense> list) {
		if(list != null && list.size() > 0){
			for(OmsCerCancellateLicense omsCerCancellateLicense : list){
				if(omsCerCancellateLicense.getId() != null && omsCerCancellateLicense.getId() != ""){
					omsCerCancellateLicense.setModifyTime(new Date());
					omsCerCancellateLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
					omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[9]));
					int count = omsCerCancellateLicenseMapper.updateById(omsCerCancellateLicense);
					if(count < 1){
						throw new CustomMessageException("撤销失败");
					}
				}else {
					//将注销证照信息过程插入到证照注销记录表
					OmsCerCancellateRecords omsCerCancellateRecords = new OmsCerCancellateRecords();
					omsCerCancellateRecords.setId(UUIDGenerator.getPrimaryKey());
					omsCerCancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
					omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[9]));     //撤销
					omsCerCancellateRecords.setSperator(UserInfoUtil.getUserInfo().getName());
					omsCerCancellateRecords.setSperatorTime(new Date());
					omsCerCancellateRecords.setCreateTime(new Date());
					omsCerCancellateRecords.setCreateUser(UserInfoUtil.getUserInfo().getId());
					int count1 = omsCerCancellateRecordsMapper.insert(omsCerCancellateRecords);
					if(count1 < 1){
						throw new CustomMessageException("将记录插入到注销记录表失败");
					}
				}
			}
		}else {
			throw new CustomMessageException("未选择要撤销的证照信息");
		}
	}


	/**
	 * <b>功能描述: 更改申请状态</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	public void updateCancellateLicenseApplyStatus(OmsCerCancellateLicense omsCerCancellateLicense) {
		if (omsCerCancellateLicense.getZhzxzt() != null && StringUtils.isBlank(omsCerCancellateLicense.getCancellateApplyId())){
			throw new CustomMessageException("参数错误");
		}

		omsCerCancellateLicense.setModifyTime(new Date());
		omsCerCancellateLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
		QueryWrapper<OmsCerCancellateLicense> queryWrapper = new QueryWrapper<OmsCerCancellateLicense>();
		queryWrapper.eq("CANCELLATE_APPLY_ID", omsCerCancellateLicense.getCancellateApplyId());
		int count = omsCerCancellateLicenseMapper.update(omsCerCancellateLicense, queryWrapper);
		if(count < 1){
			throw new CustomMessageException("更改状态失败");
		}
	}


	/**
	 * <b>功能描述: 查询证照注销申请状态</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	public List<SysDictItem> getCancellateLicenseApplyStatus() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dictCode","zhzxzt");
		List<SysDictItem> list = sysDictItemMapper.getCfCertificateSysDictItem(map);
		return list;
	}


	/**
	 * <b>功能描述: 查询证照注销原因</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	public List<SysDictItem> getCancellateLicenseApplyReason() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dictCode","zjzxyy");
		List<SysDictItem> list = sysDictItemMapper.getCfCertificateSysDictItem(map);
		return list;
	}


	/**
	 * <b>功能描述: 查询证照注销方式</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	public List<SysDictItem> getCancellateLicenseApplyWay() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dictCode","zjzxfs");
		List<SysDictItem> list = sysDictItemMapper.getCfCertificateSysDictItem(map);
		return list;
	}



	/**
	 * <b>功能描述: 查询审批记录</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: java.util.List<com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateRecords>
	 * @Author: luoshuai
	 * @Date: 2020/9/3 9:15
	 */
	public List<OmsCerCancellateRecords> getCerCancellateLicenseRecord(OmsCerCancellateLicense omsCerCancellateLicense) {
		QueryWrapper<OmsCerCancellateRecords> queryWrapper = new QueryWrapper<OmsCerCancellateRecords>();
		queryWrapper.eq("CANCELLATE_ID", omsCerCancellateLicense.getId());
		List<OmsCerCancellateRecords> list = omsCerCancellateRecordsMapper.selectList(queryWrapper);
		return list;
	}



}






