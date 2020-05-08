package com.hxoms.modules.dataCapture.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @authore:wjf
 * @data 2020/4/20 17:41
 * @Description:
 ***/
@Component
public class DynamicData {

    public static List<String> a02List ;

    @Value("${data.initSplic}")
    private int initSplic;

    public int getInitSplic() {
        return initSplic;
    }

    public void setInitSplic(int initSplic) {
        this.initSplic = initSplic;
    }

    static{
        String[] a02String = {
                "A0200",
                "A0100",
                "A0201A",
                "A0201B",
                "A0201D",
                "A0201E",
                "A0207",
                "A0209",
                "A0215A",
                "A0215B",
                "A0219",
                "A0221",
                "A0221A",
                "A0221T",
                "A0222",
                "A0223",
                "A0225",
                "A0229",
                "A0243",
                "A0245",
                "A0247",
                "A0251",
                "A0251B",
                "A0255",
                "A0259",
                "A0265",
                "A0267",
                "A0271",
                "A0284",
                "A0288",
                "A4901",
                "A4904",
                "A4907",
                "ID",
                "IS_DELETED",
                "MODIFY_USER",
                "MODIFY_TIME",
                "TIME_STAMP",
                "NODE_ID",
                "A0279",
                "A0272",
                "A0281"
        };

        a02List = new ArrayList<>(Arrays.asList(a02String));


























    }
}
