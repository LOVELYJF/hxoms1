CREATE  FUNCTION  yesOrnO_take_advice (applyId varchar(64),a0100 varchar(64),numDay int )
  RETURNS VARCHAR(20)
  BEGIN
    DECLARE  yesOrno varchar(20);

    SELECT

           ( case when  DATEDIFF(temp_1.applyTime,NOW()) <= numday then '否' else '是' end ) into yesOrno

    from (
         SELECT
             * from (
                    select CREATE_TIME as applyTime ,a0100 as a01Id, id, CREATE_TIME as createTime  from oms_pub_apply
                    UNION
                    select APPLY_TIME as applyTime,a0100 as a01Id,id , CREATE_TIME as createTime from oms_pri_apply
                    UNION
                    select  APPLY_TIME as applyTime, a0100 as a01Id ,id, CREATE_TIME as createTime  from oms_pri_delay_apply
                    ) temp  where temp.a01Id = a0100 and temp.id !=applyId  order by  temp.createTime desc LIMIT 1,1
         ) temp_1;

    RETURN yesOrno;
  END
