package com.hsx.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsx.commonutils.R;
import com.hsx.eduservice.entity.EduCourse;
import com.hsx.eduservice.entity.EduTeacher;
import com.hsx.eduservice.service.EduCourseService;
import com.hsx.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前四条名师
    @GetMapping("index")
    public R index(){
        //查询前8条热门课程
        List<EduCourse> courseList = courseService.courseList();

        //查询前四条名师
        List<EduTeacher> teacherList = teacherService.teacherList();

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
