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
    List<OmsPubGroupPreApproval> getPubGroupList(Map<String, String> param);
    //添加团体预备案申请信息
    Object insertPubGroup(OmsPubGroupPreApproval pubGroup);
    //修改团体预备案申请信息
    Object updatePubGroup(OmsPubGroupPreApproval pubGroup);
    //删除团体预备案申请信息
    Object deletePubGroup(String id);
    //获取团体预备案申请详情
    OmsPubGroupPreApproval getPubGroupDetailById(String id);
    //获取备案步骤树
    List<Map<String, String>> getNumByStatus(String bazt);
    //获取流程节点详情
    List<Map<String, String>>  getFlowDetail(String id);
}