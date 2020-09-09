package com.hxoms.modules.leaderSupervision.until;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @authore:wjf
 * @data 2020/9/8 14:16
 * @Description:
 ***/
public class HtmlUtils {


    /**
     * 替换指定标签的属性和值
     * @param str 需要处理的字符串
     * @param tag 标签名称
     * @param tagAttrib 要替换的标签属性值
     * @param startTag 新标签开始标记
     * @param endTag  新标签结束标记
     * @return
     */
    public static String replaceHtmlTag(String str, String tag, String tagAttrib, String startTag, String endTag) {
        String regxpForTag = "<\\s*" + tag + "\\s+([^>]*)\\s*" ;
        String regxpForTagAttrib = tagAttrib + "=\\s*\"([^\"]+)\"" ;
        Pattern patternForTag = Pattern.compile (regxpForTag,Pattern. CASE_INSENSITIVE );
        Pattern patternForAttrib = Pattern.compile (regxpForTagAttrib,Pattern. CASE_INSENSITIVE );
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer( "<"+tag+" ");
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
            if (matcherForAttrib.find()) {
                String attributeStr = matcherForAttrib.group(1);
                matcherForAttrib.appendReplacement(sbreplace, startTag + attributeStr + endTag);
            }
            matcherForAttrib.appendTail(sbreplace);
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }

