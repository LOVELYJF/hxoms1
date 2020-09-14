package com.hxoms.modules.leaderSupervision.until;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.StringUilt;
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

    private static String[] imageTypes={".png", ".jpg", ".jpeg", ".gif", ".bmp" };

    private static String imageBase64TypePrxfix="data:image/";

    private static String imageBase64TypeSuffix=";base64,";

    public  static void main(String[] args){
//
//       String ss =  replaceTag("<img src=\"F:\\ueditorImgUrl\\AC2D011B-94DF-445A-8953-E9FE6CA9B593.jpg\" />","src","F:/ueditorImgUrl/");
//
//        System.out.println(ss);
         String[] str =  "333.jpg".split("\\.");

        System.out.println("333.jpg".split("\\.")[1]);

//       String ss =  isHave(imageTypes,"jpg");
//        System.out.println(ss);
    }




    //替换 html img标签 src属性下的 值 转成base64 字符串

    public static String  replaceTag(String htmlStr,String tag,String imgRealPath){

        //解析传递的字符串 parse 包含 <body>标签
        Document parse = Jsoup.parse(htmlStr);


        Elements imgs = parse.getElementsByTag("img");
        for(Element img : imgs){
            String linkSrc = img.attr(tag);

            String imgName = linkSrc.split("\\\\")[linkSrc.split("\\\\").length-1];

            String realImgName =imgRealPath +imgName;
            String base64Str =  ImageToBase64(realImgName);

            String imageType = isHave(imageTypes,imgName.split("\\.")[1]);
            String base64StrPrefix = imageBase64TypePrxfix+imageType.substring(1)+imageBase64TypeSuffix;

            img.attr(tag, base64StrPrefix.trim()+base64Str);

        }

//        Document document = Jsoup.parse(htmlStr);
//        document.outputSettings(new Document.OutputSettings().syntax(Document.OutputSettings.Syntax.xml));
//        String html=document.outerHtml();

//        //newStr  该字符串包含<body>标签
//        String newStr = parse.body().toString();
//
////        //过滤<body>标签
//        newStr = newStr.substring(5, newStr.length() - 7);
        parse.outputSettings(new Document.OutputSettings().syntax(Document.OutputSettings.Syntax.xml));


        //返回修改后字符串
        String ss = parse.outerHtml().replaceAll("<html>\n" +
                " <head></head>\n" +
                " <body>","");
        String sss =    ss.replaceAll("</body>\n" +
                   "</html>","");
        return sss;


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
//        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));
        return encoder.encode(Objects.requireNonNull(data));
    }


    public static String isHave(String[] strArray, String str){

        String imageType="";

        for(int i=0;i<strArray.length;i++){

            if(strArray[i].indexOf(str)!=-1){

                imageType =strArray[i];
            }else{

                continue;
            }

        }


        if(StringUilt.stringIsNullOrEmpty(imageType)){

             throw  new CustomMessageException("图片类型错误，请仔细检查");

        }

        return imageType;

    }










}
