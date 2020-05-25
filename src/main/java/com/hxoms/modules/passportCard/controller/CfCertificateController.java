package com.hxoms.modules.passportCard.controller;


import com.hxoms.common.util.Excel.PersonExcelToDB;
import com.hxoms.common.utils.Result;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/cfCertificate")
public class CfCertificateController {


    /**
     * 导入Excel人员名单
     */
    @PostMapping("/excelToDB")
    public Result personExcelToDB(@RequestParam("file") MultipartFile file) throws Exception {
        System.out.println(file);

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;

            ins = file.getInputStream();

            toFile = new File(file.getOriginalFilename());
            System.out.println(toFile);
        }


        System.out.println(file);
        boolean falg = PersonExcelToDB.ExcelToDB(toFile);
        if (falg) {
            return Result.success("操作成功");
        } else {
            return Result.error("操作失败");
        }
    }

    @GetMapping("/add")
    public Result add() {
        System.out.println("测试成功");
        return Result.success("操作成功");
    }
}
