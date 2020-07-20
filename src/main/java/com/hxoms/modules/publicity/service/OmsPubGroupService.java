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
    Object insertPubGroup(OmsPubGroupPreApproval pubGroup,OmsPubGroupPreApprovalVO personList);
    //修改团体预备案申请信息
    Object updatePubGroup(OmsPubGroupPreApproval pubGroup,OmsPubGroupPreApprovalVO personList);
    //删除团体预备案申请信息
    Object deletePubGroup(String id);
    //上传团体预备案申请信息
    Object uploadPubGroupExcel(MultipartFile file, String orgName, String orgId);
}
