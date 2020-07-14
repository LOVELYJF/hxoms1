package com.hxoms.modules.publicity.taskSupervise.service;

import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.publicity.taskSupervise.entity.*;

import java.util.List;

public interface OmsPubTaskSuperviseService {

    /**
     * @Desc: 查询经办人所在单位的团组
     * @Author: wangyunquan
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.ZtDwVO>
     * @Date: 2020/6/19
     */
    List<ZtDwTreeVO> selectZtDwApplyList();

    /**
     * @Desc: 通过年份和组团单位名称查询团组人员
     * @Author: wangyunquan
     * @Param: [pageBean, year, ztDwName]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/7/6
     */
    PageBean selectZtDwPerson(PageBean pageBean,String year, String ztDwName);
    /**
     * @Desc: 通过筛选条件查询团组人员
     * @Author: wangyunquan
     * @Param: [pageBean, ztDwPersionQuery]
     * @Return: com.hxoms.common.utils.PageBean
     * @Date: 2020/7/6
     */
    PageBean selectZtDwPersonByQua(PageBean pageBean,ZtDwPersionQuery ztDwPersionQuery);

    /**
     * @Desc: 批量下载个人备案表
     * @Author: wangyunquan
     * @Param: [downloadBabParam]
     * @Return: com.hxoms.modules.publicity.taskSupervise.entity.FileInfo
     * @Date: 2020/7/7
     */
    FileInfo batchDownloadBab(DownloadBabParam downloadBabParam) throws Exception;
    /**
     * @Desc: 查询催办信息
     * @Author: wangyunquan
     * @Param: [urgeBusiness]
     * @Return: void
     * @Date: 2020/6/29
     */
    void selectUrgeInfo(UrgeBusiness urgeBusiness) throws Exception;
    /**
     * @Desc: 办理催办业务
     * @Author: wangyunquan
     * @Param: [urgeBusiness]
     * @Return: void
     * @Date: 2020/7/14
     */
    void insertUrgeBusiness(UrgeBusiness urgeBusiness) throws Exception;
}
