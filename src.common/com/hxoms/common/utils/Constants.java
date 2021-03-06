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
     * 8-省外办、9-统战部（港澳办）、10-统战部（台办）、11-省保密局、12-省纪委、13-处领导、14-部领导、15-其他)
     */
    public static String[] USER_TYPES = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    /**
     * 用户状态(0-注册;1-正常;2-撤销;3-监督处审核;4-处领导审批;5-拒绝;6-待撤销;7-暂停;8-身份认证;)
     */
    public static String[] USER_STATUS = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
    /**
     * 用户状态名称(0-注册;1-正常;2-撤销;3-监督处审核;4-处领导审批;5-拒绝;6-待撤销;7-暂停;8-身份认证;)
     */
    public static String[] USER_STATUSName = {"注册", "正常", "撤销", "监督处审核", "处领导审批", "拒绝", "待撤销", "暂停", "身份认证"};
    /**
     * 人员信息集
     */
    public static String PERSON_INFO = "person_infos";

    /**
     * lucene根目录配置
     */
    public static String LUCENE_BASE_DIR = "E:/lucene";

    /**
     * 配偶子女b0100
     */
    public static String FAMILY_B0100 = "ABB9009B-023F-4641-9C9E-BECCBED53A6C";

    /**
     * 备案表文件标题
     */
    public static String[] TITLES = {"因公临时出国人员备案表", "因公临时赴港澳人员备案表", "因公临时赴台人员备案表"};

    /*******出国境 业务类型*****/
    public static String[] oms_business = {"oms_pub_apply", "oms_pri_apply", "oms_pri_delay_apply", "oms_pub_apply","oms_ftpj_apply"};
    public static String[] oms_businessName = {"因公出国", "因私出国", "延期回国", "干教","台办赴台批件"};

    /**
     * 因公 业务
     */
    public static int[] public_business = {1, 2, 3};
    /**
     * 因公 业务 状态名称
     */
    public static String[] public_businessName = {"未下发", "草稿", "带材料审核"};

    /**
     * 调整期干部状态{0-调整期,1-调整完成}
     */
    public static int[] mobilizing_business = {0, 1};
    /**
     * 调整期干部状态名称
     */
    public static String[] mobilizing_businessName = {"调整期", "调整完成"};
    /**
     * 经办人交接状态{0待办、1完成、2撤消、3经办人交接、4接手人确认、5管理员确认}
     */
    public static int[] handover_business = {0, 1, 2, 3, 4, 5};
    /**
     * 因公出国境是否下达{0-未下达,1-已下达}
     */
    public static int[] pub_business = {0, 1};
    /**
     * 经办人交接业务类别{0.证照领取,1.因公出国（境）,2.因私出国（境），3.延期回国，4.撤销登记备案,5.注销证照}
     */
    public static String[] handover_type = {"0", "1", "2", "3", "4", "5"};
    /**
     * 经办人交接状态名称
     */
    public static String[] handover_businessName = {"代办", "完成", "撤销"};
    /**
     * 预备案主体代码
     * 1.因公出国（境）教育培训预备案、2.省外办下达任务方式、3.因特殊原因因公赴港澳预审批、4.台办发起的赴台备案流程
     */
    public static int[] PubGroupPreApproval_business = {1, 2, 3, 4};
    /**
     * 预备案主体名称
     */
    public static String[] PubGroupPreApproval_businessName = {"因公出国（境）教育培训预备案", "省外办下达任务方式", "因特殊原因因公赴港澳预审批", "台办发起的赴台备案流程"};
    /**
     * 干教申请状态
     * 0-草稿，1-征求意见，2-下达任务，3-撤销
     */
    public static int[] GJ_business = {0, 1, 2, 3};

    /**
     * 干部监督处 批次 (主)状态
     */

    public static String[] batch_status = {"ba1", "ba2"};

    /**
     * 干部监督处业务 (主)状态名称
     */

    public static String[] batch_statusName = {"批次正在处理", "批次已处理"};


    /**
     * 干部监督处 批次 (副)状态  待定
     */

    public static String[] batch_slave_status = {"ba1", "ba2"};

    /**
     * 干部监督处业务 （副）状态名称
     */

    public static String[] batch_slave_statusName = {"业务办理", "征求意见", "记录意见", "做成审核意见", "处领导审批", "部领导审批", "核实批件", "制作备案表", "已办结"};

    /*******约束条件保存校验*****/
    public static String[] OMS_CONDITION_CHECKTYPE = {"1", "2"};
    public static String[] OMS_CONDITION_CHECKTYPENAME = {"保存前", "保存后"};
    /*******关键词替换使用范围*****/
    public static String[] OMS_KEYWORDS_USERTYPE = {"1", "2"};
    public static String[] OMS_KEYWORDS_USERTYPENAME = {"文件", "约束条件"};
    /*******约束条件约束类型*****/
    public static String[] OMS_CONDITION_CARETYPE = {"1", "2"};
    public static String[] OMS_CONDITION_CARETYPENAME = {"约束", "提醒"};
    /*******约束条件配置类型*****/
    public static String[] OMS_CONDITION_SETTINGTYPE = {"1", "2", "3"};
    public static String[] OMS_CONDITION_SETTINGTYPENAME = {"非配置", "可配置", "开关"};
    /*******约束条件提醒人员类别*****/
    public static String[] OMS_CONDITION_REMIND_PERSONTYPE = {"1", "2", "4", "8"};
    public static String[] OMS_CONDITION_REMIND_PERSONNAME = {"经办人", "组团单位", "审批单位", "干部监督处"};

    //证照类型代码
    public static int[] CER_TYPE_CODE = {32, 1, 2, 4, 8, 16};
    //证照类型名称
    public static String[] CER_TYPE_NAME = {"查询中", "护照", "港澳通行证", "台湾通行证", "无证照", "非省委管理证照"};

    //证照借出申请状态代码
    public static int[] CER_LENDING_TYPE = {0, 1, 2, 3, 4};
    //证照借出申请状态名称
    public static String[] CER_LENDING_NAME = {"申请", "审批", "已审批", "已拒绝", "撤销"};

    //在职状态代码
    public static int[] INCUMBENCY_STATUS = {1, 2, 3, 4, 5, 6, 7, 8, 9,10,99};
    //在职状态名称
    public static String[] INCUMBENCY_STATUS_NAME = {"在职", "辞职", "开除", "解聘", "免职撤职", "退休", "去世", "调出","挂职到期","未匹配","其他"};

    //证照注销状态代码
    public static int[] CANCELL_STATUS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    //证照注销状态名称
    public static String[] CANCELL_NAME = {"填写申请", "生成材料", "打印材料清单",
            "提交干部监督处", "受理", "处领导审批", "部领导审批", "生成注销函", "拒绝", "撤销", "完成注销", "已办结"};

    //证照注销方式代码
    public static int[] CANCELL_MODE_STATUS = {0, 1};
    //证照注销方式名称
    public static String[] CANCELL_MODE_NAME = {"自行注销", "委托"};

    //证照保管代码
    public static int[] CER_SAVE_STATUS = {0, 1, 2};
    //证照保管名称
    public static String[] CER_SAVE_NAME = {"正常保管", "已取出", "未上缴"};

    //证照状态代码
    public static int[] CER_STATUS = {0, 1, 2, 3, 4, 5, 6, 40, 41};
    //证照状态名称
    public static String[] CER_NAME = {"正常", "过期", "注销", "验证失败", "已验证", "待验证", "借出", "待领取", "已领取"};

    //证照注销原因代码
    public static int[] CANCELL_REASON_STATUS = {1, 2, 3, 4};
    //证照注销原因名称
    public static String[] CANCELL_REASON_NAME = {"遗失", "被盗", "损毁", "其他原因"};
    /**
     * 因公团组流程状态代码
     */
    public static final int[] PUB_GROUP_STATUS_CODE = {0, 1, 2, 3, 4};
    /**
     * 因公团组流程状态名称
     */
    public static final String[] PUB_GROUP_STATUS_NAME = {"撤销", "上传/填写申请", "审核备案", "上传批文", "已完结"};
    /**
     * 是
     */
    public static final String IS_YES = "1";
    /**
     * 否
     */
    public static final String IS_NOT = "0";

    //出入境状态 1出境 2 入境
    public static final int[] OGE_STATUS_CODE = {1, 2};


    /**
     * 登记备案表在职状态
     * "在职", "辞职", "开除", "解聘", "免职撤职", "退休", "去世", "调出","挂职到期","其他","未匹配"
     */
    public enum emIncumbencyStatus {
        Working("在职", 1),
        Resignation("辞职",2),
        Expel("开除",3),
        Dismissal("解聘",4),
        Removal ("免职撤职",5),
        Retirement("退休",6),
        Death("去世",7),
        Dispatch("调出",8),
        Secondment("挂职到期",9),
        Other("其他",10),
        Unmatched("未匹配",99);
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private emIncumbencyStatus(String name, int index) {
            this.name = name;
            this.index = index;
        }

        // 普通方法
        public static String getName(int index) {
            for (emIncumbencyStatus c : emIncumbencyStatus.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        // get set 方法
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        /**
         * 根据Index获取去Name
         * @param index
         * @return
         */
        public static String getNameByIndex(int index){
            for(emIncumbencyStatus platformFree:emIncumbencyStatus.values()){
                if(index==(platformFree.getIndex())){
                    return platformFree.getName();
                }
            }
            return  null;
        }
    }
    /**
    * 撤销登记备案状态
    **/
    public enum emRevokeRegister{
        申请("申请",1),
        受理("受理",2),
        处领导审核("处领导审核",3),
        部领导审批("部领导审批",4),
        撤销("撤销",5),
        拒批("拒批",6),
        待备案("待备案",7),
                已备案("已备案",8);

        private emRevokeRegister(String name, int index) {
            this.name = name;
            this.index = index;
        }
        private String name;
        private int index;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        /**
         * 根据Index获取去Name
         * @param index
         * @return
         */
        public static String getNameByIndex(int index){
            for(emRevokeRegister platformFree:emRevokeRegister.values()){
                if(index==(platformFree.getIndex())){
                    return platformFree.getName();
                }
            }
            return  null;
        }
    }

    /**
    * @description:因公出国境状态枚举
    * @author:杨波
    * @date:2020-10-16
    **/
    public enum emPublicGoAbroad{
        预备案填写中("预备案填写中",0),
        草稿("草稿",1),
        生成材料("生成材料",2),
        打印材料清单("打印材料清单",3),
        签字盖章("签字盖章",4),
        自评("自评",5),
        业务受理("业务受理",20),
        征求意见("征求意见",21),
        记录意见("记录意见",22),
        做出审核意见("做出审核意见",23),
        处领导审批("处领导审批",24),
        部领导审批("部领导审批",25),
        核实批件("核实批件",26),
        制作备案表("制作备案表",27),
        已办结("已办结",28),
        待领证("待领证",29),
        已领证("已领证",30),
        撤销("撤销",31);

        private emPublicGoAbroad(String name, int index) {
            this.name = name;
            this.index = index;
        }
        private String name;
        private int index;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        /**
         * 根据Index获取去Name
         * @param index
         * @return
         */
        public static String getNameByIndex(int index){
            for(emPublicGoAbroad platformFree:emPublicGoAbroad.values()){
                if(index==(platformFree.getIndex())){
                    return platformFree.getName();
                }
            }
            return  null;
        }
    }
    /**
     * @description:因私出国境状态枚举
     * @author:杨波
     * @date:2020-10-16
     **/
    public enum emPrivateGoAbroad{
        草稿("草稿",1),
        生成材料("生成材料",2),
        打印材料清单("打印材料清单",3),
        签字盖章("签字盖章",4),
        自评("自评",5),
        业务受理("业务受理",20),
        征求意见("征求意见",21),
        记录意见("记录意见",22),
        做出审核意见("做出审核意见",23),
        处领导审批("处领导审批",24),
        部领导审批("部领导审批",25),
        核实批件("核实批件",26),
        制作备案表("制作备案表",27),
        已办结("已办结",28),
        待领证("待领证",29),
        已领证("已领证",30),
        撤销("撤销",31);

        private emPrivateGoAbroad(String name, int index) {
            this.name = name;
            this.index = index;
        }
        private String name;
        private int index;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        /**
         * 根据Index获取去Name
         * @param index
         * @return
         */
        public static String getNameByIndex(int index){
            for(emPrivateGoAbroad platformFree:emPrivateGoAbroad.values()){
                if(index==(platformFree.getIndex())){
                    return platformFree.getName();
                }
            }
            return  null;
        }
    }

    /**
     * 涉密等级
     **/
    public enum emSecretLevel{
        非涉密("非涉密",0),
        一般("一般",1),
        重要("重要",2),
        核心("核心",3);

        private emSecretLevel(String name, int index) {
            this.name = name;
            this.index = index;
        }
        private String name;
        private int index;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
    }

    /**
     * 证件类型
     **/
    public enum emCertificate{
        护照("护照",1),
        港澳通行证("港澳通行证",2),
        台湾通行证("台湾通行证",4);

        private emCertificate(String name, int index) {
            this.name = name;
            this.index = index;
        }
        private String name;
        private int index;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
    }
}
