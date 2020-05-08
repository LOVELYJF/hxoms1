package com.hxoms.modules.dataCapture.datasources.synchronizationData;

import com.hxoms.modules.dataCapture.entity.DataSource;
import com.hxoms.modules.dataCapture.entity.DefultTargetDataSource;
import com.hxoms.modules.dataCapture.datasources.service.CutTargetDataSourceService;
import com.hxoms.modules.dataCapture.entity.DynamicData;
import com.hxoms.modules.dataCapture.service.DataCaptureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @authore:wjf
 * @data 2020/4/17 9:22
 * @Description:
 ***/
@Component
public class SynchronizationData {


    @Autowired
    private DataCaptureService a01Service;

    @Autowired
    private CutTargetDataSourceService cutTargetDataSourceService;

    @Autowired
    private DefultTargetDataSource defultTargetDataSource;
    @Autowired
    private DynamicData dynamicData;
    @Autowired
    private InsertAndUpdate insertAndUpdate;
    // 需要插入的集合
    private List<Map> insertList ;
    // 需要修改的集合
    private List<Map>  updateList ;
    // 需要对比身份证的集合
    private List<Map> idCardListByA01;

 //  private List<String> idCards = new ArrayList<>();


    private Set<String> idCards = new HashSet<>();

    private final Logger log = LoggerFactory.getLogger(getClass());


