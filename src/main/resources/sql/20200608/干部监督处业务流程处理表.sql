  -- 批次表
  DROP TABLE IF EXISTS `oms_leader_batch`;
  CREATE TABLE `oms_leader_batch` (
  `id` varchar(64) NOT NULL COMMENT '批次ID',
  `name` varchar(50) default NULL COMMENT '批次名称',
  `accept_date` varchar(50) default NULL COMMENT '受理时间',
  `create_time` datetime default NULL COMMENT '创建时间',
  `create_user` datetime default NULL COMMENT '创建人',
  `master_status` datetime default NULL COMMENT '主状态',
  `slave_status` datetime default NULL COMMENT '副状态',
  `modify_user` datetime default NULL COMMENT '修改人',
  `modify_time` datetime default NULL COMMENT '修改时间',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='干部监督处管理 批次表 ';





 --批次 和因私,因公,延期 人员申请等业务的中间表

  DROP TABLE IF EXISTS `oms_batch_applybusiness`;
  CREATE TABLE `oms_batch_applybusiness` (
  `id` varchar(64) NOT NULL COMMENT '批次业务中间表ID',
  `batch_id` varchar(50) default NULL COMMENT '批次id',
  `business_id` varchar(50) default NULL COMMENT '业务id',
  `business_type` varchar(50) default NULL COMMENT '业务类型比如(因私，因公，延期，等)',

  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='批次和业务申请的中间表 ';



