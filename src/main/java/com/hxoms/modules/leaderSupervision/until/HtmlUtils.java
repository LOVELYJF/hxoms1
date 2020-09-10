package com.hxoms.modules.leaderSupervision.until;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @authore:wjf
 * @data 2020/9/8 14:16
 * @Description:
 ***/
public class HtmlUtils {




    //替换 html img标签 src属性下的 值 转成base64 字符串

    public static String replaceTag(String htmlStr,String tag,String imgRealPath){

        //解析传递的字符串 parse 包含 <body>标签
        Document parse = Jsoup.parseBodyFragment(htmlStr);


        Elements imgs = parse.getElementsByTag("img");
        for(Element img : imgs){
            String linkSrc = img.attr(tag);

            String imgName = linkSrc.split("\\\\")[linkSrc.split("\\\\").length-1];

            String realImgName =imgRealPath +imgName;
            String base64Str =  ImageToBase64(realImgName);

            img.attr(tag, "data:image/jpg;base64,"+base64Str);
        }

//        //newStr  该字符串包含<body>标签
        String newStr = parse.body().toString();
//        //过滤<body>标签
        newStr = newStr.substring(5, newStr.length() - 7);

        //返回修改后字符串
        return newStr;


    }


    private static String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));
        return encoder.encode(Objects.requireNonNull(data));
    }










}
