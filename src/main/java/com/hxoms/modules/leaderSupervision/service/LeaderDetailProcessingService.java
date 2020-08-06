package com.hxoms.modules.leaderSupervision.service;

import java.util.Map;

/**
 * @authore:wjf
 * @data 2020/7/29 10:28
 * @Description:
 ***/
public interface LeaderDetailProcessingService {

    /**
     *  通知经办人重新递交备案函
     * **/
    void sendMessageToAgent(String applyId,String tableCode);

    /**
     *
     * **/

    Map<String,String> getApplicationType();


}
