package com.hxoms.modules.condition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxoms.common.exception.CustomMessageException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.UUIDGenerator;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.common.utils.UserInfoUtil;
import com.hxoms.message.message.entity.Message;
import com.hxoms.message.message.entity.paramentity.SendMessageParam;
import com.hxoms.message.message.service.MessageService;
import com.hxoms.message.msguser.entity.MsgUser;
import com.hxoms.modules.condition.entity.ConditionReplaceVO;
import com.hxoms.modules.condition.entity.OmsCondition;
import com.hxoms.modules.condition.entity.OmsSetting;
import com.hxoms.modules.condition.mapper.OmsConditionMapper;
import com.hxoms.modules.condition.service.OmsConditionService;
import com.hxoms.modules.condition.service.OmsSettingService;
import com.hxoms.modules.file.entity.OmsReplaceKeywords;
import com.hxoms.modules.file.mapper.OmsReplaceKeywordsMapper;
import com.hxoms.modules.privateabroad.entity.OmsPriApply;
import com.hxoms.modules.privateabroad.entity.OmsPriDelayApply;
import com.hxoms.modules.privateabroad.mapper.OmsPriApplyMapper;
import com.hxoms.modules.privateabroad.mapper.OmsPriDelayApplyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @desc: 约束条件管理
 * @author: lijing
 * @date: 2020-05-28
 */
@Service
public class OmsConditionServiceImpl implements OmsConditionService {

	@Autowired
	private OmsConditionMapper omsConditionMapper;
	@Autowired
	private OmsPriApplyMapper omsPriApplyMapper;
	@Autowired
	private OmsReplaceKeywordsMapper omsReplaceKeywordsMapper;
	@Autowired
	private MessageService messageService;
	@Autowired
	private OmsPriDelayApplyMapper omsPriDelayApplyMapper;
	@Autowired
	private OmsSettingService omsSettingService;

