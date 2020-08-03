/*
package com.hxoms.modules.leaderSupervision.test;

import com.hxoms.modules.leaderSupervision.config.StatusConvertLinkMap;
import com.hxoms.modules.leaderSupervision.entity.OmsJiweiOpinion;
import com.hxoms.modules.leaderSupervision.until.LeaderSupervisionUntil;
import com.hxoms.modules.leaderSupervision.vo.OmsJiweiOpinionVo;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
*/
/*
public class LeaderSupersivisionTest {

//    @Autowired
//    private LeaderSupervisionUntil leaderSupervisionUntil;

    @Test
    public void testBatchCodeCreat(){

//    String batchCode =  LeaderSupervisionUntil.factoryBatchCode("HNSW-20200610001");


//        System.out.println(leaderSupervisionUntil.factoryBatchCode("HNSW-20200610001"));
        String name=   LeaderSupervisionUntil.selectorBussinessTypeByName("延期");

        System.out.println(LeaderSupervisionUntil.selectorBussinessTypeByName("延期"));

    }

    @Test
    public void testArray(){

//        OmsJiweiOpinionVo omsJiweiOpinionVo  = new OmsJiweiOpinionVo();
//
//        omsJiweiOpinionVo.setId("1");
//        omsJiweiOpinionVo.setFeedbackDate(new Date());
//        omsJiweiOpinionVo.setFeedbackVerdict("2222222222");
//        omsJiweiOpinionVo.setBussinessId(new String[]{"22","33","44"});
//
//        OmsJiweiOpinion  o =   new OmsJiweiOpinion();
//
//        BeanUtils.copyProperties(omsJiweiOpinionVo, o);
//
//        System.out.println(o);

        OmsJiweiOpinion omsJiweiOpinion = new OmsJiweiOpinion();

        OmsJiweiOpinionVo omsJiweiOpinionVo = new OmsJiweiOpinionVo();

        omsJiweiOpinionVo.setOpinion("2222");
        omsJiweiOpinionVo.setFeedbackVerdict("2");
        omsJiweiOpinionVo.setRemark("3333333333333");

        omsJiweiOpinion.setOfficialFeedbackType("2");
        Class<?>  omsJiweiOpinionClass =   omsJiweiOpinion.getClass();

        Field[] fields =   omsJiweiOpinionClass.getDeclaredFields();

        for (int i = 0; i <fields.length ; i++) {

            Field field = fields[i];
            field.setAccessible(true);

            if(field.getName().contains("opinion")){

                omsJiweiOpinion.setOfficialOpinion(omsJiweiOpinionVo.getOpinion()!=null?omsJiweiOpinionVo.getOpinion():"");

            }

            if(field.getName().toLowerCase().contains("feedbackType".toLowerCase())){

                omsJiweiOpinion.setOfficialFeedbackVerdict(omsJiweiOpinionVo.getFeedbackVerdict());

            }

            if(field.getName().contains("remark")){

                omsJiweiOpinion.setOfficialRemark(omsJiweiOpinionVo.getRemark());
            }



        }

        System.out.println(omsJiweiOpinion);



    }

    @Test
    public void testSpring(){

//        SpringUtil.setApplicationContext();

     //   Object o = SpringUtil.getBean("dataSource");
//        Object o1 = SpringUtil.getBean(DynamicData.class);
//
////        System.out.println(o);
//        System.out.println(o1);
//
        List list  = new ArrayList();

        Map map;

        for(int i=0;i<4;i++){

             map = new HashMap();

            map.put("name",i);

            list.add(map);

        }
        try {
            int[] nums = new int[]{1,2,3};

            System.out.println(nums[4]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();

            System.out.println(e.getMessage()+"wjf");
            System.out.println(e.toString()+"wjf");
        }


    }

    @Test
    public void testReturn(){

        
//        for(int i=0; i<5; i++){
//
//            System.out.println(i);
//
//            if(i==2){
//
//                return;
//
//            }
//
//
//
//
//        }

        StatusConvertLinkMap statusConvertLinkMap = new StatusConvertLinkMap();

        statusConvertLinkMap.put("applyStatus","22");
        System.out.println(statusConvertLinkMap);



    }
    @Test
    public void testString(){

//        String msg ;
//        String msg1="4444";
//
//        msg ="11111111";
//        msg1 ="222222";
//      //  msg1.concat("555555555");
//         msg1+="5555555555";
//        msg="333333333333333";
//
//        System.out.println(msg);
//        System.out.println(msg1);
//
////        List<String> idList=new ArrayList<String>();
////        idList.add("1002");
////        idList.add("6002");
////        idList.add("3206");
//
//        String[]  str= {"2222","2345","675"};
//        String ids = "'"+ StringUtils.join(str,"','")+"'";
//
//        System.out.println(ids);
        System.out.printf("字母a的大写是：%c %n", 'A');

    }




}
*/
