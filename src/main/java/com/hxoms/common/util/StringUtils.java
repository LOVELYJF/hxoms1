package com.hxoms.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    public static boolean contains(String[] arr, String searchStr) {
        if (arr == null || searchStr == null) {
            return false;
        }
        for (String str : arr) {
            if (searchStr.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * StringUtils.capitalize(null)  = null
     * StringUtils.capitalize("")    = ""
     * StringUtils.capitalize("cat") = "Cat"
     * StringUtils.capitalize("cAt") = "CAt"
     * StringUtils.capitalize("'cat'") = "'cat'"
     *
     * @param str
     * @return
     */
    public static String capitalize(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final char firstChar = str.charAt(0);
        final char newChar = Character.toTitleCase(firstChar);
        if (firstChar == newChar) {
            // already capitalized
            return str;
        }

        char[] newChars = new char[strLen];
        newChars[0] = newChar;
        str.getChars(1, strLen, newChars, 1);
        return String.valueOf(newChars);
    }

    public static boolean isNullOrEmpty(String strobj) {
        if (strobj != null && !strobj.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isDateNumber(String numberStr) {
        boolean isDateFormat = false;
        try {
            numberStr = numberStr.replaceAll("[^\\d]", "");
            String format = "";

            if (numberStr.length() <= 4) {
                format = "yyyy.MM.dd";
                numberStr += ".01.01";
            } else if (numberStr.length() == 6) {
                format = "yyyy.MM";
                numberStr = numberStr.substring(0, 4) + "." + numberStr.substring(4, 6);
            } else if (numberStr.length() == 5) {
                format = "yyyy.MM";
                numberStr = numberStr.substring(0, 4) + ".0" + numberStr.substring(4, 5);
            } else if (numberStr.length() == 8) {
                format = "yyyy.MM.dd";
                numberStr = numberStr.substring(0, 4) + "." + numberStr.substring(4, 6) + "." + numberStr.substring(6, 8);

            } else {
                throw new Exception("格式不正确");
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            simpleDateFormat.setLenient(false);
            Date date = simpleDateFormat.parse(numberStr);
            isDateFormat = true;
        } catch (Exception e) {
            isDateFormat = false;
        }
        return isDateFormat;
    }

    public static Date numberStrToDate(String numberStr) throws Exception {

        numberStr = numberStr.replaceAll("[^\\d]", "");
        String format = "";
        if (numberStr.length() <= 4) {
            format = "yyyy.MM.dd";
            numberStr += ".01.01";
        } else if (numberStr.length() == 5) {
            format = "yyyy.MM";
            numberStr = numberStr.substring(0, 4) + ".0" + numberStr.substring(4, 5);
        } else if (numberStr.length() == 6) {
            format = "yyyy.MM";
            numberStr = numberStr.substring(0, 4) + "." + numberStr.substring(4, 6);
        } else if (numberStr.length() == 8) {
            format = "yyyy.MM.dd";
            numberStr = numberStr.substring(0, 4) + "." + numberStr.substring(4, 6) + "." + numberStr.substring(6, 8);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setLenient(false);
        Date date = simpleDateFormat.parse(numberStr);
        return date;
    }

    public static String numberStrToDate(String numberStr, String format) throws Exception {

        Date date1 = numberStrToDate(numberStr);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setLenient(false);
        String strvalue = simpleDateFormat.format(date1);
        return strvalue;
    }

    public static String replaceToRN(String strSource) throws Exception {
        if (strSource != null)
            strSource = strSource.replace("\n", "\r")
                    .replace("<br />", "\r")
                    .replace("<br/>", "\r")
                    //.replace("\r\r", "\r")
                    .replace("\r", "\r\n");
        return strSource;
    }
}
