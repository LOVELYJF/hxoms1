package com.hxoms.modules.country.entity;

import com.hxoms.common.hxannotation.ColumnAnnotation;
import com.hxoms.common.hxannotation.IdAnnotation;
import com.hxoms.common.hxannotation.TableAnnotation;

@TableAnnotation(TableName = "country", TableDescription="国家表")
public class Country {
    @IdAnnotation
    @ColumnAnnotation(FieldName = "ID",   FieldDescription="主键ID")
    private Long id;

    @ColumnAnnotation(FieldName = "NAME_EN",   FieldDescription="国家英文全名")
    private String nameEn;

    @ColumnAnnotation(FieldName = "NAME_ZH",   FieldDescription="国家中文名")
    private String nameZh;

    @ColumnAnnotation(FieldName = "NAME_INTERNATIONAL_ABBR",   FieldDescription="国际域名缩写")
    private String nameInternationalAbbr;

    @ColumnAnnotation(FieldName = "COUNTRY_PHONE_CODE",   FieldDescription="国家电话代码")
    private Integer countryPhoneCode;

    @ColumnAnnotation(FieldName = "ZONE",   FieldDescription="国家所属洲，例如：亚洲，非洲...")
    private String zone;

    @ColumnAnnotation(FieldName = "ZONE_ALIAS",   FieldDescription="所属分区，如中东，有别于洲，但也形成了比较通俗的划分")
    private String zoneAlias;

    @ColumnAnnotation(FieldName = "LAT",   FieldDescription="国家所处纬度")
    private String lat;

    @ColumnAnnotation(FieldName = "LON",   FieldDescription="国家所处经度")
    private String lon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh == null ? null : nameZh.trim();
    }

    public String getNameInternationalAbbr() {
        return nameInternationalAbbr;
    }

    public void setNameInternationalAbbr(String nameInternationalAbbr) {
        this.nameInternationalAbbr = nameInternationalAbbr == null ? null : nameInternationalAbbr.trim();
    }

    public Integer getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(Integer countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone == null ? null : zone.trim();
    }

    public String getZoneAlias() {
        return zoneAlias;
    }

    public void setZoneAlias(String zoneAlias) {
        this.zoneAlias = zoneAlias == null ? null : zoneAlias.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon == null ? null : lon.trim();
    }
}