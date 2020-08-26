package com.hxoms.modules.privateabroad.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApply;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApplyVO;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.service.OmsPriDelayApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @desc: 延期回国
 * @author: lijing
 * @date: 2020-06-03
 */
@Api(tags="延期回国")
@RestController
@RequestMapping("/omsPriDelayApply")
public class OmsPriDelayApplyController {

    @Autowired
    private OmsPriDelayApplyService omsPriDelayApplyService;

    /**
     * 延期回国申请列表
     * @param omsPriApplyIPageParam  查询条件
     * @return
     * @throws Exception
     */
    @ApiOperation(value="延期回国申请列表", notes="延期回国申请列表")
    @GetMapping("/selectOmsDelayApplyIPage")
    public Result selectOmsDelayApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam) throws Exception {
        PageInfo<OmsPriDelayApplyVO> omsPriDelayApplyVOPageInfo = omsPriDelayApplyService.selectOmsDelayApplyIPage(omsPriApplyIPageParam);
        return Result.success(omsPriDelayApplyVOPageInfo);
    }

    /**
     * 新增或修改延期回国申请
     * @param omsPriDelayApply  申请接收参数类
     * @return
     * @throws Exception
     */
    @ApiOperation(value="新增或修改延期回国申请", notes="新增或修改延期回国申请")
    @PostMapping("/insertOrUpdateApply")
    public Result insertOrUpdateApply(OmsPriDelayApply omsPriDelayApply) throws Exception {
        String result = omsPriDelayApplyService.insertOrUpdateApply(omsPriDelayApply);
        return Result.success(result);
    }

    /**
     * 修改延期回国申请状态
     * @param omsPriDelayApply 申请id
     * @return
     * @throws Exception
     */
    @ApiOperation(value="修改延期回国申请状态", notes="修改延期回国申请状态")
    @PostMapping("/updateDelayApplyStatus")
    public Result updateDelayApplyStatus(OmsPriDelayApply omsPriDelayApply) throws Exception {
        String result = omsPriDelayApplyService.updateDelayApplyStatus(omsPriDelayApply);
        return Result.success().setMsg(result);
    }

    /**
     * 删除延期回国申请
     * @param id 申请id
     * @return
     * @throws Exception
     */
    @ApiOperation(value="删除延期回国申请", notes="删除延期回国申请")
    @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "String")
    @PostMapping("/deleteDelayApply")
    public Result deleteDelayApply(String id) throws Exception {
        String result = omsPriDelayApplyService.deleteDelayApply(id);
        return Result.success().setMsg(result);
    }

    /**
     * 延期回国申请详情
     * @param id 申请id
     * @return
     */
    @ApiOperation(value="延期回国申请详情", notes="延期回国申请详情")
    @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "String")
    @GetMapping("/selectDelayApplyById")
    public Result selectDelayApplyById(String id){
        OmsPriDelayApplyVO omsPriDelayApplyVO = omsPriDelayApplyService.selectDelayApplyById(id);
        return Result.success(omsPriDelayApplyVO);
    }
}
