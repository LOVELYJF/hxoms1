package com.hxoms.modules.dataCapture.service.lmpl;

import com.hxoms.modules.dataCapture.datasources.annotation.DBChange;
import com.hxoms.modules.dataCapture.entity.DataSource;
import com.hxoms.modules.dataCapture.entity.SysLogEntity;
import com.hxoms.modules.dataCapture.mapper.DataCaptureMapper;
import com.hxoms.modules.dataCapture.service.DataCaptureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/4/14 9:42
 * @Description:
 ***/
@Service
public class DataCaptureServiceImpl implements DataCaptureService {
    @Autowired
    private DataCaptureMapper dataCaptureMapper;







    @Override
    public List<Map> getMasterA01(int offset,int row) {
        return dataCaptureMapper.getMasterA01(offset,row);
    }

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    @DBChange
    @Override
    public List<Map> getTargetA01(  @DBChange  DataSource dataSource,int minpage,int maxpage) {
        return dataCaptureMapper.getTargetA01(minpage,maxpage);
    }

    @Override
    public int getMasterA01Count() {
        return dataCaptureMapper.getMasterA01Count();
    }


    @DBChange
    @Override
    public int getTargetA01Count(@DBChange DataSource dataSource) {
        return dataCaptureMapper.getTargetA01Count();
    }

    @Override
    public void updateMasterA01(List<Map> updateList) {

        dataCaptureMapper.updateMasterA01(updateList);

    }

    @Override
    public void insertMasterA01(List<Map> insertList) {

        dataCaptureMapper.insertMasterA01(insertList);

    }

    @Override
    public void updateMasterA01ByIdCard(List<Map> idCardListByA01) {

        dataCaptureMapper.updateMasterA01ByIdCard(idCardListByA01);
    }

   // ########################## A02 #######################################




    @Override
    public List<Map> getMasterA02(int offset,int row) {
        return dataCaptureMapper.getMasterA02(offset,row);
    }

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    @DBChange
    @Override
    public List<Map> getTargetA02(  @DBChange  DataSource dataSource,int minpage,int maxpage) {
        return dataCaptureMapper.getTargetA02(minpage,maxpage);
    }

    @Override
    public int getMasterA02Count() {
        return dataCaptureMapper.getMasterA02Count();
    }


    @DBChange
    @Override
    public int getTargetA02Count(@DBChange DataSource dataSource) {
        return dataCaptureMapper.getTargetA02Count();
    }

    @Override
    public void updateMasterA02(List<Map> updateList) {

        dataCaptureMapper.updateMasterA02(updateList);

    }

    @Override
    public void insertMasterA02(List<Map> insertList) {

        dataCaptureMapper.insertMasterA02(insertList);

    }


    // ########################## A05 #######################################



    @Override
    public List<Map> getMasterA05(int offset,int row) {
        return dataCaptureMapper.getMasterA05(offset,row);
    }

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    @DBChange
    @Override
    public List<Map> getTargetA05(  @DBChange  DataSource dataSource,int minpage,int maxpage) {
        return dataCaptureMapper.getTargetA05(minpage,maxpage);
    }

    @Override
    public int getMasterA05Count() {
        return dataCaptureMapper.getMasterA05Count();
    }


    @DBChange
    @Override
    public int getTargetA05Count(@DBChange DataSource dataSource) {
        return dataCaptureMapper.getTargetA05Count();
    }

    @Override
    public void updateMasterA05(List<Map> updateList) {

        dataCaptureMapper.updateMasterA05(updateList);

    }

    @Override
    public void insertMasterA05(List<Map> insertList) {

        dataCaptureMapper.insertMasterA05(insertList);
    }

    // ########################## A06 #######################################

    @Override
    public List<Map> getMasterA06(int offset,int row) {
        return dataCaptureMapper.getMasterA06(offset,row);
    }

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    @DBChange
    @Override
    public List<Map> getTargetA06(  @DBChange  DataSource dataSource,int minpage,int maxpage) {
        return dataCaptureMapper.getTargetA06(minpage,maxpage);
    }

    @Override
    public int getMasterA06Count() {
        return dataCaptureMapper.getMasterA06Count();
    }


    @DBChange
    @Override
    public int getTargetA06Count(@DBChange DataSource dataSource) {
        return dataCaptureMapper.getTargetA06Count();
    }
    @Override
    public void updateMasterA06(List<Map> updateList) {

        dataCaptureMapper.updateMasterA06(updateList);
    }

    @Override
    public void insertMasterA06(List<Map> insertList) {

        dataCaptureMapper.insertMasterA06(insertList);
    }

    // ########################## A08 #######################################
    @Override
    public List<Map> getMasterA08(int offset,int row) {
        return dataCaptureMapper.getMasterA08(offset,row);
    }

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    @DBChange
    @Override
    public List<Map> getTargetA08(  @DBChange  DataSource dataSource,int minpage,int maxpage) {
        return dataCaptureMapper.getTargetA08(minpage,maxpage);
    }

