package com.hxoms.modules.passportCard.exitEntryManage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory;
import com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.ExitEntrySignInfo;

import java.util.List;

public interface OmsCerExitEntryRepertoryMapper extends BaseMapper<OmsCerExitEntryRepertory> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_exit_entry_repertory
     *
     * @mbg.generated Mon Aug 17 11:30:05 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_exit_entry_repertory
     *
     * @mbg.generated Mon Aug 17 11:30:05 CST 2020
     */
    int insert(OmsCerExitEntryRepertory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_exit_entry_repertory
     *
     * @mbg.generated Mon Aug 17 11:30:05 CST 2020
     */
    int insertSelective(OmsCerExitEntryRepertory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_exit_entry_repertory
     *
     * @mbg.generated Mon Aug 17 11:30:05 CST 2020
     */
    OmsCerExitEntryRepertory selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_exit_entry_repertory
     *
     * @mbg.generated Mon Aug 17 11:30:05 CST 2020
     */
    int updateByPrimaryKeySelective(OmsCerExitEntryRepertory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_cer_exit_entry_repertory
     *
     * @mbg.generated Mon Aug 17 11:30:05 CST 2020
     */
    int updateByPrimaryKey(OmsCerExitEntryRepertory record);

    /**
     * @Desc: 查询证照出入库记录
     * @Author: wangyunquan
     * @Param: [omsCerExitEntryRepertory]
     * @Return: java.util.List<com.hxoms.modules.passportCard.exitEntryManage.entity.OmsCerExitEntryRepertory>
     * @Date: 2020/8/17
     */
    List<OmsCerExitEntryRepertory> selectExitEntryRecord(OmsCerExitEntryRepertory omsCerExitEntryRepertory);

    /**
     * @Desc: 查看签名
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: java.util.List<com.hxoms.modules.passportCard.exitEntryManage.entity.paramterEntity.ExitEntrySignInfo>
     * @Date: 2020/8/17
     */
    List<ExitEntrySignInfo> selectSignById(String id);
}