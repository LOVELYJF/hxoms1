package com.hxoms.modules.passportCard.counterGet.service.impl;/*
 * @description:
 * @author 杨波
 * @date:2020-10-21
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;
import com.hxoms.modules.passportCard.counterGet.mapper.OmsCerGetTaskMapper;
import com.hxoms.modules.passportCard.counterGet.service.OmsCerGetTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OmsCerGetTaskServiceImpl extends ServiceImpl<OmsCerGetTaskMapper, OmsCerGetTask> implements OmsCerGetTaskService {
    @Autowired
    private OmsCerGetTaskMapper omsCerGetTaskMapper;
    @Override
    public void deleteTaskByBusinessId(String businessId) {
        omsCerGetTaskMapper.deleteTaskByBusinessId(businessId);
    }
}
