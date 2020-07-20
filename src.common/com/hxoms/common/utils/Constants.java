package com.hxoms.common.utils;

public class Constants {


    // 任免表导出模板路径
    public static final String TEMPLATE = "template";

    // 任免表导出文件存放路径
    public static final String RMB_EXPORT_PATH = "exportTableFile";

    public static final int PAGE_SIZE = 20;

    /**
     * 人员照片存放路径
     */
    public static final String PHOTOS_PATH = "/photos";
    /**
     * 人员文件存储路径
     */
    public static final String PERSON_FILE_PATH = "/upload/personFiles";

    /**
     * 成功返回值
     */
    public static final int SUCCESS = 0;
    /**
     * 失败返回值
     */
    public static final int ERROR = 1;

    /**
     * 默认重置密码12345678
     */
    public static final String USER_PWD = "12345678";
    /**
     * 请求头中的key
     */
    public static final String TOKEN_KEY = "hx-token";

    public static final String EXPIRE_TIMES = "expireTimes";
    /**
     * token过期时间
     */
    public static final String DEFAULT_PASS_MINS = "30";

    /***********************************************消息模块*************************************************/

    /**
     * 是否是根节点
     */
    public static String IS_ROOT = "1";
    public static final String Not_ROOT = "0";
    /**
     * 是否删除
     */
    public static String IS_DEL = "1";
    public static String NOT_DEL = "0";
    /**
     * 消息标识
     */
    /**
     * 个人
     */
    public static String PERSONAL = "1";
    /**
     * 处室
     */
    public static String DEPARTMENT = "2";
    /**
     * 机构
     */
    public static String ORGANIZATION = "3";
    /**
     * 讨论组
     */
    public static String DISCUSSIONGROUP = "4";
    /**
     * 处理状态
     */
    /**
     * 历史消息
     */
    public static String HISTORY_MESSAGE = "1";
    /**
     * 未处理消息
     */
    public static String NOT_HANDLE_MESSAGE = "0";
    /**
     * 发送类别
     */
    /**
     * 普通消息
     */
    public static String GENERAL_MESSAGE = "1";
    /**
     * 讨论组消息
     */
    public static String DISCUSSION_MESSAGE = "2";
    /**
     * 是否置顶
     */
    /**
     * 置顶
     */
    public static String IS_TOP = "1";
    /**
     * 未置顶
     */
    public static String NOT_TOP = "0";
    /**
     * 发件箱or收件箱
     */
    /**
     * 发件箱
     */
    public static String RECEIVE = "receive";
    /**
     * 收件箱
     */
    public static String SEND = "send";
    public static final String START_PAGE = "/index.jsp";
    /**
     * 干部类别
     */
    public static String LEADER_TYPE = "LeaderType";
    /**
     * 用户类型(0-系统管理员、1-超级管理员、2-安全保密管理员、3-安全审计管理员、4-各单位管理员、5-监督处工作人员、6-经办人、7-组织部相关处室、
     *          8-省外办、9-统战部（港澳办）、10-统战部（台办）、11-省保密局、12-省纪委、13-处领导、14-部领导、15-其他)
     */
    public static String[] USER_TYPES = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
    /**
     * 用户状态(0-注册;1-正常;2-撤销;3-监督处审核;4-处领导审批;5-拒绝;6-待撤销;7-暂停;8-身份认证;)
     */
    public static String[] USER_STATUS={"0","1","2","3","4","5","6","7","8"};
    /**
     * 用户状态名称(0-注册;1-正常;2-撤销;3-监督处审核;4-处领导审批;5-拒绝;6-待撤销;7-暂停;8-身份认证;)
     */
    public static String[] USER_STATUSName={"注册","正常","撤销","监督处审核","处领导审批","拒绝","待撤销","暂停","身份认证"};
    /**
     * 人员信息集
     */
    public static String PERSON_INFO = "person_infos";

    /**
     * lucene根目录配置
     */
    public static String LUCENE_BASE_DIR = "E:/lucene";

    /**
     *配偶子女b0100
     */
    public static String FAMILY_B0100 = "ABB9009B-023F-4641-9C9E-BECCBED53A6C";

