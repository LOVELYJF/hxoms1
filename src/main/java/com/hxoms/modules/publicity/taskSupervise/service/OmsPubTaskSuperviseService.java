package com.hxoms.modules.publicity.taskSupervise.service;

import com.hxoms.common.utils.PageBean;
import com.hxoms.modules.publicity.taskSupervise.entity.DownloadBabParam;
import com.hxoms.modules.publicity.taskSupervise.entity.FileInfo;
import com.hxoms.modules.publicity.taskSupervise.entity.ZtDwPersionQuery;
import com.hxoms.modules.publicity.taskSupervise.entity.ZtDwTreeVO;

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
     * @Desc: 查询团组人员
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
     * @Desc: 催办业务流程
     * @Author: wangyunquan
     * @Param: [id]
     * @Return: void
     * @Date: 2020/6/29
     */
    void urgeBusiness(String id) throws Exception;
}
