package com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.CompressUtil;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.mapper.OmsCerGetTaskMapper;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.mapper.CfCertificateMapper;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.entity.OmsCerApplyLendingLicense;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.mapper.OmsCerApplyLendingLicenseMapper;
import com.hxoms.modules.passportCard.omsCerApplyLendingLicense.service.OmsCerApplyLendingLicenseApprovalService;
import com.hxoms.modules.passportCard.omsCerTransferOutLicense.entity.OmsCerTransferOutLicense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * <b>功能描述: 查询年份对应的批次号结构树</b>
	 * @Param: []
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/21 15:07
	 */
	public List<OmsCerApplyLendingLicense> getBatchByYear() {
		List<OmsCerApplyLendingLicense> list = omsCerApplyLendingLicenseMapper.selectYearList();      //查询批次号的年份集合
		if(list != null && list.size() > 0){
			for(OmsCerApplyLendingLicense omsCerApplyLendingLicense : list){
				String year = omsCerApplyLendingLicense.getYear();
				List<OmsCerApplyLendingLicense> list1 = omsCerApplyLendingLicenseMapper.getBatchByYear(year);   //根据年份查询对应的批次号
				if(list1 != null && list1.size() > 0){
					omsCerApplyLendingLicense.setList(list1);
				}
			}
		}

		return list;
	}



	/**
	 * <b>功能描述: 查询借出证照申请记录</b>
	 * @Param: [page,documentNum]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	public Page<Map<String, Object>> getApplyLendingLicenseApprovalRecord(Page<Map<String, Object>> page,String documentNum) {
		PageHelper.startPage((int)page.getCurrent(), (int) page.getSize());
		List<Map<String, Object>> list = omsCerApplyLendingLicenseMapper.getApplyLendingLicenseApprovalRecord(documentNum);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		page.setPages(pageInfo.getPages());
		page.setRecords(list);
		page.setTotal(pageInfo.getTotal());

		return page;
	}



	/**
	 * <b>功能描述: 录入审批结果</b>
	 * @Param: [list]
	 * @Return: com.hxoms.common.utils.Result
	 * @Author: luoshuai
	 * @Date: 2020/8/11 16:29
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateApplyLendingLicenseApprovalResult(List<OmsCerApplyLendingLicense> list) {
		if(list != null && list.size() > 0){
			for(OmsCerApplyLendingLicense omsCerApplyLendingLicense : list){
				omsCerApplyLendingLicense.setModyfyTime(new Date());
				omsCerApplyLendingLicense.setModifyUser(UserInfoUtil.getUserInfo().getId());
				omsCerApplyLendingLicense.setSqjczt("2");       //已审批
				omsCerApplyLendingLicense.setList(null);
				int count = omsCerApplyLendingLicenseMapper.updateById(omsCerApplyLendingLicense);
				if(count < 1){
					throw new CustomMessageException("录入审批失败");
				}else {
					if(omsCerApplyLendingLicense.getBldyj().equals("1")){
						//同意之后将证照信息表状态改为待取出
						CfCertificate cfCertificate = new CfCertificate();
						cfCertificate.setCardStatus(String.valueOf(Constants.CER_STATUS[7]));
						QueryWrapper<CfCertificate> wrapper = new QueryWrapper<CfCertificate>();
						wrapper.eq(omsCerApplyLendingLicense.getZjhm() != null && omsCerApplyLendingLicense.getZjhm() != "",
								"ZJHM",omsCerApplyLendingLicense.getZjhm());
						int count1 = cfCertificateMapper.update(cfCertificate,wrapper);
						if(count1 < 1){
							throw new CustomMessageException("修改证照为待取出状态失败");
						}else {
							//生成证照领取任务,向证照领取任务表中插入数据

							//根据借出证照的id查询借出证照表中的证照信息
							OmsCerApplyLendingLicense omsCerApplyLendingLicense1 = omsCerApplyLendingLicenseMapper.selectOmsCerApplyLendingLicenseInfo(omsCerApplyLendingLicense.getId());
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
								omsCerGetTask.setHappenDate(omsCerApplyLendingLicense1.getCreateTime());

								//根据证件号码查询证件信息(查ID)
								QueryWrapper<CfCertificate> queryWrapper1 = new QueryWrapper<CfCertificate>();
								queryWrapper1.eq( "ZJHM", omsCerApplyLendingLicense.getZjhm());
								CfCertificate cfCertificate1 = cfCertificateMapper.selectOne(queryWrapper1);
								omsCerGetTask.setCerId(cfCertificate1.getId());
								omsCerGetTask.setZjhm(omsCerApplyLendingLicense.getZjhm());

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
			throw new CustomMessageException("请选择要审批的申请信息");
		}
	}
}
