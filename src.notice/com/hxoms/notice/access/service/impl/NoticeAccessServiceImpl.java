package com.hxoms.notice.access.service.impl;

import com.hxoms.common.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.notice.access.entity.NoticeAccess;
import com.hxoms.notice.access.mapper.NoticeAccessMapper;
import com.hxoms.notice.access.service.NoticeAccessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 通知公告浏览权限
 * @author gaozhenyuan
 * @date 2019/12/19 15:43
 */
@Service
public class NoticeAccessServiceImpl implements NoticeAccessService {

    @Autowired
    private NoticeAccessMapper noticeAccessMapper;

    @Override
    public void insertAccess(String id, List<String> orgIdList) {
        List<NoticeAccess> accessList = new ArrayList<NoticeAccess>();
        NoticeAccess noticeAccess;
        if (id == null) {
            throw new CustomMessageException("未获取到通知公告Id！");
        }
        if (noticeAccessMapper.deleteByContentId(id) >= 0) {
            for(int i = 0;i < orgIdList.size();i++){
                noticeAccess = new NoticeAccess();
                noticeAccess.setId(UUIDGenerator.getPrimaryKey());
                noticeAccess.setContentId(id);
                noticeAccess.setOrgId(orgIdList.get(i));
                accessList.add(noticeAccess);
            }
            noticeAccessMapper.insert(accessList);
        } else {
            throw new CustomMessageException("授权失败！");
        }
    }

    @Override
    public void deleteNoticeAccessPrimaryKey(String id) {
        if (StringUtils.isBlank(id)) {
            throw new CustomMessageException("参数id为空");
        }
        noticeAccessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<NoticeAccess> selectAccessByContentId(String contentId) {
        List<NoticeAccess> list = new ArrayList<NoticeAccess>();
        if (StringUtils.isBlank(contentId)) {
            throw new CustomMessageException("未获取到内容Id！");
        }else{
            list = noticeAccessMapper.selectAccessByContentId(contentId);
        return list;
        }
    }

}
