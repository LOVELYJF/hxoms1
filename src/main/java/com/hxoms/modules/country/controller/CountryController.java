package com.hxoms.modules.country.controller;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc: 国家管理
 * @author: lijing
 * @date: 2020-06-17
 */
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
    @GetMapping("/selectCountryIPage")
    public Result selectCountryIPage(Integer pageNum, Integer pageSize, String nameZh) throws Exception {
        PageInfo<Country> countryPageInfo = countryService.selectCountryIPage(pageNum, pageSize, nameZh);
        return Result.success(countryPageInfo);
    }
}
