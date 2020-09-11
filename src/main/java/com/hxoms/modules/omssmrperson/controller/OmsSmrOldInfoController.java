package com.hxoms.modules.omssmrperson.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.service.OmsSmrOldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 涉密人员原涉密信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@RestController
@RequestMapping("/smrOldInfo")
public class OmsSmrOldInfoController {

    @Autowired
    private OmsSmrOldInfoService smrOldInfoService;

    /**
     * 获取涉密人员原涉密信息列表
     * @param id(登记备案人员主键)
     */
    @GetMapping("/getSmrOldInfoById")
    public Result getSmrOldInfoById(Integer pageNum, Integer pageSize,String id) {
        try{
            PageInfo<OmsSmrOldInfo> oldInfoList = smrOldInfoService.getSmrOldInfoById(pageNum,pageSize,id);
            return Result.success(oldInfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 添加涉密人员原涉密信息
     * @param smrOldInfo
     */
    @PostMapping("/insertSmrOldInfo")
    public Result insertSmrOldInfo(OmsSmrOldInfo smrOldInfo) {
        return Result.success(smrOldInfoService.insert(smrOldInfo));
    }

   /**
     * 修改涉密人员原涉密信息
     * @param smrOldInfo
     */
    @PostMapping("/updateSmrOldInfo")
    public Result updateSmrOldInfo(OmsSmrOldInfo smrOldInfo) {
        return Result.success(smrOldInfoService.update(smrOldInfo));
    }

    /**
     * 删除涉密人员原涉密信息
     * @param id
     */
    @PostMapping("/deleteSmrOldInfo")
    public Result deleteSmrOldInfo(String id) {
        return Result.success(smrOldInfoService.delete(id));
    }



}









