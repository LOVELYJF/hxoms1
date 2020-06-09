package com.hxoms.modules.omssmrperson.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrRecordInfo;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import com.hxoms.modules.omssmrperson.service.OmsSmrRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 省国家保密局备案涉密人员管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@RestController
@RequestMapping("/smrRecordInfo")
public class OmsSmrRecordInfoController {

    @Autowired
    private OmsSmrRecordInfoService smrRecordInfoService;

    private HttpServletResponse response;
    /**
     * 获取省国家保密局备案涉密人员信息列表
     * @param smrRecordInfo
     */
    @GetMapping("/getSmrRecordInfo")
    public Result getSmrRecordInfo(Page page, OmsSmrRecordInfo smrRecordInfo) {
        try{
            IPage<OmsSmrRecordInfo> recordInfoList = smrRecordInfoService.getSmrRecordInfoList(page,smrRecordInfo);
            return Result.success(recordInfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }


    /**
     * 获取已匹配人员列表
     * @return
     */
    @GetMapping("/getMatchingPerson")
    public Result getMatchingPerson(){
        try{
            List<OmsSmrRecordInfo> list= smrRecordInfoService.getMatchingPerson();;
            return Result.success(list);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 导出已匹配人员列表
     * @return
     */
    @PostMapping("/exportMatchingPerson")
    public Result exportMatchingPerson(){
        try{
            boolean result = smrRecordInfoService.exportMatchingPerson(response);;
            return Result.success(result);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("导出失败");
        }
    }


}









