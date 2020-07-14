package com.hxoms.modules.publicity.destroyReg.mapper;

import com.hxoms.modules.publicity.destroyReg.entity.OmsPubDestroydetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsPubDestroydetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_destroydetail
     *
     * @mbg.generated Thu Jul 02 16:51:24 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_destroydetail
     *
     * @mbg.generated Thu Jul 02 16:51:24 CST 2020
     */
    int insert(OmsPubDestroydetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_destroydetail
     *
     * @mbg.generated Thu Jul 02 16:51:24 CST 2020
     */
    int insertSelective(OmsPubDestroydetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_destroydetail
     *
     * @mbg.generated Thu Jul 02 16:51:24 CST 2020
     */
    OmsPubDestroydetail selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_destroydetail
     *
     * @mbg.generated Thu Jul 02 16:51:24 CST 2020
     */
    int updateByPrimaryKeySelective(OmsPubDestroydetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_destroydetail
     *
     * @mbg.generated Thu Jul 02 16:51:24 CST 2020
     */
    int updateByPrimaryKey(OmsPubDestroydetail record);

    /**
     * @Desc: 批量插入销毁登记明细
     * @Author: wangyunquan
     * @Param: [omsPubDestroydetailList]
     * @Return: int
     * @Date: 2020/7/2
     */
    int insertOmsPubDestroydetailList(@Param("omsPubDestroydetailList") List<OmsPubDestroydetail> omsPubDestroydetailList);
}