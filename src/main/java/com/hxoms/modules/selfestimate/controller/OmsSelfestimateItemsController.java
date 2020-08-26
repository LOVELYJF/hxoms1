package com.hxoms.modules.selfestimate.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.selfestimate.entity.OmsSelfFile;
import com.hxoms.modules.selfestimate.entity.OmsSelfFileVO;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateItems;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateResultitemVO;
import com.hxoms.modules.selfestimate.service.OmsSelfestimateItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @desc: 自评项目维护
 * @author: lijing
 * @date: 2020-05-25
 */
@Api(tags="自评项目维护")
@RestController
@RequestMapping("/omsSelfestimateItems")
public class OmsSelfestimateItemsController {
    @Autowired
    private OmsSelfestimateItemsService omsSelfestimateItemsService;

    /**
     * 自评信息维护列表
     * @return
     * @param type 因公 因私  延期回国
     * @throws Exception
     */
    @ApiOperation(value="自评信息维护列表", notes="自评信息维护列表")
    @ApiImplicitParam(name = "type", value = "type 因公 因私  延期回国", required = true, dataType = "String")
    @GetMapping("/selectItemList")
    public Result selectItemList(String type) throws Exception {
        List<OmsSelfFile> omsSelfFile = omsSelfestimateItemsService.selectItemsList(type);
        return Result.success(omsSelfFile);
    }

    /**
     * 自评项目添加修改
     * @return
     * @param omsSelfestimateItems
     * @throws Exception
     */
    @ApiOperation(value="自评项目添加修改", notes="自评项目添加修改")
    @PostMapping("/insertItem")
    public Result insertItem(OmsSelfestimateItems omsSelfestimateItems) throws Exception {
        String result  = omsSelfestimateItemsService.insertItem(omsSelfestimateItems);
        return Result.success().setMsg(result);
    }

    /**
     * 删除自评项目
     * @return
     * @param id
     * @throws Exception
     */
    @ApiOperation(value="删除自评项目", notes="删除自评项目")
    @ApiImplicitParam(name = "id", value = "自评项目id", required = true, dataType = "String")
    @PostMapping("/deleteItem")
    public Result deleteItem(String id) throws Exception {
        String result  = omsSelfestimateItemsService.deleteItem(id);
        return Result.success().setMsg(result);
    }

    /**
     * 自评文件添加修改
     * @return
     * @param omsSelfFile
     * @throws Exception
     */
    @ApiOperation(value="自评文件添加修改", notes="自评文件添加修改")
    @PostMapping("/insertSelfFile")
    public Result insertSelfFile(OmsSelfFile omsSelfFile) throws Exception {
        String result  = omsSelfestimateItemsService.insertSelfFile(omsSelfFile);
        return Result.success().setMsg(result);
    }

    /**
     * 自评文件删除
     * @return
     * @param id
     * @throws Exception
     */
    @ApiOperation(value="自评文件删除", notes="自评文件删除")
    @ApiImplicitParam(name = "id", value = "自评文件id", required = true, dataType = "String")
    @PostMapping("/deleteSelfFile")
    public Result deleteSelfFile(String id) throws Exception {
        String result  = omsSelfestimateItemsService.deleteSelfFile(id);

        return Result.success().setMsg(result);
    }

    /**
     * 自评结果列表
     * @return
     * @param type 因公 因私  延期回国
     * @param applyId 申请id
     * @param personType 操作人类型（经办人  干部监督处）
     * @throws Exception
     */
    @ApiOperation(value="下一步（生成材料）", notes="下一步（生成材料）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型（因公 因私 延期回国）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "personType", value = "操作人类型（经办人  干部监督处）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String")
    })
    @GetMapping("/selectFileList")
    public Result selectFileList(String type, String applyId, String personType) throws Exception {
        List<OmsSelfFileVO> omsSelfFileVOS = omsSelfestimateItemsService.selectFileList(type, applyId, personType);
        return Result.success(omsSelfFileVOS);
    }

    /**
     * 添加文件列表
     * @return
     * @param type 因公 因私  延期回国
     * @throws Exception
     */
    @ApiOperation(value="添加文件列表", notes="添加文件列表")
    @ApiImplicitParam(name = "type", value = "因公 因私  延期回国", required = true, dataType = "String")
    @GetMapping("/selectOmsFileList")
    public Result selectOmsFileList(String type) throws Exception {
        List<Map<String, String>> result = omsSelfestimateItemsService.selectOmsFileList(type);
        return Result.success(result);
    }

    /**
     * 自评项目维护列表
     * @return
     * @param selffileId 自评材料清单id
     * @throws Exception
     */
    @ApiOperation(value="自评项目维护列表", notes="自评项目维护列表")
    @ApiImplicitParam(name = "selffileId", value = "自评材料清单id", required = true, dataType = "String")
    @GetMapping("/selectSelfItemList")
    public Result selectSelfItemList(String selffileId) throws Exception {
        List<OmsSelfestimateItems> omsSelfestimateItems = omsSelfestimateItemsService.selectSelfItemList(selffileId);
        return Result.success(omsSelfestimateItems);
    }

    /**
     * 自评结果项列表
     * @return
     * @param selffileId 自评id
     * @param applyId 申请id
     * @param personType 操作人类型（经办人  干部监督处）
     * @throws Exception
     */
    @ApiOperation(value="自评结果项列表", notes="自评结果项列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型（因公 因私 延期回国）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "selffileId", value = "自评材料清单id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "personType", value = "操作人类型（经办人  干部监督处）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String")
    })
    @GetMapping("/selectFileItemsList")
    public Result selectFileItemsList(String type, String selffileId, String applyId, String personType) throws Exception {
        OmsSelfFileVO omsSelfFileVO = omsSelfestimateItemsService.selectFileItemsList(type, selffileId, applyId, personType);
        return Result.success(omsSelfFileVO);
    }
}
