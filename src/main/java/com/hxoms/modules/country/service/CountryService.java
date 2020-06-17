package com.hxoms.modules.country.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.country.entity.Country;

public interface CountryService {
    /**
     *
     * @param pageNum
     * @param pageSize
     * @param nameZh
     * @return
     */
    PageInfo<Country> selectCountryIPage(Integer pageNum, Integer pageSize, String nameZh);
}
