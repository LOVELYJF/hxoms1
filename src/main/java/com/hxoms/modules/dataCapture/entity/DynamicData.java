package com.hxoms.modules.dataCapture.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @authore:wjf
 * @data 2020/4/20 17:41
 * @Description:
 ***/
@Component
@PropertySource("classpath:/dataCapture.porperties")
public class DynamicData {

    public static List<String> a02List ;

    @Value("${data.initSplic}")
    private int initSplic;






    public int getInitSplic() {
        return initSplic;
    }

    public void setInitSplic(int initSplic) {
        this.initSplic = initSplic;
    }

    public static  String orcal_paging_frist = "  SELECT * " +
            "    FROM (SELECT ROWNUM AS rowno,";

    public static  String orcal_paging_last = " WHERE " +
            "                  ROWNUM <=  ? ) temp " +
            "    WHERE temp.rowno > ? ";

    public static String  mysql_paging = " LIMIT ? ,? ";
    
    public static String commonSqlA01 = " a0100," +
            "        a0101," +
            "        a0102," +
            "        a0104," +
            "        a0117_a," +
            "        a0117," +
            "        a0104_a," +
            "        a0111b," +
            "        a0111a," +
            "        a0107," +
            "        a0114a," +
            "        a0114b," +
            "        a0115b," +
            "        a0115a," +
            "        a0120," +
            "        a0128," +
            "        a0121," +
            "        a0122," +
            "        a0127," +
            "        a0160," +
            "        a0163," +
            "        a0165," +
            "        a0180," +
            "        a0141," +
            "        a0187a," +
            "        a0195," +
            "        a0192," +
            "        a0192a," +
            "        a14z101," +
            "        a15z101," +
            "        a0194," +
            "        a0197," +
            "        a0196," +
            "        a2949," +
            "        a0140," +
            "        qrzxl," +
            "        qrzxw," +
            "        qrzxlxx," +
            "        qrzxwxx," +
            "        zzxl," +
            "        zzxw," +
            "        zzxlxx," +
            "        zzxwxx," +
            "        cbdw," +
            "        jsnlsj," +
            "        nrzw," +
            "        nmzw," +
            "        rmly," +
            "        rxz_date," +
            "        id," +
            "        is_deleted," +
            "        modify_user," +
            "        modify_time," +
            "        time_stamp," +
            "        a0184," +
            "        node_id," +
            "        a3921," +
            "        a3927," +
            "" +
            "        a1701," +
            "        zzxx," +
            "        qrzxx," +
            "        a0140_lrmx," +
            "        photodetail," +
            "        a0194a," +
            "        a0221," +
            "        a0192e," +
            "        leadertype," +
            "        tbr," +
            "        tbsj," +
            "        qrzxlb," +
            "        zzxlb," +
            "        a1701_a," +
            "        zgxl," +
            "        zgxlb," +
            "        zgxlxx," +
            "        zgxw," +
            "        zgxwxx," +
            "        cfqn," +
            "        age_2," +
            "        rxz_dates," +
            "        a0196b," +
            "        a0192d," +
            "        a0229," +
            "        change_state," +
            "        zj_type,";
    




    public static String  orcalSqlA01 = orcal_paging_frist +commonSqlA01+
    "  decode(length(a0134),8,to_date(a0134,'yyyy-MM-dd'),to_date(a0134,'yyyy-MM'))," +
            "       decode(length(a0288),8,to_date(a0288,'yyyy-MM-dd'),to_date(a0288,'yyyy-MM'))," +
            "       decode(length(a0144),8,to_date(a0144,'yyyy-MM-dd'),to_date(a0144,'yyyy-MM'))," +
            "       decode(length(a0192c),8,to_date(a0192c,'yyyy-MM-dd'),to_date(a0192c,'yyyy-MM'))" +
            "        FROM A01"
            + orcal_paging_last;


    public  static String mysqlSqlA01 = "select "
            + commonSqlA01 
            +"    a0134," +
            "     a0288," +
            "     a0144," +
            "     a0192c" +
            "    FROM A01" +
            "    order by a0100"
            + mysql_paging;

