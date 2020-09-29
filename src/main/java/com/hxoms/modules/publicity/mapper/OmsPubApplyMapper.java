package com.hxoms.modules.publicity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.omsoperator.entity.OmsOperatorJBYWQueryParam;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.publicity.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface OmsPubApplyMapper extends BaseMapper<OmsPubApply> {

    OmsPubApplyVO selectById(@Param("id") String id);

    List<PersonInfoVO> selectPersonListByOrg(@Param("b0100") List<String> b0100, @Param("keyword") String keyword);

    Map<String, Object> selectBasePersonInfo(@Param("b0100") String b0100, @Param("a0100") String a0100);

    Map<String, Object> selectBasePerson( @Param("id") String id);

    /**
     * 最近一次因公出国情况
     *
     * @author sunqian
     * @date 2020/4/25 17:00
     */
    List<OmsPubApply> selectPubAbroadLatestInfo(String a0100);

    /**
     * 查询条件
     *
     * @author sunqian
     * @date 2020/4/28 15:17
     */
    List<OmsCondition> selectCheckCondition(String conditionType);

    int excuteSelectSql(String sql);

    /**
     * 查询申请人员信息
     * 
     * @author sunqian
     * @date 2020/5/6 14:56
     */
    List<OmsPubApply> selectPubApplyList(String batchId);

    Map<String, Object> selectPersonInfoByApplyId(String applyId);

    /**
     * 功能描述: <br>
     * 〈根据id删除备案申请〉
     * @Param: [id]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/6/24 9:09
     */
    void deletePubApplyById(String id);

    /**
     * 功能描述: <br>
     * 〈根据条件查询备案申请列表〉
     * @Param: omsPubApplyQueryParam
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubApply>
     * @Author: 李逍遥
     * @Date: 2020/6/28 10:15
     */
    List<OmsPubApplyVO> getPubAppListByCondition(@Param("omsPubApplyQueryParam") OmsPubApplyQueryParam omsPubApplyQueryParam);

    /**
     * 功能描述: <br>
     * 〈撤销通知书文号相同的备案申请〉
     * @Param: [pwh, cxyy, sqzt]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/7/1 9:25
     */
    void repealAllPubApplyByPwh(@Param("pwh")String pwh, @Param("cxyy")String cxyy,@Param("sqzt")int sqzt);

    List<OmsPubApplyVO> selectPubApplyListByPwh(String pwh);

    void repealPubApplyById(@Param("id") String id,@Param("cxyy") String cxyy,@Param("sqzt") int sqzt);

    void updateByPwh(OmsPubApply omsPubApply);

    List<OmsPubApplyVO> selectByYSPId(@Param("yspId") String yspId);

    void deletePubApplyByYSPId(String id);
    /**
     * 功能描述: <br>
     * 〈根据经办人名字及状态查询未完结的备案申请，〉
     * @Param:
     * @Return:
     * @Author: 李逍遥
     * @Date: 2020/7/10 9:04
     */
    List<OmsPubApplyVO> selectPubAllyByStatusAndName(@Param("createUser") String createUser,@Param("sqzt") int sqzt);


    /**
     * 功能描述: <br>
     * 〈因公备案申请步骤统计〉
     * @Param: [orgId]
     * @Return: java.util.List<com.hxoms.modules.privateabroad.entity.CountStatusResult>
     * @Author: 李逍遥
     * @Date: 2020/7/27 15:06
     */
    List<CountStatusResult> selectPubCountStatus();

    /**
     * 功能描述: <br>
     * 〈更改时通过批文号模糊查询添加人员〉
     * @Param: [pwh]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubApplyVO>
     * @Author: 李逍遥
     * @Date: 2020/8/3 16:52
     */
    List<OmsPubApplyVO> getPubApplyList(@Param("pwh") String pwh,@Param("sqzt") int sqzt);

    /**
     * 批量添加人员
     * @Author:gaozhenyuan
     * @Param: list(List<OmsPubApply>)
     * @Date: 2020/7/29 9:52
     */
    void insertPubApplyList(List<OmsPubApply> list);

    /**
     * 批量修改人员
     * @Author:gaozhenyuan
     * @Param: list(List<OmsPubApply>)
     * @Date: 2020/7/29 9:52
     */
    void updatePubApplyList(List<OmsPubApply> list);

    /**
     * 功能描述: <br>
     * 〈修改通知书文号〉
     * @Param: [yPWH, xPWH]
     * @Return: void
     * @Author: 李逍遥
     * @Date: 2020/8/31 8:58
     */
    void updatePWH(@Param("yPWH") String yPWH, @Param("xPWH") String xPWH);

    /**
     * 功能描述: <br>
     * 〈获取台办批文号树〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.PWHVO>
     * @Author: 李逍遥
     * @Date: 2020/8/31 10:43
     */
    List<PWHTreeVO> getPWHList();

    /**
     * 功能描述: <br>
     * 〈经办人选择人员：查询非省管、中管干部〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.PersonInfoVO>
     * @Author: 李逍遥
     * @Date: 2020/9/8 14:42
     */
    List<PersonInfoVO> selectPersonListForOperator(@Param("a0165") List<String> a0165 ,@Param("b0100") List<String> b0100,@Param("keyword") String keyword);

    /**
     * 功能描述: <br>
     * 〈调整期干部选择人员：查询省管干部〉
     * @Param: []
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.PersonInfoVO>
     * @Author: 李逍遥
     * @Date: 2020/9/8 14:58
     * @param b0100
     * @param keyword
     */
    List<PersonInfoVO> selectPersonListForTZQGB(@Param("b0100") List<String> b0100, @Param("keyword") String keyword);


    /**
     * 功能描述: <br>
     * 〈从团组预审批表获取负责人员姓名〉
     * @Param: [a0100]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/9/8 19:39
     */
    String getStNameForGroup(@Param("a0100") String a0100);
    /**
     * 功能描述: <br>
     * 〈从备案申请表表获取负责人姓名〉
     * @Param: [a0100]
     * @Return: java.lang.String
     * @Author: 李逍遥
     * @Date: 2020/9/8 19:50
     */
    String getStNameForPub(@Param("a0100") String a0100);
    /**
     * 功能描述: <br>
     * 〈查询该干部近3年的出国记录〉
     * @Param: [a0100]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubApplyVO>
     * @Author: 李逍遥
     * @Date: 2020/9/9 16:15
     */
    List<OmsPubApplyVO> getPubApplyListBy3Year(@Param("a0100") String a0100);

    /**
     * 功能描述: <br>
     * 〈经办人经办的因公出国备案列表〉
     * @Param: [omsOperatorJBYWQueryParam]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubApplyVO>
     * @Author: 李逍遥
     * @Date: 2020/9/11 11:28
     */
    List<OmsPubApplyVO> selectPubAllyByParam(@Param("omsOperatorJBYWQueryParam") OmsOperatorJBYWQueryParam omsOperatorJBYWQueryParam);
}