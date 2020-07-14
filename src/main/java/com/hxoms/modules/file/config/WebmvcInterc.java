package com.hxoms.modules.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @desc: 映射路径拦截器，将虚拟路径映射为真是图片地址
 * @author: lijing
 * @date: 2020-07-13
 */
@Configuration
public class WebmvcInterc implements WebMvcConfigurer {
    @Value(value = "${file.ueditorRealImgUrl}")
    private String ueditorRealImgUrl;
    @Value(value = "${file.ueditorImgUrl}")
    private String ueditorImgUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/oms"+ueditorImgUrl + "**")
                .addResourceLocations("file:" + ueditorRealImgUrl);
    }
}