    public  static String  commonSqlA02="  A0200," +
            "        A0100," +
            "        A0201A," +
            "        A0201B," +
            "        A0201D," +
            "        A0201E," +
            "        A0207," +
            "        A0209," +
            "        A0215A," +
            "        A0215B," +
            "        A0219," +
            "        A0221," +
            "        A0221A," +
            "        A0221T," +
            "        A0222," +
            "        A0223," +
            "        A0225," +
            "        A0229," +
            "        A0243," +
            "        A0245," +
            "        A0247," +
            "        A0251," +
            "        A0251B," +
            "        A0255," +
            "        A0259," +
            "        A0265," +
            "        A0267," +
            "        A0271," +
            "        A0284," +
            "        A0288," +
            "        A4901," +
            "        A4904," +
            "        A4907," +
            "        ID," +
            "        IS_DELETED," +
            "        MODIFY_USER," +
            "        MODIFY_TIME," +
            "        TIME_STAMP," +
            "        NODE_ID," +
            "        A0279," +
            "        A0272," +
            "        A0281";
    
    public  static String  orcalSqlA02 = orcal_paging_frist +commonSqlA02+
            "  FROM A02 "+
             orcal_paging_last;

    public static String commonSqlA05 ="   a0100," +
            "        a0531," +
            "        a0501b," +
            "        a0504," +
            "        a0511," +
            "        a0517," +
            "        a0524," +
            "        modify_user," +
            "        modify_time," +
            "        is_deleted," +
            "        id," +
            "        time_stamp," +
            "        node_id," +
            "        a0500," +
            "        a0526";

    public static String orcalSqlA05 = orcal_paging_frist + commonSqlA05
            + "  FROM A05 "
            +  orcal_paging_last;


    public static String commonSqlA06 = "" +
            "            a0600," +
            "            a0100," +
            "            a0601," +
            "            a0602," +
            "            a0604," +
            "            a0607," +
            "            a0611," +
            "            modify_user," +
            "            modify_time," +
            "            is_deleted," +
            "            id," +
            "            time_stamp," +
            "            node_id";
    public static String orcalSqlA06 = orcal_paging_frist + commonSqlA06
            + "  FROM A06 "
            +  orcal_paging_last;



    public static String commonSqlA08 = "" +
           "         a0801b," +
            "        a0901a," +
            "        a0901b," +
            "        a0804," +
            "        a0807," +
            "        a0811," +
            "        a0904," +
            "        a0814," +
            "        a0824," +
            "        a0827," +
            "        a0837," +
            "        a0100," +
            "        modify_user," +
            "        modify_time," +
            "        is_deleted," +
            "        a0801a," +
            "        id," +
            "        time_stamp," +
            "        node_id," +
            "        a0898," +
            "        is_normal," +
            "        a0899," +
            "        a0834," +
            "        a0835";
    public static String orcalSqlA08 = orcal_paging_frist + " a0800, " +  commonSqlA08
            + "  FROM A08 "
            +  orcal_paging_last;


    public static String commonSqlA14 = "" +
          "           a1400," +
            "         a0100," +
            "         a1424," +
            "         a1404a," +
            "         a1404b," +
            "         a1407," +
            "         a1411a," +
            "         a1414," +
            "         a1415," +
            "         a1428," +
            "         id," +
            "         is_deleted," +
            "         modify_user," +
            "         modify_time," +
            "         time_stamp," +
            "         node_id," +
            "         a14x01," +
            "         a14pzwh";
    public static String orcalSqlA14 = orcal_paging_frist + commonSqlA14
            + "  FROM A14 "
            +  orcal_paging_last;


    public static String commonSqlA15 = "" +
           "       a1500," +
            "      a0100," +
            "      a1517," +
            "      a1521," +
            "      modify_user," +
            "      modify_time," +
            "      is_deleted," +
            "      id," +
            "      time_stamp," +
            "      node_id";
    public static String orcalSqlA15 = orcal_paging_frist + commonSqlA15
            + "  FROM A15 "
            +  orcal_paging_last;