    public void synchronizationData(){
        DataSource dataSource = cutTargetDataSourceService.targetDataSource("dataSource","2");
        if(dataSource == null){
            dataSource = defultTargetDataSource;
        }

//        List<Map> targetMap =  a01Service.getTargetA01(dataSource);
//        List<Map> masterMap =  a01Service.getMasterA01();
//        diffListMap(targetMap,masterMap,"a0100");
        int a01counts =  a01Service.getMasterA01Count();

        int a01targetCounts = a01Service.getTargetA01Count(dataSource);


        List<Map> targetMapA01  =splicCountTarget(dataSource,a01targetCounts,"a0100");
        List<Map>   masterMapA01 =   splicCount(a01counts,"a0100");


        diffListMap(targetMapA01,masterMapA01,"a0100");
//
//
//
//       List<Map> targetMapA02 =  a01Service.getTargetA02(dataSource);
//        List<Map> masterMapA02 =  a01Service.getMasterA02();
//        diffListMap(targetMapA02,masterMapA02,"a0200");

        int a02counts =  a01Service.getMasterA02Count();

        int a02targetCounts = a01Service.getTargetA02Count(dataSource);


        List<Map> targetMapA02  =splicCountTarget(dataSource,a02targetCounts,"a0200");
        List<Map>   masterMapA02 =   splicCount(a02counts,"a0200");


        diffListMap(targetMapA02,masterMapA02,"a0200");
//
//
//        List<Map> targetMapA05 =  a01Service.getTargetA05(dataSource);
//        List<Map> masterMapA05 =  a01Service.getMasterA05();
//        diffListMap(targetMapA05,masterMapA05,"a0500");

        int a05counts =  a01Service.getMasterA05Count();

        int a05targetCounts = a01Service.getTargetA05Count(dataSource);


        List<Map> targetMapA05  =splicCountTarget(dataSource,a05targetCounts,"a0500");
        List<Map>   masterMapA05 =   splicCount(a05counts,"a0500");


        diffListMap(targetMapA05,masterMapA05,"a0500");
//
//        List<Map> targetMapA06 =  a01Service.getTargetA06(dataSource);
//        List<Map> masterMapA06 =  a01Service.getMasterA06();
//        diffListMap(targetMapA06,masterMapA06,"a0600");

        int a06counts =  a01Service.getMasterA06Count();

        int a06targetCounts = a01Service.getTargetA06Count(dataSource);


        List<Map> targetMapA06  =splicCountTarget(dataSource,a06targetCounts,"a0600");
        List<Map>   masterMapA06 =   splicCount(a06counts,"a0600");


        diffListMap(targetMapA06,masterMapA06,"a0600");
//
//
//        List<Map> targetMapA08 =  a01Service.getTargetA08(dataSource);
//        List<Map> masterMapA08 =  a01Service.getMasterA08();
//        diffListMap(targetMapA08,masterMapA08,"a0800");

        int a08counts =  a01Service.getMasterA08Count();

        int a08targetCounts = a01Service.getTargetA08Count(dataSource);


        List<Map> targetMapA08  =splicCountTarget(dataSource,a08targetCounts,"a0800");
        List<Map>   masterMapA08 =   splicCount(a08counts,"a0800");


        diffListMap(targetMapA08,masterMapA08,"a0800");
//
//
//        List<Map> targetMapA14 =  a01Service.getTargetA14(dataSource);
//        List<Map> masterMapA14 =  a01Service.getMasterA14();
//        diffListMap(targetMapA14,masterMapA14,"a1400");

        int a14counts =  a01Service.getMasterA14Count();

        int a14targetCounts = a01Service.getTargetA14Count(dataSource);


        List<Map> targetMapA14  =splicCountTarget(dataSource,a14targetCounts,"a1400");
        List<Map>   masterMapA14 =   splicCount(a14counts,"a1400");


        diffListMap(targetMapA14,masterMapA14,"a1400");
//
//
//        List<Map> targetMapA15 =  a01Service.getTargetA15(dataSource);
//        List<Map> masterMapA15 =  a01Service.getMasterA15();
//        diffListMap(targetMapA15,masterMapA15,"a1500");

        int a15counts =  a01Service.getMasterA15Count();

        int a15targetCounts = a01Service.getTargetA15Count(dataSource);


        List<Map> targetMapA15  =splicCountTarget(dataSource,a15targetCounts,"a1500");
        List<Map>   masterMapA15 =   splicCount(a15counts,"a1500");


        diffListMap(targetMapA15,masterMapA15,"a1500");

        int a17counts =  a01Service.getMasterA17Count();

        int a17targetCounts = a01Service.getTargetA17Count(dataSource);

//        List<Map> targetMapA17 =  a01Service.getTargetA17(dataSource);
//        List<Map> masterMapA17 =  a01Service.getMasterA17();
        List<Map> targetMapA17  =splicCountTarget(dataSource,a17targetCounts,"a1700");
        List<Map>   masterMapA17 =   splicCount(a17counts,"a1700");


        diffListMap(targetMapA17,masterMapA17,"a1700");

//
//        List<Map> targetMapA36 =  a01Service.getTargetA36(dataSource);
//        List<Map> masterMapA36 =  a01Service.getMasterA36();
//        diffListMap(targetMapA36,masterMapA36,"a3600");
        int a36counts =  a01Service.getMasterA36Count();

        int a36targetCounts = a01Service.getTargetA36Count(dataSource);


        List<Map> targetMapA36  =splicCountTarget(dataSource,a36targetCounts,"a3600");
        List<Map>   masterMapA36 =   splicCount(a36counts,"a3600");


        diffListMap(targetMapA36,masterMapA36,"a3600");


    }

//    @Transactional
    //TODO
    public   void diffListMap(List<Map> targetMapList,List<Map> masterMapList,String tabelName){

     if(targetMapList != null && targetMapList.size() > 0){

       // String  sameMajorkey =tabelName;

     //   List<String> sameA0100 = new ArrayList();
          Set<String> sameA0100 = new HashSet<>();

        if(masterMapList != null && masterMapList.size() > 0 ){

            for(Map masterMap : masterMapList){

                String a0100 =(String) masterMap.get(tabelName);
                sameA0100.add(a0100);


                if("a0100".equals(tabelName)){  //a01表需要身份证判断
                    String a0184 =(String) masterMap.get("a0184");
                    idCards.add(a0184);
                }

             }

           }else {

            log.info("主数据源为空，直接执行插入操作");

          }

        updateList =  targetMapList.stream().filter((Map m) ->sameA0100.contains(m.get(tabelName)) ).collect(Collectors.toList());

        log.info("需要修改的集合长度为"+updateList.size()+"\t"+tabelName);

        if("a0100".equals(tabelName)){
            // 对A01单独处理，因为A01表有身份证号 当主键不一致的情况，判断身份证是否相同 插入情况(主键不一致，& 身份证号也不相同)
//            insertList =  targetMapList.stream().filter((Map m) ->(!sameA0100.contains(m.get(tabelName)) && !idCards.contains(m.get("a0184"))) ).collect(Collectors.toList());
            insertList =  targetMapList.stream().filter((Map m) ->(!sameA0100.contains(m.get(tabelName)) && !idCards.contains(m.get("a0184")!=null?m.get("a0184"):"身份证为空")   )).collect(Collectors.toList());

            idCardListByA01 =  targetMapList.stream().filter((Map m) ->(!sameA0100.contains(m.get(tabelName)) && idCards.contains(m.get("a0184")!=null?m.get("a0184"):"身份证为空") )).collect(Collectors.toList());

            log.info("需要进行身份证号修改集合长度为"+idCardListByA01.size()+"\t"+tabelName);

        }else{

            insertList =  targetMapList.stream().filter((Map m) ->!sameA0100.contains(m.get(tabelName)) ).collect(Collectors.toList());

        }


        log.info("需要插入的集合长度为"+insertList.size()+"\t"+tabelName);

        if (insertList != null && insertList.size() > 0) {

            insertAndUpdate.insertTable(tabelName,insertList);

//            a01Service.insertMasterA01(insertList);
        }
        if(updateList!=null && updateList.size()>0){
            insertAndUpdate.upDataTable(tabelName,updateList,idCardListByA01,idCards);
//            idCards.clear();
//            a01Service.updateMasterA01(updateList);
        }





        }else{


            log.info("目标数据源为空不需要任何操作");
        }

    }



