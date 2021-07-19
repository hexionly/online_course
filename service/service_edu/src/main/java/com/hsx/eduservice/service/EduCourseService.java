package com.hsx.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsx.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsx.eduservice.entity.frontvo.CourseFrontVo;
import com.hsx.eduservice.entity.frontvo.CourseWebVo;
import com.hsx.eduservice.entity.vo.CourseInfoVo;
import com.hsx.eduservice.entity.vo.CoursePublisVo;
import com.hsx.eduservice.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-09
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublisVo publishCourseInfo(String id);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    void removeCourse(String courseId);

    List<EduCourse> courseList();

    Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
