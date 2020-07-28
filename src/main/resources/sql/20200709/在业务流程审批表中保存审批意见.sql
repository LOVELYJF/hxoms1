


-- 因公 流程


--  删除这个字段  因私 流程
alter table oms_pri_apply drop COLUMN FINAL_CONCLUSION;  -- 最终结论
alter table oms_pri_apply drop COLUMN LAST_ASK_TIME;    -- 上次征求 纪委意见时间

alter table oms_pri_apply add  SCZQJWYJSJ DATETIME comment '上次征求 纪委意见时间';


alter table oms_pri_apply add  CLSHSFTG char(1) comment '材料审核是否通过';

alter  table oms_pri_apply add JDCJL    char(1) comment '监督处最终结论';
alter  table oms_pri_apply add JWJL     char(1) comment '纪委结论';

alter  table oms_pri_apply add ZZJL     char(1) comment '最终结论';





-- 延期 流程

alter table oms_pri_delay_apply add  CLSHSFTG char(1) comment '材料审核是否通过';

alter table oms_pri_delay_apply add  JDCJL    char(1) comment '监督处最终结论';

alter table oms_pri_delay_apply add  JWJL char(1) comment '纪委结论';

alter table oms_pri_delay_apply add  ZZJL  char(1) comment '最终结论';

alter  table oms_pri_delay_apply add SFZQJWYJ      VARCHAR(10) comment '是否需要征求纪委意见';
alter  table oms_pri_delay_apply add SCZQJWYJSJ     datetime comment '上次征求纪委意见时间';




