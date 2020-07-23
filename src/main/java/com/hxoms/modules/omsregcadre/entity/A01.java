package com.hxoms.modules.omsregcadre.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.IgnoreLogAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

import java.math.BigDecimal;
import java.util.Date;
/**
 * a01
 */
@TableAnnotation(TableName = "a01", TableDescription="人员基本信息表")
public class A01 {
    /**
     *
     */
    @IdAnnotation
    @ColumnAnnotation(FieldName = "a0100",  FieldDescription="")
    private String a0100;

    /**
     * 姓名
     */
    @ColumnAnnotation(FieldName = "A0101",  FieldDescription="姓名")
    private String a0101;

    /**
     * 姓名拼音缩写
     */
    @ColumnAnnotation(FieldName = "A0102",  FieldDescription="姓名拼音缩写")
    private String a0102;

    /**
     * 性别
     */
    @ColumnAnnotation(FieldName = "A0104",  FieldDescription="性别")
    private String a0104;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0117_a",  FieldDescription="")
    private String a0117A;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0117",  FieldDescription="")
    private String a0117;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0104_a",  FieldDescription="")
    private String a0104A;

    /**
     * 籍贯代码
     */
    @ColumnAnnotation(FieldName = "A0111B",  FieldDescription="籍贯代码")
    private String a0111b;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0111a",  FieldDescription="")
    private String a0111a;