  //TODO 分页查询 (Mysql)
  public List<Map>  splicCount(int counts,String tableName){

        //以每次 2500 条 为基准
      int splic = dynamicData.getInitSplic();
      int parts =   counts/splic +1;
      //偏移量
      int offset =0;
      // 返回记录最大 行数
      int rows  = splic;

      List<Map> masterListMap = new ArrayList<>();

         // 切割成了几份
      for(int i=1;i<= parts; i++){

          offset = (i-1)*splic;

          switch(tableName){
              case "a0100" :

                  //语句

                  List<Map> masterMapA01 =  a01Service.getMasterA01(offset,rows);

                  masterListMap.addAll(masterMapA01);

                  //   diffListMap(targetMapA17,masterMapA17,"a1700");
                  break;

              case "a0200" :

                  //语句

                  List<Map> masterMapA02 =  a01Service.getMasterA02(offset,rows);

                  masterListMap.addAll(masterMapA02);

                  //   diffListMap(targetMapA17,masterMapA17,"a1700");
                  break;

              case "a0500" :

                  //语句

                  List<Map> masterMapA05 =  a01Service.getMasterA05(offset,rows);

                  masterListMap.addAll(masterMapA05);

                  //   diffListMap(targetMapA17,masterMapA17,"a1700");
                  break;
              case "a0600" :

                  //语句

                  List<Map> masterMapA06 =  a01Service.getMasterA06(offset,rows);

                  masterListMap.addAll(masterMapA06);

                  //   diffListMap(targetMapA17,masterMapA17,"a1700");
                  break;
              case "a0800" :

                  //语句

                  List<Map> masterMapA08 =  a01Service.getMasterA08(offset,rows);

                  masterListMap.addAll(masterMapA08);

                  //   diffListMap(targetMapA17,masterMapA17,"a1700");
                  break;
              case "a1400" :

                  //语句

                  List<Map> masterMapA14 =  a01Service.getMasterA14(offset,rows);

                  masterListMap.addAll(masterMapA14);

                  //   diffListMap(targetMapA17,masterMapA17,"a1700");
                  break;
              case "a1500" :

                  //语句

                  List<Map> masterMapA15 =  a01Service.getMasterA15(offset,rows);

                  masterListMap.addAll(masterMapA15);

                  //   diffListMap(targetMapA17,masterMapA17,"a1700");
                  break;
              case "a1700" :

                  //语句

               List<Map> masterMapA17 =  a01Service.getMasterA17(offset,rows);

               masterListMap.addAll(masterMapA17);

                  //   diffListMap(targetMapA17,masterMapA17,"a1700");
                  break;

              case "a3600" :

                  //语句
//                  a01Service.updateMasterA36(updateList);
                  List<Map> masterMapA36 =  a01Service.getMasterA36(offset,rows);

                  masterListMap.addAll(masterMapA36);

                  //   diffListMap(targetMapA17,masterMapA17,"a1700");
                  break;

              default :

                  //语句

          }



          }

      //        List<Map> targetMapA17 =  a01Service.getTargetA17(dataSource);
//        List<Map> masterMapA17 =  a01Service.getMasterA17();



      //   diffListMap(targetMapA17,masterMapA17,"a1700");




    return  masterListMap;


  }

