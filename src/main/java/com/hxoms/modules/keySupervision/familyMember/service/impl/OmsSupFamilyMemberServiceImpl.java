package com.hxoms.modules.keySupervision.familyMember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.OmsCommonUtil;
import com.hxoms.common.OmsRegInitUtil;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.*;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.keySupervision.familyMember.entity.A36;
import com.hxoms.modules.keySupervision.familyMember.mapper.A36Mapper;
import com.hxoms.modules.keySupervision.familyMember.service.OmsSupFamilyMemberService;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.enums.YesOrNoEnum;
import com.hxoms.modules.keySupervision.nakedOfficial.mapper.OmsSupNakedSignMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegRevokeapply;
import com.hxoms.modules.omsregcadre.mapper.OmsRegProcpersoninfoMapper;
import com.hxoms.modules.omsregcadre.mapper.OmsRegRevokeApplyMapper;
import com.hxoms.modules.omsregcadre.service.OmsRegProcpersonInfoService;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <b>家庭成员业务层接口实现类</b>
 *
 * @author luoshuai
 * @date 2020/5/10 18:28
 */
@Service
public class OmsSupFamilyMemberServiceImpl extends ServiceImpl<A36Mapper, A36> implements OmsSupFamilyMemberService {

    @Autowired
    private A36Mapper a36Mapper;
    @Autowired
    private A01Mapper a01Mapper;
    @Autowired
    private OmsSupNakedSignMapper omsSupNakedSignMapper;
    @Autowired
    private OmsRegProcpersoninfoMapper omsRegProcpersonInfoMapper;
    @Autowired
    private OmsRegProcpersonInfoService omsRegProcpersonInfoService;
    @Autowired
    private OmsRegRevokeApplyMapper omsRegRevokeApplyMapper;
    @Autowired
    private SysDictItemMapper sysDictItemMapper;
    @Autowired
    private CountryMapper countryMapper;

    /**
     * <b>查询家庭成员关系列表</b>
     *
     * @return
     */
    public List<SysDictItem> getFamilyMemberRelationship() {
        List<SysDictItem> list = sysDictItemMapper.selectFamilyMemberRelationship();
        if (list.size() > 0) {
            return list;
        }
        return new ArrayList<SysDictItem>();
    }


    /**
     * <b>查询政治面貌列表</b>
     *
     * @return
     */
    public List<SysDictItem> getPoliticalAffi() {
        List<SysDictItem> list = sysDictItemMapper.selectPoliticalAffi();
        if (list.size() > 0) {
            return list;
        }
        return new ArrayList<SysDictItem>();
    }


    /**
     * <b>查询人员现状</b>
     *
     * @return
     */
    public List<SysDictItem> getPersonStatus() {
        List<SysDictItem> list = sysDictItemMapper.getPersonStatus();
        if (list.size() > 0) {
            return list;
        }
        return new ArrayList<SysDictItem>();
    }

    /**
     * <b>查询国籍</b>
     *
     * @return
     */
    public List<Country> getNationality() {
        List<Country> list = countryMapper.getCountryInfo(null);
        if (list.size() > 0) {
            return list;
        }
        return new ArrayList<Country>();
    }

    /**
     * <b>查询移居类别</b>
     *
     * @return
     */
    public List<SysDictItem> getMigrationCategory() {
        List<SysDictItem> list = sysDictItemMapper.getMigrationCategory();
        if (list.size() > 0) {
            return list;
        }
        return new ArrayList<SysDictItem>();
    }


    /**
     * <b>添加家庭成员</b>
     *
     * @param a36
     * @return
     */
    public void insertFamilyMember(A36 a36) {
        a36.setA3600(UUIDGenerator.getPrimaryKey());
        a36.setIsDeleted(YesOrNoEnum.NO.getCode());
        a36.setIsNormal(YesOrNoEnum.YES.getCode());
        int count = a36Mapper.insert(a36);
        if (count < 1) {
            throw new CustomMessageException("添加家庭成员失败");
        }
    }

