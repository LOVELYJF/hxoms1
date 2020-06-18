package com.hxoms.modules.keySupervision.familyMember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.common.utils.UtilDateTime;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.modules.keySupervision.familyMember.mapper.A36Mapper;
import com.hxoms.modules.keySupervision.familyMember.service.OmsSupFamilyMemberService;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;
import com.hxoms.modules.keySupervision.nakedOfficial.mapper.OmsSupNakedSignMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersonInfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeApply;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersonInfoMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegRevokeApplyMapper;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.text.resources.cldr.es.FormatData_es_419;

import java.util.*;

/**
 * <b>家庭成员业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/10 18:28
 */
@Service
public class OmsSupFamilyMemberServiceImpl extends ServiceImpl<A36Mapper,A36> implements OmsSupFamilyMemberService {

	@Autowired
	private A36Mapper a36Mapper;
	@Autowired
	private A01Mapper a01Mapper;
	@Autowired
	private OmsSupNakedSignMapper omsSupNakedSignMapper;
	@Autowired
	private OmsRegProcpersonInfoMapper omsRegProcpersonInfoMapper;
	@Autowired
	private OmsRegRevokeApplyMapper omsRegRevokeApplyMapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	/**
	 * <b>查询家庭成员关系列表</b>
	 * @return
	 */
	public List<SysDictItem> getFamilyMemberRelationship() {
		List<SysDictItem> list = sysDictItemMapper.selectFamilyMemberRelationship();
		if(list.size() > 0){
			return list;
		}
		return new ArrayList<SysDictItem>();
	}


	/**
	 * <b>查询政治面貌列表</b>
	 * @return
	 */
	public List<SysDictItem> getPoliticalAffi() {
		List<SysDictItem> list = sysDictItemMapper.selectPoliticalAffi();
		if(list.size() > 0){
			return list;
		}
		return new ArrayList<SysDictItem>();
	}



	/**
	 * <b>查询人员现状</b>
	 * @return
	 */
	public List<SysDictItem> getPersonStatus() {
		List<SysDictItem> list = sysDictItemMapper.getPersonStatus();
		if(list.size() > 0){
			return list;
		}
		return new ArrayList<SysDictItem>();
	}

	/**
	 * <b>查询国籍</b>
	 * @return
	 */
	public List<SysDictItem> getNationality() {
		List<SysDictItem> list = sysDictItemMapper.getNationality();
		if(list.size() > 0){
			return list;
		}
		return new ArrayList<SysDictItem>();
	}

	/**
	 * <b>查询移居类别</b>
	 * @return
	 */
	public List<SysDictItem> getMigrationCategory() {
		List<SysDictItem> list = sysDictItemMapper.getMigrationCategory();
		if(list.size() > 0){
			return list;
		}
		return new ArrayList<SysDictItem>();
	}



