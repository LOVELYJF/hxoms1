package com.hxoms.modules.passportCard.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.service.CfCertificateCollectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/CfCertificateCollection")
public class CfCertificateCollectionController {

    private CfCertificateCollectionService cfCertificateCollectionService;


    /**
     * 单条增加
     * @param cfCertificateCollection
     * @return
     */
    @PostMapping("/insert")
    public Result insert(CfCertificateCollection cfCertificateCollection){

        return Result.success(cfCertificateCollectionService.insert(cfCertificateCollection));
    }

    /**
     * 批量增加
     * @param cfCertificateCollection
     * @return
     */
    @PostMapping("/insertSelective")
    public Result insertSelective(CfCertificateCollection cfCertificateCollection){

        return Result.success(cfCertificateCollectionService.insertSelective(cfCertificateCollection));

    }

    /**
     * 条件查询所有
     * @param cfCertificateCollection
     * @return
     */
    @GetMapping("/selectByCfcertificateCollection")
    public Result selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection){

        return Result.success(cfCertificateCollectionService.selectByCfcertificateCollection(cfCertificateCollection));
    }
}
