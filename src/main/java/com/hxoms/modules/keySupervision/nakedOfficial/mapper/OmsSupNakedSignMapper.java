package com.hxoms.modules.keySupervision.nakedOfficial.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.keySupervision.nakedOfficial.entity.OmsSupNakedSign;

import java.util.List;

/**
 * <b>裸官信息业务层接口实现类</b>
 * @author luoshuai
 * @date 2020/5/11 16:57
 */
public interface OmsSupNakedSignMapper extends BaseMapper<OmsSupNakedSign> {

    /**
     * <b>查询所有裸官的人员的a0100</b>
     * @return
     */
    List<String> selectA0100List();
}