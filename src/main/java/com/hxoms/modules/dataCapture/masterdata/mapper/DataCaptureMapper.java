package com.hxoms.modules.dataCapture.masterdata.mapper;

import com.hxoms.modules.dataCapture.entity.SysLogEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/4/14 9:44
 * @Description:
 ***/
public interface DataCaptureMapper {

    List<Map> getMasterA01(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA01Count();
    void      updateMasterA01(List<Map> updateList);
    void      insertMasterA01(List<Map> insertList);
    void      updateMasterA01ByIdCard(List<Map> idCardListByA01);

    /**
     *  A02表
     * **/
    List<Map> getMasterA02(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA02Count();
    List<Map> getMasterA02();
    void      updateMasterA02(List<Map> updateList);
    void      insertMasterA02(List<Map> insertList);

    /**
    *  A05表
    * **/
    List<Map> getMasterA05(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA05Count();
    void      updateMasterA05(List<Map> updateList);
    void      insertMasterA05(List<Map> insertList);


    /**
     *A06 表
     *
     */
    List<Map> getMasterA06(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA06Count();
    void      updateMasterA06(List<Map> updateList);
    void      insertMasterA06(List<Map> insertList);

    /**
     *A08 表
     *
     */
    List<Map> getMasterA08(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA08Count();
    void      updateMasterA08(List<Map> updateList);
    void      insertMasterA08(List<Map> insertList);


    /**
     *A14 表
     *
     */
    List<Map> getMasterA14(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA14Count();
    void      updateMasterA14(List<Map> updateList);
    void      insertMasterA14(List<Map> insertList);



    /**
     *A15 表
     *
     */
    List<Map> getMasterA15(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA15Count();
    void      updateMasterA15(List<Map> updateList);
    void      insertMasterA15(List<Map> insertList);

    /**
     * A17表
     * **/
    List<Map> getMasterA17(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA17Count();
    void      updateMasterA17(List<Map> updateList);
    void      insertMasterA17(List<Map> insertList);


    /**
     *A36 表
     *
     */
    List<Map> getMasterA36(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA36Count();
    void      updateMasterA36(List<Map> updateList);
    void      insertMasterA36(List<Map> insertList);

    /**
     *A30 表
     *
     */
    List<Map> getMasterA30(@Param("offset") int offset, @Param("rows") int rows);
    Integer getMasterA30Count();
    void      updateMasterA30(List<Map> updateList);
    void      insertMasterA30(List<Map> insertList);
}
