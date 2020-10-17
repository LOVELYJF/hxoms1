package com.hxoms.modules.publicity.service;

import com.github.pagehelper.PageInfo;
import com.hxoms.common.utils.Result;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.publicity.entity.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public interface OmsPubApplyService {
    /**
     * 根据机构b0111查询当前的人员
     *
     * @author sunqian
     * @date 2020/4/22 16:43
     */
    List<PersonInfoVO> selectPersonListByOrg(List<String> b0100, String keyword, String type);

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
    Result insertPubApply(OmsPubApply omsPubApply);

    /**
     *
     * 查询当前登录经办人管理单位的申请人员列表
     * @author sunqian
     * @date 2020/5/4 17:42
     */
    List<OmsPubApply> selectPubApplyList();

    /**
     * 功能描述: <br>
     * 〈根据id查询申请记录〉
     * @Param: [id]
     * @Return: com.hxoms.modules.publicity.entity.OmsPubApply
     * @Author: 李逍遥
     * @Date: 2020/7/28 10:29
     */
    OmsPubApplyVO selectPubApplyById(String id);

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
     * 〈保存台办变更后信息〉
     * @Param: [omsPubApplyChange]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/3 11:46
     */
    void insertPubApplyChange(OmsPubApplyChange omsPubApplyChange, List<String> ids);

    /**
     * 功能描述: <br>
     * 〈通过id获取变更前后信息〉
     * @Param: [id]
     * @Return: com.hxoms.modules.publicity.entity.OmsPubApplyChange
     * @Author: 李逍遥
     * @Date: 2020/7/6 10:06
     * @return
     */
    List<OmsPubApplyChange> getPubApplyChange(String id);

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

    /**
     * 功能描述: <br>
     * 〈因公备案申请步骤统计〉
     * @Param: [orgId]
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.CountStatusResult>
     * @Author: 李逍遥
     * @Date: 2020/7/27 15:04
     */
    List<CountStatusResult> selectPubCountStatus();

    /**
     * 功能描述: <br>
     * 〈因公下一步操作根据id更改状态〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/28 9:48
     */
    void updateSQZTById(String id,String currentStep);

    /**
     * 功能描述: <br>
     * 〈台办获取相同批文号的人员〉
     * @Param: [pwh]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubApplyVO>
     * @Author: 李逍遥
     * @Date: 2020/7/28 17:30
     */
    List<OmsPubApplyVO> getPubApplyByPwh(String pwh);

    /**
     * 功能描述: <br>
     * 〈因公出国境申请列表导出〉
     * @Param: [omsPubApplyQueryParam, response]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/8/3 9:56
     */
    void exportPubApply(OmsPubApplyQueryParam omsPubApplyQueryParam, HttpServletResponse response);

    /**
     * 功能描述: <br>
     * 〈更改时通过批文号模糊查询添加人员〉
     * @Param: [pwh]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubApplyVO>
     * @Author: 李逍遥
     * @Date: 2020/8/3 16:47
     */
    List<OmsPubApplyVO> getPubApplyList();

    /**
     * 功能描述: <br>
     * 〈上报干部监督处〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/8/7 9:55
     * @param id
     */
    void reportPubGroupPreApproval(List<String> id);

    /**
     * 功能描述: <br>
     * 〈撤销整个干教申请〉
     * @Param: [id, cxyy]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/8/10 10:08
     */
    void repealGJ(String id, String cxyy);

    /**
     * 功能描述: <br>
     * 〈修改通知书文号〉
     * @Param: [yPWH, xPWH]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/8/31 8:54
     */
    void updatePWH(String yPWH, String xPWH);

    /**
     * 功能描述: <br>
     * 〈获取台办批文号树〉
     * @Param: []
     * @Return: com.hxoms.modules.publicity.entity.PWHVO
     * @Author: 李逍遥
     * @Date: 2020/8/31 10:38
     */
    List<PWHTreeVO> getPWHList();


}