    /**
     * 出生年月
     */
    @ColumnAnnotation(FieldName = "A0107",  FieldDescription="出生年月")
    private String a0107;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0114a",  FieldDescription="")
    private String a0114a;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0114b",  FieldDescription="")
    private String a0114b;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0115b",  FieldDescription="")
    private String a0115b;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0115a",  FieldDescription="")
    private String a0115a;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0120",  FieldDescription="")
    private String a0120;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0128",  FieldDescription="")
    private String a0128;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0144",  FieldDescription="")
    private Date a0144;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0121",  FieldDescription="")
    private String a0121;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0122",  FieldDescription="")
    private String a0122;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0127",  FieldDescription="")
    private String a0127;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0160",  FieldDescription="")
    private String a0160;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0163",  FieldDescription="")
    private String a0163;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0165",  FieldDescription="")
    private String a0165;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0180",  FieldDescription="")
    private String a0180;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0141",  FieldDescription="")
    private String a0141;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0187a",  FieldDescription="")
    private String a0187a;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0195",  FieldDescription="")
    private String a0195;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0192a",  FieldDescription="")
    private String a0192a;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0194",  FieldDescription="")
    private String a0194;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0197",  FieldDescription="")
    private String a0197;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0196",  FieldDescription="")
    private String a0196;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a2949",  FieldDescription="")
    private String a2949;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0140",  FieldDescription="")
    private String a0140;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "qrzxl",  FieldDescription="")
    private String qrzxl;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "qrzxw",  FieldDescription="")
    private String qrzxw;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "qrzxlxx",  FieldDescription="")
    private String qrzxlxx;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "qrzxwxx",  FieldDescription="")
    private String qrzxwxx;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zzxl",  FieldDescription="")
    private String zzxl;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zzxw",  FieldDescription="")
    private String zzxw;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zzxlxx",  FieldDescription="")
    private String zzxlxx;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zzxwxx",  FieldDescription="")
    private String zzxwxx;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "cbdw",  FieldDescription="")
    private String cbdw;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "jsnlsj",  FieldDescription="")
    private String jsnlsj;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "nrzw",  FieldDescription="")
    private String nrzw;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "nmzw",  FieldDescription="")
    private String nmzw;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "rmly",  FieldDescription="")
    private String rmly;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "rxz_date",  FieldDescription="")
    private Date rxzDate;

    /**
     * 主键
     */
    @ColumnAnnotation(FieldName = "ID",  FieldDescription="主键")
    private String id;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "is_deleted",  FieldDescription="")
    private String isDeleted;

    /**
     * 操作用户
     */
    @ColumnAnnotation(FieldName = "MODIFY_USER",  FieldDescription="操作用户")
    private String modifyUser;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "modify_time",  FieldDescription="")
    private Date modifyTime;

    /**
     * 时间戳
     */
    @ColumnAnnotation(FieldName = "TIME_STAMP",  FieldDescription="时间戳")
    private Date timeStamp;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0184",  FieldDescription="")
    private String a0184;

    /**
     * 节点编码
     */
    @ColumnAnnotation(FieldName = "NODE_ID",  FieldDescription="节点编码")
    private String nodeId;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3921",  FieldDescription="")
    private String a3921;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a3927",  FieldDescription="")
    private String a3927;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a01x01",  FieldDescription="")
    private Long a01x01;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a01x02",  FieldDescription="")
    private BigDecimal a01x02;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zzxx",  FieldDescription="")
    private String zzxx;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "qrzxx",  FieldDescription="")
    private String qrzxx;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0134",  FieldDescription="")
    private Date a0134;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0140_lrmx",  FieldDescription="")
    private String a0140Lrmx;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "photodetail",  FieldDescription="")
    private String photodetail;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0194a",  FieldDescription="")
    private String a0194a;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0221",  FieldDescription="")
    private String a0221;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0288",  FieldDescription="")
    private Date a0288;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0192e",  FieldDescription="")
    private String a0192e;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0192c",  FieldDescription="")
    private Date a0192c;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "leadertype",  FieldDescription="")
    private Integer leadertype;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "tbr",  FieldDescription="")
    private String tbr;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "tbsj",  FieldDescription="")
    private String tbsj;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "qrzxlb",  FieldDescription="")
    private String qrzxlb;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zzxlb",  FieldDescription="")
    private String zzxlb;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0221a",  FieldDescription="")
    private String a0221a;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "is_normal",  FieldDescription="")
    private String isNormal;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "operate_batch",  FieldDescription="")
    private String operateBatch;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zgxl",  FieldDescription="")
    private String zgxl;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zgxlb",  FieldDescription="")
    private String zgxlb;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zgxlxx",  FieldDescription="")
    private String zgxlxx;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zgxw",  FieldDescription="")
    private String zgxw;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zgxwxx",  FieldDescription="")
    private String zgxwxx;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "gsfaqk",  FieldDescription="")
    private String gsfaqk;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "cfqn",  FieldDescription="")
    private String cfqn;

    /**
     *
     */
    @TableField(value = "age_2")
    @ColumnAnnotation(FieldName = "age_2",  FieldDescription="")
    private String age2;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0196b",  FieldDescription="")
    private String a0196b;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0192d",  FieldDescription="")
    private String a0192d;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "a0229",  FieldDescription="")
    private String a0229;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "change_state",  FieldDescription="")
    private String changeState;

    /**
     *
     */
    @ColumnAnnotation(FieldName = "zj_type",  FieldDescription="")
    private String zjType;


    /**
     *
     */
    @IgnoreLogAnnotation
    @ColumnAnnotation(FieldName = "a0192",  FieldDescription="")
    private String a0192;

    /**
     *
     */
    @IgnoreLogAnnotation
    @ColumnAnnotation(FieldName = "a14z101",  FieldDescription="")
    private String a14z101;

    /**
     *
     */
    @IgnoreLogAnnotation
    @ColumnAnnotation(FieldName = "a15z101",  FieldDescription="")
    private String a15z101;

    /**
     *
     */
    @IgnoreLogAnnotation
    @ColumnAnnotation(FieldName = "a1701",  FieldDescription="")
    private String a1701;

    /**
     * 中央职务
     */
    @IgnoreLogAnnotation
    @ColumnAnnotation(FieldName = "A1701_A",  FieldDescription="中央职务")
    private String a1701A;

    /**
     *
     */
    @IgnoreLogAnnotation
    @ColumnAnnotation(FieldName = "rxz_dates",  FieldDescription="")
    private String rxzDates;

    /**
     *
     */
    @IgnoreLogAnnotation
    @ColumnAnnotation(FieldName = "a17z1",  FieldDescription="")
    private String a17z1;

    /**
     *
     * @return a0192
     */
    public String getA0192() {
        return a0192;
    }

    /**
     *
     * @param a0192
     */
    public void setA0192(String a0192) {
        this.a0192 = a0192 == null ? null : a0192.trim();
    }

    /**
     *
     * @return a14z101
     */
    public String getA14z101() {
        return a14z101;
    }

    /**
     *
     * @param a14z101
     */
    public void setA14z101(String a14z101) {
        this.a14z101 = a14z101 == null ? null : a14z101.trim();
    }

    /**
     *
     * @return a15z101
     */
    public String getA15z101() {
        return a15z101;
    }

    /**
     *
     * @param a15z101
     */
    public void setA15z101(String a15z101) {
        this.a15z101 = a15z101 == null ? null : a15z101.trim();
    }

    /**
     *
     * @return a1701
     */
    public String getA1701() {
        return a1701;
    }

    /**
     *
     * @param a1701
     */
    public void setA1701(String a1701) {
        this.a1701 = a1701 == null ? null : a1701.trim();
    }

    /**
     * 中央职务
     * @return A1701_A 中央职务
     */
    public String getA1701A() {
        return a1701A;
    }

    /**
     * 中央职务
     * @param a1701A 中央职务
     */
    public void setA1701A(String a1701A) {
        this.a1701A = a1701A == null ? null : a1701A.trim();
    }

    /**
     *
     * @return rxz_dates
     */
    public String getRxzDates() {
        return rxzDates;
    }

    /**
     *
     * @param rxzDates
     */
    public void setRxzDates(String rxzDates) {
        this.rxzDates = rxzDates == null ? null : rxzDates.trim();
    }

    /**
     *
     * @return a17z1
     */
    public String getA17z1() {
        return a17z1;
    }

    /**
     *
     * @param a17z1
     */
    public void setA17z1(String a17z1) {
        this.a17z1 = a17z1 == null ? null : a17z1.trim();
    }

    /**
     *
     * @return a0100
     */
    public String getA0100() {
        return a0100;
    }

    /**
     *
     * @param a0100
     */
    public void setA0100(String a0100) {
        this.a0100 = a0100 == null ? null : a0100.trim();
    }

    /**
     * 姓名
     * @return A0101 姓名
     */
    public String getA0101() {
        return a0101;
    }

    /**
     * 姓名
     * @param a0101 姓名
     */
    public void setA0101(String a0101) {
        this.a0101 = a0101 == null ? null : a0101.trim();
    }

    /**
     * 姓名拼音缩写
     * @return A0102 姓名拼音缩写
     */
    public String getA0102() {
        return a0102;
    }

    /**
     * 姓名拼音缩写
     * @param a0102 姓名拼音缩写
     */
    public void setA0102(String a0102) {
        this.a0102 = a0102 == null ? null : a0102.trim();
    }

    /**
     * 性别
     * @return A0104 性别
     */
    public String getA0104() {
        return a0104;
    }

    /**
     * 性别
     * @param a0104 性别
     */
    public void setA0104(String a0104) {
        this.a0104 = a0104 == null ? null : a0104.trim();
    }

    /**
     *
     * @return a0117_a
     */
    public String getA0117A() {
        return a0117A;
    }

    /**
     *
     * @param a0117A
     */
    public void setA0117A(String a0117A) {
        this.a0117A = a0117A == null ? null : a0117A.trim();
    }

    /**
     *
     * @return a0117
     */
    public String getA0117() {
        return a0117;
    }

    /**
     *
     * @param a0117
     */
    public void setA0117(String a0117) {
        this.a0117 = a0117 == null ? null : a0117.trim();
    }

    /**
     *
     * @return a0104_a
     */
    public String getA0104A() {
        return a0104A;
    }

    /**
     *
     * @param a0104A
     */
    public void setA0104A(String a0104A) {
        this.a0104A = a0104A == null ? null : a0104A.trim();
    }

    /**
     * 籍贯代码
     * @return A0111B 籍贯代码
     */
    public String getA0111b() {
        return a0111b;
    }

    /**
     * 籍贯代码
     * @param a0111b 籍贯代码
     */
    public void setA0111b(String a0111b) {
        this.a0111b = a0111b == null ? null : a0111b.trim();
    }

    /**
     *
     * @return a0111a
     */
    public String getA0111a() {
        return a0111a;
    }

    /**
     *
     * @param a0111a
     */
    public void setA0111a(String a0111a) {
        this.a0111a = a0111a == null ? null : a0111a.trim();
    }

    /**
     * 出生年月
     * @return A0107 出生年月
     */
    public String getA0107() {
        return a0107;
    }

    /**
     * 出生年月
     * @param a0107 出生年月
     */
    public void setA0107(String a0107) {
        this.a0107 = a0107 == null ? null : a0107.trim();
    }

    /**
     *
     * @return a0114a
     */
    public String getA0114a() {
        return a0114a;
    }

    /**
     *
     * @param a0114a
     */
    public void setA0114a(String a0114a) {
        this.a0114a = a0114a == null ? null : a0114a.trim();
    }

    /**
     *
     * @return a0114b
     */
    public String getA0114b() {
        return a0114b;
    }

    /**
     *
     * @param a0114b
     */
    public void setA0114b(String a0114b) {
        this.a0114b = a0114b == null ? null : a0114b.trim();
    }

    /**
     *
     * @return a0115b
     */
    public String getA0115b() {
        return a0115b;
    }

    /**
     *
     * @param a0115b
     */
    public void setA0115b(String a0115b) {
        this.a0115b = a0115b == null ? null : a0115b.trim();
    }

    /**
     *
     * @return a0115a
     */
    public String getA0115a() {
        return a0115a;
    }

    /**
     *
     * @param a0115a
     */
    public void setA0115a(String a0115a) {
        this.a0115a = a0115a == null ? null : a0115a.trim();
    }

    /**
     *
     * @return a0120
     */
    public String getA0120() {
        return a0120;
    }

    /**
     *
     * @param a0120
     */
    public void setA0120(String a0120) {
        this.a0120 = a0120 == null ? null : a0120.trim();
    }

    /**
     *
     * @return a0128
     */
    public String getA0128() {
        return a0128;
    }

    /**
     *
     * @param a0128
     */
    public void setA0128(String a0128) {
        this.a0128 = a0128 == null ? null : a0128.trim();
    }

    /**
     *
     * @return a0144
     */
    public Date getA0144() {
        return a0144;
    }

    /**
     *
     * @param a0144
     */
    public void setA0144(Date a0144) {
        this.a0144 = a0144;
    }

    /**
     *
     * @return a0121
     */
    public String getA0121() {
        return a0121;
    }

    /**
     *
     * @param a0121
     */
    public void setA0121(String a0121) {
        this.a0121 = a0121 == null ? null : a0121.trim();
    }

    /**
     *
     * @return a0122
     */
    public String getA0122() {
        return a0122;
    }

    /**
     *
     * @param a0122
     */
    public void setA0122(String a0122) {
        this.a0122 = a0122 == null ? null : a0122.trim();
    }

    /**
     *
     * @return a0127
     */
    public String getA0127() {
        return a0127;
    }

    /**
     *
     * @param a0127
     */
    public void setA0127(String a0127) {
        this.a0127 = a0127 == null ? null : a0127.trim();
    }

    /**
     *
     * @return a0160
     */
    public String getA0160() {
        return a0160;
    }

    /**
     *
     * @param a0160
     */
    public void setA0160(String a0160) {
        this.a0160 = a0160 == null ? null : a0160.trim();
    }

    /**
     *
     * @return a0163
     */
    public String getA0163() {
        return a0163;
    }

    /**
     *
     * @param a0163
     */
    public void setA0163(String a0163) {
        this.a0163 = a0163 == null ? null : a0163.trim();
    }

    /**
     *
     * @return a0165
     */
    public String getA0165() {
        return a0165;
    }

    /**
     *
     * @param a0165
     */
    public void setA0165(String a0165) {
        this.a0165 = a0165 == null ? null : a0165.trim();
    }

    /**
     *
     * @return a0180
     */
    public String getA0180() {
        return a0180;
    }

    /**
     *
     * @param a0180
     */
    public void setA0180(String a0180) {
        this.a0180 = a0180 == null ? null : a0180.trim();
    }

    /**
     *
     * @return a0141
     */
    public String getA0141() {
        return a0141;
    }

    /**
     *
     * @param a0141
     */
    public void setA0141(String a0141) {
        this.a0141 = a0141 == null ? null : a0141.trim();
    }

    /**
     *
     * @return a0187a
     */
    public String getA0187a() {
        return a0187a;
    }

    /**
     *
     * @param a0187a
     */
    public void setA0187a(String a0187a) {
        this.a0187a = a0187a == null ? null : a0187a.trim();
    }

    /**
     *
     * @return a0195
     */
    public String getA0195() {
        return a0195;
    }

    /**
     *
     * @param a0195
     */
    public void setA0195(String a0195) {
        this.a0195 = a0195 == null ? null : a0195.trim();
    }

    /**
     *
     * @return a0192a
     */
    public String getA0192a() {
        return a0192a;
    }

    /**
     *
     * @param a0192a
     */
    public void setA0192a(String a0192a) {
        this.a0192a = a0192a == null ? null : a0192a.trim();
    }

    /**
     *
     * @return a0194
     */
    public String getA0194() {
        return a0194;
    }

    /**
     *
     * @param a0194
     */
    public void setA0194(String a0194) {
        this.a0194 = a0194 == null ? null : a0194.trim();
    }

    /**
     *
     * @return a0197
     */
    public String getA0197() {
        return a0197;
    }

    /**
     *
     * @param a0197
     */
    public void setA0197(String a0197) {
        this.a0197 = a0197 == null ? null : a0197.trim();
    }

    /**
     *
     * @return a0196
     */
    public String getA0196() {
        return a0196;
    }

    /**
     *
     * @param a0196
     */
    public void setA0196(String a0196) {
        this.a0196 = a0196 == null ? null : a0196.trim();
    }

    /**
     *
     * @return a2949
     */
    public String getA2949() {
        return a2949;
    }

    /**
     *
     * @param a2949
     */
    public void setA2949(String a2949) {
        this.a2949 = a2949 == null ? null : a2949.trim();
    }

    /**
     *
     * @return a0140
     */
    public String getA0140() {
        return a0140;
    }

    /**
     *
     * @param a0140
     */
    public void setA0140(String a0140) {
        this.a0140 = a0140 == null ? null : a0140.trim();
    }

    /**
     *
     * @return qrzxl
     */
    public String getQrzxl() {
        return qrzxl;
    }

    /**
     *
     * @param qrzxl
     */
    public void setQrzxl(String qrzxl) {
        this.qrzxl = qrzxl == null ? null : qrzxl.trim();
    }

    /**
     *
     * @return qrzxw
     */
    public String getQrzxw() {
        return qrzxw;
    }

    /**
     *
     * @param qrzxw
     */
    public void setQrzxw(String qrzxw) {
        this.qrzxw = qrzxw == null ? null : qrzxw.trim();
    }

    /**
     *
     * @return qrzxlxx
     */
    public String getQrzxlxx() {
        return qrzxlxx;
    }

    /**
     *
     * @param qrzxlxx
     */
    public void setQrzxlxx(String qrzxlxx) {
        this.qrzxlxx = qrzxlxx == null ? null : qrzxlxx.trim();
    }

    /**
     *
     * @return qrzxwxx
     */
    public String getQrzxwxx() {
        return qrzxwxx;
    }

    /**
     *
     * @param qrzxwxx
     */
    public void setQrzxwxx(String qrzxwxx) {
        this.qrzxwxx = qrzxwxx == null ? null : qrzxwxx.trim();
    }

    /**
     *
     * @return zzxl
     */
    public String getZzxl() {
        return zzxl;
    }

    /**
     *
     * @param zzxl
     */
    public void setZzxl(String zzxl) {
        this.zzxl = zzxl == null ? null : zzxl.trim();
    }

    /**
     *
     * @return zzxw
     */
    public String getZzxw() {
        return zzxw;
    }

    /**
     *
     * @param zzxw
     */
    public void setZzxw(String zzxw) {
        this.zzxw = zzxw == null ? null : zzxw.trim();
    }

    /**
     *
     * @return zzxlxx
     */
    public String getZzxlxx() {
        return zzxlxx;
    }

    /**
     *
     * @param zzxlxx
     */
    public void setZzxlxx(String zzxlxx) {
        this.zzxlxx = zzxlxx == null ? null : zzxlxx.trim();
    }

    /**
     *
     * @return zzxwxx
     */
    public String getZzxwxx() {
        return zzxwxx;
    }

    /**
     *
     * @param zzxwxx
     */
    public void setZzxwxx(String zzxwxx) {
        this.zzxwxx = zzxwxx == null ? null : zzxwxx.trim();
    }

    /**
     *
     * @return cbdw
     */
    public String getCbdw() {
        return cbdw;
    }

    /**
     *
     * @param cbdw
     */
    public void setCbdw(String cbdw) {
        this.cbdw = cbdw == null ? null : cbdw.trim();
    }

    /**
     *
     * @return jsnlsj
     */
    public String getJsnlsj() {
        return jsnlsj;
    }

    /**
     *
     * @param jsnlsj
     */
    public void setJsnlsj(String jsnlsj) {
        this.jsnlsj = jsnlsj == null ? null : jsnlsj.trim();
    }

    /**
     *
     * @return nrzw
     */
    public String getNrzw() {
        return nrzw;
    }

    /**
     *
     * @param nrzw
     */
    public void setNrzw(String nrzw) {
        this.nrzw = nrzw == null ? null : nrzw.trim();
    }

    /**
     *
     * @return nmzw
     */
    public String getNmzw() {
        return nmzw;
    }

    /**
     *
     * @param nmzw
     */
    public void setNmzw(String nmzw) {
        this.nmzw = nmzw == null ? null : nmzw.trim();
    }

    /**
     *
     * @return rmly
     */
    public String getRmly() {
        return rmly;
    }

    /**
     *
     * @param rmly
     */
    public void setRmly(String rmly) {
        this.rmly = rmly == null ? null : rmly.trim();
    }

    /**
     *
     * @return rxz_date
     */
    public Date getRxzDate() {
        return rxzDate;
    }

    /**
     *
     * @param rxzDate
     */
    public void setRxzDate(Date rxzDate) {
        this.rxzDate = rxzDate;
    }

    /**
     * 主键
     * @return ID 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     *
     * @return is_deleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     *
     * @param isDeleted
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    /**
     * 操作用户
     * @return MODIFY_USER 操作用户
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * 操作用户
     * @param modifyUser 操作用户
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    /**
     *
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     *
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 时间戳
     * @return TIME_STAMP 时间戳
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * 时间戳
     * @param timeStamp 时间戳
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     *
     * @return a0184
     */
    public String getA0184() {
        return a0184;
    }

    /**
     *
     * @param a0184
     */
    public void setA0184(String a0184) {
        this.a0184 = a0184 == null ? null : a0184.trim();
    }

    /**
     * 节点编码
     * @return NODE_ID 节点编码
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * 节点编码
     * @param nodeId 节点编码
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    /**
     *
     * @return a3921
     */
    public String getA3921() {
        return a3921;
    }

    /**
     *
     * @param a3921
     */
    public void setA3921(String a3921) {
        this.a3921 = a3921 == null ? null : a3921.trim();
    }

    /**
     *
     * @return a3927
     */
    public String getA3927() {
        return a3927;
    }

    /**
     *
     * @param a3927
     */
    public void setA3927(String a3927) {
        this.a3927 = a3927 == null ? null : a3927.trim();
    }

    /**
     *
     * @return a01x01
     */
    public Long getA01x01() {
        return a01x01;
    }

    /**
     *
     * @param a01x01
     */
    public void setA01x01(Long a01x01) {
        this.a01x01 = a01x01;
    }

    /**
     *
     * @return a01x02
     */
    public BigDecimal getA01x02() {
        return a01x02;
    }

    /**
     *
     * @param a01x02
     */
    public void setA01x02(BigDecimal a01x02) {
        this.a01x02 = a01x02;
    }

    /**
     *
     * @return zzxx
     */
    public String getZzxx() {
        return zzxx;
    }

    /**
     *
     * @param zzxx
     */
    public void setZzxx(String zzxx) {
        this.zzxx = zzxx == null ? null : zzxx.trim();
    }

    /**
     *
     * @return qrzxx
     */
    public String getQrzxx() {
        return qrzxx;
    }

    /**
     *
     * @param qrzxx
     */
    public void setQrzxx(String qrzxx) {
        this.qrzxx = qrzxx == null ? null : qrzxx.trim();
    }

    /**
     *
     * @return a0134
     */
    public Date getA0134() {
        return a0134;
    }

    /**
     *
     * @param a0134
     */
    public void setA0134(Date a0134) {
        this.a0134 = a0134;
    }

    /**
     *
     * @return a0140_lrmx
     */
    public String getA0140Lrmx() {
        return a0140Lrmx;
    }

    /**
     *
     * @param a0140Lrmx
     */
    public void setA0140Lrmx(String a0140Lrmx) {
        this.a0140Lrmx = a0140Lrmx == null ? null : a0140Lrmx.trim();
    }

    /**
     *
     * @return photodetail
     */
    public String getPhotodetail() {
        return photodetail;
    }

    /**
     *
     * @param photodetail
     */
    public void setPhotodetail(String photodetail) {
        this.photodetail = photodetail == null ? null : photodetail.trim();
    }

    /**
     *
     * @return a0194a
     */
    public String getA0194a() {
        return a0194a;
    }

    /**
     *
     * @param a0194a
     */
    public void setA0194a(String a0194a) {
        this.a0194a = a0194a == null ? null : a0194a.trim();
    }

    /**
     *
     * @return a0221
     */
    public String getA0221() {
        return a0221;
    }

    /**
     *
     * @param a0221
     */
    public void setA0221(String a0221) {
        this.a0221 = a0221 == null ? null : a0221.trim();
    }

    /**
     *
     * @return a0288
     */
    public Date getA0288() {
        return a0288;
    }

    /**
     *
     * @param a0288
     */
    public void setA0288(Date a0288) {
        this.a0288 = a0288;
    }

    /**
     *
     * @return a0192e
     */
    public String getA0192e() {
        return a0192e;
    }

    /**
     *
     * @param a0192e
     */
    public void setA0192e(String a0192e) {
        this.a0192e = a0192e == null ? null : a0192e.trim();
    }

    /**
     *
     * @return a0192c
     */
    public Date getA0192c() {
        return a0192c;
    }

    /**
     *
     * @param a0192c
     */
    public void setA0192c(Date a0192c) {
        this.a0192c = a0192c;
    }

    /**
     *
     * @return leadertype
     */
    public Integer getLeadertype() {
        return leadertype;
    }

    /**
     *
     * @param leadertype
     */
    public void setLeadertype(Integer leadertype) {
        this.leadertype = leadertype;
    }

    /**
     *
     * @return tbr
     */
    public String getTbr() {
        return tbr;
    }

    /**
     *
     * @param tbr
     */
    public void setTbr(String tbr) {
        this.tbr = tbr == null ? null : tbr.trim();
    }

    /**
     *
     * @return tbsj
     */
    public String getTbsj() {
        return tbsj;
    }

    /**
     *
     * @param tbsj
     */
    public void setTbsj(String tbsj) {
        this.tbsj = tbsj == null ? null : tbsj.trim();
    }

    /**
     *
     * @return qrzxlb
     */
    public String getQrzxlb() {
        return qrzxlb;
    }

    /**
     *
     * @param qrzxlb
     */
    public void setQrzxlb(String qrzxlb) {
        this.qrzxlb = qrzxlb == null ? null : qrzxlb.trim();
    }

    /**
     *
     * @return zzxlb
     */
    public String getZzxlb() {
        return zzxlb;
    }

    /**
     *
     * @param zzxlb
     */
    public void setZzxlb(String zzxlb) {
        this.zzxlb = zzxlb == null ? null : zzxlb.trim();
    }

    /**
     *
     * @return a0221a
     */
    public String getA0221a() {
        return a0221a;
    }

    /**
     *
     * @param a0221a
     */
    public void setA0221a(String a0221a) {
        this.a0221a = a0221a == null ? null : a0221a.trim();
    }

    /**
     *
     * @return is_normal
     */
    public String getIsNormal() {
        return isNormal;
    }

    /**
     *
     * @param isNormal
     */
    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal == null ? null : isNormal.trim();
    }

    /**
     *
     * @return operate_batch
     */
    public String getOperateBatch() {
        return operateBatch;
    }

    /**
     *
     * @param operateBatch
     */
    public void setOperateBatch(String operateBatch) {
        this.operateBatch = operateBatch == null ? null : operateBatch.trim();
    }

    /**
     *
     * @return zgxl
     */
    public String getZgxl() {
        return zgxl;
    }

    /**
     *
     * @param zgxl
     */
    public void setZgxl(String zgxl) {
        this.zgxl = zgxl == null ? null : zgxl.trim();
    }

    /**
     *
     * @return zgxlb
     */
    public String getZgxlb() {
        return zgxlb;
    }

    /**
     *
     * @param zgxlb
     */
    public void setZgxlb(String zgxlb) {
        this.zgxlb = zgxlb == null ? null : zgxlb.trim();
    }

    /**
     *
     * @return zgxlxx
     */
    public String getZgxlxx() {
        return zgxlxx;
    }

    /**
     *
     * @param zgxlxx
     */
    public void setZgxlxx(String zgxlxx) {
        this.zgxlxx = zgxlxx == null ? null : zgxlxx.trim();
    }

    /**
     *
     * @return zgxw
     */
    public String getZgxw() {
        return zgxw;
    }

    /**
     *
     * @param zgxw
     */
    public void setZgxw(String zgxw) {
        this.zgxw = zgxw == null ? null : zgxw.trim();
    }

    /**
     *
     * @return zgxwxx
     */
    public String getZgxwxx() {
        return zgxwxx;
    }

    /**
     *
     * @param zgxwxx
     */
    public void setZgxwxx(String zgxwxx) {
        this.zgxwxx = zgxwxx == null ? null : zgxwxx.trim();
    }

    /**
     *
     * @return gsfaqk
     */
    public String getGsfaqk() {
        return gsfaqk;
    }

    /**
     *
     * @param gsfaqk
     */
    public void setGsfaqk(String gsfaqk) {
        this.gsfaqk = gsfaqk == null ? null : gsfaqk.trim();
    }

    /**
     *
     * @return cfqn
     */
    public String getCfqn() {
        return cfqn;
    }

    /**
     *
     * @param cfqn
     */
    public void setCfqn(String cfqn) {
        this.cfqn = cfqn == null ? null : cfqn.trim();
    }

    /**
     *
     * @return age_2
     */
    public String getAge2() {
        return age2;
    }

    /**
     *
     * @param age2
     */
    public void setAge2(String age2) {
        this.age2 = age2 == null ? null : age2.trim();
    }

    /**
     *
     * @return a0196b
     */
    public String getA0196b() {
        return a0196b;
    }

    /**
     *
     * @param a0196b
     */
    public void setA0196b(String a0196b) {
        this.a0196b = a0196b == null ? null : a0196b.trim();
    }

    /**
     *
     * @return a0192d
     */
    public String getA0192d() {
        return a0192d;
    }

    /**
     *
     * @param a0192d
     */
    public void setA0192d(String a0192d) {
        this.a0192d = a0192d == null ? null : a0192d.trim();
    }

    /**
     *
     * @return a0229
     */
    public String getA0229() {
        return a0229;
    }

    /**
     *
     * @param a0229
     */
    public void setA0229(String a0229) {
        this.a0229 = a0229 == null ? null : a0229.trim();
    }

    /**
     *
     * @return change_state
     */
    public String getChangeState() {
        return changeState;
    }

    /**
     *
     * @param changeState
     */
    public void setChangeState(String changeState) {
        this.changeState = changeState == null ? null : changeState.trim();
    }

    /**
     *
     * @return zj_type
     */
    public String getZjType() {
        return zjType;
    }

    /**
     *
     * @param zjType
     */
    public void setZjType(String zjType) {
        this.zjType = zjType == null ? null : zjType.trim();
    }
}