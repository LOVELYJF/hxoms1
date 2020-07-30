package com.hxoms.modules.passportCard.certificateCollect.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCollectionRequestParam;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionRequestService;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/CfCertificateCollection")
public class CfCertificateCollectionController {

    //催缴记录接口
    @Autowired
    private CfCertificateCollectionService cfCertificateCollectionService;


    //催缴列表接口
    @Autowired
    private CfCertificateCollectionRequestService cfCertificateCollectionRequestService;


    /**
     *  条件查询所有的催缴名单
     * @param cfCertificateCollectionRequestParam
     * @return
     */
    @GetMapping("/selectCfCertificateCollectionRequestPage")
    public Result selectCfCertificateCollectionRequestPage(CfCertificateCollectionRequestParam cfCertificateCollectionRequestParam){

       PageInfo<CfCertificateCollectionRequest> CfCertificateCollectionRequestList =  cfCertificateCollectionRequestService.selectCfCertificateCollectionRequestPage(cfCertificateCollectionRequestParam);

       return Result.success(CfCertificateCollectionRequestList);
    }

    /**
     * 增加或者修改催缴名单
     * @param cfCertificateCollectionRequest
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(CfCertificateCollectionRequest cfCertificateCollectionRequest){

        return Result.success(cfCertificateCollectionRequestService.saveOrUpdate(cfCertificateCollectionRequest));
    }


    /**
     * 单条增加催缴记录
     * @param cfCertificateCollection
     * @return
     */
    @PostMapping("/insert")
    public Result insert(CfCertificateCollection cfCertificateCollection){

        return Result.success(cfCertificateCollectionService.insert(cfCertificateCollection));
    }

    /**
     * 批量增加催缴记录
     * @param cfCertificateCollection
     * @return
     */
    @PostMapping("/insertSelective")
    public Result insertSelective(CfCertificateCollection cfCertificateCollection){

        return Result.success(cfCertificateCollectionService.insertSelective(cfCertificateCollection));

    }

    /**
     * 条件查询所有催缴记录
     * @param cfCertificateCollection
     * @return
     */
    @GetMapping("/selectByCfcertificateCollection")
    public Result selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection){

        return Result.success(cfCertificateCollectionService.selectByCfcertificateCollection(cfCertificateCollection));
    }
}