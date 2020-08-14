package com.hxoms.modules.passportCard.certificateCollect.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjQueryParam;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCollectionRequestEx;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCollectionRequestParam;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionRequestService;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @Desc: 查询证照催缴
     * @Author: wangyunquan
     * @Param: [cfCertificateCjQuery]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/12
     */
    @GetMapping("/selectCerCjApply")
    public Result selectCerCjApply(PageBean pageBean, CfCertificateCjQueryParam cfCertificateCjQueryParam){
        return Result.success(cfCertificateCollectionService.selectCerCjApply(pageBean, cfCertificateCjQueryParam));
    }

    /**
     * @Desc: 查询催缴机构单位
     * @Author: wangyunquan
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/12
     */
    @GetMapping("/selectOrganUnit")
    public Result selectOrganUnit(){
        return Result.success(cfCertificateCollectionService.selectOrganUnit());
    }

    /**
     * @Desc: 通过单位查询催缴人员
     * @Author: wangyunquan
     * @Param: [rfB0000]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/12
     */
    @GetMapping("/selectCerCjInfoByOrgan")
    public Result selectCerCjInfoByOrgan(String rfB0000){
        return Result.success(cfCertificateCollectionService.selectCerCjInfoByOrgan(rfB0000));
    }

    /**
     * @Desc: 保存催缴结果
     * @Author: wangyunquan
     * @Param: [cerCollectionRequestList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/12
     */
    @PostMapping("/insertCerCjResult")
    public Result insertCerCjResult(@RequestBody  List<CfCertificateCollectionRequestEx> cerCollectionRequestExList){
        cfCertificateCollectionService.insertCerCjResult(cerCollectionRequestExList);
        return  Result.success();
    }

    /**
     * @Desc: 解除催缴
     * @Author: wangyunquan
     * @Param: [cfCertificateCollectionList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/12
     */
    @PostMapping("/updateCerCjForRemove")
    public Result updateCerCjForRemove(@RequestBody List<CfCertificateCollection> cfCertificateCollectionList){
        cfCertificateCollectionService.updateCerCjForRemove(cfCertificateCollectionList);
        return Result.success();
    }

    @PostMapping("/sendCjNotice")
    public Result sendCjNotice(){
        return Result.success();
    }

    /**
     * @Desc: 锁定单位出国
     * @Author: wangyunquan
     * @Param: [omsSupSuspendUnit]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/13
     */
    public Result insertSuspendUnit(OmsSupSuspendUnit omsSupSuspendUnit){
        cfCertificateCollectionService.insertSuspendUnit(omsSupSuspendUnit);
        return Result.success();
    }
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
     * 条件查询所有催缴记录
     * @param cfCertificateCollection
     * @return
     */
    @GetMapping("/selectByCfcertificateCollection")
    public Result selectByCfcertificateCollection(CfCertificateCollection cfCertificateCollection){

        return Result.success(cfCertificateCollectionService.selectByCfcertificateCollection(cfCertificateCollection));
    }

}
