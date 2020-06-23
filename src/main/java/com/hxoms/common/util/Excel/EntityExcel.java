package com.hxoms.common.util.Excel;

import org.springframework.stereotype.Component;

@Component
public class EntityExcel {

    //Excel的文件名字
    private String fileName;

    //Excel的title
    private String title;

    //存放Map的key，方便拿值
    private String[] key;

    //Excel的列名字
    private String[] rowName;

    public String[] getKey() {
        return key;
    }

    public void setKey(String[] key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getRowName() {
        return rowName;
    }

    public void setRowName(String[] rowName) {
        this.rowName = rowName;
    }
}
