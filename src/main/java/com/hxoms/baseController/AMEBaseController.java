package com.hxoms.baseController;

import com.alibaba.fastjson.JSON;
import com.hxoms.common.util.ExcelUtil;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.ResultSwg;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AMEBaseController {

    private Object[] obj;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public Method getMethod(Class clazz,String methodName){
        Method method=null;
        Method[] methods = clazz.getMethods();
        if(methods!=null && methods.length>0){
            for (int i = 0; i < methods.length; i++) {
                if(methods[i].getName().equals(methodName)){
                    Class<?>[] parameterTypes = methods[i].getParameterTypes();
                    try {
                        method = clazz.getMethod(methodName, parameterTypes);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return method;
    }

    public void setParameters(Method method,ExcelBaseEntity entity){

        Map<String,String> parameter = entity.getParameters();
        Object entityData = entity.getData();

        Class<?>[] parameterTypes = method.getParameterTypes();
        Parameter[] parameters = method.getParameters();

        if(parameterTypes!=null && parameterTypes.length>0){
            obj = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                String simpleName = parameterTypes[i].getSimpleName();
                String name = parameters[i].getName();

                if("HttpServletRequest".equals(simpleName)){
                    this.obj[i] = this.request;
                    continue;
                }
                if("HttpServletResponse".equals(simpleName)){
                    this.obj[i] = this.response;
                    continue;
                }
                if("byte".equals(simpleName) || "Byte".equals(simpleName)){
                    Byte b = parameter.get(name)==null || "".equals(parameter.get(name)) ? null :Byte.parseByte(parameter.get(name));
                    this.obj[i] = b;
                    continue;
                }
                if("short".equals(simpleName) || "Short".equals(simpleName)){
                    Short s = parameter.get(name)==null || "".equals(parameter.get(name)) ? null :Short.parseShort(parameter.get(name));
                    this.obj[i] = s;
                    continue;
                }
                if("int".equals(simpleName) || "Integer".equals(simpleName)){
                    Integer it = parameter.get(name)==null || "".equals(parameter.get(name)) ? null :Integer.parseInt(parameter.get(name));
                    this.obj[i] = it;
                    continue;
                }
                if("long".equals(simpleName) || "Long".equals(simpleName)){
                    Long l = parameter.get(name)==null || "".equals(parameter.get(name)) ? null :Long.parseLong(parameter.get(name));
                    this.obj[i] = l;
                    continue;
                }
                if("float".equals(simpleName) || "Float".equals(simpleName)){
                    Float f = parameter.get(name)==null || "".equals(parameter.get(name)) ? null :Float.parseFloat(parameter.get(name));
                    this.obj[i] = f;
                    continue;
                }
                if("double".equals(simpleName) || "Double".equals(simpleName)){
                    Double d = parameter.get(name)==null || "".equals(parameter.get(name)) ? null :Double.parseDouble(parameter.get(name));
                    this.obj[i] = d;
                    continue;
                }
                if("boolean".equals(simpleName) || "Boolean".equals(simpleName)){
                    Boolean b = parameter.get(name)==null || "".equals(parameter.get(name)) ? false :Boolean.parseBoolean(parameter.get(name));
                    this.obj[i] = b;
                    continue;
                }
                if("String".equals(simpleName)){
                    this.obj[i] = parameter.get(name);
                    continue;
                }

                try {
                    Class<?> clazz = Class.forName(parameterTypes[i].getName());
                    if(entityData==null || "".equals(entityData)){
                        this.obj[i] = clazz.newInstance();
                    }else{
                        String toJSONString = JSON.toJSONString(entityData);
                        this.obj[i] = JSON.parseObject(toJSONString,clazz);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public Method pottinParameter(ExcelBaseEntity entity){
        Class<? extends AMEBaseController> clazz = this.getClass();

        Method method = getMethod(clazz, entity.getMethodName());
        if(method!=null){
            setParameters(method,entity);
        }
        return method;
    }


    @ApiOperation(value = "exportExcel",notes = "导出Excel公共接口")
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody ExcelBaseEntity jsonData, HttpServletRequest request, HttpServletResponse response){
        this.obj = null;
        this.request = request;
        this.response = response;

        List<Map<String,String>> columnList = jsonData.getColumnList();
        jsonData.setMethodName("testExcel");

        if(columnList==null || columnList.size()==0){
            return;
        }

        Method method = pottinParameter(jsonData);

        try {
            Object data= null;
            if(obj!=null && obj.length>0){
                data = method.invoke(this,obj);
            }else{
                data = method.invoke(this);
            }

            List<Map<String,Object>> listData = null;
            if(data!=null){
                List<Object> list = dataTypeConversionList(data);
                listData = entityConvertMap(list);
            }
            if(listData!=null && listData.size()>0){
                Workbook workbook = ExcelUtil.writer07Excel(listData, columnList);
                if(workbook!=null){
                    ExcelUtil.download(workbook,response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String,Object>> entityConvertMap(List listData){

        List<Map<String,Object>> list = new ArrayList<>();

        if(listData!=null && listData.size()>0){

            Class<?> clazz = listData.get(0).getClass();
            try {
                Object object = clazz.newInstance();

                for (int i = 0; i < listData.size(); i++) {
                    Object o = listData.get(i);
                    Map<String,Object> map = new HashedMap();
                    for (Field field : o.getClass().getFields()) {
                        String name = field.getName();
                        field.setAccessible(true);
                        map.put(name.toUpperCase(),field.get(object));
                    }
                    list.add(map);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List dataTypeConversionList(Object object){
        List<Object> dataList = null;
        if(object instanceof Result){
            Result result = (Result)object;
            Object data = result.getData();
            if(data instanceof ArrayList){
                dataList = (List)data;
            }
        }else if(object instanceof ResultSwg){
            ResultSwg result = (ResultSwg)object;
            Object data = result.getData();
            if(data instanceof ArrayList){
                dataList = (List)data;
            }
        }else if(object instanceof ArrayList){
            dataList = (List)object;
        }
        return dataList;
    }

}
