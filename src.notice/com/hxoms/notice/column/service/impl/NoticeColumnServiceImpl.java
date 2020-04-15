package com.hxoms.notice.column.service.impl;

import com.hxoms.common.CustomMessageException;
import com.hxoms.common.tree.Tree;
import com.hxoms.common.tree.TreeUtil;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.notice.column.entity.NoticeColumn;
import com.hxoms.notice.column.mapper.NoticeColumnMapper;
import com.hxoms.notice.column.service.NoticeColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 栏目设置服务层实现类
 *
 * @author sunqian
 * @date 2019/9/12 10:55
 */
@Service
public class NoticeColumnServiceImpl implements NoticeColumnService {

    @Autowired
    private NoticeColumnMapper noticeColumnMapper;

    @Override
    public void inertOrUpdateColumn(NoticeColumn noticeColumn, boolean isInsert) {
        if (noticeColumn == null) {
            throw new CustomMessageException("参数为空");
        }
        noticeColumn.setModifyUser(UserInfoUtil.getUserInfo().getId());
        noticeColumn.setModifyTime(new Date());
        if (isInsert) {
            noticeColumn.setId(UUIDGenerator.getPrimaryKey());
            noticeColumn.setSortId(noticeColumnMapper.selectMaxSortId());
            noticeColumnMapper.insert(noticeColumn);
        } else {
            noticeColumnMapper.updateByPrimaryKeySelective(noticeColumn);
        }
    }

    @Override
    public List<Tree> selectColumnTree() {
        return TreeUtil.listToTreeJson(noticeColumnMapper.selectColumnTree());
    }

    @Override
    public List<NoticeColumn> selectColumnListByPId(String pId) {
        return noticeColumnMapper.selectColumnListByPId(pId);
    }

    @Override
    public NoticeColumn selectPrimaryKey(String id) {
        return noticeColumnMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deletePrimaryKey(String id) {
        //判断是否叶子节点
        List<NoticeColumn> noticeColumns = selectColumnListByPId(id);
        if (noticeColumns == null || noticeColumns.isEmpty()) {
            noticeColumnMapper.deleteByPrimaryKey(id);
        } else {
            throw new CustomMessageException("有子节点不能直接删除");
        }
    }

    @Override
    public void saveTableSort(List<NoticeColumn> list) {
        for (int i = 0; i < list.size(); i++) {
            NoticeColumn noticeColumn = list.get(i);
            noticeColumn.setSortId(i + 1);
        }
        noticeColumnMapper.saveTableSort(list);
    }

    @Override
    public List<NoticeColumn> selectColumnTypeList() {
        return noticeColumnMapper.selectColumnTypeList();
    }

    @Override
    public List<Tree> selectColumnTypeTree() {
        return TreeUtil.listToTreeJson(noticeColumnMapper.selectColumnTypeTree());
    }
}
