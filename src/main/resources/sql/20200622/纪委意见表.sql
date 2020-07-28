

-- 纪委意见记录表

DROP TABLE IF EXISTS `oms_jiwei_opinion`;
CREATE TABLE `oms_jiwei_opinion` (
  `id` varchar(36) NOT NULL COMMENT '纪委意见记录表主键',
  `feedback_type` varchar(50) default NULL COMMENT '反馈方式 1(口头反馈),2(书面反馈)',
  `opinion` varchar(50) default NULL COMMENT '意见',
  `feedback_user` varchar(50) default NULL COMMENT '反馈人',
  `telephone_number` varchar(50) default NULL COMMENT '电话号码',
  `feedback_verdict` varchar(50) default NULL COMMENT '反馈结论1(同意),2(不同意),3(不回复)',
  `feedback_date` datetime default NULL COMMENT '反馈时间',
  `remark` varchar(50) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='纪委意见记录表';



# alter table oms_pub_apply add jiwei_opinion_id varchar(36);
# alter table oms_pri_apply add jiwei_opinion_id varchar(36);
# alter table oms_pri_delay_apply add jiwei_opinion_id varchar(36);
