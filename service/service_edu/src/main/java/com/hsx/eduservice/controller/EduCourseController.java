package com.hsx.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsx.commonutils.R;
import com.hsx.eduservice.entity.EduCourse;
import com.hsx.eduservice.entity.vo.CourseInfoVo;
import com.hsx.eduservice.entity.vo.CourseQuery;
import com.hsx.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-09
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //添加课程信息
    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@ApiParam(name = "courseInfoVo", value = "课程信息", required = true)
                           @RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //根据课程id查询课程信息
    @ApiOperation(value = "根据课程查询课程信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@ApiParam(name = "courseId", value = "课程id", required = true)
                           @PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@ApiParam(name = "courseInfoVo", value = "课程信息", required = true)
                              @RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @ApiOperation(value = "课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@ApiParam(name = "id", value = "课程id", required = true)
                                  @PathVariable String id) {
        return R.ok().data("publishCourse", eduCourseService.publishCourseInfo(id));
    }

    //课程最终发布，修改课程状态
    @ApiOperation(value = "修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@ApiParam(name = "id", value = "课程id", required = true)
                           @PathVariable String id) {
        eduCourseService.updateById(new EduCourse() {{
            setId(id);
            setStatus("Normal");
        }});
        return R.ok();
    }

    //条件分页显示
    @ApiOperation(value = "分页课程列表")
    @PostMapping("pageCourse/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        eduCourseService.pageQuery(pageParam, courseQuery);
        return R.ok().data("total", pageParam.getTotal()).data("rows", pageParam.getRecords());
    }

    //删除课程
    @ApiOperation(value = "删除课程")
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@ApiParam(name = "courseId",value = "课程id",required = true)
                          @PathVariable String courseId){
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }
}