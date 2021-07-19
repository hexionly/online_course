package com.hsx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsx.eduservice.entity.EduCourse;
import com.hsx.eduservice.entity.EduCourseDescription;
import com.hsx.eduservice.entity.frontvo.CourseFrontVo;
import com.hsx.eduservice.entity.frontvo.CourseWebVo;
import com.hsx.eduservice.entity.vo.CourseInfoVo;
import com.hsx.eduservice.entity.vo.CoursePublisVo;
import com.hsx.eduservice.entity.vo.CourseQuery;
import com.hsx.eduservice.mapper.EduCourseMapper;
import com.hsx.eduservice.service.EduChapterService;
import com.hsx.eduservice.service.EduCourseDescriptionService;
import com.hsx.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsx.eduservice.service.EduVideoService;
import com.hsx.servicebase.exception.HexionlyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    //添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        EduCourse eduCourse = new EduCourse();

        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        //像课程表假数据
        int insert = baseMapper.insert(eduCourse);

        //判断是否添加成功
        if (insert == 0) {
            throw new HexionlyException(20001, "添加课程信息失败");
        }

        //像课程简介表加简介
        eduCourseDescriptionService.save(new EduCourseDescription() {{
            setDescription(courseInfoVo.getDescription());
            setId(eduCourse.getId());
        }});
        return eduCourse.getId();
    }

    //根据课程id查询课程信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程
        EduCourse eduCourse = baseMapper.selectById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();

        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //查询描述
        EduCourseDescription courseDescript = eduCourseDescriptionService.getById(courseId);

        courseInfoVo.setDescription(courseDescript.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new HexionlyException(20001, "修改课程信息失败");
        }

        //修改描述表
        eduCourseDescriptionService.updateById(new EduCourseDescription() {{
            setId(courseInfoVo.getId());
            setDescription(courseInfoVo.getDescription());
        }});
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublisVo publishCourseInfo(String id) {
        //调用mapper
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        if (!StringUtils.isEmpty(courseQuery.getTitle())) {
            queryWrapper.like("title", courseQuery.getTitle());
        }
        if (!StringUtils.isEmpty(courseQuery.getTeacherId())) {
            queryWrapper.eq("teacher_id", courseQuery.getTeacherId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);

        //根据课程id删除描述
        eduCourseDescriptionService.removeById(courseId);

        //根据id删除课程本身
        int delete = baseMapper.deleteById(courseId);
        if (delete == 0) {
            throw new HexionlyException(20001, "删除失败");
        }
    }

    //查询热门课程
    @Cacheable(value = "course", key = "'selectCourseList'")
    @Override
    public List<EduCourse> courseList() {
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        return baseMapper.selectList(courseWrapper);
    }

    //条件查询，带分页的课程
    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空
        //一级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        //二级分类
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        //关注度
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        //最新
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        //价格
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageCourse, wrapper);
        return new HashMap<String, Object>() {{
            put("records", pageCourse.getRecords());
            put("current", pageCourse.getCurrent());
            put("pages", pageCourse.getPages());
            put("total", pageCourse.getTotal());
            put("size", pageCourse.getSize());
            put("hasNext", pageCourse.hasNext());
            put("hasPrevious", pageCourse.hasPrevious());
        }};
    }

    //课程详情
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
