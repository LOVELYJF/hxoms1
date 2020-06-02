package com.hxoms.modules.passportCard.controller;


import com.hxoms.common.util.Excel.PersonExcelToDB;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.passportCard.entity.vo.CfCertificateVo;
import com.hxoms.modules.passportCard.service.CfCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cfCertificate")
public class CfCertificateController {


    @Autowired
    private CfCertificateService cfCertificateService;


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
     * @param cfCertificateVo
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(CfCertificateVo cfCertificateVo){

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("data",cfCertificateService.findAll(cfCertificateVo));
        map.put("total",cfCertificateService.findAllCount(cfCertificateVo));
        return Result.success(map);
    }


    /**
     * 保存或者修改证照信息
     * @param cfCertificateVo
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(CfCertificateVo cfCertificateVo){

        return Result.success(cfCertificateService.saveOrUpdate(cfCertificateVo));

    }


    /**
     * 删除护照信息
     * @return
     */
    @PostMapping("/delete")
    public Result delete(String id){

        return Result.success(cfCertificateService.delete(id));

    }

}
