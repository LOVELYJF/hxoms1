package com.hxoms.common;

/**
 * @authore:wjf
 * @data 2020/4/14 11:41
 * @Description:
 ***/
public class LowerKeyMapTest<K,V> extends java.util.LinkedHashMap {

    @Override
    public V put(Object key, Object value) {
        if (key != null) {
                return (V) super.put(key.toString().toLowerCase(), value);
        } else {
            return (V) super.put(key, value);
        }
    }
}
