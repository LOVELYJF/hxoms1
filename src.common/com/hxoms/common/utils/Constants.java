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
     * 用户类型(0-管理员;1-普通用户)
     */
    public static String[] USER_TYPES = {"0", "1"};
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
    public  static String[] public_business ={"pu1","pu2","pu3","pu4"};
    /**
     * 因公 业务 状态名称
     *
     */
    public  static String[] public_businessName ={"未下发","草稿","带材料审核"};

    /**
     * 因私 业务
     *
     */
    public  static String[] private_business ={"pr1","pr2","pr3","pr4","pr5","pr6","pr7"};
    /**
     * 因私 业务 状态名称
     *
     */
    public  static String[] private_businessName ={"草稿","生成材料","打印材料","自评上报","待领证","已领证","撤销"};

    /**
     *  干部监督处业务
     * */

    public static String[] leader_business={"le1","le2","le3","le4","le5","le6","le7","le8","le9"};


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
}