    //传入需要查找img的内容  将取得的src放入集合中
    public  List<String> getImgSrc(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        Matcher m;
        List<String> pics = new ArrayList<String>();
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(2));
            }
        }
        return pics;
    }

    public static void main(String[] args) {

      Set str =   getImgStr("< p style = \"white-space: normal; text-align: center;\" >< span style = \"font-family: 仿宋; font-size: 19px;\" > 关于征求 </ span >< span style = \"font-family: 仿宋; font-size: 19px;\" > $姓名$ </ span >< span style = \"font-family: 仿宋; font-size: 19px;\" ></ span >< span style = \"font-family: 仿宋; font-size: 19px;\" > 同志脱密期内申请出国（境）意见的函 </ span ></ p >< p style = \"white-space: normal; text-align: center;\" >< span style = \"font-family: 仿宋; font-size: 21px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span >< span style = \"font-family: 仿宋; font-size: 14px;\" > $工作单位及地址$ </ span >< span style = \"font-family: 仿宋; font-size: 14px;\" > 党委（党组）： </ span ></ p >< p style = \"white-space: normal; text-indent: 28px;\" >< span style = \"text-decoration-line: underline;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span ></ span >< span style = \"font-family: 仿宋; font-size: 14px;\" > $姓名$申请拟于$出境时间$至$入境时间$赴$国家（地区）$$出国事由$ </ span >< span style = \"text-decoration-line: underline;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span ></ span >< span style = \"font-family: 仿宋; font-size: 14px;\" > ，在国（境外停留时间 </ span >< span style = \"text-decoration-line: underline;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span ></ span >< span style = \"font-family: 仿宋; font-size: 14px;\" > $停留时间$天，费用自理。由于该同志尚在贵单位脱密期内，根据涉密人员出国（境）管理有关规定，现去函贵单位征求该同志出国（境）意见。 </ span ></ p >< p style = \"white-space: normal; text-indent: 28px;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal; text-indent: 28px;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" > 联系人：$联系人$ & nbsp;\n" +
              "\n" +
              "电话 : $联系电话$ </ span ></ p >< p style = \"white-space: normal;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"margin-right: 56px; white-space: normal; text-align: center;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "党委（党组） </ span ></ p >< p style = \"white-space: normal; text-indent: 392px;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >< span style = \"text-indent: 392px; font-family: 仿宋; font-size: 14px;\" >< u >& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "</ u > 年 < u >& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "</ u ></ span >< span style = \"text-indent: 392px; font-family: 仿宋; font-size: 14px;\" > 月 < u >& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "</ u ></ span >< span style = \"text-indent: 392px; font-family: 仿宋; font-size: 14px;\" > 日 </ span ></ span >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span ></ p >< p style = \"white-space: normal; text-align: center; line-height: 29px;\" >< span style = \"font-family: 仿宋; font-size: 21px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal; text-align: center; line-height: 29px;\" >< span style = \"position: absolute; z-index: 1; left: 0px; margin-left: 0.8667px; margin-top: 2.7333px; width: 540px; height: 2px;\" >< img width = \"540\" height = \"2\" src = \"http://localhost:8888/ueditor/themes/default/images/spacer.gif\" /></ span >< span style = \"font-family: 仿宋; font-size: 19px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal; text-align: center;\" >< strong >< span style = \"font-family: 等线; font-size: 14px;\" > _ </ span ></ strong >< span style = \"font-family: 仿宋; font-size: 19px;\" > 关于征求 </ span >< span style = \"font-family: 仿宋; font-size: 19px; text-align: center;\" > $姓名$ </ span >< span style = \"font-family: 仿宋; font-size: 19px;\" ></ span >< span style = \"font-family: 仿宋; font-size: 19px;\" > 同志脱密期内申请出国（境）意见的复函 </ span ></ p >< p style = \"white-space: normal; text-align: center;\" >< span style = \"font-family: 仿宋; font-size: 21px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span >< span style = \"font-family: 仿宋; font-size: 14px;\" > $工作单位及地址$ </ span >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span >< span style = \"font-family: 仿宋; font-size: 14px;\" > 党委（党组）： </ span ></ p >< p style = \"white-space: normal; text-indent: 28px;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" > 《关于征求 </ span >< span style = \"font-family: 仿宋; font-size: 14px; text-indent: 28px;\" > $姓名$ </ span >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span >< span style = \"font-family: 仿宋; font-size: 14px;\" > 同志脱密期内申请出国（境）意见的函》（文号）已收悉，经研究，同意（不同意）该同志出国（境）申请。 </ span ></ p >< p style = \"white-space: normal;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"margin-right: 56px; white-space: normal; text-align: center;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "</ span >< span style = \"font-family: 仿宋; font-size: 14px;\" > 党委（党组） </ span ></ p >< p style = \"white-space: normal; text-indent: 392px;\" >< span style = \"text-decoration-line: underline;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span ></ span >< span style = \"font-family: 仿宋; font-size: 14px; text-indent: 392px;\" >< span style = \"text-decoration:underline;\" >& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "</ span > 年 < span style = \"text-decoration:underline;\" >& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "</ span ></ span >< span style = \"font-family: 仿宋; font-size: 14px; text-indent: 392px;\" > 月 < span style = \"text-decoration:underline;\" >& nbsp;\n" +
              "\n" +
              "& nbsp;\n" +
              "\n" +
              "</ span ></ span >< span style = \"font-family: 仿宋; font-size: 14px; text-indent: 392px;\" > 日 </ span >< span style = \"font-family: 仿宋; font-size: 14px;\" ></ span ></ p >< p style = \"white-space: normal; text-align: center; line-height: 29px;\" >< span style = \"font-family: 仿宋; font-size: 21px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal; line-height: 29px;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal; line-height: 29px;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal;\" >< span style = \"font-family: 仿宋; font-size: 14px;\" >& nbsp;\n" +
              "\n" +
              "</ span ></ p >< p style = \"white-space: normal;\" >< br /></ p >< p >< br /></ p >");

     Iterator it =   str.iterator();

     while(it.hasNext()){
         System.out.println(it.next());

     }

    }

//    public static Set<String> getImgStr(String htmlStr) {
//        Set<String> pics = new HashSet<String>();
//        String img = "";
//        Pattern p_image;
//        Matcher m_image;
//        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
//        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
//        m_image = p_image.matcher(htmlStr);
//        while (m_image.find()) {
//            // 得到<img />数据
//            img = m_image.group();
//            // 匹配<img>中的src数据
//            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
//            while (m.find()) {
//                pics.add(m.group(1));
//            }
//        }
//        return pics;
//    }

//    public static List<String> getImgSrc(String htmlStr) {
//
//        String img = "";
//
//        Pattern p_image;
//
//        Matcher m_image;
//
//        List<String> pics = new ArrayList<String>();
//
//// String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
//
//        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
//
//        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
//
//        m_image = p_image.matcher(htmlStr);
//
//        while (m_image.find()) {
//
//            img = img + "," + m_image.group();
//
//// Matcher m =
//
//// Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
//
//            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
//
//            while (m.find()) {
//
//                pics.add(m.group(1));
//
//            }
//
//        }
//
//        return pics;
//
//    }


    public static  Set<String> getImgStr(String htmlStr) {
        Set<String> pics = new HashSet<String>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }


}
