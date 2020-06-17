package com.hxoms.modules.passportCard.controller;


import com.github.pagehelper.PageInfo;
import com.hxoms.common.util.Excel.PersonExcelToDB;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.entity.CfCertificate;
import com.hxoms.modules.passportCard.entity.CfCertificateReminder;
import com.hxoms.modules.passportCard.entity.param.CfCertificatePageParam;
import com.hxoms.modules.passportCard.entity.param.CfCertificateReminderParam;
import com.hxoms.modules.passportCard.entity.param.CfCheckValidParam;
import com.hxoms.modules.passportCard.service.CfCertificateService;
import com.hxoms.modules.passportCard.service.CfCheckValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;



@RestController
@RequestMapping("/cfCertificate")
public class CfCertificateController {


    @Autowired
    private CfCertificateService cfCertificateService;

    @Autowired
    private CfCheckValidService cfCheckValidService;


    /**
     * 导入Excel人员名单
     */
    @PostMapping("/excelToDB")
    public Result personExcelToDB(@RequestParam("file") MultipartFile file,String dataSource, String importPerson) throws Exception {

        InputStream inputStream = null;

        if (file.equals("") || file.getSize() <= 0 ) {

            file = null;

            return Result.error("操作失败");

        } else {

            inputStream = file.getInputStream();

        }

        boolean falg = PersonExcelToDB.ExcelToDB(inputStream,"出入境管理局","管理员");

        if (falg) {

            return Result.success("操作成功");

        } else {

            return Result.error("操作失败");

        }

    }

    /**
     * 条件查询所有
     * @param cfCertificatePageParam
     * @return
     */
    @GetMapping("/selectCfCertificate")
    public Result selectCfCertificate(CfCertificatePageParam cfCertificatePageParam){
        System.err.println("0000000000000000000000000000000000000000");
        PageInfo<CfCertificate> cfCertificatePageInfo = cfCertificateService.selectCfCertificateIPage(cfCertificatePageParam);

        return Result.success(cfCertificatePageInfo);
    }


    /**
     * 保存或者修改证照信息
     * 如果是证照对于模块，对比完成之后，
     * 如果正常，传入ID 保存单位，状态改成已取出
     * 如果不正常，传入ID ，异常信息,状态改成以取出
     * @param cfCertificate
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(CfCertificate cfCertificate){

        return Result.success(cfCertificateService.saveOrUpdate(cfCertificate));

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