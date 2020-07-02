package com.hxoms.modules.condition.service;

import com.hxoms.modules.condition.entity.OmsSetting;

import java.util.List;

/**
 * @desc: 系统配置项管理
 * @author: lijing
 * @date: 2020-07-02
 */
public interface OmsSettingService {
    /**
     * 存入缓存
     */
    void setSettingCache();

    /**
     * 获取缓存数据
     * @return
     */
    List<OmsSetting> getSettingCache();
}
