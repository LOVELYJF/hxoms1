package com.hxoms.modules.roadPage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Constants;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.roadPage.mapper.CertificateStatisticsMapper;
import com.hxoms.modules.roadPage.service.CertificateStatisticsService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CertificateStatisticsImpl implements CertificateStatisticsService {

    @Autowired
    private CertificateStatisticsMapper certificateStatisticsMapper;

    @Override
    public List<Map<String, Object>> getLicenceStatistic() {
        /**
         * 1、证照统计
         *    统计：护照（1），港澳通行证（2），台湾通行证（4）
         * 2、证照上缴情况
         *    统计：护照，港澳通行证，台湾通行证
         */

        List<Map<String, Object>> passportHongKongTaiwan = certificateStatisticsMapper.getPassportHongKongTaiwan();
        //已过期数量（证照有效期小于等于当前日期）
        Map<String, Object> expireqty = certificateStatisticsMapper.getExpireqty(-1);
        //半年内过期数量（有效期小于当前日期）
        Map<String, Object> halfYearExpireqty = certificateStatisticsMapper.getHalfYearExpireqty(181);

        passportHongKongTaiwan.add(expireqty);
        passportHongKongTaiwan.add(halfYearExpireqty);
        return passportHongKongTaiwan;
    }

    @Override
    public PageInfo<CfCertificate> getListCfCertificate(CfCertificatePageParam cfCertificatePageParam) {
        int pageNum = cfCertificatePageParam.getPageNum()==null ? 0 :cfCertificatePageParam.getPageNum();
        int pageSize = cfCertificatePageParam.getPageSize()==null ? 10 :cfCertificatePageParam.getPageSize();

        PageHelper.startPage(pageNum,pageSize);
        List<CfCertificate> listCfCertificate = certificateStatisticsMapper.getListCfCertificate(cfCertificatePageParam);

        PageInfo<CfCertificate> pageInfo = new PageInfo(listCfCertificate);
        return pageInfo;
    }

    @Override
    public Map<String,Object> getOnbgoApproval(String pwh, String sqzt) {
        /**
         * 因公出国（境）预审批
         * 1、省外办 2、港澳办 3、琼台赴字
         */
        //1、省外办


        //2、港澳办


        //3、琼台赴字
        Map<String,Object> map = new HashedMap();
        map.put("PROJECTNAME","琼台赴字");
        map.put("PUBLIC_BUSINESS",Constants.public_business);
        map.put("PUBLIC_BUSINESSNAME",Constants.public_businessName);
        map.put("DATAS",certificateStatisticsMapper.selectQiongtaiWord());

        return map;
    }

    //因私出国（境）进度
    @Override
    public Map<String, Object> getFprgoSchedule() {
        Map<String,Object> map = new HashedMap();
        map.put("PROJECTNAME","因私出国（境）进度");

        int[] private_business = Constants.private_business;
        String[] private_businessName = Constants.private_businessName;
        List<Map<String, Object>> fprgoSchedule = certificateStatisticsMapper.getFprgoSchedule();

        map.put("PRIVATE_BUSINESSNAME",private_businessName);

        //map.put("PRIVATE_BUSINESS",Constants.private_business);

        Map<Object,String> mapb = new HashedMap();
        for (int i = 0; i < private_business.length; i++) {
            mapb.put(private_business[i],private_businessName[i]);
        }
        for (int i = 0; i <fprgoSchedule.size(); i++) {
            fprgoSchedule.get(i).put("APPLYSTATUS",mapb.get(fprgoSchedule.get(i).get("APPLY_STATUS")));
        }
        map.put("DATAS",fprgoSchedule);

        return map;
    }
}
