package com.hxoms.modules.dataCapture.extractController;

import com.hxoms.modules.dataCapture.dataconfig.service.CutTargetDataSourceService;
import com.hxoms.modules.dataCapture.entity.DataSource;
import com.hxoms.modules.dataCapture.entity.DefultTargetDataSource;
import com.hxoms.modules.dataCapture.extracttable.ExtractData;
import com.hxoms.modules.dataCapture.log.service.SysLogService;
import com.hxoms.modules.dataCapture.masterdata.service.DataCaptureService;
import com.hxoms.modules.dataCapture.synchdata.Synchdata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/testA01")
public class ExtractController {

    @Autowired
    private DataCaptureService a01Service;
    @Autowired
    private CutTargetDataSourceService cutTargetDataSourceService;

    @Autowired
    private DefultTargetDataSource defultTargetDataSource;
    @Autowired
    private ExtractData  extractData;
    @Autowired
    private Synchdata synchdata;

    @Autowired
    private SysLogService sysLogService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/getA01")
    public String getA01() throws ParseException {

        synchdata.synchronizationData();
//      DataSource dataSource = cutTargetDataSourceService.targetDataSource("dataSource","2");
//      if(dataSource == null){
//          dataSource = defultTargetDataSource;
//      }
//      List<Map<String,Object>> mapList =  extractData.getTargetA01(dataSource);
//      log.info("从目标数据源查询的数据条数"+mapList.size());
//      List<Map> masterList =  a01Service.getMasterA01(0,5000);
//      log.info("从主数据源查询的数据条数"+masterList.size());
//      List<Map> mapList1 =  a01Service.getTargetA01(dataSource);
//      log.info("从目标数据源查询的数据条数"+mapList1.size()+"2次");
//      List<Map> masterList1 =  a01Service.getMasterA01();
//      log.info("从主数据源查询的数据条数"+masterList1.size()+"2次");
//        sysLogService.deleteAndsave();
        return "从干综系统抽取数据完成";
    }
}
