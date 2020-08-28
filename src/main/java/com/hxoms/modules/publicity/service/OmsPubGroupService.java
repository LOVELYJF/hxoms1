package com.hxoms.modules.publicity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubApplyVO;
import com.hxoms.modules.publicity.entity.OmsPubGroupAndApplyList;
import com.hxoms.modules.publicity.entity.OmsPubGroupPreApproval;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 省外办备案申请
 * @author gaozhenyuan
 * @date 2020/6/2 16:28
 */
public interface OmsPubGroupService extends IService<OmsPubGroupPreApproval>{
    //获取省外办备案申请列表
    PageInfo<OmsPubGroupPreApproval> getPubGroupList(Integer pageNum, Integer pageSize,Map<String, String> param) throws ParseException;
    //添加团体预备案申请信息
    String insertPubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList);
    //修改团体预备案申请信息
    void updatePubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList,String bgyy);
    //删除团体预备案申请信息
    void deletePubGroup(String id);
    //上传团体预备案申请信息
    OmsPubGroupAndApplyList uploadPubGroupJson(MultipartFile file) throws IOException;
    //重新校验
    String checkoutPerson(String idList);
    //添加人员
    void insertPerson(String a0100,String id);
    //撤销团组
    void backoutPerson(String id,String cxyy);
    //恢复团组
    void regainPerson(String id);
    //查看团组详情
    OmsPubGroupAndApplyList getPubGroupDetailById(String id);
    //查看详情
    OmsPubApply getPersonDetailById(String id);
    //获取撤销记录信息
    Map<String,Object> getBackoutDetailById(String id);
    //获取审核意见
    List<OmsPubApplyVO> getAuditOpinion(String id);
    //递送任务
    void sendTask(String id);
    //查看流程详情
    Object getFlowDetail(String id);
    //上传批文
    String uploadApproval(MultipartFile file, String id);
    //获取备案步骤任务数
    List<Map<String,String>> getNumByStatus(String bazt);
}
