package com.hxoms.modules.privateabroad.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriApplyVO;
import com.hxoms.modules.privateabroad.entity.PassportResult;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyIPageParam;
import com.hxoms.modules.privateabroad.entity.paramentity.OmsPriApplyParam;
import com.hxoms.modules.privateabroad.service.OmsPriApplyService;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @desc: 因私出国境申请
 *
 * @author: lijing
 * @date: 2020-05-15
 */
@Api(tags="因私出国境申请")
@RestController
@RequestMapping("/omsPriApply")
public class OmsPriApplyController {

    @Autowired
    private OmsPriApplyService omsPriApplyService;

    /**
     * 因私出国申请列表
     * @param omsPriApplyIPageParam  查询条件
     * @return
     * @throws Exception
     */
    @ApiOperation(value="因私出国申请列表", notes="因私出国申请列表")
    @GetMapping("/selectOmsPriApplyIPage")
    public Result selectOmsPriApplyIPage(OmsPriApplyIPageParam omsPriApplyIPageParam) throws Exception {
        PageInfo<OmsPriApplyVO> omsPriApplyVOIPage = omsPriApplyService.selectOmsPriApplyIPage(omsPriApplyIPageParam);
        return Result.success(omsPriApplyVOIPage);
    }

    /**
     * 根据姓名或者拼音查找用户
     * @param keyword 关键词
     * @return
     * @throws Exception
     */
    @ApiOperation(value="根据姓名或者拼音查找用户", notes="根据姓名或者拼音查找用户")
    @ApiImplicitParam(name = "keyword", value = "关键词", required = true, dataType = "String")
    @GetMapping("selectPersonByKeyword")
    public Result selectPersonByKeyword(String keyword) throws Exception {
        List<PersonInfoVO> personInfoVOS = omsPriApplyService.selectPersonByKeyword(keyword);
        return Result.success(personInfoVOS);
    }

    /**
     * 选择人员
     * @param procpersonId 备案表id
     * @return
     * @throws Exception
     */
    @ApiOperation(value="选择人员", notes="选择人员")
    @ApiImplicitParam(name = "procpersonId", value = "备案表id", required = true, dataType = "String")
    @GetMapping("/selectPersonById")
    public Result selectPersonById(String procpersonId) throws Exception {
        OmsPriApplyVO omsPriApplyVO = omsPriApplyService.selectPersonById(procpersonId);
        return Result.success(omsPriApplyVO);
    }

    /**
     * 新增或修改因私出国申请
     * @param omsPriApplyParam  申请接收参数类
     * @return
     * @throws Exception
     */
    @ApiOperation(value="新增或修改因私出国申请", notes="新增或修改因私出国申请")
    @PostMapping("/insertOrUpdatePriApply")
    public Result insertOrUpdatePriApply(@RequestBody OmsPriApplyParam omsPriApplyParam) throws Exception {
        String result = omsPriApplyService.insertOrUpdatePriApply(omsPriApplyParam);
        return Result.success(result);
    }

    /**
     * 修改因私出国申请状态
     * @param omsPriApply 申请id
     * @return
     * @throws Exception
     */
    @ApiOperation(value="修改因私出国申请状态", notes="修改因私出国申请状态")
    @PostMapping("/updateApplyStatus")
    public Result updateApplyStatus(OmsPriApply omsPriApply) throws Exception {
        String result = omsPriApplyService.updateApplyStatus(omsPriApply);
        return Result.success().setMsg(result);
    }

    /**
     * 删除申请
     * @param id 申请id
     * @return
     * @throws Exception
     */
    @ApiOperation(value="删除申请", notes="删除申请")
    @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "String")
    @PostMapping("/deletePriApply")
    public Result deletePriApply(String id) throws Exception {
        String result = omsPriApplyService.deletePriApply(id);
        return Result.success().setMsg(result);
    }

    /**
     * 申请详情
     * @param id 申请id
     * @return
     */
    @ApiOperation(value="申请详情", notes="申请详情")
    @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "String")
    @GetMapping("/selectPriApplyById")
    public Result selectPriApplyById(String id){
        OmsPriApplyVO omsPriApplyVO = omsPriApplyService.selectPriApplyById(id);
        return Result.success(omsPriApplyVO);
    }

    /**
     * 基本流程数据统计
     * @param type 因私 延期回国
     * @return
     */
    @ApiOperation(value="基本流程数据统计", notes="基本流程数据统计")
    @ApiImplicitParam(name = "type", value = "因私 延期回国", required = true, dataType = "String")
    @GetMapping("/selectCountStatus")
    public Result selectCountStatus(String type){
        List<CountStatusResult> countStatusResults = omsPriApplyService.selectCountStatus(type);
        return Result.success(countStatusResults);
    }

    /**
     * 下一步（生成材料）
     * @param applyId
     * @return
     * @throws Exception
     */
    @ApiOperation(value="下一步（生成材料）", notes="下一步（生成材料）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型（因公 因私 延期回国）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "applyId", value = "申请id", required = true, dataType = "String")
    })
    @PostMapping("/nextCreateFile")
    public Result nextCreateFile(String applyId, String type) throws Exception {
        String result = omsPriApplyService.nextCreateFile(applyId, type);
        return Result.success().setMsg(result);
    }

    /**
     * 情况报告
     * @param omsPriApply
     * @return
     * @throws Exception
     */
    @ApiOperation(value="情况报告", notes="情况报告")
    @PostMapping("/saveAbroadState")
    public Result saveAbroadState(OmsPriApply omsPriApply) throws Exception {
        String result = omsPriApplyService.saveAbroadState(omsPriApply);
        return Result.success().setMsg(result);
    }

    /**
     * 撤销人员统计
     * @return
     */
    @ApiOperation(value="撤销人员统计", notes="撤销人员统计")
    @GetMapping("/countCancelPriApply")
    public Result countCancelPriApply(OmsPriApplyIPageParam omsPriApplyIPageParam){
        List<Map<String, Object>> countStatusResults = omsPriApplyService.countCancelPriApply(omsPriApplyIPageParam);
        return Result.success(countStatusResults);
    }

    /**
     * 签注配置查询
     * @param infoId 出国理由编码
     * @return
     */
    @ApiOperation(value="签注配置查询", notes="签注配置查询")
    @ApiImplicitParam(name = "infoId", value = "出国理由编码", required = true, dataType = "String")
    @GetMapping("/selectVisaSettingByCode")
    public Result selectVisaSettingByCode(String infoId){
        Map<String, Object> visaSetting = omsPriApplyService.selectVisaSettingByCode(infoId);
        return Result.success(visaSetting);
    }

    /**
     * 因私出国证照查询
     * @param countries 国家id（逗号分隔）
     * @param procpersonId 备案人员id
     * @return
     */
    @ApiOperation(value="因私出国证照查询", notes="因私出国证照查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "countries", value = "国家id（逗号分隔）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "procpersonId", value = "备案人员id", required = true, dataType = "String")
    })
    @GetMapping("/selectPassportByCountry")
    public Result selectPassportByCountry(String countries, String procpersonId){
        List<PassportResult> result = omsPriApplyService.selectPassportByCountry(countries, procpersonId);
        return Result.success(result);
    }
}
