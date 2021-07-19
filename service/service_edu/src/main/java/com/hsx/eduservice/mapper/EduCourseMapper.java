package com.hsx.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsx.eduservice.entity.EduCourse;
import com.hsx.eduservice.entity.frontvo.CourseWebVo;
import com.hsx.eduservice.entity.vo.CoursePublisVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublisVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
