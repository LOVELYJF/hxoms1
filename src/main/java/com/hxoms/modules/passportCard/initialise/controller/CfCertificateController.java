package com.hxoms.modules.passportCard.initialise.controller;


import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.certificateCollect.entity.CfCertificateCollection;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/cfCertificate")
public class CfCertificateController {


    @Autowired
    private CfCertificateService cfCertificateService;

    /**
     * @Desc: 初始化证照，导入公安的证照信息
     * @Author: wangyunquan
     * @Param: [multipartFile, dataSource]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/7/24
     */
    @PostMapping("/excelToDB")
    public Result personExcelToDB(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return Result.success(cfCertificateService.excelToDB(multipartFile));
    }

    /**
     * @Desc: 查询所有证照
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/4
     */
    @GetMapping("/selectAllCertificate")
    public Result selectAllCertificate(PageBean pageBean) throws Exception {
        return Result.success(cfCertificateService.selectAllCertificate(pageBean));
    }

    /**
     * @Desc: 验证证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/4
     */
    @GetMapping("/validateCerInfo")
    public Result validateCerInfo(CfCertificate cfCertificate){
        return Result.success(cfCertificateService.validateCerInfo(cfCertificate));
    }

    /**
     * @Desc: 插入证照信息
     * @Author: wangyunquan
     * @Param: [cfCertificateGa, cfCertificateZz]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/5
     */
    @PostMapping("/insertCertificate")
    public Result insertCertificate(CfCertificate cfCertificate){
        cfCertificateService.insertCertificate(cfCertificate);
        return Result.success();
    }

    /**
     * @Desc: 未上缴证照统计
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/7
     */
    @GetMapping("/selectNotProvicdeCer")
    public Result selectNotProvicdeCer(PageBean pageBean){
        return Result.success(cfCertificateService.selectNotProvicdeCer(pageBean));
    }

    /**
     * @Desc: 已上缴未入库统计
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/7
     */
    @GetMapping("/selectProNotstorCer")
    public Result selectProNotstorCer(PageBean pageBean){
        return Result.success(cfCertificateService.selectProNotstorCer(pageBean));
    }

    /**
     * @Desc: 存疑证照统计
     * @Author: wangyunquan
     * @Param: [pageBean]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/7
     */
    @GetMapping("/selectExceptionCer")
    public Result selectExceptionCer(PageBean pageBean){
        return Result.success(cfCertificateService.selectExceptionCer(pageBean));
    }

    /**
     * @Desc: 公安已注销证照，更新状态
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/10
     */
    @PatchMapping("/updateCerForCerIsCancel")
    public Result updateCerForCerIsCancel(CfCertificate cfCertificate){
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
    @PostMapping("/createCjTask")
    public Result createCjTask(List<CfCertificateCollection> cfCertificateCollectionList){
        cfCertificateService.createCjTask(cfCertificateCollectionList);
        return Result.success();
    }
    /**
     * @Desc: 存疑处理，以证照信息为准。
     * @Author: wangyunquan
     * @Param: [cfCertificate]
     * @Return: com.hxoms.common.utils.Result
     * @Date: 2020/8/10
     */
    @PostMapping("/updateCerForCerIsRight")
    public Result updateCerForCerIsRight(CfCertificate cfCertificate){
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
    @PostMapping("/updateCerForGaInfoIsRight")
    public Result updateCerForGaInfoIsRight(CfCertificate cfCertificate){
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


}