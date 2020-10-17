package com.hxoms.modules.leaderSupervision.config;

import com.hxoms.common.OmsCommonUtil;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.StringUilt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @authore:wjf
 * @data 2020/7/16 15:21
 * @Description: 采用 约定 思想
 ***/

public class StatusConvertLinkMap<K,V> extends LinkedHashMap {

    private  Map<String,Map<String,String>> mappri = new HashMap();
   //
    private  Map mapStauts = new HashMap();

    private  Map mapSmdj = new HashMap();


    @Override
    public V put(Object key, Object value) {
        if (key != null) {

            if(key.toString().equals("applyStatus")||key.toString().equals("secretLevel")){

                // 干部监督处 状态 转换

//              return  (V) super.put(key, Constants.leader_businessName[getIndexByArray(Constants.leader_business,value.toString())]);
                return  (V) super.put(key, getK(key.toString()).get(key.toString()).get(value));
            }
            else{

                return (V) super.put(key, value);
            }

        } else {
            return (V) super.put(key, value);
        }
    }

    // 根据数组的值 获取数组 下标
    public    Integer getIndexByArray(int[] array,String value){

        if (null == array || array.length==0 || StringUilt.stringIsNullOrEmpty(value))
            return null;
        for(int i=0;i<array.length;i++){
            if(array[i]==(Integer.valueOf(value))){
                return i;
            }
        }
        return null;
    }

    public  Map<String,Map<String,String>> getK(String k){

//        if(k.equals("applyStatus")){
//            // 需要装换 状态
//            // 当两者 状态 不一致 的重新 把新的状态
//            if(mapSmdj.size()!=(Constants.private_business.length+Constants.leader_business.length)){
//                mapSmdj.clear();
//
//                for(int i=0;i<Constants.private_business.length;i++){
//                    //获取 状态  获取 状态 对应的值
//                    mapStauts.put(String.valueOf(Constants.private_business[i]),Constants.private_businessName[i]);
//                }
//                for(int j=0;j<Constants.leader_business.length;j++){
//                    mapStauts.put(String.valueOf(Constants.leader_business[j]),Constants.leader_businessName[j]);
//                }
//                mappri.put("applyStatus",mapStauts);
//                return mappri;
//            }else{
//               return  mappri;
//            }
//        }else
            if(k.equals("secretLevel")){
            // 转换涉密等级
            // 需要装换 状态
            // 当两者 状态 不一致 的重新 把新的状态 写到
            if(mapSmdj.size()!=(OmsCommonUtil.SECRET_LEVEL_STATUS.length)){
                mapSmdj.clear();

                for(int i=0;i<OmsCommonUtil.SECRET_LEVEL_STATUS.length;i++){
                    //获取 状态  获取 状态 对应的值
                    mapSmdj.put(String.valueOf(OmsCommonUtil.SECRET_LEVEL_STATUS[i]),OmsCommonUtil.SECRET_LEVEL_STATUS_NAME[i]);
                }

                mappri.put("secretLevel",mapSmdj);
                return mappri;
            }else{
                return  mappri;
            }
        }
        return null;
    }
}


