# 因私
INSERT INTO `hxoms`.`oms_file` (`ID`, `FILE_ID`, `B0100`, `FILE_NAME`, `FILE_SHORTNAME`, `FRONT_CONTENT`, `BANK_CONTENT`, `FILE_TYPE`, `TABLE_CODE`, `IS_EDIT`, `SEAL_DESC`, `RUN_SQL`, `PRINT_NUM`, `ISFILE_LIST`, `CREATE_TIME`, `CREATE_USER`, `MODIFY_TIME`, `MODIFY_USER`, `SORT_ID`) VALUES ('313', NULL, NULL, '《因私呈批单》', '呈批单', '<table width=\"771\">\r\n            <tbody>\r\n                <tr style=\"height:67px\" class=\"firstRow\">\r\n                    <td colspan=\"6\" width=\"771\" style=\"font-size:40px;text-align:center;\">中 共 海 南 省 委 组 织 部</td>\r\n                </tr>\r\n                <tr style=\"height:59px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">承 办 单 位：</td>\r\n                    <td colspan=\"3\" style=\"font-size:20px;border-right:1px solid #666;\">干  部  监  督  处</td>\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">拟 稿 人：</td>\r\n                    <td colspan=\"1\" style=\"font-size:20px;\">$用户$</td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td colspan=\"3\" style=\"font-size:20px;border-right:1px solid #666;\">&nbsp;&nbsp;处领导签署：</td>\r\n                    <td colspan=\"3\" style=\"font-size:20px;\">&nbsp;&nbsp;部领导批示：</td>\r\n                </tr>\r\n                <tr style=\"height:100px;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"3\" style=\"font-size:20px;border-right:1px solid #666;\"><br/></td>\r\n                    <td colspan=\"3\" style=\"font-size:20px;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:59px;\">\r\n                    <td ></td>\r\n                    <td ></td>\r\n                    <td colspan=\"2\"><div style =\"width:100%;height:30px;border:1px solid #666;text-align:center;\"></div></td>\r\n                    <td ></td>\r\n                    <td ></td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td colspan=\"6\" style=\"font-size:26px;text-align:center;\">关于$姓名$同志因私出国（境）的审查意见</td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td colspan=\"6\" style=\"font-size:20px;\">&nbsp;&nbsp;省公安厅：</td>\r\n                </tr>\r\n                <tr style=\"height:120px\">\r\n                    <td colspan=\"6\" style=\"font-size:20px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关于$姓名$同志赴$国家（地区）$$出国事由$事宜，已经省政府办公厅党组主要领导批准，境外停留$停留时间$天，我部审查同意，请按相关规定办理手续。</td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td colspan=\"2\" style=\"font-size:20px;\">中共海南省委组织部</td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td colspan=\"2\" style=\"font-size:20px;\">$当前时间$</td>\r\n                </tr>\r\n                <tr style=\"height:59px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"4\" style=\"font-size:20px;text-align:center;\">中共海南省委组织部办公室</td>\r\n                    <td colspan=\"2\" style=\"font-size:20px;text-align:center;\">$当前时间$印发</td>\r\n                </tr>\r\n                <tr style=\"height:22px;\">\r\n                </tr>\r\n            </tbody>\r\n        </table>\r\n', NULL, '1', 'oms_pri_apply_cadres', '2', '', 'select CONCAT(IFNULL(orp.SURNAME,\'\'),IFNULL(orp.NAME,\'\')) as name,(SELECT item_name FROM sys_dict_item WHERE dict_code=\'GB2261\' and  item_code = orp.SEX) as sex,opa.POLITICAL_OUTLOOK as politicalAffi,orp.BIRTH_DATE_GB as birthDate,\r\norp.HEALTH as health,orp.IDNUMBER_GB as idnumber,orp.REGISTE_RESIDENCE as registeResidence,\r\n(SELECT item_name FROM sys_dict_item WHERE dict_code=\'SMDJ\' and  item_code = opa.CLASSIFICATION_LEVEL) as secretLevel,opa.ABROAD_TIME as abroadTime,\r\nopa.RETURN_TIME as returnTime,(select GROUP_CONCAT(NAME_ZH) from country where id in (opa.GO_COUNTRY)) as goCountry,opa.OUTSIDE_TIME as outsideTime,\r\n(SELECT item_name FROM sys_dict_item WHERE dict_code=\'YSCGLY\' and  item_code = opa.ABROAD_REASONS) as abroadReasons,NOW() as nowTime,\r\nopa.POSTRANK as postrank, opa.FUNDS_SOURCE as fundsSource,opa.ABROAD_PHONE as contactPhone, CONCAT(IFNULL(orp.SURNAME,\'\'),IFNULL(orp.NAME,\'\')) as contactPerson,\'1\' as approvalNo,b.b0101 as b0101,\r\nopa.REVERT_LICENCE_TIME as revertLicenceTime,CONCAT(IFNULL(orp.SURNAME,\'\'),IFNULL(orp.NAME,\'\')) as nowUsername,opd.ESTIMATE_RETURNTIME as estimateReturntime\r\nfrom oms_pri_delay_apply opd LEFT JOIN oms_pri_apply opa on opd.APPLY_ID=opa.ID left join oms_reg_procpersoninfo orp on opa.PROCPERSON_ID = orp.ID left JOIN b01 b on opa.b0100=b.b0111\r\nwhere opd.ID = \'@applyId\'', '1', '1', '2020-07-24 14:57:30', NULL, NULL, NULL, '3');
INSERT INTO `hxoms`.`oms_file` (`ID`, `FILE_ID`, `B0100`, `FILE_NAME`, `FILE_SHORTNAME`, `FRONT_CONTENT`, `BANK_CONTENT`, `FILE_TYPE`, `TABLE_CODE`, `IS_EDIT`, `SEAL_DESC`, `RUN_SQL`, `PRINT_NUM`, `ISFILE_LIST`, `CREATE_TIME`, `CREATE_USER`, `MODIFY_TIME`, `MODIFY_USER`, `SORT_ID`) VALUES ('314', NULL, NULL, '《因私请示表》', '请示表', '<div style=\"width:771px;font-size:40px;text-align:center;\">中共海南省委组织部来文处理单</div>\r\n        <table width=\"771\" border>\r\n            <tbody>\r\n                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">来文单位</td>\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">来文编号</td>\r\n                    <td colspan=\"3\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td  style=\"font-size:20px;text-align:center;\">收文时间</td>\r\n                    <td  style=\"width:150px;font-size:20px;text-align:center;\"><br/></td>\r\n                    <td  style=\"font-size:20px;text-align:center;\">办文编号</td>\r\n                    <td  style=\"width:150px;font-size:20px;text-align:center;\"><br/></td>\r\n                    <td  style=\"font-size:20px;text-align:center;\">密级</td>\r\n                    <td  style=\"width:80px;font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">文件名称</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\">关于建议不予办理$姓名$同志出国（境）备案手续的请示</td>\r\n                </tr>\r\n                <tr style=\"height:150px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"width:200px;font-size:20px;text-align:center;\">干部监督处拟办意见</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;近日，我处收到关于办理$姓名$同志赴$国家（地区）$备案手续的请示，$姓名$同志于$出境时间$至$入境时间$赴$国家（地区）$开展$出访任务$，在外停留$停留时间$天。经征求省纪委意见，省纪委对$姓名$出国（境）持异议，不予回复意见。建议不予办理$姓名$同志出国（境）备案手续。\r\n\r\n    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;妥否，呈$分管部长$副部长审示。</td>\r\n                </tr>\r\n                <tr style=\"height:80px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">部领导批示：</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:80px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">省领导批示：</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">承办结果</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n            </tbody>\r\n        </table>\r\n', NULL, '1', 'oms_pri_apply_cadres ', '2', '', 'select CONCAT(IFNULL(orp.SURNAME,\'\'),IFNULL(orp.NAME,\'\')) as name,(SELECT item_name FROM sys_dict_item WHERE dict_code=\'GB2261\' and  item_code = orp.SEX) as sex,opa.POLITICAL_OUTLOOK as politicalAffi,orp.BIRTH_DATE_GB as birthDate,\r\norp.HEALTH as health,orp.IDNUMBER_GB as idnumber,orp.REGISTE_RESIDENCE as registeResidence,\r\n(SELECT item_name FROM sys_dict_item WHERE dict_code=\'SMDJ\' and  item_code = opa.CLASSIFICATION_LEVEL) as secretLevel,opa.ABROAD_TIME as abroadTime,\r\nopa.RETURN_TIME as returnTime,(select GROUP_CONCAT(NAME_ZH) from country where id in (opa.GO_COUNTRY)) as goCountry,opa.OUTSIDE_TIME as outsideTime,\r\n(SELECT item_name FROM sys_dict_item WHERE dict_code=\'YSCGLY\' and  item_code = opa.ABROAD_REASONS) as abroadReasons,NOW() as nowTime,\r\nopa.POSTRANK as postrank, opa.FUNDS_SOURCE as fundsSource,opa.ABROAD_PHONE as contactPhone, CONCAT(IFNULL(orp.SURNAME,\'\'),IFNULL(orp.NAME,\'\')) as contactPerson,\'1\' as approvalNo,b.b0101 as b0101,\r\nopa.REVERT_LICENCE_TIME as revertLicenceTime,CONCAT(IFNULL(orp.SURNAME,\'\'),IFNULL(orp.NAME,\'\')) as nowUsername,opd.ESTIMATE_RETURNTIME as estimateReturntime\r\nfrom oms_pri_delay_apply opd LEFT JOIN oms_pri_apply opa on opd.APPLY_ID=opa.ID left join oms_reg_procpersoninfo orp on opa.PROCPERSON_ID = orp.ID left JOIN b01 b on opa.b0100=b.b0111\r\nwhere opd.ID = \'@applyId\'', '1', '1', '2020-07-24 14:57:30', NULL, NULL, NULL, '4');
# 因私 请示表
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '314',
       '$姓名$',
       'getName',
       '姓名',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '314',
       '$国家（地区）$',
       'getGoCountry',
       '国家地区',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '314',
       '$出境时间$',
       'getAbroadTime',
       '出境时间',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '314',
       '$入境时间$',
       'getReturnTime',
       '入境时间',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       'REPLACE(uuid(),'-','')',
       '314',
       '$停留时间$',
       'getOutsideTime',
       '停留时间',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;



