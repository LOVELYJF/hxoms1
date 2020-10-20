package com.hxoms.modules.roadPage.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.message.message.entity.Message;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.roadPage.entity.PersonnelPageParam;
import com.hxoms.modules.roadPage.service.CertificateStatisticsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/licenceStatistics")
public class CertificateStatisticsController {


    @Autowired
    private CertificateStatisticsService certificateStatisticsService;

    /**
     * 1、证照统计
     * 2、证照上缴情况
     * 3、证照借出情况
     */
    @ApiOperation("证照统计接口")
    @GetMapping("/getLicenceStatistic")
    public Result getLicenceStatistic(){
        return Result.success(certificateStatisticsService.getLicenceStatistic());
    }

    @ApiOperation("证照详细接口：护照,港澳通行证,台湾通行证,已过期数量,半年内过期数量")
    @GetMapping("/getListCfCertificate")
    public Result getListCfCertificate(CfCertificatePageParam cfCertificatePageParam){
        return Result.success(certificateStatisticsService.getListCfCertificate(cfCertificatePageParam));
    }

    @ApiOperation("因公出国（境）预审批")
    @GetMapping("/getOnbgoApproval")
    public Result getOnbgoApproval(String pwh,String sqzt){
        System.out.println(pwh+"    "+sqzt);
        return Result.success(certificateStatisticsService.getOnbgoApproval(pwh,sqzt));
    }

    @ApiOperation("因私出国（境）进度接口")
    @GetMapping("/getFprgoSchedule")
    public Result getFprgoSchedule(){
        return Result.success(certificateStatisticsService.getFprgoSchedule());
    }

    @ApiOperation("人员情况接口")
    @GetMapping("/getPersonnelRoster")
    public Result getPersonnelRoster(PersonnelPageParam plpageParam){

        return Result.success(certificateStatisticsService.getPersonnelRoster(plpageParam));
    }

    /**
     * 功能描述: <br>
     * 〈获取待办任务〉
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/10/19 19:51
     */
    @GetMapping("/getDBMessageList")
    public Result getDBMessageList(){
        List<Message> messages = certificateStatisticsService.getDBMessageList();
        return Result.success(messages);
    }

}
