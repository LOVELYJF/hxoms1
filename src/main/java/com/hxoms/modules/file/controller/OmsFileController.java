package com.hxoms.modules.file.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.entity.OmsFile;
import com.hxoms.modules.file.entity.OmsTYMBVO;
import com.hxoms.modules.file.entity.OtherMaterial;
import com.hxoms.modules.file.entity.paramentity.AbroadFileDestailParams;
import com.hxoms.modules.file.service.OmsFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "材料管理")
@RestController
@RequestMapping("/omsFile")
public class OmsFileController {

    @Autowired
    private OmsFileService omsFileService;

    /**
     * 文件列表
     *
     * @param tableCode    类型（因公 因私 延期回国）
     * @param procpersonId 出国人
     * @param applyId      申请id
     * @return
     */
    @ApiOperation(value = "文件列表", notes = "根据类型查询文件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableCode", value = "类型（因公 因私 延期回国）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "procpersonId", value = "出国人申请备案id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String")
    })
    @GetMapping("/selectFileListByCode")
    public Result selectFileListByCode(String tableCode, String procpersonId, String applyId) {
        List<OmsCreateFile> list = omsFileService.selectFileListByCode(tableCode, procpersonId, applyId);
        return Result.success(list);
    }
    @ApiOperation(value = "其它列表", notes = "根据类型查询其它列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableCode", value = "类型（因公 因私 延期回国）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "procpersonId", value = "出国人申请备案id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String")
    })
    @GetMapping("/selectOtherMaterial")
    public Result selectOtherMaterial(String tableCode, String procpersonId, String applyId) {
        List<OtherMaterial> list = omsFileService.selectOtherMaterial(tableCode, procpersonId, applyId);
        return Result.success(list);
    }
    /**
     * 查询富文本文件详情
     */
    @ApiOperation(value = "查询富文本文件详情", notes = "查询富文本文件详情")
    @GetMapping("/selectFileTemplate")
    public Result selectFileTemplate(String fileTemplateId) {
        Map<String, Object> result = omsFileService.selectFileTemplate(fileTemplateId);
        return Result.success(result);
    }


    /**
     * 文件类型下载
     */
    @ApiIgnore
    @GetMapping("/downloadOmsFile")
    public void downloadOmsFile(AbroadFileDestailParams abroadFileDestailParams) throws Exception {
        omsFileService.downloadOmsFile(abroadFileDestailParams);
    }

    /**
     * 保存富文本文件
     *
     * @param omsFile
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "保存富文本文件", notes = "保存富文本文件")
    @PostMapping("/saveTextOmsFile")
    public Result saveTextOmsFile(OmsFile omsFile) throws Exception {
        String result = omsFileService.saveTextOmsFile(omsFile);
        return Result.success().setMsg(result);
    }
    @ApiOperation(value = "保存其它材料勾选结果", notes = "保存其它材料勾选结果")
    @PostMapping("/saveOtherFile")
    public Result saveOtherFile(String id, String applyId, Integer isRequired){
        return omsFileService.saveOtherFile(id,applyId,isRequired);
    }
    /**
     * 重新生成内容
     *
     * @param fileId omsCreateFile的id
     */
    @ApiOperation(value = "重新生成内容", notes = "重新生成内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "实际生成的文件id", required = true, dataType = "String")
    })
    @GetMapping("/selectFileDestailNew")
    public Result selectFileDestailNew(String fileId) {
        OmsCreateFile omsFile = omsFileService.selectFileDestailNew(fileId);
        return Result.success(omsFile);
    }

    /**
     * 功能描述: <br>
     * 〈通用模板查询〉
     * @Param: []
     * @Return: com.hxoms.common.utils.Result
     * @Author: 李逍遥
     * @Date: 2020/10/12 19:31
     */
    @GetMapping("/selectFileList")
    public Result selectFileList(){
        List<OmsTYMBVO> result =omsFileService.selectFileList();
        return Result.success(result);
    }


     /**
     * <b>功能描述: 自评页面查询文件列表</b>
     * @Param:
     * @Return:
     * @Author: luoshuai
     * @Date: 2020/10/21 20:04
     */
     @GetMapping("/selectFileListForSelf")
    public Result selectFileListForSelf(OmsFile omsFile){
        List<OmsFile> list = omsFileService.selectFileListForSelf(omsFile);
        return Result.success(list);
    }



    /**
     * <b>功能描述: 自评页面维护页面添加文件</b>
     * @Param:
     * @Return:
     * @Author: luoshuai
     * @Date: 2020/10/22 10:04
     */
    @PostMapping("/updateFileListForSelf")
    public Result updateFileListForSelf(OmsFile omsFile) {
        omsFileService.updateFileListForSelf(omsFile);
        return Result.success().setData("aaa");
    }
}
