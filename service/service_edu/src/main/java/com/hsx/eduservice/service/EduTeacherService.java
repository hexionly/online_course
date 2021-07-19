package com.hsx.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsx.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-05
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> teacherList();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
