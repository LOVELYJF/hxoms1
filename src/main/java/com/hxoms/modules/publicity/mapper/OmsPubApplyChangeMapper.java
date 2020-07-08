package com.hxoms.modules.publicity.mapper;

import com.hxoms.modules.publicity.entity.OmsPubApplyChange;
import org.apache.ibatis.annotations.Param;

public interface OmsPubApplyChangeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_apply_change
     *
     * @mbg.generated Fri Jul 03 09:55:32 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_apply_change
     *
     * @mbg.generated Fri Jul 03 09:55:32 CST 2020
     */
    int insert(OmsPubApplyChange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_apply_change
     *
     * @mbg.generated Fri Jul 03 09:55:32 CST 2020
     */
    int insertSelective(OmsPubApplyChange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_apply_change
     *
     * @mbg.generated Fri Jul 03 09:55:32 CST 2020
     */
    OmsPubApplyChange selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_apply_change
     *
     * @mbg.generated Fri Jul 03 09:55:32 CST 2020
     */
    int updateByPrimaryKeySelective(OmsPubApplyChange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_apply_change
     *
     * @mbg.generated Fri Jul 03 09:55:32 CST 2020
     */
    int updateByPrimaryKey(OmsPubApplyChange record);

    /**
     * 功能描述: <br>
     * 〈通过批文号获取变更前后信息〉
     * @Param: [pwh]
     * @Return: com.hxoms.modules.publicity.entity.OmsPubApplyChange
     * @Author: 李逍遥
     * @Date: 2020/7/6 10:08
     */
    OmsPubApplyChange selectByPrimaryPwh(@Param("pwh") String pwh);

    void deleteByPrimaryBAID(String id);
}