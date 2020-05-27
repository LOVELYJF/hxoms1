package com.hxoms.modules.file.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.paramentity.AbroadAskFileParams;
import com.hxoms.modules.file.service.OmsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/omsFile")
public class OmsFileController {

    @Autowired
    private OmsFileService omsFileService;

    /**
     * 根据业务表编码查询文件列表
     *
     * @author sunqian
     * @date 2020/5/7 14:17
     */
    @GetMapping("/selectFileListByCode")
    public Result selectFileListByCode(String tableCode) {
        List<OmsFile> list = omsFileService.selectFileListByCode(tableCode);
        return Result.success(list);
    }

    /**
     * 文件上传(单文件)
     *
     * @author sunqian
     * @date 2020/5/7 17:07
     */
    @PostMapping("/uploadOmsFile")
    public Result uploadOmsFile(MultipartFile file, OmsFile omsFile) throws IOException {
        omsFileService.uploadOmsFile(file, omsFile);
        return Result.success();
    }

    /**
     * 根据id删除文件
     *
     * @author sunqian
     * @date 2020/5/8 11:28
     */
    @PostMapping("/deleteOmsFile")
    public Result deleteOmsFile(String id) {
        omsFileService.deleteOmsFile(id);
        return Result.success();
    }

    /**
     * 下载文件
     * 根据申请的记录进行下载
     *
     * @author sunqian
     * @date 2020/5/8 14:34
     */
    @GetMapping("/downloadOmsFile")
    public void downloadOmsFile(String fileId, String applyId) throws Exception {
        omsFileService.downloadOmsFile(fileId, applyId);
//        return Result.success();
    }

    /**
     * 查询请示文件
     *
     */
    public void selectAbroadAskFile(AbroadAskFileParams abroadAskFileParams){
        Map<String, Object> result = omsFileService.selectAbroadAskFile(abroadAskFileParams);
    }
}