    @Override
    public void updateList(List<A36> a36) {
        a36Mapper.updateList(a36);
    }


    /**
     * <b>家庭成员模块查询人员基本信息</b>
     *
     * @param page
     * @param idList
     * @param name
     * @return
     */
    public Page getPersonInfoForfamily(Page<Map<String, Object>> page, List<String> idList, String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("idList", idList);
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<Map<String, Object>> mapList = a01Mapper.selectPersonInfoForfamily(map);
        PageInfo pageInfo = new PageInfo(mapList);
        page.setRecords(mapList);
        page.setTotal(pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
        return page;
    }


    /**
     * <b>根据人员主键查询家庭成员信息(配偶子女)</b>
     *
     * @param a0100
     * @return
     */
    public List<A36> getFamilyMember(String a0100) {
        if (StringUtils.isBlank(a0100)) {
            throw new CustomMessageException("参数错误");
        }

		List<A36> list = a36Mapper.selectFamilyMember(a0100);
		//根据身份证号码切割得到出生日期
		if(!ListUtil.isEmpty(list)){
			for(A36 a36 : list){
				if(!StringUtils.isBlank(a36.getIdCard())){
					String birthdate = OmsRegInitUtil.getBirthByIdNumber(a36.getIdCard());
					a36.setA3607(birthdate);
				}
			}
		}
		return list;
	}


    /**
     * <b>保存家庭成员并登记备案</b>
     *
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Result addToRegistration(List<A36> list) {
        if (list == null || list.size() < 1) {
            return Result.error("未选中成员信息");
        }
        //保存家庭成员
        for (A36 a36 : list) {
            a36.setModifyTime(new Date());
            a36.setModifyUser(UserInfoUtil.getUserInfo().getId());
            QueryWrapper<A36> queryWrapper = new QueryWrapper<A36>();
            queryWrapper.eq("a3600", a36.getA3600());
            int count = a36Mapper.update(a36, queryWrapper);
            if (count < 1) {
                return Result.error("更新保存家庭成员信息失败");
            }
        }
        //缓存行政区划
        HashMap<String, SysDictItem> hashMapXZQH = OmsCommonUtil.CacheDictItem("ZB01", false);
        //缓存政治面貌
        HashMap<String, SysDictItem> hashMapPolitical = OmsCommonUtil.CacheDictItem("GB4762", false);

        //查询备案表中的所有家庭成员
        List<OmsRegProcpersoninfo> omsRegProcpersoninfoList = omsRegProcpersonInfoMapper.selectFamilyByA0100(list.get(0).getA0100());

        //根据人员主键查询裸官信息，用于判断裸官是否在限制性岗位
        QueryWrapper<OmsSupNakedSign> queryWrapper = new QueryWrapper<OmsSupNakedSign>();
        queryWrapper.eq("A0100", list.get(0).getA0100());
        queryWrapper.eq("IS_DELETE", "0");
        OmsSupNakedSign omsSupNakedSign = omsSupNakedSignMapper.selectOne(queryWrapper);

        if (omsSupNakedSign == null) return Result.error("未查询到该省管干部的裸官信息，是否已取消了裸官标识？");
        if (!omsSupNakedSign.getXzxgw().equals(YesOrNoEnum.YES.getCode()) ||
                !omsSupNakedSign.getFjgnf().equals(YesOrNoEnum.YES.getCode()))
            return Result.error("只有在限制性岗位，且家庭成员受监管的干部的配偶子女才能登记备案！");

        List<OmsRegProcpersoninfo> inserted=new ArrayList<>();
        List<OmsRegProcpersoninfo> updated=new ArrayList<>();

        for (A36 a36 : list) {
            OmsRegProcpersoninfo nOmsRegProcpersonInfo = null;

            //家庭成员登记备案判断是否重复（根据身份证号码判断）
            if (omsRegProcpersoninfoList != null && omsRegProcpersoninfoList.size() > 0) {
                for (OmsRegProcpersoninfo omsRegProcpersonInfo : omsRegProcpersoninfoList) {
                    if (!omsRegProcpersonInfo.getId().equals(a36.getA3600())) {
                        continue;
                    } else {
                        nOmsRegProcpersonInfo = omsRegProcpersonInfo;
                        break;
                    }
                }
            }

            if (nOmsRegProcpersonInfo == null) {
                //登记备案库中没有该人员，进行添加
                nOmsRegProcpersonInfo = new OmsRegProcpersoninfo();
                //限制性岗位的裸官家属职务设置为'801'或'802'
                String a3604a = a36.getA3604a();
                if (a3604a.equals("妻子") || a3604a.equals("丈夫")) {
                    nOmsRegProcpersonInfo.setPost("801");
                } else {
                    nOmsRegProcpersonInfo.setPost("802");
                }

                //备案人员主键与家庭成员表主键相同，方便修改时匹配
                nOmsRegProcpersonInfo.setId(a36.getA3600());
                nOmsRegProcpersonInfo.setDataType("1");
                nOmsRegProcpersonInfo.setInboundFlag("U");
                nOmsRegProcpersonInfo.setRfStatus("0");
                nOmsRegProcpersonInfo.setCheckStatus("0");
                nOmsRegProcpersonInfo.setIncumbencyStatus("1");
                nOmsRegProcpersonInfo.setIdentity("其他人员");
                nOmsRegProcpersonInfo.setIdentityCode("9");
                nOmsRegProcpersonInfo.setSecretLevel("非涉密");
                nOmsRegProcpersonInfo.setCreateTime(new Date());
                nOmsRegProcpersonInfo.setCreateUser(UserInfoUtil.getUserInfo().getId());

                inserted.add(nOmsRegProcpersonInfo);
            } else {
                //登记备案库中 已经存在该人员，进行更新操作，根据身份证号码修改
                nOmsRegProcpersonInfo.setModifyTime(new Date());
                nOmsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());

                updated.add(nOmsRegProcpersonInfo);
            }
            if(a36.getA3604a().indexOf("母")>0||a36.getA3604a().indexOf("妻")>0||a36.getA3604a().indexOf("女")>0)
            {
                nOmsRegProcpersonInfo.setSex("2");
            }
            else
                nOmsRegProcpersonInfo.setSex("1");
            //判断是否复姓
            omsRegProcpersonInfoService.SplitName(nOmsRegProcpersonInfo, a36.getA3601());
            if(StringUilt.stringIsNullOrEmpty(a36.getA3607())==false) {
                Date date = UtilDateTime.formatDate(a36.getA3607());
                nOmsRegProcpersonInfo.setBirthDate(date);
            }

            //政治面貌
            String zzmm = a36.getA3627();
            SysDictItem sysDictItem = hashMapPolitical.get(zzmm);
            if (sysDictItem != null)
                nOmsRegProcpersonInfo.setPoliticalAfficode(sysDictItem.getItemCode());
            nOmsRegProcpersonInfo.setPoliticalAffiname(zzmm);

            //设置户口所在地代码
            String huKouLocation = a36.getHukouLocation();
            SysDictItem hk = hashMapXZQH.get(huKouLocation);
            if (hk != null)
                nOmsRegProcpersonInfo.setRegisteResidenceCode(hk.getItemCode());
            nOmsRegProcpersonInfo.setRegisteResidence(huKouLocation);

            nOmsRegProcpersonInfo.setWorkUnit(a36.getA3611());
            nOmsRegProcpersonInfo.setPost(a36.getA3611());
            nOmsRegProcpersonInfo.setIdnumberGb(a36.getIdCard());

        }
        if(inserted.size()>0)
            omsRegProcpersonInfoService.saveBatch(inserted);
        if(updated.size()>0)
            omsRegProcpersonInfoService.updateBatchById(updated);
        return Result.success("选择的家庭成员已存入登记备案表，请及时到出入境管理机构登记备案。");
    }


    /**
     * <b>取消裸官在限制性岗位时撤销家庭成员登记备案</b>
     *
     * @param a0100
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeToRegistration(String a0100) {
        if (StringUtils.isBlank(a0100)) {
            throw new CustomMessageException("参数错误");
        }
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

        if (omsRegProcpersoninfoList != null && omsRegProcpersoninfoList.size() > 0) {
            //首先查询该干部的家庭成员身份证号，根据身份证号匹配备案表中的家庭成员信息
            List<String> idCardList = a36Mapper.selectIdCardList(a0100);
            for (OmsRegProcpersoninfo omsRegProcpersonInfo : omsRegProcpersoninfoList) {
                boolean flag = false;
                if (idCardList.contains(omsRegProcpersonInfo.getIdnumberGb())) {
                    //修改备案人员信息，改为已撤销，未备案，未验收
                    omsRegProcpersonInfo.setInboundFlag("D");
                    omsRegProcpersonInfo.setRfStatus("0");
                    omsRegProcpersonInfo.setCheckStatus("0");
                    omsRegProcpersonInfo.setModifyTime(new Date());
                    omsRegProcpersonInfo.setModifyUser(UserInfoUtil.getUserInfo().getId());
                    omsRegProcpersonInfoMapper.updateById(omsRegProcpersonInfo);

                    if (!ListUtil.isEmpty(omsRegRevokeapplyList)) {
                        //在撤销登记备案表中添加，首先查询撤销备案表是否存在该家庭成员
                        for (OmsRegRevokeapply omsRegRevokeApply : omsRegRevokeapplyList) {
                            if (omsRegRevokeApply.getIdnumberGb().equals(omsRegProcpersonInfo.getIdnumberGb()) &&
                                    omsRegRevokeApply.getStatus().equals("0")) {
                                //撤销登记备案表中已经存在
                                flag = true;
                                break;
                            } else {
                                continue;
                            }
                        }
                    }

                    if (flag == false) {
                        OmsRegRevokeapply omsRegRevokeApply = new OmsRegRevokeapply();
                        omsRegRevokeApply.setId(UUIDGenerator.getPrimaryKey());
                        omsRegRevokeApply.setCreateDate(new Date());
                        omsRegRevokeApply.setCreateUser(UserInfoUtil.getUserInfo().getId());
                        omsRegRevokeApply.setSurname(omsRegProcpersonInfo.getSurname());
                        omsRegRevokeApply.setName(omsRegProcpersonInfo.getName());
                        omsRegRevokeApply.setBirthDate(omsRegProcpersonInfo.getBirthDate());
                        omsRegRevokeApply.setIdnumberGb(omsRegProcpersonInfo.getIdnumberGb());
                        omsRegRevokeApply.setRegisteResidence(omsRegProcpersonInfo.getRegisteResidence());
                        omsRegRevokeApply.setWorkUnit(omsRegProcpersonInfo.getWorkUnit());
                        omsRegRevokeApply.setPost(omsRegProcpersonInfo.getPost());
                        omsRegRevokeApply.setIdentity(omsRegProcpersonInfo.getIdentity());
                        omsRegRevokeApply.setIdentityCode(omsRegProcpersonInfo.getIdentityCode());
                        omsRegRevokeApply.setExitDate(UtilDateTime.formatDate(new Date(), "yyyy-MM-dd"));
                        omsRegRevokeApply.setStatus("0");
                        omsRegRevokeApply.setApplyReason("裸官取消限制性岗位");

                        omsRegRevokeApplyMapper.insert(omsRegRevokeApply);

                    }
                } else {
                    continue;
                }
            }
        }
    }
}
