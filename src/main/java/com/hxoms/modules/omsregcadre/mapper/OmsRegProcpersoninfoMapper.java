package com.hxoms.modules.omsregcadre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfo;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcpersoninfoVO;
import com.hxoms.modules.omsregcadre.entity.StatisticsCountVo;
import com.hxoms.modules.omsregcadre.entity.paramentity.OmsRegProcpersoninfoIPagParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OmsRegProcpersoninfoMapper extends BaseMapper<OmsRegProcpersoninfo> {

    List<OmsRegProcpersoninfo> selectRegProcpersonInfo(String dataType);

    int batchAddorpInfo(@Param(value = "list")List<OmsRegProcpersoninfo> subList);

    Object selectPersonAndAllowRevoke(OmsRegProcpersoninfo msRegProcpersonInfo);

    OmsRegProcpersoninfo selectRegIdByMap(Map<String, Object> map);

    List<OmsRegProcpersoninfo> selectProcpersoninfoList(OmsRegProcpersoninfo info);

    List<StatisticsCountVo> selectInboudFlagCount();

    List<StatisticsCountVo> selectIdentityCodeCount();

    List<StatisticsCountVo> selectAllFlagCount();

    List<OmsRegProcpersoninfo> selectMergeList(String sortType);

    List<OmsRegProcpersoninfo> selectListById(@Param(value = "ids")List<String> ids);

    List<OmsRegProcpersoninfoVO> selectRegPersonInfoList(OmsRegProcpersoninfoIPagParam personInfoIPagParam);

    int updateRegProcpersoninfo(@Param(value = "info")OmsRegProcpersoninfoIPagParam info);

    List<OmsRegProcpersoninfoVO> searchRevokeRegPersonList(OmsRegProcpersoninfo regProcpersonInfo);

    OmsRegProcpersoninfo selectPersonInfoByIdCard(@Param(value = "name") String name,@Param(value = "idCardNum") String idCardNum,@Param(value = "b0101") String b0101);

    List<String> selectIdnumberByType(String dataType);

    List<Map> selectRegInfoListById(@Param(value = "ids")List<String> ids);

    List<OmsRegProcpersoninfo> selectAllowRevokePerson(String orgId);

    List<OmsRegProcpersoninfo> getSmrPersonInfo(Map<String, Object> param);

    /*
    * @param a0100 省管干部id
    * @return:
            **/
    List<OmsRegProcpersoninfo> selectFamilyByA0100(String a0100);

    /**
     * @author:李姣姣
     * @param personInfoIPagParam
     * @return
     */
    List<OmsRegProcpersoninfo> queryProvinceCadresList(OmsRegProcpersoninfoIPagParam personInfoIPagParam);


}


