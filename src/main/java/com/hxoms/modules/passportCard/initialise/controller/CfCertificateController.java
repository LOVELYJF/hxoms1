package com.hxoms.modules.passportCard.initialise.controller;


import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificateHistoryRecord;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.*;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateHistoryRecordService;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

@Api(tags = "初始化证照库")
@RestController
@RequestMapping("/cfCertificate")
@Validated
public class CfCertificateController {

    @Autowired
    private CfCertificateService cfCertificateService;
    @Autowired
    private CfCertificateHistoryRecordService cfHistoryRecordService;


    /**
     * @Desc: 初始化证照，导出存疑证照统计-导出证照查询
     * @Author: wuqingfan
     * @Param: [ids]
     * @Return: excel
     * @Date: 2020/9/10
     */
    @ApiOperation(value = "证照管理-导出证照查询表（涉及到的模板可共用）")
    @ApiImplicitParam(value = "选中列表ID，利用','隔开拼接", name = "ids", required = true, paramType = "query")
    @PostMapping("/exportExceptionCer")
    public void exportExceptionCer(@ApiIgnore @NotBlank(message = "ids不能为空") String ids, @ApiIgnore HttpServletResponse response) {
        cfCertificateService.exportExceptionCer(Arrays.asList(ids.split(",")),response);
    }

    /**
     * @Desc: 初始化证照，导出存疑证照统计-导出证照查询
     * @Author: wuqingfan
     * @Param: [ids]
     * @Return: excel
     * @Date: 2020/9/10
     */
    @ApiOperation(value = "证照管理-导出证照查询表omsID（涉及到的模板可共用）")
    @ApiImplicitParam(value = "选中列表中的omsId，利用','隔开拼接", name = "ids", required = true, paramType = "query")
    @PostMapping("/exportExceptionCerForOmsId")
    public void exportExceptionCerForOmsId(@ApiIgnore @NotBlank(message = "ids不能为空") @RequestBody String ids, @ApiIgnore HttpServletResponse response) {
        List<String> idss = null;
        if (!StringUtils.isBlank(ids)){
            idss = Arrays.asList(ids.split(","));
        }
        cfCertificateService.exportExceptionCerForOmsId(idss,response);
    }

    /**
     * @Desc: 导出未上缴证照统计
     * @Author: wuqingfan
     * @Param: [pageBean]
     * @Return:EXCEL
     * @Date: 2020/9/10
     */
    @ApiOperation(value = "导出未上缴证照统计")
    @ApiImplicitParam(value = "选中列表ID，利用','隔开拼接",name = "ids",required = true,paramType = "query")
    @PostMapping("/exportNotProvicdeCer")
    public void  exportNotProvicdeCer(@ApiIgnore @NotBlank(message = "ids不能为空") String ids, @ApiIgnore HttpServletResponse response){
        cfCertificateService.exportNotProvicdeCer(Arrays.asList(ids.split(",")),response);
    }



    /**
     * @Desc: 初始化证照，导入公安的证照信息
     * @Author: wangyunquan
     * @Param: [multipartFile, dataSource]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>>
     * @Date: 2020/7/24
     */

