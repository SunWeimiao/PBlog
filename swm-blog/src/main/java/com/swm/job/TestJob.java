package com.swm.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestJob {
//    @Scheduled(cron = "0/5 * * * * ?")//从0秒开始，每隔5秒执行一次。
    public void testjob(){
        //要执行的代码
        System.out.println("定时任务执行了");
    }

}
