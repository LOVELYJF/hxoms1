package com.hxoms.modules.publicity.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.modules.publicity.entity.*;

import java.util.Date;
import java.util.List;

public interface OmsPubApplyService {
    /**
     * 根据机构b0111查询当前的人员
     *
     * @author sunqian
     * @date 2020/4/22 16:43
     */
    List<PersonInfoVO> selectPersonListByOrg(String orgId);

    /**
     * 根据人员id查询申请需要的基本信息
     * 
     * @author sunqian
     * @date 2020/4/23 15:21
     */
    OmsPubApplyVO selectPubApplyPersonInfo(String b0100, String a0100);

    /**
     * 新增备案申请
     * 
     * @author sunqian
     * @date 2020/4/26 17:22
     */
    String insertOrUpdatePubApply(OmsPubApply omsPubApply);

    /**
     *
     * 查询当前登录经办人管理单位的申请人员列表
     * @author sunqian
     * @date 2020/5/4 17:42
     */
    List<OmsPubApply> selectPubApplyList();

    OmsPubApply selectPubApplyById(String id);

    /**
     * 功能描述: <br>
     * 〈根据id删除备案人员〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/24 9:06
     */
    void deletePubApplyById(String id);

    /**
     * 功能描述: <br>
     * 〈根据条件查询备案申请列表〉
     * @Param: [omsPubApplyQueryParam]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/6/28 18:04
     */
    PageInfo getPubAppListByCondition(OmsPubApplyQueryParam omsPubApplyQueryParam);

    /**
     * 功能描述: <br>
     * 〈撤销通知书文号相同的备案申请〉
     * @Param: [pwh, cxyy]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/1 9:02
     */
    void repealAllPubApplyByPwh(String pwh, String cxyy);

    /**
     * 功能描述: <br>
     * 〈通过id撤销备案申请〉
     * @Param: [id, cxyy]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/1 10:32
     */
    void repealPubApplyById(String id, String cxyy);

    /**
     * 功能描述: <br>
     * 〈获取台办变更前信息〉
     * @Param: [pwh]
     * @Return: com.hxoms.modules.publicity.entity.OmsPubApplyChange
     * @Author: 李逍遥
     * @Date: 2020/7/3 11:30
     */
    OmsPubApplyChange getPubApplyChangeByPwh(String pwh);

    /**
     * 功能描述: <br>
     * 〈保存台办变更后信息〉
     * @Param: [omsPubApplyChange]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/3 11:46
     */
    void insertPubApplyChange(OmsPubApplyChange omsPubApplyChange);

    /**
     * 功能描述: <br>
     * 〈通过批文号获取变更前后信息〉
     * @Param: [pwh]
     * @Return: com.hxoms.modules.publicity.entity.OmsPubApplyChange
     * @Author: 李逍遥
     * @Date: 2020/7/6 10:06
     */
    OmsPubApplyChange getPubApplyChange(String pwh);

    /**
     * 功能描述: <br>
     * 〈添加干教因公出国备案申请〉
     * @Param: [omsPubGroupPreApproval]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/7/6 15:52
     */
    String insertOrUpdatePubGroupPreApproval(OmsPubGroupPreApprovalVO omsPubGroupPreApproval);

    /**
     * 功能描述: <br>
     * 〈根据条件查看干教列表〉
     * @Param:
     * @Return:
     * @Author: 李逍遥
     * @Date: 2020/7/7 15:50
     */
    PageInfo getPubGroupPreApprovalByCondition(OmsPubApplyQueryParam omsPubApplyQueryParam);

    /**
     * 功能描述: <br>
     * 〈根据ID删除干教信息〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/7 17:35
     */
    void deletePubGroupPreApprovalById(String id);

    /**
     * 功能描述: <br>
     * 〈根据ID查看干教信息〉
     * @Param: [id]
     * @Return: com.github.pagehelper.PageInfo
     * @Author: 李逍遥
     * @Date: 2020/7/7 17:47
     */
    OmsPubGroupPreApprovalVO getPubGroupPreApprovalById(String id);

    /**
     * 功能描述: <br>
     * 〈获取负面信息、家庭主要成员、单位是否接收巡视等信息〉
     * @Param: [b0100, a0100, cgsj]
     * @Return: com.hxoms.modules.publicity.entity.OtherPubApply
     * @Author: 李逍遥
     * @Date: 2020/7/21 9:42
     */
    OtherPubApply getOtherPubApply(String b0100, String a0100, Date cgsj);
}
