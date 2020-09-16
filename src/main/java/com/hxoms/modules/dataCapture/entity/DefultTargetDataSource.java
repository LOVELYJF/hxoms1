package com.hxoms.modules.dataCapture.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @authore:wjf
 * @data 2020/4/15 10:42
 * @Description:
 ***/
@Component
@PropertySource("classpath:/dataCapture.porperties")
//@ConfigurationProperties(prefix = "defulttargetdatasource")
//public class DefultTargetDataSource extends DataSource {
public class DefultTargetDataSource  {
    @Value("${defulttargetdatasource.datasourceId}")
    String datasourceId;
    @Value("${defulttargetdatasource.url}")
    String url;
    @Value("${defulttargetdatasource.username}")
    String userName;
    @Value("${defulttargetdatasource.password}")
    String passWord;
    @Value("${defulttargetdatasource.code}")
    String code;
    @Value("${defulttargetdatasource.databasetype}")
    String databasetype;

    @Value("${job.cron}")
     String cron;

    @Value("${omsFile.baseDir}")
    private String omsfileBaseDir;


    public String getOmsfileBaseDir() {
        return omsfileBaseDir;
    }

    public void setOmsfileBaseDir(String omsfileBaseDir) {
        this.omsfileBaseDir = omsfileBaseDir;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDatabasetype() {
        return databasetype;
    }

    public void setDatabasetype(String databasetype) {
        this.databasetype = databasetype;
    }

    public String getCron() {

        System.out.println(omsfileBaseDir+"33333333333333333333");
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