# 因私 呈批单
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '313',
       '$姓名$',
       'getName',
       '姓名',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '313',
       '$国家（地区）$',
       'getGoCountry',
       '国家地区',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '313',
       '$出国事由$',
       'getAbroadReasons',
       '出国（境）事由',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '313',
       '$停留时间$',
       'getOutsideTime',
       '停留时间',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '313',
       '$当前用户$',
       'getNowUsername',
       '当前用户',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '313',
       '$当前用户$',
       'getNowUsername',
       '当前用户',
       'oms_pri_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

# 因公
INSERT INTO `hxoms`.`oms_file` (`ID`, `FILE_ID`, `B0100`, `FILE_NAME`, `FILE_SHORTNAME`, `FRONT_CONTENT`, `BANK_CONTENT`, `FILE_TYPE`, `TABLE_CODE`, `IS_EDIT`, `SEAL_DESC`, `RUN_SQL`, `PRINT_NUM`, `ISFILE_LIST`, `CREATE_TIME`, `CREATE_USER`, `MODIFY_TIME`, `MODIFY_USER`, `SORT_ID`) VALUES ('311', NULL, NULL, '《因公呈批单》', '呈批单', '<table width=\"771\">\r\n            <tbody>\r\n                <tr style=\"height:67px\" class=\"firstRow\">\r\n                    <td colspan=\"6\" width=\"771\" style=\"font-size:40px;text-align:center;\">中 共 海 南 省 委 组 织 部</td>\r\n                </tr>\r\n                <tr style=\"height:59px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">承 办 单 位：</td>\r\n                    <td colspan=\"3\" style=\"font-size:20px;border-right:1px solid #666;\">干  部  监  督  处</td>\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">拟 稿 人：</td>\r\n                    <td colspan=\"1\" style=\"font-size:20px;\">$用户$</td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td colspan=\"3\" style=\"font-size:20px;border-right:1px solid #666;\">&nbsp;&nbsp;处领导签署：</td>\r\n                    <td colspan=\"3\" style=\"font-size:20px;\">&nbsp;&nbsp;部领导批示：</td>\r\n                </tr>\r\n                <tr style=\"height:100px;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"3\" style=\"font-size:20px;border-right:1px solid #666;\"><br/></td>\r\n                    <td colspan=\"3\" style=\"font-size:20px;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:59px;\">\r\n                    <td ></td>\r\n                    <td ></td>\r\n                    <td colspan=\"2\"><div style =\"width:100%;height:30px;border:1px solid #666;text-align:center;\"></div></td>\r\n                    <td ></td>\r\n                    <td ></td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td colspan=\"6\" style=\"font-size:26px;text-align:center;\">省 管 干 部 因 公 出 国 及 赴 港 澳 台 备 案 件</td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td colspan=\"6\" style=\"font-size:20px;\">&nbsp;&nbsp;处领导：</td>\r\n                </tr>\r\n                <tr style=\"height:120px\">\r\n                    <td colspan=\"6\" style=\"font-size:20px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关于$姓名$同志赴$国家（地区）$$出访任务$事宜，已经$批文号$文批准，根据琼办发[2020]21号文件规定，现呈上备案。</td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td colspan=\"2\" style=\"font-size:20px;\">干部监督处</td>\r\n                </tr>\r\n                <tr style=\"height:59px\">\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td></td>\r\n                    <td colspan=\"2\" style=\"font-size:20px;\">$当前时间$</td>\r\n                </tr>\r\n            </tbody>\r\n        </table>', NULL, '1', 'oms_pub_apply_cadres', '2', '', 'SELECT\r\n	CONCAT(\r\n		IFNULL(orp.SURNAME, \'\'),\r\n		IFNULL(orp. NAME, \'\')\r\n	) AS NAME,\r\n	(\r\n		SELECT\r\n			item_name\r\n		FROM\r\n			sys_dict_item\r\n		WHERE\r\n			dict_code = \'GB2261\'\r\n		AND item_code = orp.SEX\r\n	) AS sex,\r\n	opa.POLITICAL_AFF AS politicalAffi,\r\n	orp.BIRTH_DATE_GB AS birthDate,\r\n	orp.HEALTH AS health,\r\n	orp.IDNUMBER_GB AS idnumber,\r\n	orp.REGISTE_RESIDENCE AS registeResidence,\r\n	(\r\n		SELECT\r\n			item_name\r\n		FROM\r\n			sys_dict_item\r\n		WHERE\r\n			dict_code = \'SMDJ\'\r\n		AND item_code = opa.CLASSIFICATION_LEVEL\r\n	) AS secretLevel,\r\n	DATE_FORMAT(opa.CGSJ,\'%Y.%m.%d\') AS abroadTime,\r\n	DATE_FORMAT(opa.HGSJ,\'%Y.%m.%d\') AS returnTime,\r\n	(\r\n		SELECT\r\n			GROUP_CONCAT(NAME_ZH)\r\n		FROM\r\n			country\r\n		WHERE\r\n			id IN (opa.GO_COUNTRY)\r\n	) AS goCountry,\r\n	opa.TLSJ AS outsideTime,\r\n\r\n	NOW() AS nowTime,\r\n  opa.PWH as pwh,\r\n  opa.cfrw as cfrw,\r\n	CONCAT(\r\n		IFNULL(orp.SURNAME, \'\'),\r\n		IFNULL(orp. NAME, \'\')\r\n	) AS contactPerson,\r\n	\'1\' AS approvalNo,\r\n	b.b0101 AS b0101,\r\n	opa.REVERT_LICENCE_TIME AS revertLicenceTime,\r\n	CONCAT(\r\n		IFNULL(orp.SURNAME, \'\'),\r\n		IFNULL(orp. NAME, \'\')\r\n	) AS nowUsername\r\n	\r\nFROM\r\n	\r\noms_pub_apply opa \r\nLEFT JOIN oms_reg_procpersoninfo orp ON opa.a0100 = orp.a0100\r\nLEFT JOIN b01 b ON opa.b0100 = b.b0111\r\nWHERE\r\n	opa.ID = \'@applyId\'', '1', '1', '2020-07-24 14:57:30', NULL, NULL, NULL, '1');
INSERT INTO `hxoms`.`oms_file` (`ID`, `FILE_ID`, `B0100`, `FILE_NAME`, `FILE_SHORTNAME`, `FRONT_CONTENT`, `BANK_CONTENT`, `FILE_TYPE`, `TABLE_CODE`, `IS_EDIT`, `SEAL_DESC`, `RUN_SQL`, `PRINT_NUM`, `ISFILE_LIST`, `CREATE_TIME`, `CREATE_USER`, `MODIFY_TIME`, `MODIFY_USER`, `SORT_ID`) VALUES ('312', NULL, NULL, '《因公请示表》', '请示表', '<div style=\"width:771px;font-size:40px;text-align:center;\">中共海南省委组织部来文处理单</div>\r\n        <table width=\"771\" border>\r\n            <tbody>\r\n                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">来文单位</td>\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">来文编号</td>\r\n                    <td colspan=\"3\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td  style=\"font-size:20px;text-align:center;\">收文时间</td>\r\n                    <td  style=\"width:150px;font-size:20px;text-align:center;\"><br/></td>\r\n                    <td  style=\"font-size:20px;text-align:center;\">办文编号</td>\r\n                    <td  style=\"width:150px;font-size:20px;text-align:center;\"><br/></td>\r\n                    <td  style=\"font-size:20px;text-align:center;\">密级</td>\r\n                    <td  style=\"width:80px;font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">文件名称</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\">关于建议不予办理$姓名$同志出国（境）备案手续的请示</td>\r\n                </tr>\r\n                <tr style=\"height:150px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"width:200px;font-size:20px;text-align:center;\">干部监督处拟办意见</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;近日，我处收到关于办理$姓名$同志赴$国家（地区）$备案手续的请示，$姓名$同志于$出境时间$至$入境时间$赴$国家（地区）$开展$出访任务$，在外停留$停留时间$天。经征求省纪委意见，省纪委对$姓名$出国（境）持异议，不予回复意见。建议不予办理$姓名$同志出国（境）备案手续。\r\n\r\n    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;妥否，呈$分管部长$副部长审示。</td>\r\n                </tr>\r\n                <tr style=\"height:80px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">部领导批示：</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:80px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">省领导批示：</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n                <tr style=\"height:45px;border-top:1px solid #666;border-bottom:1px solid #666;\">\r\n                    <td colspan=\"1\" style=\"font-size:20px;text-align:center;\">承办结果</td>\r\n                    <td colspan=\"5\" style=\"font-size:20px;text-align:center;\"><br/></td>\r\n                </tr>\r\n            </tbody>\r\n        </table>', NULL, '1', 'oms_pub_apply_cadres', '2', '', 'SELECT\r\n	CONCAT(\r\n		IFNULL(orp.SURNAME, \'\'),\r\n		IFNULL(orp. NAME, \'\')\r\n	) AS NAME,\r\n	(\r\n		SELECT\r\n			item_name\r\n		FROM\r\n			sys_dict_item\r\n		WHERE\r\n			dict_code = \'GB2261\'\r\n		AND item_code = orp.SEX\r\n	) AS sex,\r\n	opa.POLITICAL_AFF AS politicalAffi,\r\n	orp.BIRTH_DATE_GB AS birthDate,\r\n	orp.HEALTH AS health,\r\n	orp.IDNUMBER_GB AS idnumber,\r\n	orp.REGISTE_RESIDENCE AS registeResidence,\r\n	(\r\n		SELECT\r\n			item_name\r\n		FROM\r\n			sys_dict_item\r\n		WHERE\r\n			dict_code = \'SMDJ\'\r\n		AND item_code = opa.CLASSIFICATION_LEVEL\r\n	) AS secretLevel,\r\n	DATE_FORMAT(opa.CGSJ,\'%Y.%m.%d\') AS abroadTime,\r\n	DATE_FORMAT(opa.HGSJ,\'%Y.%m.%d\') AS returnTime,\r\n	(\r\n		SELECT\r\n			GROUP_CONCAT(NAME_ZH)\r\n		FROM\r\n			country\r\n		WHERE\r\n			id IN (opa.GO_COUNTRY)\r\n	) AS goCountry,\r\n	opa.TLSJ AS outsideTime,\r\n\r\n	NOW() AS nowTime,\r\n  opa.PWH as pwh,\r\n  opa.cfrw as cfrw,\r\n	CONCAT(\r\n		IFNULL(orp.SURNAME, \'\'),\r\n		IFNULL(orp. NAME, \'\')\r\n	) AS contactPerson,\r\n	\'1\' AS approvalNo,\r\n	b.b0101 AS b0101,\r\n	opa.REVERT_LICENCE_TIME AS revertLicenceTime,\r\n	CONCAT(\r\n		IFNULL(orp.SURNAME, \'\'),\r\n		IFNULL(orp. NAME, \'\')\r\n	) AS nowUsername\r\n	\r\nFROM\r\n	\r\noms_pub_apply opa \r\nLEFT JOIN oms_reg_procpersoninfo orp ON opa.a0100 = orp.a0100\r\nLEFT JOIN b01 b ON opa.b0100 = b.b0111\r\nWHERE\r\n	opa.ID = \'@applyId\'', '1', '1', '2020-07-24 14:57:30', NULL, NULL, NULL, '2');


