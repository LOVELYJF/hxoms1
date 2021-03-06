package com.hxoms.modules.omssmrperson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    List<OmsSmrOldInfoVO> getSmrOldInfoList(String a0100);

    /**
     * 涉密信息列表
     * @param paramMap 参数a
     * @return
     */
    List<OmsSmrOldInfoVO> getSmrOldInfoVOList(Map<String, String> paramMap);
    /**
    * @description:根据人员ID获取未过脱密期的涉密信息
    * @author:杨波
    * @date:2020-10-10
    *  * @param a0100 人员ID
    * @return:
    **/
    List<OmsSmrOldInfoVO> getSmrOldInfoByA0100(String a0100);

    List<OmsSmrOldInfoVO> getConfirmPeriodList(String orgId,String name,String namePy);
    //获取涉密人员信息维护列表
    List<OmsSmrOldInfoVO> getSmrMaintainList(String orgId,String name,String namePy);
    /**
    * @description:获取涉密信息记录条数
    * @author:杨波
    * @date:2020-09-19
    *  * @param null
    * @return:
    **/
    String getSmrCount();
    //获取差异数据列表
    List<OmsSmrOldInfoVO> getDifferentData(@Param("importYear") String importYear,@Param("b0100") String b0100);
}