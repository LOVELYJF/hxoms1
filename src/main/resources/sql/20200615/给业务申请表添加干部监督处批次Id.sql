

alter table oms_pub_apply add leader_batch_id varchar(64);
alter table oms_pri_apply add leader_batch_id varchar(64);
alter table oms_pri_delay_apply add leader_batch_id varchar(64);