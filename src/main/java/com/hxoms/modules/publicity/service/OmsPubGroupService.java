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
    Map<String,String> insertPubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList) throws Exception;
    //获取备案步骤任务数
    void updateTimeTask(OmsPubGroupAndApplyList pubGroupAndApplyList, String bgyy);
    //修改团体预备案申请信息
    void updatePubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList);
    //删除团体预备案申请信息
    void deletePubGroup(String id);
    //上传团体预备案申请信息
    OmsPubGroupAndApplyList uploadPubGroupJson(MultipartFile file,String orgName,String orgId,String bazt) throws IOException;
    //重新校验
    List<OmsPubApplyVO> checkoutPerson(OmsPubGroupAndApplyList pubGroupAndApplyList);
    //添加人员
    String insertPerson(String personId,String pubId,String b0100) throws Exception;
    //撤销人员
    String backoutPerson(String id,String cxyy);
    //撤销团组
    void backoutGroup(String id,String cxyy);
    //恢复团组
    void regainGroup(String id);
    //查看团组详情
    OmsPubGroupAndApplyList getPubGroupDetailById(String id);
    //查看详情
    OmsPubApply getPersonDetailById(String id);
    //查看人员审批详情
    List<OmsPubApplyVO> getAuditOpinion(String id);
    //获取撤销记录信息
    Map<String,Object> getBackoutDetailById(String id);
    //递送任务
    String sendTask(OmsPubGroupAndApplyList pubGroupAndApplyList,String bazt);
    //审核备案下一步
    String goToUploadApproval(String id);
    //上传批文
    String uploadApproval(MultipartFile file, String id);
    //更新批文号
    String updateApproval(String pwh, String id);
    //获取备案步骤任务数
    List<Map<String,String>> getNumByStatus(String bazt);
    //判断流程是否完结（给三凡用）
    void isGroupBeOver(String id);
}