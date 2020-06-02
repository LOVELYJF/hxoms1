package com.hxoms.modules.dataCapture.timingschedule;

import com.hxoms.modules.dataCapture.synchdata.Synchdata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * @authore:wjf
 * @data 2020/4/18 9:09
 * @Description: 定时调度任务
 ***/
@Service
@PropertySource("classpath:/dataCapture.porperties")
public class ScheduleServiceImp {
    @Autowired
    private Synchdata synchronizationData;


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    @Value("${job.cron}")
//    private String cron;
//
    private Logger logger= LoggerFactory.getLogger(getClass());
    int count = 1;
//    int count2 = 1;
//    int count3 = 1;
//    @Scheduled(fixedRate = 1000)
////    @Async
//    public void jobFixedRate(){
//        Thread.currentThread().setName("间隔执行");
//        logger.info("{}.执行第{}次",Thread.currentThread().getName(),count1++);
//    }
//    @Scheduled(fixedRateString = "${job.fixedRate}")
////    @Async
//    public void jobFixedRateString(){
//        Thread.currentThread().setName("spel表达式间隔执行");
//        logger.info("{}.执行第{}次",Thread.currentThread().getName(),count2++);
//    }
//    @Scheduled(fixedDelay = 3000)
////    @Async
//    public void jobFixedDelay() throws InterruptedException{
//        Thread.sleep(1000);
//        Thread.currentThread().setName("上次任务完成后间隔执行");
//        logger.info("{}.执行第{}次",Thread.currentThread().getName(),count3++);
//    }
//    @Scheduled(initialDelay = 8000,fixedRate = 1000)
////    @Async
//    public void jobInitialDelay(){
//        Thread.currentThread().setName("spring容器完成后间隔执行");
//        logger.info("{}.执行第{}次",Thread.currentThread().getName(),count3++);
//    }
//    @Scheduled(cron = "0 24 17 * * ?")
    @Scheduled(cron = "${job.cron}")
//    @Async   次注解是开启 异步 可以开启多线程 以后出现效率问题，在改造代码 用它优化
    public void jobCron(){
        Thread.currentThread().setName("cron表达式执行");
        Calendar calendar = Calendar.getInstance();
        logger.info("开始执行数据同步"+sdf.format(calendar.getTime()));
        synchronizationData.synchronizationData();

        Calendar calendar1 = Calendar.getInstance();
        logger.info("数据同步执行结束"+sdf.format(calendar1.getTime()));
        logger.info("{}.执行第{}次",Thread.currentThread().getName(),count++);
    }





}
