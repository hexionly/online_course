package com.hsx.eduservice.controller;


import com.hsx.commonutils.R;
import com.hsx.eduservice.entity.EduChapter;
import com.hsx.eduservice.entity.vo.ChapterVo;
import com.hsx.eduservice.service.EduChapterService;
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
@Api(tags = "章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //课程大纲列表，根据课程id查询
    @ApiOperation(value = "课程大纲列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@ApiParam(name = "courseId", value = "课程id", required = true)
                             @PathVariable String courseId) {
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    //添加章节
    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@ApiParam(name = "eduChapter", value = "章节信息", required = true)
                        @RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //根据id查询章节
    @ApiOperation(value = "根据id查询章节")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@ApiParam(name = "chapterId", value = "章节id", required = true)
                            @PathVariable String chapterId) {
        return R.ok().data("chapter", eduChapterService.getById(chapterId));
    }

    //修改章节
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@ApiParam(name = "eduChapter", value = "章节信息", required = true)
                           @RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除章节
    @ApiOperation(value = "删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@ApiParam(name = "chapterId", value = "章节id", required = true)
                           @PathVariable String chapterId) {
        if (eduChapterService.deleteChapter(chapterId)) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

