package com.hxoms.modules.selfestimate.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.selfestimate.entity.OmsSelfFile;
import com.hxoms.modules.selfestimate.entity.OmsSelfFileVO;
import com.hxoms.modules.selfestimate.entity.OmsSelfestimateItems;
import com.hxoms.modules.selfestimate.service.OmsSelfestimateItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @desc: 自评项目维护
 * @author: lijing
 * @date: 2020-05-25
 */
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
    @GetMapping("/selectItemList")
    public Result selectItemList(String type) throws Exception {
        List<OmsSelfFileVO> omsSelfFileVOS = omsSelfestimateItemsService.selectItemsList(type);
        return Result.success(omsSelfFileVOS);
    }

    /**
     * 自评项目添加修改
     * @return
     * @param omsSelfestimateItems
     * @throws Exception
     */
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
    @PostMapping("/deleteSelfFile")
    public Result deleteSelfFile(String id) throws Exception {
        String result  = omsSelfestimateItemsService.deleteSelfFile(id);

        return Result.success().setMsg(result);
    }

    /**
     * 自评文件列表
     * @return
     * @param type 因公 因私  延期回国
     * @throws Exception
     */
    @GetMapping("/selectFileList")
    public Result selectFileList(String type) throws Exception {
        List<OmsSelfFileVO> omsSelfFileVOS = omsSelfestimateItemsService.selectFileList(type);
        return Result.success(omsSelfFileVOS);
    }

}
