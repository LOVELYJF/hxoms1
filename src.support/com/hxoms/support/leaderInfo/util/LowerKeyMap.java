package com.hxoms.support.leaderInfo.util;

import dm.jdbc.driver.DmdbClob;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @desc: 键值为小写的map
 * @author: lijing
 * @date: 2019/8/29
 */
public class LowerKeyMap<K,V> extends HashMap {
    @Override
    public V put(Object key, Object value) {
        if (key != null) {
            if (value != null && value.getClass().getTypeName().equals("dm.jdbc.driver.DmdbClob")) {
                DmdbClob val = (DmdbClob) value;
                if(val != null){
                    InputStream input = null;
                    try {
                        input = val.getAsciiStream();
                        int len = (int)val.length();
                        byte by[] = new byte[len];
                        int i = 0;
                        while(-1 != (i = input.read(by, 0, by.length))){
                            input.read(by, 0, i);
                        }
                        return (V) super.put(key.toString().toLowerCase(), new String(by, "utf-8"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }


                } else {
                    return (V) super.put(key.toString().toLowerCase(), null);
                }
            }else {
                return (V) super.put(key.toString().toLowerCase(), value);
            }
        } else {
            return (V) super.put(key, value);
        }
    }
}
