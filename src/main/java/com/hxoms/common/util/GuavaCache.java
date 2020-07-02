package com.hxoms.common.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @desc: 缓存
 * @author: lijing
 * @date: 2020-07-02
 */
public class GuavaCache {
    //创建静态缓存
    private static volatile Cache<String, Object> cache;

    //单例模式获取缓存
    public static Cache<String, Object> getCache() {
        if (cache == null) {
            synchronized (GuavaCache.class) {
                if (cache == null) {
                //通过CacheBuilder的build方法创建缓存
                    cache = CacheBuilder.newBuilder()
                            .maximumSize(512)//根据系统内存大小设置缓存存储数量
                            .expireAfterAccess(60*12, TimeUnit.SECONDS)//设置缓存失效时间
                            .build();
                }
            }
        }
        return cache;
    }
}