    /**
     *  备案表文件标题
     */
    public static String[] TITLES = {"因公临时出国人员备案表","因公临时赴港澳人员备案表","因公临时赴台人员备案表"};

    /*******出国境 业务类型*****/
    public static String[] oms_business={"oms_pub_apply","oms_pri_apply","oms_pri_delay_apply"};
    public static String[] oms_businessName={"因公出国","因私出国","延期回国"};

    /**
     * 因公 业务
     *
     */
    public  static int[] public_business ={1,2,3};
    /**
     * 因公 业务 状态名称
     *
     */
    public  static String[] public_businessName ={"未下发","草稿","带材料审核"};

    /**
     *调整期干部状态{0-调整期,1-调整完成}
     *
     */
    public static int[] mobilizing_business={0,1};
    /**
     *调整期干部状态名称
     *
     */
    public static String[] mobilizing_businessName={"调整期","调整完成"};
    /**
     *经办人交接状态{0待办、1完成、2撤消、3经办人交接、4接手人确认、5管理员确认}
     *
     */
    public static int[] handover_business={0,1,2,3,4,5};
    /**
     *因公出国境是否下达{0-未下达,1-已下达}
     *
     */
    public static int[] pub_business={0,1};
    /**
     *经办人交接业务类别{0.证照领取,1.因公出国（境）,2.因私出国（境），3.延期回国，4.撤销登记备案}
     *
     */
    public static String[] handover_type={"0","1","2","3","4"};
    /**
     *经办人交接状态名称
     *
     */
    public static String[] handover_businessName={"代办","完成","撤销"};
    /**
     * 因私 业务
     *
     */
    public  static int[] private_business ={1,2,3,4,5,29,30,31};
    /**
     * 因私 业务 状态名称
     *
     */
    public  static String[] private_businessName ={"草稿","生成材料","打印材料清单","签字盖章","自评","待领证","已领证","撤销"};

    /**
     *  干部监督处业务
     *
     *  数字 从 20 开始
     * */


    public static int[] leader_business={20,21,22,23,24,25,26,27,28};


    /**
     *  干部监督处业务 状态名称
     * */

    public static String[] leader_businessName={"业务办理","征求意见","记录意见","做成审核意见","处领导审批","部领导审批","核实批件","制作备案表","已办结"};

    /**
     *  干部监督处 批次 (主)状态
     * */

    public static String[] batch_status={"ba1","ba2"};

    /**
     *  干部监督处业务 (主)状态名称
     * */

    public static String[] batch_statusName={"批次正在处理","批次已处理"};


    /**
     *  干部监督处 批次 (副)状态  待定
     * */

    public static String[] batch_slave_status={"ba1","ba2"};

    /**
     *  干部监督处业务 （副）状态名称
     * */

    public static String[] batch_slave_statusName={"业务办理","征求意见","记录意见","做成审核意见","处领导审批","部领导审批","核实批件","制作备案表","已办结"};

    /*******约束条件保存校验*****/
    public static String[] OMS_CONDITION_CHECKTYPE={"1","2"};
    public static String[] OMS_CONDITION_CHECKTYPENAME={"保存前","保存后"};
    /*******关键词替换使用范围*****/
    public static String[] OMS_KEYWORDS_USERTYPE={"1","2"};
    public static String[] OMS_KEYWORDS_USERTYPENAME={"文件","约束条件"};
    /*******约束条件约束类型*****/
    public static String[] OMS_CONDITION_CARETYPE={"1","2"};
    public static String[] OMS_CONDITION_CARETYPENAME={"约束","提醒"};
    /*******约束条件配置类型*****/
    public static String[] OMS_CONDITION_SETTINGTYPE={"1","2", "3"};
    public static String[] OMS_CONDITION_SETTINGTYPENAME={"非配置","可配置", "开关"};
    /*******约束条件提醒人员类别*****/
    public static String[] OMS_CONDITION_REMIND_PERSONTYPE={"1","2", "4", "8"};
    public static String[] OMS_CONDITION_REMIND_PERSONNAME={"经办人","组团单位", "审批单位", "干部监督处"};
}
