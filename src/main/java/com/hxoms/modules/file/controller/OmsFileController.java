package com.hxoms.modules.file.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.OmsSealhandleRecords;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import com.hxoms.modules.file.entity.OmsFileVO;
import com.hxoms.modules.file.service.OmsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/omsFile")
public class OmsFileController {

    @Autowired
    private OmsFileService omsFileService;

    /**
     * 文件列表
     * @param tableCode 类型（因公 因私 延期回国）
     * @return
     */
    @GetMapping("/selectFileListByCode")
    public Result selectFileListByCode(String tableCode) {
        List<OmsFile> list = omsFileService.selectFileListByCode(tableCode);
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
     * 签字盖章列表
     * @param tableCode
     * @param applyId
     * @return
     */
    @GetMapping("selectSealHandleList")
    public Result selectSealHandleList(String tableCode, String applyId){
        List<OmsFileVO> omsFileVOs = omsFileService.selectSealHandleList(tableCode, applyId);
        return Result.success(omsFileVOs);
    }

    /**
     * 保存签字盖章
     * @param omsSealhandleRecords
     * @return
     * @throws Exception
     */
    @PostMapping("/saveSealHandle")
    public Result saveSealHandle(List<OmsSealhandleRecords> omsSealhandleRecords) throws Exception {
        String result = omsFileService.saveSealHandle(omsSealhandleRecords);
        return Result.success().setMsg(result);
    }
}
