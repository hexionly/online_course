package com.hsx.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsx.commonutils.R;
import com.hsx.statistics.client.UcenterClient;
import com.hsx.statistics.entity.Daily;
import com.hsx.statistics.mapper.DailyMapper;
import com.hsx.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-05-16
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //统计某一天注册人数
    @Override
    public void registerCount(String day) {

        //添加数据之前先删除表中相同日期的数据
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        R countRegister = ucenterClient.countRegister(day);
        Integer regCount = (Integer) countRegister.getData().get("countRegister");

        //把取出数据添加数据库，统计分析表里面
        Daily daily = new Daily() {{
            setRegisterNum(regCount);
            setDateCalculated(day);

            setVideoViewNum(RandomUtils.nextInt(100, 200));
            setLoginNum(RandomUtils.nextInt(100, 200));
            setCourseNum(RandomUtils.nextInt(100, 200));
        }};

        baseMapper.insert(daily);
    }

    //图表显示，返回两部分数据，日期json数据，数量json数据
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        List<Daily> dailyList = baseMapper.selectList(wrapper);
        List<String> dateList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (int i = 0; i < dailyList.size(); i++) {
            Daily daily = dailyList.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":
                    countList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    countList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    countList.add(daily.getVideoViewNum());
                    break;
                default:
                    countList.add(daily.getCourseNum());
            }
        }
        return new HashMap<String, Object>() {{
            put("dateList", dateList);
            put("countList", countList);
        }};
    }
}
