package com.hxoms.modules.dataCapture.masterdata.service.lmpl;

import com.hxoms.modules.dataCapture.entity.SysLogEntity;

import com.hxoms.modules.dataCapture.masterdata.mapper.DataCaptureMapper;
import com.hxoms.modules.dataCapture.masterdata.service.DataCaptureService;
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

    @Override
    public int getMasterA01Count() {
        return dataCaptureMapper.getMasterA01Count();
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

    @Override
    public int getMasterA02Count() {
        return dataCaptureMapper.getMasterA02Count();
    }

    @Override
    public void updateMasterA02(List<Map> updateList) {
        dataCaptureMapper.updateMasterA02(updateList);
    }

    @Override
    public void insertMasterA02(List<Map> insertList) {
        dataCaptureMapper.insertMasterA02(insertList);
    }

    @Override
    public List<Map> getMasterA30(int offset, int rows) {
        return dataCaptureMapper.getMasterA30(offset,rows);
    }

    @Override
    public int getMasterA30Count() {
        return dataCaptureMapper.getMasterA30Count();
    }

    @Override
    public void updateMasterA30(List<Map> updateList) {
        dataCaptureMapper.updateMasterA30(updateList);
    }

    @Override
    public void insertMasterA30(List<Map> insertList) {
        dataCaptureMapper.insertMasterA30(insertList);
    }


    // ########################## A05 #######################################
    @Override
    public List<Map> getMasterA05(int offset,int row) {
        return dataCaptureMapper.getMasterA05(offset,row);
    }

    @Override
    public int getMasterA05Count() {
        return dataCaptureMapper.getMasterA05Count();
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

    @Override
    public int getMasterA06Count() {
        return dataCaptureMapper.getMasterA06Count();
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

    @Override
    public int getMasterA08Count() {
        return dataCaptureMapper.getMasterA08Count();
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

    @Override
    public int getMasterA14Count() {
        return dataCaptureMapper.getMasterA14Count();
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

    @Override
    public int getMasterA15Count() {
        return dataCaptureMapper.getMasterA15Count();
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

    @Override
    public int getMasterA17Count() {
        return dataCaptureMapper.getMasterA17Count();
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

    @Override
    public int getMasterA36Count() {
        return dataCaptureMapper.getMasterA36Count();
    }

    @Override
    public void updateMasterA36(List<Map> updateList) {
        dataCaptureMapper.updateMasterA36(updateList);
    }

    @Override
    public void insertMasterA36(List<Map> insertList) {
        dataCaptureMapper.insertMasterA36(insertList);
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
