package com.hsx.statistics.controller;


import com.hsx.commonutils.R;
import com.hsx.statistics.client.UcenterClient;
import com.hsx.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-05-16
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class DailyController {

    @Autowired
    private DailyService dailyService;

    //统计某一天注册人数
    @GetMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        dailyService.registerCount(day);
        return R.ok();
    }

    //图表显示，返回两部分数据，日期json数据，数量json数据
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,
                      @PathVariable String end){
        Map<String, Object> map = dailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

