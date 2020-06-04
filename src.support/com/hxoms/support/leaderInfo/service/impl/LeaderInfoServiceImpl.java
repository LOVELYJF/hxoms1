package com.hxoms.support.leaderInfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.dataType.DataType;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.person.markedcadre.mapper.McMarkedcadreMapper;
import com.hxoms.support.inforesource.entity.DataTableCol;
import com.hxoms.support.leaderInfo.mapper.A01Mapper;
import com.hxoms.support.leaderInfo.service.LeaderInfoService;
import com.hxoms.support.leaderInfo.util.LowerKeyMap;
import com.hxoms.support.sysdict.service.SysDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LeaderInfoServiceImpl implements LeaderInfoService {

    @Autowired
    private A01Mapper a01Mapper;

    @Autowired
    private McMarkedcadreMapper mcMarkedcadreMapper;

    @Autowired
    private SysDictItemService sysDictItemService;

    @Autowired
    private SelectMapper selectMapper;

    //根据id查询返回数据
    @Override
    public Map<String,Object> selectBasicInfo(Integer pageNum, Integer pageSize, String orgId) {

        //结果map
        Map<String, Object> resultMap = new LinkedHashMap<>();

        //查询动态表头信息
        List<DataTableCol> dataTableColList = mcMarkedcadreMapper.selectDynamicColumn();
        resultMap.put("dataCol", dataTableColList);

        //查询列相关字典
        Map<String, Object> dicCodeItemMap = new LinkedHashMap<>();
        for (DataTableCol dataTableCol : dataTableColList) {
            String controlType = dataTableCol.getControlType();
            if ("2".equals(controlType) || "14".equals(controlType)) {
                if (!dicCodeItemMap.containsKey(dataTableCol.getDicCode())) {
                    List<Map<String, Object>> dicCodeItems = sysDictItemService.getDictInfoByDictCode(dataTableCol.getDicCode(), null);
                    dicCodeItemMap.put(dataTableCol.getDicCode(), dicCodeItems);
                }
            }
        }
        resultMap.put("dicCodeItems", dicCodeItemMap);

        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        if(orgId == null){
            List<LinkedHashMap<String, Object>> list1 = a01Mapper.selectAllInfo();
            PageInfo info1 = new PageInfo(list1);
            resultMap.put("info",info1);
            return resultMap;
        }else {
            List<LinkedHashMap<String, Object>> list2 = a01Mapper.selectByOrgId(orgId);
            PageInfo info2 = new PageInfo(list2);
            resultMap.put("info",info2);
            return resultMap;
        }
    }

    //查询数据信息集
    @Override
    public List<Map<String,Object>> selectLeaderInfoData(String tablecode, String id) {

        Map<String, Object> resultMap = new LinkedHashMap<>();

        if("Data_table".equalsIgnoreCase(tablecode)){
            List<Map<String, Object>> infoLine = a01Mapper.selectInfoLine();
            return infoLine;
        }

        List<Map<String, Object>> tableCol = a01Mapper.selectTableCol(tablecode);
        List<Map<String, Object>> tableInfo = a01Mapper.selectTableInfo(tablecode);

        //查询列相关字典
        Map<String, Object> dicCodeItemMap = new LinkedHashMap<>();
        for (Map<String, Object> map : tableCol) {
            String controlType = (String) map.get("CONTROL_TYPE");
            if (map.get("DIC_CODE") != null && map.get("DIC_CODE") != ""){
                if ("2".equals(controlType) || "14".equals(controlType)) {
                    if (!dicCodeItemMap.containsKey(map.get("DIC_CODE"))) {
                        List<Map<String, Object>> dicCodeItems = sysDictItemService.getDictInfoByDictCode((String) map.get("DIC_CODE"), null);
                        dicCodeItemMap.put((String) map.get("DIC_CODE"), dicCodeItems);
                    }
                    resultMap.put("dicCodeItems", dicCodeItemMap);
                }
            }
        }

        resultMap.put("tableCol",tableCol);
        resultMap.put("tableInfo",tableInfo);

        /*if("A36".equalsIgnoreCase(tablecode)){
            List<Map<String, Object>> tableData = a01Mapper.selectFamilyMember(id);
            resultMap.put("tableData",tableData);
            return Collections.singletonList(resultMap);
        }*/

        List<LowerKeyMap<String, Object>> tableData = a01Mapper.selectLeaderInfoData(tablecode, id);
        resultMap.put("tableData",tableData);
        return Collections.singletonList(resultMap);
    }

    /**
     * 功能描述: <br>
     * 〈根据关键词/姓氏模糊查询返回数据〉
     * @Param: [name]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 李逍遥
     * @Date: 2020/4/29 11:07
     */
    @Override
    public Map<String, Object> selectBasicInfoByName(String name) {
        //结果map
        Map<String, Object> resultMap = new LinkedHashMap<>();

        //查询动态表头信息
        List<DataTableCol> dataTableColList = mcMarkedcadreMapper.selectDynamicColumn();
        resultMap.put("dataCol", dataTableColList);

        //查询列相关字典
        Map<String, Object> dicCodeItemMap = new LinkedHashMap<>();
        for (DataTableCol dataTableCol : dataTableColList) {
            String controlType = dataTableCol.getControlType();
            if ("2".equals(controlType) || "14".equals(controlType)) {
                if (!dicCodeItemMap.containsKey(dataTableCol.getDicCode())) {
                    List<Map<String, Object>> dicCodeItems = sysDictItemService.getDictInfoByDictCode(dataTableCol.getDicCode(), null);
                    dicCodeItemMap.put(dataTableCol.getDicCode(), dicCodeItems);
                }
            }
        }
        resultMap.put("dicCodeItems", dicCodeItemMap);


        if(name == null){
            return null;
        }else {
            List<LinkedHashMap<String, Object>> list1 = a01Mapper.selectAllInfoByName(name);
            PageInfo info1 = new PageInfo(list1);
            resultMap.put("info",info1);
            return resultMap;
        }
    }

    @Override
    public void updateLeaderInfoByTableCode(Map<String,String> updateMap) {
        /** StringBuffer  线程安全
         *  StringBuilder则没有实现线程安全功能，所以性能略高
         * **/
      StringBuffer  sb = new StringBuffer();
      String sql   = "";
      String tableId = "";

        if(updateMap.get("tableName") !=null){
            // 表 Id
            tableId += updateMap.get("tableName") +"00";

           if(updateMap.get(tableId.toLowerCase())!=null && !updateMap.get(tableId.toLowerCase()).isEmpty()){

               updateTable(updateMap);
           }else{

               insterTable(updateMap);
           }


        }else{

            throw new CustomMessageException("表名为空，请仔细检查");
        }


    }

    public void  updateTable(Map<String,String> updateMap){
        StringBuffer  sb = new StringBuffer();
        String sql   = "";
        String tableId = "";

        if(updateMap.get("tableName") !=null){

            sb.append(" update  " + updateMap.get("tableName")  + " set " );
            tableId += updateMap.get("tableName") +"00";

            List<LinkedHashMap<String, Object>> typemap  =  judgeType(updateMap.get("tableName"));

            Iterator<Map.Entry<String, String>>  tm =  updateMap.entrySet().iterator();

            while(tm.hasNext()){
                Map.Entry<String, String>    entity  =   tm.next();
                String colName =  entity.getKey().toLowerCase();

                if(!"tablename".equals(colName) && !tableId.toLowerCase().equals(colName)){

                    String type =  judgeType(typemap,colName);
                    String colValue =  entity.getValue().toString();

                    if(type.equals("datetime")){
                        colValue =    dealDateFormat(colValue);

                        String  splicType = getSplicType(colValue);

                        sb.append(colName+" = "+ " str_to_date('"+ colValue+"' ,'%Y"+splicType+"%m"+splicType+"%d %H:%i:%s')");

                    }else{

                        sb.append(colName+" = "+ "'"+ colValue+"'");

                    }




                    sb.append(",");



                }

            }

            sql =   sb.substring(0,sb.length()-1);


            sql=sql+(" where "+tableId.toLowerCase()+" = " + "'" + updateMap.get(tableId.toLowerCase())+"'" );


        }

        if(sql.length()>0){

            SqlVo instance = SqlVo.getInstance(sql);
            selectMapper.update(instance);


        }




    }

    public void insterTable(Map<String,String> updateMap){


        StringBuffer  sb = new StringBuffer();
        String sql   = "";
        String tableId = "";

        updateMap.size();

        if(updateMap.get("tableName") !=null){

            sb.append("insert into   " + updateMap.get("tableName") +"\t" );
            tableId += updateMap.get("tableName") +"00";

            List<LinkedHashMap<String, Object>> typemap  =  judgeType(updateMap.get("tableName"));

            Iterator<Map.Entry<String, String>>  tm =  updateMap.entrySet().iterator();

            // 字段语句
            StringBuffer fieldSql = new StringBuffer();
            // 值语句
            StringBuffer valueSql = new StringBuffer();

            fieldSql.append("(");

            valueSql.append("(");

            int count = 0;

            while(tm.hasNext()){
                Map.Entry<String, String>    entity  =   tm.next();
                String colName =  entity.getKey().toLowerCase();

                count++;

                if(!"tablename".equals(colName) ){

                    // 添加字段

                    fieldSql.append(colName);



                    // 添加值

                    String type =  judgeType(typemap,colName);   // 获取 值得类型
                    String colValue = (String) entity.getValue();
                  // 先判断字段是否为 主键， 在判断 值类型

                  if(tableId.toLowerCase().equals(colName)){

                      valueSql.append("'"+ UUIDGenerator.getPrimaryKey()+"'");
                  }else{

                      if(type.equals("datetime")){

                          String  splicType = getSplicType(colValue);

                          valueSql.append( " str_to_date('"+ colValue+"' ,'%Y"+splicType+"%m"+splicType+"%d')");

                      }else{

                          valueSql.append("'"+ colValue+"'");

                      }

                  }




                    if(count<updateMap.size()){
                        fieldSql.append(",");
                        valueSql.append(",");
                    }



                }

            }
            fieldSql.deleteCharAt(fieldSql.length()-1);
            fieldSql.append(")");
            valueSql.deleteCharAt(valueSql.length()-1);
            valueSql.append(")");



         sql+=sb.toString()+fieldSql+"\t"+"value"+"\t" +valueSql;

        }

        if(sql.length()>0){

            SqlVo instance = SqlVo.getInstance(sql);
            selectMapper.insert(instance);


        }







    }

    public String judgeType(List<LinkedHashMap<String, Object>> map,String colName){

        if(map!=null && map.size()>0){

            for(Map m : map){

                if(colName.equals(m.get("column_name").toString().toLowerCase())){

                    return (String) m.get("data_type");

                }

            }


        }

        
      return  "";

    }


    public List<LinkedHashMap<String, Object>> judgeType(String tableName){


        String sqlType = "select distinct column_name,data_type " +
                "from information_schema.columns " +
                "where table_name='"+tableName+" '";

        SqlVo instance = SqlVo.getInstance(sqlType);
        List<LinkedHashMap<String, Object>> map = selectMapper.select(instance);


        return  map;

    }


    public String getSplicType(String colValue){
        for(DataType dateType  : DataType.values()){

             if(colValue.indexOf(dateType.getCode())!=-1){

                 return  dateType.getCode();

             }

        }

        return  null;


    }

    public  String dealDateFormat(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return df2.format(date1);
    }


    public static void main(String[] args) {

        String str = "222";
        String s ="";


        Map m = new HashMap();
        m.put("1",1);
        m.put("2",2);
        m.put(null,3);
        m.put(null,5);
        m.put("6",6);


        System.out.println(m.size());

        System.out.println(s.isEmpty());

//        str+="333";   使用 + 通过反编译可知，他是生成了一个 中间过渡的 StringBuffer 添加的，最后在转成 String；
        str.concat("3333");

        System.out.println(str);

    }
}
