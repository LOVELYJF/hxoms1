package com.hxoms.common.util;

import com.hxoms.common.rmbKit.models.Family;
import com.hxoms.common.rmbKit.models.RmTable;
import com.hxoms.common.utils.Base64Util;
import com.hxoms.modules.omsoperator.entity.A17;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/****
 * 生成解析xml 任免表
 * @author Administrator
 *
 * 下午2:37:22 2018年8月11日
 */
public class RmbXMLUtils {
	
	public static void main(String[] args) throws Exception {

    }
 


    /****
     * 创建XML
     * @throws Exception
     */
    public void createXML() throws Exception {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Person");
        Element xingMing = root.addElement("XingMing")
                .addAttribute("company", "Ferrai");
        xingMing.addElement("carname")
                .addAttribute("type", "Ferrari 101")
                .addText("Ferrari 101");
        xingMing.addElement("carname")
                .addAttribute("type", "sports")
                .addText("Ferrari 202");
        RmbXMLUtils.writer(document, "");
    }

    /**
     * 把document对象写入新的文件
     *
     * @param document
     * @throws Exception
     */
    public static void writer(Document document, String filePath) throws Exception {
        // 紧凑的格式
        // OutputFormat format = OutputFormat.createCompactFormat();

        // 排版缩进的格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置编码
        format.setEncoding("UTF-8");
        format.setTrimText(false);
        format.setLineSeparator("\r\n");

        OutputStream fileOutputStream = new FileOutputStream(filePath);
        XMLWriter writer = new XMLWriter(fileOutputStream, format);

        // 写入
        writer.write(document);
        // 立即写入
        writer.flush();
        // 关闭操作
        writer.close();
    }
	


