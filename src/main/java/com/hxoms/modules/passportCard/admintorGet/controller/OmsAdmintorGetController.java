package com.hxoms.modules.passportCard.admintorGet.controller;


import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.*;
import com.hxoms.modules.passportCard.admintorGet.service.OmsAdmintorGetService;
import com.hxoms.modules.passportCard.printGetQrCode.entity.parameterEntity.QrCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.IOException;


/**
 * @Desc: TODO
 * @Author: wangyunquan
 * @Param:
 * @Return:
 * @Date: 2020/8/14
 */
@Api(tags = "管理员取证")
@RestController
@RequestMapping("/admintorGet")
@Validated
public class OmsAdmintorGetController {

    @Autowired
    private OmsAdmintorGetService omsAdmintorGetService;

    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [pageBean, admintorGetQueryParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/18
     */
    @ApiOperation(value = "查询证照信息")
    @GetMapping("/selectCerInfo")
    public Result<PageBean<AdmintorGetCerInfo>> selectCerInfo(PageBean pageBean, AdmintorGetQueryParam admintorGetQueryParam){
        return Result.success(omsAdmintorGetService.selectCerInfo(pageBean,admintorGetQueryParam));
    }

    /**
     * @Desc: 查询人员证照
     * @Author: wangyunquan
     * @Param: [omsId]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.RequestList<com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.PersonInfo>>
     * @Date: 2020/9/14
     */
    @ApiOperation(value = "查询人员证照")
    @ApiImplicitParam(value = "备案表id",name = "omsId",required = true,paramType = "query")
    @GetMapping("/selectInfoByName")
    public Result<RequestList<PersonInfo>> selectInfoByOmsId(@NotBlank(message = "omsId不能为空") String omsId){
        return Result.success(omsAdmintorGetService.selectInfoByOmsId(omsId));
    }

   /**
    * @Desc: 保存管理员取证申请并打印二维码
    * @Author: wangyunquan
    * @Param: [admintorGetApplyList]
    * @Return: com.hxoms.common.utils.Result
    * @Date: 2020/9/14
    */
    @ApiOperation(value = "保存管理员取证申请并打印二维码")
    @PostMapping("/insertAdmintorGetApply")
    public Result<QrCode> insertAdmintorGetApply(@RequestBody @Validated RequestList<AdminGetCerApply> admintorGetApplyList){
        return Result.success(omsAdmintorGetService.insertAdmintorGetApply(admintorGetApplyList.getList()));
    }

    /**
     * @Desc: 打印二维码
     * @Author: wangyunquan
     * @Param: [requestList]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.GetCerInfoAndQrCode>
     * @Date: 2020/9/15
     */
    @ApiOperation(value = "打印二维码")
    @PostMapping("/createPrintQrCode")
    public Result<GetCerInfoAndQrCode> createPrintQrCode(@RequestBody @Validated RequestList<PrintQrCodeParams> requestList){
        try {
            return Result.success(omsAdmintorGetService.createPrintQrCode(requestList.getList()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomMessageException("二维码生成失败，原因："+e.getMessage());
        }
    }
}
