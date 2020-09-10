package com.hxoms.modules.leaderSupervision.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

       String str =  "\n" +
               "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
               "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
               "<head>\n" +
               "  <meta charset=\"UTF-8\"/>\n" +
               "  <title>表格样式</title>\n" +
               "  <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width\"/>\n" +
               "  <link rel=\"stylesheet\" href=\"https://static.loyalvalleycapital.com/web/css/frame.css\"/>\n" +
               "  <style>\n" +
               "  table.table-separate th{\n" +
               "    font-weight:bold;\n" +
               "    font-size:14px;\n" +
               "    border-top:1px solid #F3EDE9 !important;\n" +
               "  }\n" +
               "  table.table-separate td{\n" +
               "    padding: 13px 0;\n" +
               "    font-weight:100;\n" +
               "  }\n" +
               "  .table-separate td.tit{\n" +
               "    background-color: #f4f9fe;\n" +
               "    font-weight:normal;\n" +
               "    padding:22px 0;\n" +
               "    width:15%;\n" +
               "  }\n" +
               "  .table-separate td.cont{\n" +
               "    text-align: left;\n" +
               "    padding:16px 22px;\n" +
               "    width:85%;\n" +
               "    line-height:175%;\n" +
               "  }\n" +
               "  .table-separate.no-border th{\n" +
               "    border:none;\n" +
               "    text-align: left;\n" +
               "  }\n" +
               "  .table-separate.no-border td{\n" +
               "    text-align: left;\n" +
               "    border:none;\n" +
               "  }\n" +
               " \n" +
               "\ttable {\n" +
               "\t\t\tborder-collapse: collapse;\n" +
               "\t\t\ttable-layout: fixed;\n" +
               "\t\t\tword-break:break-all;\n" +
               "\t\t\tfont-size: 10px;\n" +
               "\t\t\twidth: 100%;\n" +
               "\t\t\ttext-align: center;\n" +
               "\t}\n" +
               "\ttd {\n" +
               "\t\tword-break:break-all;\n" +
               "\t\tword-wrap : break-word;\n" +
               "\t}\n" +
               "\t@page {\n" +
               "\t\tsize:210mm 297mm;//纸张大小A4\n" +
               "\t\tmargin: 0.25in;\n" +
               "\t\t-fs-flow-bottom: \"footer\";\n" +
               "\t\t-fs-flow-left: \"left\";\n" +
               "\t\t-fs-flow-right: \"right\";\n" +
               "\t\tpadding: 1em;\n" +
               "\t\t}\n" +
               "\t\t#footer {\n" +
               "\t\tfont-size: 90%; font-style: italic;\n" +
               "\t\tposition: absolute; top: 0; left: 0;\n" +
               "\t\t-fs-move-to-flow: \"footer\";\n" +
               "\t\t}\n" +
               "\t\t#pagenumber:before {\n" +
               "\t\tcontent: counter(page);\n" +
               "\t\t}\n" +
               "\t\t#pagecount:before {content: counter(pages);\n" +
               "\t\t}\n" +
               "</style>\n" +
               "</head>\n" +
               "<body class=\"bg-white pb-3\"  style = \"font-family: SimSun;\"><div id=\"footer\" style=\"\"/> <title>中共海南省委组织部来文处理单</title>\n" +
               "        <table width=\"1000\">\n" +
               "            <tbody>\n" +
               "                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
               "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">来文单位</td>\n" +
               "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
               "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">来文编号</td>\n" +
               "                    <td colspan=\"3\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
               "                </tr>\n" +
               "                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
               "                    <td  style=\"font-size:20px;text-align:center;\">收文时间</td>\n" +
               "                    <td  style=\"width:150px;font-size:20px;text-align:center;\"><br/></td>\n" +
               "                    <td  style=\"font-size:20px;text-align:center;\">办文编号</td>\n" +
               "                    <td  style=\"width:150px;font-size:20px;text-align:center;\"><br/></td>\n" +
               "                    <td  style=\"font-size:20px;text-align:center;\">密级</td>\n" +
               "                    <td  style=\"width:80px;font-size:20px;text-align:center;\"><br/></td>\n" +
               "                </tr>\n" +
               "                <tr style=\"border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
               "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">文件名称</td>\n" +
               "                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\">关于建议不予办理张新文同志出国（境）备案手续的请示</td>\n" +
               "                </tr>\n" +
               "                <tr style=\"border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
               "                    <td colspan=\"1\" style=\"width:200px;font-size:20px;text-align:center;\">干部监督处拟办意见</td>\n" +
               "                    <td colspan=\"5\" style=\"font-size:20px;min-height:100px;\"> <p>     近日，我处收到关于办理张新文同志赴安道尔共和国备案手续的请示，张新文同志于2020年07月30日至2020年08月07日赴安道尔共和国开展$出访任务$，在外停留4天。经征求省纪委意见，省纪委对张新文出国（境）持异议，不予回复意见。建议不予办理张新文同志出国（境）备案手续。\n" +
               "\n" +
               "       \n" +
               "                     妥否，呈$分管部长$副部长审示。</p></td>\n" +
               "                </tr>\n" +
               "                <tr style=\"border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
               "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">部领导批示：</td>\n" +
               "                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
               "                </tr>\n" +
               "                <tr style=\"border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
               "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">省领导批示：</td>\n" +
               "                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
               "                </tr>\n" +
               "                <tr style=\"border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
               "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">承办结果</td>\n" +
               "                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
               "                </tr>\n" +
               " <tr><td colspan=\"3\"><p> <img src=\"C:\\Users\\Administrator\\Desktop\\u=3068352452,3843479988&fm=26&gp=0.png\"> </p>\n" +
               "</td></tr>\n" +
               "             </tbody>\n" +
               "        </table></body>\n" +
               "</html>";
//     Iterator it =   str.iterator();
//
//     while(it.hasNext()){
//         System.out.println(it.next());
//
//     }

        Set set = getImgStrs(str.toString());

//        String str = ReadFromFile.readFile(fileName).toString();


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


    public static Set<String> getImgStrs(String htmlStr) {
        Set<String> set = null;

        if(htmlStr!=null && !"".equals(htmlStr)){
            String[] split = htmlStr.split("< img src=");
            if(split==null || split.length==0){
                return set;
            }
            set = new HashSet();
            for (int i = 0; i < split.length; i++) {
                if(split[i]!=null && !"".equals(split[i])){
                    String substring = split[i].substring(0, split[i].indexOf(".jpg")+5);
                    set.add(substring);
                }
            }
        }
        return set;
    }






}