    /**
     * 解析任免表XML为RmTable对象
     *
     * @param file
     */
    public static Map<String, Object> xmlToRmTable(File file) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        RmTable rmb = new RmTable();
        String code = "0";
        String msg = "成功";
        try {
        	SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Element node = document.getRootElement();
            Element xingMing = node.element("XingMing");
            Element xingBie = node.element("XingBie");
            Element chuShengNianYue = node.element("ChuShengNianYue");
            Element minZu = node.element("MinZu");
            Element jiGuan = node.element("JiGuan");
            Element chuShengDi = node.element("ChuShengDi");
            Element ruDangShiJian = node.element("RuDangShiJian");
            Element canJiaGongZuoShiJian = node.element("CanJiaGongZuoShiJian");
            Element jianKangZhuangKuang = node.element("JianKangZhuangKuang");
            Element zhuanYeJiShuZhiWu = node.element("ZhuanYeJiShuZhiWu");
            Element shuXiZhuanYeYouHeZhuanChang = node.element("ShuXiZhuanYeYouHeZhuanChang");
            Element quanRiZhiJiaoYu_XueLi = node.element("QuanRiZhiJiaoYu_XueLi");
            Element quanRiZhiJiaoYu_XueWei = node.element("QuanRiZhiJiaoYu_XueWei");
            Element quanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi = node.element("QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi");
            Element quanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi = node.element("QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi");
            Element zaiZhiJiaoYu_XueLi = node.element("ZaiZhiJiaoYu_XueLi");
            Element zaiZhiJiaoYu_XueWei = node.element("ZaiZhiJiaoYu_XueWei");
            Element zaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi = node.element("ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi");
            Element zaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi = node.element("ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi");
            Element xianRenZhiWu = node.element("XianRenZhiWu");
            Element niRenZhiWu = node.element("NiRenZhiWu");
            Element niMianZhiWu = node.element("NiMianZhiWu");
            Element jianLi = node.element("JianLi");
            Element jiangChengQingKuang = node.element("JiangChengQingKuang");
            Element nianDuKaoHeJieGuo = node.element("NianDuKaoHeJieGuo");
            Element renMianLiYou = node.element("RenMianLiYou");

            rmb.setXingMing(xingMing != null ? xingMing.getText() : "");
            rmb.setXingBie(xingBie != null ? xingBie.getText() : "");
            rmb.setChuShengNianYue(chuShengNianYue != null ? chuShengNianYue.getText() : "");
            rmb.setMinZu(minZu != null ? minZu.getText() : "");
            rmb.setJiGuan(jiGuan != null ? jiGuan.getText() : "");
            rmb.setChuShengDi(chuShengDi != null ? chuShengDi.getText() : "");
            //rmb.setRuDangShiJian(ruDangShiJian.getText());
            rmb.setRuDangShiJianLrmx(ruDangShiJian != null ? ruDangShiJian.getText() : "");
            rmb.setCanJiaGongZuoShiJian(canJiaGongZuoShiJian != null ? canJiaGongZuoShiJian.getText() : "");
            rmb.setJianKangZhuangKuang(jianKangZhuangKuang != null ? jianKangZhuangKuang.getText() : "");
            rmb.setZhuanYeJiShuZhiWu(zhuanYeJiShuZhiWu != null ? zhuanYeJiShuZhiWu.getText() : "");
            rmb.setShuXiZhuanYeYouHeZhuanChang(shuXiZhuanYeYouHeZhuanChang != null ? shuXiZhuanYeYouHeZhuanChang.getText() : "");
            rmb.setQuanRiZhiJiaoYuXueLi(quanRiZhiJiaoYu_XueLi != null ? quanRiZhiJiaoYu_XueLi.getText() : "");
            rmb.setQuanRiZhiJiaoYuXueWei(quanRiZhiJiaoYu_XueWei != null ? quanRiZhiJiaoYu_XueWei.getText() : "");
            rmb.setQrzhiJiaoYuXueLiBiYeYuanXiao(quanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi != null ? quanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi.getText() : "");
            rmb.setQrzhiJiaoYuXueWeiBiYeYuanXiao(quanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi != null ? quanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi.getText() : "");
            rmb.setZaiZhiJiaoYuXueLi(zaiZhiJiaoYu_XueLi != null ? zaiZhiJiaoYu_XueLi.getText() : "");
            rmb.setZaiZhiJiaoYuXueWei(zaiZhiJiaoYu_XueWei != null ? zaiZhiJiaoYu_XueWei.getText() : "");
            rmb.setZaiZhiJiaoYuXueLiBiYeYuanXiao(zaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi != null ? zaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi.getText() : "");
            rmb.setZaiZhiJiaoYuXueWeiBiYeYuanXiao(zaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi != null ? zaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi.getText() : "");
            rmb.setXianRenZhiWu(xianRenZhiWu != null ? xianRenZhiWu.getText() : "");
            rmb.setNiRenZhiWu(niRenZhiWu != null ? niRenZhiWu.getText() : "");
            rmb.setNiMianZhiWu(niMianZhiWu != null ? niMianZhiWu.getText() : "");
            rmb.setJianLi(jianLi != null ? jianLi.getText() : "");
            rmb.setJiangChengQingKuang(jiangChengQingKuang != null ? jiangChengQingKuang.getText() : "");
            rmb.setNianDuKaoHeJieGuo(nianDuKaoHeJieGuo != null ? nianDuKaoHeJieGuo.getText() : "");
            rmb.setRenMianLiYou(renMianLiYou != null ? renMianLiYou.getText() : "");
            //获取家庭成员循环
            Element jiaTingChengYuan = node.element("JiaTingChengYuan");
            List<Element> jiaTingChengYuanItems = jiaTingChengYuan.elements("Item");
            List<Family> jtcyList = new ArrayList<Family>();
            for (Element jtcy : jiaTingChengYuanItems) {
                Family family = new Family();
                Element jtcyChengWei = jtcy.element("ChengWei");
                Element jtcyXingMing = jtcy.element("XingMing");
                Element jtcyChuShengRiQi = jtcy.element("ChuShengRiQi");
                Element jtcyZhengZhiMianMao = jtcy.element("ZhengZhiMianMao");
                Element jtcyGongZuoDanWeiJiZhiWu = jtcy.element("GongZuoDanWeiJiZhiWu");
                family.setChengWei(jtcyChengWei != null ? jtcyChengWei.getText() : "");
                family.setXingMing(jtcyXingMing != null ? jtcyXingMing.getText() : "");
                family.setChuShengRiQi(jtcyChuShengRiQi != null ? jtcyChuShengRiQi.getText() : "");
                family.setZhengZhiMianMao(jtcyZhengZhiMianMao != null ? jtcyZhengZhiMianMao.getText() : "");
                family.setGongZuoDanWeiJiZhiWu(jtcyGongZuoDanWeiJiZhiWu != null ? jtcyGongZuoDanWeiJiZhiWu.getText() : "");
                jtcyList.add(family);
            }
            rmb.setJiaTingChengYuanList(jtcyList);
            Element chengBaoDanWei = node.element("ChengBaoDanWei");
            Element jiSuanNianLingShiJian = node.element("JiSuanNianLingShiJian");
            Element tianBiaoShiJian = node.element("TianBiaoShiJian");
            Element tianBiaoRen = node.element("TianBiaoRen");
            Element ShenFenZheng = node.element("ShenFenZheng");
            Element zhaoPian = node.element("ZhaoPian");
            Element version = node.element("Version");

            rmb.setChengBaoDanWei(chengBaoDanWei != null ? chengBaoDanWei.getText() : "");
            rmb.setTianBiaoShiJian(tianBiaoShiJian != null ? tianBiaoShiJian.getText() : "");
            rmb.setTianBiaoRen(tianBiaoRen != null ? tianBiaoRen.getText() : "");
            rmb.setZhaoPian(zhaoPian != null ? zhaoPian.getText() : "");
            //rmb.setZhaoPianPath(""); 录入时候添加
            rmb.setVersion(version != null ? version.getText() : "");
            rmb.setShenFenZheng(ShenFenZheng != null ? ShenFenZheng.getText() : "");
        } catch (Exception e) {
            code = "1";
            msg = "文件格式有误，解析失败";
            e.printStackTrace();
        }
        returnMap.put("rmb", rmb);
        returnMap.put("code", code);
        returnMap.put("msg", msg);
        return returnMap;
    }

    /****
     * 将任免表对象组装成xml，写入传入的文件路径
     * @param rmb
     * @param fileOutPath
     * @throws Exception
     */
    public static void rmTableToFile(RmTable rmb, String fileOutPath) throws Exception {
        Document document = DocumentHelper.createDocument();
        //姓名
        Element root = document.addElement("Person");
        Element xingMing = root.addElement("XingMing");
        xingMing.setText(rmb.getXingMing() == null ? "" : rmb.getXingMing());
        //性别
        Element xingBie = root.addElement("XingBie");
        xingBie.setText(rmb.getXingBie() == null ? "" : rmb.getXingBie());
        //出生年月
        Element chuShengNianYue = root.addElement("ChuShengNianYue");
        chuShengNianYue.setText(rmb.getChuShengNianYue() == null ? "" : rmb.getChuShengNianYue());
        //民族
        Element minZu = root.addElement("MinZu");
        minZu.setText(rmb.getMinZu() == null ? "" : rmb.getMinZu());
        //籍贯
        Element jiGuan = root.addElement("JiGuan");
        jiGuan.setText(rmb.getJiGuan() == null ? "" : rmb.getJiGuan());
        //出生地
        Element chuShengDi = root.addElement("ChuShengDi");
        chuShengDi.setText(rmb.getChuShengDi() == null ? "" : rmb.getChuShengDi());
        //入党时间
        Element ruDangShiJian = root.addElement("RuDangShiJian");
        ruDangShiJian.setText(rmb.getRuDangShiJianLrmx() == null ? "" : rmb.getRuDangShiJianLrmx());
        //参工时间
        Element canJiaGongZuoShiJian = root.addElement("CanJiaGongZuoShiJian");
        canJiaGongZuoShiJian.setText(rmb.getCanJiaGongZuoShiJian() == null ? "" :rmb.getCanJiaGongZuoShiJian());
        //健康状况
        Element jianKangZhuangKuang = root.addElement("JianKangZhuangKuang");
        jianKangZhuangKuang.setText(rmb.getJianKangZhuangKuang() == null ? "" : rmb.getJianKangZhuangKuang());
        //专业技术职务
        Element zhuanYeJiShuZhiWu = root.addElement("ZhuanYeJiShuZhiWu");
        zhuanYeJiShuZhiWu.setText(rmb.getZhuanYeJiShuZhiWu() == null ? "" : rmb.getZhuanYeJiShuZhiWu());
        //熟悉专业有何专长
        Element shuXiZhuanYeYouHeZhuanChang = root.addElement("ShuXiZhuanYeYouHeZhuanChang");
        shuXiZhuanYeYouHeZhuanChang.setText(rmb.getShuXiZhuanYeYouHeZhuanChang() == null ? "" : rmb.getShuXiZhuanYeYouHeZhuanChang());
        //全日制学历
        Element quanRiZhiJiaoYu_XueLi = root.addElement("QuanRiZhiJiaoYu_XueLi");
        quanRiZhiJiaoYu_XueLi.setText(rmb.getQuanRiZhiJiaoYuXueLi() == null ? "" : rmb.getQuanRiZhiJiaoYuXueLi());//全日制学历
        //全日制学位
        Element quanRiZhiJiaoYu_XueWei = root.addElement("QuanRiZhiJiaoYu_XueWei");
        quanRiZhiJiaoYu_XueWei.setText(rmb.getQuanRiZhiJiaoYuXueWei() == null ? "" : rmb.getQuanRiZhiJiaoYuXueWei());//全日制学位
        //全日制学历学校
        Element quanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi = root.addElement("QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi");
        String qrzXlXx = rmb.getQrzhiJiaoYuXueLiBiYeYuanXiao() == null ? "" : rmb.getQrzhiJiaoYuXueLiBiYeYuanXiao();
        qrzXlXx = StringUtils.replaceToRN(qrzXlXx);
        quanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi.setText(qrzXlXx);//全日制学校
        //全日制学位学校
        Element quanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi = root.addElement("QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi");
        String qrzXwXx = rmb.getQrzhiJiaoYuXueWeiBiYeYuanXiao() == null ? "" : rmb.getQrzhiJiaoYuXueWeiBiYeYuanXiao();
        qrzXwXx = StringUtils.replaceToRN(qrzXwXx);
        quanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi.setText(qrzXwXx);//全日制系及专业
        //在职学历
        Element ZaiZhiJiaoYu_XueLi = root.addElement("ZaiZhiJiaoYu_XueLi");
        ZaiZhiJiaoYu_XueLi.setText(rmb.getZaiZhiJiaoYuXueLi() == null ? "" : rmb.getZaiZhiJiaoYuXueLi());//在职学历
        //在职学位
        Element zaiZhiJiaoYu_XueWei = root.addElement("ZaiZhiJiaoYu_XueWei");
        zaiZhiJiaoYu_XueWei.setText(rmb.getZaiZhiJiaoYuXueWei() == null ? "" : rmb.getZaiZhiJiaoYuXueWei());//在职学位
        //在职学历学校
        Element zaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi = root.addElement("ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi");
        String zzXlXx = rmb.getZaiZhiJiaoYuXueLiBiYeYuanXiao() == null ? "" : rmb.getZaiZhiJiaoYuXueLiBiYeYuanXiao();
        zzXlXx = StringUtils.replaceToRN(zzXlXx);
        zaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi.setText(zzXlXx);
        //在职学位学校
        Element zaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi = root.addElement("ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi");
        String zzXwXx = rmb.getZaiZhiJiaoYuXueWeiBiYeYuanXiao() == null ? "" : rmb.getZaiZhiJiaoYuXueWeiBiYeYuanXiao();
        zzXwXx = StringUtils.replaceToRN(zzXwXx);
        zaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi.setText(zzXwXx);
        //现任职务
        Element xianRenZhiWu = root.addElement("XianRenZhiWu");
        xianRenZhiWu.setText(rmb.getXianRenZhiWu() == null ? "" : rmb.getXianRenZhiWu());
        Element jianLi = root.addElement("JianLi");

        //拼接简历
        String jianliStr = "";
        List<String> jianliList = rmb.getJianLiList();
        if (jianliList != null && jianliList.size() > 0) {
            for (String jl : jianliList) {
                if (jl != null) {
                    jl = StringUtils.replaceToRN(jl);
                    if (!jianliStr.equals("")) {
                        jianliStr += "\r\n" + jl;
                    } else {
                        jianliStr += jl;
                    }

                }
            }
        }
        jianLi.setText(jianliStr);
        //拟任职务
        root.addElement("NiRenZhiWu").setText(rmb.getNiRenZhiWu() == null ? "" : rmb.getNiRenZhiWu());
        //拟免职务
        root.addElement("NiMianZhiWu").setText(rmb.getNiMianZhiWu() == null ? "" : rmb.getNiMianZhiWu());
        //奖惩描述
        Element jiangChengQingKuang = root.addElement("JiangChengQingKuang");
        String jiangCheng = rmb.getJiangChengQingKuang() == null ? "" : rmb.getJiangChengQingKuang();
        jiangCheng = StringUtils.replaceToRN(jiangCheng);
        jiangChengQingKuang.setText(jiangCheng);
        //年度考核结果
        Element nianDuKaoHeJieGuo = root.addElement("NianDuKaoHeJieGuo");
        String nianDuKaoHe = rmb.getNianDuKaoHeJieGuo() == null ? "" : rmb.getNianDuKaoHeJieGuo();
        nianDuKaoHe = StringUtils.replaceToRN(nianDuKaoHe);
        nianDuKaoHeJieGuo.setText(nianDuKaoHe);
        //任免理由
        Element renMianLiYou = root.addElement("RenMianLiYou");
        String liyou = rmb.getRenMianLiYou() == null ? "" : rmb.getRenMianLiYou();
        liyou = StringUtils.replaceToRN(liyou);
        renMianLiYou.setText(liyou);
        //查询家庭成员和社会关系
        Element jiaTingChengYuan = root.addElement("JiaTingChengYuan");
        List<Family> jtcyList = rmb.getJiaTingChengYuanList();
        if (jtcyList != null && jtcyList.size() > 0) {
            for (Family family : jtcyList) {
                Element jtcyItem = jiaTingChengYuan.addElement("Item");
                jtcyItem.addElement("ChengWei").setText(family.getChengWei() == null ? "" : family.getChengWei());
                jtcyItem.addElement("XingMing").setText(family.getXingMing() == null ? "" : family.getXingMing());
                jtcyItem.addElement("ChuShengRiQi").setText(family.getChuShengRiQi() == null ? "" : family.getChuShengRiQi());
                jtcyItem.addElement("ZhengZhiMianMao").setText(family.getZhengZhiMianMao() == null ? "" : family.getZhengZhiMianMao().replace("<br/>",""));
                jtcyItem.addElement("GongZuoDanWeiJiZhiWu").setText(family.getGongZuoDanWeiJiZhiWu() == null ? "" : family.getGongZuoDanWeiJiZhiWu());
            }
        }
        Element chengBaoDanWei = root.addElement("ChengBaoDanWei");
        chengBaoDanWei.setText(rmb.getChengBaoDanWei() == null ? "" : rmb.getChengBaoDanWei());
        Element jiSuanNianLingShiJian = root.addElement("JiSuanNianLingShiJian");
        //jiSuanNianLingShiJian.setText(UtilDateTime.formatDate(new Date(), "yyyyMMdd"));
        jiSuanNianLingShiJian.setText(rmb.getJiSuanNianLingShiJian()==null?"":rmb.getJiSuanNianLingShiJian());
        //填表时间
        Element tianBiaoShiJian = root.addElement("TianBiaoShiJian");
        tianBiaoShiJian.setText(rmb.getTianBiaoShiJian()==null?"":rmb.getTianBiaoShiJian());
        //tianBiaoShiJian.setText(UtilDateTime.formatDate(new Date(), "yyyyMMdd"));
        //填表人
        Element tianBiaoRen = root.addElement("TianBiaoRen");
        tianBiaoRen.setText(rmb.getTianBiaoRen()==null?"":rmb.getTianBiaoRen());

        //身份证
        Element shenFenZheng = root.addElement("ShenFenZheng");
        shenFenZheng.setText(rmb.getShenFenZheng() == null ? "" : rmb.getShenFenZheng());
        //照片
        Element zhaoPian = root.addElement("ZhaoPian");
        zhaoPian.setText(Base64Util.imageToBase64(rmb.getZhaoPianPath()));
        Element version = root.addElement("Version");
        version.setText("3.2.1.6");
        writer(document, fileOutPath);
    }


    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     *
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    /*****
     * 去字符串结尾的空格
     * @param value
     * @return
     */
    public static String trimEnd(char[] value) {
        int len = value.length;
        int st = 0;
        char[] val = value;
        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        return ((st > 0) || (len < value.length)) ? new String(val).substring(st, len) : new String(val);
    }

    public static Map<String, Object> StringToJianLiList(String string) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        string = string.replace("\r\n", "\n").replace("\r", "\n");
        String[] split = string.split("\n\n");
        if (split.length > 0) {
            map.put("A1701_A", split[0].trim());
        }
        map.put("a17List", stringToJianLiList(split[0]));
        return map;
    }

    /****
     * 导入简历处理
     * @param jianLiStr
     * @return
     */
    public static List<A17> stringToJianLiList(String jianLiStr) throws Exception {

        String jianliStr1=jianLiStr;
        List<A17> jianLiList = null;
        if (jianLiStr != null && jianLiStr.length() > 0) {
            int JianLi_DateCharLength = 10;
            jianLiStr = jianLiStr.replace("\n", "\r").replace("&#xD;", "\r");
            if (jianLiStr.contains("\r")) {

            } else {
                //先处理最后一行
                jianLiStr = jianLiStr.replaceAll("(\\d{4}\\.\\d{2}(-|-|—|——|―|─){1,2}\\s{7,9})", "\r$1");
				jianLiStr = jianLiStr.replaceAll("(\\d{4}(-|-|—|——|―){1,2}\\s{7,9})", "\r$1");
                //处理前面的行
                jianLiStr = jianLiStr.replaceAll("(\\d{4}\\.\\d{2}(-|-|—|——|―|─){1,2}\\d{4}\\.\\d{2}\\s{1,2})", "\r$1");
				jianLiStr = jianLiStr.replaceAll("(\\d{4}(-|-|—|——|―|─){1,2}\\d{4}\\s{1,2})", "\r$1");

                if (jianLiStr.startsWith("\r")) {
                    jianLiStr = jianLiStr.substring(1);
                }
                jianLiStr = jianLiStr.replace("                  ", "\r");
            }

            jianLiStr = jianLiStr.replace("\n", "").replace("&#xD;", "\r");
            //person的简历对象集合

            String[] array = jianLiStr.split("\r");
            //转化为简历对象
            for (int j = 0; j < array.length; j++) {
                if (jianLiList == null) {
                    jianLiList = new ArrayList<A17>();
                }



                String singleLineContent = trimEnd(array[j].replace("\n", "").toCharArray());
                //以4位数字开头的视为简历的头
                Pattern pattern = Pattern.compile("^\\s{0,4}[0-9]{4}(-|-|—|——|―|─|\\d|.){0,12}\\s{2}");
                Matcher match = pattern.matcher(singleLineContent);
                if (match.find()) {
                    //1998.09  参加

                    singleLineContent = singleLineContent.trim();

                    singleLineContent=singleLineContent.replaceFirst("\t","  ");

                    String[] jianliStrArray = singleLineContent.split("  ");
                    if (jianliStrArray.length > 0) {

                        String dateStr=jianliStrArray[0];
                        String contentStr=jianliStrArray.length>1?jianliStrArray[jianliStrArray.length-1]:"";

                        List<String> dateListStartWithEnd=getDateArray(dateStr,jianliStr1);

                        A17 a17 = new A17();
                        int indexOfstart = singleLineContent.indexOf("--");
                        if(indexOfstart<0){
                            System.out.println(singleLineContent);
                        }
                        String startdate = dateListStartWithEnd.get(0);
                        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM");
                        a17.setA1701(ft.parse(startdate));
                        if(dateListStartWithEnd.size()>1){
                            a17.setA1702(ft.parse(dateListStartWithEnd.get(1)));
                        }
                        a17.setA1703(contentStr.trim());
                        jianLiList.add(a17);

                    }
                } else {
                    singleLineContent = singleLineContent.trim();
                    //如果第一条简历没有开始时间和结束时间new一条添加内容，若中间任意一条简历没有开始和结束时间内容会追加到上一条内容后面
                    if (jianLiList.size() == 0) {
                        jianLiList.add(new A17());
                    }

                    String contents = jianLiList.get(jianLiList.size() - 1).getA1703();
                    contents += "\r" + singleLineContent;
                    jianLiList.get(jianLiList.size() - 1).setA1703(contents);
                }
            }
        }
        return jianLiList;
    }

    private static List<String> getDateArray(String dateStr,String jianliStr)  throws Exception{
        dateStr=dateStr.replace("-","|")
                .replace("--","|")
                .replace("—","|")
                .replace("——","|")
                .replace("―","|")
                .replace("─","|");
        String[] dateStartWithEnd=dateStr.split("\\|");

        List<String> dateList=new ArrayList<String>();
        int zongchangdu=0;
        //有效的日期个数
        int realDateCounter=0;
        for(int i=0;i<dateStartWithEnd.length;i++){
            String thisStr=dateStartWithEnd[i];
            if(thisStr!=null&&!"".equals(thisStr)) {

                realDateCounter++;

                //2011.09-2-015.07
                if (i == 2) {
                    thisStr = dateStartWithEnd[1] + dateStartWithEnd[2];
                }

                Pattern pattern = Pattern.compile("(\\d{4}(\\.{1,2}\\d{2}))");
                Matcher matcher = pattern.matcher(thisStr);
                //解析正确的次数
                int rightCounter = 0;
                while (matcher.find()) {
                    rightCounter++;
                    String thisDateStr = matcher.group(1);

                    zongchangdu += thisDateStr.length();

                    thisDateStr = thisDateStr.replace("..", ".");

                    dateList.add(thisDateStr);
                }

                //正则解析失败则用纯数字解析
                if (rightCounter == 0) {
                    if (StringUtils.isDateNumber(thisStr)) {
                        //含月
                        if (thisStr.length() >= 6) {
                            String newStr = StringUtils.numberStrToDate(thisStr, "yyyy.MM");
                            zongchangdu += thisStr.length();
                            dateList.add(newStr);
                        }
                        //只有年
                        else if (thisStr.length() == 4) {
                            zongchangdu += thisStr.length();
                            dateList.add(thisStr);
                        }
                    }
                }
            }
        }

        //解析完如果有漏掉的  单独处理第二个日期
        if(dateList.size()<realDateCounter){
            String otherStr=dateStr.replaceFirst("(\\d{4}(\\.{1,2}\\d{2}))","");
            String endDateStr="";
            Pattern pattern2=Pattern.compile("(\\d)");
            Matcher matcher2= pattern2.matcher(otherStr);
            while (matcher2.find()){
                endDateStr+=matcher2.group(1);
            }

            if(StringUtils.isDateNumber(endDateStr))
            {
                //含月
                if(endDateStr.length()>=6){
                    String newStr=StringUtils.numberStrToDate(endDateStr,"yyyy.MM");
                    dateList.add(newStr);
                }
                //只有年
                else if(endDateStr.length()==4){
                    dateList.add(endDateStr);
                }
            }
        }

        if(zongchangdu!=dateStr.replace("|","").replace("..",".").length()){
            System.out.println(jianliStr);
            System.out.println(dateStr);
        }

        for(int i=0;i<dateList.size();i++){
            if(!StringUtils.isDateNumber(dateList.get(i))){
                dateList.set(i,null);
            }
            else {
                String strAtDB= StringUtils.numberStrToDate(dateList.get(i),"yyyy.MM");
                dateList.set(i,strAtDB);
            }
        }


        return dateList;
    }

    /******
     * 解析lrm 为任免表对象 读取文本，按照,截取
     * @param txtfile
     * @return
     */
    public static Map<String, Object> txtToRmTable(File txtfile) throws Exception{
        Map<String, Object> returnMap = new HashMap<String, Object>();
        RmTable rmb = new RmTable();
        String code="0";
        String msg = "成功";
        if(txtfile!=null && txtfile.isFile() && txtfile.exists()) {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(txtfile), "gbk");
            StringBuilder result = new StringBuilder();
            BufferedReader br = new BufferedReader(isr);
            String lrmTxt = null;
            while ((lrmTxt = br.readLine()) != null) {
                result.append(lrmTxt);
            }
                System.out.println(result);
                String[] rmbArray = result.toString().split(",");
                if(rmbArray!=null && rmbArray.length==33){

                        rmb.setXingMing(rmbArray[0].replace("\"", ""));
                        rmb.setXingBie(rmbArray[1].replace("\"", ""));
                        rmb.setChuShengNianYue(rmbArray[2].replace("\"", ""));
                        rmb.setMinZu(rmbArray[3].replace("\"", ""));
                        rmb.setJiGuan(rmbArray[4].replace("\"", ""));
                        rmb.setRuDangShiJianLrmx(rmbArray[5].replace("\"", "").replace(".",""));
                        rmb.setJianKangZhuangKuang(rmbArray[6].replace("\"", ""));
                        rmb.setChuShengDi(rmbArray[7].replace("\"", ""));
                        rmb.setCanJiaGongZuoShiJian(rmbArray[8].replace("\"", ""));
                        //学历学位 如：大学#经济学学士@大学#哲学博士
                        String xlxw = rmbArray[9].replace("\"", "");
                            String[] qrzxlxwArry = xlxw.split("@");
                                    //全日制学历
                                    String qrzxl = "";
                                    //全日制学位
                                    String qrzxw = "";
                                    //在职学历
                                    String zzxl = "";
                                    //在职学位
                                    String zzxw = "";
                              if(qrzxlxwArry.length==1){
                                  //全日制学历学位
                                  String[] qrzxlxw = qrzxlxwArry[0].split("#");
                                  if(qrzxlxw.length==1){
                                      //全日制学历
                                      qrzxl = qrzxlxw[0];
                                  }else if(qrzxlxw.length>1){
                                      //全日制学历
                                      qrzxl = qrzxlxw[0];
                                      //全日制学位
                                      qrzxw = qrzxlxw[1];
                                  }
                              }else  if(qrzxlxwArry.length>1){
                                  //全日制学历学位
                                  String[] qrzxlxw = qrzxlxwArry[0].split("#");
                                  if(qrzxlxw.length==1){
                                      //全日制学历
                                      qrzxl = qrzxlxw[0];
                                  }else if(qrzxlxw.length>1){
                                      //全日制学历
                                      qrzxl = qrzxlxw[0];
                                      //全日制学位
                                      qrzxw = qrzxlxw[1];
                                  }
                                  //在职学历学位
                                  String[] zzxlxw = qrzxlxwArry[1].split("#");
                                  if(zzxlxw.length==1){
                                      //在职学历
                                      zzxl = zzxlxw[0];
                                  }else if(zzxlxw.length>1){
                                      //在职学历
                                      zzxl = zzxlxw[0];
                                      //在职学位
                                      zzxw = zzxlxw[1];
                                  }

                              }

                        //毕业院校 如：123123财政学专业#123123财政学专业@qwe经济学专业#qwe经济学专业
                        String byyx = rmbArray[10].replace("\"", "");
                                String[] qrzxlxwbyyx = byyx.split("@");
                                //全日制学历毕业院校专业
                                String qrzxlbyyx = "";
                                //全日制学位毕业院校专业
                                String qrzxwbyyx = "";
                                //在职学历毕业院校专业
                                String zzxlbyyx = "";
                                //在职学位毕业院校专业
                                String zzxwbyyx = "";

                             if(qrzxlxwbyyx.length==1){
                                 //全日制学历学位毕业院校专业
                                 String[] qrz = qrzxlxwbyyx[0].split("#");
                                 if(qrz.length==1){
                                     //全日制学历毕业院校专业
                                     qrzxlbyyx = qrz[0];
                                 }else if(qrz.length>1){
                                     //全日制学历毕业院校专业
                                     qrzxlbyyx = qrz[0];
                                     //全日制学位毕业院校专业
                                     qrzxwbyyx = qrz[1];
                                 }
                             }else if(qrzxlxwbyyx.length>1){
                                 //全日制学历学位毕业院校专业
                                 String[] qrz = qrzxlxwbyyx[0].split("#");
                                 if(qrz.length==1){
                                     //全日制学历毕业院校专业
                                     qrzxlbyyx = qrz[0];
                                 }else if(qrz.length>1){
                                     //全日制学历毕业院校专业
                                     qrzxlbyyx = qrz[0];
                                     //全日制学位毕业院校专业
                                     qrzxwbyyx = qrz[1];
                                 }
                                 //在职学历学位毕业院校专业
                                 String[] zz = qrzxlxwbyyx[1].split("#");
                                 if(zz.length==1){
                                     //在职学历毕业院校专业
                                     zzxlbyyx = zz[0];
                                 }else if(zz.length>1){
                                     //在职学历毕业院校专业
                                     zzxlbyyx = zz[0];
                                     //在职学位毕业院校专业
                                     zzxwbyyx = zz[1];
                                 }
                             }


                    rmb.setQuanRiZhiJiaoYuXueLi(qrzxl);
                    rmb.setQuanRiZhiJiaoYuXueWei(qrzxw);
                    rmb.setQrzhiJiaoYuXueLiBiYeYuanXiao(qrzxlbyyx);
                    rmb.setQrzhiJiaoYuXueWeiBiYeYuanXiao(qrzxwbyyx);
                    rmb.setZaiZhiJiaoYuXueLi(zzxl);
                    rmb.setZaiZhiJiaoYuXueWei(zzxw);
                    rmb.setZaiZhiJiaoYuXueLiBiYeYuanXiao(zzxlbyyx);
                    rmb.setZaiZhiJiaoYuXueWeiBiYeYuanXiao(zzxwbyyx);
                    rmb.setZhuanYeJiShuZhiWu(rmbArray[11].replace("\"", ""));

                    //未知
                    String weizhi1 = rmbArray[12].replace("\"", "");
                    String weizhi2 = rmbArray[13].replace("\"", "");
                    String weizhi3 = rmbArray[14].replace("\"", "");
                    String weizhi4 = rmbArray[15].replace("\"", "");
                    rmb.setShuXiZhuanYeYouHeZhuanChang(rmbArray[16].replace("\"", ""));
                    //简历
                    String jl =  rmbArray[17].replace("\"", "");
                    rmb.setJianLi(jl);

                    rmb.setJiangChengQingKuang(rmbArray[18].replace("\"", ""));
                    rmb.setNianDuKaoHeJieGuo(rmbArray[19].replace("\"", ""));

                    String[] jtcycw = rmbArray[20].replace("\"", "").split("@");
                    String[] jtcyxm = rmbArray[21].replace("\"", "").split("@");
                    String[] jtcycsrq = rmbArray[22].replace("\"", "").split("@");
                    String[] jtcyzzmm = rmbArray[23].replace("\"", "").split("@");
                    String[] jtcygzdwzw = rmbArray[24].replace("\"", "").split("@");

                    //家庭成员
                    List<Family> jtcyList = new ArrayList<Family>();
                    for (int i=0;i<jtcycw.length;i++) {
                        Family family = new Family();
                        family.setChengWei(jtcycw[i]);
                        family.setXingMing(jtcyxm[i]);
                        family.setChuShengRiQi(jtcycsrq[i]);
                        family.setZhengZhiMianMao(jtcyzzmm[i]);
                        family.setGongZuoDanWeiJiZhiWu(jtcygzdwzw[i]);
                        jtcyList.add(family);
                    }
                    rmb.setJiaTingChengYuanList(jtcyList);

                    rmb.setXianRenZhiWu(rmbArray[25].replace("\"", ""));
                    rmb.setNiRenZhiWu(rmbArray[26].replace("\"", ""));
                    rmb.setNiMianZhiWu(rmbArray[27].replace("\"", ""));
                    rmb.setRenMianLiYou(rmbArray[28].replace("\"", ""));
                    rmb.setChengBaoDanWei(rmbArray[29].replace("\"", ""));
                    //没用
                    String jisuannianlingshijian = rmbArray[30].replace("\"", "");
                    rmb.setTianBiaoRen(rmbArray[31].replace("\"", ""));
                    rmb.setTianBiaoShiJian(rmbArray[32].replace("\"", ""));
                }else {
                    msg="数据文件格式有误";
                    code = "1";
                    System.out.println("导入的lrm文件数据格式有误：" + rmbArray);
                }


            br.close();
        } else {
            msg="文件不存在";
            code = "1";
            System.out.println("文件不存在!");
        }
        returnMap.put("rmb", rmb);
        returnMap.put("code", code);
        returnMap.put("msg", msg);

        return returnMap;
    }

}
