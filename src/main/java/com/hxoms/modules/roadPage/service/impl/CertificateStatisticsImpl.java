package com.hxoms.modules.roadPage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.message.message.entity.Message;
import com.hxoms.message.message.mapper.MessageMapper;
import com.hxoms.modules.keySupervision.majorLeader.entity.OmsSupMajorLeader;
import com.hxoms.modules.keySupervision.majorLeader.mapper.OmsSupMajorLeaderMapper;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.roadPage.entity.PersonnelPageParam;
import com.hxoms.modules.roadPage.mapper.CertificateStatisticsMapper;
import com.hxoms.modules.roadPage.service.CertificateStatisticsService;
import com.hxoms.modules.roster.mapper.SysRosterMapper;
import com.hxoms.modules.sysUser.mapper.CfUserMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CertificateStatisticsImpl implements CertificateStatisticsService {

    @Autowired
    private CertificateStatisticsMapper certificateStatisticsMapper;
    
    @Autowired
    private SysRosterMapper sysRosterMapper;

    @Autowired
    private OmsSupMajorLeaderMapper omsSupMajorLeaderMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private CfUserMapper cfUserMapper;

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

        return new PageInfo(listCfCertificate);
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

        List<Map<String, Object>> fprgoSchedule = certificateStatisticsMapper.getFprgoSchedule();

        Map<Object,String> mapb = new HashedMap();
        List<String> private_businessName=new ArrayList<>();
        for (Constants.emPrivateGoAbroad goAbroad:Constants.emPrivateGoAbroad.values()) {
            mapb.put(goAbroad.getIndex(),goAbroad.getName());
            private_businessName.add(goAbroad.getName());
        }
        map.put("PRIVATE_BUSINESSNAME",private_businessName);
        for (int i = 0; i <fprgoSchedule.size(); i++) {
            fprgoSchedule.get(i).put("APPLYSTATUS",mapb.get(fprgoSchedule.get(i).get("APPLY_STATUS")));
        }
        map.put("DATAS",fprgoSchedule);

        return map;
    }



    @Override
    public Object getPersonnelRoster(PersonnelPageParam plpageParam) {
        Object object = null;
        Integer pagenum = plpageParam.getPageNum()==null ? 1 :plpageParam.getPageNum();
        Integer pagesize = plpageParam.getPageSize()==null ? 10 :plpageParam.getPageSize();

        switch (plpageParam.getOrderIndex()){
            case 0:
                object = sysRosterMapper.selectSysRosterRtype("PERSONNELINFORMATION");
                break;
            case 1:
                System.out.println(plpageParam.getNodeName());
                break;
            case 2:
                System.out.println(plpageParam.getNodeName());
                break;
            case 3:
                PageHelper.startPage(pagenum,pagesize);
                List<OmsSupMajorLeader> omsSupMajorLeaders = omsSupMajorLeaderMapper.selectList(null);
                object = new PageInfo<>(omsSupMajorLeaders);
                break;
            case 4:
                System.out.println(plpageParam.getNodeName());
                break;
            case 5:
                System.out.println(plpageParam.getNodeName());
                break;
            case 6:
                System.out.println(plpageParam.getNodeName());
                break;
        }
        return object;
    }

    /**
     * 功能描述: <br>
     * 〈获取待办任务〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.message.message.entity.Message>
     * @Author: 李逍遥
     * @Date: 2020/10/19 20:18
     */
    @Override
    public List<Message> getDBMessageList() {
        //获取登录用户信息
        UserInfo loginUser = UserInfoUtil.getUserInfo();
        //查询用户所在组的id
        String id = cfUserMapper.getUg_id(loginUser.getId());
        //封装id集合
        List<String> ids = new ArrayList<>();
        if (loginUser != null ){
            ids.add(loginUser.getId());
            ids.add(loginUser.getOrgId());
            ids.add(id);
        }
        //查询所有需要处理的且未处理的信息
        List<Message> messages = messageMapper.getDBMessageList(ids);
        return messages;
    }
}
