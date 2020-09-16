package com.hxoms.modules.passportCard.admintorGet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.admintorGet.entity.OmsCerAdmintorGetApply;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetCerInfo;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetQueryParam;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.CanGetCerInfo;
import com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.PersonInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsCerAdmintorGetApplyMapper extends BaseMapper<OmsCerAdmintorGetApply> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_admintor_get_apply
     *
     * @mbg.generated Tue Aug 18 15:44:52 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_admintor_get_apply
     *
     * @mbg.generated Tue Aug 18 15:44:52 CST 2020
     */
    int insert(OmsCerAdmintorGetApply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_admintor_get_apply
     *
     * @mbg.generated Tue Aug 18 15:44:52 CST 2020
     */
    int insertSelective(OmsCerAdmintorGetApply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_admintor_get_apply
     *
     * @mbg.generated Tue Aug 18 15:44:52 CST 2020
     */
    OmsCerAdmintorGetApply selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_admintor_get_apply
     *
     * @mbg.generated Tue Aug 18 15:44:52 CST 2020
     */
    int updateByPrimaryKeySelective(OmsCerAdmintorGetApply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_admintor_get_apply
     *
     * @mbg.generated Tue Aug 18 15:44:52 CST 2020
     */
    int updateByPrimaryKey(OmsCerAdmintorGetApply record);

    /**
     * @Desc: 查询证照信息
     * @Author: wangyunquan
     * @Param: [admintorGetQueryParam]
     * @Return: java.util.List<com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.AdmintorGetCerInfo>
     * @Date: 2020/8/18
     */
    List<AdmintorGetCerInfo> selectCerInfo(AdmintorGetQueryParam admintorGetQueryParam);

    /**
     * @Desc: 查询人员证照
     * @Author: wangyunquan
     * @Param: [omsId]
     * @Return: java.util.List<com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.PersonInfo>
     * @Date: 2020/9/14
     */
    List<PersonInfo> selectInfoByOmsId(String omsId);

    /**
     * @Desc: 查询打印证照信息
     * @Author: wangyunquan
     * @Param: [ids]
     * @Return: java.util.List<com.hxoms.modules.passportCard.admintorGet.entity.parameterEntiry.CanGetCerInfo>
     * @Date: 2020/9/15
     */
    List<CanGetCerInfo> selectPrintCerInfo(@Param("ids") List<String> ids);
}