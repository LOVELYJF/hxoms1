package com.hxoms.modules.country.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc: 国家管理
 * @author: lijing
 * @date: 2020-06-17
 */
@Api(tags = "国家管理")
@RestController
@RequestMapping("/country")
public class CountryController {
    @Autowired
    private CountryService countryService;

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param nameZh
     * @return
     * @throws Exception
     */
    @ApiOperation(value="国家列表", notes="国家列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "nameZh", value = "国家名字", required = true, dataType = "String")
    })
    @GetMapping("/selectCountryIPage")
    public Result selectCountryIPage(Integer pageNum, Integer pageSize, String nameZh) throws Exception {
        PageInfo<Country> countryPageInfo = countryService.selectCountryIPage(pageNum, pageSize, nameZh);
        return Result.success(countryPageInfo);
    }
}
