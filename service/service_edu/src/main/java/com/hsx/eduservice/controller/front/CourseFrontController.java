package com.hsx.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsx.commonutils.JwtUtils;
import com.hsx.commonutils.R;
import com.hsx.eduservice.client.OrderClient;
import com.hsx.eduservice.entity.EduCourse;
import com.hsx.eduservice.entity.frontvo.CourseFrontVo;
import com.hsx.eduservice.entity.frontvo.CourseWebVo;
import com.hsx.eduservice.entity.vo.ChapterVo;
import com.hsx.eduservice.service.EduChapterService;
import com.hsx.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    //条件查询，带分页的课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.getFrontCourseList(pageCourse, courseFrontVo);
        return R.ok().data(map);
    }

    //课程详情
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //查询课程信息
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);

        //章节小节
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoByCourseId(courseId);

        //根据课程id和用户id查询课程是否已支付
        boolean buyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isBuy",buyCourse);
    }

    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVo getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVo courseWebVo = new CourseWebVo();
        BeanUtils.copyProperties(courseInfo,courseWebVo);
        return courseWebVo;
    }
}
