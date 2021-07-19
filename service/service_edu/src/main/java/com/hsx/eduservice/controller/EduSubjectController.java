package com.hsx.eduservice.controller;


import com.hsx.commonutils.R;
import com.hsx.eduservice.entity.subject.OneSubject;
import com.hsx.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-08
 */
@Api(tags = "课程科目")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /*
    添加课程分类
    获取上传过来的文件，把文件内容读取出来
     */
    @ApiOperation(value = "添加课程科目")
    @PostMapping("addSubject")
    public R addSubject(@ApiParam(name = "file", value = "课程科目文件", required = true)
                                MultipartFile file) {
        //上传过来的excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类列表（树形）
    @ApiOperation(value = "课程分类列表（树形）")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

