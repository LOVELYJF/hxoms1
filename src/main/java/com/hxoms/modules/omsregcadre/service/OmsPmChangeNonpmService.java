package com.hxoms.modules.omsregcadre.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.omsregcadre.entity.OmsEntryexitRecord;
import com.hxoms.modules.omsregcadre.entity.OmsPmChangeNonpm;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsPmChangeNonpmIPageParam;

import java.util.Map;

public interface OmsPmChangeNonpmService {


    Object insertPmChangeNonpmApply(OmsPmChangeNonpm pmChangeNonpm);

    PageInfo<OmsPmChangeNonpm> getPmChangeNonpminfo(OmsPmChangeNonpmIPageParam pmChangeNonpmIPageParam);
}
