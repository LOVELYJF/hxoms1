package com.hxoms.modules.leaderSupervision.service.impl;

import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.general.select.entity.SqlVo;
import com.hxoms.general.select.mapper.SelectMapper;
import com.hxoms.modules.leaderSupervision.Enum.BussinessApplyStatus;
import com.hxoms.modules.leaderSupervision.service.LeaderCommonService;
import com.hxoms.modules.leaderSupervision.service.VerifyCheckService;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.BussinessTypeAndIdVo;
import com.hxoms.modules.leaderSupervision.vo.LeaderSupervisionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @authore:wjf
 * @data 2020/9/3 16:25
 * @Description:
 ***/
@Service("verifyCheckService")
public class VerifyCheckServiceImpl implements VerifyCheckService {

    @Autowired
    private LeaderCommonServiceImpl leaderCommonService;
    @Autowired
    private SelectMapper selectMapper;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional(rollbackFor = CustomMessageException.class)
    public void verifyCheckApprove(LeaderSupervisionVo leaderSupervisionVo) {
        // 校验参数
        LeaderSupervisionUntil.throwableByParam(leaderSupervisionVo,leaderSupervisionVo.getIspass());

        // 保持审批记录

        if("pass".equals(leaderSupervisionVo.getIspass())){

            //  (1) 保存 审批记录(通过)
            leaderCommonService.saveAbroadApprovalByBussinessId(leaderSupervisionVo.getBussinessTypeAndIdVos(),"通过",
                    Constants.emPrivateGoAbroad.核实批件.getName(), Constants.emPrivateGoAbroad.部领导审批.getIndex(),null);
            // 修改最终结论
            leaderCommonService.updateBussinessApplyRecordOpinion(leaderSupervisionVo.getBussinessTypeAndIdVos(),"1",null);


        }else if("nopass".equals(leaderSupervisionVo.getIspass())){

            leaderCommonService.saveAbroadApprovalByBussinessId(leaderSupervisionVo.getBussinessTypeAndIdVos(),"不通过",
                    Constants.emPrivateGoAbroad.核实批件.getName(), Constants.emPrivateGoAbroad.部领导审批.getIndex(),leaderSupervisionVo.getReason());
            leaderCommonService.updateBussinessApplyRecordOpinion(leaderSupervisionVo.getBussinessTypeAndIdVos(),"2",null);
        }

        //  修改 业务流程状态 (第二步) 修改 为  处领导审批
        updteBussinessApplyStatueByverify(leaderSupervisionVo.getBussinessTypeAndIdVos(),
                Constants.emPrivateGoAbroad.制作备案表.getName(),leaderSupervisionVo.getIspass());

        leaderCommonService.selectBatchIdAndisOrNotUpateBatchStatus(
                leaderSupervisionVo.getBussinessTypeAndIdVos().stream().map(s-> s.getBussinessId()).collect(Collectors.toList()),
                Constants.emPrivateGoAbroad.制作备案表.getIndex());




    }


    public void updteBussinessApplyStatueByverify(List<BussinessTypeAndIdVo> businessTypeAndIdAndOnJobVos, String leaderStatusName,String ispass){

        for(int i=0;i<businessTypeAndIdAndOnJobVos.size();i++){

            String bussinessType =  LeaderSupervisionUntil.selectorBussinessTypeByName(businessTypeAndIdAndOnJobVos.get(i).getBussinessName());
            String updateApplyStatusSql =   getUpdateStatusSql(businessTypeAndIdAndOnJobVos.get(i).getBussinessId(),bussinessType,leaderStatusName,ispass);
            log.info("修改业务 流程的 sql ="+updateApplyStatusSql);

            if(updateApplyStatusSql.length()>0){

                SqlVo instance = SqlVo.getInstance(updateApplyStatusSql);
                selectMapper.update(instance);
            }
        }
    }

    public String getUpdateStatusSql(String busessId,String bussinesType,String leaderStatusName,String ispass){

        String updateSql = "update "+bussinesType;
        String setSql = " set  " ;
        String whereCondition = " where id = '" + busessId+"'";

        for(BussinessApplyStatus applyStatus  : BussinessApplyStatus.values()){

            if(bussinesType.indexOf(applyStatus.getTableName())!=-1){

               if("oms_pub_apply".equals(bussinesType)){

                    String status = applyStatus.getApplySatus();

                    // 同意到  生成 备案表
                    if("pass".equals(ispass)){

                        setSql+= status + "=" + Constants.emPrivateGoAbroad.制作备案表.getIndex();
                        return  updateSql+setSql+whereCondition;
                    }
                    // 不同意 到 已完结 流程走完
                    else if("nopass".equals(ispass)){

                        setSql+= status + "=" + Constants.emPrivateGoAbroad.已办结.getIndex();
                        return  updateSql+setSql+whereCondition;
                    }
                }
            }
        }

        return null;
    }




}
