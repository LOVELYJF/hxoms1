package com.hxoms.modules.omsregcadre.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsPmChangeNonpm;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsPmChangeNonpmIPageParam;
import com.hxoms.modules.omsregcadre.service.OmsPmChangeNonpmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/OmsPmChangeNonpm")
public class OmsPmChangeNonpmController {

    @Autowired
    private OmsPmChangeNonpmService pmChangeNonpmService;

    /**
     * 省管变非省管干部撤销登记备案申请
     * @author lijiaojiao
     * @date 2020/5/14 09:01
     */
    @GetMapping("/getInitialReginfo")
    public Result getPmChangeNonpminfo(OmsPmChangeNonpmIPageParam pmChangeNonpmIPageParam) throws Exception{
        PageInfo<OmsPmChangeNonpm> pmChangeNonpmIPage = pmChangeNonpmService.getPmChangeNonpminfo(pmChangeNonpmIPageParam);
        return Result.success(pmChangeNonpmIPage);
    }




    /**
     * 添加省管变非省管干部撤销登记备案申请
     * @param
     * @return
     */
    @PostMapping("/insertPmChangeNonpmApply")
    public Result insertPmChangeNonpmApply(OmsPmChangeNonpm pmChangeNonpm) {
        return Result.success(pmChangeNonpmService.insertPmChangeNonpmApply(pmChangeNonpm));
    }



}
