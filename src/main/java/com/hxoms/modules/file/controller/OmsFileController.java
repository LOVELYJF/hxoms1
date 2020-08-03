package com.hxoms.modules.file.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import com.hxoms.modules.file.service.OmsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * @desc: 材料管理
 * @author: lijing
 * @date: 2020-06-12
 */
@RestController
@RequestMapping("/omsFile")
public class OmsFileController {

    @Autowired
    private OmsFileService omsFileService;

    /**
     * 文件列表
     * @param tableCode 类型（因公 因私 延期回国）
     * @param a0100 出国人
     * @param applyId 申请id
     * @return
     */
    @GetMapping("/selectFileListByCode")
    public Result selectFileListByCode(String tableCode, String a0100, String applyId) {
        List<OmsFile> list = omsFileService.selectFileListByCode(tableCode, a0100, applyId);
        return Result.success(list);
    }

    /**
     * 查询富文本文件详情
     *
     */
    @GetMapping("/selectFileDestail")
    public Result selectFileDestail(AbroadFileDestailParams broadFileDestailParams){
        Map<String, Object> result = omsFileService.selectFileDestail(broadFileDestailParams);
        return Result.success(result);
    }

    /**
     * 文件类型下载
     *
     */
    @GetMapping("/downloadOmsFile")
    public void downloadOmsFile(AbroadFileDestailParams abroadFileDestailParams) throws Exception {
        omsFileService.downloadOmsFile(abroadFileDestailParams);
    }

    /**
     * 保存富文本文件
     * @param omsFile
     * @return
     * @throws Exception
     */
    @PostMapping("/saveTextOmsFile")
    public Result saveTextOmsFile(OmsFile omsFile) throws Exception {
        String result = omsFileService.saveTextOmsFile(omsFile);
        return Result.success().setMsg(result);
    }
    /**
     * 重新生成内容
     * @param fileId 文件id
     */
    @GetMapping("/selectFileDestailNew")
    public Result selectFileDestailNew(String fileId, String applyId, String tableCode){
        OmsFile omsFile = omsFileService.selectFileDestailNew(fileId, applyId, tableCode);
        return Result.success(omsFile);
    }

}
