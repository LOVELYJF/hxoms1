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
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.modules.keySupervision.familyMember.mapper.A36Mapper;
import com.hxoms.modules.keySupervision.familyMember.service.OmsSupFamilyMemberService;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;
import com.hxoms.modules.keySupervision.nakedOfficial.mapper.OmsSupNakedSignMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapply;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegRevokeApplyMapper;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private OmsRegProcpersoninfoMapper omsRegProcpersonInfoMapper;
	@Autowired
	private OmsRegRevokeApplyMapper omsRegRevokeApplyMapper;
	@Autowired
	private SysDictItemMapper sysDictItemMapper;
	@Autowired
	private CountryMapper countryMapper;
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
	public List<Country> getNationality() {
		List<Country> list = countryMapper.getCountryInfo(null);
		if(list.size() > 0){
			return list;
		}
		return new ArrayList<Country>();
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
	 * @return
	 */
	public List<A36> getFamilyMember(String a0100) {

		List<A36> list = a36Mapper.selectFamilyMember(a0100);
		//根据身份证号码切割得到出生日期
		if(list != null && list.size() > 0){
			for(A36 a36 : list){
				String birthdate = OmsRegInitUtil.getBirthByIdNumber(a36.getIdCard());
				a36.setA3607(birthdate);
			}
		}
		return list;
	}


	/**
	 * <b>保存家庭成员并登记备案</b>
	 * @param list
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addToRegistration(List<A36> list) {
		//保存家庭成员
		for(A36 a36 : list){
			a36.setModifyTime(new Date());
			a36.setModifyUser(UserInfoUtil.getUserInfo().getId());
			QueryWrapper<A36> queryWrapper = new QueryWrapper<A36>();
			queryWrapper.eq("a3600", a36.getA3600());
			int count = a36Mapper.update(a36, queryWrapper);
			if(count < 1){
				throw new CustomMessageException("更新保存家庭成员信息失败");
			}
		}

		//查询备案表中的所有家庭成员
		QueryWrapper<OmsRegProcpersoninfo> wrapper = new QueryWrapper<OmsRegProcpersoninfo>();
		wrapper.eq("POST", "801")
				.or()
				.eq("POST", "802");
		List<OmsRegProcpersoninfo> omsRegProcpersoninfoList = omsRegProcpersonInfoMapper.selectList(wrapper);

		if(list != null && list.size() > 0){
			for(A36 a36 : list){
				boolean result = false;
				//根据人员主键查询裸官信息，用于判断裸官是否在限制性岗位
				QueryWrapper<OmsSupNakedSign> queryWrapper = new QueryWrapper<OmsSupNakedSign>();
				queryWrapper.eq("A0100", a36.getA0100());
				OmsSupNakedSign omsSupNakedSign = omsSupNakedSignMapper.selectOne(queryWrapper);

				//限制性岗位的裸官家属可登记备案
				if(omsSupNakedSign != null && omsSupNakedSign.getXzxgw().equals("1") && omsSupNakedSign.getFjgnf().equals("1")){
					//家庭成员登记备案判断是否重复（根据身份证号码判断）
					if(omsRegProcpersoninfoList != null && omsRegProcpersoninfoList.size() > 0){
						for(OmsRegProcpersoninfo omsRegProcpersonInfo : omsRegProcpersoninfoList){
							if(!omsRegProcpersonInfo.getIdnumberGb().equals(a36.getIdCard())){
								continue;
							}else{
								result = true;
								break;
							}
						}
					}

					if(result == false){
						//登记备案库中没有该人员，进行添加
						OmsRegProcpersoninfo omsRegProcpersonInfo = new OmsRegProcpersoninfo();
						//限制性岗位的裸官家属职务设置为'801'或'802'
						String a3604a = a36.getA3604a();
						if(a3604a.equals("妻子") || a3604a.equals("丈夫")){
							omsRegProcpersonInfo.setPost("801");
						}else {
							omsRegProcpersonInfo.setPost("802");
						}

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
						omsRegProcpersonInfo.setRfStatus("0");
						omsRegProcpersonInfo.setCheckStatus("0");
						omsRegProcpersonInfo.setIncumbencyStatus("1");
						omsRegProcpersonInfo.setIdentity("其他人员");
						omsRegProcpersonInfo.setIdentityCode("9");
						omsRegProcpersonInfo.setSecretLevel("非涉密");
						omsRegProcpersonInfo.setRegisteResidence(a36.getHukouLocation());
						omsRegProcpersonInfo.setIdnumberGb(a36.getIdCard());
						omsRegProcpersonInfo.setWorkUnit(a36.getA3611());

						//查询家庭成员政治面貌
						List<String> politicalAffiList = a36Mapper.selectPiliticalAffi(a36.getA3600());
						//根据人员主键应该只能查到一个人员信息，因此取第一个
						omsRegProcpersonInfo.setPoliticalAffiname(politicalAffiList.get(0));
						omsRegProcpersonInfo.setPoliticalAfficode(a36.getA3627());

						Date date = UtilDateTime.toDateFormat(a36.getA3607(),"yyyy-MM-dd");
						omsRegProcpersonInfo.setBirthDate(date);
						omsRegProcpersonInfo.setCreateTime(new Date());
						omsRegProcpersonInfo.setCreateUser(UserInfoUtil.getUserInfo().getId());

						int count = omsRegProcpersonInfoMapper.insert(omsRegProcpersonInfo);
						if(count < 1){
							throw new CustomMessageException("家庭成员备案失败");
						}
					}else {
						//登记备案库中 已经存在该人员，进行更新操作，根据身份证号码修改
						OmsRegProcpersoninfo omsRegProcpersonInfo = new OmsRegProcpersoninfo();

						//判断是否复姓
						boolean flag = OmsRegInitUtil.isCompoundSurname(a36.getA3601());
						if(flag){
							omsRegProcpersonInfo.setSurname(a36.getA3601().substring(0, 2));
							omsRegProcpersonInfo.setName(a36.getA3601().substring(2));
						}else {
							omsRegProcpersonInfo.setSurname(a36.getA3601().substring(0, 1));
							omsRegProcpersonInfo.setName(a36.getA3601().substring(1));
						}
						Date date = UtilDateTime.toDateFormat(a36.getA3607(),"yyyy-MM-dd");
						omsRegProcpersonInfo.setBirthDate(date);
						//政治面貌代码
						omsRegProcpersonInfo.setPoliticalAfficode(a36.getA3627());
						//根据政治面貌代码查询政治面貌
						String politicalAffi = sysDictItemMapper.selectPoliticalAffiByCode(a36.getA3627());
						omsRegProcpersonInfo.setPoliticalAffiname(politicalAffi);

						omsRegProcpersonInfo.setRegisteResidence(a36.getHukouLocation());
						omsRegProcpersonInfo.setWorkUnit(a36.getA3611());
						omsRegProcpersonInfo.setModifyTime(new Date());
						omsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());

						QueryWrapper<OmsRegProcpersoninfo> queryWrapper1 = new QueryWrapper<OmsRegProcpersoninfo>();
						queryWrapper1.eq("IDNUMBER", a36.getIdCard());
						omsRegProcpersonInfoMapper.update(omsRegProcpersonInfo, queryWrapper1);
					}
				}else {
					throw new CustomMessageException("在限制性岗位且家属受监管的裸官家属才能备案");
				}
			}
		}else {
			throw new CustomMessageException("请选择要备案的人员信息");
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
		QueryWrapper<OmsRegProcpersoninfo> wrapper1 = new QueryWrapper<OmsRegProcpersoninfo>();
		wrapper1.eq("POST", "801")
				.or()
				.eq("POST", "802");
		List<OmsRegProcpersoninfo> omsRegProcpersoninfoList = omsRegProcpersonInfoMapper.selectList(wrapper1);

		//查询撤销备案表中的所有家庭成员
		QueryWrapper<OmsRegRevokeapply> wrapper2 = new QueryWrapper<OmsRegRevokeapply>();
		wrapper2.eq("POST", "801")
				.or()
				.eq("POST", "802");
		List<OmsRegRevokeapply> omsRegRevokeapplyList = omsRegRevokeApplyMapper.selectList(wrapper2);

		if(omsRegProcpersoninfoList != null && omsRegProcpersoninfoList.size() > 0){
			//首先查询该干部的家庭成员身份证号，根据身份证号匹配备案表中的家庭成员信息
			List<String> idCardList = a36Mapper.selectIdCardList(a0100);
			for(OmsRegProcpersoninfo omsRegProcpersonInfo : omsRegProcpersoninfoList){
				boolean flag = false;
				if(idCardList.contains(omsRegProcpersonInfo.getIdnumberGb())){
					//修改备案人员信息，改为已撤销，未备案，未验收
					omsRegProcpersonInfo.setInboundFlag("D");
					omsRegProcpersonInfo.setRfStatus("0");
					omsRegProcpersonInfo.setCheckStatus("0");
					omsRegProcpersonInfo.setModifyTime(new Date());
					omsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());
					int count = omsRegProcpersonInfoMapper.updateById(omsRegProcpersonInfo);
					if(count < 1){
						throw new CustomMessageException("裸官家庭成员撤销备案失败");
					}else{
						if(omsRegRevokeapplyList != null && omsRegRevokeapplyList.size() > 0){
							//在撤销登记备案表中添加，首先查询撤销备案表是否存在该家庭成员
							for(OmsRegRevokeapply omsRegRevokeApply : omsRegRevokeapplyList){
								if(omsRegRevokeApply.getIdnumber().equals(omsRegProcpersonInfo.getIdnumberGb()) &&
										omsRegRevokeApply.getStatus().equals("0")){
									//撤销登记备案表中已经存在
									flag = true;
									break;
								}else {
									continue;
								}
							}
						}

						if(flag == false){
							OmsRegRevokeapply omsRegRevokeApply = new OmsRegRevokeapply();
							omsRegRevokeApply.setId(UUIDGenerator.getPrimaryKey());
							omsRegRevokeApply.setCreateDate(new Date());
							omsRegRevokeApply.setCreateUser(UserInfoUtil.getUserInfo().getId());
							omsRegRevokeApply.setSurname(omsRegProcpersonInfo.getSurname());
							omsRegRevokeApply.setName(omsRegProcpersonInfo.getName());
							omsRegRevokeApply.setBirthDate(omsRegProcpersonInfo.getBirthDate());
							omsRegRevokeApply.setIdnumber(omsRegProcpersonInfo.getIdnumberGb());
							omsRegRevokeApply.setRegisteResidence(omsRegProcpersonInfo.getRegisteResidence());
							omsRegRevokeApply.setWorkUnit(omsRegProcpersonInfo.getWorkUnit());
							omsRegRevokeApply.setPost(omsRegProcpersonInfo.getPost());
							omsRegRevokeApply.setIdentity(omsRegProcpersonInfo.getIdentity());
							omsRegRevokeApply.setIdentityCode(omsRegProcpersonInfo.getIdentityCode());
							omsRegRevokeApply.setExitDate(UtilDateTime.formatDate(new Date() , "yyyy-MM-dd"));
							omsRegRevokeApply.setStatus("0");
							omsRegRevokeApply.setApplyReason("裸官取消限制性岗位");

							int result = omsRegRevokeApplyMapper.insert(omsRegRevokeApply);
							if(result < 1){
								throw new CustomMessageException("保存到撤销登记备案失败");
							}
						}
					}
				}else {
					continue;
				}
			}
		}else {
			throw new CustomMessageException("该裸官家属未备案");
		}
	}
}
