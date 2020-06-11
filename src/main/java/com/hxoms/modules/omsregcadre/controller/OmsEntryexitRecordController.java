package com.hxoms.modules.omsregcadre.controller;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsEntryexitRecordIPagParam;
import com.hxoms.modules.omsregcadre.service.OmsEntryexitRecordService;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/OmsEntryexitRecord")
public class OmsEntryexitRecordController {

    @Autowired
    private OmsEntryexitRecordService entryexitRecordService;

    @GetMapping("/getEntryexitRecordinfo")
    public Result getEntryexitRecordinfo(OmsEntryexitRecordIPagParam entryexitRecordIPagParam) throws Exception{
        PageInfo<OmsEntryexitRecord> entryexitRecordIPag = entryexitRecordService.getEntryexitRecordinfo(entryexitRecordIPagParam);
        return Result.success(entryexitRecordIPagParam);
    }

    /**
     * 根据人员查询对应的因私出国申请记录
     * @return
     */
    @GetMapping("/queryPriApplyList")
    public Result queryPriApplyList(String a0100) {
        try{
            List<OmsPriApply> priapplylist = entryexitRecordService.queryPriApplyList(a0100);

            return Result.success(priapplylist);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }



}
