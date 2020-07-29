package com.hxoms.modules.leaderSupervision.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.modules.leaderSupervision.entity.OmsLeaderBatch;
import com.hxoms.modules.leaderSupervision.mapper.OmsLeaderBatchMapper;
import com.hxoms.modules.leaderSupervision.service.OmsLeaderBatchService;
import org.springframework.stereotype.Service;

@Service("omsLeaderBatchService")
public class OmsLeaderBatchServiceImpl extends ServiceImpl<OmsLeaderBatchMapper, OmsLeaderBatch> implements OmsLeaderBatchService {
}
