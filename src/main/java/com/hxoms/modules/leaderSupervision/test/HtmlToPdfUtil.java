package com.hxoms.modules.leaderSupervision.test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlToPdfUtil {
    private static Logger logger = LoggerFactory.getLogger(HtmlToPdfUtil.class);

    // wkhtmltopdf在windows系统中的路径
    private static String WKHTMLTOPDF_WINDOWS_URL = "D:\\tools\\pdf\\wkhtmltox\\bin\\wkhtmltopdf.exe";
    // wkhtmltopdf在linux系统中的路径
    private static String WKHTMLTOPDF_LINUX_URL= "/usr/local/bin/wkhtmltopdf";

    /**
     * 生成PDF文件
     * @param htmlFilePath html地址
     * @param destPath 保存pdf地址
     * @return
     */
    /**
     *        StringBuffer stringBuffer = new StringBuffer();
     *         stringBuffer.append("<!DOCTYPE html>")
     *                 .append("<html lang=\"en\">")
     *                 .append("<head>")
     *                 .append("<meta charset=\"UTF-8\">")
     * //                .append("<title>Title</title>")
     * //                .append("</head>")
     * //                .append("<body>")
     * //                .append("<div style=\"width: 20%;margin: 40px auto 0 auto;\">")
     * //                .append("<div><h1>HtmlToPdf</h1></div>")
     * //                .append("<div>")
     * //                .append("<table border=\"1\" cellspacing=\"0\" style=\"width: 200px; min-height: 25px; line-height: 25px; text-align: center; border-collapse: collapse; padding:2px;\">")
     * //                .append("<tr>")
     * //                .append("<td>姓名</td>")
     * //                .append("<td>性别</td>")
     * //                .append("</tr>");
     * //        if (data != null && data.get("list") != null){
     * //            List<Map> list = (List<Map>)data.get("list");
     * //            for(Map map : list){
     * //                stringBuffer = stringBuffer.append("<tr>")
     * //                        .append("<td>").append(map.get("name")).append("</td>")
     * //                        .append("<td>").append(map.get("sex")).append("</td>")
     * //                        .append("</tr>");
     * //            }
     * //        }
     *                .append("</table>")
     *                 .append("<div style=\"width:771px;font-size:40px;text-align:center;\">中共海南省委组织部来文处理单</div>\n" +
     *                         "        <table width=\"771\" border>\n" +
     *                         "            <tbody>\n" +
     *                         "                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
     *                         "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">来文单位</td>\n" +
     *                         "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
     *                         "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">来文编号</td>\n" +
     *                         "                    <td colspan=\"3\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
     *                         "                </tr>\n" +
     *                         "                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
     *                         "                    <td  style=\"font-size:20px;text-align:center;\">收文时间</td>\n" +
     *                         "                    <td  style=\"width:150px;font-size:20px;text-align:center;\"><br/></td>\n" +
     *                         "                    <td  style=\"font-size:20px;text-align:center;\">办文编号</td>\n" +
     *                         "                    <td  style=\"width:150px;font-size:20px;text-align:center;\"><br/></td>\n" +
     *                         "                    <td  style=\"font-size:20px;text-align:center;\">密级</td>\n" +
     *                         "                    <td  style=\"width:80px;font-size:20px;text-align:center;\"><br/></td>\n" +
     *                         "                </tr>\n" +
     *                         "                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
     *                         "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">文件名称</td>\n" +
     *                         "                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\">关于建议不予办理张猛同志出国（境）备案手续的请示</td>\n" +
     *                         "                </tr>\n" +
     *                         "                <tr style=\"height:150px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
     *                         "                    <td colspan=\"1\" style=\"width:200px;font-size:20px;text-align:center;\">干部监督处拟办意见</td>\n" +
     *                         "                    <td colspan=\"5\" style=\"font-size:20px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;近日，我处收到关于办理张猛同志赴备案手续的请示，张猛同志于0121年08月01日至0121年08月01日赴开展按三次SaaS，在外停留13天。经征求省纪委意见，省纪委对张猛出国（境）持异议，不予回复意见。建议不予办理张猛同志出国（境）备案手续。\n" +
     *                         "\n" +
     *                         "    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;妥否，呈$分管部长$副部长审示。</td>\n" +
     *                         "                </tr>\n" +
     *                         "                <tr style=\"height:80px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
     *                         "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">部领导批示：</td>\n" +
     *                         "                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
     *                         "                </tr>\n" +
     *                         "                <tr style=\"height:80px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
     *                         "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">省领导批示：</td>\n" +
     *                         "                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
     *                         "                </tr>\n" +
     *                         "                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
     *                         "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">承办结果</td>\n" +
     *                         "                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\n" +
     *                         "                </tr>\n" +
     *                         "                <tr><td colspan=\"3\"><p><img src=\"F:\\ueditorImgUrl\\AC2D011B-94DF-445A-8953-E9FE6CA9B593.jpg\" title=\"\" alt=\"AC2D011B-94DF-445A-8953-E9FE6CA9B593.jpg\" width=\"196\" height=\"154\"/></p>\n</td></tr>\n "+
     *
     *                         "            </tbody>\n" +
     *                         "        </table>")
     *
     * //                .append("<table width=\"771\">\n" +
     * //                        "            <tbody>\n" +
     * //                        "                <tr style=\"height:67px\" class=\"firstRow\">\n" +
     * //                        "                    <td colspan=\"6\" width=\"771\" style=\"font-size:40px;text-align:center;\">中 共 海 南 省 委 组 织 部</td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:59px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
     * //                        "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">承 办 单 位：</td>\n" +
     * //                        "                    <td colspan=\"3\" style=\"font-size:20px;border-right:1px solid #666;\">干  部  监  督  处</td>\n" +
     * //                        "                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">拟 稿 人：</td>\n" +
     * //                        "                    <td colspan=\"1\" style=\"font-size:20px;\">$用户$</td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:59px\">\n" +
     * //                        "                    <td colspan=\"3\" style=\"font-size:20px;border-right:1px solid #666;\">&nbsp;&nbsp;处领导签署：</td>\n" +
     * //                        "                    <td colspan=\"3\" style=\"font-size:20px;\">&nbsp;&nbsp;部领导批示：</td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:100px;border-bottom:1px solid #666;\">\n" +
     * //                        "                    <td colspan=\"3\" style=\"font-size:20px;border-right:1px solid #666;\"><br/></td>\n" +
     * //                        "                    <td colspan=\"3\" style=\"font-size:20px;\"><br/></td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:59px;\">\n" +
     * //                        "                    <td ></td>\n" +
     * //                        "                    <td ></td>\n" +
     * //                        "                    <td colspan=\"2\"><div style =\"width:100%;height:30px;border:1px solid #666;text-align:center;\"></div></td>\n" +
     * //                        "                    <td ></td>\n" +
     * //                        "                    <td ></td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:59px\">\n" +
     * //                        "                    <td colspan=\"6\" style=\"font-size:26px;text-align:center;\">关于$姓名$同志因私出国（境）的审查意见</td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:59px\">\n" +
     * //                        "                    <td colspan=\"6\" style=\"font-size:20px;\">&nbsp;&nbsp;省公安厅：</td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:120px\">\n" +
     * //                        "                    <td colspan=\"6\" style=\"font-size:20px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关于$姓名$同志赴$国家（地区）$$出国事由$事宜，已经省政府办公厅党组主要领导批准，境外停留$停留时间$天，我部审查同意，请按相关规定办理手续。</td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:59px\">\n" +
     * //                        "                    <td></td>\n" +
     * //                        "                    <td></td>\n" +
     * //                        "                    <td></td>\n" +
     * //                        "                    <td></td>\n" +
     * //                        "                    <td colspan=\"2\" style=\"font-size:20px;\">中共海南省委组织部</td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:59px\">\n" +
     * //                        "                    <td></td>\n" +
     * //                        "                    <td></td>\n" +
     * //                        "                    <td></td>\n" +
     * //                        "                    <td></td>\n" +
     * //                        "                    <td colspan=\"2\" style=\"font-size:20px;\">$当前时间$</td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:59px;border-top:1px solid #666;border-bottom:1px solid #666;\">\n" +
     * //                        "                    <td colspan=\"4\" style=\"font-size:20px;text-align:center;\">中共海南省委组织部办公室</td>\n" +
     * //                        "                    <td colspan=\"2\" style=\"font-size:20px;text-align:center;\">$当前时间$印发</td>\n" +
     * //                        "                </tr>\n" +
     * //                        "                <tr style=\"height:22px;\">\n" +
     * //                        "                </tr>\n" +
     * //                        "            </tbody>\n" +
     * //                        "        </table>\n")
     *
     *                 .append("</div>")
     *                 .append("</div>")
     *                 .append("</body>")
     *                 .append("</html>");
     *
     *
     * **/
    public static boolean convert(String htmlFilePath, String destPath) {
        File file = new File(destPath);
        File parent = file.getParentFile();
        // 如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder cmd = new StringBuilder();
        String toPdfTool = "";
        if (System.getProperty("os.name").indexOf("Windows") == -1) {
            // linux 系统
            toPdfTool = WKHTMLTOPDF_LINUX_URL;
        }else{
            // windows 系统
            toPdfTool = WKHTMLTOPDF_WINDOWS_URL;
        }
        //注意命令，html路径不能加双引号，我遇到坑了，加上双引号虽然wendows系统能正常访问，但是Linux系统会访问不到。
        cmd.append(toPdfTool).append(" ").append(htmlFilePath).append(" ").append(file);
        System.out.println(cmd.toString());
        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将html转pdf
     * @param data 页面数据
     * @param name pdf文件名
     */
    public static void htmlToPdf(Map data,String name) {
        String fileName = "D:/pdf/" + name + (int) (Math.random() * 100000);
        //获取html文件路径
        String htmlStr = strToHtmlFile(getHtmlStr(data), fileName + ".html");
        //将html文件转换成pdf文件
        boolean result = convert(htmlStr, fileName + ".pdf");
        if(result){
            logger.info("生成PDF文件成功！");
        }else{
            logger.info("生成PDF文件失败！");
        }
    }

    /**
     * 将html
     * @param htmlStr html字符串
     * @param path html文件保存地址
     * @return
     */
    public static String strToHtmlFile(String htmlStr, String path) {
        File file = new File(path);
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(htmlStr.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    /**
     * 生成html字符串
     * @param data
     * @return
     */
    private static String getHtmlStr(Map data){

        StringBuffer sb = new StringBuffer();

        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
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
                "<body class=\"bg-white pb-3\"  style = \"font-family: SimSun;\">")
                .append("<div id=\"footer\" style=\"\"/> ")
//                .append("<div style=\"width:1000px;font-size:40px;text-align:left;\">中共海南省委组织部来文处理单</div>\n" +
                .append("<title>中共海南省委组织部来文处理单</title>\n" +
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
                        "       \n                     妥否，呈$分管部长$副部长审示。</p></td>\n" +
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
                        " <tr><td colspan=\"3\"><p>" +
                        " <img src=\"C:\\Users\\Administrator\\Desktop\\u=3068352452,3843479988&fm=26&gp=0.png\"> "+
                        " <tr><td colspan=\"3\"><p><img class=\"hidden\"  src=\"data:image/jpg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAGkAfQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA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\" title=\"\" alt=\"AC2D011B-94DF-445A-8953-E9FE6CA9B593.jpg\" width=\"196\" height=\"154\"  /></p>\n</td></tr>\n "+
                        "</p>\n</td></tr>\n "+

                        "            </tbody>\n" +
                        "        </table>")
                .append("</body>\n" +
                        "</html>");

        return sb.toString();
    }

    public static void main(String[] args) {
        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("name","张三");
        map.put("sex","男");
        list.add(map);
        map = new HashMap();
        map.put("name","李四");
        map.put("sex","男");
        list.add(map);
        Map data = new HashMap();
        data.put("list",list);
        //将html字符串转pdf
        HtmlToPdfUtil.htmlToPdf(data,"index");
        //直接将页面转PDF
        //HtmlToPdfUtil.convert("https://www.baidu.com/", "E:/pdf/index.pdf");
    }
}
