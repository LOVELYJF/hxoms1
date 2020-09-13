package com.hxoms.modules.passportCard.certificateCollect.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollectionRequest;
import com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionRequestService;
import com.hxoms.modules.passportCard.certificateCollect.service.CfCertificateCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Api(tags = "证照催缴")
@RestController
@RequestMapping("/cerCollection")
@Validated
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
    @ApiImplicitParam(value = "机构单位编码",name = "rfB0000",required = true,paramType = "query")
    @GetMapping("/selectCerCjInfoByOrgan")
    public Result<CfCertificateCjByPhone> selectCerCjInfoByOrgan(@NotBlank(message = "机构单位编码不能为空") String rfB0000){
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
    public Result<PhoneContent> createPhoneContent(@RequestBody @Validated RequestList<CjContentParam> requestList){
        return Result.success(cfCertificateCollectionService.createPhoneContent(requestList.getList()));
    }
    /**
     * @Desc: 保存催缴结果（电话催缴）
     * @Author: wangyunquan
     * @Param: [cerCollectionRequestList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/12
     */
    @ApiOperation(value = "保存催缴结果（电话催缴）")
    @PostMapping("/insertCerCjResult")
    public Result insertCerCjResult(@RequestBody @Validated RequestList<CfCertificateCollectionRequestEx> requestList){
        cfCertificateCollectionService.insertCerCjResult(requestList.getList());
        return  Result.success();
    }
    /**
     * @Desc: 保存催缴结果（短信催缴）
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/9/4
     */
    @ApiOperation(value = "保存催缴结果（短信催缴）")
    @PostMapping("/updateCerCjResult")
    public Result updateCerCjResult(@RequestBody @Validated RequestList<SaveCjResult> requestList){
        cfCertificateCollectionService.updateCerCjResult(requestList.getList());
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
    public Result updateCerCjForRemove(@RequestBody @Validated RequestList<RemoveCjApply> requestList){
        cfCertificateCollectionService.updateCerCjForRemove(requestList.getList());
        return Result.success();
    }

    /**
     * @Desc: 获取导出短信催缴名单
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: com.hxoms.common.utils.Result<java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.ExportSMSCjInfo>>
     * @Date: 2020/9/3
     */
    @ApiOperation(value = "获取导出短信催缴名单")
    @PostMapping("/getExportSMSCjList")
    public Result<List<ExportSMSCjInfo>> getExportSMSCjList(@RequestBody  @Validated RequestList<SMSCjInfo> requestList){
        return Result.success(cfCertificateCollectionService.getExportSMSCjList(requestList.getList()));
    }

    @ApiOperation(value = "导出短信催缴名单")
    @PostMapping("/ExportSMSCjList")
    public void exportSMSCjList(@RequestBody @Validated ExportRequestPara exportRequestPara, HttpServletResponse response){
        try {
            cfCertificateCollectionService.exportSMSCjList(exportRequestPara,response.getOutputStream());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", URLEncoder.encode(UUIDGenerator.getPrimaryKey()+".xls", "utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomMessageException("导出失败，原因："+e.getMessage());
        }
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
    public Result sendCjNotice(@RequestBody @Validated  RequestList<SendNotice> requestList){
        cfCertificateCollectionService.sendCjNotice(requestList.getList());
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
    public Result insertSuspendUnit(@RequestBody @Validated SupSuspendUnitApply supSuspendUnitApply){
        cfCertificateCollectionService.insertSuspendUnit(supSuspendUnitApply);
        return Result.success();
    }

    @ApiOperation(value = "催缴记录查询")
    @ApiImplicitParam(value = "主键id",name = "id",required = true,paramType = "query")
    @GetMapping("/selectCjRecord")
    public Result<RequestList<CjRecord>> selectCjRecord(@NotBlank(message = "id不能为空") String id){
        return Result.success(cfCertificateCollectionRequestService.selectCjRecord(id));
    }

    /**
     * @Desc: 生成打印文件
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: com.hxoms.common.utils.Result<java.util.List<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.PrintFile>>
     * @Date: 2020/9/13
     */
    @ApiOperation(value="生成打印文件")
    @GetMapping("/createFileListByCode")
    public Result<List<PrintFile>> createFileListByCode(RequestList<FileQuery> requestList) {
        return Result.success(cfCertificateCollectionService.createFileListByCode(requestList.getList()));
    }

    /**
     * @Desc: 打印文件详情
     * @Author: wangyunquan
     * @Param: [fileDestailParams]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.modules.passportCard.certificateCollect.entity.parameterEntity.PrintFileDetail>
     * @Date: 2020/9/13
     */
    @ApiOperation(value="富文本文件详情")
    @GetMapping("/selectFileDestail")
    public Result<PrintFileDetail> selectFileDestail(FileDestailParams fileDestailParams){
        return Result.success(cfCertificateCollectionService.selectFileDestail(fileDestailParams));
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
