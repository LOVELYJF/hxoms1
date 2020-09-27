package com.hxoms.modules.omssmrperson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omssmrperson.entity.OmsSmrRecordInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 省国家保密局备案涉密人员管理
 * @author gaozhenyuan
 * @date 2020/5/12 16:28
 */
@Repository
public interface OmsSmrRecordInfoMapper extends BaseMapper<OmsSmrRecordInfo> {
    //批量添加备案涉密人员
    int insertRecordList(List<OmsSmrRecordInfo> list);

    void deleteByB0100AndYear(String b0100,String importYear);
}