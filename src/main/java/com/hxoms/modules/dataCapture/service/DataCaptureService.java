package com.hxoms.modules.dataCapture.service;

//import com.hxoms.modules.dataCapture.datasources.annotation.DBChange;
import com.hxoms.modules.dataCapture.datasources.annotation.DBChange;
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
    *
    * **/

   List<Map> getMasterA01(int offset, int rows);

   /**
    * 通过注解将数据源改为参数指定的。
    * @param dataSource
    * @return
   */
   List<Map> getTargetA01(@DBChange DataSource dataSource, int minpage, int maxpage);

   int getMasterA01Count();

   int getTargetA01Count(@DBChange DataSource dataSource);


    void      updateMasterA01(List<Map> updateList);

    void      insertMasterA01(List<Map> insertList);

    void      updateMasterA01ByIdCard(List<Map> idCardListByA01);

    /**
     * A02表的相关操作
     *
     * **/
    List<Map> getMasterA02(int offset, int rows);

    /**
    * 通过注解将数据源改为参数指定的。
     * @param dataSource
    * @return
    */
    List<Map> getTargetA02(@DBChange DataSource dataSource, int minpage, int maxpage);

    int getMasterA02Count();

    int getTargetA02Count(@DBChange DataSource dataSource);


    void      updateMasterA02(List<Map> updateList);

    void      insertMasterA02(List<Map> insertList);

    /**
     *  表A05 的相关操作
     *
     * **/

    List<Map> getMasterA05(int offset, int rows);

   /**
    * 通过注解将数据源改为参数指定的。
    * @param dataSource
    * @return
  */
  List<Map> getTargetA05(@DBChange DataSource dataSource, int minpage, int maxpage);

  int getMasterA05Count();

  int getTargetA05Count(@DBChange DataSource dataSource);



    void      updateMasterA05(List<Map> updateList);

    void      insertMasterA05(List<Map> insertList);


    /**
     * *  表A06 的相关操作
    ** **/

    List<Map> getMasterA06(int offset, int rows);

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    List<Map> getTargetA06(@DBChange DataSource dataSource, int minpage, int maxpage);

    int getMasterA06Count();

    int getTargetA06Count(@DBChange DataSource dataSource);


     void      updateMasterA06(List<Map> updateList);

     void      insertMasterA06(List<Map> insertList);

    /**
      *  表A08 的相关操作
      *
     * **/
    List<Map> getMasterA08(int offset, int rows);

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    List<Map> getTargetA08(@DBChange DataSource dataSource, int minpage, int maxpage);

    int getMasterA08Count();

    int getTargetA08Count(@DBChange DataSource dataSource);


    void      updateMasterA08(List<Map> updateList);

    void      insertMasterA08(List<Map> insertList);


    /**
     *  表A14的相关操作
     *
     * **/

    List<Map> getMasterA14(int offset, int rows);

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    List<Map> getTargetA14(@DBChange DataSource dataSource, int minpage, int maxpage);

    int getMasterA14Count();

    int getTargetA14Count(@DBChange DataSource dataSource);


    void      updateMasterA14(List<Map> updateList);

    void      insertMasterA14(List<Map> insertList);

    /**
     *  表A15的相关操作
     *
     * **/

    List<Map> getMasterA15(int offset, int rows);

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    List<Map> getTargetA15(@DBChange DataSource dataSource, int minpage, int maxpage);

    int getMasterA15Count();

    int getTargetA15Count(@DBChange DataSource dataSource);


    void      updateMasterA15(List<Map> updateList);

    void      insertMasterA15(List<Map> insertList);

   /**
    *  表A17 的相关操作
    *
    *
    * **/
    List<Map> getMasterA17(int offset, int rows);

   /**
    * 通过注解将数据源改为参数指定的。
    * @param dataSource
    * @return
    */
    List<Map> getTargetA17(@DBChange DataSource dataSource, int minpage, int maxpage);

    int getMasterA17Count();

    int getTargetA17Count(@DBChange DataSource dataSource);


    void      updateMasterA17(List<Map> updateList);

    void      insertMasterA17(List<Map> insertList);


    /**
     *  表A36 的相关操作
     *
     *
      * **/
    List<Map> getMasterA36(int offset, int rows);

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    List<Map> getTargetA36(@DBChange DataSource dataSource, int minpage, int maxpage);

    int getMasterA36Count();

    int getTargetA36Count(@DBChange DataSource dataSource);


    void      updateMasterA36(List<Map> updateList);

    void      insertMasterA36(List<Map> insertList);

    // 日志

    void     insertSysLog(SysLogEntity sysLogEntity);

//    /**
//     * 目标数据源向主数据同步
//     */
//    void   synchronizationData();
}
