package com.hxoms.common.applyStatus;

public class ApplyStatus {



   // 业务类型
   public static final String  public_business = "因公"  ;
   public static final String  private_business = "因私"  ;
   public static final String  delay_business = "延期"  ;
   //...





   // 因公
    public static final String  pul_not_issued = "未下发"  ;  //未下发
    public static final String  pul_draft = "草稿"  ;  //草稿
    public static final String  pul_materials = "带材料审核"  ;  //带材料审核


    //因私
    public static final String  pri_draft = "草稿"  ;  //草稿
    public static final String  pri_materials = "生成材料"  ;  //生成材料
    public static final String  pri_print_materials= "打印材料"  ;  //打印材料
    public static final String  pri_appera = "自评上报"  ;  //自评上报

    public static final String  pri_wait_card = "待领证"  ;  //待领证
    public static final String  pri_card_over = "已领证"  ;  //已领证
    public static final String  pri_revocation = "撤销"  ;  //撤销

    //延期
    public static final String  del_draft = "草稿"  ;  //草稿
    public static final String  del_materials = "生成材料"  ;  //生成材料
    public static final String  del_print_materials= "打印材料"  ;  //打印材料
    public static final String  del_appera = "自评上报"  ;  //自评上报

    public static final String  del_wait_card = "待领证"  ;  //待领证
    public static final String  del_card_over = "已领证"  ;  //已领证
    public static final String  del_revocation = "撤销"  ;  //撤销


   // 最新流程 不区分 因私 因公 延期

   public static final String   common_business_work = "业务办理"  ;  //业务办理
   public static final String   common_task_advice = "征求意见"  ;  //征求意见
   public static final String   common_record_advice = "记录意见"  ;  //记录意见
   public static final String   common_check_advice = "做成审核意见"  ;  //做成审核意见
   public static final String   common_chu_check = "处领导审批"  ;  //处领导审批
   public static final String   common_bu_check = "部领导审批"  ;  //部领导审批
   public static final String   common_check_instructions = "核实批件"  ;  //核实批件
   public static final String   common_make_table = "制作备案表"  ;  //制作备案表
   public static final String   common_overed = "已办结"  ;  //已办结




    // 批次 状态  (主状态)

   public static final String   bath_status_staring = "批次正在处理"  ;  //批次正在处理


   // 整个 批次 的人员 全部 审批通过 批次状态 置成  已完成。
   public static final String   bath_status_over = "批次已处理"  ;  //批次已处理

   // 批次 副状态 （预留）










}
