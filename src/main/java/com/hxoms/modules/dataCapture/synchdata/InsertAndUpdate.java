package com.hxoms.modules.dataCapture.synchdata;

import com.hxoms.modules.dataCapture.entity.DynamicData;
import com.hxoms.modules.dataCapture.log.annotation.SysLog;
import com.hxoms.modules.dataCapture.masterdata.service.DataCaptureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class InsertAndUpdate {

    @Autowired
    private DataCaptureService a01Service;


    @Autowired
    private DynamicData dynamicData;

    @SysLog("插入数据")
    public void insertTable(String tableName,List<Map> insertList){

        switch(tableName){

            case "a0100" :

                //语句
            //    a01Service.insertMasterA01(insertList);
                List<List<Map>> splicUpdateListA01 = fixedGrouping(insertList,dynamicData.getInitSplic());
                if(splicUpdateListA01 !=null && splicUpdateListA01.size()>0){

                    for(int i =0;i<splicUpdateListA01.size();i++){

                        a01Service.insertMasterA01(splicUpdateListA01.get(i));

                    }

                }
                break;
            case "a0200" :

                //语句
//                a01Service.insertMasterA02(insertList);
                List<List<Map>> splicUpdateListA02 = fixedGrouping(insertList,dynamicData.getInitSplic());
                if(splicUpdateListA02 !=null && splicUpdateListA02.size()>0){

                    for(int i =0;i<splicUpdateListA02.size();i++){

                        a01Service.insertMasterA02(splicUpdateListA02.get(i));

                    }

                }

                break;

            case "a0500" :

                //语句
//                a01Service.insertMasterA05(insertList);
                List<List<Map>> splicUpdateListA05 = fixedGrouping(insertList,dynamicData.getInitSplic());
                if(splicUpdateListA05 !=null && splicUpdateListA05.size()>0){

                    for(int i =0;i<splicUpdateListA05.size();i++){

                        a01Service.insertMasterA05(splicUpdateListA05.get(i));

                    }

                }
                break;
            case "a0600" :

                //语句
//                a01Service.insertMasterA06(insertList);
                List<List<Map>> splicUpdateListA06 = fixedGrouping(insertList,dynamicData.getInitSplic());
                if(splicUpdateListA06 !=null && splicUpdateListA06.size()>0){

                    for(int i =0;i<splicUpdateListA06.size();i++){

                        a01Service.insertMasterA06(splicUpdateListA06.get(i));

                    }

                }
                break;
            case "a0800" :

                //语句
//                a01Service.insertMasterA08(insertList);
                List<List<Map>> splicUpdateListA08 = fixedGrouping(insertList,dynamicData.getInitSplic());
                if(splicUpdateListA08 !=null && splicUpdateListA08.size()>0){

                    for(int i =0;i<splicUpdateListA08.size();i++){

                        a01Service.insertMasterA08(splicUpdateListA08.get(i));

                    }

                }
                break;
            case "a1400" :

                //语句
//                a01Service.insertMasterA14(insertList);
                List<List<Map>> splicUpdateListA14 = fixedGrouping(insertList,dynamicData.getInitSplic());
                if(splicUpdateListA14 !=null && splicUpdateListA14.size()>0){

                    for(int i =0;i<splicUpdateListA14.size();i++){

                        a01Service.insertMasterA14(splicUpdateListA14.get(i));

                    }

                }
                break;
            case "a1500" :

                //语句
//                a01Service.insertMasterA15(insertList);
                List<List<Map>> splicUpdateListA15 = fixedGrouping(insertList,dynamicData.getInitSplic());
                if(splicUpdateListA15 !=null && splicUpdateListA15.size()>0){

                    for(int i =0;i<splicUpdateListA15.size();i++){

                        a01Service.insertMasterA15(splicUpdateListA15.get(i));

                    }

                }
                break;
            case "a1700" :

                //语句
//                a01Service.insertMasterA17(insertList);
                List<List<Map>> splicUpdateListA17 = fixedGrouping(insertList,dynamicData.getInitSplic());
                if(splicUpdateListA17 !=null && splicUpdateListA17.size()>0){

                    for(int i =0;i<splicUpdateListA17.size();i++){

                        a01Service.insertMasterA17(splicUpdateListA17.get(i));

                    }

                }

                break;
            case "a3600" :

                //语句
//                a01Service.insertMasterA36(insertList);
                List<List<Map>> splicUpdateListA36 = fixedGrouping(insertList,dynamicData.getInitSplic());
                if(splicUpdateListA36 !=null && splicUpdateListA36.size()>0){

                    for(int i =0;i<splicUpdateListA36.size();i++){

                        a01Service.insertMasterA36(splicUpdateListA36.get(i));

                    }

                }
                break;
            default :

                //语句

        }
    }

    @SysLog("修改数据")
    public void upDataTable(String tableName, List<Map> updateList, List<Map> idCardListByA01){

        switch(tableName){

            case "a0100" :

                //语句
//                a01Service.updateMasterA01(updateList);
                List<List<Map>> splicUpdateListA01 = fixedGrouping(updateList,dynamicData.getInitSplic());
                if(splicUpdateListA01 !=null && splicUpdateListA01.size()>0){

                    for(int i =0;i<splicUpdateListA01.size();i++){

                        a01Service.updateMasterA01(splicUpdateListA01.get(i));

                    }

                }

                if(idCardListByA01 != null && idCardListByA01.size() > 0){

                a01Service.updateMasterA01ByIdCard(idCardListByA01);
                }
                //idCards.clear();
                break;
            case "a0200" :

                //语句
//                a01Service.updateMasterA02(updateList);
                List<List<Map>> splicUpdateListA02 = fixedGrouping(updateList,dynamicData.getInitSplic());
                if(splicUpdateListA02 !=null && splicUpdateListA02.size()>0){

                    for(int i =0;i<splicUpdateListA02.size();i++){

                        a01Service.updateMasterA02(splicUpdateListA02.get(i));

                    }

                }
                break;
            case "a0500" :

                //语句
//                a01Service.updateMasterA05(updateList);
                List<List<Map>> splicUpdateListA05 = fixedGrouping(updateList,dynamicData.getInitSplic());
                if(splicUpdateListA05 !=null && splicUpdateListA05.size()>0){

                    for(int i =0;i<splicUpdateListA05.size();i++){

                        a01Service.updateMasterA05(splicUpdateListA05.get(i));

                    }

                }
                break;
            case "a0600" :

                //语句
//                a01Service.updateMasterA06(updateList);
                List<List<Map>> splicUpdateListA06 = fixedGrouping(updateList,dynamicData.getInitSplic());
                if(splicUpdateListA06 !=null && splicUpdateListA06.size()>0){

                    for(int i =0;i<splicUpdateListA06.size();i++){

                        a01Service.updateMasterA06(splicUpdateListA06.get(i));

                    }

                }

                break;
            case "a0800" :

                //语句
//                a01Service.updateMasterA08(updateList);
                List<List<Map>> splicUpdateListA08 = fixedGrouping(updateList,dynamicData.getInitSplic());
                if(splicUpdateListA08 !=null && splicUpdateListA08.size()>0){

                    for(int i =0;i<splicUpdateListA08.size();i++){

                        a01Service.updateMasterA08(splicUpdateListA08.get(i));

                    }

                }
                break;
            case "a1400" :

                //语句
//                a01Service.updateMasterA14(updateList);
                List<List<Map>> splicUpdateListA14 = fixedGrouping(updateList,dynamicData.getInitSplic());
                if(splicUpdateListA14 !=null && splicUpdateListA14.size()>0){

                    for(int i =0;i<splicUpdateListA14.size();i++){

                        a01Service.updateMasterA14(splicUpdateListA14.get(i));

                    }

                }
                break;

            case "a1500" :

                //语句
//                a01Service.updateMasterA15(updateList);
                List<List<Map>> splicUpdateListA15 = fixedGrouping(updateList,dynamicData.getInitSplic());
                if(splicUpdateListA15 !=null && splicUpdateListA15.size()>0){

                    for(int i =0;i<splicUpdateListA15.size();i++){

                        a01Service.updateMasterA15(splicUpdateListA15.get(i));

                    }

                }
                break;
            case "a1700" :

                //语句
                List<List<Map>> splicUpdateList = fixedGrouping(updateList,dynamicData.getInitSplic());
                if(splicUpdateList !=null && splicUpdateList.size()>0){

                  for(int i =0;i<splicUpdateList.size();i++){

                      a01Service.updateMasterA17(splicUpdateList.get(i));

                  }

                }

                break;

            case "a3600" :

                //语句
                a01Service.updateMasterA36(updateList);
                break;

            default :

                //语句

        }
    }


    //TODO 切割集合 由于云上面的 mysql 效率低下，容易 让数据库失去连接 切割插入
    public  <T> List<List<T>> fixedGrouping(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }
}
