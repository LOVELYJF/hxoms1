package com.hxoms.modules.passportCard.omsCerCancellateLicense.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.enums.*;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.mapper.OmsCerGetTaskMapper;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.OmsCerCounterNumber;
import com.hxoms.modules.passportCard.initialise.entity.enums.*;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.initialise.mapper.OmsCerConuterNumberMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateRecords;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.enums.ZxfsEnum;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper.OmsCerCancellateLicenseMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper.OmsCerCancellateRecordsMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.service.OmsCerCancellateLicenseAcceptanceService;
import org.apache.commons.lang3.StringUtils;
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
 * <b>功能描述: 注销证照受理业务层实现类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/5 16:24
 */
@Service
public class OmsCerCancellateLicenseAcceptanceServiceImpl implements OmsCerCancellateLicenseAcceptanceService {

	@Autowired
	private OmsCerCancellateLicenseMapper omsCerCancellateLicenseMapper;
	@Autowired
	private CfCertificateMapper cfCertificateMapper;
	@Autowired
	private OmsRegProcpersoninfoMapper omsRegProcpersoninfoMapper;
	@Autowired
	private OmsCerGetTaskMapper omsCerGetTaskMapper;
	@Autowired
	private OmsCerConuterNumberMapper omsCerConuterNumberMapper;
	@Autowired
	private OmsCerCancellateRecordsMapper omsCerCancellateRecordsMapper;
	/**
	 * <b>功能描述: 注销证照受理查询(按单位主键，姓名，证件号码，证件类型,申请时间，申请状态查询)
	 * 和处领导审批处的查询为同一接口</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 * @Author: luoshuai
	 * @Date: 2020/8/5 16:52
	 */
	public Page<Map<String, Object>> getCerCancellateLicenseAcceptance(Page<Map<String, Object>> page,List<String> idList,
	                                                                   OmsCerCancellateLicense omsCerCancellateLicense) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", omsCerCancellateLicense.getName());
		map.put("zjhm", omsCerCancellateLicense.getZjhm());
		map.put("zjlx", omsCerCancellateLicense.getZjlx());
		map.put("idList", idList);
		map.put("zhzxzt",omsCerCancellateLicense.getZhzxzt());
		map.put("applyQueryStartTime", omsCerCancellateLicense.getApplyQueryStartTime());
		map.put("applyQueryEndTime", omsCerCancellateLicense.getApplyQueryEndTime());
		PageHelper.startPage((int) page.getCurrent(),(int) page.getSize());
		List<Map<String,Object>> list = omsCerCancellateLicenseMapper.getCerCancellateLicenseAcceptance(map);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>();
		page.setRecords(list);
		page.setPages(pageInfo.getPages());
		page.setTotal(pageInfo.getTotal());
		return page;
	}

	/**
	 * <b>功能描述: 强制注销（监督处强制注销某个人的证照信息）进行下一步</b>
	 * @Param: [list]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	@Transactional(rollbackFor=Exception.class)
	public void getCerCancellateLicenseForce(List<OmsCerCancellateLicense> list) {
		if(!ListUtil.isEmpty(list)){
			for(OmsCerCancellateLicense omsCerCancellateLicense : list){

				omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[5]));        //证照申请注销状态（处领导审批）
				omsCerCancellateLicense.setZxfs(String.valueOf(Constants.CANCELL_MODE_STATUS[1]));          //注销方式（委托）
				omsCerCancellateLicense.setId(UUIDGenerator.getPrimaryKey());
				omsCerCancellateLicense.setCreateTime(new Date());
				omsCerCancellateLicense.setCreateUser(UserInfoUtil.getUserInfo().getId());
				int count = omsCerCancellateLicenseMapper.insert(omsCerCancellateLicense);
				if(count < 1){
					throw new CustomMessageException("强制注销插入数据失败");
				}else {
					//将注销证照信息过程插入到证照注销记录表
					OmsCerCancellateRecords omsCerCancellateRecords = new OmsCerCancellateRecords();
					omsCerCancellateRecords.setId(UUIDGenerator.getPrimaryKey());
					omsCerCancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
					omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[5]));
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
		}
	}




	/**
	 * <b>功能描述: 注销证照受理-修改</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateCerCancellateLicenseAcceptance(OmsCerCancellateLicense omsCerCancellateLicense) {
		if(StringUtils.isBlank(omsCerCancellateLicense.getId())){
			throw new CustomMessageException("参数错误");
		}

		omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[5]));        //证照申请注销状态（处领导审批）
		omsCerCancellateLicense.setZxfs(String.valueOf(Constants.CANCELL_MODE_STATUS[1]));          //注销方式（委托）
		omsCerCancellateLicense.setModifyTime(new Date());
		omsCerCancellateLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
		int count = omsCerCancellateLicenseMapper.updateById(omsCerCancellateLicense);
		if(count < 1){
			throw new CustomMessageException("修改失败");
		}else {
			//将注销证照信息过程插入到证照注销记录表
			OmsCerCancellateRecords omsCerCancellateRecords = new OmsCerCancellateRecords();
			omsCerCancellateRecords.setId(UUIDGenerator.getPrimaryKey());
			omsCerCancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
			omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[5]));
			omsCerCancellateRecords.setSperator(UserInfoUtil.getUserInfo().getName());
			omsCerCancellateRecords.setSperatorTime(new Date());
			omsCerCancellateRecords.setCreateTime(new Date());
			omsCerCancellateRecords.setCreateUser(UserInfoUtil.getUserInfo().getId());
			int count1 = omsCerCancellateRecordsMapper.insert(omsCerCancellateRecords);
			if(count1 < 1){
				throw new CustomMessageException("修改到注销记录表失败");
			}
		}
	}


	/**
	 * <b>功能描述: 注销证照受理申请--下一步
	 *      在此处进行判断是自行注销且材料通过的不用处领导审批，直接将证照状态改为注销，申请状态改为已办结
	 * </b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/7 16:43
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateCerCancellateLicenseAcceptanceNext(OmsCerCancellateLicense omsCerCancellateLicense) {
		if(StringUtils.isBlank(omsCerCancellateLicense.getZxfs()) ||
				StringUtils.isBlank(omsCerCancellateLicense.getId()) ||
				StringUtils.isBlank(omsCerCancellateLicense.getZjhm())){
			throw new CustomMessageException("参数错误");
		}
		omsCerCancellateLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
		omsCerCancellateLicense.setModifyTime(new Date());
		//进行判断注销方式
		if(omsCerCancellateLicense.getZxfs().equals(ZxfsEnum.CELF_CANCELLATE.getCode())){      //自行注销
			//状态置为已办结
			omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[11]));
			int count = omsCerCancellateLicenseMapper.updateById(omsCerCancellateLicense);
			if(count < 1){
				throw new CustomMessageException("修改注销状态失败");
			}else {
				//将注销证照信息过程插入到证照注销记录表
				OmsCerCancellateRecords omsCerCancellateRecords = new OmsCerCancellateRecords();
				omsCerCancellateRecords.setId(UUIDGenerator.getPrimaryKey());
				omsCerCancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
				omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[11]));     //撤销
				omsCerCancellateRecords.setSperator(UserInfoUtil.getUserInfo().getName());
				omsCerCancellateRecords.setSperatorTime(new Date());
				omsCerCancellateRecords.setCreateTime(new Date());
				omsCerCancellateRecords.setCreateUser(UserInfoUtil.getUserInfo().getId());
				int count2 = omsCerCancellateRecordsMapper.insert(omsCerCancellateRecords);
				if(count2 < 1){
					throw new CustomMessageException("将记录插入到注销记录表失败");
				}

				//根据证照号码查询证照是否存在柜台编号
				QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<CfCertificate>();
				queryWrapper.eq(!StringUtils.isBlank(omsCerCancellateLicense.getZjhm()),
						"ZJHM", omsCerCancellateLicense.getZjhm());
				CfCertificate cfCertificate = cfCertificateMapper.selectOne(queryWrapper);
				if(cfCertificate.getCounterNum() != null){
					//将证照号码插入到证照号码废弃表中
					QueryWrapper<OmsCerCounterNumber> wrapper = new QueryWrapper<OmsCerCounterNumber>();
					wrapper.eq("COUNTER_NUM", cfCertificate.getCounterNum());
					OmsCerCounterNumber omsCerCounterNumber = new OmsCerCounterNumber();
					omsCerCounterNumber.setStatus(UseStatusEnum.NOT_USE.getCode());
					omsCerCounterNumber.setIsLock(LockEnum.NOT_LOCK.getCode());
					int count1 = omsCerConuterNumberMapper.update(omsCerCounterNumber,wrapper);
					if(count1 < 1){
						throw new CustomMessageException("将证照柜台号码插入到证照号废弃表失败");
					}
				}
			}
		}else if(omsCerCancellateLicense.getZxfs().equals(ZxfsEnum.ENTRUST_CANCELLATE.getCode())){      //委托注销
			//状态置为处领导审批
			omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[5]));
			int count = omsCerCancellateLicenseMapper.updateById(omsCerCancellateLicense);
			if(count < 1){
				throw new CustomMessageException("修改注销状态失败");
			}else {
				//将注销证照信息过程插入到证照注销记录表
				OmsCerCancellateRecords omsCerCancellateRecords = new OmsCerCancellateRecords();
				omsCerCancellateRecords.setId(UUIDGenerator.getPrimaryKey());
				omsCerCancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
				omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[5]));     //撤销
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
	}


	/**
	 * <b>功能描述: 处领导审批进行下一步</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/9/5 14:50
	 */
	public Map<String, Object> getCerCancellateLicenseBillApproval(List<OmsCerCancellateLicense> list) {
		if(list == null || list.size() < 1){
			throw new CustomMessageException("未选择要打印呈批单的证照信息");
		}

		//判断选择的是否是同一个人
		String omsId = list.get(0).getOmsId();
		for(OmsCerCancellateLicense omsCerCancellateLicense : list){
			if(!omsCerCancellateLicense.getOmsId().equals(omsId)){
				throw new CustomMessageException("选中的多个人员不是同一个人");
			}
			if(!omsCerCancellateLicense.getZhzxzt().equals(String.valueOf(Constants.CANCELL_STATUS[6]))){
				throw new CustomMessageException("选择的证照还未通过处领导审批");
			}
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableCode", "oms_cer_cancellate_approval");
		map.put("procpersonId", list.get(0).getOmsId());
		map.put("applyId", list.get(0).getCancellateApplyId());
		return map;
	}


	/**
	 * <b>功能描述: 处领导审批(可以批量审批)</b>
	 * @Param: [list,omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 16:43
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateCerCancellateLicenseApproval(List<String> list, OmsCerCancellateLicense omsCerCancellateLicense) {
		if(list == null || list.size() < 1){
			throw new CustomMessageException("未选择要审批的人员");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(!StringUtils.isBlank(omsCerCancellateLicense.getCldyj())){
			if(omsCerCancellateLicense.getCldyj().equals(IsAllowEnum.ALLOW.getCode())){
				//通过
				map.put("zhzxzt", String.valueOf(Constants.CANCELL_STATUS[6]));   //状态置为部领导审批
			}else if(omsCerCancellateLicense.getCldyj().equals(IsAllowEnum.NOT_ALLOW.getCode())){
				//不通过
				map.put("zhzxzt", String.valueOf(Constants.CANCELL_STATUS[8]));   //状态置为拒绝
			}

			if(!StringUtils.isBlank(omsCerCancellateLicense.getCldyjly())){
				map.put("cldyjly", omsCerCancellateLicense.getCldyjly());
			}
			map.put("list", list);
			map.put("modifyUser",UserInfoUtil.getUserInfo().getId());
			map.put("modifyTime", new Date());
			int count = omsCerCancellateLicenseMapper.updateCerCancellateLicenseApproval(map);
			if(count < 1){
				throw new CustomMessageException("审批操作失败");
			}else {
				//将注销证照信息过程插入到证照注销记录表
				for(String id : list){
					OmsCerCancellateRecords omsCerCancellateRecords = new OmsCerCancellateRecords();
					omsCerCancellateRecords.setId(UUIDGenerator.getPrimaryKey());
					omsCerCancellateRecords.setCancellateId(id);
					if(omsCerCancellateLicense.getCldyj().equals(IsAllowEnum.ALLOW.getCode())){
						omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[6])); //状态置为部领导审批
					}else if (omsCerCancellateLicense.getCldyj().equals(IsAllowEnum.NOT_ALLOW.getCode())){
						omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[8]));   //状态置为拒绝
					}
					omsCerCancellateRecords.setResult(omsCerCancellateLicense.getCldyjly());
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
		}
	}

	/**
	 * <b>功能描述: 部领导审批</b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 16:48
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String,Object> updateCerCancellateLicenseApprovalMinister(OmsCerCancellateLicense omsCerCancellateLicense) {
		if(StringUtils.isBlank(omsCerCancellateLicense.getId()) ||
				StringUtils.isBlank(omsCerCancellateLicense.getOmsId()) ||
				StringUtils.isBlank(omsCerCancellateLicense.getCancellateApplyId())){
			throw new CustomMessageException("参数错误");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(omsCerCancellateLicense.getBldyj().equals(IsAllowEnum.ALLOW.getCode())){
			//通过
			omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[7]));  //生成注销函
			map.put("tableCode", "oms_cer_cancellate_letter");
			map.put("procpersonId",omsCerCancellateLicense.getOmsId());
			map.put("applyId", omsCerCancellateLicense.getCancellateApplyId());
		}else if(omsCerCancellateLicense.getBldyj().equals(IsAllowEnum.NOT_ALLOW.getCode())){
			//不通过
			omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[8]));  //拒绝
		}

		omsCerCancellateLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
		omsCerCancellateLicense.setModifyTime(new Date());
		int count = omsCerCancellateLicenseMapper.updateById(omsCerCancellateLicense);
		if(count < 1){
			throw new CustomMessageException("审核操作失败");
		}else {
			//将注销证照信息过程插入到证照注销记录表
			OmsCerCancellateRecords omsCerCancellateRecords = new OmsCerCancellateRecords();
			omsCerCancellateRecords.setId(UUIDGenerator.getPrimaryKey());
			omsCerCancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
			if(omsCerCancellateLicense.getBldyj().equals(IsAllowEnum.ALLOW.getCode())){
				omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[7]));   //生成注销函
			}else if (omsCerCancellateLicense.getBldyj().equals(IsAllowEnum.NOT_ALLOW.getCode())){
				omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[8]));   //拒绝
			}
			omsCerCancellateRecords.setResult(omsCerCancellateLicense.getBldyjly());
			omsCerCancellateRecords.setSperator(UserInfoUtil.getUserInfo().getName());
			omsCerCancellateRecords.setSperatorTime(new Date());
			omsCerCancellateRecords.setCreateTime(new Date());
			omsCerCancellateRecords.setCreateUser(UserInfoUtil.getUserInfo().getId());
			int count2 = omsCerCancellateRecordsMapper.insert(omsCerCancellateRecords);
			if(count2 < 1){
				throw new CustomMessageException("将记录插入到注销记录表失败");
			}
		}
		return map;
	}



	/**
	 * <b>功能描述: 完成注销(公安厅意见)
	 *     分两个按钮，第一个通过后生成证照领取任务，
	 *     第二个领取任务完成后再次完成注销，状态改为已办结
	 * </b>
	 * @Param: [omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 11:48
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateCerCancellateLicenseApprovalComplete(OmsCerCancellateLicense omsCerCancellateLicense) {
		if(StringUtils.isBlank(omsCerCancellateLicense.getId())){
			throw new CustomMessageException("参数错误");
		}
		if(omsCerCancellateLicense.getGatshyj().equals(IsAllowEnum.ALLOW.getCode())){
			//公安厅通过
			OmsCerCancellateLicense cancellateLicense = new OmsCerCancellateLicense();
			cancellateLicense.setId(omsCerCancellateLicense.getId());
			cancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[10]));      //完成注销状态之后，状态改为完成注销
			cancellateLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
			cancellateLicense.setModifyTime(new Date());
			cancellateLicense.setGatshyj(omsCerCancellateLicense.getGatshyj());
			cancellateLicense.setGatyjly(omsCerCancellateLicense.getGatyjly());
			int count = omsCerCancellateLicenseMapper.updateById(cancellateLicense);
			if(count < 1){
				throw new CustomMessageException("公安厅通过，更改状态失败");
			}else {
				//将注销证照信息过程插入到证照注销记录表
				OmsCerCancellateRecords omsCerCancellateRecords = new OmsCerCancellateRecords();
				omsCerCancellateRecords.setId(UUIDGenerator.getPrimaryKey());
				omsCerCancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
				omsCerCancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[10]));     //撤销
				omsCerCancellateRecords.setResult(omsCerCancellateLicense.getGatyjly());
				omsCerCancellateRecords.setSperator(UserInfoUtil.getUserInfo().getName());
				omsCerCancellateRecords.setSperatorTime(new Date());
				omsCerCancellateRecords.setCreateTime(new Date());
				omsCerCancellateRecords.setCreateUser(UserInfoUtil.getUserInfo().getId());
				int count3 = omsCerCancellateRecordsMapper.insert(omsCerCancellateRecords);
				if(count3 < 1){
					throw new CustomMessageException("将记录插入到注销记录表失败");
				}

				//判断当前证照的保管状态
				if (omsCerCancellateLicense.getSaveStatus().equals(String.valueOf(Constants.CER_SAVE_STATUS[0]))){
					//保管状态为正常保管的，生成证照领取任务
					OmsCerGetTask omsCerGetTask = new OmsCerGetTask();
					omsCerGetTask.setId(UUIDGenerator.getPrimaryKey());
					omsCerGetTask.setBusiId(omsCerCancellateLicense.getId());
					omsCerGetTask.setName(omsCerCancellateLicense.getName());
					omsCerGetTask.setZjlx(omsCerCancellateLicense.getZjlx());
					omsCerGetTask.setDataSource(ReceiveSourceEnum.SOURCE_5.getCode());
					omsCerGetTask.setGetPeople(UserInfoUtil.getUserInfo().getId());
					omsCerGetTask.setCreateTime(new Date());
					omsCerGetTask.setCreator(UserInfoUtil.getUserInfo().getId());
					omsCerGetTask.setOmsId(omsCerCancellateLicense.getOmsId());
					omsCerGetTask.setGetStatus(GetStatusEnum.STATUS_ENUM_0.getCode());              //未领取
					omsCerGetTask.setHappenDate(omsCerCancellateLicense.getCreateTime());           //业务发生时间

					//根据证件号码查询证件信息(查ID)
					String zjhm = omsCerCancellateLicense.getZjhm();
					QueryWrapper<CfCertificate> queryWrapper1 = new QueryWrapper<CfCertificate>();
					queryWrapper1.eq(!StringUtils.isBlank(zjhm), "ZJHM", zjhm);
					CfCertificate cfCertificate1 = cfCertificateMapper.selectOne(queryWrapper1);
					omsCerGetTask.setCerId(cfCertificate1.getId());
					omsCerGetTask.setZjhm(omsCerCancellateLicense.getZjhm());

					//根据备案主键在登记备案表中查询证照领取任务表中需要的信息
					OmsRegProcpersoninfo omsRegProcpersoninfo = omsRegProcpersoninfoMapper.selectById(omsCerCancellateLicense.getOmsId());
					omsCerGetTask.setRfB0000(omsRegProcpersoninfo.getRfB0000());

					int count2 = omsCerGetTaskMapper.insert(omsCerGetTask);
					if(count2 < 1){
						throw new CustomMessageException("插入数据到证照领取任务表失败");
					}else {
						//在证照信息表中将证照状态变为待领取，注销证照申请表也置为待领取
						OmsCerCancellateLicense omsCerCancellateLicense1 = new OmsCerCancellateLicense();
						omsCerCancellateLicense1.setId(omsCerCancellateLicense.getId());
						omsCerCancellateLicense1.setCardStatus(String.valueOf(Constants.CER_STATUS[7]));
						omsCerCancellateLicense1.setModifyTime(new Date());
						omsCerCancellateLicense1.setModifyUser(UserInfoUtil.getUserInfo().getId());
						int count4 = omsCerCancellateLicenseMapper.updateById(omsCerCancellateLicense1);
						if(count4 < 1){
							throw new CustomMessageException("更改证照状态失败");
						}

						CfCertificate cfCertificate = new CfCertificate();
						cfCertificate.setCardStatus(String.valueOf(Constants.CER_STATUS[7]));
						cfCertificate.setUpdateTime(new Date());
						cfCertificate.setUpdater(UserInfoUtil.getUserInfo().getId());
						QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<CfCertificate>();
						queryWrapper.eq("ZJHM", omsCerCancellateLicense.getZjhm());
						int count5 = cfCertificateMapper.update(cfCertificate, queryWrapper);
						if(count5 < 1){
							throw new CustomMessageException("更改证照状态失败");
						}
					}
				}else if(omsCerCancellateLicense.getSaveStatus().equals(String.valueOf(Constants.CER_SAVE_STATUS[1]))){
					//保管状态为已取出的，将状态置为注销
					OmsCerCancellateLicense omsCerCancellateLicense1 = new OmsCerCancellateLicense();
					omsCerCancellateLicense1.setId(omsCerCancellateLicense.getId());
					//证照状态改成过期
					omsCerCancellateLicense1.setCardStatus(String.valueOf(Constants.CER_STATUS[2]));
					//公安厅通过，且状态已取出，将证照注销状态改成已办结
					omsCerCancellateLicense1.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[11]));
					omsCerCancellateLicense1.setModifyUser(UserInfoUtil.getUserInfo().getId());
					omsCerCancellateLicense1.setModifyTime(new Date());
					int count6 = omsCerCancellateLicenseMapper.updateById(omsCerCancellateLicense1);
					if(count6 < 1){
						throw new CustomMessageException("更改证照状态失败");
					}else {
						//将注销证照信息过程插入到证照注销记录表
						OmsCerCancellateRecords cancellateRecords = new OmsCerCancellateRecords();
						cancellateRecords.setId(UUIDGenerator.getPrimaryKey());
						cancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
						cancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[11]));     //撤销
						cancellateRecords.setResult(omsCerCancellateLicense.getGatyjly());
						cancellateRecords.setSperator(UserInfoUtil.getUserInfo().getName());
						cancellateRecords.setSperatorTime(new Date());
						cancellateRecords.setCreateTime(new Date());
						cancellateRecords.setCreateUser(UserInfoUtil.getUserInfo().getId());
						int count2 = omsCerCancellateRecordsMapper.insert(cancellateRecords);
						if(count2 < 1){
							throw new CustomMessageException("将记录插入到注销记录表失败");
						}
					}

					CfCertificate cfCertificate = new CfCertificate();
					cfCertificate.setCardStatus(String.valueOf(Constants.CER_STATUS[2]));       //注销
					QueryWrapper<CfCertificate> queryWrapper = new QueryWrapper<CfCertificate>();
					queryWrapper.eq("ZJHM", omsCerCancellateLicense.getZjhm());
					int count4 = cfCertificateMapper.update(cfCertificate, queryWrapper);
					if(count4 < 1){
						throw new CustomMessageException("更改证照状态失败");
					}

					//将证照的柜台保管号插入到证照号废弃表中，供重复使用。
					//根据证照号码查询证照是否存在柜台编号
					QueryWrapper<CfCertificate> wrapper = new QueryWrapper<CfCertificate>();
					wrapper.eq(!StringUtils.isBlank(omsCerCancellateLicense.getZjhm()),
							"ZJHM", omsCerCancellateLicense.getZjhm());
					CfCertificate cfCertificate1 = cfCertificateMapper.selectOne(queryWrapper);
					if(cfCertificate1.getCounterNum() != null){
						//将证照号码插入到证照号码废弃表中
						QueryWrapper<OmsCerCounterNumber> wrapper1 = new QueryWrapper<OmsCerCounterNumber>();
						wrapper1.eq("COUNTER_NUM", cfCertificate.getCounterNum());
						OmsCerCounterNumber omsCerCounterNumber = new OmsCerCounterNumber();
						omsCerCounterNumber.setStatus(UseStatusEnum.NOT_USE.getCode());
						omsCerCounterNumber.setIsLock(LockEnum.NOT_LOCK.getCode());
						int count1 = omsCerConuterNumberMapper.update(omsCerCounterNumber,wrapper1);
						if(count1 < 1){
							throw new CustomMessageException("将证照柜台号码插入到证照号废弃表失败");
						}
					}
				}
			}
		}else if(omsCerCancellateLicense.getGatshyj().equals(IsAllowEnum.NOT_ALLOW.getCode())){
			//公安厅不通过
			omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[8]));
			int count = omsCerCancellateLicenseMapper.updateById(omsCerCancellateLicense);
			if(count < 1){
				throw new CustomMessageException("公安厅不通过，更改状态失败");
			}else {
				//将注销证照信息过程插入到证照注销记录表
				OmsCerCancellateRecords cancellateRecords = new OmsCerCancellateRecords();
				cancellateRecords.setId(UUIDGenerator.getPrimaryKey());
				cancellateRecords.setCancellateId(omsCerCancellateLicense.getId());
				cancellateRecords.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[8]));     //拒绝
				cancellateRecords.setResult(omsCerCancellateLicense.getGatyjly());
				cancellateRecords.setSperator(UserInfoUtil.getUserInfo().getName());
				cancellateRecords.setSperatorTime(new Date());
				cancellateRecords.setCreateTime(new Date());
				cancellateRecords.setCreateUser(UserInfoUtil.getUserInfo().getId());
				int count2 = omsCerCancellateRecordsMapper.insert(cancellateRecords);
				if(count2 < 1){
					throw new CustomMessageException("将记录插入到注销记录表失败");
				}
			}
		}
	}


	/**
	 * <b>功能描述: 受理审批导出</b>
	 * @Param: [idList,omsCerCancellateLicense]
	 * @Return: org.apache.ibatis.annotations.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/10 11:48
	 */
	public void getCerCancellateLicenseApprovalOut(List<String> idList, OmsCerCancellateLicense omsCerCancellateLicense,
													HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", omsCerCancellateLicense.getName());
		map.put("zjhm", omsCerCancellateLicense.getZjhm());
		map.put("zjlx", omsCerCancellateLicense.getZjlx());
		map.put("idList", idList);
		List<Map<String,Object>> list = omsCerCancellateLicenseMapper.getCerCancellateLicenseAcceptance(map);

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
			HSSFSheet sheet=wb.createSheet("注销证照申请审批记录");
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
			cell.setCellValue("注销证照申请审批记录");

			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0,1,0,23));
			//在sheet里创建第二行
			HSSFRow row2=sheet.createRow(2);
			//创建单元格并设置单元格内容
			row2.createCell(0).setCellValue("序号");
			row2.createCell(1).setCellValue("姓名");
			row2.createCell(2).setCellValue("性别");
			row2.createCell(3).setCellValue("单位");
			row2.createCell(4).setCellValue("任职状态");
			row2.createCell(5).setCellValue("职务");
			row2.createCell(6).setCellValue("审批状态");
			row2.createCell(7).setCellValue("注销方式");
			row2.createCell(8).setCellValue("注销原因");
			row2.createCell(9).setCellValue("发生地");
			row2.createCell(10).setCellValue("注销说明");
			row2.createCell(11).setCellValue("证照类型");
			row2.createCell(12).setCellValue("证件号码");
			row2.createCell(13).setCellValue("有效期至");
			row2.createCell(14).setCellValue("证照状态");
			row2.createCell(15).setCellValue("保管状态");
			row2.createCell(16).setCellValue("保管方式");
			row2.createCell(17).setCellValue("机柜");
			row2.createCell(18).setCellValue("位置");
			row2.createCell(19).setCellValue("柜台编号");
			row2.createCell(20).setCellValue("出生日期");
			row2.createCell(21).setCellValue("签发单位");
			row2.createCell(22).setCellValue("签发日期");
			row2.createCell(23).setCellValue("出生地");

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
				row.createCell(1).setCellValue((String) list.get(i).get("name"));
				row.createCell(2).setCellValue(String.valueOf(list.get(i).get("sex")).equals(SexEnum.MALE.getCode()) ? "男" : "女");
				row.createCell(3).setCellValue((String) list.get(i).get("workUnit"));
				row.createCell(4).setCellValue(Constants.INCUMBENCY_STATUS_NAME[Integer.parseInt((String)list.get(i).get("incumbencyStatus")) - 1]);
				row.createCell(5).setCellValue((String) list.get(i).get("post"));
				row.createCell(6).setCellValue(Constants.CANCELL_NAME[Integer.parseInt((String)list.get(i).get("zhzxzt"))]);
				row.createCell(7).setCellValue(((String) list.get(i).get("zxfs")).equals(ZxfsEnum.CELF_CANCELLATE.getCode()) ? "自行注销" : "委托");
				row.createCell(8).setCellValue(Constants.CANCELL_REASON_NAME[Integer.parseInt((String)list.get(i).get("zxyy")) - 1]);
				row.createCell(9).setCellValue(((String) list.get(i).get("appendPlace")).equals(IsDomesticEnum.DOMESTIC_ENUM.getCode()) ? "国内" : "国外");
				row.createCell(10).setCellValue((String) list.get(i).get("zxsm"));
				row.createCell(11).setCellValue(CerTypeUtil.getCnTypeLicence((Integer) list.get(i).get("zjlx")));
				row.createCell(12).setCellValue((String) list.get(i).get("zjhm"));
				row.createCell(13).setCellValue(UtilDateTime.formatCNDate((Date)list.get(i).get("yxqz")));
				row.createCell(14).setCellValue(Constants.CER_NAME[Integer.parseInt((String)list.get(i).get("cardStatus"))]);
				row.createCell(15).setCellValue(Constants.CER_SAVE_NAME[Integer.parseInt((String)list.get(i).get("saveStatus"))]);
				row.createCell(16).setCellValue(((String) list.get(i).get("surelyWay")).equals(SurelyWayEnum.CABINET.getCode()) ? "证照机" : "柜台");
				row.createCell(17).setCellValue((String) list.get(i).get("cabinetNum"));
				row.createCell(18).setCellValue((String) list.get(i).get("place"));
				row.createCell(19).setCellValue(String.valueOf(list.get(i).get("counterNum")));
				row.createCell(20).setCellValue(UtilDateTime.formatCNDate((Date) list.get(i).get("csrq")));
				row.createCell(21).setCellValue((String) list.get(i).get("qfjg"));
				row.createCell(22).setCellValue(UtilDateTime.formatCNDate((Date)list.get(i).get("qfrq")));
				row.createCell(23).setCellValue((String) list.get(i).get("csdd"));

				//设置单元格字体大小
				for(int j = 0;j < 24;j++){
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
