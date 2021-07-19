package com.hsx.statistics.schedule;

import com.hsx.statistics.service.DailyService;
import com.hsx.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {

    @Autowired
    private DailyService dailyService;

    /*
        定时任务
        0/5 * * * * ?表示每隔5秒执行一次该方法
        每天凌晨一点，执行此方法，把前一天的数据进行查询添加
    */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task1() {
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
