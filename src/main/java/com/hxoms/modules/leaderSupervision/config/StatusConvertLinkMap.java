package com.hxoms.modules.leaderSupervision.config;

import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.StringUilt;

import java.util.LinkedHashMap;

/**
 * @authore:wjf
 * @data 2020/7/16 15:21
 * @Description:
 ***/
public class StatusConvertLinkMap<K,V> extends LinkedHashMap {




    @Override
    public V put(Object key, Object value) {
        if (key != null) {

            if(key.toString().equals("applyStatus")){

                // 干部监督处 状态 转换

              return  (V) super.put(key, Constants.leader_businessName[getIndexByArray(Constants.leader_business,value.toString())]);
            }else{
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




}
