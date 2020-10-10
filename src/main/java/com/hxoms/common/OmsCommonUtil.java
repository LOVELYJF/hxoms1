package com.hxoms.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxoms.common.utils.BeanUtilSelf;
import com.hxoms.common.utils.Result;
import com.hxoms.common.utils.StringUilt;
import com.hxoms.message.message.entity.Message;
import com.hxoms.message.message.entity.paramentity.SendMessageParam;
import com.hxoms.message.message.service.MessageService;
import com.hxoms.message.msguser.entity.MsgUser;
import com.hxoms.modules.omsregcadre.entity.OmsRegProcbatchPerson;
import com.hxoms.support.parameter.service.ParameterService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 涉密等级工具类
 * @author gaozhenyuan
 * @date 2020/05/28
 */
@Component
public class OmsCommonUtil {
    @Autowired
    private SysDictItemMapper autoSysDictItemMapper;
    @Autowired
    private MessageService autoMessageService;
    @Autowired
    private ParameterService autoParameterService;

    private static SysDictItemMapper sysDictItemMapper;
    private static MessageService messageService;
    private static ParameterService parameterService;

    @PostConstruct
    public void init() {
        sysDictItemMapper=autoSysDictItemMapper;
        messageService=autoMessageService;
        parameterService=autoParameterService;
    }
    /** 涉密等级状态 */
    public static final String[] SECRET_LEVEL_STATUS = {"0","1","2","3"};
    public static final String[] SECRET_LEVEL_STATUS_NAME = {"非涉密","一般","重要","核心"};

    /**
     * 转换涉密等级名称为状态
     * @param name
     * @return String
     */
    public static final String toSecretLevelStatus(String name){
        String result = "";
        if("非涉密".equals(name)){
            result = SECRET_LEVEL_STATUS[0];
        }
        if("一般".equals(name) || "一般涉密".equals(name)){
            result = SECRET_LEVEL_STATUS[1];
        }
        if("重要".equals(name) || "重要涉密".equals(name)){
            result = SECRET_LEVEL_STATUS[2];
        }
        if("核心".equals(name) || "核心涉密".equals(name)){
            result = SECRET_LEVEL_STATUS[3];
        }
        return result;
    }

    /**
     * 转换涉密等级状态为名称
     * @param status
     * @return String
     */
    public static final String toSecretLevelStatusName(String status){
        String result = "";
        if(SECRET_LEVEL_STATUS[0].equals(status)){
            result = SECRET_LEVEL_STATUS_NAME[0];
        }
        if(SECRET_LEVEL_STATUS[1].equals(status)){
            result = SECRET_LEVEL_STATUS_NAME[1];
        }
        if(SECRET_LEVEL_STATUS[2].equals(status)){
            result = SECRET_LEVEL_STATUS_NAME[2];
        }
        if(SECRET_LEVEL_STATUS[3].equals(status)){
            result = SECRET_LEVEL_STATUS_NAME[3];
        }
        return result;
    }
    /**
     * @description:缓存数据字典到hash表
     * @author:杨波
     * @date:2020-09-18
     *   @param dictCode 字典类别
     *  @param codeAsKey 是否以code作为hash表的key
     * @return:java.util.HashMap<java.lang.String,com.hxoms.support.sysdict.entity.SysDictItem>
     **/
    public static HashMap<String, SysDictItem> CacheDictItem(String dictCode,boolean codeAsKey){

        List<SysDictItem> listDictItems = sysDictItemMapper.selectItemCodeByDictCode(dictCode);
        HashMap<String,SysDictItem> hashMapDictItem=new HashMap<>();
        for (SysDictItem DictItem:listDictItems
        ) {
            if(codeAsKey)
                hashMapDictItem.put(DictItem.getItemCode(),DictItem);
            else
                hashMapDictItem.put(DictItem.getItemName(),DictItem);
        }

        return hashMapDictItem;
    }
    public static HashMap<String, SysDictItem> CacheDictItem(String dictCode){
        return CacheDictItem(dictCode,true);
    }
    /**
     * @description:利用指定属性的值缓存list到hashMap
     * @author:杨波
     * @date:2020-10-01
     *  * @param objects 要缓存的数据
     * @param keyFieldName 作为key值的属性
     * @return:java.util.HashMap<java.lang.String,org.apache.poi.ss.formula.functions.T>
     **/
    public static HashMap<String,Object> CacheHasMap(List<Object> objects,String keyFieldName){

        HashMap<String,Object> hashMap = new HashMap<>();
        for (Object t:objects
             ) {
            String key= BeanUtilSelf.getFieldStringValueByFieldName(keyFieldName,t,"");
            if(hashMap.containsKey(key)) continue;

            hashMap.put(key,t);
        }
        return hashMap;
    }
    /**
     * @param paramCode 接收人的系统参数编码，格式：类型（1个人 2处室 3机构 4讨论组）,机构或处室ID，机构或处室名称
     * @description:发送消息通用方法
     * @author:杨波
     * @date:2020-09-15 * @param msg 要发送的消息
     * @return:void
     **/
    public static void SendMessage(String msg, String paramCode) throws IllegalAccessException, ClassNotFoundException, InstantiationException {

        String receiver = parameterService.selectPValueByCode(paramCode);
        //格式：类型（1个人 2处室 3机构 4讨论组）,机构或处室ID，机构或处室名称
        String[] receivers = receiver.split(",");

        SendMessage(msg,receivers[0],receivers[1],receivers[2]);
    }
    /**
     * @description: 发送在线提醒消息
     * @author:杨波
     * @date:2020-09-19
     *  * @param msg 要发送的消息
     * @param type 类型（1个人 2处室 3机构 4讨论组）
     * @param id ,机构或处室ID
     * @param name 机构或处室名称
     * @return:void
     **/
    public static void SendMessage(String msg,String type, String id,String name) throws IllegalAccessException, ClassNotFoundException, InstantiationException {

        SendMessageParam param = new SendMessageParam();
        Message message = new Message();
        message.setContent(msg);
        param.setMessage(message);

        Map<String, List<MsgUser>> msgUserMap = new HashMap<>();
        List<MsgUser> msgUsers = new ArrayList<>();
        MsgUser msgUser = new MsgUser();
        msgUser.setHandleIdentify(type);//类型
        msgUser.setReceiveUserId(id);//机构或处室ID
        msgUser.setReceiveUsername(name);//机构或处室名称
        msgUsers.add(msgUser);
        msgUserMap.put(type, msgUsers);

        param.setMsgUserMap(msgUserMap);

        messageService.sendMessage(param);
    }
    public static List<Object> Deserialization(String dateFormat,String data,Class<?> cls) throws IOException {
        if (StringUilt.stringIsNullOrEmpty(data)) {
            return new ArrayList<>();
        }
        ObjectMapper om = new ObjectMapper();
        om.setDateFormat(new SimpleDateFormat(dateFormat));
        List<Object> objects = om.readerFor(cls).readValues(data).readAll();

        return objects;
    }
}



