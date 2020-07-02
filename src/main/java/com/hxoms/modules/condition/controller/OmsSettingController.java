package com.hxoms.modules.condition.controller;

import com.hxoms.common.utils.Result;
import com.hxoms.modules.condition.entity.OmsSetting;
import com.hxoms.modules.condition.service.OmsSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @desc: 系统配置项管理
 * @author: lijing
 * @date: 2020-07-02
 */
@RestController
@RequestMapping("/omsSetting")
public class OmsSettingController {
    @Autowired
    private OmsSettingService omsSettingService;

    /**
     * 检查约束条件
     * @return
     */
    @GetMapping("/getSettingList")
    public Result getSettingList(){
        List<OmsSetting> result = omsSettingService.getSettingCache();
        return Result.success(result);
    }
}
