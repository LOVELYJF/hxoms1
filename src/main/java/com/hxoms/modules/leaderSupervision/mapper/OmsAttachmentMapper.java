package com.hxoms.modules.leaderSupervision.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.leaderSupervision.entity.OmsAttachment;
import com.hxoms.modules.leaderSupervision.entity.OmsJiweiOpinion;

public interface OmsAttachmentMapper extends BaseMapper<OmsAttachment> {
    OmsAttachment getAttachmentByBussinessId(String bussinessId);
}