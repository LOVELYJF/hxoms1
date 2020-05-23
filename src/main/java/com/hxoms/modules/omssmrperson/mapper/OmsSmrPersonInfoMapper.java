package com.hxoms.modules.omssmrperson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omssmrperson.entity.OmsSmrPersonInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OmsSmrPersonInfoMapper  extends BaseMapper<OmsSmrPersonInfo> {
    List<OmsSmrPersonInfo> selectSmrPersonInfo(Map<String,Object> param);
}