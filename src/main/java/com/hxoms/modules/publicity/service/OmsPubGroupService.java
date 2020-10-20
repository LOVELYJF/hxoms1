package com.hxoms.modules.publicity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.publicity.entity.OmsPubGroupAndApplyList;
import com.hxoms.modules.publicity.entity.OmsPubGroupPreApproval;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * 省外办备案申请
 * @author gaozhenyuan
 * @date 2020/6/2 16:28
 */
public interface OmsPubGroupService extends IService<OmsPubGroupPreApproval>{
    //获取省外办备案申请列表
    PageInfo<OmsPubGroupPreApproval> getPubGroupList(Integer pageNum, Integer pageSize, Map<String, String> param) throws ParseException;
    //添加团体预备案申请信息
    Result insertPubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList) throws Exception;
    //添加手工填写团体预备案申请信息，校验未通过时，删除已经保存的团组及申请
    Result insertWritePubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList)throws Exception;
    //获取备案步骤任务数
    Result updateTimeTask(OmsPubGroupAndApplyList pubGroupAndApplyList, String bgyy);
    //修改团体预备案申请信息
    Result updatePubGroup(OmsPubGroupAndApplyList pubGroupAndApplyList);
    //删除团体预备案申请信息
    Result deletePubGroup(String id);
    //上传团体预备案申请信息
    Result uploadPubGroupJson(MultipartFile file,String orgName,String orgId,String bazt) throws IOException;
    //重新校验
    Result checkoutPerson(OmsPubGroupAndApplyList pubGroupAndApplyList);
    //添加人员
    Result insertPerson(String personId,String pubId,String b0100) throws Exception;
    //撤销人员
    Result backoutPerson(String id,String cxyy);
    //撤销团组
    Result backoutGroup(String id,String cxyy);
    //恢复团组
    Result regainGroup(String id);
    //查看团组详情
    Result getPubGroupDetailById(String id);
    //查看详情
    Result getPersonDetailById(String id);
    //查看人员审批详情
    Result getAuditOpinion(String id);
    //获取撤销记录信息
    Result getBackoutDetailById(String id);
    //递送任务
    Result sendTask(OmsPubGroupAndApplyList pubGroupAndApplyList,String bazt);
    //审核备案下一步
    Result goToUploadApproval(String id);
    //上传批文
    Result uploadApproval(MultipartFile file, String id);
    //更新批文号
    Result updateApproval(String pwh, String id);
    //获取备案步骤任务数
    Result getNumByStatus(String bazt);
    //判断流程是否完结（给三凡用）
    void isGroupBeOver(String id);
}