	@Override
	public List<Map<String, String>> checkCondition(String applyId, String type) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(type)) {
			throw new CustomMessageException("参数错误");
		}
		ConditionReplaceVO conditionReplaceVO = getConditionReplaceInfo(applyId, type);
		return checkApply(conditionReplaceVO, Constants.OMS_CONDITION_CHECKTYPE[1], type);
	}

	@Override
	public List<Map<String, String>> checkConditionByA0100(String procpersonId, String type) {
		if (StringUtils.isBlank(procpersonId) || StringUtils.isBlank(type)) {
			throw new CustomMessageException("参数错误");
		}
		// 登录用户信息
		UserInfo userInfo = UserInfoUtil.getUserInfo();
		ConditionReplaceVO conditionReplaceVO = new ConditionReplaceVO();
		conditionReplaceVO.setProcpersonId(procpersonId);
		conditionReplaceVO.setHandleId(userInfo.getId());
		return checkApply(conditionReplaceVO, Constants.OMS_CONDITION_CHECKTYPE[0], type);
	}

	@Override
	public String selectNegativeInfo(String a0100, Date abroadTime) {
		if (StringUtils.isBlank(a0100) || abroadTime == null) {
			throw new CustomMessageException("参数错误");
		}
		// 获取负面信息
		StringBuilder negativeInfo = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		params.put("a0100", a0100);
		params.put("abroadDate", abroadTime);
		List<String> negativeInfos = omsConditionMapper.selectNegativeInfo(params);
		for (String item : negativeInfos) {
			negativeInfo.append(item);
		}
		return negativeInfo.toString();
	}

	@Override
	public void remindCondition(String applyId, String type) {
		if (StringUtils.isBlank(type) || StringUtils.isBlank(applyId)) {
			throw new CustomMessageException("参数错误");
		}
		ConditionReplaceVO conditionReplaceVO = getConditionReplaceInfo(applyId, type);
		remindCheckApply(conditionReplaceVO, type);
	}

	/**
	 * 查询约束信息替换类
	 * 
	 * @param applyId
	 * @param type
	 * @return
	 */
	private ConditionReplaceVO getConditionReplaceInfo(String applyId, String type) {
		// 登录用户信息
		UserInfo userInfo = UserInfoUtil.getUserInfo();
		ConditionReplaceVO conditionReplaceVO = new ConditionReplaceVO();
		String procpersonId = "";
		String a0100 = "";
		if (Constants.oms_business[1].equals(type)) {
			// 因私出国
			OmsPriApply omsPriApply = omsPriApplyMapper.selectById(applyId);
			if (omsPriApply == null) {
				throw new CustomMessageException("申请不存在");
			}
			procpersonId = omsPriApply.getProcpersonId();
			a0100 = omsPriApply.getA0100();
		} else if (Constants.oms_business[2].equals(type)) {
			// 延期回国
			OmsPriDelayApply omsPriDelayApply = omsPriDelayApplyMapper.selectById(applyId);
			if (omsPriDelayApply == null) {
				throw new CustomMessageException("申请不存在");
			}
			procpersonId = omsPriDelayApply.getProcpersonId();
			a0100 = omsPriDelayApply.getA0100();
		} else if (Constants.oms_business[0].equals(type)) {
			// 因公 TODO
		}
		conditionReplaceVO.setProcpersonId(procpersonId);
		conditionReplaceVO.setApplyId(applyId);
		conditionReplaceVO.setHandleId(userInfo.getId());
		conditionReplaceVO.setA0100(a0100);
		return conditionReplaceVO;
	}

	/**
	 * 申请信息校验
	 * 
	 * @param conditionReplaceVO 申请信息
	 * @param checkType          保存前， 保存后(全部校验时传空)
	 * @param type               因公 因私 延期回国
	 * @return
	 */
	private List<Map<String, String>> checkApply(ConditionReplaceVO conditionReplaceVO, String checkType, String type) {
		// 结果
		List<Map<String, String>> result = new ArrayList<>();
		// 约束条件
		Map<String, Object> paramsMap = new HashMap<>();
		if (!StringUtils.isBlank(checkType)) {
			paramsMap.put("checkType", checkType);
		}
		paramsMap.put("conditionType", type);
		paramsMap.put("careType", Constants.OMS_CONDITION_CARETYPE[0]); // 约束
		List<OmsCondition> omsConditions = omsConditionMapper.selectConditionList(paramsMap);
		if (omsConditions != null && omsConditions.size() > 0) {
			// 查询关键词
			QueryWrapper<OmsReplaceKeywords> keywords = new QueryWrapper<>();
			keywords.eq("USE_TYPE", Constants.OMS_KEYWORDS_USERTYPE[1]).eq("TYPE", type);
			List<OmsReplaceKeywords> omsReplaceKeywords = omsReplaceKeywordsMapper.selectList(keywords);
			// 检验条件
			for (OmsCondition omsCondition : omsConditions) {
				Map<String, String> map = new HashMap<>();
				// 替换关键词，执行sql
				String fit = handleSql(conditionReplaceVO, omsCondition, omsReplaceKeywords);
				// 条件标题
				map.put("desc", omsCondition.getDescription());
				map.put("title", omsCondition.getName());
				map.put("isFit", fit);
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 提醒约束条件校验 申请信息
	 * 
	 * @param conditionReplaceVO
	 * @param type               因公 因私 延期回国
	 */
	private void remindCheckApply(ConditionReplaceVO conditionReplaceVO, String type) {
		// 提醒条件
		UserInfo userInfo = UserInfoUtil.getUserInfo();
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("careType", Constants.OMS_CONDITION_CARETYPE[1]); // 提醒
		paramsMap.put("conditionType", type);
		List<OmsCondition> omsConditions = omsConditionMapper.selectConditionList(paramsMap);
		if (omsConditions != null && omsConditions.size() > 0) {
			// 查询关键词
			QueryWrapper<OmsReplaceKeywords> keywords = new QueryWrapper<>();
			keywords.eq("TYPE", type).eq("USE_TYPE", Constants.OMS_KEYWORDS_USERTYPE[1]);
			List<OmsReplaceKeywords> omsReplaceKeywords = omsReplaceKeywordsMapper.selectList(keywords);
			// 检验条件
			for (OmsCondition omsCondition : omsConditions) {
				// 替换关键词，执行sql
				String fit = handleSql(conditionReplaceVO, omsCondition, omsReplaceKeywords);
				// 发送消息
				if ("1".equals(fit)) {
					// 消息
					Message message = new Message();
					message.setId(UUIDGenerator.getPrimaryKey());
					message.setSendUserId("00000000-0000-0000-0000-000000000000");
					message.setSendUsername("系统管理员");
					message.setSendTime(new Date());
					message.setTypeId("AA630152-D57A-40A6-8DCD-EDE7D80871E1"); // 出国境
					message.setFeather("0");
					message.setHandleIdentify(Constants.PERSONAL); // 个人
					message.setMsgStatus(Constants.NOT_HANDLE_MESSAGE);
					message.setCreateTime(new Date());
					message.setDataSource("出国境管理系统");
					message.setIsTop(Constants.NOT_TOP);
					message.setContent(omsCondition.getRemindContent());
					// 接收人
					Map<String, List<MsgUser>> msgUserMap = new HashMap<>();
					List<MsgUser> msgUsers = new ArrayList<>();
					MsgUser msgUser = new MsgUser();
					msgUser.setId(UUIDGenerator.getPrimaryKey());
					msgUser.setMsgId(message.getId());
					msgUser.setCreateTime(new Date());
					if (Constants.OMS_CONDITION_REMIND_PERSONTYPE[0].equals(omsCondition.getRemindPersonType())) {
						// 经办人
						msgUser.setReceiveUserId(userInfo.getId());
						msgUser.setReceiveUsername(userInfo.getUserName());
						msgUser.setHandleIdentify(Constants.PERSONAL);
						msgUsers.add(msgUser);
						msgUserMap.put(Constants.PERSONAL, msgUsers);
					} else if (Constants.OMS_CONDITION_REMIND_PERSONTYPE[1]
							.equals(omsCondition.getRemindPersonType())) {
						// 组团单位 TODO
					} else if (Constants.OMS_CONDITION_REMIND_PERSONTYPE[2]
							.equals(omsCondition.getRemindPersonType())) {
						// 审批单位 TODO
					} else if (Constants.OMS_CONDITION_REMIND_PERSONTYPE[3]
							.equals(omsCondition.getRemindPersonType())) {
						// 干部监督处 TODO
					}
					SendMessageParam sendMessageParam = new SendMessageParam();
					sendMessageParam.setMessage(message);
					sendMessageParam.setMsgUserMap(msgUserMap);
					try {
						if (msgUsers != null && !msgUsers.isEmpty()) {
							messageService.sendMessage(sendMessageParam);
						}

					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new CustomMessageException("服务器异常");
					} catch (InstantiationException e) {
						e.printStackTrace();
						throw new CustomMessageException("服务器异常");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						throw new CustomMessageException("服务器异常");
					}
				}
			}
		}
	}

	/**
	 * 约束条件sql执行
	 * 
	 * @param conditionReplaceVO
	 * @param omsCondition
	 * @param omsReplaceKeywords
	 * @return
	 */
	private String handleSql(ConditionReplaceVO conditionReplaceVO, OmsCondition omsCondition,
			List<OmsReplaceKeywords> omsReplaceKeywords) {
		String sql = omsCondition.getSqlContent();
		int count = 1;
		if (!StringUtils.isBlank(sql)) {
			// 替换关键词
			Class clazz = conditionReplaceVO.getClass();
			for (OmsReplaceKeywords item : omsReplaceKeywords) {
				try {
					String value = (String) clazz.getDeclaredMethod(item.getReplaceField()).invoke(conditionReplaceVO);
					sql = sql.replace(item.getKeyword(), value == null ? "" : value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new CustomMessageException("数据异常");
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					throw new CustomMessageException("数据异常");
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					throw new CustomMessageException("数据异常");
				}
			}
			Integer number = omsConditionMapper.excuteSelectSql(sql);
			if (number == null) {
				count = 0;
			} else {
				count = number.intValue();
			}

		}
		// 替换描述中
		if (Constants.OMS_CONDITION_SETTINGTYPE[1].equals(omsCondition.getSettingType())) {
			// 获取setting
			List<OmsSetting> settings = omsSettingService.getSettingCache();
			for (OmsSetting omsSetting : settings) {
				if (omsSetting.getSettingCode().equals(omsCondition.getSettingCode())) {
					omsCondition.setName(omsCondition.getName().replace(omsCondition.getSettingCode(),
							omsSetting.getSettingValue()));
					break;
				}
			}
		}
		if (count > 0) {
			// 不符合条件
			return "0";
		} else {
			// 符合条件
			return "1";
		}
	}
}
