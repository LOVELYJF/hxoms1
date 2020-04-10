package com.hxoms.notice.access.controller;

import com.hxoms.common.utils.RequestList;
import com.hxoms.common.utils.Result;
import com.hxoms.notice.access.entity.NoticeAccess;
import com.hxoms.notice.access.service.NoticeAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通知公告内容设置
 *
 * @author gaozhenyuan
 * @date 2019/12/19 14:22
 */
@RestController
@RequestMapping("/noticeAccess")
public class NoticeAccessController {

    @Autowired
    private NoticeAccessService noticeAccessService;

    /**
     * 新增公告浏览权限
     * @author gaozhenyuan
     * @date 2019/12/19 14:23
     */
    @RequestMapping("/insertAccess")
    public Result insertAccess(String id, RequestList requestList) {
        List<String> orgIdList = requestList.getList(String.class);
        noticeAccessService.insertAccess(id,orgIdList);
        return Result.success();
    }

    /**
     * 根据主键删除公告浏览权限
     * @author gaozhenyuan
     * @date 2019/12/19 11:27
     */
    @RequestMapping("/deleteNoticeAccessPrimaryKey")
    public Result deleteNoticeAccessPrimaryKey(String id) {
        noticeAccessService.deleteNoticeAccessPrimaryKey(id);
        return Result.success();
    }

    /**
     * 根据公告id查询浏览权限列表
     * @author gaozhenyuan
     * @date 2019/12/19 16:55
     */
    @RequestMapping("/selectAccessByContentId")
    public Result selectAccessByContentId(String contentId) {
        List<NoticeAccess> list = noticeAccessService.selectAccessByContentId(contentId);
        return Result.success(list);
    }

}
