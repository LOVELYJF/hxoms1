package com.hxoms.common;

import com.hxoms.common.utils.StringUilt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author lijiaojao
 * @date 2020: 04: 17
 */
public class OmsRegInitUtil {

        //辞职、开除公职的，要满足3年（可调整）的期限
        public static int czyear = 3;

        //退休的，撤销的条件为退休后40年（可调整）的期限
        public static int txyear = 3;

        /**
         * 在原有日期基础上加几年或者几天
         * @param date
         * @param year
         * @return
         */
        public static Date getDateByLongYears(Date date, int year){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);//设置起时间
            cal.add(Calendar.YEAR, year);//增加一年
            //cd.add(Calendar.DATE, n);//增加一天
            //cd.add(Calendar.DATE, -10);//减10天
            //cd.add(Calendar.MONTH, n);//增加一个月
            return cal.getTime();
        }



        /**
         * 根据身份证号截取出生日期
         * @param idNumber
         * @return
         */
        public static String getBirthByIdNumber(String idNumber){
            String birthDay="";
            if(StringUilt.stringIsNullOrEmpty(idNumber)) return birthDay;
            if(idNumber.length()== 15){
                birthDay = "19"+idNumber.substring(6,8) + idNumber.substring(8,10)+ idNumber.substring(10,12);
            }else if(idNumber.length()== 18){
                birthDay = idNumber.substring(6,10) + idNumber.substring(10,12)+ idNumber.substring(12,14);
            }
            return birthDay;
        }


        /**
         * 计算两个日期相差年数
         */
        public static int yearDateDiff(String startDate,String endDate) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            Calendar calBegin = Calendar.getInstance(); //获取日历实例
            Calendar calEnd = Calendar.getInstance();
            calBegin.setTime(sdf.parse(startDate)); //字符串按照指定格式转化为日期
            calEnd.setTime(sdf.parse(endDate));
            return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
        }


        /**
         * 计算两个日期相差天数
         */
        public static int dayDiff(Date startDate,Date endDate){
            Calendar  from  =  Calendar.getInstance();
            from.setTime(startDate);
            Calendar  to  =  Calendar.getInstance();
            to.setTime(endDate);
            int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
            return day;
        }

        /**
         * 判断当前姓名是否为复姓
         * @param name
         */
        public static boolean isCompoundSurname(String name) {
            boolean flag = false;
            //百家姓
            String[] surname = {"欧阳","太史","端木","上官","司马","东方","独孤","南宫","万俟","闻人","夏侯","诸葛","尉迟","公羊","赫连",
                    "澹台","皇甫","宗政","濮阳","公冶","太叔","申屠","公孙","慕容","仲孙","钟离","长孙","宇文","司徒","鲜于","司空","闾丘",
                    "子车","亓官","司寇","巫马","公西","颛孙","壤驷","公良","漆雕","乐正","宰父","谷梁","拓跋","夹谷","轩辕","令狐","段干",
                    "百里","呼延","东郭","南门","羊舌","微生","公户","公玉","公仪","梁丘","公仲","公上","公门","公山","公坚","左丘","公伯",
                    "西门","公祖","第五","公乘","贯丘","公皙","南荣","东里","东宫","仲长","子书","子桑","即墨","淳于","达奚","褚师","吴铭",
                    "纳兰","归海"};

            List<String> surNameList = Arrays.asList(surname); //百家姓集合

            String fuxing = name.substring(0,2);
            if (name.length() > 3) {//超过三个字的名字   首先考虑是复姓
                if (surNameList.contains(fuxing)) { //是复姓
                    flag = true;
                }
            }
            return flag;
        }



}