    @ApiOperation(value = "初始化证照，导入公安的证照信息")
    @ApiImplicitParam(value = "年度",name = "year",dataType = "String",paramType = "query")
    @PostMapping("/excelToDB")
    public Result<PageBean<ImportInterface>> personExcelToDB(@RequestParam("file") MultipartFile multipartFile,String year) throws Exception {
        return Result.success(cfCertificateService.excelToDB(multipartFile,year));
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
    public Result<PageBean<ImportInterface>> selectAllCertificate(PageBean pageBean) throws Exception {
        return Result.success(cfCertificateService.selectAllCertificate(pageBean));
    }


    /**
     * @Desc: 查询所有证照根据机构
     * @Author: lijiaojiao
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.CfCertificate>>
     * @Date: 2020/9/9
     */
    @ApiOperation(value = "查询所有证照根据机构")
    @PostMapping("/queryCertificateByOmsId")
    public Result<PageBean<ImportInterface>> queryCertificateByOmsId(PageBean pageBean,String b0100) throws Exception {
        return Result.success(cfCertificateService.queryCertificateByOmsId(pageBean,b0100));
    }
    /**
     * @Desc: 查询证照个数
     * @Author: wangyunquan
     * @Param: [validateCerInfoParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/4
     */
    @ApiOperation(value = "查询证照个数")
    @GetMapping("/selectCerCount")
    public Result<CerTotalCount> selectCerCount(){
        return Result.success(cfCertificateService.selectCerCount());
    }

    /**
     * @Desc: 验证证照信息
     * @Author: wangyunquan
     * @Param: [validateCerInfoParam]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/4
     */
    @ApiOperation(value = "验证证照信息")
    @PostMapping("/validateCerInfo")
    public Result<CfCertificateValidate> validateCerInfo(@RequestBody @Validated ValidateCerInfo validateCerInfo){
        CfCertificateValidate cfCertificateValidate = cfCertificateService.validateCerInfo(validateCerInfo);
        return Result.success(cfCertificateValidate).setMsg(cfCertificateValidate.getMessage());
    }

    /**
     * @Desc: 保存证照信息
     * @Author: wangyunquan
     * @Param: [validateCerSave]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/5
     */
    @ApiOperation(value = "保存证照信息")
    @PostMapping("/insertCertificate")
    public Result insertCertificate(@RequestBody @Validated ValidateCerSave validateCerSave){
        CfCertificate certificate=new CfCertificate();
        BeanUtils.copyProperties(validateCerSave,certificate);
        cfCertificateService.insertCertificate(certificate);
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
    @ApiImplicitParam(value = "年度",name = "year",dataType = "String",paramType = "query")
    @GetMapping("/selectNotProvicdeCer")
    public Result<PageBean<CfCertificateInfo>> selectNotProvicdeCer(PageBean pageBean,String year){
        return Result.success(cfCertificateService.selectNotProvicdeCer(pageBean,year));
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
    @ApiImplicitParam(value = "年度",name = "year",dataType = "String",paramType = "query")
    @GetMapping("/selectExceptionCer")
    public Result<PageBean<CfCertificateInfo>> selectExceptionCer(PageBean pageBean,String year){
        return Result.success(cfCertificateService.selectExceptionCer(pageBean,year));
    }

    /**
     * @Desc: 公安已注销证照，更新状态
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/10
     */
    @ApiOperation(value = "公安已注销证照，更新状态")
    @PostMapping("/updateCerForCerIsCancel")
    @ApiImplicitParam(value = "数据列表id",name = "id",required = true,paramType = "query")
    public Result updateCerForCerIsCancel( @NotBlank(message = "数据列表id不能为空") String id){
        cfCertificateService.updateCerForCerIsCancel(id);
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
    public Result createCjTask(@RequestBody  @Validated CfCertificateCollectionApplyList cfCertificateCollectionApplyList){
        cfCertificateService.createCjTask(cfCertificateCollectionApplyList);
        return Result.success();
    }
    /**
     * @Desc: 存疑处理，以证照信息为准。
     * @Author: wangyunquan
     * @Param: [qureyDealRequestInfoEx]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/10
     */
    @ApiOperation(value = "存疑处理，以证照信息为准，保存处理结果")
    @PostMapping("/updateCerForCerIsRight")
    public Result updateCerForCerIsRight(@RequestBody  @Validated QureyDealRequestInfoEx qureyDealRequestInfoEx){
        cfCertificateService.updateCerForCerIsRight(qureyDealRequestInfoEx);
        return Result.success();
    }

    /**
     * @Desc: 存疑处理，以公安信息为准。
     * @Author: wangyunquan
     * @Param: [qureyDealRequestInfoEx]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/10
     */
    @ApiOperation(value = "存疑处理，以公安信息为准，置为未上缴")
    @PostMapping("/updateCerForGaInfoIsRight")
    public Result updateCerForGaInfoIsRight(@RequestBody @Validated  QureyDealRequestInfoEx qureyDealRequestInfoEx){
        cfCertificateService.updateCerForGaInfoIsRight(qureyDealRequestInfoEx);
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

    /**
     * 根据年度查询对应证照信息并保存对比结果到记录表中
     * @param year
     * @author lijiaojiao
     * @return
     */
    @ApiOperation(value = "年度查询对应证照信息并保存对比结果到记录表中")
    @ApiImplicitParam(value = "年度",name = "year",dataType = "String",paramType = "query")
    @PostMapping("/saveCfCertificateHistoryRecord")
    public Result saveCfCertificateHistoryRecord(String year){
        cfHistoryRecordService.saveCfCertificateHistoryRecord(year);
        return  Result.success();
    }

    /**
     * 根据年度查询证照历史记录表中的未上缴证照统计
     * @param year
     * @author lijiaojiao
     * @return
     */
    @ApiOperation(value = "未上缴证照统计")
    @ApiImplicitParam(value = "年度",name = "year",dataType = "String",paramType = "query")
    @GetMapping("/selectNotProvicdeCerRecord")
    public Result<PageBean<CfCertificateHistoryRecord>> selectNotProvicdeCerRecord(PageBean pageBean,String year){
        return Result.success(cfHistoryRecordService.selectNotProvicdeCerRecord(pageBean,year));
    }


    /**
     * @Desc: 历史记录表中的存疑证照统计
     * @Author: lijiaojiao
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result<com.hxoms.common.utils.PageBean<com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateInfo>>
     * @Date: 2020/8/7
     */
    @ApiOperation(value = "存疑证照统计")
    @ApiImplicitParam(value = "年度",name = "year",dataType = "String",paramType = "query")
    @GetMapping("/selectExceptionCerRecord")
    public Result<PageBean<CfCertificateHistoryRecord>> selectExceptionCerRecord(PageBean pageBean,String year){
        return Result.success(cfHistoryRecordService.selectExceptionCerRecord(pageBean,year));
    }
}