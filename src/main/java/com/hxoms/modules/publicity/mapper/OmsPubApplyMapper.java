package com.hxoms.modules.publicity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.privateabroad.entity.CountStatusResult;
import com.hxoms.modules.publicity.entity.OmsPubApply;
import com.hxoms.modules.publicity.entity.OmsPubApplyVO;
import com.hxoms.modules.publicity.entity.PersonInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OmsPubApplyMapper extends BaseMapper<OmsPubApply> {

    OmsPubApply selectById(@Param("id") String id);

    List<PersonInfoVO> selectPersonListByOrg(@Param("b0100") String b0100,@Param("keyword") String keyword);

    Map<String, Object> selectBasePersonInfo(@Param("b0100") String b0100, @Param("a0100") String a0100);

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
     * @Param: [status, name, cgsj, hgsj, ztdw]
     * @Return: java.util.List<com.hxoms.modules.publicity.entity.OmsPubApply>
     * @Author: 李逍遥
     * @Date: 2020/6/28 10:15
     */
    List<OmsPubApplyVO> getPubAppListByCondition(@Param("sqzt") List<String> status, @Param("name") String name, @Param("cgsj") Date cgsj, @Param("hgsj") Date hgsj, @Param("ztdw") String ztdw, @Param("pwh") String pwh,@Param("b0100") String b0100);

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
     * @Author:高振元
     * @Param: list(List<OmsPubApply>)
     * @Date: 2020/7/29 9:52
     */
    void insertPubApplyList(List<OmsPubApply> list);
}