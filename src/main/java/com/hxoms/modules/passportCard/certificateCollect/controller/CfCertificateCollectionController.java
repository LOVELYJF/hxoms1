package com.hxoms.modules.passportCard.certificateCollect.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionRequestService;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "证照催缴")
@RestController
@RequestMapping("/cerCollection")
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
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjInfo>>
     * @Date: 2020/8/12
     */
    @ApiOperation(value = "查询证照催缴申请")
    @GetMapping("/selectCerCjApply")
    public Result<PageBean<CfCertificateCjInfo>> selectCerCjApply(PageBean pageBean, CfCertificateCjQueryParam cfCertificateCjQueryParam){
        return Result.success(cfCertificateCollectionService.selectCerCjApply(pageBean, cfCertificateCjQueryParam));
    }

    /**
     * @Desc: 查询催缴机构单位
     * @Author: wangyunquan
     * @Param: []
     * @Return: com.hxoms.common.utils.Result<java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.OrganUnit>>
     * @Date: 2020/8/12
     */
    @ApiOperation(value = "查询催缴机构单位")
    @GetMapping("/selectOrganUnit")
    public Result<List<OrganUnit>> selectOrganUnit(){
        return Result.success(cfCertificateCollectionService.selectOrganUnit());
    }

    /**
     * @Desc: 通过单位查询催缴人员
     * @Author: wangyunquan
     * @Param: [rfB0000]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.CfCertificateCjByPhone>
     * @Date: 2020/8/12
     */
    @ApiOperation(value = "通过单位查询催缴人员")
    @ApiImplicitParam(value = "机构单位编码",name = "rfB0000",required = true)
    @GetMapping("/selectCerCjInfoByOrgan")
    public Result<CfCertificateCjByPhone> selectCerCjInfoByOrgan(String rfB0000){
        return Result.success(cfCertificateCollectionService.selectCerCjInfoByOrgan(rfB0000));
    }

    /**
     * @Desc: 获取电话催缴内容
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: com.hxoms.common.utils.Result<java.lang.String>
     * @Date: 2020/9/1
     */
    @ApiOperation(value = "获取电话催缴内容")
    @PostMapping("/createPhoneContent")
    public Result<String> createPhoneContent(@RequestBody RequestList<PhoneContentParam> requestList){
        return Result.success(cfCertificateCollectionService.createPhoneContent(requestList.getList()));
    }
    /**
     * @Desc: 保存催缴结果
     * @Author: wangyunquan
     * @Param: [cerCollectionRequestList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/12
     */
    @ApiOperation(value = "保存催缴结果")
    @PostMapping("/insertCerCjResult")
    public Result insertCerCjResult(@RequestBody RequestList<CfCertificateCollectionRequestEx> requestList){
        cfCertificateCollectionService.insertCerCjResult(requestList.getList());
        return  Result.success();
    }

    /**
     * @Desc: 解除催缴
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/12
     */
    @ApiOperation(value = "解除催缴")
    @PostMapping("/updateCerCjForRemove")
    public Result updateCerCjForRemove(@RequestBody RequestList<RemoveCjApply> requestList){
        cfCertificateCollectionService.updateCerCjForRemove(requestList.getList());
        return Result.success();
    }

    /**
     * @Desc: 发送催缴通知
     * @Author: wangyunquan
     * @Param: [omsSupSuspendUnit]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/13
     */
    @ApiOperation(value = "发送催缴通知")
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
    @ApiOperation(value = "锁定单位出国申请")
    @PostMapping("/insertSuspendUnit")
    public Result insertSuspendUnit(@RequestBody SupSuspendUnitApply supSuspendUnitApply){
        cfCertificateCollectionService.insertSuspendUnit(supSuspendUnitApply);
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
