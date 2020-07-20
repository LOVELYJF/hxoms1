package com.hxoms.modules.publicity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubGroupPreApproval;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 省外办备案申请
 * @author gaozhenyuan
 * @date 2020/6/2 16:28
 */
@Repository
public interface OmsPubGroupMapper extends BaseMapper<OmsPubGroupPreApproval> {
    //获取团体预备案申请列表
    List<OmsPubGroupPreApproval> getPubGroupList(Map<Object, String> param);
    //添加团体预备案申请信息
    Object insertPubGroup(OmsPubGroupPreApproval pubGroup);
    //添加团体预备案申请信息
    Object updatePubGroup(OmsPubGroupPreApproval pubGroup);
    //删除团体预备案申请信息
    Object deletePubGroup(String id);
}