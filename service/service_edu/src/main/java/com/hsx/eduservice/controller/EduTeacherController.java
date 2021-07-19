package com.hsx.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsx.commonutils.R;
import com.hsx.eduservice.entity.EduTeacher;
import com.hsx.eduservice.entity.vo.TeacherQuery;
import com.hsx.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-05
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    //注入service
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询讲师表中所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service中的方法查询所有
        return R.ok().data("items", eduTeacherService.list(null));
    }

    //逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                           @PathVariable String id) {
        if (eduTeacherService.removeById(id)) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询讲师方法
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit) {
        //把分页数据封装到teacherPage对象里面
        Page<EduTeacher> teacherPage = new Page<>(current, limit);

        //异常测试
//        try{
//            int i = 10 / 0;
//        }catch (Exception e){
//            throw new HexionlyException(20001,"执行了自定义异常");
//        }

        eduTeacherService.page(teacherPage, null);

        //总页数
        long total = teacherPage.getTotal();
        //数据
        List<EduTeacher> teacherList = teacherPage.getRecords();

        //也可以写在map中
        return R.ok().data("total", total).data("teacherList", teacherList);
    }

    //条件查询带分页的
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit,
            //参数值可以为空,必须是post请求
            @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建配置对象
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(teacherQuery.getName())) {
            wrapper.like("name", teacherQuery.getName());
        }

        if (!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level", teacherQuery.getLevel());
        }

        if (!StringUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("begin", teacherQuery.getBegin());
        }

        if (!StringUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.ge("end", teacherQuery.getEnd());
        }

        //根据时间进行排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件分页
        eduTeacherService.page(teacherPage, wrapper);
        //总页数
        long total = teacherPage.getTotal();
        //数据
        List<EduTeacher> teacherList = teacherPage.getRecords();
        return R.ok().data("total", total).data("teacherList", teacherList);
    }

    //添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        if (eduTeacherService.save(eduTeacher)) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据讲师id查询讲师信息
    @ApiOperation(value = "根据讲师id查询讲师信息")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "ID", value = "讲师id", required = true)
                        @PathVariable String id) {
        return R.ok().data("teacher", eduTeacherService.getById(id));
    }

    //讲师修改
    @ApiOperation(value = "讲师修改")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        if (eduTeacherService.updateById(eduTeacher)) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

