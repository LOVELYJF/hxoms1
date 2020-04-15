package com.hxoms.message.read.service.impl;
import com.hxoms.common.CustomMessageException;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.message.msgsend.entity.MMsgSend;
import com.hxoms.message.msgsend.mapper.MMsgSendMapper;
import com.hxoms.message.read.entity.Read;
import com.hxoms.message.read.entity.paramentity.ReadMarkParam;
import com.hxoms.message.read.mapper.ReadMapper;
import com.hxoms.message.read.service.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @desc: 消息阅读业务实现
 * @author: lijing
 * @date: 2019/6/5
 */
@Service
public class ReadServiceImpl implements ReadService {

    @Autowired
    private ReadMapper readMapper;

    @Autowired
    private MMsgSendMapper msgSendMapper;
    /**
     * @desc: 批量阅读消息
     * @author: lijing
     * @date: 2019/6/4
     */
    @Override
    public void readingMark(ReadMarkParam readMarkParam) throws Exception{
        if (readMarkParam.getMsgIds() == null || readMarkParam.getMsgIds().size() == 0){
            throw new CustomMessageException("请选择需要处理的消息");
        }

        //当前登录用户
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if (userInfo == null){
            throw new CustomMessageException("用户信息获取失败");
        }
        //需要操作的数据
        List<Read> readList = new ArrayList<>();

        for (String msgId:readMarkParam.getMsgIds()) {
            //消息是否已读
            int isRead = readMapper.selectIsReadMsg(msgId, userInfo.getId());
            if (isRead < 1){
                Read read = new Read();
                read.setId(UUIDGenerator.getPrimaryKey());
                read.setMsgId(msgId);
                read.setReadUserId(userInfo.getId());
                read.setReadTime(new Date());
                readList.add(read);
            }
            MMsgSend mMsgSend=new MMsgSend();
            mMsgSend.setMsgid(msgId);
            mMsgSend.setReceiveUserId(userInfo.getId());
            msgSendMapper.markReaded(mMsgSend);
        }
        //阅读标记
        if (readList.size() > 0){
            int readingMark = readMapper.readingMark(readList);
            if (readingMark == 0){
                throw new CustomMessageException("操作失败");
            }
        }
    }

    @Override
    public int notReadNum() {
        //当前登录用户
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if (userInfo == null){
            throw new CustomMessageException("用户信息获取失败");
        }
        //参数param
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userInfo.getId());
        //查询
        int notReadMsgNum = readMapper.notReadNum(paramMap);
        int notReadMsgNumDisc = readMapper.notReadNumDisc(paramMap);
        return notReadMsgNum + notReadMsgNumDisc;
    }
}
