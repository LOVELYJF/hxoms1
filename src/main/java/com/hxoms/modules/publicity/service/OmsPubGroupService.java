package com.hxoms.modules.publicity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubGroupPreApproval;
import com.hxoms.modules.publicity.entity.OmsPubGroupPreApprovalVO;
import org.springframework.web.multipart.MultipartFile;

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
    PageInfo<OmsPubGroupPreApproval> getPubGroupList(Map<Object, String> param) throws ParseException;
    //添加团体预备案申请信息
    Object insertPubGroup(OmsPubGroupPreApproval pubGroup,List<OmsPubApply> personList);
    //修改团体预备案申请信息
    Object updatePubGroup(OmsPubGroupPreApproval pubGroup,List<OmsPubApply> personList);
    //删除团体预备案申请信息
    Object deletePubGroup(String id);
    //上传团体预备案申请信息
    Object uploadPubGroupExcel(MultipartFile file, String orgName, String orgId);
    //重新校验
    Object checkoutPerson(String idList);
    //添加人员
    Object insertPerson(String a0100);
    //撤销人员
    Object backoutPerson(String id);
    //查看详情
    Object getPersonDetailById(String id);
    //递送任务
    Object sendTask(String id);
    //查看流程详情
    Object getFlowDetail(String id);
    //上传批文
    Object uploadApproval(MultipartFile file, String id);
    //获取备案步骤任务数
    List<Map<String,String>> getNumByStatus(String bazt);
}
