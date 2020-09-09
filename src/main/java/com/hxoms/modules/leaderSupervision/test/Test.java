package com.hxoms.modules.leaderSupervision.test;

import org.apache.logging.log4j.core.util.UuidUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @authore:wjf
 * @data 2020/9/8 20:34
 * @Description:
 ***/
public class Test {

    /**
     * 将html中的图片下载到服务器，并且使用服务器上图片的地址替换图片的网络路径
     * @param html 要处理的html
     * @param request
     * @param uploadFolder 服务器上保存图片的目录
     * @return
     */
    public static String transHtml(String html,HttpServletRequest request,String uploadFolder){
        List<String> imgList = getImgStrList(html);
        for (String imgStr : imgList) {
            try {
                String newUrl = reSaveImage(imgStr, request,uploadFolder);
                html = html.replace(imgStr, newUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return html;
    }

    /**
     * 将指定的网络图片保存到本地指定目录
     * @param httpUrl 图片原来的网络路径
     * @param request
     * @param uploadFolder 服务器上保存图片的目录
     * @return httpUrl newPath
     */
    private static String reSaveImage(String httpUrl,  HttpServletRequest request,String uploadFolder){
        FileOutputStream out = null;
        BufferedInputStream in = null;
        HttpURLConnection connection = null;
        Map<String, Object> urlMap = new HashMap<>();

        byte[] buf = new byte[1024];
        int len = 0;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            in = new BufferedInputStream(connection.getInputStream());
            urlMap = getNewPath(httpUrl, request,uploadFolder);
            out = new FileOutputStream(urlMap.get("newPath").toString());
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return urlMap.get("newUrl").toString();
    }

    /**
     * 获取保存在服务器上的图片的实际存储地址以及访问地址
     * @param httpUrl 图片原来的网络路径
     * @param request
     * @param uploadFolder服务器上保存图片的目录
     * @return
     */
    private static Map<String, Object> getNewPath(String httpUrl, HttpServletRequest request,String uploadFolder) {
        Map<String, Object> relMap = new HashMap<>();
        String fileName = getFileName();
        String filefix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();// 文件后缀
        fileName = 111 + "." + filefix;
        String prefix = getUrlPrefix(request) + "/iconimg/";
        relMap.put("newUrl", prefix + fileName);
        relMap.put("newPath", uploadFolder + fileName);
        return relMap;
    }

    /**
     * 设置图片的名称（时间+用户编号）
     * @return
     */
    private static String getFileName() {
        return "reload"+ File.pathSeparator+new Date().getTime()+".jpg";
    }

    /**
     * 提取HTML字符串中的img
     * @param htmlStr 要处理的html字符串
     * @return
     */
    private static List<String> getImgStrList(String htmlStr) {
        List<String> list = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String path = m.group(1);

                    list.add(handleSrc(path));

            }
        }
        return list;
    }

    /**
     * 去除src路径中的前后引号
     * @param src 图片的src路径
     * @return
     */
    private static String handleSrc(String src) {
        if (src != null) {
            if (src.startsWith("'")|| src.startsWith("\"")) {
                return src.substring(1, src.length());
            }
            if (src.endsWith("'")|| src.endsWith("\"")) {
                return src.substring(0, src.length());
            }
        }
        return src;
    }

    /**
     * 获取网站的URL
     * @param request
     * @return 例如：http://192.168.11.3:8089
     */
    public static String getUrlPrefix(HttpServletRequest request) {
        StringBuffer str = new StringBuffer();
        str.append(request.getScheme());
        str.append("://");
        str.append(request.getServerName());
        if (80 != request.getServerPort()) {
            str.append(":" + request.getServerPort());
        }
        str.append(request.getContextPath());
        return str.toString();
    }


    public static void main(String[] args){

    List list =    getImgStrList("< img width = \\\"540\\\" height = \\\"2\\\" src = \\\"http://localhost:8888/ueditor/themes/default/images/spacer.gif\\\" />");
        System.out.println(list);

    }
}