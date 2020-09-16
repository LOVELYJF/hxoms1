package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.A30;

import java.util.List;

public interface A30Mapper extends BaseMapper<A30> {

    /**
    * @description:只取省管干部（通过机构区分）的退出信息
    * @author:杨波
    * @date:2020-09-15
    *  * @param null
    * @return:
    **/
    List<A30> extractForRegister();
}