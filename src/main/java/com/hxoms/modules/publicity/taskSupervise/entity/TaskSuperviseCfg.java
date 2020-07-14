package com.hxoms.modules.publicity.taskSupervise.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Desc：配置文件
 * @Author: wangyunquan
 * @Date: 2020/6/30
 */
@Component
@PropertySource("classpath:publicity/TaskSupervise.properties")
@ConfigurationProperties(prefix = "task-supervise")
public class TaskSuperviseCfg {
    //备案表下载路径
    private String babDownPath;
    //备案表下载格式
    private String babDownSuffix;
    //压缩文件下载路径
    private String compressDownPath;
    //压缩文件下载格式
    private String compressDownSuffix;
    //干部监督处话术模板
    private String jdcMsgTemplate;
    //经办人话术模板
    private String jbrMsgTemplate;

    public String getBabDownPath() {
        return babDownPath;
    }

    public void setBabDownPath(String babDownPath) {
        this.babDownPath = babDownPath;
    }

    public String getBabDownSuffix() {
        return babDownSuffix;
    }

    public void setBabDownSuffix(String babDownSuffix) {
        this.babDownSuffix = babDownSuffix;
    }

    public String getCompressDownPath() {
        return compressDownPath;
    }

    public void setCompressDownPath(String compressDownPath) {
        this.compressDownPath = compressDownPath;
    }

    public String getCompressDownSuffix() {
        return compressDownSuffix;
    }

    public void setCompressDownSuffix(String compressDownSuffix) {
        this.compressDownSuffix = compressDownSuffix;
    }

    public String getJdcMsgTemplate() {
        return jdcMsgTemplate;
    }

    public void setJdcMsgTemplate(String jdcMsgTemplate) {
        this.jdcMsgTemplate = jdcMsgTemplate;
    }

    public String getJbrMsgTemplate() {
        return jbrMsgTemplate;
    }

    public void setJbrMsgTemplate(String jbrMsgTemplate) {
        this.jbrMsgTemplate = jbrMsgTemplate;
    }
}
