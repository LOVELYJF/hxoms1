package com.hxoms.modules.leaderSupervision.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxoms.modules.leaderSupervision.entity.OmsBatchApplybusiness;
import com.hxoms.modules.leaderSupervision.mapper.OmsBatchApplybusinessMapper;
import com.hxoms.modules.leaderSupervision.service.OmsBatchApplybusinessService;
import org.springframework.stereotype.Service;

@Service("omsBatchApplybusinessService")
public class OmsBatchApplybusinessServiceImpl extends ServiceImpl<OmsBatchApplybusinessMapper, OmsBatchApplybusiness> implements OmsBatchApplybusinessService {
}
