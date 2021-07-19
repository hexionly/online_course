package com.hsx.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsx.eduservice.entity.EduChapter;
import com.hsx.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-09
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);
}
