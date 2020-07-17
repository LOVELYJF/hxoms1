package com.hxoms.modules.publicity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.publicity.entity.OmsPubApply;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 省外办备案申请
 * @author gaozhenyuan
 * @date 2020/6/2 16:28
 */
public interface OmsPubProvinceService extends IService<OmsPubApply>{
    //获取省外办备案申请列表
    PageInfo<OmsPubApply> getPubProvinceList(Map<Object, String> param) throws ParseException;
    //添加省外办备案申请信息
    Object insertPubProvince(OmsPubApply pubApply, List<OmsPubApply> list);
}