    //TODO 分页查询 (orcal)
    public List<Map>  splicCountTarget(DataSource dataSource,int counts,String tableName){

        //以每次 2500 条 为基准
        int splic = dynamicData.getInitSplic();
        int parts =   counts/splic +1;
        //偏移量
        int minpage =0;
        // 返回记录最大 行数
        int maxpage  = 0;

        List<Map> targetListMap = new ArrayList<>();

        // 切割成了几份
        for(int i=1;i<= parts; i++){

            minpage = (i-1)*splic;
            maxpage = i*splic;

            switch(tableName){

                case "a0100" :

                    //语句

                    List<Map> targetMapA01 =  a01Service.getTargetA01(dataSource,minpage,maxpage);

                    targetListMap.addAll(targetMapA01);

                    //   diffListMap(targetMapA17,masterMapA17,"a1700");
                    break;

                case "a0200" :

                    //语句

                    List<Map> targetMapA02 =  a01Service.getTargetA02(dataSource,minpage,maxpage);

                    targetListMap.addAll(targetMapA02);

                    //   diffListMap(targetMapA17,masterMapA17,"a1700");
                    break;

                case "a0500" :

                    //语句

                    List<Map> targetMapA05 =  a01Service.getTargetA05(dataSource,minpage,maxpage);

                    targetListMap.addAll(targetMapA05);

                    //   diffListMap(targetMapA17,masterMapA17,"a1700");
                    break;
                case "a0600" :

                    //语句

                    List<Map> targetMapA06 =  a01Service.getTargetA06(dataSource,minpage,maxpage);

                    targetListMap.addAll(targetMapA06);

                    //   diffListMap(targetMapA17,masterMapA17,"a1700");
                    break;

                case "a0800" :

                    //语句

                    List<Map> targetMapA08 =  a01Service.getTargetA08(dataSource,minpage,maxpage);

                    targetListMap.addAll(targetMapA08);

                    //   diffListMap(targetMapA17,masterMapA17,"a1700");
                    break;
                case "a1400" :

                    //语句

                    List<Map> targetMapA14 =  a01Service.getTargetA14(dataSource,minpage,maxpage);

                    targetListMap.addAll(targetMapA14);

                    //   diffListMap(targetMapA17,masterMapA17,"a1700");
                    break;
                case "a1500" :

                    //语句

                    List<Map> targetMapA15 =  a01Service.getTargetA15(dataSource,minpage,maxpage);

                    targetListMap.addAll(targetMapA15);

                    //   diffListMap(targetMapA17,masterMapA17,"a1700");
                    break;

                case "a1700" :

                    //语句

                    List<Map> targetMapA17 =  a01Service.getTargetA17(dataSource,minpage,maxpage);

                    targetListMap.addAll(targetMapA17);

                    //   diffListMap(targetMapA17,masterMapA17,"a1700");
                    break;

                case "a3600" :

                    //语句
//                    a01Service.updateMasterA36(updateList);
                    List<Map> targetMapA36 =  a01Service.getTargetA36(dataSource,minpage,maxpage);

                    targetListMap.addAll(targetMapA36);

                    //   diffListMap(targetMapA17,masterMapA17,"a1700");
                    break;

                default :

                    //语句

            }



        }

        //        List<Map> targetMapA17 =  a01Service.getTargetA17(dataSource);
//        List<Map> masterMapA17 =  a01Service.getMasterA17();



        //   diffListMap(targetMapA17,masterMapA17,"a1700");




        return  targetListMap;


    }

   //TODO 统一修改方法
//   public void updateTable(String tableTame){
//
//       List<List<Map>> splicUpdateList = fixedGrouping(updateList,dynamicData.getInitSplic());
//       if(splicUpdateList !=null && splicUpdateList.size()>0){
//
//           for(int i =0;i<splicUpdateList.size();i++){
//
//               a01Service.updateMasterA17(splicUpdateList.get(i));
//
//           }
//
//       }
//   }







   public static void main(String[] args) {


//        List a = new ArrayList();
//        a.add(1);
//        a.add(2);
//
//        if(a.contains(null)){
//            System.out.println(true);
//
//        }else{
//            System.out.println(false);
//        }
//
//
//        Map  map = new HashMap();

       //以每次 2500 条 为基准
//       int splic = 2500;
//       int parts =   55667/splic +1;
//
//       int fristCount =0;
//
//       int lastCount = splic;
//
//
//       // 切割成了几份
//       for(int i=1;i<= parts; i++){
//
//           fristCount = (i-1)*splic;
//
//
//           System.out.println("fristCount : lastCount" + fristCount+"\t" + lastCount);
//
//
//
//
//       }

       List list = new ArrayList();
       Map map = new HashMap();

       System.out.println(list.contains(2));
       System.out.println(map.get("wjf"));

       String str ="w";


   }









}
