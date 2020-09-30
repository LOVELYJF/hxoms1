package com.hxoms.modules.omsregcadre.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatch;
import com.hxoms.modules.omsregcadre.service.OmsRegProcbatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/regProcbatch")
public class OmsRegProcbatchController {
    @Autowired
    private OmsRegProcbatchService orpbatchService;

    /**
     * 启动登记备案
     * @param regProcbatch
     * @return
     */
    @PostMapping("/startOmsReg")
    public Result startOmsReg(OmsRegProcbatch regProcbatch) {
        return Result.success(orpbatchService.startOmsReg(regProcbatch));
    }

    /**
     * 新增
     * @param regProcbatch
     * @return
     */
    @PostMapping("/insertProcbatch")
    public Result insertProcbatch(OmsRegProcbatch regProcbatch) {
        return Result.success(orpbatchService.insertProcbatch(regProcbatch));
    }


    /**
     * 确定备案完成
     * @author lijiaojiao
     * @date 2020/4/27 14:01
     */
    @PostMapping("/determineRegFinish")
    public Result determineRegFinish(String data)
    {
        return orpbatchService.determineRegFinish(data);
    }


    /**
     * 历史批次下拉框查询
     * @return
     */
    @GetMapping("/getHistoryBatch")
    public Result getHistoryBatch() {
        try{
            List<String> historybatchList = orpbatchService.getHistoryBatch();
            return Result.success(historybatchList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }
    /**
     * @description: 获取待确认登记备案记录
     * @author:杨波
     * @date:2020-09-30
     *  * @param batchId 批次主键，为空时返回未完成的批次人员
     * @return:com.hxoms.common.utils.Result
     **/
    @GetMapping("/getToBeConfirmed")
    public Result getToBeConfirmed(String batchId){

        return orpbatchService.getToBeConfirmed(batchId);
    }

}
