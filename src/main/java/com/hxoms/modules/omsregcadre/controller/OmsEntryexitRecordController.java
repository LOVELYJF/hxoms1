package com.hxoms.modules.omsregcadre.controller;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecordVO;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags="出国境记录管理")
@RestController
@RequestMapping("/omsEntryexitRecord")
public class OmsEntryexitRecordController {

    @Autowired
    private OmsEntryexitRecordService entryexitRecordService;

    /**
     * 因私出入境记录维护
     * @param entryexitRecordIPagParam
     * @return
     * @throws Exception
     */
    @ApiOperation(value="因私出入境记录维护", notes="因私出入境记录维护")
    @PostMapping("/getEntryexitRecordinfo")
    public Result getEntryexitRecordinfo(OmsEntryexitRecordIPagParam entryexitRecordIPagParam) throws Exception{
        PageInfo<OmsEntryexitRecord> entryexitRecordIPag = entryexitRecordService.getEntryexitRecordinfo(entryexitRecordIPagParam);
     return Result.success(entryexitRecordIPag);
     }




    /**
     * 新增出入境记录
     * @param entryexitRecord
     * @return
     */
    @ApiOperation(value="新增出入境记录", notes="新增出入境记录")
    @PostMapping("/insertEntryexitRecord")
    public Result insertEntryexitRecord(OmsEntryexitRecord entryexitRecord) {
        return Result.success(entryexitRecordService.insertEntryexitRecord(entryexitRecord));
    }


    /**
     * 修改出入境记录
     * @param entryexitRecord
     * @return
     */
    @ApiOperation(value="修改出入境记录", notes="修改出入境记录")
    @PostMapping("/updateEntryexitRecord")
    public Result updateEntryexitRecord(OmsEntryexitRecord entryexitRecord) {
        return Result.success(entryexitRecordService.updateEntryexitRecord(entryexitRecord));
    }



     /*删除出入境记录
     * @param id
     * @return
     */
    @ApiOperation(value="删除出入境记录", notes="删除出入境记录")
    @ApiImplicitParam(name = "id", value = "记录id", required = true, dataType = "String")
    @PostMapping("/deleteEntryexitRecord")
    public Result deleteEntryexitRecord(String id) {
        return Result.success(entryexitRecordService.deleteEntryexitRecord(id));
    }


    /**
     * 锁定出国申请
     * @param supSuspendUnit
     * @return
     */
    @ApiOperation(value="锁定出国申请", notes="锁定出国申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "suspendTime", value = "暂停开始时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "pauseTime", value = "暂停时长（月）", required = true, dataType = "String"),
            @ApiImplicitParam(name = "b0100", value = "锁定单位", required = true, dataType = "String")
    })
    @PostMapping("/clockGoAbroadApply")
    public Result clockGoAbroadApply(OmsSupSuspendUnit supSuspendUnit) {
        return Result.success(entryexitRecordService.clockGoAbroadApply(supSuspendUnit));
    }


    /**
     * 根据人员查询对应的因私出国申请记录
     * 以及单人比对
     * @return
     */
    @ApiOperation(value="根据人员查询对应的因私出国申请记录并自动比对", notes="根据人员查询对应的因私出国申请记录并自动比对")
    @ApiImplicitParam(name = "omsId", value = "登记备案id", required = true, dataType = "String")
    @PostMapping("/queryPriApplyList")
    public Result queryPriApplyList(String omsId) {
        try{
            Map<String, Object> map = entryexitRecordService.queryPriApplyList(omsId);
            return Result.success().setData(map);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }



    /**
     * 根据人员查询对应的异常出入境记录
     * @return
     */
    @ApiOperation(value="根据人员查询对应的异常出入境记录", notes="根据人员查询对应的异常出入境记录")
    @ApiImplicitParam(name = "omsId", value = "登记备案id", required = true, dataType = "String")
    @PostMapping("/queryExceptionPriApplyList")
    public Result queryExceptionPriApplyList(String omsId) {
        try{
            List<OmsEntryexitRecordVO> exceptionPriApplylist = entryexitRecordService.queryExceptionPriApplyList(omsId);
            return Result.success(exceptionPriApplylist);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    @ApiOperation(value="查询异常出入境记录", notes="查询异常出入境记录")
    @PostMapping("/getExceptionPriApply")
    public Result getExceptionRecord(OmsEntryexitRecordIPagParam entryexitRecordIPagParam) throws Exception{
        PageInfo<OmsEntryexitRecordVO> exceptionRecordlist = entryexitRecordService.getExceptionRecord(entryexitRecordIPagParam);
        return Result.success(exceptionRecordlist);
    }

    /**
     * 选择数据进行匹配
     * @return
     */
    @ApiOperation(value="选择数据进行匹配", notes="选择数据进行匹配")
    @GetMapping("/selectCompareInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "omsId", value = "备案id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "priapplyId", value = "因私申请id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "recordIds", value = "出入境记录id字符串（数组）", required = true, dataType = "String")
    })
    public Result selectCompareInfo(String omsId,String priapplyId,List<String> recordIds) {
        try{
            Map<String, Object> map = entryexitRecordService.selectCompareInfo(omsId,priapplyId,recordIds);
            return Result.success(map);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 选择数据取消匹配
     * @return
     */
    @ApiOperation(value="选择数据取消匹配", notes="选择数据取消匹配")
    @ApiImplicitParam(name = "id", value = "审批记录id", required = true, dataType = "String")
    @GetMapping("/cancelCompareInfo")
    public Result cancelCompareInfo(String id) {
        try{
            return Result.success(entryexitRecordService.cancelCompareInfo(id));
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 批量比对
     */
    @ApiOperation(value="批量比对", notes="批量比对")
    @PostMapping("/batchPriApplyList")
    public Result batchPriApplyList(@RequestBody List<String> omsIds) {
        return Result.success(entryexitRecordService.batchPriApplyList(omsIds));
    }

    /**
     * 年度出入境比对结果统计
     */
    @ApiOperation(value="年度出入境比对结果统计", notes="年度出入境比对结果统计")
    @GetMapping("/queryCompresultByYear")
    public Result queryCompresultByYear(OmsEntryexitRecordIPagParam entryexitRecordIPagParam) {
        Map<String, Object> map = entryexitRecordService.queryCompresultByYear(entryexitRecordIPagParam);
        return Result.success(map);
    }



}
