package com.hxoms.modules.omssmrperson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfo;
import com.hxoms.modules.omssmrperson.entity.OmsSmrOldInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 涉密人员原涉密信息管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@Repository
public interface OmsSmrOldInfoMapper extends BaseMapper<OmsSmrOldInfo> {
    //根据A0100获取涉密人员原涉密信息列表
    List<OmsSmrOldInfo> getSmrOldInfoList(String a0100);

    /**
     * 涉密信息列表
     * @param paramMap 参数
     * @return
     */
    List<OmsSmrOldInfoVO> getSmrOldInfoVOList(Map<String, String> paramMap);
}