package com.hxoms.modules.omsregcadre.controller;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.keySupervision.suspendApproval.entity.OmsSupSuspendUnit;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


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
    @PostMapping("/insertEntryexitRecord")
    public Result insertEntryexitRecord(OmsEntryexitRecord entryexitRecord) {
        return Result.success(entryexitRecordService.insertEntryexitRecord(entryexitRecord));
    }


    /**
     * 修改出入境记录
     * @param entryexitRecord
     * @return
     */
    @PostMapping("/updateEntryexitRecord")
    public Result updateEntryexitRecord(OmsEntryexitRecord entryexitRecord) {
        return Result.success(entryexitRecordService.updateEntryexitRecord(entryexitRecord));
    }



    /**
     * 删除出入境记录
     * @param id
     * @return
     */
    @PostMapping("/deleteEntryexitRecord")
    public Result deleteEntryexitRecord(String id) {
        return Result.success(entryexitRecordService.deleteEntryexitRecord(id));
    }


    /**
     * 锁定出国申请
     * @param supSuspendUnit
     * @return
     */
    @PostMapping("/clockGoAbroadApply")
    public Result clockGoAbroadApply(OmsSupSuspendUnit supSuspendUnit) {
        return Result.success(entryexitRecordService.clockGoAbroadApply(supSuspendUnit));
    }


    /**
     * 根据人员查询对应的因私出国申请记录
     * 以及单人比对
     * @return
     */
    @GetMapping("/queryPriApplyList")
    public Result queryPriApplyList(String a0100) {
        try{
            Map<String, Object> map = entryexitRecordService.queryPriApplyList(a0100);
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
    @GetMapping("/cancelCompareInfo")
    public Result cancelCompareInfo(String id) {
        try{
            Map<String, Object> map = entryexitRecordService.cancelCompareInfo(id);
            return Result.success(map);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 批量比对
     */
    @GetMapping("/batchPriApplyList")
    public Result batchPriApplyList(List<String> a0100s) {
        return Result.success(entryexitRecordService.batchPriApplyList(a0100s));
    }



}
