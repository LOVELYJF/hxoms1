package com.hxoms.modules.file.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.modules.file.entity.OmsCreateFile;
import com.hxoms.modules.file.mapper.OmsCreateFileMapper;
import com.hxoms.modules.file.service.OmsCreateFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * @desc: 生成材料
 * @author: lijing
 * @date: 2020-06-12
 */
@Api(tags = "生成材料管理")
@RestController
@RequestMapping("/omsCreateFile")
public class OmsCreateFileController {

    @Autowired
    private OmsCreateFileService omsCreateFileService;
    @Autowired
    private OmsCreateFileMapper omsCreateFileMapper;

    /**
     * 生成文件列表
     * @param tableCode 类型（因公 因私 延期回国）
     * @param applyId 申请Id
     * @return
     */
    @ApiOperation(value="生成文件列表", notes="生成文件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tableCode", value = "类型（因公，因私，延期回国）", required = true, dataType = "String")
    })
    @GetMapping("/selectCreateFileList")
    public Result selectCreateFileList(String tableCode, String applyId) {
        List<OmsCreateFile> list = omsCreateFileService.selectCreateFileList(tableCode, applyId);
        return Result.success(list);
    }

    /**
     * 保存或者更新
     *
     */
    @ApiOperation(value="保存或者更新", notes="保存或者更新")
    @PostMapping("/insertOrUpdate")
    public Result insertOrUpdate(OmsCreateFile omsCreateFile){
        omsCreateFile = omsCreateFileService.InsertOrUpdate(omsCreateFile);
        return Result.success(omsCreateFile);
    }

    /**
     * 批量删除生成文件
     * @param tableCode
     * @param applyId
     * @return
     */
    @ApiOperation(value="批量删除生成文件", notes="批量删除生成文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tableCode", value = "类型（因公，因私，延期回国）", required = true, dataType = "String")
    })
    @PostMapping("/deleteCreateFile")
    public Result deleteCreateFile(String tableCode, String applyId){
        String result = omsCreateFileService.deleteCreateFile(tableCode, applyId);
        return Result.success().setMsg(result);
    }
}
