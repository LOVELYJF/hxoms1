package com.hxoms.modules.passportCard.counterGet.service;/*
 * @description:
 * @author 杨波
 * @date:2020-10-21
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxoms.modules.passportCard.counterGet.entity.OmsCerGetTask;

public interface OmsCerGetTaskService extends IService<OmsCerGetTask> {
    /**
     * @description:根据业务主键删除取证任务
     * @author:杨波
     * @date:2020-10-21
     *  * @param ids
     * @return:
     **/
    void deleteTaskByBusinessId(String businessId);
}
