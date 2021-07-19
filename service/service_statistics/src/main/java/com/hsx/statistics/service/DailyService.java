package com.hsx.statistics.service;

import com.hsx.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-05-16
 */
public interface DailyService extends IService<Daily> {

    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
