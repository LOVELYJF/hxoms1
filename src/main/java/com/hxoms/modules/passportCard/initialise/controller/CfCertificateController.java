package com.hxoms.modules.passportCard.initialise.controller;


import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.PageBean;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.counterReceive.entity.parameterEntity.CfCheckValidParam;
import com.hxoms.modules.passportCard.counterReceive.service.CfCheckValidService;
import com.hxoms.modules.passportCard.initialise.entity.CfCertificate;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificatePageParam;
import com.hxoms.modules.passportCard.initialise.entity.parameterEntity.CfCertificateReminderParam;
import com.hxoms.modules.passportCard.initialise.service.CfCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/cfCertificate")
public class CfCertificateController {


    @Autowired
    private CfCertificateService cfCertificateService;

    @Autowired
    private CfCheckValidService cfCheckValidService;


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
     * 查找过期证照
     * @param cfCertificateReminderParam
     * @return
     */
    @GetMapping("/findOverduePass")
    public Result findOverduePass(CfCertificateReminderParam cfCertificateReminderParam){

        return Result.success(cfCertificateService.findOverduePass(cfCertificateReminderParam));

    }


    /**
     * 删除护照信息
     * @return
     */
    @PostMapping("/delete")
    public Result delete(String id){

        return Result.success(cfCertificateService.delete(id));

    }

    /**
     * 查询已经验证的护照信息的总数
     * @return
     */
    @GetMapping("/findSuccessCf")
    public Result findSuccessCf(){
        return Result.success(cfCertificateService.findSuccessCf());
    }

    /**
     * 查看所有重复的有效证件
     * @param cfCheckValidParam
     * @return
     */
    @GetMapping("/selectCfCheckValid")
    public Result selectCfCheckValid(CfCheckValidParam cfCheckValidParam){
        return Result.success(cfCheckValidService.selectCfCheckValid(cfCheckValidParam));
    }

    /**
     * 查看有重复证件的人员
     * @return
     */
    @GetMapping("/selectCfCheckValidByName")
    public Result selectCfCheckValidByName(CfCheckValidParam cfCheckValidParam){
        return Result.success(cfCheckValidService.selectCfCheckValidByName(cfCheckValidParam));
    }


}