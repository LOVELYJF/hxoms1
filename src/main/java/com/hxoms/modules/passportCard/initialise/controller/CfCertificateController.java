package com.hxoms.modules.passportCard.initialise.controller;


import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateCollectionApplyList;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateValidate;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "初始化证照库")
@RestController
@RequestMapping("/cfCertificate")
public class CfCertificateController {


    @Autowired
    private CfCertificateService cfCertificateService;

    /**
     * @Desc: 初始化证照，导入公安的证照信息
     * @Author: wangyunquan
     * @Param: [multipartFile, dataSource]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>>
     * @Date: 2020/7/24
     */

    @ApiOperation(value = "初始化证照，导入公安的证照信息")
    @PostMapping("/excelToDB")
    public Result<PageBean<CfCertificate>> personExcelToDB(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return Result.success(cfCertificateService.excelToDB(multipartFile));
    }

    /**
     * @Desc: 查询所有证照
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>>
     * @Date: 2020/8/4
     */
    @ApiOperation(value = "查询所有证照")
    @GetMapping("/selectAllCertificate")
    public Result<PageBean<CfCertificate>> selectAllCertificate(PageBean pageBean) throws Exception {
        return Result.success(cfCertificateService.selectAllCertificate(pageBean));
    }

    /**
     * @Desc: 验证证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/4
     */
    @ApiOperation(value = "验证证照信息")
    @GetMapping("/validateCerInfo")
    public Result<CfCertificateValidate> validateCerInfo(CfCertificate cfCertificate){
        return Result.success(cfCertificateService.validateCerInfo(cfCertificate));
    }

    /**
     * @Desc: 保存证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificateGa, cfCertificateZz]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/5
     */
    @ApiOperation(value = "保存证照信息")
    @PostMapping("/insertCertificate")
    public Result insertCertificate(@RequestBody CfCertificate cfCertificate){
        cfCertificateService.insertCertificate(cfCertificate);
        return Result.success();
    }

    /**
     * @Desc: 未上缴证照统计
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>>
     * @Date: 2020/8/7
     */
    @ApiOperation(value = "未上缴证照统计")
    @GetMapping("/selectNotProvicdeCer")
    public Result<PageBean<CfCertificateInfo>> selectNotProvicdeCer(PageBean pageBean){
        return Result.success(cfCertificateService.selectNotProvicdeCer(pageBean));
    }

    /**
     * @Desc: 已上缴未入库统计
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>>
     * @Date: 2020/8/7
     */
    @ApiOperation(value = "已上缴未入库统计")
    @GetMapping("/selectProNotstorCer")
    public Result<PageBean<CfCertificateInfo>> selectProNotstorCer(PageBean pageBean){
        return Result.success(cfCertificateService.selectProNotstorCer(pageBean));
    }

    /**
     * @Desc: 存疑证照统计
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>>
     * @Date: 2020/8/7
     */
    @ApiOperation(value = "存疑证照统计")
    @GetMapping("/selectExceptionCer")
    public Result<PageBean<CfCertificateInfo>> selectExceptionCer(PageBean pageBean){
        return Result.success(cfCertificateService.selectExceptionCer(pageBean));
    }

    /**
     * @Desc: 公安已注销证照，更新状态
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/10
     */
    @ApiOperation(value = "公安已注销证照，更新状态")
    @PostMapping("/updateCerForCerIsCancel")
    public Result updateCerForCerIsCancel(@RequestBody CfCertificate cfCertificate){
        cfCertificateService.updateCerForCerIsCancel(cfCertificate);
        return Result.success();
    }

    /**
     * @Desc: 生成催缴任务
     * @Author: wangyunquan
     * @Param: [cfCertificateCollectionList]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/11
     */
    @ApiOperation(value = "生成催缴任务")
    @PostMapping("/createCjTask")
    public Result createCjTask(@RequestBody CfCertificateCollectionApplyList cfCertificateCollectionApplyList){
        cfCertificateService.createCjTask(cfCertificateCollectionApplyList);
        return Result.success();
    }
    /**
     * @Desc: 存疑处理，以证照信息为准。
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/10
     */
    @ApiOperation(value = "存疑处理，以证照信息为准")
    @PostMapping("/updateCerForCerIsRight")
    public Result updateCerForCerIsRight(@RequestBody CfCertificate cfCertificate){
        cfCertificateService.updateCerForCerIsRight(cfCertificate);
        return Result.success();
    }

    /**
     * @Desc: 存疑处理，以公安信息为准。
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/10
     */
    @ApiOperation(value = "存疑处理，以公安信息为准")
    @PostMapping("/updateCerForGaInfoIsRight")
    public Result updateCerForGaInfoIsRight(@RequestBody CfCertificate cfCertificate){
        cfCertificateService.updateCerForGaInfoIsRight(cfCertificate);
        return Result.success();
    }
    /**
     * 条件查询所有
     * @param cfCertificatePageParam
     * @return
     */
    @GetMapping("/selectCfCertificate")
    public Result selectCfCertificate(CfCertificatePageParam cfCertificatePageParam){
        PageInfo<CfCertificate> cfCertificatePageInfo = cfCertificateService.selectCfCertificateIPage(cfCertificatePageParam);
        return Result.success(cfCertificatePageInfo);
    }


    /**
     * <b>功能描述: 查询证照状态</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    @GetMapping("/getCfCertificateStatus")
    public Result getCfCertificateStatus(){
        List<SysDictItem> list = cfCertificateService.getCfCertificateStatus();
        return Result.success(list);
    }


    /**
     * <b>功能描述: 查询证照类型</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    @GetMapping("/getCfCertificateType")
    public Result getCfCertificateType(){
        List<SysDictItem> list = cfCertificateService.getCfCertificateType();
        return Result.success(list);
    }


    /**
     * <b>功能描述: 查询证照形式</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    @GetMapping("/getCfCertificateForm")
    public Result getCfCertificateForm(){
        List<SysDictItem> list = cfCertificateService.getCfCertificateForm();
        return Result.success(list);
    }


    /**
     * <b>功能描述: 查询证照保管状态</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    @GetMapping("/getCfCertificateSaveStatus")
    public Result getCfCertificateSaveStatus(){
        List<SysDictItem> list = cfCertificateService.getCfCertificateSaveStatus();
        return Result.success(list);
    }


    /**
     * <b>功能描述: 查询证照保管单位</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    @GetMapping("/getCfCertificateSaveCompany")
    public Result getCfCertificateSaveCompany(){
        List<SysDictItem> list = cfCertificateService.getCfCertificateSaveCompany();
        return Result.success(list);
    }


    /**
     * <b>功能描述: 查询证照保管方式</b>
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: luoshuai
     * @Date: 2020/8/27 14:41
     */
    @GetMapping("/getCfCertificateSaveWay")
    public Result getCfCertificateSaveWay(){
        List<SysDictItem> list = cfCertificateService.getCfCertificateSaveWay();
        return Result.success(list);
    }
}