package com.hxoms.modules.country.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.PageUtil;
import com.hxoms.modules.country.entity.Country;
import com.hxoms.modules.country.mapper.CountryMapper;
import com.hxoms.modules.country.service.CountryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @desc: 国家管理
 * @author: lijing
 * @date: 2020-06-17
 */
@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryMapper countryMapper;

    @Override
    public PageInfo<Country> selectCountryIPage(Integer pageNum, Integer pageSize, String nameZh) {
        if (pageNum == null || pageSize == null){
            throw new CustomMessageException("参数错误");
        }
        //分页
        PageUtil.pageHelp(pageNum, pageSize);
        QueryWrapper<Country> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isBlank(nameZh),"NAME_ZH", nameZh);
        List<Country> countries = countryMapper.selectList(queryWrapper);
        //返回数据
        PageInfo<Country> pageInfo = new PageInfo(countries);
        return pageInfo;
    }
}
