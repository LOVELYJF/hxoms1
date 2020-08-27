package com.hxoms.modules.omsregcadre.controller;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/getEntryexitRecordinfo")
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



    /**
     * 删除出入境记录
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
            @ApiImplicitParam(name = "pauseTime", value = "暂停时长（月）", required = true, dataType = "String")
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
    @GetMapping("/queryPriApplyList")
    public Result queryPriApplyList(String omsId) {
        try{
            Map<String, Object> map = entryexitRecordService.queryPriApplyList(omsId);
            return Result.success(map);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }




    /**
     * 选择数据进行匹配
     * @return
     */
    @GetMapping("/selectCompareInfo")
    public Result selectCompareInfo(OmsPriApply priapply, OmsEntryexitRecord outinfo, OmsEntryexitRecord joininfo) {
        try{
            Map<String, Object> map = entryexitRecordService.selectCompareInfo(priapply,outinfo,joininfo);
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
    @ApiImplicitParam(name = "omsIds", value = "登记备案id拼接字符串", required = true, dataType = "String")
    @GetMapping("/batchPriApplyList")
    public Result batchPriApplyList(List<String> omsIds) {
        return Result.success(entryexitRecordService.batchPriApplyList(omsIds));
    }



}
