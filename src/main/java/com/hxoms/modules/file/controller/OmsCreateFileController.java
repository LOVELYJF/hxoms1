package com.hxoms.modules.file.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.service.OmsCreateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @desc: 生成材料
 * @author: lijing
 * @date: 2020-06-12
 */
@RestController
@RequestMapping("/omsCreateFile")
public class OmsCreateFileController {

    @Autowired
    private OmsCreateFileService omsCreateFileService;

    /**
     * 生成文件列表
     * @param tableCode 类型（因公 因私 延期回国）
     * @param applyId 申请Id
     * @return
     */
    @GetMapping("/selectCreateFileList")
    public Result selectCreateFileList(String tableCode, String applyId) {
        List<OmsCreateFile> list = omsCreateFileService.selectCreateFileList(tableCode, applyId);
        return Result.success(list);
    }

    /**
     * 保存或者更新
     *
     */
    @PostMapping("/insertOrUpdate")
    public Result insertOrUpdate(OmsCreateFile omsCreateFile){
        String result = omsCreateFileService.insertOrUpdate(omsCreateFile);
        return Result.success().setMsg(result);
    }
}
