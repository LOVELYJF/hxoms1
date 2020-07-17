package com.hxoms.modules.publicity.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.omssmrperson.entity.OmsSmrCompare;
import com.hxoms.modules.omssmrperson.service.OmsSmrCompareService;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.service.OmsPubProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 省外办预备案
 * @author gaozhenyuan
 * @date 2020/6/2 16:28
 */
@RestController
@RequestMapping("/omsPubProvince")
public class OmsPubProvinceController {

    @Autowired
    private OmsPubProvinceService pubProvinceService;

    /**
     * 获取省外办备案申请列表
     * @param orgId
     * @param status
     * @param startDate
     * @param endDate
     */
    @GetMapping("/getPubProvinceList")
    public Result getPubProvinceList(String orgId,String status,String startDate,String endDate) {
        Map<Object,String> param = new HashMap<>();
        try{
            param.put("orgId",orgId);
            param.put("status",status);
            param.put("startDate",startDate);
            param.put("endDate",endDate);
            PageInfo<OmsPubApply> pageInfo = pubProvinceService.getPubProvinceList(param);
            return Result.success(pageInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统错误");
        }
    }

    /**
     * 添加省外办备案申请信息
     * @param pubApply
     * @param list
     */
    @PostMapping("/insertPubProvince")
    public Result insertPubProvince(OmsPubApply pubApply,List<OmsPubApply> list) {
        return Result.success(pubProvinceService.insertPubProvince(pubApply,list));
    }

}









