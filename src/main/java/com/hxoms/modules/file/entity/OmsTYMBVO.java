package com.hxoms.modules.file.entity;

import java.util.List;

public class OmsTYMBVO {
    //类型
    private String type;
    //文件
    private List<OmsMB> omsMBS;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public List<OmsMB> getOmsMBS() {
        return omsMBS;
    }

    public void setOmsMBS(List<OmsMB> omsMBS) {
        this.omsMBS = omsMBS;
    }
}