    @Override
    public int getMasterA08Count() {
        return dataCaptureMapper.getMasterA08Count();
    }


    @DBChange
    @Override
    public int getTargetA08Count(@DBChange DataSource dataSource) {
        return dataCaptureMapper.getTargetA08Count();
    }
    @Override
    public void updateMasterA08(List<Map> updateList) {
        dataCaptureMapper.updateMasterA08(updateList);
    }

    @Override
    public void insertMasterA08(List<Map> insertList) {
      dataCaptureMapper.insertMasterA08(insertList);
    }

    // ########################## A14 #######################################

    @Override
    public List<Map> getMasterA14(int offset,int row) {
        return dataCaptureMapper.getMasterA14(offset,row);
    }

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    @DBChange
    @Override
    public List<Map> getTargetA14(  @DBChange  DataSource dataSource,int minpage,int maxpage) {
        return dataCaptureMapper.getTargetA14(minpage,maxpage);
    }

    @Override
    public int getMasterA14Count() {
        return dataCaptureMapper.getMasterA14Count();
    }


    @DBChange
    @Override
    public int getTargetA14Count(@DBChange DataSource dataSource) {
        return dataCaptureMapper.getTargetA14Count();
    }
    @Override
    public void updateMasterA14(List<Map> updateList) {
        dataCaptureMapper.updateMasterA14(updateList);
    }

    @Override
    public void insertMasterA14(List<Map> insertList) {

        dataCaptureMapper.insertMasterA14(insertList);
    }

    // ########################## A15 #######################################


    public List<Map> getMasterA15(int offset,int row) {
        return dataCaptureMapper.getMasterA15(offset,row);
    }

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    @DBChange
    @Override
    public List<Map> getTargetA15(  @DBChange  DataSource dataSource,int minpage,int maxpage) {
        return dataCaptureMapper.getTargetA15(minpage,maxpage);
    }

    @Override
    public int getMasterA15Count() {
        return dataCaptureMapper.getMasterA15Count();
    }


    @DBChange
    @Override
    public int getTargetA15Count(@DBChange DataSource dataSource) {
        return dataCaptureMapper.getTargetA15Count();
    }
    @Override
    public void updateMasterA15(List<Map> updateList) {

        dataCaptureMapper.updateMasterA15(updateList);
    }

    @Override
    public void insertMasterA15(List<Map> insertList) {

        dataCaptureMapper.insertMasterA15(insertList);
    }


    // ########################## A17 #######################################

    @Override
    public List<Map> getMasterA17(int offset,int row) {
        return dataCaptureMapper.getMasterA17(offset,row);
    }

    @DBChange
    @Override
    public List<Map> getTargetA17(@DBChange DataSource dataSource,int minpage,int maxpage) {
        return dataCaptureMapper.getTargetA17(minpage,maxpage);
    }

    @Override
    public int getMasterA17Count() {
        return dataCaptureMapper.getMasterA17Count();
    }

    @DBChange
    @Override
    public int getTargetA17Count(@DBChange DataSource dataSource) {
        return dataCaptureMapper.getTargetA17Count();
    }

    @Override
    public void updateMasterA17(List<Map> updateList) {

        dataCaptureMapper.updateMasterA17(updateList);

    }

    @Override
    public void insertMasterA17(List<Map> insertList) {

        dataCaptureMapper.insertMasterA17(insertList);

    }

    // ########################## A36 #######################################

    public List<Map> getMasterA36(int offset,int row) {
        return dataCaptureMapper.getMasterA36(offset,row);
    }

    /**
     * 通过注解将数据源改为参数指定的。
     * @param dataSource
     * @return
     */
    @DBChange
    @Override
    public List<Map> getTargetA36(  @DBChange  DataSource dataSource,int minpage,int maxpage) {
        return dataCaptureMapper.getTargetA36(minpage,maxpage);
    }

    @Override
    public int getMasterA36Count() {
        return dataCaptureMapper.getMasterA36Count();
    }


    @DBChange
    @Override
    public int getTargetA36Count(@DBChange DataSource dataSource) {
        return dataCaptureMapper.getTargetA36Count();
    }
    @Override
    public void updateMasterA36(List<Map> updateList) {
        dataCaptureMapper.updateMasterA36(updateList);
    }

    @Override
    public void insertMasterA36(List<Map> insertList) {
        dataCaptureMapper.insertMasterA36(insertList);
    }



    @Override
    public void insertSysLog(SysLogEntity sysLogEntity) {

        dataCaptureMapper.insertSysLog(sysLogEntity);
    }


//    /**
//     * 目标数据源向主数据源同步数据
//     */
//    @Override
//    public void synchronizationData() {
//
//
//     diffListMap(targetMap,masterMap);
//
//    }



}
