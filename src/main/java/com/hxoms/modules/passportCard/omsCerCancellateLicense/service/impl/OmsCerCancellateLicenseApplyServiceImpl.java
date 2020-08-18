package com.hxoms.modules.passportCard.omsCerCancellateLicense.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.entity.OmsCerCancellateLicense;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.mapper.OmsCerCancellateLicenseMapper;
import com.hxoms.modules.passportCard.omsCerCancellateLicense.service.OmsCerCancellateLicenseApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void saveCancellateLicenseChoose(List<OmsCerCancellateLicense> list) {
		if(list != null && list.size() > 0){
			for(OmsCerCancellateLicense omsCerCancellateLicense : list){
				omsCerCancellateLicense.setZhzxzt(String.valueOf(Constants.CANCELL_STATUS[1]));        //证照申请注销状态（生成材料）
				omsCerCancellateLicense.setZxfs(String.valueOf(Constants.CANCELL_MODE_STATUS[0]));          //注销方式（自行注销）
				omsCerCancellateLicense.setId(UUIDGenerator.getPrimaryKey());
				omsCerCancellateLicense.setCreateTime(new Date());
				omsCerCancellateLicense.setCreateUser(UserInfoUtil.getUserInfo().getId());
				int count = omsCerCancellateLicenseMapper.insert(omsCerCancellateLicense);
				if(count < 1){
					throw new CustomMessageException("将信息保存到注销证照申请表中失败");
				}
			}
		}else {
			throw new CustomMessageException("请选择相关的人员证照信息再进行下一步");
		}
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
				}
			}
		}else {
			throw new CustomMessageException("未选择要撤销的证照信息");
		}
	}


	/**
	 * <b>功能描述: 更改申请状态</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/5 15:09
	 */
	public void updateCancellateLicenseApplyStatus(List<OmsCerCancellateLicense> list) {
		for(OmsCerCancellateLicense omsCerCancellateLicense : list){
			omsCerCancellateLicense.setModifyTime(new Date());
			omsCerCancellateLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
			int count = omsCerCancellateLicenseMapper.updateById(omsCerCancellateLicense);
			if(count < 1){
				throw new CustomMessageException("更改状态失败");
			}
		}
	}


}






