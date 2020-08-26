package com.hxoms.modules.file.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import com.hxoms.modules.file.service.OmsFileService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @desc: 材料管理
 * @author: lijing
 * @date: 2020-06-12
 */
@Api(tags="材料管理")
@RestController
@RequestMapping("/omsFile")
public class OmsFileController {

    @Autowired
    private OmsFileService omsFileService;

    /**
     * 文件列表
     * @param tableCode 类型（因公 因私 延期回国）
     * @param procpersonId 出国人
     * @param applyId 申请id
     * @return
     */
    @ApiOperation(value="文件列表", notes="根据类型查询文件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableCode", value = "类型（因公 因私 延期回国）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "procpersonId", value = "出国人申请备案id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String")
    })
    @GetMapping("/selectFileListByCode")
    public Result selectFileListByCode(String tableCode, String procpersonId, String applyId) {
        List<OmsFile> list = omsFileService.selectFileListByCode(tableCode, procpersonId, applyId);
        return Result.success(list);
    }

    /**
     * 查询富文本文件详情
     *
     */
    @ApiOperation(value="查询富文本文件详情", notes="查询富文本文件详情")
    @GetMapping("/selectFileDestail")
    public Result selectFileDestail(AbroadFileDestailParams broadFileDestailParams){
        Map<String, Object> result = omsFileService.selectFileDestail(broadFileDestailParams);
        return Result.success(result);
    }

    /**
     * 文件类型下载
     *
     */
    @ApiIgnore
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
    @ApiOperation(value="保存富文本文件", notes="保存富文本文件")
    @PostMapping("/saveTextOmsFile")
    public Result saveTextOmsFile(OmsFile omsFile) throws Exception {
        String result = omsFileService.saveTextOmsFile(omsFile);
        return Result.success().setMsg(result);
    }
    /**
     * 重新生成内容
     * @param fileId 文件id
     */
    @ApiOperation(value="重新生成内容", notes="重新生成内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="fileId", value = "文件id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tableCode", value = "类型（因公 因私 延期回国）", required = true, dataType = "String")
    })
    @GetMapping("/selectFileDestailNew")
    public Result selectFileDestailNew(String fileId, String applyId, String tableCode){
        OmsFile omsFile = omsFileService.selectFileDestailNew(fileId, applyId, tableCode);
        return Result.success(omsFile);
    }

}
