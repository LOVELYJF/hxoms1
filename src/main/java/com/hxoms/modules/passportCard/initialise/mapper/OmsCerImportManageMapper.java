package com.hxoms.modules.passportCard.initialise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.initialise.entity.OmsCerImportManage;

public interface OmsCerImportManageMapper extends BaseMapper<OmsCerImportManage> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_import_manage
     *
     * @mbg.generated Wed Aug 26 10:21:47 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_import_manage
     *
     * @mbg.generated Wed Aug 26 10:21:47 CST 2020
     */
    int insert(OmsCerImportManage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_import_manage
     *
     * @mbg.generated Wed Aug 26 10:21:47 CST 2020
     */
    int insertSelective(OmsCerImportManage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_import_manage
     *
     * @mbg.generated Wed Aug 26 10:21:47 CST 2020
     */
    OmsCerImportManage selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_import_manage
     *
     * @mbg.generated Wed Aug 26 10:21:47 CST 2020
     */
    int updateByPrimaryKeySelective(OmsCerImportManage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_import_manage
     *
     * @mbg.generated Wed Aug 26 10:21:47 CST 2020
     */
    int updateByPrimaryKey(OmsCerImportManage record);
}