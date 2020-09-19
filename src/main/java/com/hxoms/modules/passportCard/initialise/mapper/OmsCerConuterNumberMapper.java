package com.hxoms.modules.passportCard.initialise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.initialise.entity.OmsCerCounterNumber;

public interface OmsCerConuterNumberMapper extends BaseMapper<OmsCerCounterNumber> {

    /**
     * @Desc: 查询可使用柜台号码
     * @Author: wangyunquan
     * @Param: [surelyUnit, zjlx, zjxs]
     * @Return: com.hxoms.modules.passportCard.initialise.entity.OmsCerCounterNumber
     * @Date: 2020/9/8
     */

    OmsCerCounterNumber  selectCounterNum(String surelyUnit, Integer zjlx, String zjxs);
}