package com.hxoms.modules.publicity.entity;

import java.util.List;

/**
 * 功能描述: <br>
 * 〈因公出国团体预审批申请表附带表〉
 * @Param:
 * @Return:
 * @Author: 李逍遥
 * @Date: 2020/7/6 15:46
 */
public class OmsPubGroupPreApprovalVO extends OmsPubGroupPreApproval {
    //添加干教因公出国人员集合
   private List<PersonInfoVO> personInfoVOS;

    public List<PersonInfoVO> getPersonInfoVOS() {
        return personInfoVOS;
    }

    public void setPersonInfoVOS(List<PersonInfoVO> personInfoVOS) {
        this.personInfoVOS = personInfoVOS;
    }
}