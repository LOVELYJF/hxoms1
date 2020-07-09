package com.hxoms.modules.roadPage.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.roadPage.service.CertificateStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    @GetMapping("/getLicenceStatistic")
    public Result getLicenceStatistic(){
        return Result.success(certificateStatisticsService.getLicenceStatistic());
    }

    @GetMapping("/getListCfCertificate")
    public Result getListCfCertificate(CfCertificatePageParam cfCertificatePageParam){
        return Result.success(certificateStatisticsService.getListCfCertificate(cfCertificatePageParam));
    }

    //因公出国（境）预审批
    @GetMapping("/getOnbgoApproval")
    public Result getOnbgoApproval(String pwh,String sqzt){
        System.out.println(pwh+"    "+sqzt);
        return Result.success(certificateStatisticsService.getOnbgoApproval(pwh,sqzt));
    }

    //因私出国（境）进度
    @GetMapping("/getFprgoSchedule")
    public Result getFprgoSchedule(){
        return Result.success(certificateStatisticsService.getFprgoSchedule());
    }
}
