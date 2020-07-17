package com.hxoms.modules.publicity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 省外办备案申请
 * @author gaozhenyuan
 * @date 2020/6/2 16:28
 */
@Repository
public interface OmsPubProvinceMapper extends BaseMapper<OmsPubApply> {
    //获取省外办备案申请列表
    List<OmsPubApply> getPubProvinceList(Map<Object, String> param);
    //添加省外办备案申请信息
    Object insertPubProvince(List<OmsPubApply> list);
}