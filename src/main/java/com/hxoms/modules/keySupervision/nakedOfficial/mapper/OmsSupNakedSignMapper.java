package com.hxoms.modules.keySupervision.nakedOfficial.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;

import java.util.List;
import java.util.Map;
/**
 * <b>裸官信息业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/11 16:57
 */
public interface OmsSupNakedSignMapper extends BaseMapper<OmsSupNakedSign> {
    /**
     * <b>查询裸官信息</b>
     * @param map
     * @return
     */
    List<OmsSupNakedSign> selectNakedOfficialList(Map<String, Object> map);


    /**
     * <b>查询政治面貌</b>
     * @param a0100
     * @return
     */
    String selectPiliticalAffi(String a0100);

    /**
     * <b>查询工作单位名称</b>
     * @param map
     * @return
     */
    List<String> selectWorkUnit(Map<String,Object> map);
}