    public static String commonSqlA17 = "" +
            "        a1700," +
            "        a1703," +
            "        a1704," +
            "        a1705," +
            "        is_deleted," +
            "        a1708," +
            "        a0100," +
            "        id," +
            "        modify_user," +
            "        modify_time," +
            "        time_stamp," +
            "        node_id," +
            "        A1701," +
            "        A1702," +
            "        a1706," +
            "        a1707," +
            "        is_normal," +
            "        operate_batch";
    public static String orcalSqlA17 = orcal_paging_frist + commonSqlA17
            + "  FROM A17 "
            +  orcal_paging_last;



    public static String commonSqlA36 = "" +
            "        a3600," +
            "        a0100," +
            "        a3601," +
            "        a3604a," +
            "        a3607," +
            "        a3611," +
            "        a3627," +
            "        id," +
            "        is_deleted," +
            "        modify_user," +
            "        modify_time," +
            "        time_stamp," +
            "        node_id," +
            "        sortid";
    public static String orcalSqlA36 = orcal_paging_frist + commonSqlA36
            + "  FROM A36 "
            +  orcal_paging_last;


    public static String commonSqlA30 = "" +
            "        a3000," +
            "        a0100," +
            "        a3001," +
            "        a3004," +
            "        a3007a," +
            "        a3034," +
            "        a3038," +
            "        3117a," +
            "        a3137," +
            "        a3101," +
            "        id," +
            "        is_deleted," +
            "        modify_user," +
            "        modify_time," +
            "        time_stamp," +
            "        node_id " ;
    public static String orcalSqlA30 = orcal_paging_frist + commonSqlA30
            + "  FROM A30 "
            +  orcal_paging_last;

    public static String commonSqlB01 = "" +
            "        	B0111 as B0100	," +
            "        	B0101	," +
            "        	B0104	," +
            "        	B0107	," +
            "        	B0111	," +
            "        	B0114	," +
            "        	B0117	," +
            "        	B0124	," +
            "        	B0127	," +
            "        	B0131	," +
            "        	B0164	," +
            "        	B0167	," +
            "        	B0183	," +
            "        	B0185	," +
            "        	B0188	," +
            "        	B0189	," +
            "        	B0190	," +
            "        	B0191	," +
            "        	B0191A	," +
            "        	B0192	," +
            "        	B0193	," +
            "        	B0194	," +
            "        	B0227	," +
            "        	B0232	," +
            "        	B0233	," +
            "        	B0238	," +
            "        	B0239	," +
            "        	SORTID	," +
            "        	TIME_STAMP	," +
            "        	MODIFY_USER	," +
            "        	MODIFY_TIME	," +
            "        	IS_DELETED	," +
            "        	B0140	," +
            "        	B0141	," +
            "        	B0142	," +
            "        	B0143	," +
            "        	B0150	," +
            "        	B0180	," +
            "        	B0236	," +
            "        	B0234	," +
            "        	B0121	," +
            "        	BX001	," +
            "        	B0268	," +
            "        	B0269	," +
            "        	POSICOUNTZJ	," +
            "        	ORGPARTTYPE	," +
            "        	ORGSTANDARDNAME	," +
            "        	SUPERVISOR	" ;

    public static String orcalSqlB01 = orcal_paging_frist + commonSqlB01
            + "  FROM B01 "
            +  orcal_paging_last;

    static{
        String[] a02String = {
                "A0200",
                "A0100",
                "A0201A",
                "A0201B",
                "A0201D",
                "A0201E",
                "A0207",
                "A0209",
                "A0215A",
                "A0215B",
                "A0219",
                "A0221",
                "A0221A",
                "A0221T",
                "A0222",
                "A0223",
                "A0225",
                "A0229",
                "A0243",
                "A0245",
                "A0247",
                "A0251",
                "A0251B",
                "A0255",
                "A0259",
                "A0265",
                "A0267",
                "A0271",
                "A0284",
                "A0288",
                "A4901",
                "A4904",
                "A4907",
                "ID",
                "IS_DELETED",
                "MODIFY_USER",
                "MODIFY_TIME",
                "TIME_STAMP",
                "NODE_ID",
                "A0279",
                "A0272",
                "A0281"
        };

        a02List = new ArrayList<>(Arrays.asList(a02String));















    }
}
