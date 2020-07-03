package com.hxoms.modules.condition.service.impl;

import com.google.common.cache.Cache;
import com.hxoms.common.util.GuavaCache;
import com.hxoms.modules.condition.entity.OmsSetting;
import com.hxoms.modules.condition.mapper.OmsSettingMapper;
import com.hxoms.modules.condition.service.OmsSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @desc: 系统配置项管理
 * @author: lijing
 * @date: 2020-07-02
 */
@Service
public class OmsSettingServiceImpl implements OmsSettingService {
    @Autowired
    private OmsSettingMapper omsSettingMapper;

    @Override
    public void setSettingCache() {
        //获取缓存
        Cache<String,Object> cache = GuavaCache.getCache();
        List<OmsSetting> omsSettings = omsSettingMapper.selectList(null);
        cache.put("settingList", omsSettings);
    }

    @Override
    public List<OmsSetting> getSettingCache() {
        //获取缓存
        Cache<String,Object> cache = GuavaCache.getCache();
        List<OmsSetting> omsSettings = (List<OmsSetting>) cache.getIfPresent("settingList");
        if (omsSettings == null){
            omsSettings = omsSettingMapper.selectList(null);
            cache.put("settingList", omsSettings);
        }
        return omsSettings;
    }
}
