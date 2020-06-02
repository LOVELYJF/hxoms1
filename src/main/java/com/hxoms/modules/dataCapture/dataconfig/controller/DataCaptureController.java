package com.hxoms.modules.dataCapture.dataconfig.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/4/14 9:44
 * @Description:
 ***/
@RestController
@RequestMapping("/testA01")
public class DataCaptureController {
//    @Autowired
//    private DataCaptureService a01Service;
//    @Autowired
//    private CutTargetDataSourceService cutTargetDataSourceService;
//    @Autowired
//    private DefultTargetDataSource defultTargetDataSource;
//
//    @Autowired
//    private SynchronizationData synchronizationData;
//
//    private final Logger log = LoggerFactory.getLogger(getClass());
//
//    @RequestMapping("/getA01")
//    public List<Map> getA01(){
//
////      DataSource dataSource = cutTargetDataSourceService.targetDataSource("dataSource","2");
////      if(dataSource == null){
////          dataSource = defultTargetDataSource;
////      }
////      List<Map> mapList =  a01Service.getTargetA01(dataSource);
////      log.info("从目标数据源查询的数据条数"+mapList.size());
////      List<Map> masterList =  a01Service.getMasterA01();
////      log.info("从主数据源查询的数据条数"+masterList.size());
////      List<Map> mapList1 =  a01Service.getTargetA01(dataSource);
////      log.info("从目标数据源查询的数据条数"+mapList1.size()+"2次");
////      List<Map> masterList1 =  a01Service.getMasterA01();
////      log.info("从主数据源查询的数据条数"+masterList1.size()+"2次");
//        synchronizationData.synchronizationData();
//     return a01Service.getMasterA01(0,100000);
//    }


}
