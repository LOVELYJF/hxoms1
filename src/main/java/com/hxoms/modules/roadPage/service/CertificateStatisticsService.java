package com.hxoms.modules.roadPage.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.message.message.entity.Message;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.roadPage.entity.PersonnelPageParam;

import java.util.List;
import java.util.Map;

public interface CertificateStatisticsService {

    List<Map<String,Object>> getLicenceStatistic();

    PageInfo<CfCertificate> getListCfCertificate(CfCertificatePageParam cfCertificatePageParam);

    Map<String,Object> getOnbgoApproval(String pwh, String sqzt);

    Map<String,Object> getFprgoSchedule();

    Object getPersonnelRoster(PersonnelPageParam plpageParam);


    /**
     * 功能描述: <br>
     * 〈获取待办任务〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.message.message.entity.Message>
     * @Author: 李逍遥
     * @Date: 2020/10/19 19:51
     */
    List<Message> getDBMessageList();
}


