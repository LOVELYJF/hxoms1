package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.mapper.OmsCerGetTaskMapper;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity.OmsCerApplyLendingLicense;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.mapper.OmsCerApplyLendingLicenseMapper;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service.OmsCerApplyLendingLicenseApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <b>功能描述: 借出证照申请审批业务层接口实现类</b>
 * @Param:
 * @Return:
 * @Author: luoshuai
 * @Date: 2020/8/11 16:09
 */
@Service
public class OmsCerApplyLendingLicenseApprovalServiceImpl implements OmsCerApplyLendingLicenseApprovalService {


	@Autowired
	private OmsCerApplyLendingLicenseMapper omsCerApplyLendingLicenseMapper;
	@Autowired
	private CfCertificateMapper cfCertificateMapper;
	@Autowired
	private OmsRegProcpersoninfoMapper omsRegProcpersoninfoMapper;
	@Autowired
	private OmsCerGetTaskMapper omsCerGetTaskMapper;

	/**
	 * <b>功能描述: 查询借出证照申请记录</b>
	 * @Param: [page]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	public Page<Map<String, Object>> getApplyLendingLicenseApprovalRecord(Page<Map<String, Object>> page) {
		PageHelper.startPage((int)page.getCurrent(), (int) page.getSize());
		List<Map<String, Object>> list = omsCerApplyLendingLicenseMapper.getApplyLendingLicenseApprovalRecord();
		PageInfo pageInfo = new PageInfo(list);
		page.setPages(pageInfo.getPages());
		page.setRecords(list);
		page.setTotal(pageInfo.getTotal());

		return page;
	}



	/**
	 * <b>功能描述: 录入审批结果</b>
	 * @Param: [list,omsCerApplyLendingLicense]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	public void updateApplyLendingLicenseApprovalResult(List<String> list, OmsCerApplyLendingLicense omsCerApplyLendingLicense) {
		if(list != null && list.size() > 0){
			QueryWrapper<OmsCerApplyLendingLicense> queryWrapper = new QueryWrapper<OmsCerApplyLendingLicense>();
			queryWrapper.in( "ID", list);
			int count = omsCerApplyLendingLicenseMapper.update(omsCerApplyLendingLicense,queryWrapper);
			if(count < 1){
				throw new CustomMessageException("录入失败");
			}else {
				if(omsCerApplyLendingLicense.getBldyj().equals("1")){
					//同意之后将状态改为待取出
					CfCertificate cfCertificate = new CfCertificate();
					cfCertificate.setCardStatus(String.valueOf(Constants.CER_STATUS[7]));
					QueryWrapper<CfCertificate> wrapper = new QueryWrapper<CfCertificate>();
					wrapper.in(list != null && list.size() > 0, "ID", list);
					int count1 = cfCertificateMapper.update(cfCertificate,wrapper);
					if(count1 < 1){
						throw new CustomMessageException("修改证照为待取出状态失败");
					}else {
						//生成证照领取任务,向证照领取任务表中插入数据
						for(String id : list){
							//根据借出证照的id查询借出证照表中的证照信息
							OmsCerApplyLendingLicense omsCerApplyLendingLicense1 = omsCerApplyLendingLicenseMapper.selectById(id);
							if(omsCerApplyLendingLicense1 != null){
								OmsCerGetTask omsCerGetTask = new OmsCerGetTask();          //创建证照领取对象
								omsCerGetTask.setId(UUIDGenerator.getPrimaryKey());
								omsCerGetTask.setBusiId(omsCerApplyLendingLicense1.getId());
								omsCerGetTask.setName(omsCerApplyLendingLicense1.getName());
								omsCerGetTask.setZjlx(Integer.parseInt(omsCerApplyLendingLicense1.getZjlx()));
								omsCerGetTask.setDataSource("2");
								omsCerGetTask.setGetPeople(UserInfoUtil.getUserInfo().getName());
								omsCerGetTask.setCreateTime(new Date());
								omsCerGetTask.setCreator(UserInfoUtil.getUserInfo().getId());
								omsCerGetTask.setOmsId(omsCerApplyLendingLicense1.getOmsId());
								omsCerGetTask.setGetStatus("0");                //未领取

								//根据证件号码查询证件信息(查ID)
								String zjhm = omsCerApplyLendingLicense1.getZjhm();
								QueryWrapper<CfCertificate> queryWrapper1 = new QueryWrapper<CfCertificate>();
								queryWrapper1.eq(zjhm != null && zjhm != "", "ZJHM", zjhm);
								CfCertificate cfCertificate1 = cfCertificateMapper.selectOne(queryWrapper1);
								omsCerGetTask.setCerId(cfCertificate1.getId());
								omsCerGetTask.setZjhm(omsCerApplyLendingLicense1.getZjhm());

								//根据备案主键在登记备案表中查询证照领取任务表中需要的信息
								OmsRegProcpersoninfo omsRegProcpersoninfo = omsRegProcpersoninfoMapper.selectById(omsCerApplyLendingLicense.getOmsId());
								omsCerGetTask.setRfB0000(omsRegProcpersoninfo.getRfB0000());

								int count2 = omsCerGetTaskMapper.insert(omsCerGetTask);
								if(count2 < 1){
									throw new CustomMessageException("插入数据到证照领取任务表失败");
								}
							}else {
								throw new CustomMessageException("没有查询到相关的证照领取借出信息");
							}
						}
					}
				}
			}
		}else {
			throw new CustomMessageException("请选择要审批的证照信息");
		}
	}
}
