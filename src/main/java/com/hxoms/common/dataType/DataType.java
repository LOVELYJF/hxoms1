package com.hxoms.common.dataType;

public enum DataType {

    spot(".","点"),bar("-","横杠"),diagonal("/","斜杆");

    private String code;

    private String desc;

    private DataType(String code, String desc) {

        this.code = code;
        this.desc =desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