#因公呈批单
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '311',
       '$当前用户$',
       'getNowUsername',
       '当前用户',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '311',
       '$姓名$',
       'getName',
       '姓名',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '311',
       '$国家（地区）$',
       'getName',
       '国家地区',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '311',
       '$出访任务$',
       'getCfrw',
       '出访任务',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;



INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '311',
       '$批文号$',
       'getPwh',
       '批文号',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

# 因公请示表

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '312',
       '$姓名$',
       'getName',
       '姓名',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '312',
       '$国家（地区）$',
       'getGoCountry',
       '姓名',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '312',
       '$出境时间$',
       'getAbroadTime',
       '出境时间',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '312',
       '$入境时间$',
       'getReturnTime',
       '入境时间',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '312',
       '$出访任务$',
       'getCfrw',
       '出访任务',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '312',
       '$停留时间$',
       'getOutsideTime',
       '停留时间',
       'oms_pub_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

#延期

#延期呈批单
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '315',
       '$姓名$',
       'getName',
       '姓名',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '315',
       '$工作单位$',
       'getB0101',
       '工作单位',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '315',
       '$出境时间$',
       'getAbroadTime',
       '出境时间',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '315',
       '$入境时间$',
       'getReturnTime',
       '入境时间',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '315',
       '$国家（地区）$',
       'getGoCountry',
       '国家地区',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '315',
       '$国家（地区）$',
       'getGoCountry',
       '国家地区',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '315',
       '$出国事由$',
       'getAbroadReasons',
       '出国（境）事由',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '315',
       '$延期时间$',
       'getDelayTime',
       '延期时间',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;
# 延期 请示表
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '316',
       '$姓名$',
       'getName',
       '姓名',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '316',
       '$工作单位$',
       'getB0101',
       '工作单位',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '316',
       '$出境时间$',
       'getAbroadTime',
       '出境时间',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '316',
       '$入境时间$',
       'getReturnTime',
       '入境时间',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '316',
       '$国家（地区）$',
       'getGoCountry',
       '国家地区',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;
INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '316',
       '$国家（地区）$',
       'getGoCountry',
       '国家地区',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;

INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '316',
       '$出国事由$',
       'getAbroadReasons',
       '出国（境）事由',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;


INSERT INTO `hxoms`.`oms_replace_keywords` (
    `ID`,
    `FILE_ID`,
    `KEYWORD`,
    `REPLACE_FIELD`,
    `DESCRIPTION`,
    `TYPE`,
    `USE_TYPE`,
    `CREATE_TIME`,
    `CREATE_USER`,
    `MODIFY_TIME`,
    `MODIFY_USER`
    )
select

       REPLACE(uuid(),'-',''),
       '316',
       '$延期时间$',
       'getDelayTime',
       '延期时间',
       'oms_pri_delay_apply_cadres',
       '1',
       now(),
       NULL,
       NULL,
       NULL
;