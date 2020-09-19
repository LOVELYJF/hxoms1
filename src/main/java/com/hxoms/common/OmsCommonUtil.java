package com.hxoms.common;


import com.hxoms.message.message.entity.Message;
import com.hxoms.message.message.entity.paramentity.SendMessageParam;
import com.hxoms.message.message.service.MessageService;
import com.hxoms.message.msguser.entity.MsgUser;
import com.hxoms.support.parameter.service.ParameterService;
import com.hxoms.support.sysdict.entity.SysDictItem;
import com.hxoms.support.sysdict.mapper.SysDictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
     * @param paramCode 接收人的系统参数编码，格式：类型（1个人 2处室 3机构 4讨论组）,机构或处室ID，机构或处室名称
     * @description:发送消息通用方法
     * @author:杨波
     * @date:2020-09-15 * @param msg 要发送的消息
     * @return:void
     **/
    public static void SendMessage(String msg, String paramCode) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        SendMessageParam param = new SendMessageParam();
        Message message = new Message();
        message.setContent(msg);
        param.setMessage(message);

        String receiver = parameterService.selectPValueByCode(paramCode);

        //格式：类型（1个人 2处室 3机构 4讨论组）,机构或处室ID，机构或处室名称
        String[] receivers = receiver.split(",");

        Map<String, List<MsgUser>> msgUserMap = new HashMap<>();
        List<MsgUser> msgUsers = new ArrayList<>();
        MsgUser msgUser = new MsgUser();
        msgUser.setHandleIdentify(receivers[0]);//类型
        msgUser.setReceiveUserId(receivers[1]);//机构或处室ID
        msgUser.setReceiveUsername(receivers[2]);//机构或处室名称
        msgUsers.add(msgUser);
        msgUserMap.put(receivers[0], msgUsers);

        param.setMsgUserMap(msgUserMap);

        messageService.sendMessage(param);
    }
}



