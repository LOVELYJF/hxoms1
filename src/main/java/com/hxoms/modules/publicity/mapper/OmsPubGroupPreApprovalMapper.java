package com.hxoms.modules.publicity.mapper;

import com.hxoms.modules.publicity.entity.OmsPubGroupPreApproval;
import com.hxoms.modules.publicity.entity.OmsPubGroupPreApprovalVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OmsPubGroupPreApprovalMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_group_pre_approval
     *
     * @mbg.generated Fri Jul 03 10:32:18 CST 2020
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_group_pre_approval
     *
     * @mbg.generated Fri Jul 03 10:32:18 CST 2020
     */
    int insert(OmsPubGroupPreApproval record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_group_pre_approval
     *
     * @mbg.generated Fri Jul 03 10:32:18 CST 2020
     */
    int insertSelective(OmsPubGroupPreApproval record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_group_pre_approval
     *
     * @mbg.generated Fri Jul 03 10:32:18 CST 2020
     */
    OmsPubGroupPreApprovalVO selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_group_pre_approval
     *
     * @mbg.generated Fri Jul 03 10:32:18 CST 2020
     */
    int updateByPrimaryKeySelective(OmsPubGroupPreApproval record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oms_pub_group_pre_approval
     *
     * @mbg.generated Fri Jul 03 10:32:18 CST 2020
     */
    int updateByPrimaryKey(OmsPubGroupPreApproval record);

    /**
     * 功能描述: <br>
     * 〈查询干教预备案列表〉
     * @Param: [tzmc, status, ztdw, cgsj, hgsj]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubGroupPreApproval>
     * @Author: 李逍遥
     * @Date: 2020/7/7 16:09
     */
    List<OmsPubGroupPreApprovalVO> selectByCondition(@Param("tzmc") String tzmc, @Param("sqzt") List<Integer> sqzt, @Param("ztdw") String ztdw, @Param("cgsj") Date cgsj, @Param("hgsj") Date hgsj);
}