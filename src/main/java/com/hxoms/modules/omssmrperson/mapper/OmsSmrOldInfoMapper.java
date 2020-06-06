package com.hxoms.modules.omssmrperson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 涉密人员原涉密信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@Repository
public interface OmsSmrOldInfoMapper extends BaseMapper<OmsSmrOldInfo> {
    //根据A0100获取涉密人员原涉密信息列表
    IPage<OmsSmrOldInfo> getSmrOldInfoList(@Param("page") Page page, @Param("smrOldInfo")OmsSmrOldInfo smrOldInfo);
}