	/**
	 * <b>家庭成员模块查询人员基本信息</b>
	 * @param page
	 * @param idList
	 * @param name
	 * @return
	 */
	public Page getPersonInfoForfamily(Page<Map<String,Object>> page, List<String> idList, String name) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name" , name);
		map.put("idList", idList);
		PageHelper.startPage((int)page.getCurrent(), (int)page.getSize());
		List<Map<String,Object>> mapList = a01Mapper.selectPersonInfoForfamily(map);
		PageInfo pageInfo = new PageInfo(mapList);
		page.setRecords(mapList);
		page.setTotal(pageInfo.getTotal());
		page.setPages(pageInfo.getPages());
		return page;
	}


	/**
	 * <b>根据人员主键查询家庭成员信息(配偶子女)</b>
	 * @param a0100
	 * @param page
	 * @return
	 */
	public Page<A36> getFamilyMember(Page<A36> page,String a0100) {

		PageHelper.startPage((int)page.getCurrent(), (int)page.getSize());
		List<A36> list = a36Mapper.selectFamilyMember(a0100);
		PageInfo<A36> pageInfo = new PageInfo<A36>(list);
		page.setRecords(list);
		page.setTotal(pageInfo.getTotal());
		page.setPages(pageInfo.getPages());
		return page;
	}


	/**
	 * <b>更新保存家庭成员信息</b>
	 * @param list
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateFamilyMemberInfo(List<A36> list) {
		for(A36 a36 : list){
			a36.setModifyTime(new Date());
			a36.setModifyUser(UserInfoUtil.getUserInfo().getUserName());
			int count = a36Mapper.updateById(a36);
			if(count < 0){
				throw new CustomMessageException("更新保存家庭成员信息失败");
			}
		}
	}


	/**
	 * <b>对家庭成员进行登记备案</b>
	 * @param list
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addToRegistration(List<A36> list) {

		//查询备案表中的所有家庭成员
		QueryWrapper<OmsRegProcpersonInfo> wrapper = new QueryWrapper<OmsRegProcpersonInfo>();
		wrapper.eq("A0100", null)
				.or()
				.eq("A0100", "")
				.eq("POST", "801")
				.or()
				.eq("POST", "802");
		List<OmsRegProcpersonInfo> omsRegProcpersonInfoList = omsRegProcpersonInfoMapper.selectList(wrapper);

		for(A36 a36 : list){
			boolean result = false;
			//根据裸官主键查询裸官信息，用于判断裸官是否在限制性岗位
			QueryWrapper<OmsSupNakedSign> queryWrapper = new QueryWrapper<OmsSupNakedSign>();
			queryWrapper.eq("A0100", a36.getA0100())
						.eq("NS_STATUS", "1");
			OmsSupNakedSign omsSupNakedSign = omsSupNakedSignMapper.selectOne(queryWrapper);

			//限制性岗位的裸官家属可登记备案
			if(omsSupNakedSign != null && omsSupNakedSign.getXzxgw().equals("1")){
				//家庭成员登记备案判断是否重复（根据身份证号码判断）
				for(OmsRegProcpersonInfo omsRegProcpersonInfo : omsRegProcpersonInfoList){
					if(!omsRegProcpersonInfo.getIdnumber().equals(a36.getIdCard())){
						continue;
					}else{
						result = true;
						break;
					}
				}

				if(result == false){
					OmsRegProcpersonInfo omsRegProcpersonInfo = new OmsRegProcpersonInfo();
					//限制性岗位的裸官家属职务设置为'801'或'802'
					omsRegProcpersonInfo.setPost("801");

					//判断是否复姓
					boolean flag = OmsRegInitUtil.isCompoundSurname(a36.getA3601());
					if(flag){
						omsRegProcpersonInfo.setSurname(a36.getA3601().substring(0, 2));
						omsRegProcpersonInfo.setName(a36.getA3601().substring(2));
					}else {
						omsRegProcpersonInfo.setSurname(a36.getA3601().substring(0, 1));
						omsRegProcpersonInfo.setName(a36.getA3601().substring(1));
					}

					//生成备案人员主键
					omsRegProcpersonInfo.setId(UUIDGenerator.getPrimaryKey());
					omsRegProcpersonInfo.setInboundFlag("U");
					omsRegProcpersonInfo.setRfStatus("1");
					omsRegProcpersonInfo.setCheckStatus("1");
					omsRegProcpersonInfo.setIncumbencyStatus("1");
					omsRegProcpersonInfo.setDataType("1");
					omsRegProcpersonInfo.setIdentity("裸官配偶子女");
					omsRegProcpersonInfo.setSecretLevel("非涉密");
					omsRegProcpersonInfo.setFjgnf("1");
					omsRegProcpersonInfo.setRegisteResidence(a36.getHukouLocation());
					omsRegProcpersonInfo.setIdnumber(a36.getIdCard());
					omsRegProcpersonInfo.setWorkUnit(a36.getA3611());

					//查询家庭成员政治面貌
					List<String> politicalAffiList = a36Mapper.selectPiliticalAffi(a36.getA3600());
					//根据人员主键应该只能查到一个人员信息，因此取第一个
					omsRegProcpersonInfo.setPoliticalAffi(politicalAffiList.get(0));

					Date date = UtilDateTime.toDateFormat(a36.getA3607(),"yyyy-MM-dd");
					omsRegProcpersonInfo.setBirthDate(date);
					omsRegProcpersonInfo.setCreateTime(new Date());
					omsRegProcpersonInfo.setCreateUser(UserInfoUtil.getUserInfo().getUserName());

					int count = omsRegProcpersonInfoMapper.insert(omsRegProcpersonInfo);
					if(count <= 0){
						throw new CustomMessageException("家庭成员备案失败");
					}
				}
			}else {
				throw new CustomMessageException("裸官在限制性岗位，家属才能备案");
			}
		}
	}



	/**
	 * <b>取消裸官在限制性岗位时撤销家庭成员登记备案</b>
	 * @param a0100
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeToRegistration(String a0100) {
		//查询备案表中的所有家庭成员
		QueryWrapper<OmsRegProcpersonInfo> wrapper1 = new QueryWrapper<OmsRegProcpersonInfo>();
		wrapper1.eq("A0100", null)
				.or()
				.eq("A0100", "")
				.eq("POST", "801")
				.or()
				.eq("POST", "802");
		List<OmsRegProcpersonInfo> omsRegProcpersonInfoList = omsRegProcpersonInfoMapper.selectList(wrapper1);

		//查询撤销备案表中的所有家庭成员
		QueryWrapper<OmsRegRevokeApply> wrapper2 = new QueryWrapper<OmsRegRevokeApply>();
		wrapper2.eq("A0100", null)
				.or()
				.eq("A0100", "")
				.eq("POST", "801")
				.or()
				.eq("POST", "802");
		List<OmsRegRevokeApply> omsRegRevokeApplyList = omsRegRevokeApplyMapper.selectList(wrapper2);

		//首先查询该干部的家庭成员身份证号，根据身份证号匹配备案表中的家庭成员信息
		List<String> idCardList = a36Mapper.selectIdCardList(a0100);
		for(OmsRegProcpersonInfo omsRegProcpersonInfo : omsRegProcpersonInfoList){
			boolean flag = false;
			if(idCardList.contains(omsRegProcpersonInfo.getIdnumber())){
				//修改备案人员信息，改为已撤销，未备案，未验收
				omsRegProcpersonInfo.setInboundFlag("D");
				omsRegProcpersonInfo.setRfStatus("0");
				omsRegProcpersonInfo.setCheckStatus("0");
				omsRegProcpersonInfo.setIncumbencyStatus("0");
				omsRegProcpersonInfo.setFjgnf("0");
				omsRegProcpersonInfo.setModifyTime(new Date());
				omsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getUserName());
				int count = omsRegProcpersonInfoMapper.updateById(omsRegProcpersonInfo);
				if(count < 0){
					throw new CustomMessageException("裸官家庭成员撤销备案失败");
				}else{
					//在撤销登记备案表中添加，首先查询已经存在该家庭成员
					for(OmsRegRevokeApply omsRegRevokeApply : omsRegRevokeApplyList){
						if(omsRegRevokeApply.getIdnumber().equals(omsRegProcpersonInfo.getIdnumber()) &&
							omsRegRevokeApply.getStatus().equals("0")){
							//撤销登记备案表中已经存在
							flag = true;
							break;
						}
					}



					for(OmsRegRevokeApply omsRegRevokeApply1 : omsRegRevokeApplyList){
						if(idCardList.contains(omsRegRevokeApply1.getIdnumber()) && omsRegRevokeApply1.getStatus().equals("0")){
							continue;
						}else {
							//撤销登记备案中不存在或申请状态不是已上报，确认不是重复添加
							OmsRegRevokeApply omsRegRevokeApply = new OmsRegRevokeApply();
							omsRegRevokeApply.setId(UUIDGenerator.getPrimaryKey());
							omsRegRevokeApply.setA0100(omsRegProcpersonInfo.getA0100());
							omsRegRevokeApply.setCreateDate(new Date());
							omsRegRevokeApply.setCreateUser(UserInfoUtil.getUserInfo().getUserName());
							omsRegRevokeApply.setSurname(omsRegProcpersonInfo.getSurname());
							omsRegRevokeApply.setName(omsRegProcpersonInfo.getName());
							omsRegRevokeApply.setBirthDate(omsRegProcpersonInfo.getBirthDate());
							omsRegRevokeApply.setIdnumber(omsRegProcpersonInfo.getIdnumber());
							omsRegRevokeApply.setRegisteResidence(omsRegProcpersonInfo.getRegisteResidence());
							omsRegRevokeApply.setWorkUnit(omsRegProcpersonInfo.getWorkUnit());
							omsRegRevokeApply.setPost(omsRegProcpersonInfo.getPost());
							omsRegRevokeApply.setIdentity(omsRegProcpersonInfo.getIdentity());
							omsRegRevokeApply.setExitDate(UtilDateTime.formatDate(new Date() , "yy-MM-dd"));
							omsRegRevokeApply.setStatus("0");
							omsRegRevokeApply.setApplyReason("裸官取消限制性岗位");

							int result = omsRegRevokeApplyMapper.insert(omsRegRevokeApply);
							if(result < 0){
								throw new CustomMessageException("保存到撤销登记备案失败");
							}
						}
					}

				}
			}else {
				continue;
			}
		}
	}
}
