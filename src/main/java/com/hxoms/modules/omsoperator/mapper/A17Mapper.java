package com.hxoms.modules.omsoperator.mapper;

import com.hxoms.modules.omsoperator.entity.A17;

public interface A17Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table a17
     *
     * @mbg.generated Sun Sep 27 19:59:18 CST 2020
     */
    int deleteByPrimaryKey(String a1700);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table a17
     *
     * @mbg.generated Sun Sep 27 19:59:18 CST 2020
     */
    int insert(A17 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table a17
     *
     * @mbg.generated Sun Sep 27 19:59:18 CST 2020
     */
    int insertSelective(A17 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table a17
     *
     * @mbg.generated Sun Sep 27 19:59:18 CST 2020
     */
    A17 selectByPrimaryKey(String a1700);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table a17
     *
     * @mbg.generated Sun Sep 27 19:59:18 CST 2020
     */
    int updateByPrimaryKeySelective(A17 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table a17
     *
     * @mbg.generated Sun Sep 27 19:59:18 CST 2020
     */
    int updateByPrimaryKeyWithBLOBs(A17 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table a17
     *
     * @mbg.generated Sun Sep 27 19:59:18 CST 2020
     */
    int updateByPrimaryKey(A17 record);
}