DROP TABLE IF EXISTS `oms_attachment`;
CREATE TABLE `oms_attachment`
(
  `id`  varchar(36) NOT NULL,
  `type` VARCHAR(10) default NULL COMMENT '文件类型',
  `size` VARCHAR(20) default NULL COMMENT '文件大小',
  `name` VARCHAR(500) default NULL COMMENT '文件名',
  `url` VARCHAR(500) default NULL COMMENT '存储路径',
  `bussiness_type` VARCHAR(10) default NULL COMMENT '业务分类',
  `bussiness_occure_stpet` VARCHAR(10) default NULL COMMENT '发生在业务的哪一步code',
  `bussiness_occure_stpet_name` VARCHAR(10) default NULL COMMENT '发生在业务的哪一步code的名称',
  `bussinessId`  VARCHAR(36) default NULL COMMENT '业务Id',
  `CREATE_TIME` datetime default NULL ,
  `CREATE_USER` VARCHAR(36) default NULL COMMENT '创建人',
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';


DROP TABLE IF EXISTS `attachment_askForJiwei`;
Create TABLE `attachment_askForJiwei`
(
  `id` varchar(36) NOT NULL,
  `attachmentId` varchar(36) NOT NULL COMMENT '附件Id',
  `leader_batchId`  varchar(36) NOT NULL COMMENT 'Id',
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件和征求纪委意见的中间表';