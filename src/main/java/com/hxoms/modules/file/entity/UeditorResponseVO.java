package com.hxoms.modules.file.entity;

/**
 * @desc: Ueditor上传图片返回实体类
 * @author: lijing
 * @date: 2020-07-13
 */
public class UeditorResponseVO {
    //图片上传成功后返回获取图片的路径，
    //可以是一个二进制流的接口，也可以是图片地址得接口，总之这个路径必须是写在
    //img标签内能够显示的
    private String url;
    //上传状态信息，如果为"SUCCESS"(必须是大写)，则表示上传成功，不为SUCCESS时
    //页面提示信息就是state得值
    private String state;
    private String type;
    private String original;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
