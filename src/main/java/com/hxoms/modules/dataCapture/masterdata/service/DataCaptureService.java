package com.hxoms.modules.dataCapture.masterdata.service;

//import com.hxoms.modules.dataCapture.datasources.annotation.DBChange;

import com.hxoms.modules.dataCapture.entity.DataSource;
import com.hxoms.modules.dataCapture.entity.SysLogEntity;

import java.util.List;
import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/4/14 9:40
 * @Description:
 ***/
public interface DataCaptureService {


    /**
     * A01表的相关操作
     **/
    List<Map> getMasterA01(int offset, int rows);
    int getMasterA01Count();
    void updateMasterA01(List<Map> updateList);
    void insertMasterA01(List<Map> insertList);
    void updateMasterA01ByIdCard(List<Map> idCardListByA01);

    /**
     * A02表的相关操作
     **/
    List<Map> getMasterA02(int offset, int rows);
    int getMasterA02Count();
    void updateMasterA02(List<Map> updateList);
    void insertMasterA02(List<Map> insertList);

    /**
     * A30表的相关操作
     **/
    List<Map> getMasterA30(int offset, int rows);
    int getMasterA30Count();
    void updateMasterA30(List<Map> updateList);
    void insertMasterA30(List<Map> insertList);

    /**
     * 表A05 的相关操作
     **/
    List<Map> getMasterA05(int offset, int rows);
    int getMasterA05Count();
    void updateMasterA05(List<Map> updateList);
    void insertMasterA05(List<Map> insertList);


    /**
     * *  表A06 的相关操作
     * *
     **/

    List<Map> getMasterA06(int offset, int rows);
    int getMasterA06Count();
    void updateMasterA06(List<Map> updateList);
    void insertMasterA06(List<Map> insertList);

    /**
     * 表A08 的相关操作
     **/
    List<Map> getMasterA08(int offset, int rows);
    int getMasterA08Count();
    void updateMasterA08(List<Map> updateList);
    void insertMasterA08(List<Map> insertList);

    /**
     * 表A14的相关操作
     **/
    List<Map> getMasterA14(int offset, int rows);
    int getMasterA14Count();
    void updateMasterA14(List<Map> updateList);
    void insertMasterA14(List<Map> insertList);

    /**
     * 表A15的相关操作
     **/
    List<Map> getMasterA15(int offset, int rows);
    int getMasterA15Count();
    void updateMasterA15(List<Map> updateList);
    void insertMasterA15(List<Map> insertList);

    /**
     * 表A17 的相关操作
     **/
    List<Map> getMasterA17(int offset, int rows);
    int getMasterA17Count();
    void updateMasterA17(List<Map> updateList);
    void insertMasterA17(List<Map> insertList);


    /**
     * 表A36 的相关操作
     **/
    List<Map> getMasterA36(int offset, int rows);
    int getMasterA36Count();
    void updateMasterA36(List<Map> updateList);
    void insertMasterA36(List<Map> insertList);

    /**
     * B01表的相关操作
     **/
    List<Map> getMasterB01(int offset, int rows);
    int getMasterB01Count();
    void updateMasterB01(List<Map> updateList);
    void insertMasterB01(List<Map> insertList);
//    /**
//     * 目标数据源向主数据同步
//     */
//    void   synchronizationData();
}
