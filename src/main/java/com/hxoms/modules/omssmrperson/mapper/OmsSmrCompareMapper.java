package com.hxoms.modules.omssmrperson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omssmrperson.entity.OmsSmrCompare;
import com.hxoms.modules.omssmrperson.entity.OmsSmrCompareVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 涉密人员身份证对照
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@Repository
public interface OmsSmrCompareMapper extends BaseMapper<OmsSmrCompare> {
    //获取身份证纠正列表
    List<OmsSmrCompareVO> getCompareIdCard(String b0100);
    //判断导入的涉密人员信息能否匹配
    OmsSmrCompare getMatchingDate(String b0100, String idCardNumber);
}